package com.in.memory.queue.retry;

import lombok.Getter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class RetryHandler {
    private static final int MAX_RETRIES_ALLOWED = 3;
    @Getter
    private final LinkedList<RetryMessage> messageToRetry = new LinkedList<>();
    private final Set<RetryMessage> sidelinedMessages = new HashSet<>();

    synchronized public void addMessageToRetry(RetryMessage message) {
        if (message.getRetriesLeft() == 0) {
            addSidelinedMessage(message);
            return;
        }
        message.setRetriesLeft(message.getRetriesLeft() - 1);
        messageToRetry.addLast(message);
        System.out.println("<Retry> added message for retrying: " + message);
    }

    public void addSidelinedMessage(RetryMessage message) {
        messageToRetry.remove(message);
        sidelinedMessages.add(message);
        System.out.println("<Sidelined> " + message);
    }
}
