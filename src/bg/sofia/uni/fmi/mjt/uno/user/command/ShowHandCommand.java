package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

import java.io.PrintWriter;

public class ShowHandCommand implements Command {

    public static final String COMMAND_TEXT = "show-hand";

    public static final String COMMAND_DESCRIPTION = COMMAND_TEXT + " (shows your hand)";

    private final User user;
    private final PrintWriter out;

    public ShowHandCommand(User user, PrintWriter out) {
        this.user = user;
        this.out = out;
    }

    @Override
    public void execute() throws InvalidUserOperation {
        out.println(user.showHand());
    }

    public static Command of(User user, PrintWriter out) {
        return new ShowHandCommand(user, out);
    }

}
