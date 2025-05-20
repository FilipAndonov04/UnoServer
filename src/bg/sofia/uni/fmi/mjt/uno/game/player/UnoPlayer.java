package bg.sofia.uni.fmi.mjt.uno.game.player;

import bg.sofia.uni.fmi.mjt.uno.game.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UnoPlayer implements Player {

    private String username;
    private final List<Card> hand = new ArrayList<>();

    public UnoPlayer(String username) {
        setUsername(username);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null nor blank!");
        }

        this.username = username;
    }

    @Override
    public void takeCard(Card card) {
        assertCard(card);

        hand.add(card);
    }

    @Override
    public Card playCard(int index) {
        assertIndex(index);

        return hand.remove(index);
    }

    @Override
    public boolean isHandEmpty() {
        return hand.isEmpty();
    }

    @Override
    public int getCardsInHandCount() {
        return hand.size();
    }

    @Override
    public List<Card> getHand() {
        return Collections.unmodifiableList(hand);
    }

    @Override
    public Set<Integer> getPlayableCardsIndexes(Card card) {
        assertCard(card);

        Set<Integer> playableCards = new HashSet<>();
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).canBePlayedAfter(card)) {
                playableCards.add(i);
            }
        }
        return playableCards;
    }

    @Override
    public String getHandToString() {
        StringBuilder builder = new StringBuilder();
        for (Card card : hand) {
            builder.append(card).append(' ');
        }
        return builder.toString();
    }

    private void assertIndex(int index) {
        if (index < 0 || index >= hand.size()) {
            throw new IllegalArgumentException("There is no card at that index in the hand!");
        }
    }

    private void assertCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Card cannot be null!");
        }
    }

}
