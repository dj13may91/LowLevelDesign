package com.in.memory.queue.constants;

public enum ConsumerGroupType {
    PARALLEL, // publish on all consumers
    SINGLE, //default: any one of the consumers would be used to publish the message
    DEPENDENT // if there are rules, we make a group dependent i.e. publish based on rules
}
