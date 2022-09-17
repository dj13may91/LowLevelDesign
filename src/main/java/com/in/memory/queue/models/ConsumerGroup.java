package com.in.memory.queue.models;

import com.in.memory.queue.constants.ConsumerGroupType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ConsumerGroup implements Comparable<ConsumerGroup> {
    @Getter
    @Setter
    private double priority = 10.0;
    @Getter
    private final String consumerGroupId;
    @Getter
    private final List<Consumer> consumerList = new ArrayList<>();
    @Setter
    private ConsumerGroupType groupType = ConsumerGroupType.SINGLE;
    @Getter
    private final List<Predicate<Message>> filters = new ArrayList<>();

    public ConsumerGroup(String consumerGroupId) {
        this.consumerGroupId = consumerGroupId;
    }

    public void addFilter(List<Predicate<Message>> filter) {
        filters.addAll(filter);
    }

    @Override
    public int compareTo(ConsumerGroup o) {
        if (o.getPriority() < this.getPriority()) {
            return -1;
        } else if (o.getPriority() > this.getPriority()) {
            return 1;
        }
        return 0;
    }
}
