package com.in.memory.queue.models;

import com.in.memory.queue.utils.MessageArchivalHandler;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TopicMessageProcessor {

    private final int capacity;
    private final MessageArchivalHandler archivalHandler = new MessageArchivalHandler();
    private final Map<Integer, Message> messageLedger = new HashMap<>();
    private final AtomicInteger offset = new AtomicInteger(1);
    @Getter
    private final String topicName;


    public TopicMessageProcessor(int capacity, String topicName) {
        this.capacity = capacity;
        this.topicName = topicName;
    }

    synchronized public void handleMessage(Message message) {
        if (messageLedger.size() == capacity) {
            int removeOffset = findOffset() - capacity + 1;
            Message archive = messageLedger.remove(removeOffset);
            archivalHandler.handle(archive,removeOffset, getTopicName());
        }
        messageLedger.put(offset.get(), message);
        System.out.println(":" + getTopicName() + ": " + message + "; offset: " + offset.get());
        offset.incrementAndGet();
    }

    public int findOffset() {
        return this.offset.get();
    }


    synchronized public Message getLatestMessage(int offset){
        return messageLedger.get(offset);
    }

    //if a topics wants to read again from a certain offset (say in case of server crash)
    synchronized public Message findMessageByOffset(int offset) {
        if (messageLedger.get(offset) == null) {
            System.out.println("Consuming old message for offset " + offset);
            return archivalHandler.getByOffset(offset);
        }
        if (messageLedger.get(offset).isExpired()) {
            archivalHandler.handle(messageLedger.get(offset),offset, topicName);
            throw new RuntimeException("Message expired!");
        }
        return messageLedger.get(offset);
    }
}
