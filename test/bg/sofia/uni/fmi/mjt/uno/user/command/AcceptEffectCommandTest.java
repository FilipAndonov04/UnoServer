package bg.sofia.uni.fmi.mjt.uno.user.command;

import static org.mockito.Mockito.*;

import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class AcceptEffectCommandTest {

    private User user;
    private AcceptEffectCommand command;

    @BeforeEach
    void setUp() {
        user = mock(User.class);  // Mock the User class
        command = new AcceptEffectCommand(user);
    }

    @Test
    void testCommandText() {
        assertEquals("accept-effect", AcceptEffectCommand.COMMAND_TEXT);
    }

    @Test
    void testCommandDescription() {
        assertEquals("accept-effect (accepts an effect given by another player)", AcceptEffectCommand.COMMAND_DESCRIPTION);
    }

    @Test
    void testExecuteAcceptEffect() throws InvalidUserOperation, GameException {
        doNothing().when(user).acceptEffect();  // Mock the behavior of acceptEffect()

        command.execute();  // Call the execute method

        verify(user).acceptEffect();  // Verify that acceptEffect was called on the user
    }

    @Test
    void testExecuteThrowsInvalidUserOperation() throws InvalidUserOperation, GameException {
        doThrow(InvalidUserOperation.class).when(user).acceptEffect();

        assertThrows(InvalidUserOperation.class, () -> command.execute());
    }

    @Test
    void testExecuteThrowsGameException() throws InvalidUserOperation, GameException {
        doThrow(GameException.class).when(user).acceptEffect();

        assertThrows(GameException.class, () -> command.execute());
    }

    @Test
    void testCommandOf() {
        Command createdCommand = AcceptEffectCommand.of(user);
        assertNotNull(createdCommand);
        assertInstanceOf(AcceptEffectCommand.class, createdCommand);
    }

}