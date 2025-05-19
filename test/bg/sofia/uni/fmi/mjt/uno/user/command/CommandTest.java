package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserInput;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;

public class CommandTest {

    @Test
    void testCreateWithNoOptions() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "show-hand";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(ShowHandCommand.class, command);
    }

    @Test
    void testCreateWithZeroOptions2() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "list-games";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(ListGamesCommand.class, command);
    }

    @Test
    void testCreateWithZeroOptions3() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(RefreshCommand.class, command);
    }

    @Test
    void testCreateWithZeroOptions4() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "help";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(HelpCommand.class, command);
    }

    @Test
    void testCreateWithZeroOptions5() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "spectate";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(SpectateGameCommand.class, command);
    }

    @Test
    void testCreateWithZeroOptions6() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "show-played-cards";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(ShowPlayedCardsCommand.class, command);
    }

    @Test
    void testCreateWithZeroOptions7() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "show-last-card";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(ShowLastCardCommand.class, command);
    }

    @Test
    void testCreateWithZeroOptions8() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "draw";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(DrawCardCommand.class, command);
    }

    @Test
    void testCreateWithZeroOptions9() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "accept-effect";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(AcceptEffectCommand.class, command);
    }

    @Test
    void testCreateWithZeroOptions10() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "leave";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(LeaveGameCommand.class, command);
    }

    @Test
    void testCreateWithZeroOptions11() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "logout";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(LogoutCommand.class, command);
    }

    @Test
    void testCreateWithZeroOptions12() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "start";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(StartGameCommand.class, command);
    }

    @Test
    void testCreateWithOneOption() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "list-games --status=available";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(ListGamesCommand.class, command);
    }

    @Test
    void testCreateWithOneOption2() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "summary --game-id=1";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(SummaryCommand.class, command);
    }

    @Test
    void testCreateWithOneOption3() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "play --card-id=1";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(PlayCardCommand.class, command);
    }

    @Test
    void testCreateWithOneOption4() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "play-choose --card-id=1";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(PlayChooseColorCommand.class, command);
    }

    @Test
    void testCreateWithOneOption5() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "play-plus-four --card-id=1";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(PlayPlusFourCommand.class, command);
    }

    @Test
    void testCreateWithOneOption6() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "create-game --game-id=1";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(CreateGameCommand.class, command);
    }

    @Test
    void testCreateWithOneOption7() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "join --game-id=1";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(JoinGameCommand.class, command);
    }

    @Test
    void testCreateWithTwoOptions() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "create-game --game-id=123 --number-of-players=4";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(CreateGameCommand.class, command);
    }

    @Test
    void testCreateWithTwoOptions2() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "join --game-id=123 --display-name=213";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(JoinGameCommand.class, command);
    }

    @Test
    void testCreateWithTwoOptions3() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "play-choose --color=red --card-id=123";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(PlayChooseColorCommand.class, command);
    }

    @Test
    void testCreateWithTwoOptions4() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "play-plus-four --color=red --card-id=123";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(PlayPlusFourCommand.class, command);
    }

    @Test
    void testCreateWithTwoOptions5() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "register --username=red --password=123";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(RegisterCommand.class, command);
    }

    @Test
    void testCreateWithTwoOptions6() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "login --password=123 --username=red ";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(LoginCommand.class, command);
    }

    @Test
    void testCreateWithInvalidCommand() {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "invalid-command";

        assertThrows(InvalidUserInput.class, () -> Command.create(commandLine, userMock, printWriterMock));
    }

    @Test
    void testCreateWithInvalidOption() {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "list-games --invalid-option";

        assertThrows(InvalidUserInput.class, () -> Command.create(commandLine, userMock, printWriterMock));
    }

    @Test
    void testCreateWithBlankOption() {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "create-game --game-id= --player-count=4";

        assertThrows(InvalidUserInput.class, () -> Command.create(commandLine, userMock, printWriterMock));
    }

    @Test
    void testCreateWithBlankOption2() {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "play --card-id=";

        assertThrows(InvalidUserInput.class, () -> Command.create(commandLine, userMock, printWriterMock));
    }

    @Test
    void testCreateWithExtraSpaces() throws InvalidUserInput {
        User userMock = mock(User.class);
        PrintWriter printWriterMock = mock(PrintWriter.class);

        String commandLine = "list-games   --status=ended   ";
        Command command = Command.create(commandLine, userMock, printWriterMock);

        assertInstanceOf(ListGamesCommand.class, command);
    }

}
