package com.ratelimiter.types;

import com.ratelimiter.requests.Request;

public class LeakyBucket extends RateLimiter {

    @Override
    public void hitServer(Request request) {
        // todo
    }

    @Override
    public boolean isAllowed(Request request) {
        return false;
    }
}
