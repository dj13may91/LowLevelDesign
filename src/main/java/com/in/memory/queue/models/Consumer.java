package com.in.memory.queue.models;

import com.in.memory.queue.exception.CustomMQException;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

public class Consumer {

    @Getter private final String consumerName;
    @Getter private final String topicName;
    @Getter private String consumerGroupId;

    public Consumer(String consumerName, String topicName, String consumerGroupId) {
        this.consumerName = consumerName;
        this.topicName = topicName;
        this.consumerGroupId = consumerGroupId;
    }

    public void consume(Message message) {
        if(message.getHttpStatus() == 500){
            throw new CustomMQException("I was thrown for no reason !!", message);
        }
        System.out.println("<C>: " + consumerName + "; <CG>:" + consumerGroupId + "; <M> " + message);
    }

    @Getter
    private final AtomicInteger offset = new AtomicInteger(1);

    @Getter
    @Setter
    boolean subscribed = true;

    public void updateOffset() {
        this.offset.incrementAndGet();
    }

    public void setOffset(int offset) {
        this.offset.set(offset);
    }

}
