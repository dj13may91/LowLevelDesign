package com.cards;

import com.cards.constants.Denomination;
import com.cards.constants.Suite;
import lombok.Getter;

@Getter
public class Card implements Comparable<Card> {
    private final Suite suite;
    private final Denomination denomination;

    public Card(Suite suite, Denomination denomination) {
        this.suite = suite;
        this.denomination = denomination;
    }

    @Override
    public String toString() {
        return suite.getTrump() + denomination.getValue();
    }

    @Override
    public int compareTo(Card other) {
        return this.getDenomination().getRank() - other.getDenomination().getRank();
    }

    public boolean isTrump(Suite trump){
        return suite.equals(trump);
    }
}
