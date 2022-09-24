package com.game.of.snake;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
    private final int x;
    private final int y;
    private final Snake snake = new Snake();
    private boolean outOfBoundsAllowed = true;
    private final Set<String> snakeCoveredPositions = new HashSet<>();
    private final Map<String, Cell> foodMap = new HashMap<>();

    public Board(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Board(int x, int y, boolean outOfBoundsAllowed) {
        this.x = x;
        this.y = y;
        this.outOfBoundsAllowed = outOfBoundsAllowed;
    }

    public void addFood(Cell food) {
        foodMap.put(food.toString(), food);
    }

    public void consumeFood(Cell newHead) {
        if (foodMap.containsKey(newHead.toString())) {
            System.out.println("Consumed food at " + newHead);
            foodMap.remove(newHead.toString());
        }
    }

    public void moveSnake(Move move) {
        Cell nextHead = getNextHead(snake.getHead(), move);
        System.out.println("Moved " + move + "; Snake head at " + nextHead);
        if (foodMap.containsKey(nextHead.toString())) {
            snake.getSnakePositions().add(nextHead);
            consumeFood(nextHead);
        } else {
            Cell freed = snake.getSnakePositions().poll();
            snakeCoveredPositions.remove(freed.toString());
            snake.getSnakePositions().add(nextHead);
        }
        snake.setHead(nextHead);
        checkCollision(nextHead);
        snakeCoveredPositions.add(nextHead.toString());
        System.out.println("Snake: " + snake) ;
    }

    private void checkCollision(Cell next) {
        if (snakeCoveredPositions.contains(next.toString())) {
            System.err.println("Snake collided with itself at " + next);
            System.out.println("Covered positions were " + snakeCoveredPositions);
            System.err.println("Collide and Die!!!!");
            System.exit(1);
        }
    }

    private Cell getNextHead(Cell next, Move move) {
        int headX = snake.getHead().getX() + move.x;
        int headY = snake.getHead().getY() + move.y;
        if (outOfBoundsAllowed) {
//            System.out.println("Allowed for out of bounds, snake will come in from next end");
            headX = headX < 0 ? x - 1 : headX;
            headY = headY < 0 ? y - 1 : headY;

            headX = headX >= x ? 0 : headX;
            headY = headY >= x ? 0 : headY;
        } else if (next.getX() < 0 || next.getY() < 0 || next.getX() >= x || next.getY() >= y) {
            System.err.println("Snake out of bounds at " + next);
            System.err.println("Fell and died!!");
            System.exit(2);
        }
        return new Cell(headX, headY);
    }
}
