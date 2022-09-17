package com.in.memory.queue.publisher;

import com.in.memory.queue.models.Consumer;
import com.in.memory.queue.models.TopicErrorQueue;
import com.in.memory.queue.models.Topic;

public abstract class EventListener implements IListener {
    private final Topic topic;
    private final Consumer consumer;
    private final TopicErrorQueue errorQueue;

    public EventListener(Topic topic, Consumer consumer, TopicErrorQueue errorQueue) {
        this.topic = topic;
        this.consumer = consumer;
        this.errorQueue = errorQueue;
    }

}
