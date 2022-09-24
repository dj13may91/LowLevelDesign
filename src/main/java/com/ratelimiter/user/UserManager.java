package com.ratelimiter.user;

import com.ratelimiter.requests.Request;
import com.ratelimiter.requests.RequestManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UserManager {
    private static final AtomicInteger userId = new AtomicInteger(1);
    private final Map<Integer, User> userMap = new HashMap<>();
    private final RequestManager requestManager = RequestManager.getInstance();

    public User registerUser(UserPlan userPlan) {
        User user = new User(userId.getAndIncrement(), userPlan);
        userMap.put(user.getUserId(), user);
        requestManager.enableUserAccess(user);
        System.out.println("User " + user.getUserId() + " created with plan " + user.getUserPlan());
        return user;
    }

    public void makeRequest(Request request){
        requestManager.createRequest(request);
    }

    public void validateUser(int userId) {
        if (!userMap.containsKey(userId)) {
            throw new RuntimeException("User ID " + userId + " is not registered");
        }
    }
}
