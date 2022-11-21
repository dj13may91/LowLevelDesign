package com.cards.constants;

import lombok.Getter;

@Getter
public enum Suite {
    HEART('H', CardColor.RED),
    DIAMOND('D', CardColor.RED),
    SPADE('S', CardColor.BLACK),
    CLUB('C', CardColor.BLACK),
    ;

    private final char trump;
    private final CardColor color;

    Suite(char trump, CardColor color) {
        this.trump = trump;
        this.color = color;
    }
}
