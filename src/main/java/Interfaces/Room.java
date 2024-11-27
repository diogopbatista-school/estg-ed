package Interfaces;

import Game.Enumerations.ItemType;
import Game.Exceptions.*;

/**
 * Represents a room in the game
 */
public interface Room {

    public void setEntryAndExit();

    /**
     * Getter for the room's power
     * @return the room's power
     */
    public int getRoomPower();

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
