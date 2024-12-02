package Game.Entities;

import Game.Enumerations.ItemType;
import Game.Interfaces.Item;

/**
 * Represents an item in the game
 */
public class ItemImp implements Item {

    /**
     * The points the item gives
     */
    private int points;

    /**
     * The type of the item
     */
    private ItemType type;

    /**
     * Constructor for the ItemImp class
     * @param points The points the item gives
     * @param type The type of the item
     */
    public ItemImp(int points, String type) { // mudar sem enum
        this.points = points;
        this.type = ItemType.fromString(type);
    }

    /**
     * Getter for the name of the item
     * @return The name of the item
     */
    @Override
    public String getNameItem() {
        return this.type.toString();
    }


    /**
     * Getter for the points the item gives
     * @return The points the item gives
     */
    @Override
    public int getPoints() {
        return points;
    }

    /**
     * Getter for the type of the item
     * @return The type of the item
     */
    @Override
    public ItemType getType() {
        return type;
    }
}
