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

public class PlayChooseColorCommandTest {

    User user;
    PlayChooseColorCommand playChooseColorCommand;

    @BeforeEach
    void setUp() {
        user = mock(User.class);
    }

    @Test
    void testCommandText() {
        assertEquals("play-choose", PlayChooseColorCommand.COMMAND_TEXT);
    }

    @Test
    void testCommandDescription() {
        String expectedDescription = "play-choose --card-id=<card-id> --color=<red/green/blue/yellow> " +
            "(plays a choose-color card from your hand; if a color is not chosen, it is the same as last card's)";
        assertEquals(expectedDescription, PlayChooseColorCommand.COMMAND_DESCRIPTION);
    }

    @Test
    void testExecutePlayChooseColorSuccess() throws InvalidUserOperation, GameException {
        int cardId = 1;
        Color color = Color.RED;
        playChooseColorCommand = new PlayChooseColorCommand(user, cardId, color);
        playChooseColorCommand.execute();
        verify(user, times(1)).playChooseColor(cardId, color);
    }

    @Test
    void testExecutePlayChooseColorFailure() throws InvalidUserOperation, GameException {
        int cardId = 1;
        Color color = Color.RED;
        doThrow(new GameException("Invalid color card")).when(user).playChooseColor(cardId, color);
        playChooseColorCommand = new PlayChooseColorCommand(user, cardId, color);
        assertThrows(GameException.class, () -> playChooseColorCommand.execute());
    }

    @Test
    void testExecuteInvalidUserOperation() throws InvalidUserOperation, GameException {
        int cardId = 1;
        Color color = Color.RED;
        doThrow(new InvalidUserOperation("Invalid user operation")).when(user).playChooseColor(cardId, color);
        playChooseColorCommand = new PlayChooseColorCommand(user, cardId, color);
        assertThrows(InvalidUserOperation.class, () -> playChooseColorCommand.execute());
    }

    @Test
    void testCommandOf() throws InvalidUserInput {
        String cardIdString = "5";
        playChooseColorCommand = (PlayChooseColorCommand) PlayChooseColorCommand.of(user, cardIdString);
        assertNotNull(playChooseColorCommand);
    }

    @Test
    void testCommandOfInvalidCardId() {
        String cardIdString = "invalid";
        assertThrows(InvalidUserInput.class, () -> PlayChooseColorCommand.of(user, cardIdString));
    }

    @Test
    void testCommandOfWithColor() throws InvalidUserInput {
        List<String> options = List.of("5", "red");
        playChooseColorCommand = (PlayChooseColorCommand) PlayChooseColorCommand.of(user, options);
        assertNotNull(playChooseColorCommand);
    }

}
