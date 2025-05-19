package bg.sofia.uni.fmi.mjt.uno.user.command;

import static org.mockito.Mockito.*;

import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class DrawCardCommandTest {

    private User user;
    private DrawCardCommand command;

    @BeforeEach
    void setUp() {
        user = mock(User.class);  // Mock the User class
    }

    @Test
    void testCommandText() {
        assertEquals("draw", DrawCardCommand.COMMAND_TEXT);
    }

    @Test
    void testCommandDescription() {
        String expectedDescription = "draw (draws a card; you can only draw a card if you can't play any cards in your hand)";
        assertEquals(expectedDescription, DrawCardCommand.COMMAND_DESCRIPTION);
    }

    @Test
    void testExecuteDrawCard() throws InvalidUserOperation, GameException {
        command = new DrawCardCommand(user);
        doNothing().when(user).drawCard();  // Mock drawCard method

        command.execute();  // Execute the command

        verify(user).drawCard();  // Verify that drawCard was called on the user
    }

    @Test
    void testExecuteThrowsInvalidUserOperation() throws InvalidUserOperation, GameException {
        command = new DrawCardCommand(user);
        doThrow(InvalidUserOperation.class).when(user).drawCard();

        assertThrows(InvalidUserOperation.class, () -> command.execute());
    }

    @Test
    void testExecuteThrowsGameException() throws InvalidUserOperation, GameException {
        command = new DrawCardCommand(user);
        doThrow(GameException.class).when(user).drawCard();

        assertThrows(GameException.class, () -> command.execute());
    }

    @Test
    void testCommandOf() {
        command = (DrawCardCommand) DrawCardCommand.of(user);
        assertNotNull(command);
    }
    
}
