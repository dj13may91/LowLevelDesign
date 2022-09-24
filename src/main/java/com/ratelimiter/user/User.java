package com.ratelimiter.user;

import lombok.Getter;

@Getter
public class User {
    private final int userId;
    private UserPlan userPlan;
//    private RateLimitAlgo rateLimitAlgo;

    public User(int userId, UserPlan userPlan) {
        this.userId = userId;
        this.userPlan = userPlan;
    }

    public void changeUserPlan(UserPlan userPlan) {
        System.out.println("Changing userType from " + userPlan + " to " + userPlan);
        this.userPlan = userPlan;
    }
}