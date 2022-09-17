package com.in.memory.queue.utils;

import com.in.memory.queue.models.Message;

import java.util.function.Predicate;

public class CustomFilters {
    public static Predicate<Message> status400 = (message) -> message.getHttpStatus() == 400;
    public static Predicate<Message> payloadSizeMoreThan3 = (message) -> message.getPayload().length() > 3;
    public static Predicate<Message> status200 = (message) -> message.getHttpStatus() == 200;
}
