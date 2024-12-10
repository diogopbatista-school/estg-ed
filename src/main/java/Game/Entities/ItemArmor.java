package Game.Entities;

import Game.Enum.ItemType;
import Game.Interfaces.Hero;

/**
 * Represents an armor item in the game
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class ItemArmor extends ItemImp {

    /**
     * The type of the item
     */
    private final ItemType type;

    /**
     * Constructor for the armor item
     *
     * @param name   the name of the item
     * @param points the points of the item
     */
    public ItemArmor(String name, double points) {
        super(name, points);
        this.type = ItemType.ARMOR;
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
        hero.healArmor(this.getPoints());
    }
}
