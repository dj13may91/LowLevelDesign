package com.in.memory.queue.utils;

import com.in.memory.queue.models.TopicErrorQueue;
import com.in.memory.queue.publisher.EventListener;
import com.in.memory.queue.models.ConsumerGroup;
import com.in.memory.queue.models.Topic;
import com.in.memory.queue.publisher.ChatterBox;
import com.in.memory.queue.retry.RetryMessage;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class TopicHandler {
    @Getter
    private final Topic topic;
    private final TopicErrorQueue errorQueue;
    private final Map<String, EventListener> consumerMap;

    public TopicHandler(Topic topic) {
        this.topic = topic;
        consumerMap = new HashMap<>();
        this.errorQueue = new TopicErrorQueue(topic.getTopicName());
        new Thread(this::retry).start();
    }

    public void broadcast() {
        List<ConsumerGroup> groupList = topic.getConsumerGroup();
        ExecutorService executor = Executors.newFixedThreadPool(groupList.size());
        for (ConsumerGroup group : groupList) {
            Runnable runnable = () -> {
                ChatterBox chatterBox = new ChatterBox(topic, errorQueue);
                chatterBox.publish(group);
            };
            executor.submit(runnable);
        }
    }

    @SneakyThrows
    public void retry() {
        while (true) {
            if (errorQueue.getRetryHandler().getMessageToRetry().isEmpty()) {
                Thread.sleep(5000);
            } else {
                RetryMessage message = errorQueue.getRetryHandler().getMessageToRetry().removeFirst();
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Callable callable = () -> {
                    ChatterBox chatterBox = new ChatterBox(topic, errorQueue);
                    Thread.sleep(1500);
                    chatterBox.sendToConsumer(message.getConsumerGroup(), message.getMessage());
                    return true;
                };
                Future future = executor.submit(callable);
                try {
                    future.get();
                } catch (ExecutionException ex) {
                    System.err.println("Failed again, re-registering <M>: " + message);
                    errorQueue.getRetryHandler().addMessageToRetry(message);
                    ex.getCause().printStackTrace();
                }

            }
        }
    }

//pull based mechanism where a consumer constantly listens to a topic or waits if all messages are consumed
    //    public void publish() {
//        topic.getConsumerList().forEach(this::startConsumerWorker);
//    }
//
//    public void startConsumerWorker(Consumer consumer) {
//        String consumerId = consumer.getConsumerName();
//        EventListener eventListener;
//        // lazy initialization so that starving topics don't create new listener on subscription
//        if (!consumerMap.containsKey(consumerId)) {
//            eventListener = new SerialEventListener(topic, consumer, errorQueue);
//            consumerMap.put(consumerId, eventListener);
//        } else {
//            eventListener = consumerMap.get(consumerId);
//        }
//        new Thread(eventListener).start();
//    }
}
