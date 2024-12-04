package Game.Interfaces;

import Collections.Lists.UnorderedListADT;
import Game.Exceptions.*;

import java.util.Iterator;

/**
 * Represents a room in the game
 */
public interface Room extends Comparable<Room> {


    public boolean isThereAnEnemyAlive();


    public int getNumberOfEnemyAlives();

    public Iterator<Item> getItems();

    public boolean hasItems();

    public boolean isTargetInRoom();

    public boolean getIsInAndOut();

    /**
     * Returns a list of all enemies in the room.
     * @return a LinkedUnorderedList of all enemies
     */
    public UnorderedListADT<Enemy> getEnemies();


    /**
     * Removes an enemy from the room.
     * @param enemy the enemy to remove
     * @throws EnemyException if the enemy is not found in the room
     */
    public void removeEnemy(Enemy enemy) throws EnemyException;

    public Hero getHero();

    public void setEntryAndExit();

    /**
     * Calculates the total attack power of all enemies in the room that are still alive.
     *
     * Iterates through the list of enemies in the room and sums up the attack power
     * of each enemy whose health is greater than zero.
     *
     * @return the total attack power of all living enemies in the room.
     */
    public int getTotalEnemiesAttackPower();

    /**
     * Adds an enemy to the room
     * @param enemy the enemy to add
     * @throws EnemyException if the enemy is invalid or null
     */
    public void addEnemy(Enemy enemy) throws EnemyException;

    /**
     * Adds a hero to the room
     * @param hero the hero to add
     * @throws HeroException if the hero is invalid or null
     */
    public void addHero(Hero hero) throws HeroException;

    /**
     * Removes a hero from the room
     */
    public void removeHero();

    /**
     * Adds a target to the room
     * @param target the target to add
     * @throws TargetException if the target is invalid or null
     */
    public void addTargetToRoom(Target target) throws TargetException;

    /**
     * Removes a target from the room
     * @param target the target to remove
     * @throws TargetException if the target is invalid or null
     */
    public void removeTarget(Target target) throws TargetException;

    /**
     * Adds an item to the room
     * @param item the item to add
     * @throws ItemException if the item is invalid or null
     */
    public void addItem(Item item) throws ItemException;

    /**
     * Removes an item from the room
     * @param hero the hero to add the item to ( backPack or add to armor bar)
     * @throws ItemException if the item is invalid or null
     */
    public void removeItem(Hero hero) throws ItemException;

    public String getRoomName();

    public boolean isIsAndOut();

    public Target getTarget();

}
