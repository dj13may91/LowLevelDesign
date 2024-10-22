package com.in.memory.queue.publisher;

import com.in.memory.queue.models.*;
import com.in.memory.queue.retry.RetryMessage;

import java.util.Random;
import java.util.function.Predicate;

public class ChatterBox {
    private static final Random random = new Random();
    private final Topic topic;
    private final TopicErrorQueue errorQueue;

    public ChatterBox(Topic topic, TopicErrorQueue errorQueue) {
        this.topic = topic;
        this.errorQueue = errorQueue;
    }

    public void publish(ConsumerGroup group) {
        Consumer consumer = group.getConsumerList().get(random.nextInt(group.getConsumerList().size()));
        Message message = topic.getMessageProcessor().getLatestMessage(consumer.getOffset().getAndIncrement());
        try {
            sendToConsumer(group, consumer, message);
        } catch (Exception ex) {
            System.err.println("Message publishing failed: " + ex.getMessage());
            System.err.println("^Retry Registering^ ! <M> : " + message);
            errorQueue.getRetryHandler().addMessageToRetry(new RetryMessage(message, topic, group, ex.getMessage()));
//            throw new CustomMQException(ex.getMessage(), message);
        }
    }

    public void sendToConsumer(ConsumerGroup group, Message message) {
        Consumer consumer = group.getConsumerList().get(random.nextInt(group.getConsumerList().size()));
        sendToConsumer(group, consumer, message);
    }

    private void sendToConsumer(ConsumerGroup group, Consumer consumer, Message message) {
        if (group.getFilters().size() > 0) {
            filterAndProcessMessage(consumer, message);
        } else {
            consumer.consume(message);
            topic.getConsumerGroup(consumer.getConsumerGroupId()).getConsumerList().parallelStream()
                    .forEach(c -> c.setOffset(topic.getMessageProcessor().findOffset()));
        }
    }

    private void filterAndProcessMessage(Consumer consumer, Message message) {
        if (message != null) {
            boolean isPatternMatching = true;
            for (Predicate<Message> filter : topic.getConsumerGroup(consumer.getConsumerGroupId()).getFilters()) {
                isPatternMatching = isPatternMatching && filter.test(message);
            }
            if (isPatternMatching) {
                consumer.consume(message);
                topic.getConsumerGroup(consumer.getConsumerGroupId()).getConsumerList().parallelStream()
                        .forEach(c -> c.setOffset(topic.getMessageProcessor().findOffset()));
            } else {
                System.out.println(consumer.getConsumerGroupId() + "<discarded by filters> <M>" + message + "; <C>: " + consumer.getConsumerName() + ",<CG>: " + consumer.getConsumerGroupId());
            }
            consumer.setOffset(topic.getMessageProcessor().findOffset());
        }
    }
}
