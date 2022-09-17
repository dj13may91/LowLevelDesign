package com.in.memory.queue.utils;

import com.in.memory.queue.models.Consumer;
import com.in.memory.queue.models.Message;
import com.in.memory.queue.models.Topic;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
@Scope("prototype")
public class Broker {

    private final Map<String, TopicHandler> topicHandlers = new HashMap<>();

    public void createTopic(String topicName, int capacity) {
        if (topicHandlers.get(topicName) != null) {
            throw new RuntimeException("Topic " + topicName + " is already registered !!");
        }
        Topic topic = new Topic(topicName, capacity);
        TopicHandler handler = new TopicHandler(topic);
        topicHandlers.put(topic.getTopicName(), handler);
        System.out.println(topicHandlers);
    }

    public void registerConsumerGroup(String consumerGroupId, String topicName) {
        registerConsumerGroup(consumerGroupId, topicName, new ArrayList<>());
    }

    public void registerConsumerGroup(String consumerGroupId, String topicName, List<Predicate<Message>> filters) {
        checkIfTopicExists(topicName);
        topicHandlers.get(topicName).getTopic().addConsumerGroup(consumerGroupId, filters);
    }

    public Consumer subscribeTopic(String topicName, String consumerGroupId) {
        checkIfTopicExists(topicName);
        return registerConsumer(topicName, consumerGroupId);
    }

    private void checkIfTopicExists(String topicName) {
        if (topicHandlers.get(topicName) == null) {
            throw new RuntimeException("Topic " + topicName + " is not registered with Broker!!");
        }
    }

    public void unsubscribeTopic(Consumer consumer) {
        topicHandlers.get(consumer.getTopicName())
                .getTopic()
                .removeConsumer(consumer);
    }

    private Consumer registerConsumer(String topicName, String consumerGroupId) {
        String randomConsumerName = getRandomString(topicName);
        Consumer consumer = new Consumer(randomConsumerName, topicName, consumerGroupId);
        topicHandlers.get(topicName).getTopic().addConsumer(consumer);
        return consumer;
    }

    public void publishMessage(Message message, String topicName) {
        Topic topic = topicHandlers.get(topicName).getTopic();
        topic.addMessage(message);
        new Thread(() -> topicHandlers.get(topicName).broadcast()).start();
    }

    private String getRandomString(String topicName) {
        return  topicName + "-" +UUID.randomUUID().toString().split("-")[0].substring(0,2);
    }

}
