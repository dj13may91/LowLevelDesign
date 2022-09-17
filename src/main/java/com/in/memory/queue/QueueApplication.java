package com.in.memory.queue;

import com.in.memory.queue.models.Consumer;
import com.in.memory.queue.utils.Broker;
import com.in.memory.queue.utils.CustomFilters;
import com.in.memory.queue.models.Message;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class QueueApplication {

    public static void main(String[] args) throws InterruptedException {
        Broker broker = new Broker();
        String topicName = "first";
        broker.createTopic(topicName, 3);
        String consumerGroup = "cg1";
        broker.registerConsumerGroup(consumerGroup, topicName);

        new Thread(() -> {
            Consumer c1 = broker.subscribeTopic(topicName, consumerGroup);
            broker.subscribeTopic(topicName, consumerGroup);
            broker.subscribeTopic(topicName, consumerGroup);
            try {
                Thread.sleep(1000);
                Consumer c2 = broker.subscribeTopic(topicName, consumerGroup);
                Thread.sleep(7500);
                unsubscribeConsumer(broker, c2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        createConsumersWithFilters(broker, topicName);
        produceMessages(broker, topicName);
        createConsumerWithNewGroup(broker, topicName);
    }

    private static void createConsumersWithFilters(Broker broker, String topicName) {
        String consumerGroupId = "cg2";
        broker.registerConsumerGroup(consumerGroupId, topicName, Arrays.asList(CustomFilters.status200, CustomFilters.payloadSizeMoreThan3));
        Consumer c1 = broker.subscribeTopic(topicName, consumerGroupId);
        Consumer c2 = broker.subscribeTopic(topicName, consumerGroupId);
        Consumer c3 = broker.subscribeTopic(topicName, consumerGroupId);
    }

    private static void createConsumerWithNewGroup(Broker broker, String topicName) {
        new Thread(() -> {
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String consumerGroupId = "cg3";
            broker.registerConsumerGroup(consumerGroupId, topicName, Arrays.asList(CustomFilters.status400));
            broker.subscribeTopic(topicName, consumerGroupId);
        }).start();
    }

    private static void unsubscribeConsumer(Broker broker, Consumer c2) {
        new Thread(() -> {
            try {
                Thread.sleep(4500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            broker.unsubscribeTopic(c2);
        }).start();
    }

    private static void produceMessages(Broker broker, String topicName) {
        new Thread(() -> {
            for (int i = 1; i < 6; i++) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int status = i % 2 == 0 ? 200 : 400;
                broker.publishMessage(new Message("hello-" + i, status), topicName);
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(15000);
                System.err.println("Embrace for error!!!");
                Thread.sleep(1000);
                broker.publishMessage(new Message("hello-" +500, 500), topicName);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
