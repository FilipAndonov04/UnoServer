package bg.sofia.uni.fmi.mjt.uno.game.deck;

import bg.sofia.uni.fmi.mjt.uno.game.card.Card;
import bg.sofia.uni.fmi.mjt.uno.game.card.Color;
import bg.sofia.uni.fmi.mjt.uno.game.card.Value;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnoDeckTest {

    @Test
    void testAddCardWithNullCard() {
        UnoDeck deck = new UnoDeck();

        assertThrows(IllegalArgumentException.class, () -> deck.addCard(null),
            "Add card should throw IllegalArgumentException when called with null card!");
    }

    @Test
    void testAddCardWithEmptyDeckToNotBeEmpty() {
        UnoDeck deck = new UnoDeck();
        Card card = new Card(Color.RED, Value.ONE);
        deck.addCard(card);

        assertFalse(deck.seeCards().isEmpty(), "Deck should not be empty when adding a card to it!");
    }

    @Test
    void testAddCardWithOneCard() {
        UnoDeck deck = new UnoDeck();
        Card card = new Card(Color.RED, Value.ONE);
        deck.addCard(card);

        Card ans = new Card(Color.RED, Value.ONE);
        assertEquals(ans, deck.seeCards().getLast(), "Add card should add a card to the back of the deck!");
    }

    @Test
    void testAddCardWithThreeCard() {
        UnoDeck deck = new UnoDeck();
        deck.addCard(new Card(Color.RED, Value.ONE));
        deck.addCard(new Card(Color.BLUE, Value.TWO));
        deck.addCard(new Card(Color.RED, Value.THREE));

        List<Card> ans = List.of(new Card(Color.RED, Value.ONE), new Card(Color.BLUE, Value.TWO),
            new Card(Color.RED, Value.THREE));
        assertEquals(ans, deck.seeCards(), "The deck should have cards in correct order!");
    }

    @Test
    void testDrawCardWithEmptyDeck() {
        UnoDeck deck = new UnoDeck();

        assertNull(deck.drawCard(), "Draw card should return null then the deck is empty!");
    }

    @Test
    void testDrawCardDrawsFirstCard() {
        UnoDeck deck = new UnoDeck();
        deck.addCard(new Card(Color.RED, Value.ONE));
        deck.addCard(new Card(Color.BLUE, Value.TWO));
        deck.addCard(new Card(Color.RED, Value.THREE));

        assertEquals(new Card(Color.RED, Value.ONE), deck.drawCard(), "Draw card should draw the first card!");
    }

    @Test
    void testDrawCardWithThreeCards() {
        UnoDeck deck = new UnoDeck();
        deck.addCard(new Card(Color.RED, Value.ONE));
        deck.addCard(new Card(Color.BLUE, Value.TWO));
        deck.addCard(new Card(Color.RED, Value.THREE));
        deck.drawCard();

        List<Card> ans = List.of(new Card(Color.BLUE, Value.TWO), new Card(Color.RED, Value.THREE));
        assertEquals(ans, deck.seeCards(), "The deck should have cards in correct order!");
    }

    @Test
    void testShuffleHasTheSameCards() {
        UnoDeck deck = new UnoDeck();
        deck.addCard(new Card(Color.RED, Value.ONE));
        deck.addCard(new Card(Color.BLUE, Value.TWO));
        deck.addCard(new Card(Color.RED, Value.THREE));
        deck.shuffle();

        Set<Card> ans =
            Set.of(new Card(Color.RED, Value.ONE), new Card(Color.BLUE, Value.TWO), new Card(Color.RED, Value.THREE));
        assertEquals(ans, new HashSet<>(deck.seeCards()),
            "Shuffle should not change the cards in the deck, only their order!");
    }

    @Test
    void testIsEmptyWithConstructedDeck() {
        UnoDeck deck = new UnoDeck();

        assertTrue(deck.isEmpty(), "Deck should be empty when is constructed!");
    }

    @Test
    void testIsEmptyWithOneCard() {
        UnoDeck deck = new UnoDeck();
        deck.addCard(Mockito.mock(Card.class));

        assertFalse(deck.isEmpty(), "Deck should not be empty when to it has just been added a card!");
    }

    @Test
    void testIsEmptyWithEmptyDeck() {
        UnoDeck deck = new UnoDeck();
        deck.addCard(Mockito.mock(Card.class));
        deck.drawCard();

        assertTrue(deck.isEmpty(), "Deck should be empty when adding a card and then drawing it!");
    }

}
