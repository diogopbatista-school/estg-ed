package Game.Navigation;

import Collections.Lists.ArrayUnorderedList;
import Game.Exceptions.*;
import Interfaces.*;

public class RoomImp implements Room {

    private ArrayUnorderedList<Enemy> enemies;
    private Hero hero;
    private ArrayUnorderedList<Target> targets;
    private ArrayUnorderedList<Item> items;
    private String roomName;

    public RoomImp(String roomName) {
        this.enemies = new ArrayUnorderedList<>();
        this.targets = new ArrayUnorderedList<>();
        this.items = new ArrayUnorderedList<>();
        this.hero = null;
        this.roomName = roomName;
    }

    @Override
    public void addEnemy(Enemy enemy) throws EnemyException {
        if(enemy == null){
            throw new EnemyException("Enemy cannot be null");
        }

        if(this.enemies.contains(enemy)) {
            throw new EnemyException("Enemy already exists in the room");
        }

        this.enemies.addToFront(enemy);
    }

    @Override
    public void addHero(Hero hero) throws HeroException {
        if(hero == null){
            throw new HeroException("Hero cannot be null");
        }
        this.hero = hero;
    }

    @Override
    public void removeHero(){
        this.hero = null;
    }

    @Override
    public void addTarget(Target target) throws TargetException {
        if(target == null){
            throw new TargetException("Target cannot be null");
        }

        if(this.targets.contains(target)) {
            throw new TargetException("Target already exists in the room");
        }
        this.targets.addToFront(target);
    }

    @Override
    public void removeTarget(Target target) throws TargetException {
        if(target == null){
            throw new TargetException("Target cannot be null");
        }
        this.targets.remove(target);
    }

    @Override
    public void addItem(Item item) throws ItemException {
        if(item == null){
            throw new ItemException("Item cannot be null");
        }
        this.items.addToFront(item);
    }

    @Override
    public void removeItem(Item item) throws ItemException {
        this.items.remove(item);
    }

    @Override
    public void fight() {

    }

    @Override
    public String getRoomName() {
        return this.roomName;
    }
}
