package bg.sofia.uni.fmi.mjt.uno.user;

import bg.sofia.uni.fmi.mjt.uno.database.account.Database;
import bg.sofia.uni.fmi.mjt.uno.database.account.Account;
import bg.sofia.uni.fmi.mjt.uno.database.account.exception.UsernameTakenException;
import bg.sofia.uni.fmi.mjt.uno.database.game.account.AccountDataDatabase;
import bg.sofia.uni.fmi.mjt.uno.database.game.account.data.AccountData;
import bg.sofia.uni.fmi.mjt.uno.game.Game;
import bg.sofia.uni.fmi.mjt.uno.game.GameStatus;
import bg.sofia.uni.fmi.mjt.uno.game.UnoGame;
import bg.sofia.uni.fmi.mjt.uno.game.card.Color;
import bg.sofia.uni.fmi.mjt.uno.game.deck.Deck;
import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.game.gamelogs.UnoGameLogs;
import bg.sofia.uni.fmi.mjt.uno.game.gamelogs.spectator.Spectator;
import bg.sofia.uni.fmi.mjt.uno.game.player.UnoPlayer;
import bg.sofia.uni.fmi.mjt.uno.database.game.GameDatabase;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

public class UnoUser implements User {

    private final Database database;
    private final GameDatabase gameDatabase;
    private final Spectator spectator;
    private final AccountDataDatabase accountDataDatabase;

    private Account account;

    public UnoUser(Database database, GameDatabase gameDatabase, Spectator spectator,
                   AccountDataDatabase accountDataDatabase) {
        this.database = database;
        this.gameDatabase = gameDatabase;
        this.spectator = spectator;
        this.accountDataDatabase = accountDataDatabase;
    }

    @Override
    public void register(String username, String password) throws InvalidUserOperation {
        try {
            database.addAccount(new Account(username, password));
        } catch (UsernameTakenException e) {
            throw new InvalidUserOperation("Username is already taken!", e);
        }
    }

    @Override
    public void login(String username, String password) throws InvalidUserOperation {
        if (isLoggedIn()) {
            throw new InvalidUserOperation("User is already logged in!");
        }

        Account temp = new Account(username, password);
        if (database.containsAccount(temp)) {
            account = temp;

            accountDataDatabase.add(account, new AccountData());
            if (accountDataDatabase.get(account).getCurrentGame() != null &&
                !accountDataDatabase.get(account).getPlayer().isHandEmpty()) {
                accountDataDatabase.get(account).getCurrentGame().getGameLogs().addSpectator(spectator);
            }

        } else if (!database.isFreeUsername(username)) {
            throw new InvalidUserOperation("Wrong password!");
        } else {
            throw new InvalidUserOperation("There is no user with that username!");
        }
    }

    @Override
    public void logout() throws InvalidUserOperation {
        assertIsLoggedIn();

        account = null;
    }

    @Override
    public String listGames(GameStatus status) throws InvalidUserOperation {
        assertIsLoggedIn();

        StringBuilder builder = new StringBuilder();
        for (Game game : gameDatabase.getGames(status)) {
            builder.append(game.toString()).append(System.lineSeparator());
        }
        return builder.toString();
    }

    @Override
    public void createGame(int playersCount, int gameId) throws InvalidUserOperation, GameException {
        assertIsLoggedIn();

        if (accountDataDatabase.get(account).getCreatedGame() != null) {
            throw new InvalidUserOperation("User has already created a game!");
        }
        
        synchronized (gameDatabase) {
            if (!gameDatabase.isFreeId(gameId)) {
                throw new InvalidUserOperation("Game id must be unique!");
            }

            Game game = new UnoGame(Deck.getTheClassicalUnoDeck(), gameId, new UnoGameLogs(), playersCount);
            accountDataDatabase.get(account).setCreatedGame(game);
            gameDatabase.addGame(game);
        }
    }

    @Override
    public void joinGame(int gameId, String displayName) throws InvalidUserOperation, GameException {
        assertIsLoggedIn();
        assertUserIsNotInAGame();

        Game temp = gameDatabase.getGame(gameId);
        if (temp == null) {
            throw new InvalidUserOperation("There is no game with that id!");
        }
        if (temp.getGameStatus() != GameStatus.AVAILABLE) {
            throw new InvalidUserOperation("The game is not available!");
        }
        accountDataDatabase.get(account)
            .setPlayer(new UnoPlayer(displayName == null ? account.username() : displayName));
        try {
            temp.addPlayer(accountDataDatabase.get(account).getPlayer());
        } catch (GameException e) {
            accountDataDatabase.get(account).setPlayer(null);
            throw e;
        }

        accountDataDatabase.get(account).setCurrentGame(temp);
        accountDataDatabase.get(account).getCurrentGame().getGameLogs().addSpectator(spectator);
    }

    @Override
    public void startGame() throws InvalidUserOperation, GameException {
        assertIsLoggedIn();

        if (accountDataDatabase.get(account).getCreatedGame() == null) {
            throw new InvalidUserOperation("User did not create a game!");
        }
        if (accountDataDatabase.get(account).getCurrentGame() != accountDataDatabase.get(account).getCreatedGame()) {
            throw new InvalidUserOperation("User is not in the game that he wants to start!");
        }

        accountDataDatabase.get(account).getCreatedGame().start();
        accountDataDatabase.get(account).setCreatedGame(null);
    }

