package bg.sofia.uni.fmi.mjt.uno.user.command;

import java.io.PrintWriter;

public class HelpCommand implements Command {

    public static final String COMMAND_TEXT = "help";

    private static final String LINE_START_SIGN = "(*) ";
    private static final String END_COMMANDS_LINE = "-----------------------------------------------------------------";
    private static final String NEW_LINE_SIGH = System.lineSeparator();

    private final PrintWriter out;

    public HelpCommand(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void execute() {
        StringBuilder builder = new StringBuilder("List ot commands:").append(NEW_LINE_SIGH);

        builder.append(END_COMMANDS_LINE).append(NEW_LINE_SIGH);
        builder.append(LINE_START_SIGN).append("Press Enter to refresh!").append(NEW_LINE_SIGH);
        builder.append(LINE_START_SIGN).append(RegisterCommand.COMMAND_DESCRIPTION).append(NEW_LINE_SIGH);
        builder.append(LINE_START_SIGN).append(LoginCommand.COMMAND_DESCRIPTION).append(NEW_LINE_SIGH);
        builder.append(LINE_START_SIGN).append(LogoutCommand.COMMAND_DESCRIPTION).append(NEW_LINE_SIGH);
        builder.append(LINE_START_SIGN).append(ListGamesCommand.COMMAND_DESCRIPTION).append(NEW_LINE_SIGH);
        builder.append(LINE_START_SIGN).append(CreateGameCommand.COMMAND_DESCRIPTION).append(NEW_LINE_SIGH);
        builder.append(LINE_START_SIGN).append(JoinGameCommand.COMMAND_DESCRIPTION).append(NEW_LINE_SIGH);
        builder.append(LINE_START_SIGN).append(StartGameCommand.COMMAND_DESCRIPTION).append(NEW_LINE_SIGH);
        builder.append(LINE_START_SIGN).append(ShowHandCommand.COMMAND_DESCRIPTION).append(NEW_LINE_SIGH);
        builder.append(LINE_START_SIGN).append(ShowLastCardCommand.COMMAND_DESCRIPTION).append(NEW_LINE_SIGH);
        builder.append(LINE_START_SIGN).append(ShowPlayedCardsCommand.COMMAND_DESCRIPTION).append(NEW_LINE_SIGH);
        builder.append(LINE_START_SIGN).append(PlayCardCommand.COMMAND_DESCRIPTION).append(NEW_LINE_SIGH);
        builder.append(LINE_START_SIGN).append(PlayChooseColorCommand.COMMAND_DESCRIPTION).append(NEW_LINE_SIGH);
        builder.append(LINE_START_SIGN).append(PlayPlusFourCommand.COMMAND_DESCRIPTION).append(NEW_LINE_SIGH);
        builder.append(LINE_START_SIGN).append(DrawCardCommand.COMMAND_DESCRIPTION).append(NEW_LINE_SIGH);
        builder.append(LINE_START_SIGN).append(AcceptEffectCommand.COMMAND_DESCRIPTION).append(NEW_LINE_SIGH);
        builder.append(LINE_START_SIGN).append(LeaveGameCommand.COMMAND_DESCRIPTION).append(NEW_LINE_SIGH);
        builder.append(LINE_START_SIGN).append(SpectateGameCommand.COMMAND_DESCRIPTION).append(NEW_LINE_SIGH);
        builder.append(LINE_START_SIGN).append(SummaryCommand.COMMAND_DESCRIPTION).append(NEW_LINE_SIGH);
        builder.append(END_COMMANDS_LINE).append(NEW_LINE_SIGH);

        out.println(builder);
    }

    public static Command of(PrintWriter out) {
        return new HelpCommand(out);
    }

}
