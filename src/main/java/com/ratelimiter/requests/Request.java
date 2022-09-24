package com.ratelimiter.requests;

import lombok.Getter;

import java.time.Instant;

@Getter
public class Request {
    private Instant timeStamp;
    private String payload;
    private int userId;

    public Request(String payload, int userId) {
        this.timeStamp = Instant.now();
        this.payload = payload;
        this.userId = userId;
    }
}
