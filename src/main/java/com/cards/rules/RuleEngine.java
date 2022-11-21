package com.cards.rules;


import com.cards.Card;
import com.cards.Player;

import java.util.Map;

public abstract class RuleEngine {
    private RuleEngine nextLink;

    public static RuleEngine linkRules(RuleEngine headRule, RuleEngine... ruleChain) {
        RuleEngine head = headRule;
        for (RuleEngine nextInChain : ruleChain) {
            head.nextLink = nextInChain;
            head = nextInChain;
        }
        return headRule;
    }

    /**
     * Subclasses will implement this method with concrete checks.
     */
    public abstract Player check(Map<Card, Player> hands);

    /**
     * Runs check on the next link in chain or ends check if we are at last link in chain.
     */
    protected Player checkNextRule(Map<Card, Player> hands) {
        if (nextLink == null) {
            return null;
        }
        return nextLink.check(hands);
    }
}
