package bg.sofia.uni.fmi.mjt.uno.game;

import bg.sofia.uni.fmi.mjt.uno.game.card.Color;
import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.game.gamelogs.GameLogs;
import bg.sofia.uni.fmi.mjt.uno.game.player.Player;

import java.util.List;

public interface Game {

    int MAX_PLAYERS = 10;

    int getId();

    GameLogs getGameLogs();

    GameStatus getGameStatus();

    List<Player> getPlayers();

    void start() throws GameException;

    void addPlayer(Player player) throws GameException;

    void playRegularCard(Player player, int cardIndex) throws GameException;

    void playChooseColor(Player player, int cardIndex, Color newColor) throws GameException;

    void playPlusFour(Player player, int cardIndex, Color newColor) throws GameException;

    void drawCard(Player player) throws GameException;

    void acceptEffect(Player player) throws GameException;

    void leave(Player player) throws GameException;
    
}
