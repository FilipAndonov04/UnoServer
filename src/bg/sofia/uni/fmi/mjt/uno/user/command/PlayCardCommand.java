package bg.sofia.uni.fmi.mjt.uno.user.command;

import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserInput;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

public class PlayCardCommand implements Command {

    public static final String COMMAND_TEXT = "play";
    public static final String CARD_ID_OPTION_TEXT = "--card-id=";

    public static final String COMMAND_DESCRIPTION =
        COMMAND_TEXT + ' ' + CARD_ID_OPTION_TEXT + "<card-id> " + "(plays a non-wild card from the your hand)";

    private final User user;
    private final int cardId;

    public PlayCardCommand(User user, int cardId) {
        this.user = user;
        this.cardId = cardId;
    }

    @Override
    public void execute() throws InvalidUserOperation, GameException {
        user.playCard(cardId);
    }

    public static Command of(User user, String cardIdString) throws InvalidUserInput {
        int cardId;
        try {
            cardId = Integer.parseInt(cardIdString);
        } catch (NumberFormatException e) {
            throw new InvalidUserInput("Invalid option content: card id must be a number!");
        }
        return new PlayCardCommand(user, cardId);
    }

}
