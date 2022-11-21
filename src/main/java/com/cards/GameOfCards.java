package com.cards;

public class GameOfCards {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting game");
        GameManager gameManager = GameManager.getInstance();
        gameManager.setupPlayers(4);
        for (int i = 0; i < 10; i++) {
            gameManager.play();
            Thread.sleep(1000);
        }
    }
}
