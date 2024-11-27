package Game.Navigation;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Lists.ArrayUnorderedList;
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
        this.enemies = new ArrayUnorderedList<>();
        this.target = null;
        this.items = new ArrayUnorderedList<>();
        this.hero = null;
        this.roomName = roomName;
        this.heroHasAttackPriority = true;
        this.totalRoomPower = 0;
        this.isInAndOut = false;
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

        this.enemies.addToFront(enemy); // sempre adicionar na frente pois os primeiros ataques o inicio tem mais probabilidade
        // de falecer primeiro , entao a remoçao no meio da lista é mais eficiente

    }

    public void removeEnemy(Enemy enemy) throws EmptyCollectionException {
        // logica para adicionar os inimigos derrotados a uma coleçao da network
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

        if (hero.isBackPackFull()) {
            throw new ItemException("Backpack is full");
        }

        Item removedItem = removeItemByType(it);
        if (removedItem == null) {
            throw new ItemException("No item of type " + it + " found in the room");
        }

        hero.addToBackPack(removedItem);
    }

    private Item removeItemByType(ItemType it) {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getType().equals(it)) {
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
