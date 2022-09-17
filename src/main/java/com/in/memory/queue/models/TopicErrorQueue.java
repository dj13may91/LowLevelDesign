package com.in.memory.queue.models;

import com.in.memory.queue.retry.RetryHandler;
import lombok.Getter;
import lombok.Setter;

public class TopicErrorQueue {

    @Getter
    private final String errorTopicName;  //always unique
    @Getter
    private final RetryHandler retryHandler = new RetryHandler();
    @Setter
    @Getter
    private boolean enableHistory = true;

    public TopicErrorQueue(String errorTopicName) {
        this.errorTopicName = errorTopicName + "_error";
    }

}
