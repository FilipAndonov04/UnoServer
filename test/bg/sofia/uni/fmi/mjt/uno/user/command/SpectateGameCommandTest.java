package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpectateGameCommandTest {

    @Test
    void testExecute() throws InvalidUserOperation {
        User userMock = mock(User.class);
        SpectateGameCommand spectateGameCommand = new SpectateGameCommand(userMock);

        spectateGameCommand.execute();

        verify(userMock, times(1)).spectateGame();
    }

    @Test
    void testExecuteThrowsInvalidUserOperation() throws InvalidUserOperation {
        User userMock = mock(User.class);
        SpectateGameCommand spectateGameCommand = new SpectateGameCommand(userMock);

        doThrow(InvalidUserOperation.class).when(userMock).spectateGame();

        assertThrows(InvalidUserOperation.class, spectateGameCommand::execute);
    }

    @Test
    void testOf() {
        User userMock = mock(User.class);

        Command spectateGameCommand = SpectateGameCommand.of(userMock);

        assertInstanceOf(SpectateGameCommand.class, spectateGameCommand);
    }

}
