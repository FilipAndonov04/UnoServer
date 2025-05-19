package bg.sofia.uni.fmi.mjt.uno.database.game.account.data;

import bg.sofia.uni.fmi.mjt.uno.game.Game;
import bg.sofia.uni.fmi.mjt.uno.game.player.Player;

public class AccountData {

    private Player player;
    private Game currentGame;
    private Game createdGame;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public Game getCreatedGame() {
        return createdGame;
    }

    public void setCreatedGame(Game createdGame) {
        this.createdGame = createdGame;
    }

}
