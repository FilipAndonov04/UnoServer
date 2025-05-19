package bg.sofia.uni.fmi.mjt.uno.game;

import bg.sofia.uni.fmi.mjt.uno.game.deck.Deck;
import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.game.gamelogs.GameLogs;
import bg.sofia.uni.fmi.mjt.uno.game.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnoGameTest {

    private UnoGame game;
    private Deck unoDeck;
    private GameLogs mockGameLogs;

    @BeforeEach
    void setUp() throws GameException {
        unoDeck = Deck.getTheClassicalUnoDeck();
        mockGameLogs = Mockito.mock(GameLogs.class);
        game = new UnoGame(unoDeck, 0, mockGameLogs, 3);
    }

    @Test
    void testConstructorWithTooBigPlayerCount() {
        assertThrows(GameException.class, () -> new UnoGame(unoDeck, 0, mockGameLogs, Game.MAX_PLAYERS + 1),
            "Constructor should throw when given more maxPlayers that MAX_PLAYERS!");
    }

    @Test
    void testAddPlayerWithMorePlayersThatMaxCount() throws GameException {
        Player mockPlayer1 = Mockito.mock(Player.class);
        Player mockPlayer2 = Mockito.mock(Player.class);
        Player mockPlayer3 = Mockito.mock(Player.class);
        Player mockPlayer4 = Mockito.mock(Player.class);

        game.addPlayer(mockPlayer1);
        game.addPlayer(mockPlayer2);
        game.addPlayer(mockPlayer3);

        assertThrows(GameException.class, () -> game.addPlayer(mockPlayer4),
            "AddPlayer should throw when adding more players than capacity!");
        assertEquals(List.of(mockPlayer1, mockPlayer2, mockPlayer3), game.getPlayers(), "Get players should give players in game!");
    }

    @Test
    void testStartWithNoPlayers() {
        assertThrows(GameException.class, () -> game.start(),
            "Start should throw when starting with no players!");
    }

    @Test
    void testStartWithTooLittlePlayers() throws GameException {
        Player mockPlayer1 = Mockito.mock(Player.class);
        game.addPlayer(mockPlayer1);

        assertThrows(GameException.class, () -> game.start(),
            "Start should throw when starting with too little players!");
    }

    @Test
    void testStartWithValidPlayerCount() throws GameException {
        Player mockPlayer1 = Mockito.mock(Player.class);
        Player mockPlayer2 = Mockito.mock(Player.class);
        game.addPlayer(mockPlayer1);
        game.addPlayer(mockPlayer2);

        assertDoesNotThrow(() -> game.start(), "Start should not throw when starting valid number of players!");
    }

    @Test
    void testStartGameMoreThatOnce() throws GameException {
        Player mockPlayer1 = Mockito.mock(Player.class);
        Player mockPlayer2 = Mockito.mock(Player.class);
        game.addPlayer(mockPlayer1);
        game.addPlayer(mockPlayer2);

        assertEquals(GameStatus.AVAILABLE, game.getGameStatus(), "Game status should be available before starting!");
        assertDoesNotThrow(() -> game.start(), "Start should not throw when starting valid number of players!");
        assertEquals(GameStatus.STARTED, game.getGameStatus(), "Game status should be started when started!");
        assertThrows(GameException.class, () -> game.start(),"Game can be started only once!");
    }

    @Test
    void testGetId() {
        assertEquals(0, game.getId(), "GetId should return correct id!");
    }


    @Test
    void testGetGameLogs() {
        assertEquals(mockGameLogs, game.getGameLogs(), "GetGameLogs should return correct id!");
    }

    @Test
    void testPlayRegularCardWithPlayerNotInTheGame() {
        Player mockPlayer1 = Mockito.mock(Player.class);

        assertThrows(GameException.class, () -> game.playRegularCard(mockPlayer1, 0),
            "PlayRegularCard should throw when player is not in game!");
    }

    @Test
    void testLeaveWithPLayerNotInGame() {
        Player mockPlayer1 = Mockito.mock(Player.class);

        assertThrows(GameException.class, () -> game.leave(mockPlayer1),
            "Leave should throw when player is not in game!");
    }

    @Test
    void testLeaveWithPLayerInGame() throws GameException {
        Player mockPlayer1 = Mockito.mock(Player.class);
        game.addPlayer(mockPlayer1);

        assertDoesNotThrow(() -> game.leave(mockPlayer1),
            "Leave should not throw when player is in game and it is not started!");
    }

}
