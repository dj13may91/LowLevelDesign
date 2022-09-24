package com.ratelimiter.types;

import com.ratelimiter.requests.Request;

public abstract class RateLimiter {
    public abstract void hitServer(Request request);
    public abstract boolean isAllowed(Request request);
}
