package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

public class LogoutCommand implements Command {

    public static final String COMMAND_TEXT = "logout";

    public static final String COMMAND_DESCRIPTION = COMMAND_TEXT + " (logs out of an account)";

    private final User user;

    public LogoutCommand(User user) {
        this.user = user;
    }

    @Override
    public void execute() throws InvalidUserOperation {
        user.logout();
    }

    public static Command of(User user) {
        return new LogoutCommand(user);
    }

}
