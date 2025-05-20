package bg.sofia.uni.fmi.mjt.uno.game.player;

import bg.sofia.uni.fmi.mjt.uno.game.card.Card;

import java.util.List;
import java.util.Set;

public interface Player {

    /**
     * Returns the username of the player
     *
     * @return The username of the player
     */
    String getUsername();

    /**
     * Sets the username of a player
     *
     * @param username The new username
     * @throws IllegalArgumentException If the username is null
     */
    void setUsername(String username);

    /**
     * Adds a card to his hand
     *
     * @param card The card to be added
     * @throws IllegalArgumentException If the card is null
     */
    void takeCard(Card card);

    /**
     * Plays a card from his hand
     *
     * @param index The index of the card to be played
     * @return The card to be played
     * @throws IllegalArgumentException If the index is out of bound
     */
    Card playCard(int index);

    /**
     * Returns whether a player has any cards in his hand
     *
     * @return True if his hand is empty, false otherwise
     */
    boolean isHandEmpty();

    /**
     * Returns the number of the cards in the hand
     *
     * @return The count of cards in the hand
     */
    int getCardsInHandCount();

    /**
     * Returns the cards in the player's hans
     *
     * @return List of the player's cards
     */
    List<Card> getHand();

    /**
     * Returns indexes of cards that can be played given a predicate card
     *
     * @param card The predicate card
     * @return Set of indexed of all the cards that can be played after the given card
     * @throws IllegalArgumentException If the card is null
     */
    Set<Integer> getPlayableCardsIndexes(Card card);

    /**
     * Returns text representation of the player's hand
     *
     * @return The text representation
     */
    String getHandToString();

}
