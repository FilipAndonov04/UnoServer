package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.PrintWriter;

public class ShowLastCardCommandTest {

    @Test
    void testExecute() throws InvalidUserOperation, GameException {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);
        ShowLastCardCommand showLastCardCommand = new ShowLastCardCommand(userMock, printWriterMock);

        String lastCard = "Card1";
        when(userMock.showLastCard()).thenReturn(lastCard);

        showLastCardCommand.execute();

        verify(printWriterMock, times(1)).println(lastCard);
    }

    @Test
    void testExecuteThrowsInvalidUserOperation() throws InvalidUserOperation, GameException {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);
        ShowLastCardCommand showLastCardCommand = new ShowLastCardCommand(userMock, printWriterMock);

        when(userMock.showLastCard()).thenThrow(InvalidUserOperation.class);

        assertThrows(InvalidUserOperation.class, showLastCardCommand::execute);
    }

    @Test
    void testExecuteThrowsGameException() throws InvalidUserOperation, GameException {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);
        ShowLastCardCommand showLastCardCommand = new ShowLastCardCommand(userMock, printWriterMock);

        when(userMock.showLastCard()).thenThrow(GameException.class);

        assertThrows(GameException.class, showLastCardCommand::execute);
    }

    @Test
    void testOf() {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        Command showLastCardCommand = ShowLastCardCommand.of(userMock, printWriterMock);

        assertInstanceOf(ShowLastCardCommand.class, showLastCardCommand);
    }

}
