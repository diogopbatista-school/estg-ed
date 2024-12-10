package Game.Interfaces;


import Game.Enum.ItemType;

/**
 * Represents an item in the game
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public interface Item {

    /**
     * Getter for the type of the item
     *
     * @return the type of the item
     */
    ItemType getType();

    /**
     * Getter for the points of the item
     *
     * @return the points of the item
     */
    double getPoints();

    /**
     * Getter for the name of the item
     *
     * @return the name of the item
     */
    String getName();

    /**
     * Applies the effect of the item to the hero
     *
     * @param hero the hero to apply the effect to
     */
    void applyEffect(Hero hero);
}
