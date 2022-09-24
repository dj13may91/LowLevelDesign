package com.game.of.snake;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Queue;

public class Snake {
    @Getter @Setter
    private Cell head;
    @Getter private final Queue<Cell> snakePositions = new LinkedList<>();

    public Snake() {
        head = new Cell(0, 0);
        snakePositions.add(head);
    }

    @Override
    public String toString() {
        return "Snake: " + snakePositions;
    }
}
