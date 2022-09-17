package com.in.memory.queue.models;

import lombok.Data;

@Data
public class Message {
    private String payload;
    private int httpStatus;
    private int TTL = 5000; //default TTL = 3 seconds;
    private boolean isExpired = false;

    public Message(String payload, int httpStatus) {
        this.payload = payload;
        this.httpStatus = httpStatus;
        enableExpiryClock();
    }

    public Message(String payload, int httpStatus, int TTL) {
        this.payload = payload;
        this.httpStatus = httpStatus;
        this.TTL = TTL;
        enableExpiryClock();
    }

    private void enableExpiryClock() {
        new Thread(() -> {
            try {
                Thread.sleep(TTL);
                isExpired = true;
                System.out.println("expired: " + payload);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @Override
    public String toString() {
        return "Message{" +
                "payload='" + payload + '\'' +
                ", httpStatus=" + httpStatus +
                '}';
    }
}
