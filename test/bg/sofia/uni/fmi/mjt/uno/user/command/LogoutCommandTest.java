package bg.sofia.uni.fmi.mjt.uno.user.command;

import static org.mockito.Mockito.*;

import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LogoutCommandTest {

    User user;
    LogoutCommand logoutCommand;

    @BeforeEach
    void setUp() {
        user = mock(User.class);
    }

    @Test
    void testCommandText() {
        assertEquals("logout", LogoutCommand.COMMAND_TEXT);
    }

    @Test
    void testCommandDescription() {
        String expectedDescription = "logout (logs out of an account)";
        assertEquals(expectedDescription, LogoutCommand.COMMAND_DESCRIPTION);
    }

    @Test
    void testExecuteLogoutSuccess() throws InvalidUserOperation {
        logoutCommand = new LogoutCommand(user);
        logoutCommand.execute();
        verify(user, times(1)).logout();
    }

    @Test
    void testExecuteLogoutFailure() throws InvalidUserOperation {
        doThrow(new InvalidUserOperation("Invalid logout operation")).when(user).logout();
        logoutCommand = new LogoutCommand(user);
        assertThrows(InvalidUserOperation.class, () -> logoutCommand.execute());
    }

    @Test
    void testCommandOf() {
        logoutCommand = (LogoutCommand) LogoutCommand.of(user);
        assertNotNull(logoutCommand);
    }

}
