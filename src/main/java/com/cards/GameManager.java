package com.cards;

import com.cards.rules.ColorTieBreakerRuleEngine;
import com.cards.rules.HighCardRuleEngine;
import com.cards.rules.RuleEngine;
import com.cards.rules.TrumpRuleManager;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class GameManager {
    private Deck deck = Deck.getInstance();
    private int playerCount = -1;
    private Player head, tail;
    private final Map<Integer, Player> playerMap = new HashMap<>();
    private RuleEngine ruleEngine;

    public static void main(String[] args) {
        new GameManager();
    }


    private static final GameManager gameManager = new GameManager();

    public static GameManager getInstance() {
        return gameManager;
    }

    private GameManager() {
    }

    public void setupPlayers(int playerCount) {
        this.playerCount = playerCount;
        for (int i = 1; i <= playerCount; i++) {
            addPlayer(i);
        }
        setupRules();
    }

    private void setupRules() {
        ruleEngine = RuleEngine.linkRules(
                new TrumpRuleManager(),
                new HighCardRuleEngine(),
                new ColorTieBreakerRuleEngine());
    }

    public void play() {
        if (this.playerCount == -1) throw new RuntimeException("Please setup players");
        deck.shuffleTrump();
        Map<Card, Player> currentServe = deck.serve(playerCount, head);
        if (currentServe == null) {
            return;
        }
        Player winner = ruleEngine.check(currentServe);
        if (winner != null) {
            head = winner;
        } else {
            System.out.println(" Game tied!!");
        }
    }


    // map <10,<p1 ,p3> || J, p2,p4 => p5 || p6

    private void addPlayer(int value) {
        Player newPlayer = new Player(value);

        if (head == null) {
            head = newPlayer;
        } else {
            tail.nextPlayer = newPlayer;
        }

        tail = newPlayer;
        tail.nextPlayer = head;
        playerMap.put(newPlayer.name, newPlayer);
    }
}

//        cards.Player temp = head;
//        do {
//            System.out.println(temp);
//            temp = temp.nextPlayer;
//        } while (temp != head);