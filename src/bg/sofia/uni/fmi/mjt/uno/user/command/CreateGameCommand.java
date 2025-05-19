package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserInput;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

import java.util.List;

public class CreateGameCommand implements Command {

    public static final String COMMAND_TEXT = "create-game";
    public static final String GAME_ID_OPTION_TEXT = "--game-id=";
    public static final String PLAYER_COUNT_OPTION_TEXT = "--number-of-players=";

    private static final int DEFAULT_PLAYER_COUNT = 2;

    public static final String COMMAND_DESCRIPTION =
        COMMAND_TEXT + ' ' + GAME_ID_OPTION_TEXT + "<game-id> " + PLAYER_COUNT_OPTION_TEXT + "<number> " +
            "(creates a game; must have unique id; when not given number of players, it is " + DEFAULT_PLAYER_COUNT +
            " by default)";

    private final User user;
    private final int gameId;
    private final int playerCount;

    public CreateGameCommand(User user, int gameId, int playerCount) {
        this.user = user;
        this.gameId = gameId;
        this.playerCount = playerCount;
    }

    @Override
    public void execute() throws InvalidUserOperation, GameException {
        user.createGame(playerCount, gameId);
    }

    public static Command of(User user, String gameIdString) throws InvalidUserInput {
        int gameId;
        try {
            gameId = Integer.parseInt(gameIdString);
        } catch (NumberFormatException e) {
            throw new InvalidUserInput("Invalid option content: game id must be a number!");
        }
        return new CreateGameCommand(user, gameId, DEFAULT_PLAYER_COUNT);
    }

    public static Command of(User user, List<String> options) throws InvalidUserInput {
        String gameIdString = options.getFirst();
        String playerCountString = options.getLast();

        int gameId;
        try {
            gameId = Integer.parseInt(gameIdString);
        } catch (NumberFormatException e) {
            throw new InvalidUserInput("Invalid option content: game id must be a number!");
        }
        int playerCount;
        try {
            playerCount = Integer.parseInt(playerCountString);
        } catch (NumberFormatException e) {
            throw new InvalidUserInput("Invalid option content: player count must be a number!");
        }
        return new CreateGameCommand(user, gameId, playerCount);
    }

}
