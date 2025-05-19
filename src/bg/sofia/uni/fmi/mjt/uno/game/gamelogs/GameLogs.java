package bg.sofia.uni.fmi.mjt.uno.game.gamelogs;

import bg.sofia.uni.fmi.mjt.uno.game.card.Card;
import bg.sofia.uni.fmi.mjt.uno.game.gamelogs.spectator.Spectator;
import bg.sofia.uni.fmi.mjt.uno.game.player.Player;

import java.util.List;

public interface GameLogs {

    String getLastCard();

    String getPlayedCards();

    String getScoreboard();

    String getGameSummary();

    void logGameStart(List<Player> players, Card card, int currentPlayerIndex, int toAddToGetToNextPlayer);

    void logPlayerLeaving(Player player, boolean isGameOver);

    void logPlayerPlayCard(List<Player> players, Card card, int currentPlayerIndex, int toAddToGetToNextPlayer,
                           Player lastPlayer);

    void logPlayerDraw(List<Player> players, int currentPlayerIndex, int toAddToGetToNextPlayer,
                       int cardCount, Player lastPlayer);

    void logGameEnd();

    void addSpectator(Spectator spectator);

    void removeSpectator(Spectator spectator);

}
