package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.game.GameStatus;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserInput;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

import java.io.PrintWriter;

public class ListGamesCommand implements Command {

    public static final String COMMAND_TEXT = "list-games";
    public static final String STATUS_OPTION_TEXT = "--status=";

    public static final String COMMAND_DESCRIPTION =
        COMMAND_TEXT + ' ' +  STATUS_OPTION_TEXT + "<started/ended/available/all> " +
            "(lists all the game with the chosen status; when not given a status, it is all by default)";

    private final User user;
    private final GameStatus status;
    private final PrintWriter out;

    public ListGamesCommand(User user, GameStatus status, PrintWriter out) {
        this.user = user;
        this.status = status;
        this.out = out;
    }

    @Override
    public void execute() throws InvalidUserOperation {
        out.println(user.listGames(status));
    }

    public static Command of(User user, PrintWriter out) {
        return new ListGamesCommand(user, null, out);
    }

    public static Command of(User user, PrintWriter out, String gameStatusString) throws InvalidUserInput {
        return new ListGamesCommand(user, getGameStatusFromString(gameStatusString), out);
    }

    private static GameStatus getGameStatusFromString(String string) throws InvalidUserInput {
        return switch (string) {
            case "started" -> GameStatus.STARTED;
            case "ended" -> GameStatus.ENDED;
            case "available" -> GameStatus.AVAILABLE;
            case "all" -> null;
            default -> throw new InvalidUserInput("Invalid option content: invalid status!");
        };
    }

}
