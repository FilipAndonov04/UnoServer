package bg.sofia.uni.fmi.mjt.uno.database.game;

import bg.sofia.uni.fmi.mjt.uno.game.Game;
import bg.sofia.uni.fmi.mjt.uno.game.GameStatus;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UnoGameDatabase implements GameDatabase {

    private final Map<Integer, Game> games = new HashMap<>();

    @Override
    public Collection<Game> getGames(GameStatus status) {
        if (status == null) {
            return getAllGames();
        }

        return games.values().stream()
            .filter(e -> e.getGameStatus().equals(status))
            .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Collection<Game> getAllGames() {
        return Collections.unmodifiableCollection(games.values());
    }

    @Override
    public synchronized void addGame(Game game) {
        games.putIfAbsent(game.getId(), game);
    }

    @Override
    public synchronized boolean isFreeId(int id) {
        return !games.containsKey(id);
    }

    @Override
    public synchronized Game getGame(int id) {
        return games.get(id);
    }

}
