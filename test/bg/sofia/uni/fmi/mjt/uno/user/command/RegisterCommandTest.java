package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegisterCommandTest {

    @Test
    void testExecute() throws InvalidUserOperation {
        User userMock = mock(User.class);
        String username = "testUser";
        String password = "testPassword";
        RegisterCommand registerCommand = new RegisterCommand(userMock, username, password);

        registerCommand.execute();

        verify(userMock, times(1)).register(username, password);
    }

    @Test
    void testExecuteThrowsInvalidUserOperation() throws InvalidUserOperation {
        User userMock = mock(User.class);
        String username = "testUser";
        String password = "testPassword";
        RegisterCommand registerCommand = new RegisterCommand(userMock, username, password);

        doThrow(InvalidUserOperation.class).when(userMock).register(username, password);

        assertThrows(InvalidUserOperation.class, registerCommand::execute);
    }

    @Test
    void testOf() {
        List<String> options = List.of("testUser", "testPassword");
        User userMock = mock(User.class);

        Command registerCommand = RegisterCommand.of(userMock, options);

        assertInstanceOf(RegisterCommand.class, registerCommand);
    }

}
