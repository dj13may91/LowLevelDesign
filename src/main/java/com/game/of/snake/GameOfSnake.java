package com.game.of.snake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.game.of.snake.Move.*;

// https://www.geeksforgeeks.org/design-snake-game/
public class GameOfSnake {

    public static void main(String[] args) {
        System.out.println("Start game");
        int foodMap[][] = {{1, 2}, {0, 1} , {0,2}};
        Board board = new Board(3, 2);
        for (int[] food : foodMap) {
            Cell cell = new Cell(food[0], food[1]);
            board.addFood(cell);
        }
        List<Move> moves = new ArrayList<>(Arrays.asList(R, D, R, U, L, U));
        for(Move move : moves){
            board.moveSnake(move);
        }
    }
}
