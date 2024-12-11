package Game.Interfaces;

import Collections.Stacks.StackADT;
import Game.Exceptions.HeroException;
import Game.Exceptions.ItemException;

/**
 * Represents a hero in the game
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public interface Hero extends Character {

    /**
     * Method that verifies if the backpack is full
     *
     * @return true if the backpack is full, false otherwise
     */
    boolean isBackPackFull();

    /**
     * Getter for the target attribute
     *
     * @return the target attribute
     */
    Item getItemFirstItem();

    /**
     * Setter for the target attribute
     *
     * @param target the target attribute
     */
    void setTarget(Target target);

    /**
     * Method that verifies if the hero has items on the backpack
     *
     * @return true if the hero has items on the backpack, false otherwise
     */
    boolean isItemsOnBackPack();

    /**
     * Getter for the target attribute
     *
     * @return the target attribute
     */
    boolean doesHeroHaveTarget();

    /**
     * Setter for the current room
     *
     * @param room the current room
     */
    void setCurrentRoom(Room room);

    /**
     * Getter for the current room
     *
     * @return the current room
     */
    Room getCurrentRoom();


    /**
     * Method adds a new item to the hero's backpack with the FILO principle
     *
     * @param item the item to add
     * @throws ItemException if the item is invalid or null
     * @throws HeroException if the backpack is full
     */
    void addToBackPack(Item item) throws ItemException, HeroException;

    /**
     * Method to use an item from the hero's backpack
     * @return the item used
     */
    Item useItem();

    /**
     * Heals the hero's helath by the given amount
     *
     * @param amount the amount to heal
     * @throws IllegalArgumentException if the amount is negative
     */
    void heal(double amount) throws IllegalArgumentException;

    /**
     * Returns the armor health of the hero
     *
     * @return the armor health of the hero
     */
    double getArmorHealth();

    /**
     * Sets the armor health of the hero
     *
     * @param armorHealth the armor health of the hero
     * @throws IllegalArgumentException if armor health is negative
     */
    void setArmorHealth(double armorHealth) throws IllegalArgumentException;

    /**
     * Returns the armor defense of the hero
     *
     * @param amount the amount to heal
     * @throws IllegalArgumentException if the amount is negative
     */
    void healArmor(double amount) throws IllegalArgumentException;

    /**
     * Method that gets the hero's backpack in items
     * @return the hero's backpack in items as a stack of items
     */
    StackADT<Item> getBackPack();
}
