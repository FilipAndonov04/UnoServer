package bg.sofia.uni.fmi.mjt.uno.user.command;

import static org.mockito.Mockito.*;

import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserInput;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayCardCommandTest {

    User user;
    PlayCardCommand playCardCommand;

    @BeforeEach
    void setUp() {
        user = mock(User.class);
    }

    @Test
    void testCommandText() {
        assertEquals("play", PlayCardCommand.COMMAND_TEXT);
    }

    @Test
    void testCommandDescription() {
        String expectedDescription = "play --card-id=<card-id> (plays a non-wild card from the your hand)";
        assertEquals(expectedDescription, PlayCardCommand.COMMAND_DESCRIPTION);
    }

    @Test
    void testExecutePlayCardSuccess() throws InvalidUserOperation, GameException {
        int cardId = 1;
        playCardCommand = new PlayCardCommand(user, cardId);
        playCardCommand.execute();
        verify(user, times(1)).playCard(cardId);
    }

    @Test
    void testExecutePlayCardFailure() throws InvalidUserOperation, GameException {
        int cardId = 1;
        doThrow(new GameException("Invalid card")).when(user).playCard(cardId);
        playCardCommand = new PlayCardCommand(user, cardId);
        assertThrows(GameException.class, () -> playCardCommand.execute());
    }

    @Test
    void testExecuteInvalidUserOperation() throws InvalidUserOperation, GameException {
        int cardId = 1;
        doThrow(new InvalidUserOperation("Invalid user operation")).when(user).playCard(cardId);
        playCardCommand = new PlayCardCommand(user, cardId);
        assertThrows(InvalidUserOperation.class, () -> playCardCommand.execute());
    }

    @Test
    void testCommandOf() throws InvalidUserInput {
        String cardIdString = "5";
        playCardCommand = (PlayCardCommand) PlayCardCommand.of(user, cardIdString);
        assertNotNull(playCardCommand);
    }

    @Test
    void testCommandOfInvalidCardId() {
        String cardIdString = "invalid";
        assertThrows(InvalidUserInput.class, () -> PlayCardCommand.of(user, cardIdString));
    }

}

