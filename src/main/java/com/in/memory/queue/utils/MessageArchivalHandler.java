package com.in.memory.queue.utils;

import com.in.memory.queue.models.Message;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class MessageArchivalHandler {
    private final HashMap<Integer, Message> archivedMessages = new HashMap<>();
    @Setter
    @Getter
    private boolean historyEnabled = true;

    public void handle(Message message,int offset, String topicName) {
        if (isHistoryEnabled()) {
            archivedMessages.put(offset, message);
//            System.out.println("Archived message " + message + " from topic " + topicName);
        }
    }

    public Message getByOffset(int offset) {
        return archivedMessages.get(offset - 1);
    }
}
