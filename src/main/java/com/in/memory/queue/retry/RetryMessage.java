package com.in.memory.queue.retry;

import com.in.memory.queue.models.ConsumerGroup;
import com.in.memory.queue.models.Message;
import com.in.memory.queue.models.Topic;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RetryMessage {
    private Message message;
    private int retriesLeft = 3;
    private Topic topic;
    private ConsumerGroup consumerGroup;
    private String failureReason;

    public RetryMessage(Message message, int retriesLeft, Topic topic, ConsumerGroup consumerGroup, String failureReason) {
        this.message = message;
        this.retriesLeft = retriesLeft;
        this.topic = topic;
        this.consumerGroup = consumerGroup;
        this.failureReason = failureReason;
    }

    public RetryMessage(Message message, Topic topic, ConsumerGroup consumerGroup, String failureReason) {
        this.message = message;
        this.topic = topic;
        this.consumerGroup = consumerGroup;
        this.failureReason = failureReason;
    }

    @Override
    public String toString() {
        return "RetryMessage{" +
                message +
                ", <retriesLeft>: " + retriesLeft +
                ", <T>>:" + topic.getTopicName() +
                ", <CG>:" + consumerGroup.getConsumerGroupId() +
                ", <ERR>'" + failureReason + '\'' +
                '}';
    }
}
