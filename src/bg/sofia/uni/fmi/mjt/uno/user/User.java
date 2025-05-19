package bg.sofia.uni.fmi.mjt.uno.user;

import bg.sofia.uni.fmi.mjt.uno.game.GameStatus;
import bg.sofia.uni.fmi.mjt.uno.game.card.Color;
import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

public interface User {

    void register(String username, String password) throws InvalidUserOperation;

    void login(String username, String password) throws InvalidUserOperation;

    void logout() throws InvalidUserOperation;

    String listGames(GameStatus status) throws InvalidUserOperation;

    void createGame(int playersCount, int gameId) throws InvalidUserOperation, GameException;

    void joinGame(int gameId, String displayName) throws InvalidUserOperation, GameException;

    void startGame() throws InvalidUserOperation, GameException;

    String showHand() throws InvalidUserOperation;

    String showLastCard() throws InvalidUserOperation, GameException;

    void acceptEffect() throws InvalidUserOperation, GameException;

    void drawCard() throws InvalidUserOperation, GameException;

    void playCard(int cardIndex) throws InvalidUserOperation, GameException;

    void playChooseColor(int cardIndex, Color newColor) throws InvalidUserOperation, GameException;

    void playPlusFour(int cardIndex, Color newColor) throws InvalidUserOperation, GameException;

    String showPlayedCards() throws InvalidUserOperation;

    void leaveGame() throws InvalidUserOperation, GameException;

    void spectateGame() throws InvalidUserOperation;

    String getGameSummary(int gameId) throws InvalidUserOperation;

}
