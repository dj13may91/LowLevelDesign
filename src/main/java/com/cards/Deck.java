package com.cards;

import com.cards.constants.Denomination;
import com.cards.constants.Suite;
import lombok.Getter;

import java.util.*;

@Getter
public class Deck {

    private final Stack<Card> cardStack = new Stack<>();
    private final List<Map<Card, Player>> handsPlayed = new ArrayList<>();
    private Suite trump = Suite.SPADE;
    private final Random random = new Random();

    private final static Deck deck = new Deck();

    public static Deck getInstance() {
        return deck;
    }

    private Deck() {
        createDeck();
    }

    private void createDeck() {
        for (Suite suite : Suite.values()) {
            for (Denomination den : Denomination.values()) {
                Card card = new Card(suite, den);
                cardStack.add(card);
            }
        }
        Collections.shuffle(cardStack);
        System.out.println("Stack: " + cardStack);
    }

    public void resetDeck() {
        createDeck();
        handsPlayed.clear();
        cardStack.clear();
        createDeck();
    }

    public Map<Card, Player> serve(int playerCount, Player first) {
        if (playerCount > cardStack.size()) {
            System.out.println("Game has ended, no more cards left for " + playerCount + " players!!");
            return null;
        }
        Map<Card, Player> currentServe = new HashMap<>();
        Player curr = first;
        System.out.print("currentServe: ");
        for (int i = 0; i < playerCount; i++) {
            System.out.print(curr + ":" + cardStack.peek() + "  ");
            currentServe.put(cardStack.pop(), curr);
            curr = curr.getNextPlayer();
        }
        System.out.println();
        handsPlayed.add(currentServe);
        return currentServe;
    }

    public void shuffleTrump() {
        int index = random.nextInt(Suite.values().length);
        this.trump = Suite.values()[index];
        System.out.println("Setting trump to " + trump);
    }
}
