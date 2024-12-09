package Game.Interfaces;

import Collections.Lists.UnorderedListADT;
import Game.Exceptions.*;

import java.util.Iterator;

/**
 * Represents a room in the game
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public interface Room extends Comparable<Room> {


    /**
     * Method that verifies if there is an enemy alive
     *
     * @return true if there is at least one enemy alive, false otherwise
     */
    public boolean isThereAnEnemyAlive();

    /**
     * Method that returns the items in the room
     *
     * @return the items in the room
     */
    public Iterator<Item> getItems();

    /**
     * Method that verifies if there are items in the room
     *
     * @return true if there are items in the room, false otherwise
     */
    public boolean hasItems();

    /**
     * Method that verifies if the target is in the room
     *
     * @return true if the target is in the room, false otherwise
     */
    public boolean isTargetInRoom();

    /**
     * Method that verifies if the room has an exit and an entry
     *
     * @return true if the room has an exit and an entry, false otherwise
     */
    public boolean isInAndOutRoom();

    /**
     * Returns a list of all enemies in the room.
     *
     * @return a LinkedUnorderedList of all enemies
     */
    public UnorderedListADT<Enemy> getEnemies();


    /**
     * Removes an enemy from the room.
     *
     * @param enemy the enemy to remove
     * @throws EnemyException if the enemy is not found in the room
     */
    public void removeEnemy(Enemy enemy) throws EnemyException;

    /**
     * Setter for the room's entry and exit status.
     */
    public void setEntryAndExit();

    /**
     * Calculates the total attack power of all enemies in the room that are still alive.
     * <p>
     * Iterates through the list of enemies in the room and sums up the attack power
     * of each enemy whose health is greater than zero.
     *
     * @return the total attack power of all living enemies in the room.
     */
    public double getTotalEnemiesAttackPower();

    /**
     * Adds an enemy to the room
     *
     * @param enemy the enemy to add
     * @throws EnemyException if the enemy is invalid or null
     */
    public void addEnemy(Enemy enemy) throws EnemyException;

    /**
     * Adds a hero to the room
     *
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
     *
     * @param target the target to add
     * @throws TargetException if the target is invalid or null
     */
    public void addTargetToRoom(Target target) throws TargetException;

    /**
     * Removes a target from the room
     *
     * @param target the target to remove
     * @throws TargetException if the target is invalid or null
     */
    public void removeTarget(Target target) throws TargetException;

    /**
     * Adds an item to the room
     *
     * @param item the item to add
     * @throws ItemException if the item is invalid or null
     */
    public void addItem(Item item) throws ItemException;

    /**
     * Removes an item from the room
     *
     * @param hero the hero to add the item to ( backPack or add to armor bar)
     * @throws ItemException if the item is invalid or null
     * @throws HeroException if the hero backpack is full
     * @throws RoomException if the room doesn't have any items
     */
    public void removeItems(Hero hero) throws ItemException, HeroException, RoomException;

    /**
     * Getter for the room's name
     *
     * @return the room's name
     */
    public String getRoomName();

    /**
     * Getter for the target in the room
     *
     * @return the target in the room
     */
    public Target getTarget();

    /**
     * Getter for the hero in the room
     *
     * @return the hero in the room
     */
    public Hero getHero();
}
