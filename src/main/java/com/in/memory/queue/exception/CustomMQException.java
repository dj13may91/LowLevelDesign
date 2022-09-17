package com.in.memory.queue.exception;

import com.in.memory.queue.models.Message;
import lombok.Getter;

public class CustomMQException extends RuntimeException {
    @Getter private final Message payload;
    @Getter private final String err;

    public CustomMQException(String err, Message message, Throwable cause) {
        super(err, cause);
        this.payload = message;
        this.err = err;
    }

    public CustomMQException(String err, Message message) {
        super(err);
        this.payload = message;
        this.err = err;
    }

}
