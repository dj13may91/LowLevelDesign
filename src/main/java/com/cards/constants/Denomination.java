package com.cards.constants;

import lombok.Getter;

@Getter
public enum Denomination {

    Card_2("2", 2),
    Card_3("3", 3), Card_4("4", 4),
    Card_5("5", 5), Card_6("6", 6),
    Card_7("7", 7), Card_8("8", 8),
    Card_9("9", 9), Card_10("10", 10),
    Card_J("J", 11), Card_Q("Q", 12),
    Card_K("K", 13), Card_A("A", 14);

    final String value;
    final int rank;

    Denomination(String value, int rank) {
        this.value = value;
        this.rank = rank;
    }
}
