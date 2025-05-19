package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.game.card.Color;
import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserInput;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

import java.util.List;

public class PlayPlusFourCommand implements Command {

    public static final String COMMAND_TEXT = "play-plus-four";
    public static final String CARD_ID_OPTION_TEXT = "--card-id=";
    public static final String COLOR_OPTION_TEXT = "--color=";

    public static final String COMMAND_DESCRIPTION =
        COMMAND_TEXT + ' ' + CARD_ID_OPTION_TEXT + "<card-id> " + COLOR_OPTION_TEXT + "<red/green/blue/yellow> " +
            "(plays a plus-four card from your hand; if a color is not chosen, it is the same as last card's)";

    private final User user;
    private final int cardId;
    private final Color color;

    public PlayPlusFourCommand(User user, int cardId, Color color) {
        this.user = user;
        this.cardId = cardId;
        this.color = color;
    }

    @Override
    public void execute() throws InvalidUserOperation, GameException {
        user.playPlusFour(cardId, color);
    }

    public static Command of(User user, String cardIdString) throws InvalidUserInput {
        int cardId;
        try {
            cardId = Integer.parseInt(cardIdString);
        } catch (NumberFormatException e) {
            throw new InvalidUserInput("Invalid option content: card id must be a number!");
        }
        return new PlayPlusFourCommand(user, cardId, null);
    }

    public static Command of(User user, List<String> options) throws InvalidUserInput {
        String cardIdString = options.getFirst();
        String colorString = options.getLast();

        int cardId;
        try {
            cardId = Integer.parseInt(cardIdString);
        } catch (NumberFormatException e) {
            throw new InvalidUserInput("Invalid option content: card id must be a number!");
        }
        return new PlayPlusFourCommand(user, cardId, getColorFromString(colorString));
    }

    private static Color getColorFromString(String string) throws InvalidUserInput {
        return switch (string) {
            case "red" -> Color.RED;
            case "green" -> Color.GREEN;
            case "yellow" -> Color.YELLOW;
            case "blue" -> Color.BLUE;
            default -> throw new InvalidUserInput("Invalid option content: invalid color!");
        };
    }

}
