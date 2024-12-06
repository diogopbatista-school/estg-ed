package Game.Navigation;

import Collections.Lists.DoublyLinkedUnorderedList;
import Collections.Lists.LinkedUnorderedList;
import Collections.Lists.UnorderedListADT;
import Game.Enumerations.ItemType;
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
     * Method that returns the target in the room
     * @return the target in the room
     */
    public Target getTarget(){
        return this.target;
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

    @Override
    public int getNumberOfEnemyAlives() {
        Iterator<Enemy> enemy = this.enemies.iterator();
        int count = 0;
        while(enemy.hasNext()){
            if(enemy.next().isAlive()){
                count++;
            }
        }
        return count;
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
    public boolean getIsInAndOut(){
        return this.isInAndOut;
    }

    @Override
    public void removeEnemy(Enemy enemy) throws EnemyException {
        if (enemy == null || !this.enemies.contains(enemy)) {
            throw new EnemyException("Enemy not found in the room.");
        }

        UnorderedListADT<Enemy> enemiesToRemove = new LinkedUnorderedList<>();
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy currentEnemy = iterator.next();
            if (currentEnemy.equals(enemy)) {
                enemiesToRemove.addToRear(currentEnemy);
                enemy.setCurrentRoom(null); // Optionally clear the enemy's current room reference
                break; // Exit the loop after finding the enemy
            }
        }

        Iterator<Enemy> removeIterator = enemiesToRemove.iterator();
        while (removeIterator.hasNext()) {
            enemies.remove(removeIterator.next());
        }
    }


    @Override
    public UnorderedListADT<Enemy> getEnemies() {
        return this.enemies;
    }

    @Override
    public Hero getHero(){
        return this.hero;
    }

    /**
     * Sets the boolean flag indicating if the room is an in and out room
     */
    public void setEntryAndExit(){
        this.isInAndOut = true;
    }



    public int getTotalEnemiesAttackPower(){
        Iterator<Enemy> enemies = this.enemies.iterator();
        int totalRoomPower = 0;

        while(enemies.hasNext()){
            totalRoomPower += enemies.next().getAttackPower();
        }

        return totalRoomPower;
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

        this.enemies.addToFront(enemy);


    }


    @Override
    public void addHero(Hero hero) throws HeroException {
        if(hero == null){
            throw new HeroException("Hero cannot be null");
        }
        this.hero = hero;
        this.hero.setCurrentRoom(this);
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
    public void removeItem(Hero hero) throws ItemException { // iterar todos os items da sala
        if(hero == null){
            throw new ItemException("Hero cannot be null");
        }

        if(this.items.isEmpty()){
            throw new ItemException("There are no items in the room");
        }

        boolean itemRemoved;
        do {
            itemRemoved = false;
            Iterator<Item> iterator = this.items.iterator();
            while (iterator.hasNext()) {
                Item currentItem = iterator.next();
                if (currentItem.getType() == ItemType.KIT_DE_VIDA) {
                    hero.addToBackPack(currentItem);
                    this.items.remove(currentItem);
                    itemRemoved = true;
                    break; // Restart the iterator
                } else if (currentItem.getType() == ItemType.COLETE) {
                    hero.healArmor(currentItem.getPoints());
                    this.items.remove(currentItem);
                    itemRemoved = true;
                    break; // Restart the iterator
                }
            }
        } while (itemRemoved);

        throw new ItemException("There are no items in the room");
    }

    public Hero getHeroInRoom(){
        return this.hero;
    }

    @Override
    public String getRoomName() {
        return this.roomName;
    }


    /**
     * Checks if the room is an in and out room.
     * @return true if the room is an in and out, false otherwise.
     */
    @Override
    public boolean isIsAndOut() {
        return isInAndOut;
    }

    public String toString() {
        return "Room: " + this.roomName;
    }

    @Override
    public int compareTo(Room o) {
        return 0;
    }
}
