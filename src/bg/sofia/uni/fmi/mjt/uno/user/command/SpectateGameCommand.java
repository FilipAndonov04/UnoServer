package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

public class SpectateGameCommand implements Command {

    public static final String COMMAND_TEXT = "spectate";

    public static final String COMMAND_DESCRIPTION = COMMAND_TEXT + " (spectates the game you have just won)";

    private final User user;

    public SpectateGameCommand(User user) {
        this.user = user;
    }

    @Override
    public void execute() throws InvalidUserOperation {
        user.spectateGame();
    }

    public static Command of(User user) {
        return new SpectateGameCommand(user);
    }

}
