package Game.Entities;

import Game.Interfaces.Hero;
import Game.Interfaces.Item;

/**
 * Represents an item in the game
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public abstract class ItemImp implements Item {

    /**
     * The name of the item
     */
    private final String name;

    /**
     * The points of the item
     */
    private final double points;

    /**
     * Constructor for the item
     *
     * @param name   the name of the item
     * @param points the points of the item
     */
    public ItemImp(String name, double points) {
        this.name = name;
        this.points = points;
    }

    /**
     * Getter for the name of the item
     *
     * @return the name of the item
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the points of the item
     *
     * @return the points of the item
     */
    public double getPoints() {
        return this.points;
    }

    /**
     * Applies the effect of the item to the hero
     *
     * @param hero the hero to apply the effect to
     */
    public abstract void applyEffect(Hero hero);
}
