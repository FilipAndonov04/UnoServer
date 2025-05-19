package bg.sofia.uni.fmi.mjt.uno.user.command;

import static org.mockito.Mockito.*;

import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class LoginCommandTest {

    private User user;
    private LoginCommand loginCommand;

    @BeforeEach
    void setUp() {
        user = mock(User.class);
    }

    @Test
    void testCommandText() {
        assertEquals("login", LoginCommand.COMMAND_TEXT);
    }

    @Test
    void testCommandDescription() {
        String expectedDescription = "login --username=<username> --password=<password> (logs in an account)";
        assertEquals(expectedDescription, LoginCommand.COMMAND_DESCRIPTION);
    }

    @Test
    void testExecuteLoginSuccess() throws InvalidUserOperation {
        String username = "testUser";
        String password = "testPass";

        loginCommand = new LoginCommand(user, username, password);

        loginCommand.execute();
        verify(user, times(1)).login(username, password);
    }

    @Test
    void testExecuteLoginFailure() throws InvalidUserOperation {
        String username = "testUser";
        String password = "wrongPass";

        doThrow(new InvalidUserOperation("Invalid credentials")).when(user).login(username, password);

        loginCommand = new LoginCommand(user, username, password);

        assertThrows(InvalidUserOperation.class, () -> loginCommand.execute());
    }

    @Test
    void testCommandOf() {
        String username = "testUser";
        String password = "testPass";
        List<String> options = List.of(username, password);

        loginCommand = (LoginCommand) LoginCommand.of(user, options);

        assertNotNull(loginCommand);
    }

}
