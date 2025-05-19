package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

public class AcceptEffectCommand implements Command {

    public static final String COMMAND_TEXT = "accept-effect";

    public static final String COMMAND_DESCRIPTION = COMMAND_TEXT + " (accepts an effect given by another player)";

    private final User user;

    public AcceptEffectCommand(User user) {
        this.user = user;
    }

    @Override
    public void execute() throws InvalidUserOperation, GameException {
        user.acceptEffect();
    }

    public static Command of(User user) {
        return new AcceptEffectCommand(user);
    }

}
