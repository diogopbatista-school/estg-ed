package Game.Navigation;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Lists.ArrayUnorderedList;
import Collections.Lists.LinkedUnorderedList;
import Collections.Lists.UnorderedListADT;
import Collections.Queues.LinkedQueue;
import Collections.Queues.QueueADT;
import Game.Enumerations.ItemType;
import Game.Exceptions.*;
import Interfaces.*;

import java.util.Iterator;

public class RoomImp implements Room {

    private UnorderedListADT<Enemy> enemies;
    private Hero hero;
    private Target target;
    private UnorderedListADT<Item> items;
    private String roomName;
    private Boolean heroHasAttackPriority;
    private int totalRoomPower;
    private Boolean isInAndOut;

    public RoomImp(String roomName) {
        this.enemies = new LinkedUnorderedList<>();
        this.target = null;
        this.items = new ArrayUnorderedList<>();
        this.hero = null;
        this.roomName = roomName;
        this.heroHasAttackPriority = true;
        this.totalRoomPower = 0;
        this.isInAndOut = false;
    }

    /**
     * Sets the boolean flag indicating if the room is an in and out room
     */
    public void setEntryAndExit(){
        this.isInAndOut = true;
    }

    /**
     * Getter for the room's power
     * @return the room's power
     */
    public int getRoomPower(){
        return this.totalRoomPower;
    }

    /**
     * Adds a target to the room
     * @param target the target to add
     */
    public void addTargetToRoom(Target target) throws TargetException {
        this.target = target;
    }

    @Override
    public void addEnemy(Enemy enemy) throws EnemyException {

        if(enemy == null){
            throw new EnemyException("Enemy cannot be null");
        }

        this.totalRoomPower += enemy.getAttackPower();

        if(this.enemies.isEmpty()){
            this.enemies.addToFront(enemy);
            return;
        }

        this.enemies.addAfter(enemy, this.enemies.last());
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
    public void removeTarget(Target target) throws TargetException {
        if(target == null){
            throw new TargetException("Target cannot be null");
        }

        this.target = null;
    }

    @Override
    public void addItem(Item item) throws ItemException {
        if(item == null){
            throw new ItemException("Item cannot be null");
        }
        this.items.addToFront(item);
    }

    @Override
    public void removeItem(Item itemToRemove, Hero hero) throws ItemException { // iterar todos os items da sala

        if (itemToRemove == null || itemToRemove.getType() == ItemType.UNKNOWN) {
            throw new ItemException("Item cannot be null/unknown");
        }

        if (hero.isBackPackFull() && itemToRemove.getType().equals(ItemType.POTION)) {
            return;
        }else{
            Item removedItem = removeItemByType(itemToRemove);
            hero.addToBackPack(removedItem);
        }

        if(itemToRemove.getType().equals(ItemType.ARMOR)){
            hero.healArmor(itemToRemove.getPoints());
            return;
        }

    }

    private Item removeItemByType(Item itemToRemove) {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getType().equals(itemToRemove.getType())) {
                iterator.remove();
                return item;
            }
        }
        return null;
    }

    @Override
    public void fight() {



    }

    public int getTotalRoomPower(){
        return this.totalRoomPower;
    }

    @Override
    public String getRoomName() {
        return this.roomName;
    }

    
    /**
     * Sets the boolean flag indicating if the room is an in and out room.
     * @param isInAndOut Boolean value to set the room as in and out (true) or not (false)
     */
    @Override
    public void setInAndOut(Boolean isInAndOut) {
        this.isInAndOut = isInAndOut;
    }

    /**
     * Checks if the room is an in and out room.
     * @return true if the room is an in and out, false otherwise.
     */
    @Override
    public boolean isIsAndOut() {
        return isInAndOut;
    }
}
