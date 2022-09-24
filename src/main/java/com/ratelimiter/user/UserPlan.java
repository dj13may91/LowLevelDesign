package com.ratelimiter.user;

public enum UserPlan {
    BASIC(3),
    GOLD(5),
    DIAMOND(10),
    BLOCKED(0);

    final int limit;

    public int getLimit() {
        return limit;
    }

    UserPlan(int limit) {
        this.limit = limit;
    }
}
