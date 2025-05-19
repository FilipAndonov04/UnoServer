package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

public class LeaveGameCommand implements Command {

    public static final String COMMAND_TEXT = "leave";

    public static final String COMMAND_DESCRIPTION = COMMAND_TEXT + " (leaves a game)";

    private final User user;

    public LeaveGameCommand(User user) {
        this.user = user;
    }

    @Override
    public void execute() throws InvalidUserOperation, GameException {
        user.leaveGame();
    }

    public static Command of(User user) {
        return new LeaveGameCommand(user);
    }

}
