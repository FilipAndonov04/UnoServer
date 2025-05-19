package bg.sofia.uni.fmi.mjt.uno.game.deck;

import bg.sofia.uni.fmi.mjt.uno.game.card.Card;
import bg.sofia.uni.fmi.mjt.uno.game.card.Color;
import bg.sofia.uni.fmi.mjt.uno.game.card.Value;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest {

    @Test
    void testGetTheClassicalUnoDeckHasTheRightNumberOfCards() {
        Deck deck = Deck.getTheClassicalUnoDeck();

        assertEquals(108, deck.seeCards().size(), "Classical UNO deck should have 108 cards!");
    }

    @Test
    void testGetTheClassicalUnoDeckHasTheRightCards() {
        Deck deck = Deck.getTheClassicalUnoDeck();
        Map<Card, Long> map = deck.seeCards().stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<Card, Long> ans = getClassicalDeck();
        assertEquals(ans, map, "Classical UNO deck should have the correct cards!");
    }

    Map<Card, Long> getClassicalDeck() {
        Map<Card, Long> map = new HashMap<>();

        map.put(new Card(Color.RED, Value.ZERO), 1L);
        map.put(new Card(Color.GREEN, Value.ZERO), 1L);
        map.put(new Card(Color.YELLOW, Value.ZERO), 1L);
        map.put(new Card(Color.BLUE, Value.ZERO), 1L);


        map.put(new Card(Color.RED, Value.ONE), 2L);
        map.put(new Card(Color.GREEN, Value.ONE), 2L);
        map.put(new Card(Color.YELLOW, Value.ONE), 2L);
        map.put(new Card(Color.BLUE, Value.ONE), 2L);
        map.put(new Card(Color.RED, Value.TWO), 2L);
        map.put(new Card(Color.GREEN, Value.TWO), 2L);
        map.put(new Card(Color.YELLOW, Value.TWO), 2L);
        map.put(new Card(Color.BLUE, Value.TWO), 2L);
        map.put(new Card(Color.RED, Value.THREE), 2L);
        map.put(new Card(Color.GREEN, Value.THREE), 2L);
        map.put(new Card(Color.YELLOW, Value.THREE), 2L);
        map.put(new Card(Color.BLUE, Value.THREE), 2L);
        map.put(new Card(Color.RED, Value.FOUR), 2L);
        map.put(new Card(Color.GREEN, Value.FOUR), 2L);
        map.put(new Card(Color.YELLOW, Value.FOUR), 2L);
        map.put(new Card(Color.BLUE, Value.FOUR), 2L);
        map.put(new Card(Color.RED, Value.FIVE), 2L);
        map.put(new Card(Color.GREEN, Value.FIVE), 2L);
        map.put(new Card(Color.YELLOW, Value.FIVE), 2L);
        map.put(new Card(Color.BLUE, Value.FIVE), 2L);
        map.put(new Card(Color.RED, Value.SIX), 2L);
        map.put(new Card(Color.GREEN, Value.SIX), 2L);
        map.put(new Card(Color.YELLOW, Value.SIX), 2L);
        map.put(new Card(Color.BLUE, Value.SIX), 2L);
        map.put(new Card(Color.RED, Value.SEVEN), 2L);
        map.put(new Card(Color.GREEN, Value.SEVEN), 2L);
        map.put(new Card(Color.YELLOW, Value.SEVEN), 2L);
        map.put(new Card(Color.BLUE, Value.SEVEN), 2L);
        map.put(new Card(Color.RED, Value.EIGHT), 2L);
        map.put(new Card(Color.GREEN, Value.EIGHT), 2L);
        map.put(new Card(Color.YELLOW, Value.EIGHT), 2L);
        map.put(new Card(Color.BLUE, Value.EIGHT), 2L);
        map.put(new Card(Color.RED, Value.NINE), 2L);
        map.put(new Card(Color.GREEN, Value.NINE), 2L);
        map.put(new Card(Color.YELLOW, Value.NINE), 2L);
        map.put(new Card(Color.BLUE, Value.NINE), 2L);

        map.put(new Card(Color.RED, Value.PLUS_TWO), 2L);
        map.put(new Card(Color.GREEN, Value.PLUS_TWO), 2L);
        map.put(new Card(Color.YELLOW, Value.PLUS_TWO), 2L);
        map.put(new Card(Color.BLUE, Value.PLUS_TWO), 2L);
        map.put(new Card(Color.RED, Value.SKIP), 2L);
        map.put(new Card(Color.GREEN, Value.SKIP), 2L);
        map.put(new Card(Color.YELLOW, Value.SKIP), 2L);
        map.put(new Card(Color.BLUE, Value.SKIP), 2L);
        map.put(new Card(Color.RED, Value.REVERSE), 2L);
        map.put(new Card(Color.GREEN, Value.REVERSE), 2L);
        map.put(new Card(Color.YELLOW, Value.REVERSE), 2L);
        map.put(new Card(Color.BLUE, Value.REVERSE), 2L);

        map.put(new Card(Color.WILD, Value.CHOOSE_COLOR), 4L);
        map.put(new Card(Color.WILD, Value.PLUS_FOUR), 4L);

        return map;
    }

}
