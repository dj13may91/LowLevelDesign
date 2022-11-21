package com.cards;

import lombok.Getter;

@Getter
public class Player {
    int name;
    Player nextPlayer;

    public Player(int name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player-" + name;
    }
}
