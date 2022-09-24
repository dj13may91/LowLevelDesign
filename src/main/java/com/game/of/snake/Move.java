package com.game.of.snake;

public enum Move {
    U(-1, 0),
    D(1, 0),
    L(0, -1),
    R(0, 1);

    Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int x, y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
