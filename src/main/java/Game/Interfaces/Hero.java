package Game.Interfaces;

import Collections.Stacks.StackADT;
import Game.Exceptions.HeroException;
import Game.Exceptions.ItemException;

/**
 * Represents a hero in the game
 */
public interface Hero extends Character{

    /**
     * Method that verifies if the backpack is full
     * @return true if the backpack is full, false otherwise
     */
    public boolean isBackPackFull();

    /**
     * Getter for the target attribute
     * @return the target attribute
     */
    public Item getItemFirstItem();

    /**
     * Setter for the target attribute
     * @param target the target attribute
     */
    public void setTarget(Target target);

    /**
     * Method that verifies if the hero has items on the backpack
     * @return true if the hero has items on the backpack, false otherwise
     */
    public boolean isItemsOnBackPack();

    /**
     * Getter for the target attribute
     * @return
     */
    public boolean doesHeroHaveTarget();

    /**
     * Setter for the current room
     * @param room the current room
     */
    public void setCurrentRoom(Room room);

    /**
     * Getter for the current room
     * @return the current room
     */
    public Room getCurrentRoom();


    /**
     * Method adds a new item to the hero's backpack with the FILO principle
     * @param item the item to add
     * @throws ItemException if the item is invalid or null
     * @throws HeroException if the backpack is full
     */
    public void addToBackPack(Item item) throws ItemException, HeroException;

    /**
     * Method uses an item from the hero's backpack
     */
    public Item useItem();

    /**
     * Heals the hero's helath by the given amount
     * @param amount the amount to heal
     * @throws IllegalArgumentException if the amount is negative
     */
    public void heal(double amount) throws IllegalArgumentException;

    /**
     * Returns the armor health of the hero
     * @return the armor health of the hero
     */
    public double getArmorHealth();

    /**
     * Sets the armor health of the hero
     * @param armorHealth the armor health of the hero
     * @throws IllegalArgumentException if armor health is negative
     */
    public void setArmorHealth(double armorHealth) throws IllegalArgumentException;

    /**
     * Returns the armor defense of the hero
     * @param amount the amount to heal
     * @throws IllegalArgumentException if the amount is negative
     */
    public void healArmor(double amount) throws IllegalArgumentException;

    public StackADT<Item> getBackPack();
}
