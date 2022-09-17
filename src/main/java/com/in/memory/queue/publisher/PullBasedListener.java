package com.in.memory.queue.publisher;

import com.in.memory.queue.models.TopicErrorQueue;
import com.in.memory.queue.models.Consumer;
import com.in.memory.queue.models.Message;
import com.in.memory.queue.models.Topic;
import lombok.SneakyThrows;

import java.util.function.Predicate;

public class PullBasedListener extends EventListener {
    private final Topic topic;
    private final Consumer consumer;
    private final TopicErrorQueue errorQueue;

    public PullBasedListener(Topic topic, Consumer consumer, TopicErrorQueue errorQueue) {
        super(topic, consumer, errorQueue);
        this.topic = topic;
        this.consumer = consumer;
        this.errorQueue = errorQueue;
    }

    @SneakyThrows
    @Override
    public void run() {
        synchronized (consumer) {
            while (consumer.isSubscribed()) {
                Thread.sleep(1500);
                int currOffset = consumer.getOffset().get();
                if (currOffset >= topic.getMessageProcessor().findOffset()) {
                    consumer.wait();
                }
                try {
                    filterAndProcessMessage(currOffset);
                } catch (RuntimeException ex) {
                    System.err.println("!!!!! error : " + ex.getMessage() + " !!!!!");
                    ex.printStackTrace();
                }
            }
        }
    }

    private void filterAndProcessMessage(int currOffset) {
        Message message = topic.getMessageProcessor().getLatestMessage(currOffset);
        if (message != null) {
            boolean isPatternMatching = true;
            for (Predicate<Message> filter : topic.getConsumerGroup(consumer.getConsumerGroupId()).getFilters()) {
                isPatternMatching = isPatternMatching && filter.test(message);
            }
            if (isPatternMatching) {
                consumer.consume(message);
                consumer.setOffset(topic.getMessageProcessor().findOffset());
//                topic.getConsumerList()
//                        .forEach(c -> c.setOffset(topic.getMessageProcessor().findOffset()));
            }
        }
    }
}
