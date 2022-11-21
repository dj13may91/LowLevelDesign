package com.cards.rules;


import com.cards.Card;
import com.cards.GameManager;
import com.cards.Player;
import com.cards.constants.Suite;

import java.util.Map;

public class ColorTieBreakerRuleEngine extends RuleEngine {
    private final GameManager gameManager = GameManager.getInstance();

    @Override
    public Player check(Map<Card, Player> hands) {
        Suite trump = gameManager.getDeck().getTrump();
        for (Card c : hands.keySet()) {
            if (c.getSuite().getColor().equals(trump.getColor())) {
                System.out.println("winner by color : " + hands.get(c));
                return hands.get(c);
            }
        }
        return checkNextRule(hands);
    }
}
