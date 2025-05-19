package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

public class StartGameCommand implements Command {

    public static final String COMMAND_TEXT = "start";

    public static final String COMMAND_DESCRIPTION = COMMAND_TEXT + " (starts the game you have created)";

    private final User user;

    public StartGameCommand(User user) {
        this.user = user;
    }

    @Override
    public void execute() throws InvalidUserOperation, GameException {
        user.startGame();
    }

    public static Command of(User user) {
        return new StartGameCommand(user);
    }

}
