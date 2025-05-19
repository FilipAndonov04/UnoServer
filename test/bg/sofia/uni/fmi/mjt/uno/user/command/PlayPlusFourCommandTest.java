package bg.sofia.uni.fmi.mjt.uno.user.command;

import static org.mockito.Mockito.*;

import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserInput;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import bg.sofia.uni.fmi.mjt.uno.game.card.Color;

import java.util.List;

class PlayPlusFourCommandTest {

    User user;
    PlayPlusFourCommand playPlusFourCommand;

    @BeforeEach
    void setUp() {
        user = mock(User.class);
    }

    @Test
    void testCommandText() {
        assertEquals("play-plus-four", PlayPlusFourCommand.COMMAND_TEXT);
    }

    @Test
    void testCommandDescription() {
        String expectedDescription = "play-plus-four --card-id=<card-id> --color=<red/green/blue/yellow> " +
            "(plays a plus-four card from your hand; if a color is not chosen, it is the same as last card's)";
        assertEquals(expectedDescription, PlayPlusFourCommand.COMMAND_DESCRIPTION);
    }

    @Test
    void testExecutePlayPlusFourSuccess() throws InvalidUserOperation, GameException {
        int cardId = 1;
        Color color = Color.RED;
        playPlusFourCommand = new PlayPlusFourCommand(user, cardId, color);
        playPlusFourCommand.execute();
        verify(user, times(1)).playPlusFour(cardId, color);
    }

    @Test
    void testExecutePlayPlusFourFailure() throws InvalidUserOperation, GameException {
        int cardId = 1;
        Color color = Color.RED;
        doThrow(new GameException("Invalid plus-four card")).when(user).playPlusFour(cardId, color);
        playPlusFourCommand = new PlayPlusFourCommand(user, cardId, color);
        assertThrows(GameException.class, () -> playPlusFourCommand.execute());
    }

    @Test
    void testExecuteInvalidUserOperation() throws InvalidUserOperation, GameException {
        int cardId = 1;
        Color color = Color.RED;
        doThrow(new InvalidUserOperation("Invalid user operation")).when(user).playPlusFour(cardId, color);
        playPlusFourCommand = new PlayPlusFourCommand(user, cardId, color);
        assertThrows(InvalidUserOperation.class, () -> playPlusFourCommand.execute());
    }

    @Test
    void testCommandOf() throws InvalidUserInput {
        String cardIdString = "5";
        playPlusFourCommand = (PlayPlusFourCommand) PlayPlusFourCommand.of(user, cardIdString);
        assertNotNull(playPlusFourCommand);
    }

    @Test
    void testCommandOfInvalidCardId() {
        String cardIdString = "invalid";
        assertThrows(InvalidUserInput.class, () -> PlayPlusFourCommand.of(user, cardIdString));
    }

    @Test
    void testCommandOfWithColor() throws InvalidUserInput {
        List<String> options = List.of("5", "red");
        playPlusFourCommand = (PlayPlusFourCommand) PlayPlusFourCommand.of(user, options);
        assertNotNull(playPlusFourCommand);
    }

}
