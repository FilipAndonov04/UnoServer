package bg.sofia.uni.fmi.mjt.uno.game.deck;

import bg.sofia.uni.fmi.mjt.uno.game.card.Card;
import bg.sofia.uni.fmi.mjt.uno.game.card.Color;
import bg.sofia.uni.fmi.mjt.uno.game.card.Value;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public interface Deck {

    /**
     * Adds a card to the bottom of the deck
     *
     * @param card The card to be added
     * @throws IllegalArgumentException If the card is null
     */
    void addCard(Card card);

    /**
     * Draws a card from the top of the deck
     *
     * @return The drawn card, or null if the deck is empty
     */
    Card drawCard();

    /**
     * Returns whether there are cards in the deck
     *
     * @return True if the deck is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Randomly shuffles the deck
     */
    void shuffle();

    /**
     * Returns the cards in the deck
     *
     * @return List of the cards in the deck
     */
    List<Card> seeCards();

    static Deck getTheClassicalUnoDeck() {
        final int zeroCount = 1;
        final int colorCount = 2;
        final int wildCount = 4;

        Set<Value> zero = EnumSet.of(Value.ZERO);
        Set<Value> colorValues = EnumSet.range(Value.ONE, Value.PLUS_TWO);
        Set<Value> wildValues = EnumSet.range(Value.CHOOSE_COLOR, Value.PLUS_FOUR);

        Set<Color> fourColors = EnumSet.range(Color.RED, Color.BLUE);
        Set<Color> wildColor = EnumSet.of(Color.WILD);

        Deck deck = new UnoDeck();
        putInDeck(deck, fourColors, zero, zeroCount);
        putInDeck(deck, fourColors, colorValues, colorCount);
        putInDeck(deck, wildColor, wildValues, wildCount);
        return deck;
    }

    private static void putInDeck(Deck deck, Set<Color> colors, Set<Value> values, int count) {
        for (Color color : colors) {
            for (Value value : values) {
                for (int i = 0; i < count; i++) {
                    deck.addCard(new Card(color, value));
                }
            }
        }
    }

}
