package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.PrintWriter;

public class ShowPlayedCardsCommandTest {

    @Test
    void testExecute() throws InvalidUserOperation {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);
        ShowPlayedCardsCommand showPlayedCardsCommand = new ShowPlayedCardsCommand(userMock, printWriterMock);

        String playedCards = "Card1, Card2";
        when(userMock.showPlayedCards()).thenReturn(playedCards);

        showPlayedCardsCommand.execute();

        verify(printWriterMock, times(1)).println(playedCards);
    }

    @Test
    void testExecuteThrowsInvalidUserOperation() throws InvalidUserOperation {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);
        ShowPlayedCardsCommand showPlayedCardsCommand = new ShowPlayedCardsCommand(userMock, printWriterMock);

        when(userMock.showPlayedCards()).thenThrow(InvalidUserOperation.class);

        assertThrows(InvalidUserOperation.class, showPlayedCardsCommand::execute);
    }

    @Test
    void testOf() {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        Command showPlayedCardsCommand = ShowPlayedCardsCommand.of(userMock, printWriterMock);

        assertInstanceOf(ShowPlayedCardsCommand.class, showPlayedCardsCommand);
    }

}
