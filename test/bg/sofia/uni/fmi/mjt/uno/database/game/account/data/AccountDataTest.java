package bg.sofia.uni.fmi.mjt.uno.database.game.account.data;

import bg.sofia.uni.fmi.mjt.uno.game.Game;
import bg.sofia.uni.fmi.mjt.uno.game.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountDataTest {

    private AccountData accountData;
    private Player mockPlayer;
    private Game mockCurrentGame;
    private Game mockCreatedGame;

    @BeforeEach
    void setUp() {
        accountData = new AccountData();
        mockPlayer = mock(Player.class);
        mockCurrentGame = mock(Game.class);
        mockCreatedGame = mock(Game.class);
    }

    @Test
    void testSetAndGetPlayer() {
        accountData.setPlayer(mockPlayer);
        assertEquals(mockPlayer, accountData.getPlayer(), "getPlayer() should return the correct player");
    }

    @Test
    void testSetAndGetCurrentGame() {
        accountData.setCurrentGame(mockCurrentGame);
        assertEquals(mockCurrentGame, accountData.getCurrentGame(), "getCurrentGame() should return the correct game");
    }

    @Test
    void testSetAndGetCreatedGame() {
        accountData.setCreatedGame(mockCreatedGame);
        assertEquals(mockCreatedGame, accountData.getCreatedGame(), "getCreatedGame() should return the correct game");
    }

}