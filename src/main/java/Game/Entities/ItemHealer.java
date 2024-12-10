package Game.Entities;

import Game.Enum.ItemType;
import Game.Interfaces.Hero;

/**
 * Represents a healer item in the game
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class ItemHealer extends ItemImp {

    /**
     * The type of the item
     */
    private final ItemType type;

    /**
     * Constructor for the healer item
     *
     * @param name   the name of the item
     * @param points the points of the item
     */
    public ItemHealer(String name, double points) {
        super(name, points);
        this.type = ItemType.HEALER;
    }

    /**
     * Getter for the type of the item
     *
     * @return the type of the item
     */
    @Override
    public ItemType getType() {
        return this.type;
    }

    /**
     * Getter for the name of the item
     *
     * @return the name of the item
     */
    public String getName() {
        return super.getName();
    }

    /**
     * Applies the effect of the item to the hero
     *
     * @param hero the hero to apply the effect to
     */
    @Override
    public void applyEffect(Hero hero) {
        hero.heal(this.getPoints());
    }


}
