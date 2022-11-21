package com.elevator.button;

public interface DropHandler {
    void dropRequest(int to);
    void cancelDrop(int to);
}
