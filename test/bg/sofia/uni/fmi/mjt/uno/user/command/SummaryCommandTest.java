package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserInput;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;

public class SummaryCommandTest {

    @Test
    void testExecute() throws InvalidUserOperation {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);
        int gameId = 1;
        SummaryCommand summaryCommand = new SummaryCommand(userMock, gameId, printWriterMock);

        summaryCommand.execute();

        verify(userMock, times(1)).getGameSummary(gameId);
    }

    @Test
    void testExecuteThrowsInvalidUserOperation() throws InvalidUserOperation {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);
        int gameId = 1;
        SummaryCommand summaryCommand = new SummaryCommand(userMock, gameId, printWriterMock);

        doThrow(InvalidUserOperation.class).when(userMock).getGameSummary(gameId);

        assertThrows(InvalidUserOperation.class, summaryCommand::execute);
    }

    @Test
    void testOf() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);
        String gameIdString = "1";

        Command summaryCommand = SummaryCommand.of(userMock, printWriterMock, gameIdString);

        assertInstanceOf(SummaryCommand.class, summaryCommand);
    }

    @Test
    void testOfThrowsInvalidUserInput() {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);
        String gameIdString = "invalid";

        assertThrows(InvalidUserInput.class, () -> SummaryCommand.of(userMock, printWriterMock, gameIdString));
    }

}
