package Game.Interfaces;

import Collections.Lists.LinkedUnorderedList;
import Game.Exceptions.*;

import java.util.List;

/**
 * Represents a room in the game
 */
public interface Room {

    /**
     * Returns a list of all enemies in the room.
     * @return a LinkedUnorderedList of all enemies
     */
    public LinkedUnorderedList<Enemy> getEnemies();


    /**
     * Removes an enemy from the room.
     * @param enemy the enemy to remove
     * @throws EnemyException if the enemy is not found in the room
     */
    public void removeEnemy(Enemy enemy) throws EnemyException;

    public Hero getHero();

    public void setEntryAndExit();

    /**
     * Sets whether the hero has attack priority in the room.
     * This determines if the hero will attack first during a turn-based fight sequence.
     *
     * @param heroHasAttackPriority Boolean value indicating if the hero has attack priority.
     */
    public void setHeroHasAttackPriority(Boolean heroHasAttackPriority);
    /**
     * Calculates the total attack power of all enemies in the room that are still alive.
     *
     * Iterates through the list of enemies in the room and sums up the attack power
     * of each enemy whose health is greater than zero.
     *
     * @return the total attack power of all living enemies in the room.
     */
    public int getTotalRoomPower();

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
     * @param itemToRemove the item to remove
     * @param hero the hero to add the item to ( backPack or add to armor bar)
     * @throws ItemException if the item is invalid or null
     */
    public void removeItem(Item itemToRemove, Hero hero) throws ItemException;

    /**
     * Simulation of the fight between the hero and the enemies in the room
     */
    public void fight();

    /**
     * Returns the name of the room
     * @return the name of the room
     */
    public String getRoomName();

    public void setInAndOut(Boolean isInAndOut);

    public boolean isIsAndOut();

}
