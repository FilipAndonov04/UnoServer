package bg.sofia.uni.fmi.mjt.uno.database.game;

import bg.sofia.uni.fmi.mjt.uno.game.Game;
import bg.sofia.uni.fmi.mjt.uno.game.GameStatus;

import java.util.Collection;

public interface GameDatabase {

    Collection<Game> getGames(GameStatus status);

    Collection<Game> getAllGames();

    void addGame(Game game);

    boolean isFreeId(int id);

    Game getGame(int id);

}
