package bg.sofia.uni.fmi.mjt.uno.user.command;

import static org.mockito.Mockito.*;

import bg.sofia.uni.fmi.mjt.uno.game.GameStatus;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserInput;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ListGamesCommandTest {

    private User user;
    private PrintWriter out;
    private ListGamesCommand listGamesCommand;

    @BeforeEach
    void setUp() {
        user = mock(User.class);
        out = new PrintWriter(new StringWriter());
    }

    @Test
    void testCommandText() {
        assertEquals("list-games", ListGamesCommand.COMMAND_TEXT);
    }

    @Test
    void testCommandDescription() {
        String expectedDescription = "list-games --status=<started/ended/available/all> " +
            "(lists all the game with the chosen status; when not given a status, it is all by default)";
        assertEquals(expectedDescription, ListGamesCommand.COMMAND_DESCRIPTION);
    }

    @Test
    void testExecuteThrowsInvalidUserOperation() throws InvalidUserOperation {
        when(user.listGames(null)).thenThrow(InvalidUserOperation.class);

        listGamesCommand = new ListGamesCommand(user, null, out);

        assertThrows(InvalidUserOperation.class, () -> listGamesCommand.execute());
    }

    @Test
    void testCommandOfWithStatus() throws InvalidUserInput {
        listGamesCommand = (ListGamesCommand) ListGamesCommand.of(user, out, "started");

        assertNotNull(listGamesCommand);
    }

    @Test
    void testCommandOfWithInvalidStatus() {
        assertThrows(InvalidUserInput.class, () -> ListGamesCommand.of(user, out, "invalid"));
    }

    @Test
    void testCommandOfWithNoStatus() throws InvalidUserInput {
        listGamesCommand = (ListGamesCommand) ListGamesCommand.of(user, out);

        assertNotNull(listGamesCommand);
    }
    
}
