package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

import java.io.PrintWriter;

public class ShowLastCardCommand implements Command {

    public static final String COMMAND_TEXT = "show-last-card";

    public static final String COMMAND_DESCRIPTION = COMMAND_TEXT + " (shows last played card)";

    private final User user;
    private final PrintWriter out;

    public ShowLastCardCommand(User user, PrintWriter out) {
        this.user = user;
        this.out = out;
    }

    @Override
    public void execute() throws InvalidUserOperation, GameException {
        out.println(user.showLastCard());
    }

    public static Command of(User user, PrintWriter out) {
        return new ShowLastCardCommand(user, out);
    }

}
