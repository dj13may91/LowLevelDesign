package com.ratelimiter.types;

import com.ratelimiter.requests.Request;
import com.ratelimiter.user.User;

import java.time.Instant;
import java.util.LinkedList;

public class SlidingWindow extends RateLimiter {
    private final int WINDOW_LENGTH_MS = 30000;
    private final User user;
    private final LinkedList<Request> requestWindow = new LinkedList<>();

    public SlidingWindow(User user) {
        this.user = user;
        new Thread(() -> {
            try {
                Thread.sleep(WINDOW_LENGTH_MS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.cleanUpService();
        }).start();
    }

    public void hitServer(Request request) {
        synchronized (this) {
            if (!isAllowed(request)) {
                System.err.println("Dropping request " + request.getPayload() + " for user " + user.getUserId());
                return;
            }
        }
        System.out.println("Executed request " + request.getPayload() + " for user " + user.getUserId());
        requestWindow.addLast(request);
    }

    @Override
    public boolean isAllowed(Request request) {
        System.out.println("User " + user.getUserId() + " plan: " + user.getUserPlan().getLimit() + "; window: " + requestWindow.size());
        return user.getUserPlan().getLimit() - requestWindow.size() > 0;
    }


    public void cleanUpService() {
        System.out.println("$$$ cleanup service in run for user " + user.getUserId());
        try {
            while (true) {
                Thread.sleep(1500);
                if (requestWindow.size() > 0) {
                    boolean remove = true;
                    while (remove && requestWindow.size() > 0) { // remove all old requests in one go
                        Instant reqTime = requestWindow.getFirst().getTimeStamp();
                        if (Instant.now().toEpochMilli() - reqTime.toEpochMilli() > WINDOW_LENGTH_MS) {
                            System.out.println("--- clearing req " + requestWindow.getFirst().getPayload() + " for user " + user.getUserId());
                            requestWindow.removeFirst();
                        } else {
                            remove = false;
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
