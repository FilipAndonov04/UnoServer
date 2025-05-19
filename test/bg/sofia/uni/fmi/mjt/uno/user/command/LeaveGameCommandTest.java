package bg.sofia.uni.fmi.mjt.uno.user.command;

import static org.mockito.Mockito.*;

import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class LeaveGameCommandTest {

    private User user;
    private LeaveGameCommand leaveGameCommand;

    @BeforeEach
    void setUp() {
        user = mock(User.class);
        leaveGameCommand = new LeaveGameCommand(user);
    }

    @Test
    void testCommandText() {
        assertEquals("leave", LeaveGameCommand.COMMAND_TEXT);
    }

    @Test
    void testCommandDescription() {
        String expectedDescription = "leave (leaves a game)";
        
        assertEquals(expectedDescription, LeaveGameCommand.COMMAND_DESCRIPTION);
    }

    @Test
    void testExecuteLeaveGame() throws InvalidUserOperation, GameException {
        doNothing().when(user).leaveGame();
        leaveGameCommand.execute();
        
        verify(user).leaveGame();
    }

    @Test
    void testExecuteThrowsException() throws InvalidUserOperation, GameException {
        doThrow(GameException.class).when(user).leaveGame();

        assertThrows(GameException.class, () -> leaveGameCommand.execute());
    }

    @Test
    void testCommandOf() {
        LeaveGameCommand command = (LeaveGameCommand) LeaveGameCommand.of(user);

        assertNotNull(command);
    }
}
