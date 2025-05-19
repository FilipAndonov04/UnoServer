package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

import java.util.List;

public class LoginCommand implements Command {

    public static final String COMMAND_TEXT = "login";
    public static final String USERNAME_OPTION_TEXT = "--username=";
    public static final String PASSWORD_OPTION_TEXT = "--password=";

    public static final String COMMAND_DESCRIPTION =
        COMMAND_TEXT + ' ' + USERNAME_OPTION_TEXT + "<username> " + PASSWORD_OPTION_TEXT + "<password> " +
            "(logs in an account)";

    private final User user;
    private final String username;
    private final String password;

    public LoginCommand(User user, String username, String password) {
        this.user = user;
        this.username = username;
        this.password = password;
    }

    @Override
    public void execute() throws InvalidUserOperation {
        user.login(username, password);
    }

    public static Command of(User user, List<String> options) {
        String username = options.getFirst();
        String password = options.getLast();

        return new LoginCommand(user, username, password);
    }

}
