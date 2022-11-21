package com.elevator.constant;

public enum Direction {
    UP(1),
    DOWN(-1),
    ;
    public final int move;

    Direction(int move) {
        this.move = move;
    }
}
