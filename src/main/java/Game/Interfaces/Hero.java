package Game.Interfaces;

import Game.Exceptions.ItemException;

/**
 * Represents a hero in the game
 */
public interface Hero extends Character{

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
     * Method that verifies if the hero's backpack is full
     * @return true if the backpack is full, false otherwise
     */
    public boolean isBackPackFull();

    /**
     * Method adds a new item to the hero's backpack with the FILO principle
     * @param item the item to add
     * @throws ItemException if the item is invalid or null
     */
    public void addToBackPack(Item item) throws ItemException;

    /**
     * Method uses an item from the hero's backpack
     */
    public void UseItem();

    /**
     * Heals the hero's helath by the given amount
     * @param amount the amount to heal
     * @throws IllegalArgumentException if the amount is negative
     */
    public void heal(int amount) throws IllegalArgumentException;

    /**
     * Returns the armor health of the hero
     * @return the armor health of the hero
     */
    public int getArmorHealth();

    /**
     * Sets the armor health of the hero
     * @param armorHealth the armor health of the hero
     * @throws IllegalArgumentException if armor health is negative
     */
    public void setArmorHealth(int armorHealth) throws IllegalArgumentException;

    /**
     * Returns the armor defense of the hero
     * @param amount the amount to heal
     * @throws IllegalArgumentException if the amount is negative
     */
    public void healArmor(int amount) throws IllegalArgumentException;

}
