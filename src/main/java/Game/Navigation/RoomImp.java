package Game.Navigation;

import Collections.Lists.DoublyLinkedUnorderedList;
import Collections.Lists.LinkedUnorderedList;
import Collections.Lists.UnorderedListADT;
import Game.Entities.ItemArmor;
import Game.Entities.ItemHealer;
import Game.Exceptions.*;
import Game.Interfaces.*;

import java.util.Iterator;


public class RoomImp implements Room {

    private UnorderedListADT<Enemy> enemies;
    private Hero hero;
    private Target target;
    private UnorderedListADT<Item> items;
    private final String roomName;
    private Boolean isInAndOut;

    public RoomImp(String roomName) {
        this.enemies = new LinkedUnorderedList<>();
        this.target = null;
        this.items = new LinkedUnorderedList<>();
        this.hero = null;
        this.roomName = roomName;
        this.isInAndOut = false;
    }


    /**
     * Method that verifies if there is an enemy alive
     * @return true if there is at least one enemy alive, false otherwise
     */
    public boolean isThereAnEnemyAlive(){
        Iterator<Enemy> enemy = this.enemies.iterator();
        while(enemy.hasNext()){
            if(enemy.next().isAlive()){
                return true;
            }
        }
        return false;
    }


    /**
     * Method that returns the items in the room
     * @return the items in the room
     */
    @Override
    public Iterator<Item> getItems() {
        return items.iterator();
    }

    /**
     * Method that verifies if the room has items
     * @return true if the room has items, false otherwise
     */
    public boolean hasItems(){
        return !this.items.isEmpty();
    }

    /**
     * Method that verifies if the room has a target
     * @return true if the room has a target, false otherwise
     */
    public boolean isTargetInRoom(){
        return this.target != null;
    }

    /**
     * Method that verifies if the room is and exit and a entry
     * @return true if the room is an exit and entry, false otherwise
     */
    public boolean isInAndOutRoom(){
        return this.isInAndOut;
    }

    /**
     * Removes an enemy from the room
     * @param enemy the enemy to remove
     * @throws EnemyException if the enemy is not found in the room
     */
    @Override
    public void removeEnemy(Enemy enemy) throws EnemyException {
        if (enemy == null || !this.enemies.contains(enemy)) {
            throw new EnemyException("Enemy not found in the room / Enemy cannot be null");
        }


        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy currentEnemy = iterator.next();
            if (currentEnemy.equals(enemy)) {
                enemies.remove(currentEnemy);
                enemy.setCurrentRoom(null); // Optionally clear the enemy's current room reference
                break; // Exit the loop after finding the enemy
            }
        }

    }

    /**
     * Returns a list of all enemies in the room
     * @return an unordered list of all enemies
     */
    @Override
    public UnorderedListADT<Enemy> getEnemies() {
        return this.enemies;
    }


    /**
     * Sets the boolean flag indicating if the room is an in and out room
     */
    public void setEntryAndExit(){
        this.isInAndOut = true;
    }

    /**
     * Calculates the total attack power of all enemies in the room that are still alive
     * @return the total attack power of all living enemies in the room
     */
    @Override
    public double getTotalEnemiesAttackPower(){
        Iterator<Enemy> enemies = this.enemies.iterator();
        double totalRoomPower = 0;

        while(enemies.hasNext()){
            Enemy enemy = enemies.next();
            if(enemy.isAlive()){
                totalRoomPower += enemy.getAttackPower();
            }

        }
        return totalRoomPower;
    }

    /**
     * Adds a target to the room
     * @param target the target to add
     */
    @Override
    public void addTargetToRoom(Target target) throws TargetException {
        this.target = target;
    }

    /**
     * Adds an enemy to the room
     * @param enemy the enemy to add
     * @throws EnemyException if the enemy is invalid or null
     */
    @Override
    public void addEnemy(Enemy enemy) throws EnemyException {

        if(enemy == null){
            throw new EnemyException("Enemy cannot be null");
        }

        this.enemies.addToFront(enemy);
        enemy.setCurrentRoom(this);
    }


    /**
     * Adds a hero to the room
     * @param hero the hero to add
     * @throws HeroException if the hero is invalid or null
     */
    @Override
    public void addHero(Hero hero) throws HeroException {
        if(hero == null){
            throw new HeroException("Hero cannot be null");
        }
        this.hero = hero;
        this.hero.setCurrentRoom(this);
    }

    /**
     * Removes a hero from the room
     */
    @Override
    public void removeHero(){
        this.hero = null;
    }

    /**
     * Method that removes a target from the room
     * @param target the target to remove
     * @throws TargetException
     */
    @Override
    public void removeTarget(Target target) throws TargetException {
        if(target == null){
            throw new TargetException("Target cannot be null");
        }

        this.target = null;
    }

    /**
     * Method that adds an item to the room
     * @param item the item to add to the room
     * @throws ItemException if the item is invalid or null
     */
    @Override
    public void addItem(Item item) throws ItemException {
        if(item == null){
            throw new ItemException("Item cannot be null");
        }
        this.items.addToFront(item);
    }

    /**
     * Method that removes all item from the room
     * @param hero the hero to add the item to ( backPack or add to armor bar)
     * @throws ItemException if the
     * @throws HeroException if the hero is invalid or null
     */
    @Override
    public void removeItems(Hero hero) throws ItemException, HeroException, RoomException { // iterar todos os items da sala
        if(hero == null){
            throw new HeroException("Hero cannot be null");
        }

        if(hero.isBackPackFull()){
            throw new HeroException("Hero's backpack is full");
        }

        if(this.items.isEmpty()){
            throw new RoomException("There are no items in the room");
        }

        boolean itemRemoved;
        do {
            itemRemoved = false;
            Iterator<Item> itemIterator = this.items.iterator();
            while (itemIterator.hasNext()) {
                Item currentItem = itemIterator.next();
                if (currentItem instanceof ItemHealer itemHealer) {
                    hero.addToBackPack(itemHealer);
                    items.remove(currentItem);
                    itemRemoved = true;
                    break;
                } else if (currentItem instanceof ItemArmor) {
                    currentItem.applyEffect(hero);
                    items.remove(currentItem);
                    itemRemoved = true;
                    break;
                }
            }
        } while (itemRemoved);

        throw new RoomException("There are no more items in the room");
    }

    /**
     * Method that returns the room's name
     * @return the room's name
     */
    @Override
    public String getRoomName() {
        return this.roomName;
    }

    /**
     * Method that returns the target in the room
     * @return the target in the room
     */
    @Override
    public Target getTarget() {
        return this.target;
    }

    /**
     * Method that returns the hero in the room
     * @return the hero in the room
     */
    @Override
    public Hero getHero(){
        return this.hero;
    }

    /**
     * Method to string that return this room name
     * @return the room name in a string format
     */
    public String toString() {
        return "Room: " + this.roomName;
    }


    @Override
    public int compareTo(Room o) {
        return 0;
    }
}
