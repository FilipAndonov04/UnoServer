package bg.sofia.uni.fmi.mjt.uno.game.player;

import bg.sofia.uni.fmi.mjt.uno.game.card.Card;
import bg.sofia.uni.fmi.mjt.uno.game.card.Color;
import bg.sofia.uni.fmi.mjt.uno.game.card.Value;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class UnoPlayerTest {

    @Test
    void testUnoPlayerConstructorWithNullUsername() {
        assertThrows(IllegalArgumentException.class, () -> new UnoPlayer(null),
            "Constructor should throw IllegalArgumentException when given a null username!");
    }

    @Test
    void testUnoPlayerConstructorWithBlankUsername() {
        assertThrows(IllegalArgumentException.class, () -> new UnoPlayer(""),
            "Constructor should throw IllegalArgumentException when given a blank username!");
    }

    @Test
    void testUnoPlayerConstructorWithValidUsername() {
        assertDoesNotThrow(() -> new UnoPlayer("name"),
            "Constructor should not throw when given a valid username!");
    }

    @Test
    void testIsHandEmptyWhenConstructed() {
        assertTrue(new UnoPlayer("name").isHandEmpty(),
            "Players hand should be empty when constructed!");
    }

    @Test
    void testIsHandEmptyWhenAddingACard() {
        UnoPlayer player = new UnoPlayer("name");
        player.takeCard(Mockito.mock(Card.class));

        assertFalse(player.isHandEmpty(), "Player's hand should not be empty when adding a card to it!");
    }

    @Test
    void testTakeCardWithNullCard() {
        UnoPlayer player = new UnoPlayer("name");

        assertThrows(IllegalArgumentException.class, () -> player.takeCard(null),
            "Take card should throw IllegalArgumentException when called with null card!");
    }

    @Test
    void testTakeCardWithOneCard() {
        UnoPlayer player = new UnoPlayer("name");
        player.takeCard(new Card(Color.RED, Value.ONE));

        assertEquals(new Card(Color.RED, Value.ONE), player.getHand().getFirst(),
            "Take card should add the card the player's hand correctly!");
    }

    @Test
    void testTakeCardWithThreeCards() {
        UnoPlayer player = new UnoPlayer("name");
        player.takeCard(new Card(Color.RED, Value.ONE));
        player.takeCard(new Card(Color.RED, Value.ONE));
        player.takeCard(new Card(Color.BLUE, Value.TWO));

        List<Card> ans = List.of(new Card(Color.RED, Value.ONE), new Card(Color.RED, Value.ONE),
            new Card(Color.BLUE, Value.TWO));
        assertEquals(ans, player.getHand(),
            "Player should have the cards he has taken in order!");
    }

    @Test
    void testPlayCardWithNegativeIndex() {
        UnoPlayer player = new UnoPlayer("name");

        assertThrows(IllegalArgumentException.class, () -> player.playCard(-1),
            "Play card should throw IllegalArgumentException when called with negative index!");
    }

    @Test
    void testPlayCardWithTooBigIndex() {
        UnoPlayer player = new UnoPlayer("name");

        assertThrows(IllegalArgumentException.class, () -> player.playCard(1000),
            "Play card should throw IllegalArgumentException when called out of bound index!");
    }

    @Test
    void testPlayCardWithIndexEqualToCardCount() {
        UnoPlayer player = new UnoPlayer("name");
        for (int i = 0; i < 9090; i++) {
            player.takeCard(Mockito.mock(Card.class));
        }

        assertThrows(IllegalArgumentException.class, () -> player.playCard(player.getCardsInHandCount()),
            "Play card should throw IllegalArgumentException when called out of bound index!");
    }

    @Test
    void testPlayCardWithValidIndexZero() {
        UnoPlayer player = new UnoPlayer("name");
        player.takeCard(new Card(Color.RED, Value.ONE));
        player.takeCard(new Card(Color.GREEN, Value.ONE));
        player.takeCard(new Card(Color.BLUE, Value.TWO));

        assertEquals(new Card(Color.RED, Value.ONE), player.playCard(0),
            "Play card should play the card with the given index!");
    }

    @Test
    void testPlayCardWithValidIndexOne() {
        UnoPlayer player = new UnoPlayer("name");
        player.takeCard(new Card(Color.RED, Value.ONE));
        player.takeCard(new Card(Color.GREEN, Value.ONE));
        player.takeCard(new Card(Color.BLUE, Value.TWO));

        assertEquals(new Card(Color.GREEN, Value.ONE), player.playCard(1),
            "Play card should play the card with the given index!");
    }

    @Test
    void testGetPlayableCardsIndexesWithNullCard() {
        UnoPlayer player = new UnoPlayer("name");

        assertThrows(IllegalArgumentException.class, () -> player.getPlayableCardsIndexes(null),
            "Get playable cards indexes should throw IllegalArgumentException when called with null card!");
    }

    @Test
    void testGetPlayableCardsIndexesWithNonMatchingCard() {
        UnoPlayer player = new UnoPlayer("name");
        player.takeCard(new Card(Color.RED, Value.ONE));
        player.takeCard(new Card(Color.GREEN, Value.ONE));
        player.takeCard(new Card(Color.BLUE, Value.TWO));

        assertEquals(Set.of(), player.getPlayableCardsIndexes(new Card(Color.YELLOW, Value.NINE)),
            "Get playable cards indexes should return an empty set when called with non matching card!");
    }

    @Test
    void testGetPlayableCardsIndexesWithNonMatchingCardButWildCardInHand() {
        UnoPlayer player = new UnoPlayer("name");
        player.takeCard(new Card(Color.RED, Value.ONE));
        player.takeCard(new Card(Color.GREEN, Value.ONE));
        player.takeCard(new Card(Color.WILD, Value.TWO));

        assertEquals(Set.of(2), player.getPlayableCardsIndexes(new Card(Color.YELLOW, Value.NINE)),
            "Get playable cards indexes should return the indexes of all the wild cards!");
    }

    @Test
    void testGetPlayableCardsIndexesWithTwoMatchingCard() {
        UnoPlayer player = new UnoPlayer("name");
        player.takeCard(new Card(Color.RED, Value.ONE));
        player.takeCard(new Card(Color.GREEN, Value.REVERSE));
        player.takeCard(new Card(Color.YELLOW, Value.TWO));

        assertEquals(Set.of(2, 1), player.getPlayableCardsIndexes(new Card(Color.YELLOW, Value.REVERSE)),
            "Get playable cards indexes should return the indexes of all the cards that have matching color or value!");
    }

    @Test
    void testGetHandToString() {
        UnoPlayer player = new UnoPlayer("name");

        Card card1 = Mockito.mock(Card.class);
        Card card2 = Mockito.mock(Card.class);
        when(card1.toString()).thenReturn("One");
        when(card2.toString()).thenReturn("Two");

        player.takeCard(card1);
        player.takeCard(card2);

        assertEquals("One Two ", player.getHandToString(),
            "Get hand to string should convert correctly!");
    }

}
