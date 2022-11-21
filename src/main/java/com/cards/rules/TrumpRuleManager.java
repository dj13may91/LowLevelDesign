package com.cards.rules;

import com.cards.Card;
import com.cards.GameManager;
import com.cards.Player;

import java.util.Map;
import java.util.Set;

public class TrumpRuleManager extends RuleEngine {
    private final GameManager gameManager = GameManager.getInstance();

    @Override
    public Player check(Map<Card, Player> hands) {
        Card winner = getTrumpIndexes(hands.keySet());
        if (winner != null) {
            System.out.println("winner by trump : " + hands.get(winner));
            return hands.get(winner);
        }
        return checkNextRule(hands);
    }

    private Card getTrumpIndexes(Set<Card> serve) {
        Card winner = null;
        for (Card c : serve) {
            if (c.isTrump(gameManager.getDeck().getTrump())) {
                if (winner == null) {
                    winner = c;
                } else if (c.compareTo(winner) > 0)
                    winner = c;
            }
        }
        return winner;
    }


}
