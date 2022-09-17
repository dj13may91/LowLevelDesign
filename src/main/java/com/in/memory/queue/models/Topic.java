package com.in.memory.queue.models;

import lombok.Getter;

import java.util.*;
import java.util.function.Predicate;

public class Topic {

    private final Map<String, ConsumerGroup> consumerGroupMap = new LinkedHashMap<>();
    @Getter
    private final String topicName;  //always unique
    @Getter
    private final TopicMessageProcessor messageProcessor;

    public Topic(String topicName, int capacity) {
        this.topicName = topicName;
        this.messageProcessor = new TopicMessageProcessor(capacity, topicName);
    }

    synchronized public void addMessage(Message message) {
        messageProcessor.handleMessage(message);
    }

    public void addConsumerGroup(String consumerGroupId, List<Predicate<Message>> filters) {
        if (consumerGroupMap.get(consumerGroupId) != null) {
            throw new RuntimeException("Consumer group " + consumerGroupId + " already exists");
        }
        ConsumerGroup consumerGroup = new ConsumerGroup(consumerGroupId);
        consumerGroup.addFilter(filters);
        consumerGroupMap.put(consumerGroupId, consumerGroup);
    }

    public void addConsumer(Consumer consumer) {
        ConsumerGroup consumerGroup = consumerGroupMap.get(consumer.getConsumerGroupId());
        if (consumerGroup == null) {
            throw new RuntimeException("Consumer-group " + consumer.getConsumerGroupId() + " does not exist!!");
        }
        consumer.setOffset(messageProcessor.findOffset());
        consumerGroup.getConsumerList().add(consumer);
        System.out.println("+++ <C>> " + consumer.getConsumerName() +
                "; <CG>>:" + consumer.getConsumerGroupId() +
                "; <T> " + topicName + "; <offset>: " + consumer.getOffset() + " +++");

    }

    public void removeConsumer(Consumer consumer) {
        consumer.setSubscribed(false);
        consumerGroupMap.get(consumer.getConsumerGroupId()).getConsumerList().remove(consumer);
        System.out.println("----- Consumer " + consumer.getConsumerName() + "(" + consumer.getConsumerGroupId() + ") removed -------");
    }

    public List<ConsumerGroup> getConsumerGroup() {
        return new ArrayList<>(consumerGroupMap.values());
    }

    public ConsumerGroup getConsumerGroup(String consumerGroupId) {
        return consumerGroupMap.get(consumerGroupId);
    }
}
