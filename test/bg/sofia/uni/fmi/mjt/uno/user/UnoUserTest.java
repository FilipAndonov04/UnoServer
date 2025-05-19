package bg.sofia.uni.fmi.mjt.uno.user;

import bg.sofia.uni.fmi.mjt.uno.database.account.Database;
import bg.sofia.uni.fmi.mjt.uno.database.account.Account;
import bg.sofia.uni.fmi.mjt.uno.database.account.exception.UsernameTakenException;
import bg.sofia.uni.fmi.mjt.uno.database.game.GameDatabase;
import bg.sofia.uni.fmi.mjt.uno.database.game.account.AccountDataDatabase;
import bg.sofia.uni.fmi.mjt.uno.database.game.account.data.AccountData;
import bg.sofia.uni.fmi.mjt.uno.game.Game;
import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.game.gamelogs.spectator.Spectator;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnoUserTest {

    private Database mockDatabase;
    private GameDatabase mockGameDatabase;
    private Spectator mockSpectator;
    private AccountDataDatabase mockAccountDataDatabase;
    private UnoUser unoUser;
    private Account testAccount;

    @BeforeEach
    void setUp() {
        mockDatabase = mock(Database.class);
        mockGameDatabase = mock(GameDatabase.class);
        mockSpectator = mock(Spectator.class);
        mockAccountDataDatabase = mock(AccountDataDatabase.class);
        unoUser = new UnoUser(mockDatabase, mockGameDatabase, mockSpectator, mockAccountDataDatabase);
        testAccount = new Account("testUser", "testPass");

        when(mockDatabase.containsAccount(any())).thenReturn(true);
        when(mockDatabase.containsAccount(any(Account.class))).thenReturn(true);
        when(mockAccountDataDatabase.get(any(Account.class))).thenReturn(new AccountData());
    }

    @Test
    void testRegisterSuccessful() throws InvalidUserOperation, UsernameTakenException {
        assertDoesNotThrow(() -> unoUser.register("testUser", "testPass"));
        verify(mockDatabase).addAccount(any(Account.class));
    }

    @Test
    void testRegisterWithTakenUsernameThrowsException() throws UsernameTakenException {
        doThrow(new UsernameTakenException("Username taken!"))
            .when(mockDatabase).addAccount(any(Account.class));
        assertThrows(InvalidUserOperation.class, () -> unoUser.register("testUser", "testPass"));
    }

    @Test
    void testLoginSuccessful() throws InvalidUserOperation {
        when(mockDatabase.containsAccount(any(Account.class))).thenReturn(true);
        when(mockAccountDataDatabase.get(any(Account.class))).thenReturn(new AccountData());
        assertDoesNotThrow(() -> unoUser.login("testUser", "testPass"));
    }

    @Test
    void testLoginWithWrongPasswordThrowsException() {
        when(mockDatabase.isFreeUsername("testUser")).thenReturn(false);
        when(mockDatabase.containsAccount(any(Account.class))).thenReturn(false);
        assertThrows(InvalidUserOperation.class, () -> unoUser.login("testUser", "wrongPass"));
    }

    @Test
    void testLogoutSuccessful() throws InvalidUserOperation {
        when(mockDatabase.containsAccount(any(Account.class))).thenReturn(true);
        when(mockAccountDataDatabase.get(any(Account.class))).thenReturn(new AccountData());
        unoUser.login("testUser", "testPass");
        assertDoesNotThrow(() -> unoUser.logout());
    }

    @Test
    void testCreateGameSuccessful() throws InvalidUserOperation, GameException {
        when(mockAccountDataDatabase.get(any(Account.class))).thenReturn(new AccountData());
        when(mockGameDatabase.isFreeId(anyInt())).thenReturn(true);
        when(mockDatabase.containsAccount(any())).thenReturn(true);
        unoUser.login("testUser", "testPass");
        assertDoesNotThrow(() -> unoUser.createGame(4, 1));
        verify(mockGameDatabase).addGame(any(Game.class));
    }

    @Test
    void testDrawCardThrowsExceptionIfNotInGame() throws InvalidUserOperation {
        when(mockAccountDataDatabase.get(any(Account.class))).thenReturn(new AccountData());
        when(mockDatabase.containsAccount(any())).thenReturn(true);
        unoUser.login("testUser", "testPass");
        assertThrows(InvalidUserOperation.class, () -> unoUser.drawCard());
    }

    @Test
    void testRegisterWithValidUsername() throws InvalidUserOperation, UsernameTakenException {
        String username = "testUser";
        String password = "password123";

        unoUser.register(username, password);

        verify(mockDatabase).addAccount(any(Account.class));
    }

    @Test
    void testRegisterWithUsernameTaken() throws UsernameTakenException {
        String username = "testUser";
        String password = "password123";
        doThrow(new UsernameTakenException("")).when(mockDatabase).addAccount(any(Account.class));

        assertThrows(InvalidUserOperation.class, () -> unoUser.register(username, password));
    }

    @Test
    void testLoginWithValidCredentials() throws InvalidUserOperation {
        String username = "testUser";
        String password = "password123";
        Account accountMock = mock(Account.class);
        when(mockDatabase.containsAccount(any(Account.class))).thenReturn(true);

        unoUser.login(username, password);

        assertTrue(unoUser.isLoggedIn());
    }

    @Test
    void testLoginWithInvalidUsername() {
        String username = "invalidUser";
        String password = "password123";

        when(mockDatabase.containsAccount(any(Account.class))).thenReturn(false);

        assertThrows(InvalidUserOperation.class, () -> unoUser.login(username, password));
    }

    @Test
    void testLogoutWhenLoggedIn() throws InvalidUserOperation {
        unoUser.login("testUser", "password123");
        assertDoesNotThrow(() -> unoUser.logout(), "Logout should not throw when u are logged in!");
    }

    @Test
    void testLogoutWhenNotLoggedIn() {
        assertThrows(InvalidUserOperation.class, unoUser::logout);
    }

    @Test
    void testCreateGameWithValidInputs() throws InvalidUserOperation, GameException {
        unoUser.login("testUser", "password123");

        int gameId = 123;
        int playersCount = 4;

        when(mockGameDatabase.isFreeId(gameId)).thenReturn(true);

        unoUser.createGame(playersCount, gameId);

        verify(mockGameDatabase).addGame(any(Game.class));
    }

    @Test
    void testJoinGameWithInvalidGameId() throws InvalidUserOperation {
        unoUser.login("testUser", "password123");

        int gameId = 123;

        when(mockGameDatabase.getGame(gameId)).thenReturn(null);

        assertThrows(InvalidUserOperation.class, () -> unoUser.joinGame(gameId, "testPlayer"));
    }

    @Test
    void testStartGame() throws InvalidUserOperation, GameException {
        unoUser.login("testUser", "password123");

        Game gameMock = mock(Game.class);
        when(mockAccountDataDatabase.get(any(Account.class))).thenReturn(mock(AccountData.class));
        when(mockGameDatabase.getGame(123)).thenReturn(gameMock);

        assertThrows(InvalidUserOperation.class, () -> unoUser.startGame(),
            "AcceptEffect should throw when user did not create not in game!");
    }

    @Test
    void testStartGameWithoutCreatedGame() throws InvalidUserOperation {
        unoUser.login("testUser", "password123");

        assertThrows(InvalidUserOperation.class, () -> unoUser.startGame());
    }

    @Test
    void testShowHand() throws InvalidUserOperation {
        unoUser.login("testUser", "password123");

        when(mockAccountDataDatabase.get(any(Account.class))).thenReturn(mock(AccountData.class));

        assertThrows(InvalidUserOperation.class, () -> unoUser.showHand(),
            "ShowHand should throw when user is not in game!");
    }

    @Test
    void testShowLastCard() throws InvalidUserOperation, GameException {
        unoUser.login("testUser", "password123");

        when(mockAccountDataDatabase.get(any(Account.class))).thenReturn(mock(AccountData.class));

        assertThrows(InvalidUserOperation.class, () -> unoUser.showLastCard(),
            "ShowLastCard should throw when user is not in game!");
    }

    @Test
    void testAcceptEffect() throws InvalidUserOperation, GameException {
        unoUser.login("testUser", "password123");

        when(mockAccountDataDatabase.get(any(Account.class))).thenReturn(mock(AccountData.class));

        assertThrows(InvalidUserOperation.class, () -> unoUser.acceptEffect(),
            "AcceptEffect should throw when user is not in game!");
    }

    @Test
    void testDrawCard() throws InvalidUserOperation, GameException {
        unoUser.login("testUser", "password123");

        when(mockAccountDataDatabase.get(any(Account.class))).thenReturn(mock(AccountData.class));

        assertThrows(InvalidUserOperation.class, () -> unoUser.drawCard(),
            "DrawCard should throw when user is not in game!");
    }

    @Test
    void testLeaveGame() throws InvalidUserOperation, GameException {
        unoUser.login("testUser", "password123");

        when(mockAccountDataDatabase.get(any(Account.class))).thenReturn(mock(AccountData.class));

        assertThrows(InvalidUserOperation.class, () -> unoUser.leaveGame(),
            "LeaveGame should throw when user is not in game!");
    }

    @Test
    void testSpectateGame() throws InvalidUserOperation {
        unoUser.login("testUser", "password123");

        when(mockAccountDataDatabase.get(any(Account.class))).thenReturn(mock(AccountData.class));

        assertThrows(InvalidUserOperation.class, () -> unoUser.spectateGame(),
            "SpectateGame should throw when user is not in game!");
    }

}
