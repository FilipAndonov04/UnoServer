package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.PrintWriter;

public class ShowHandCommandTest {

    @Test
    void testExecute() throws InvalidUserOperation {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);
        ShowHandCommand showHandCommand = new ShowHandCommand(userMock, printWriterMock);

        String hand = "Card1, Card2, Card3";
        when(userMock.showHand()).thenReturn(hand);

        showHandCommand.execute();

        verify(printWriterMock, times(1)).println(hand);
    }

    @Test
    void testExecuteThrowsInvalidUserOperation() throws InvalidUserOperation {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);
        ShowHandCommand showHandCommand = new ShowHandCommand(userMock, printWriterMock);

        when(userMock.showHand()).thenThrow(InvalidUserOperation.class);

        assertThrows(InvalidUserOperation.class, showHandCommand::execute);
    }

    @Test
    void testOf() {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        Command showHandCommand = ShowHandCommand.of(userMock, printWriterMock);

        assertInstanceOf(ShowHandCommand.class, showHandCommand);
    }

}
