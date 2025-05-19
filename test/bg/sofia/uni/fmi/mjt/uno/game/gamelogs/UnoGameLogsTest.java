package bg.sofia.uni.fmi.mjt.uno.game.gamelogs;

import bg.sofia.uni.fmi.mjt.uno.game.card.Card;
import bg.sofia.uni.fmi.mjt.uno.game.gamelogs.spectator.Spectator;
import bg.sofia.uni.fmi.mjt.uno.game.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnoGameLogsTest {

    private UnoGameLogs gameLogs;
    private Spectator mockSpectator;
    private Player mockPlayer;
    private Card mockCard;

    @BeforeEach
    void setUp() {
        gameLogs = new UnoGameLogs();
        mockSpectator = mock(Spectator.class);
        mockPlayer = mock(Player.class);
        mockCard = mock(Card.class);
    }

    @Test
    void testAddAndRemoveSpectator() {
        assertDoesNotThrow(() -> gameLogs.addSpectator(mockSpectator), "AddSpectator should not throw");
        assertDoesNotThrow(() -> gameLogs.removeSpectator(mockSpectator), "AddSpectator should not throw");
    }

    @Test
    void testLogGameStart() {
        when(mockCard.toString()).thenReturn("Red 5");
        gameLogs.logGameStart(List.of(mockPlayer), mockCard, 0, 1);
        assertEquals("Red 5", gameLogs.getLastCard(), "getLastCard should give correct card");
    }

    @Test
    void testLogPlayerLeaving() {
        when(mockPlayer.getUsername()).thenReturn("Player1");
        assertDoesNotThrow(() -> gameLogs.logPlayerLeaving(mockPlayer, false),
            "LogPlayerLeaving should not throw");
    }

    @Test
    void testLogPlayerPlayCard() {
        when(mockCard.toString()).thenReturn("Blue 7");
        when(mockPlayer.getUsername()).thenReturn("Player1");
        gameLogs.logPlayerPlayCard(List.of(mockPlayer), mockCard, 0, 1, mockPlayer);
        assertEquals("Blue 7", gameLogs.getLastCard(), "getLastCard should give correct card");
    }

    @Test
    void testGetPlayedCards() {
        gameLogs.logPlayerPlayCard(List.of(mockPlayer), mockCard, 0, 1, mockPlayer);
        String playedCards = gameLogs.getPlayedCards();

        assertNotNull(playedCards);
    }

    @Test
    void testGetScoreboardWithPlayerWhoLeft() {
        when(mockPlayer.getUsername()).thenReturn("NAME");

        gameLogs.logPlayerLeaving(mockPlayer, false);
        String scoreboard = gameLogs.getScoreboard();

        assertTrue(scoreboard.contains("NAME"), "Scoreboard should contain player");
    }

    @Test
    void testGetScoreboardWithPlayerWhoWon() {
        when(mockPlayer.getUsername()).thenReturn("NAME");

        gameLogs.logPlayerLeaving(mockPlayer, true);
        String scoreboard = gameLogs.getScoreboard();

        assertTrue(scoreboard.contains("NAME"), "Scoreboard should contain player");
    }

    @Test
    void testGetGameSummary() {
        gameLogs.logGameStart(List.of(mockPlayer), mockCard, 0, 1);
        String summary = gameLogs.getGameSummary();

        assertNotNull(summary, "Summary should not be null!");
        assertTrue(summary.contains("Game has started!"), "LogGameStart should work correctly");
    }

    @Test
    void testLogPlayerDraw() {
        when(mockPlayer.getUsername()).thenReturn("NAME");

        gameLogs.logPlayerDraw(List.of(mockPlayer), 0, 1, 2, mockPlayer);
        String summary = gameLogs.getGameSummary();

        assertNotNull(summary, "Summary should not be null!");
        assertTrue(summary.contains("NAME drew 2 cards"), "LogPlayerDraw should work correctly");
    }

    @Test
    void testLogGameEnd() {
        gameLogs.logGameEnd();
        String summary = gameLogs.getGameSummary();

        assertNotNull(summary, "Summary should not be null!");
        assertTrue(summary.contains("Scoreboard:"), "LogGameEnd should work correctly");
    }

}
