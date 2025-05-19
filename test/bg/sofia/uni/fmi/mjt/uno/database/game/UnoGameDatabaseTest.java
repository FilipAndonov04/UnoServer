package bg.sofia.uni.fmi.mjt.uno.database.game;

import bg.sofia.uni.fmi.mjt.uno.game.Game;
import bg.sofia.uni.fmi.mjt.uno.game.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UnoGameDatabaseTest {

    private UnoGameDatabase database;
    private Game mockGame;

    @BeforeEach
    public void setUp() {
        database = new UnoGameDatabase();
        mockGame = mock(Game.class);
        when(mockGame.getId()).thenReturn(1);
        when(mockGame.getGameStatus()).thenReturn(GameStatus.AVAILABLE);
    }

    @Test
    public void testAddGameAdding() {
        database.addGame(mockGame);
        Collection<Game> availableGames = database.getGames(GameStatus.AVAILABLE);
        assertTrue(availableGames.contains(mockGame), "Add game should add games correctly!");
    }

    @Test
    public void testAddGameAddsTheSameGame() {
        database.addGame(mockGame);
        assertEquals(mockGame, database.getGame(1), "Add game should add games correctly!");
    }

    @Test
    public void testGetGamesWithSameIsNotEmpty() {
        database.addGame(mockGame);
        Collection<Game> games = database.getGames(GameStatus.AVAILABLE);
        assertFalse(games.isEmpty(), "Get games should not return empty set!");
    }

    @Test
    public void testGetGamesWithDifferentIsEmpty() {
        database.addGame(mockGame);
        Collection<Game> games = database.getGames(GameStatus.ENDED);
        assertEquals(Set.of(), games, "Get games should return empty set!");
    }

    @Test
    public void testGetGamesWithNullStatus() {
        Game mockGame2 = mock(Game.class);
        when(mockGame2.getId()).thenReturn(12);
        when(mockGame2.getGameStatus()).thenReturn(GameStatus.ENDED);
        database.addGame(mockGame);
        database.addGame(mockGame2);

        assertEquals(Set.of(mockGame, mockGame2), new HashSet<>(database.getGames(null)),
            "Get games should return all games when called with null!");
    }

    @Test
    public void testGetAllGames() {
        Game mockGame2 = mock(Game.class);
        when(mockGame2.getId()).thenReturn(12);
        when(mockGame2.getGameStatus()).thenReturn(GameStatus.ENDED);
        database.addGame(mockGame);
        database.addGame(mockGame2);

        assertEquals(Set.of(mockGame, mockGame2), new HashSet<>(database.getAllGames()),
            "Get all games should return all games!");
    }

    @Test
    public void testIsFreeId() {
        assertTrue(database.isFreeId(1),
            "IsFreeId should return true when there is no game with index 1!");
        database.addGame(mockGame);
        assertFalse(database.isFreeId(1),
            "IsFreeId should return true when there is a game with index 1!");
    }

    @Test
    public void testGetGameById() {
        assertNull(database.getGame(1),
            "GetGameById should return null when there is no game with that index!");
        database.addGame(mockGame);
        assertEquals(mockGame, database.getGame(1),
            "GetGameById should return the game with index 1!");
    }

}
