package bg.sofia.uni.fmi.mjt.uno.user.command;

import static org.mockito.Mockito.*;

import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserInput;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class JoinGameCommandTest {

    private User user;
    private JoinGameCommand joinGameCommand;

    @BeforeEach
    void setUp() {
        user = mock(User.class);  // Mock the User class
    }

    @Test
    void testCommandText() {
        assertEquals("join", JoinGameCommand.COMMAND_TEXT);
    }

    @Test
    void testCommandDescription() {
        String expectedDescription = "join --game-id=<game-id> --display-name=<display-name> " +
            "(joins a game; game must be available and not full; when not given display name, it is your username by default)";
        assertEquals(expectedDescription, JoinGameCommand.COMMAND_DESCRIPTION);
    }

    @Test
    void testExecuteJoinGame() throws InvalidUserOperation, GameException {
        joinGameCommand = new JoinGameCommand(user, 123, "Player1");
        doNothing().when(user).joinGame(123, "Player1");  // Mock joinGame method

        joinGameCommand.execute();  // Execute the command

        verify(user).joinGame(123, "Player1");  // Verify that joinGame was called with correct parameters
    }

    @Test
    void testExecuteThrowsGameException() throws InvalidUserOperation, GameException {
        joinGameCommand = new JoinGameCommand(user, 123, "Player1");
        doThrow(GameException.class).when(user).joinGame(123, "Player1");

        assertThrows(GameException.class, () -> joinGameCommand.execute());
    }

    @Test
    void testCommandOfWithGameId() throws InvalidUserInput {
        joinGameCommand = (JoinGameCommand) JoinGameCommand.of(user, "123");

        assertNotNull(joinGameCommand);
    }

    @Test
    void testCommandOfWithOptions() throws InvalidUserInput {
        List<String> options = Arrays.asList("123", "Player1");
        joinGameCommand = (JoinGameCommand) JoinGameCommand.of(user, options);

        assertNotNull(joinGameCommand);
    }

    @Test
    void testInvalidGameIdThrowsInvalidUserInput() {
        assertThrows(InvalidUserInput.class, () -> JoinGameCommand.of(user, "invalid"));
    }

    @Test
    void testInvalidGameIdInOptionsThrowsInvalidUserInput() {
        List<String> options = Arrays.asList("invalid", "Player1");
        assertThrows(InvalidUserInput.class, () -> JoinGameCommand.of(user, options));
    }

}
