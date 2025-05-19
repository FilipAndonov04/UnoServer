package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class StartGameCommandTest {

    @Test
    void testExecute() throws InvalidUserOperation, GameException {
        User userMock = mock(User.class);
        StartGameCommand startGameCommand = new StartGameCommand(userMock);

        startGameCommand.execute();

        verify(userMock, times(1)).startGame();
    }

    @Test
    void testExecuteThrowsInvalidUserOperation() throws InvalidUserOperation, GameException {
        User userMock = mock(User.class);
        StartGameCommand startGameCommand = new StartGameCommand(userMock);

        doThrow(InvalidUserOperation.class).when(userMock).startGame();

        assertThrows(InvalidUserOperation.class, startGameCommand::execute);
    }

    @Test
    void testExecuteThrowsGameException() throws InvalidUserOperation, GameException {
        User userMock = mock(User.class);
        StartGameCommand startGameCommand = new StartGameCommand(userMock);

        doThrow(GameException.class).when(userMock).startGame();

        assertThrows(GameException.class, startGameCommand::execute);
    }

    @Test
    void testOf() {
        User userMock = mock(User.class);

        Command startGameCommand = StartGameCommand.of(userMock);

        assertTrue(startGameCommand instanceof StartGameCommand);
    }

}
