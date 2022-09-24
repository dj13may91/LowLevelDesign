package com.ratelimiter.requests;

import com.ratelimiter.types.RateLimiter;
import com.ratelimiter.types.SlidingWindow;
import com.ratelimiter.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestManager {

    private final Map<Integer, List<Request>> userRequests = new ConcurrentHashMap<>();
    private final Map<Integer, Request> userLastRequestLedger = new ConcurrentHashMap<>();
    private final Map<Integer, RateLimiter> userRateLimitMap = new ConcurrentHashMap<>();
    private static final RequestManager requestManager = new RequestManager();

    public RequestManager() {
    }

    public static RequestManager getInstance() {
        return requestManager;
    }

    public void createRequest(Request request) {
        updateEntries(request);
        new Thread(() -> userRateLimitMap.get(request.getUserId()).hitServer(request)).start();
    }

    public void enableUserAccess(User user) {
        userRequests.put(user.getUserId(), new ArrayList<>());
        userRateLimitMap.put(user.getUserId(), new SlidingWindow(user));
    }

    public Request getUserLastRequest(int userId) {
        return userLastRequestLedger.get(userId);
    }

    private void updateEntries(Request request) {
        userRequests.get(request.getUserId()).add(request);
        userLastRequestLedger.put(request.getUserId(), request);
    }
}
