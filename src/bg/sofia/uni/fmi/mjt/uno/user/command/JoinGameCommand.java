package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserInput;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

import java.util.List;

public class JoinGameCommand implements Command {

    public static final String COMMAND_TEXT = "join";
    public static final String GAME_ID_OPTION_TEXT = "--game-id=";
    public static final String DISPLAY_NAME_OPTION_TEXT = "--display-name=";

    public static final String COMMAND_DESCRIPTION =
        COMMAND_TEXT + ' ' + GAME_ID_OPTION_TEXT + "<game-id> " + DISPLAY_NAME_OPTION_TEXT + "<display-name> " +
            "(joins a game; game must be available and not full; " +
            "when not given display name, it is your username by default)";

    private final User user;
    private final int gameId;
    private final String displayName;

    public JoinGameCommand(User user, int gameId, String displayName) {
        this.user = user;
        this.displayName = displayName;
        this.gameId = gameId;
    }

    @Override
    public void execute() throws InvalidUserOperation, GameException {
        user.joinGame(gameId, displayName);
    }

    public static Command of(User user, String gameIdString) throws InvalidUserInput {
        int gameId;
        try {
            gameId = Integer.parseInt(gameIdString);
        } catch (NumberFormatException e) {
            throw new InvalidUserInput("Invalid option content: game id must be a number!");
        }
        return new JoinGameCommand(user, gameId, null);
    }

    public static Command of(User user, List<String> options) throws InvalidUserInput {
        String gameIdString = options.getFirst();
        String displayName = options.getLast();

        int gameId;
        try {
            gameId = Integer.parseInt(gameIdString);
        } catch (NumberFormatException e) {
            throw new InvalidUserInput("Invalid option content: game id must be a number!");
        }
        return new JoinGameCommand(user, gameId, displayName);
    }

}
