package bg.sofia.uni.fmi.mjt.uno.user.command;

import static org.mockito.Mockito.*;

import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserInput;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class CreateGameCommandTest {

    private User user;
    private CreateGameCommand command;

    @BeforeEach
    void setUp() {
        user = mock(User.class);  // Mock the User class
    }

    @Test
    void testCommandText() {
        assertEquals("create-game", CreateGameCommand.COMMAND_TEXT);
    }

    @Test
    void testGameIdOptionText() {
        assertEquals("--game-id=", CreateGameCommand.GAME_ID_OPTION_TEXT);
    }

    @Test
    void testPlayerCountOptionText() {
        assertEquals("--number-of-players=", CreateGameCommand.PLAYER_COUNT_OPTION_TEXT);
    }

    @Test
    void testCommandDescription() {
        String expectedDescription = "create-game --game-id=<game-id> --number-of-players=<number> (creates a game; must have unique id; when not given number of players, it is 2 by default)";
        assertEquals(expectedDescription, CreateGameCommand.COMMAND_DESCRIPTION);
    }

    @Test
    void testExecuteCreatesGame() throws InvalidUserOperation, GameException {
        int gameId = 123;
        int playerCount = 4;

        command = new CreateGameCommand(user, gameId, playerCount);
        doNothing().when(user).createGame(playerCount, gameId);  // Mock createGame method

        command.execute();  // Execute the command

        verify(user).createGame(playerCount, gameId);  // Verify that createGame was called
    }

    @Test
    void testExecuteThrowsInvalidUserOperation() throws InvalidUserOperation, GameException {
        int gameId = 123;
        int playerCount = 4;

        command = new CreateGameCommand(user, gameId, playerCount);
        doThrow(InvalidUserOperation.class).when(user).createGame(playerCount, gameId);

        assertThrows(InvalidUserOperation.class, () -> command.execute());
    }

    @Test
    void testExecuteThrowsGameException() throws InvalidUserOperation, GameException {
        int gameId = 123;
        int playerCount = 4;

        command = new CreateGameCommand(user, gameId, playerCount);
        doThrow(GameException.class).when(user).createGame(playerCount, gameId);

        assertThrows(GameException.class, () -> command.execute());
    }

    @Test
    void testCreateGameCommandFromGameIdString() throws InvalidUserInput {
        String gameIdString = "123";

        command = (CreateGameCommand) CreateGameCommand.of(user, gameIdString);

        assertNotNull(command);  // Default player count should be 2
    }

    @Test
    void testCreateGameCommandFromOptions() throws InvalidUserInput {
        List<String> options = List.of("123", "4");

        command = (CreateGameCommand) CreateGameCommand.of(user, options);

        assertNotNull(command);
    }

    @Test
    void testCreateGameCommandThrowsInvalidUserInputForInvalidGameId() {
        List<String> options = List.of("invalid", "4"); 

        assertThrows(InvalidUserInput.class, () -> CreateGameCommand.of(user, options));
    }

    @Test
    void testCreateGameCommandThrowsInvalidUserInputForInvalidPlayerCount() {
        List<String> options = List.of("123", "invalid");  // Invalid playerCount

        assertThrows(InvalidUserInput.class, () -> CreateGameCommand.of(user, options));
    }
    
}
