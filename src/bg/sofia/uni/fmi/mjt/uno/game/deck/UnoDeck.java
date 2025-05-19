package bg.sofia.uni.fmi.mjt.uno.game.deck;

import bg.sofia.uni.fmi.mjt.uno.game.card.Card;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class UnoDeck implements Deck {

    private final Queue<Card> cards = new ArrayDeque<>();

    @Override
    public void addCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Card cannot be null!");
        }

        cards.offer(card);
    }

    @Override
    public Card drawCard() {
        return cards.poll();
    }

    @Override
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    @Override
    public void shuffle() {
        List<Card> cardList = new ArrayList<>(cards);
        Collections.shuffle(cardList);
        cards.clear();
        cards.addAll(cardList);
    }

    @Override
    public List<Card> seeCards() {
        return new ArrayList<>(cards);
    }
    
}
