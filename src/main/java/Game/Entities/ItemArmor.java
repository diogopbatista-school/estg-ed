package Game.Entities;

import Collections.Enum.ItemType;
import Game.Interfaces.Hero;

public class ItemArmor extends ItemImp{

    private ItemType type;

    public ItemArmor(String name , double points) {
        super(name, points);
        this.type = ItemType.ARMOR;
    }

    @Override
    public ItemType getType() {
        return this.type;
    }

    public String getName() {
        return super.getName();
    }

    @Override
    public void applyEffect(Hero hero) {
        hero.healArmor(this.getPoints());
    }
}
