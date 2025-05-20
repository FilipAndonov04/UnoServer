package bg.sofia.uni.fmi.mjt.uno.game;

import bg.sofia.uni.fmi.mjt.uno.game.card.Color;
import bg.sofia.uni.fmi.mjt.uno.game.card.Value;
import bg.sofia.uni.fmi.mjt.uno.game.deck.Deck;
import bg.sofia.uni.fmi.mjt.uno.game.deck.UnoDeck;
import bg.sofia.uni.fmi.mjt.uno.game.card.Card;
import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.game.gamelogs.GameLogs;
import bg.sofia.uni.fmi.mjt.uno.game.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class UnoGame implements Game {

    private static final int STARTING_CARDS_PER_PLAYER = 7;

    private static final int PLAYER_COUNT_WHEN_REVERSE_ACTS_LIKE_SKIP = 2;
    private static final int PLUS_TWO_ADDED_CARDS = 2;
    private static final int PLUS_FOUR_ADDED_CARDS = 4;

    private static final int MIN_PLAYER_COUNT_TO_START = 2;

    private static final String CANNOT_MAKE_ILLEGAL_MOVE_MESSAGE = "Player cannot make an illegal move!";

    private final GameLogs gameLogs;
    private final int id;
    private final int maxPlayerCount;

    private GameStatus gameStatus = GameStatus.AVAILABLE;

    private final List<Player> players = new ArrayList<>();
    private int playersLeft;
    private int currentPlayerIndex = 0;
    private int toAddToNextPlayer = 1;

    private Deck drawingDeck;
    private Deck playedCards = new UnoDeck();

    private Card currentCard;
    private Card facadeCard;

    private boolean hasEffectToAccept = false;

    public UnoGame(Deck deck, int id, GameLogs gameLogs, int maxPlayerCount) throws GameException {
        if (maxPlayerCount > MAX_PLAYERS) {
            throw new GameException("User cannot create a game with more than " +
                Game.MAX_PLAYERS + " players!");
        }

        drawingDeck = deck;
        this.id = id;
        this.gameLogs = gameLogs;
        this.maxPlayerCount = maxPlayerCount;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public GameLogs getGameLogs() {
        return gameLogs;
    }

    @Override
    public synchronized GameStatus getGameStatus() {
        return gameStatus;
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public synchronized void start() throws GameException {
        if (!gameStatus.equals(GameStatus.AVAILABLE)) {
            throw new GameException("Only available game can start!");
        }
        if (players.size() < MIN_PLAYER_COUNT_TO_START) {
            throw new GameException("The game cannot start with less than two players!");
        }

        gameStatus = GameStatus.STARTED;
        prepareGame();
        gameLogs.logGameStart(players, facadeCard, currentPlayerIndex, toAddToNextPlayer);
    }

    @Override
    public synchronized void addPlayer(Player player) throws GameException {
        if (players.size() == maxPlayerCount) {
            throw new GameException("Game is full!");
        }
        if (players.stream()
            .anyMatch(p -> p.getUsername().equals(player.getUsername()))) {
            throw new GameException("Player must have unique display name!");
        }

        players.add(player);
    }

    @Override
    public synchronized void playRegularCard(Player player, int cardIndex) throws GameException {
        assertIsNotEnded();
        assertIsInTheGame(player);
        assertIsPLayersTurn(player);
        assertHasNoEffectsToAccept();
        if (!player.getPlayableCardsIndexes(facadeCard).contains(cardIndex)) {
            throw new GameException(CANNOT_MAKE_ILLEGAL_MOVE_MESSAGE);
        }
        if (player.getHand().get(cardIndex).isWild()) {
            throw new GameException("Cannot play Wild card!");
        }

        updateCurrentCard(player.playCard(cardIndex), null);
        gameLogs.logPlayerPlayCard(players, facadeCard, currentPlayerIndex, toAddToNextPlayer, player);
        checkIfPlayerHasWon(player);
    }

    @Override
    public synchronized void playChooseColor(Player player, int cardIndex, Color newColor) throws GameException {
        playWildCard(player, cardIndex, newColor, Value.CHOOSE_COLOR, "Selected card is not choose color!");
        gameLogs.logPlayerPlayCard(players, facadeCard, currentPlayerIndex, toAddToNextPlayer, player);
        checkIfPlayerHasWon(player);
    }

    @Override
    public synchronized void playPlusFour(Player player, int cardIndex, Color newColor) throws GameException {
        playWildCard(player, cardIndex, newColor, Value.PLUS_FOUR, "Selected card is not plus four!");
        gameLogs.logPlayerPlayCard(players, facadeCard, currentPlayerIndex, toAddToNextPlayer, player);
        checkIfPlayerHasWon(player);
    }

    @Override
    public synchronized void drawCard(Player player) throws GameException {
        assertIsNotEnded();
        assertIsInTheGame(player);
        assertIsPLayersTurn(player);
        assertHasNoEffectsToAccept();
        if (canPlayerPlay()) {
            throw new GameException("Player cannot draw when he can play a card!");
        }

        givePlayerCards(player, 1);
        goToNextPlayer();
        gameLogs.logPlayerDraw(players, currentPlayerIndex, toAddToNextPlayer, 1, player);
    }

    @Override
    public synchronized void acceptEffect(Player player) throws GameException {
        assertIsNotEnded();
        assertIsInTheGame(player);
        assertIsPLayersTurn(player);
        if (!hasEffectsToAccept()) {
            throw new GameException("Player does not have effects to accept!");
        }

        hasEffectToAccept = false;
        givePlayerCards(player, getEffectsToAcceptCardsCount());
        goToNextPlayer();
        gameLogs.logPlayerDraw(players, currentPlayerIndex, toAddToNextPlayer, getEffectsToAcceptCardsCount(), player);
    }

    @Override
    public synchronized void leave(Player player) throws GameException {
        assertIsInTheGame(player);
        if (gameStatus.equals(GameStatus.AVAILABLE)) {
            players.remove(player);
            return;
        }
        if (gameStatus.equals(GameStatus.ENDED) || player.isHandEmpty()) {
            return;
        }

        gameLogs.logPlayerLeaving(player, false);
        while (!player.isHandEmpty()) {
            playedCards.addCard(player.playCard(0));
        }
        playersLeft--;
        if (playersLeft == 1) {
            goToNextPlayer();
            gameLogs.logPlayerLeaving(getCurrentPlayer(), true);
            gameLogs.logGameEnd();
            gameStatus = GameStatus.ENDED;
            return;
        }
        if (isPlayersTurn(player)) {
            goToNextPlayer();
        }
    }

    @Override
    public String toString() {
        return "Game : [id = " + id + ", max number of players = " + maxPlayerCount + ", status = " + gameStatus + "]";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UnoGame unoGame = (UnoGame) object;
        return id == unoGame.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    private void prepareGame() {
        Collections.shuffle(players);

        drawingDeck.shuffle();
        for (Player player : players) {
            givePlayerCards(player, STARTING_CARDS_PER_PLAYER);
        }

        chooseStartingCard();
    }

    private void chooseStartingCard() {
        currentCard = drawingDeck.drawCard();
        boolean shouldReshuffle = false;

        EnumSet<Value> validValue = EnumSet.range(Value.ZERO, Value.NINE);
        while (!validValue.contains(currentCard.value())) {
            shouldReshuffle = true;
            drawingDeck.addCard(currentCard);
            currentCard = drawingDeck.drawCard();
        }
        if (shouldReshuffle) {
            drawingDeck.shuffle();
        }

        facadeCard = currentCard;
        playersLeft = players.size();
    }

    private void givePlayerCards(Player player, int count) {
        for (int i = 0; i < count; i++) {
            reloadDeckIfNeeded();
            player.takeCard(drawingDeck.drawCard());
        }
    }

    private void reloadDeckIfNeeded() {
        if (drawingDeck.isEmpty()) {
            reloadDeck();
        }
    }

    private void reloadDeck() {
        Deck temp = drawingDeck;
        drawingDeck = playedCards;
        playedCards = temp;

        drawingDeck.shuffle();
    }

    private Player getCurrentPlayer() throws GameException {
        assertIsNotEnded();
        return players.get(currentPlayerIndex);
    }

    private void reverseDirection() {
        toAddToNextPlayer *= -1;
    }

    private void goToNextPlayer() {
        if (gameStatus.equals(GameStatus.ENDED)) {
            return;
        }

        currentPlayerIndex += toAddToNextPlayer;
        if (currentPlayerIndex == -1) {
            currentPlayerIndex = players.size() - 1;
        } else if (currentPlayerIndex == players.size()) {
            currentPlayerIndex = 0;
        }
        if (players.get(currentPlayerIndex).isHandEmpty()) {
            goToNextPlayer();
        }
    }

    private void checkIfPlayerHasWon(Player player) {
        if (player.isHandEmpty()) {
            gameLogs.logPlayerLeaving(player, true);
            playersLeft--;
            if (playersLeft == 1) {
                gameLogs.logPlayerLeaving(players.get(currentPlayerIndex), true);
                gameLogs.logGameEnd();
                gameStatus = GameStatus.ENDED;
            }
        }
    }

    private boolean isBlocked() {
        return currentCard.value().equals(Value.REVERSE) &&
            playersLeft == PLAYER_COUNT_WHEN_REVERSE_ACTS_LIKE_SKIP ||
            currentCard.value().equals(Value.SKIP);
    }

    private boolean isPlayersTurn(Player player) throws GameException {
        return player.equals(getCurrentPlayer());
    }

    private void assertIsPLayersTurn(Player player) throws GameException {
        if (!isPlayersTurn(player)) {
            throw new GameException("It is not player's turn!");
        }
    }

    private boolean hasEffectsToAccept() {
        return hasEffectToAccept;
    }

    private void assertHasNoEffectsToAccept() throws GameException {
        if (hasEffectsToAccept()) {
            throw new GameException("Player has effects to accept and must accept them!");
        }
    }

    private int getEffectsToAcceptCardsCount() {
        if (currentCard.value().equals(Value.PLUS_FOUR)) {
            return PLUS_FOUR_ADDED_CARDS;
        }
        if (currentCard.value().equals(Value.PLUS_TWO)) {
            return PLUS_TWO_ADDED_CARDS;
        }
        return 0;
    }

    private boolean canPlayerPlay() throws GameException {
        return !getCurrentPlayer().getPlayableCardsIndexes(currentCard).isEmpty();
    }

    private void updateCurrentCard(Card card, Color color) {
        playedCards.addCard(currentCard);
        currentCard = card;
        facadeCard = color == null ? card : new Card(color, card.value());

        if (currentCard.value().equals(Value.PLUS_FOUR) ||
            currentCard.value().equals(Value.PLUS_TWO)) {
            hasEffectToAccept = true;
        } else if (card.value().equals(Value.REVERSE)) {
            reverseDirection();
        }
        goToNextPlayer();
        if (isBlocked()) {
            goToNextPlayer();
        }
    }

    private void playWildCard(Player player, int cardIndex, Color newColor, Value value, String wildMessage)
        throws GameException {
        assertIsNotEnded();
        assertIsInTheGame(player);
        assertIsPLayersTurn(player);
        assertHasNoEffectsToAccept();
        try {
            if (!player.getHand().get(cardIndex).equals(new Card(Color.WILD, value))) {
                throw new GameException(wildMessage);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new GameException(CANNOT_MAKE_ILLEGAL_MOVE_MESSAGE);
        }

        if (newColor == null) {
            newColor = facadeCard.color();
        }
        updateCurrentCard(player.playCard(cardIndex), newColor);
    }

    private void assertIsNotEnded() throws GameException {
        if (gameStatus.equals(GameStatus.ENDED)) {
            throw new GameException("Game is over! Cannot play!");
        }
    }

    private void assertIsInTheGame(Player player) throws GameException {
        if (!players.contains(player)) {
            throw new GameException("Player is not in the game!");
        }
    }

}
