package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

import java.io.PrintWriter;

public class ShowPlayedCardsCommand implements Command {

    public static final String COMMAND_TEXT = "show-played-cards";

    public static final String COMMAND_DESCRIPTION = COMMAND_TEXT + " (shows all played cards in the game)";

    private final User user;
    private final PrintWriter out;

    public ShowPlayedCardsCommand(User user, PrintWriter out) {
        this.user = user;
        this.out = out;
    }

    @Override
    public void execute() throws InvalidUserOperation {
        out.println(user.showPlayedCards());
    }

    public static Command of(User user, PrintWriter out) {
        return new ShowPlayedCardsCommand(user, out);
    }

}
