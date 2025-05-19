package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

public class DrawCardCommand implements Command {

    public static final String COMMAND_TEXT = "draw";

    public static final String COMMAND_DESCRIPTION =
        COMMAND_TEXT + " (draws a card; you can only draw a card if you can't play any cards in your hand)";

    private final User user;

    public DrawCardCommand(User user) {
        this.user = user;
    }

    @Override
    public void execute() throws InvalidUserOperation, GameException {
        user.drawCard();
    }

    public static Command of(User user) {
        return new DrawCardCommand(user);
    }

}
