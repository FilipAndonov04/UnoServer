package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserInput;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

import java.io.PrintWriter;

public class SummaryCommand implements Command {

    public static final String COMMAND_TEXT = "summary";
    public static final String GAME_ID_OPTION_TEXT = "--game-id=";

    public static final String COMMAND_DESCRIPTION =
        COMMAND_TEXT + ' ' + GAME_ID_OPTION_TEXT + "<game-id> " + "(gets a ended game summary)";

    private final User user;
    private final int gameId;
    private final PrintWriter out;

    public SummaryCommand(User user, int gameId, PrintWriter out) {
        this.user = user;
        this.gameId = gameId;
        this.out = out;
    }

    @Override
    public void execute() throws InvalidUserOperation {
        out.println(user.getGameSummary(gameId));
    }

    public static Command of(User user, PrintWriter out, String gameIdString) throws InvalidUserInput {
        int gameId;
        try {
            gameId = Integer.parseInt(gameIdString);
        } catch (NumberFormatException e) {
            throw new InvalidUserInput("Invalid option content: game id must be a number!");
        }
        return new SummaryCommand(user, gameId, out);
    }

}
