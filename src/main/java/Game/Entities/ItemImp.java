package Game.Entities;

import Collections.Enum.ItemType;
import Game.Interfaces.Hero;
import Game.Interfaces.Item;

/**
 * Represents an item in the game
 */
public abstract class ItemImp implements Item {

    private String name;
    private double points;

    public ItemImp(String name, double points) {
        this.name = name;
        this.points = points;
    }


    public String getName() {
        return this.name;
    }


    public double getPoints() {
        return this.points;
    }


    public abstract void applyEffect(Hero hero);
}
