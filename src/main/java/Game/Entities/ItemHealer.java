package Game.Entities;

import Collections.Enum.ItemType;
import Game.Interfaces.Hero;

public class ItemHealer extends ItemImp {

    private final ItemType type;

    public ItemHealer(String name , double points) {
        super(name, points);
        this.type = ItemType.HEALER;
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
        hero.heal(this.getPoints());
    }



}
