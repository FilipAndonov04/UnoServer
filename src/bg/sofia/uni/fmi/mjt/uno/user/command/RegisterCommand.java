package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

import java.util.List;

public class RegisterCommand implements Command {

    public static final String COMMAND_TEXT = "register";
    public static final String USERNAME_OPTION_TEXT = "--username=";
    public static final String PASSWORD_OPTION_TEXT = "--password=";

    public static final String COMMAND_DESCRIPTION =
        COMMAND_TEXT + ' ' + USERNAME_OPTION_TEXT + "<username> " + PASSWORD_OPTION_TEXT + "<password> " +
            "(registers an account)";

    private final User user;
    private final String username;
    private final String password;

    public RegisterCommand(User user, String username, String password) {
        this.user = user;
        this.username = username;
        this.password = password;
    }

    @Override
    public void execute() throws InvalidUserOperation {
        user.register(username, password);
    }

    public static Command of(User user, List<String> options) {
        String username = options.getFirst();
        String password = options.getLast();

        return new RegisterCommand(user, username, password);
    }

}
