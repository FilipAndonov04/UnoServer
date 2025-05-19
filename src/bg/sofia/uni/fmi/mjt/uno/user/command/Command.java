package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserInput;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Command {

    void execute() throws InvalidUserOperation, GameException;

    String INVALID_USER_COMMAND_MESSAGE = "Invalid user command!";
    String INVALID_COMMAND_OPTION_MESSAGE = "Invalid command option!";
    String OPTION_CANNOT_BE_BLANK_MESSAGE = "Invalid option content: option cannot be blank!";

    static Command create(String line, User user, PrintWriter out) throws InvalidUserInput {
        if (line.isBlank()) {
            return new RefreshCommand();
        }

        List<String> words = new ArrayList<>(Arrays.asList(line.split("\\s+")));
        String command = words.removeFirst();

        return switch (words.size()) {
            case 0 -> createCommandWithNoOptions(command, user, out);
            case 1 -> createCommandWithOneOption(command, words.getFirst(), user, out);
            case 2 -> createCommandWithTwoOptions(command, words, user, out);
            default -> throw new InvalidUserInput(INVALID_USER_COMMAND_MESSAGE);
        };
    }

    private static Command createCommandWithNoOptions(String command, User user, PrintWriter out)
        throws InvalidUserInput {
        return switch (command) {
            case AcceptEffectCommand.COMMAND_TEXT -> AcceptEffectCommand.of(user);
            case DrawCardCommand.COMMAND_TEXT -> DrawCardCommand.of(user);
            case HelpCommand.COMMAND_TEXT -> HelpCommand.of(out);
            case LeaveGameCommand.COMMAND_TEXT -> LeaveGameCommand.of(user);
            case ListGamesCommand.COMMAND_TEXT -> ListGamesCommand.of(user, out);
            case LogoutCommand.COMMAND_TEXT -> LogoutCommand.of(user);
            case ShowHandCommand.COMMAND_TEXT -> ShowHandCommand.of(user, out);
            case ShowPlayedCardsCommand.COMMAND_TEXT -> ShowPlayedCardsCommand.of(user, out);
            case ShowLastCardCommand.COMMAND_TEXT -> ShowLastCardCommand.of(user, out);
            case SpectateGameCommand.COMMAND_TEXT -> SpectateGameCommand.of(user);
            case StartGameCommand.COMMAND_TEXT -> StartGameCommand.of(user);
            default -> throw new InvalidUserInput(INVALID_USER_COMMAND_MESSAGE);
        };
    }

    private static Command createCommandWithOneOption(String command, String word, User user, PrintWriter out)
        throws InvalidUserInput {
        return switch (command) {
            case ListGamesCommand.COMMAND_TEXT ->
                ListGamesCommand.of(user, out, getOptionFromOneWord(word, ListGamesCommand.STATUS_OPTION_TEXT));
            case SummaryCommand.COMMAND_TEXT ->
                SummaryCommand.of(user, out, getOptionFromOneWord(word, SummaryCommand.GAME_ID_OPTION_TEXT));
            case PlayCardCommand.COMMAND_TEXT ->
                PlayCardCommand.of(user, getOptionFromOneWord(word, PlayCardCommand.CARD_ID_OPTION_TEXT));
            case CreateGameCommand.COMMAND_TEXT ->
                CreateGameCommand.of(user, getOptionFromOneWord(word, CreateGameCommand.GAME_ID_OPTION_TEXT));
            case JoinGameCommand.COMMAND_TEXT ->
                JoinGameCommand.of(user, getOptionFromOneWord(word, JoinGameCommand.GAME_ID_OPTION_TEXT));
            case PlayChooseColorCommand.COMMAND_TEXT ->
                PlayChooseColorCommand.of(user, getOptionFromOneWord(word, PlayChooseColorCommand.CARD_ID_OPTION_TEXT));
            case PlayPlusFourCommand.COMMAND_TEXT ->
                PlayPlusFourCommand.of(user, getOptionFromOneWord(word, PlayPlusFourCommand.CARD_ID_OPTION_TEXT));
            default -> throw new InvalidUserInput(INVALID_USER_COMMAND_MESSAGE);
        };
    }

    private static Command createCommandWithTwoOptions(String command, List<String> words, User user, PrintWriter out)
        throws InvalidUserInput {
        return switch (command) {
            case CreateGameCommand.COMMAND_TEXT -> CreateGameCommand.of(user,
                getOptionsFromTwoWords(words, CreateGameCommand.GAME_ID_OPTION_TEXT,
                    CreateGameCommand.PLAYER_COUNT_OPTION_TEXT));
            case JoinGameCommand.COMMAND_TEXT -> JoinGameCommand.of(user,
                getOptionsFromTwoWords(words, JoinGameCommand.GAME_ID_OPTION_TEXT,
                    JoinGameCommand.DISPLAY_NAME_OPTION_TEXT));
            case PlayChooseColorCommand.COMMAND_TEXT -> PlayChooseColorCommand.of(user,
                getOptionsFromTwoWords(words, PlayChooseColorCommand.CARD_ID_OPTION_TEXT,
                    PlayChooseColorCommand.COLOR_OPTION_TEXT));
            case PlayPlusFourCommand.COMMAND_TEXT -> PlayPlusFourCommand.of(user,
                getOptionsFromTwoWords(words, PlayPlusFourCommand.CARD_ID_OPTION_TEXT,
                    PlayPlusFourCommand.COLOR_OPTION_TEXT));
            case RegisterCommand.COMMAND_TEXT -> RegisterCommand.of(user,
                getOptionsFromTwoWords(words, RegisterCommand.USERNAME_OPTION_TEXT,
                    RegisterCommand.PASSWORD_OPTION_TEXT));
            case LoginCommand.COMMAND_TEXT -> LoginCommand.of(user,
                getOptionsFromTwoWords(words, LoginCommand.USERNAME_OPTION_TEXT,
                    LoginCommand.PASSWORD_OPTION_TEXT));
            default -> throw new InvalidUserInput(INVALID_USER_COMMAND_MESSAGE);
        };
    }

    private static String getOptionFromOneWord(String word, String optionText) throws InvalidUserInput {
        if (!word.startsWith(optionText)) {
            throw new InvalidUserInput(INVALID_COMMAND_OPTION_MESSAGE);
        }

        String option = word.substring(optionText.length());
        if (option.isBlank()) {
            throw new InvalidUserInput(OPTION_CANNOT_BE_BLANK_MESSAGE);
        }
        return option;
    }

    private static List<String> getOptionsFromTwoWords(List<String> words, String optionTextOne, String optionTextTwo)
        throws InvalidUserInput {
        if (words.getFirst().startsWith(optionTextOne) && words.getLast().startsWith(optionTextTwo)) {
            String optionOne = words.getFirst().substring(optionTextOne.length());
            String optionTwo = words.getLast().substring(optionTextTwo.length());
            if (optionOne.isBlank() || optionTwo.isBlank()) {
                throw new InvalidUserInput(OPTION_CANNOT_BE_BLANK_MESSAGE);
            }
            return List.of(optionOne, optionTwo);
        }
        if (words.getFirst().startsWith(optionTextTwo) && words.getLast().startsWith(optionTextOne)) {
            String optionOne = words.getLast().substring(optionTextOne.length());
            String optionTwo = words.getFirst().substring(optionTextTwo.length());
            if (optionOne.isBlank() || optionTwo.isBlank()) {
                throw new InvalidUserInput(OPTION_CANNOT_BE_BLANK_MESSAGE);
            }
            return List.of(optionOne, optionTwo);
        }

        throw new InvalidUserInput(INVALID_COMMAND_OPTION_MESSAGE);
    }

}