    @Override
    public String showHand() throws InvalidUserOperation {
        assertIsLoggedIn();
        assertUserIsInLiveGame();

        if (accountDataDatabase.get(account).getPlayer().isHandEmpty()) {
            throw new InvalidUserOperation("User has already won!");
        }
        return accountDataDatabase.get(account).getPlayer().getHandToString();
    }

    @Override
    public String showLastCard() throws InvalidUserOperation, GameException {
        assertIsLoggedIn();
        assertUserIsInLiveGame();

        return accountDataDatabase.get(account).getCurrentGame().getGameLogs().getLastCard();
    }

    @Override
    public void acceptEffect() throws InvalidUserOperation, GameException {
        assertIsLoggedIn();
        assertUserIsInLiveGame();

        accountDataDatabase.get(account).getCurrentGame().acceptEffect(accountDataDatabase.get(account).getPlayer());
    }

    @Override
    public void drawCard() throws InvalidUserOperation, GameException {
        assertIsLoggedIn();
        assertUserIsInLiveGame();

        accountDataDatabase.get(account).getCurrentGame().drawCard(accountDataDatabase.get(account).getPlayer());
    }

    @Override
    public void playCard(int cardIndex) throws InvalidUserOperation, GameException {
        assertIsLoggedIn();
        assertUserIsInLiveGame();

        accountDataDatabase.get(account).getCurrentGame()
            .playRegularCard(accountDataDatabase.get(account).getPlayer(), cardIndex);
        removeSpectatorIfHandIsEmpty();
    }

    @Override
    public void playChooseColor(int cardIndex, Color newColor) throws InvalidUserOperation, GameException {
        assertIsLoggedIn();
        assertUserIsInLiveGame();

        accountDataDatabase.get(account).getCurrentGame()
            .playChooseColor(accountDataDatabase.get(account).getPlayer(), cardIndex, newColor);
        removeSpectatorIfHandIsEmpty();
    }

    @Override
    public void playPlusFour(int cardIndex, Color newColor) throws InvalidUserOperation, GameException {
        assertIsLoggedIn();
        assertUserIsInLiveGame();

        accountDataDatabase.get(account).getCurrentGame()
            .playPlusFour(accountDataDatabase.get(account).getPlayer(), cardIndex, newColor);
        removeSpectatorIfHandIsEmpty();
    }

    @Override
    public String showPlayedCards() throws InvalidUserOperation {
        assertIsLoggedIn();
        assertUserIsInLiveGame();

        return accountDataDatabase.get(account).getCurrentGame().getGameLogs().getPlayedCards();
    }

    @Override
    public void leaveGame() throws InvalidUserOperation, GameException {
        assertIsLoggedIn();
        assertUserIsInGame();

        accountDataDatabase.get(account).getCurrentGame().getGameLogs().removeSpectator(spectator);
        accountDataDatabase.get(account).getCurrentGame().leave(accountDataDatabase.get(account).getPlayer());

        accountDataDatabase.get(account).setCurrentGame(null);
        accountDataDatabase.get(account).setPlayer(null);
    }

    @Override
    public void spectateGame() throws InvalidUserOperation {
        assertIsLoggedIn();
        assertUserIsInLiveGame();
        if (!accountDataDatabase.get(account).getPlayer().isHandEmpty()) {
            throw new InvalidUserOperation("User cannot spectate while playing!");
        }

        accountDataDatabase.get(account).getCurrentGame().getGameLogs().addSpectator(spectator);
    }

    @Override
    public String getGameSummary(int gameId) throws InvalidUserOperation {
        assertIsLoggedIn();
        assertUserIsNotInAGame();

        Game temp = gameDatabase.getGame(gameId);
        if (temp == null) {
            throw new InvalidUserOperation("There is no game with that id!");
        }
        if (!temp.getGameStatus().equals(GameStatus.ENDED)) {
            throw new InvalidUserOperation("User can only get a summary on ended games!");
        }

        return temp.getGameLogs().getGameSummary();
    }

    boolean isLoggedIn() {
        return account != null;
    }

    private void assertIsLoggedIn() throws InvalidUserOperation {
        if (!isLoggedIn()) {
            throw new InvalidUserOperation("User is not logged in!");
        }
    }

    private void assertUserIsNotInAGame() throws InvalidUserOperation {
        if (accountDataDatabase.get(account).getCurrentGame() != null) {
            throw new InvalidUserOperation("User is already in a game!");
        }
    }

    private void assertUserIsInGame() throws InvalidUserOperation {
        if (accountDataDatabase.get(account).getCurrentGame() == null) {
            throw new InvalidUserOperation("User is not in a game!");
        }
    }

    private void assertUserIsInLiveGame() throws InvalidUserOperation {
        if (accountDataDatabase.get(account).getCurrentGame() == null ||
            accountDataDatabase.get(account).getCurrentGame().getGameStatus() != GameStatus.STARTED) {
            throw new InvalidUserOperation("User is not in a live game!");
        }
    }

    private void removeSpectatorIfHandIsEmpty() throws InvalidUserOperation {
        assertIsLoggedIn();
        assertUserIsInLiveGame();
        if (accountDataDatabase.get(account).getPlayer().isHandEmpty()) {
            accountDataDatabase.get(account).getCurrentGame().getGameLogs().removeSpectator(spectator);
        }
    }

}
