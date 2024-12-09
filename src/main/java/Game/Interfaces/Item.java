package Game.Interfaces;


import Game.Enum.ItemType;

/**
 * Represents an item in the game
 */
public interface Item {

    public ItemType getType();

    public double getPoints();

    public String getName();

    public abstract void applyEffect(Hero hero);
}
