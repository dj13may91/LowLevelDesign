package com.cards.rules;

import com.cards.Card;
import com.cards.GameManager;
import com.cards.Player;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HighCardRuleEngine extends RuleEngine {
    private final GameManager gameManager = GameManager.getInstance();

    @Override
    public Player check(Map<Card, Player> hands) {
        Card highest = null;
        Set<Card> eliminationCards = new HashSet<>(hands.keySet());
        for (Card c : eliminationCards) {
            if (highest == null) highest = c;
            else if (c.compareTo(highest) > 0) {
                hands.remove(highest);
                highest = c;
            } else if (c.compareTo(highest) < 0) {
                hands.remove(c);
            } // else don't remove cards of same rank for a tie breaker
        }


        if (hands.size() > 1) {
            return checkNextRule(hands);
        }
        System.out.println("winner by high-card : " + hands.get(highest));
        return hands.get(highest);
    }
}
