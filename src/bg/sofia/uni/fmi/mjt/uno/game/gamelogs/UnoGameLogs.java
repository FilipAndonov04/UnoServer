package bg.sofia.uni.fmi.mjt.uno.game.gamelogs;

import bg.sofia.uni.fmi.mjt.uno.game.card.Card;
import bg.sofia.uni.fmi.mjt.uno.game.gamelogs.spectator.Spectator;
import bg.sofia.uni.fmi.mjt.uno.game.player.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UnoGameLogs implements GameLogs {

    private final Set<Spectator> spectators = new HashSet<>();

    private final List<Player> scoreboard = new ArrayList<>();
    private final List<Player> leavers = new ArrayList<>();

    private final List<String> allPlayedNonRevealedMoves = new ArrayList<>();
    private final List<String> allPlayedRevealedMoves = new ArrayList<>();
    private final StringBuilder playedCards = new StringBuilder();
    private String lastCard;

    @Override
    public synchronized String getLastCard() {
        return lastCard;
    }

    @Override
    public synchronized String getPlayedCards() {
        return playedCards.toString();
    }

    @Override
    public synchronized String getScoreboard() {
        return "Scoreboard:" +
            System.lineSeparator() +
            playerListToString(scoreboard, 1) +
            playerListToString(leavers, scoreboard.size() + 1);
    }

    @Override
    public synchronized String getGameSummary() {
        StringBuilder builder = new StringBuilder();
        for (String string : allPlayedRevealedMoves) {
            builder.append(string);
        }
        return builder.toString();
    }

    @Override
    public synchronized void logGameStart(List<Player> players, Card card, int currentPlayerIndex,
                                          int toAddToGetToNextPlayer) {
        updateLastCard(card);

        addNextNonRevealedMove(players, currentPlayerIndex, toAddToGetToNextPlayer,
            "Game has started!");
        addNextRevealedMove(players, currentPlayerIndex, toAddToGetToNextPlayer,
            "Game has started!");

        notifyAllSubscribers(allPlayedNonRevealedMoves.getLast());
    }

    @Override
    public synchronized void logPlayerLeaving(Player player, boolean isGameOver) {
        if (isGameOver) {
            scoreboard.add(player);
        } else {
            leavers.add(player);
            notifyAllSubscribers(player.getUsername() + " has left the game!");
        }
    }

    @Override
    public synchronized void logPlayerPlayCard(List<Player> players, Card card, int currentPlayerIndex,
                                               int toAddToGetToNextPlayer,
                                               Player lastPlayer) {
        updateLastCard(card);

        addNextNonRevealedMove(players, currentPlayerIndex, toAddToGetToNextPlayer,
            lastPlayer.getUsername() + " played " + card);
        addNextRevealedMove(players, currentPlayerIndex, toAddToGetToNextPlayer,
            lastPlayer.getUsername() + " played " + card);

        notifyAllSubscribers(allPlayedNonRevealedMoves.getLast());
    }

    @Override
    public synchronized void logPlayerDraw(List<Player> players, int currentPlayerIndex, int toAddToGetToNextPlayer,
                                           int cardCount, Player lastPlayer) {
        addNextNonRevealedMove(players, currentPlayerIndex, toAddToGetToNextPlayer,
            lastPlayer.getUsername() + " drew " + cardCount + " cards");
        addNextRevealedMove(players, currentPlayerIndex, toAddToGetToNextPlayer,
            lastPlayer.getUsername() + " drew " + cardCount + " cards");

        notifyAllSubscribers(allPlayedNonRevealedMoves.getLast());
    }

    @Override
    public synchronized void logGameEnd() {
        StringBuilder message = new StringBuilder("Game has ended!");
        message.append(System.lineSeparator())
            .append(getScoreboard())
            .append(System.lineSeparator());
        notifyAllSubscribers(message.toString());

        allPlayedRevealedMoves.add(getScoreboard());
    }

    @Override
    public synchronized void addSpectator(Spectator spectator) {
        spectators.add(spectator);
    }

    @Override
    public synchronized void removeSpectator(Spectator spectator) {
        spectators.remove(spectator);
    }

    private void notifyAllSubscribers(String event) {
        for (Spectator spectator : spectators) {
            spectator.onEvent(event);
        }
    }

    private String playerListToString(List<Player> playerList, int index) {
        StringBuilder builder = new StringBuilder();
        for (Player player : playerList) {
            builder.append(index++).append(". ").append(player.getUsername()).append(System.lineSeparator());
        }
        return builder.toString();
    }

    private void addNextNonRevealedMove(List<Player> players, int currentPlayerIndex,
                                        int toAddToGetToNextPlayer, String headerMessage) {
        StringBuilder builder = new StringBuilder(headerMessage);
        builder.append(System.lineSeparator())
            .append(System.lineSeparator())
            .append(getOrderMessage(players, currentPlayerIndex, toAddToGetToNextPlayer))
            .append(System.lineSeparator())
            .append(System.lineSeparator())
            .append(getLastCardMessage())
            .append(System.lineSeparator())
            .append(System.lineSeparator())
            .append(getPlayersCardsCount(players))
            .append(System.lineSeparator());
        allPlayedNonRevealedMoves.add(builder.toString());
    }

    private void addNextRevealedMove(List<Player> players, int currentPlayerIndex,
                                     int toAddToGetToNextPlayer, String headerMessage) {
        StringBuilder builder = new StringBuilder(headerMessage);
        builder.append(System.lineSeparator())
            .append(System.lineSeparator())
            .append(getOrderMessage(players, currentPlayerIndex, toAddToGetToNextPlayer))
            .append(System.lineSeparator())
            .append(System.lineSeparator())
            .append(getLastCardMessage())
            .append(System.lineSeparator())
            .append(System.lineSeparator())
            .append(getPlayersRevealedCards(players))
            .append(System.lineSeparator());
        allPlayedRevealedMoves.add(builder.toString());
    }

    private String getPlayersRevealedCards(List<Player> players) {
        StringBuilder builder = new StringBuilder();
        for (Player player : players) {
            builder.append("Player ")
                .append(player.getUsername())
                .append(" has ")
                .append(player.getCardsInHandCount())
                .append(" cards : ")
                .append(player.getHandToString())
                .append(System.lineSeparator());
        }
        return builder.toString();
    }

    private String getPlayersCardsCount(List<Player> players) {
        StringBuilder builder = new StringBuilder();
        for (Player player : players) {
            builder.append("Player ")
                .append(player.getUsername())
                .append(" has ")
                .append(player.getCardsInHandCount())
                .append(" cards")
                .append(System.lineSeparator());
        }
        return builder.toString();
    }

    private void updateLastCard(Card card) {
        lastCard = card.toString();
        playedCards.insert(0, ' ').insert(0, card);
    }

    private String getLastCardMessage() {
        return "Last card: " + getLastCard();
    }

    private String getOrderMessage(List<Player> playerList, int current, int addition) {
        return getCurrentPlayerOnTurn(playerList, current) + System.lineSeparator() +
            getPlayersTurns(playerList, current, addition);
    }

    private String getCurrentPlayerOnTurn(List<Player> playerList, int current) {
        return "It is turn to player: " + playerList.get(current).getUsername();
    }

    private String getPlayersTurns(List<Player> playerList, int current, int addition) {
        StringBuilder builder = new StringBuilder("Player order: ");
        builder.append(playerList.get(current).getUsername());
        int index = getInBound(current + addition, playerList.size());
        while (index != current) {
            if (!playerList.get(index).isHandEmpty()) {
                builder.append(", ").append(playerList.get(index).getUsername());
            }
            index = getInBound(index + addition, playerList.size());
        }
        return builder.toString();
    }

    private int getInBound(int value, int size) {
        if (value == -1) {
            return size - 1;
        }
        if (value == size) {
            return 0;
        }
        return value;
    }

}
