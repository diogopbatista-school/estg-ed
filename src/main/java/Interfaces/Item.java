package Interfaces;

import Game.Enumerations.ItemType;

/**
 * Represents an item in the game
 */
public interface Item {
    /**
     * Sets the name of the item
     * @return the name of the item
     */
    public String getNameItem();

    /**
     * Getter of the room name the item is in
     * @return The name of the room the item is in
     */
    public String getRoomName();

    /**
     * Getter of the points the item gives
     * @return The points the item gives
     */
    public int getPoints();

    /**
     * Getter of the type of the item
     * @return The type of the item
     */
    public ItemType getType();
}
