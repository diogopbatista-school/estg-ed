// src/main/java/Game/Entities/HeroImp.java
package Game.Entities;

import Collections.Stacks.ArrayStack;
import Collections.Stacks.StackADT;
import Game.Exceptions.HeroException;
import Game.Exceptions.ItemException;
import Game.Interfaces.Hero;
import Game.Interfaces.Item;
import Game.Interfaces.Room;
import Game.Interfaces.Target;


/**
 * Represents a hero in the game
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class HeroImp extends CharacterImp implements Hero {

    /**
     * The hero's armor health
     */
    private double armor;

    /**
     * The hero's backpack with a stack data structure ( LIFO )
     */
    private StackADT<Item> backPack;

    /**
     * Boolean value indicating if the hero's backpack is full
     */
    private boolean isBackPackFull;

    /**
     * The room where the hero is currently located
     */
    private Room currentRoom;

    /**
     * The target that the hero wants to get it
     */
    private Target target;

    /**
     * The size of the backpack
     */
    private int sizeBackPack;

    /**
     * Constructor for the HeroImp class
     * @param attackPower the hero's attack power
     * @param maxSize the maximum size of the backpack
     */
    public HeroImp(double attackPower, int maxSize) {
        super("Tó Cruz", 100, attackPower); // Em memória ao grande Tom Cruise
        this.armor = 100;
        this.backPack = new ArrayStack<>(maxSize);
        this.sizeBackPack = maxSize;
        this.isBackPackFull = false;
        this.currentRoom = null;
        this.target = null;
    }

    /**
     * Constructor for the HeroImp class with health and armor for the logs of the game
     * @param health the hero's health
     * @param armor the hero's armor
     */
    public HeroImp(double health, double armor) {
        super("Tó Cruz", health, 100);
        this.armor = armor;
    }


    /**
     * Getter that returns the hero's first item in the backpack
     *
     * @return the hero's first item in the backpack
     */
    @Override
    public Item getItemFirstItem() {
        return this.backPack.peek();
    }

    /**
     * Setter for the hero's target
     *
     * @param target the target to set
     */
    public void setTarget(Target target) {
        this.target = target;
    }

    /**
     * Method that verifies if the hero has items on the backpack
     *
     * @return true if the hero has items on the backpack, false otherwise
     */
    public boolean isItemsOnBackPack() {
        return !this.backPack.isEmpty();
    }

    /**
     * Method that verifies if the hero is alive
     *
     * @return true if the hero is alive, false otherwise
     */
    public boolean isAlive() {
        return this.health > 0;
    }

    /**
     * Method that verifies if the hero has a target
     *
     * @return true if the hero has a target, false otherwise
     */
    public boolean doesHeroHaveTarget() {
        return this.target != null;
    }

    /**
     * Setter for the hero's current room
     *
     * @param room the room to set
     */
    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    /**
     * Getter for the hero's current room
     *
     * @return the hero's current room
     */
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * Method that verifies if the hero's backpack is full
     *
     * @return true if the backpack is full, false otherwise
     */
    @Override
    public boolean isBackPackFull() {
        return this.isBackPackFull;
    }

    /**
     * Method that adds a new item to the hero's backpack with the FILO principle
     *
     * @param item the item to add
     * @throws ItemException if the item is invalid or null
     * @throws HeroException if the backpack is full
     */
    @Override
    public void addToBackPack(Item item) throws ItemException, HeroException {
        if (item == null) {
            throw new ItemException("Item cannot be null");
        }
        if (this.backPack.size() == this.sizeBackPack) {
            this.isBackPackFull = true;
            throw new HeroException("Backpack is full");
        }
        this.backPack.push(item);
    }

    /**
     * Method that removes an item from the hero's backpack
     *
     * @return the item removed
     * @throws ItemException if the item is invalid or null
     */
    private Item RemoveItem() throws ItemException {
        if (this.backPack.isEmpty()) {
            throw new ItemException("Backpack is empty");
        }
        return this.backPack.pop();
    }

    /**
     * Method that uses an item from the hero's backpack
     */
    public Item useItem() {
        try {
            Item item = RemoveItem();
            if (item instanceof ItemHealer) {
                item.applyEffect(this);
                return item;
            }
        } catch (ItemException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Getter for the hero's name
     *
     * @return the hero's name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Method that heals the hero's health
     *
     * @param amount the amount of health to be healed
     * @throws IllegalArgumentException if the amount is negative
     */
    @Override
    public void heal(double amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.health += amount;
        if (this.health > 100) {
            this.health = 100;
        }
    }

    /**
     * Method that returns the hero's armor health
     *
     * @return the hero's armor health
     */
    @Override
    public double getArmorHealth() {
        return this.armor;
    }

    /**
     * Method that sets the hero's armor health
     *
     * @param armorHealth the hero's armor health
     * @throws IllegalArgumentException if the armor health is negative
     */
    @Override
    public void setArmorHealth(double armorHealth) throws IllegalArgumentException {
        if (armorHealth < 0) {
            throw new IllegalArgumentException("Armor health must be positive");
        }
        this.armor = armorHealth;
    }

    /**
     * Method that heals the hero's armor health
     *
     * @param amount the amount of armor health to be healed
     * @throws IllegalArgumentException if the amount is negative
     */
    @Override
    public void healArmor(double amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.armor += amount;
        if (this.armor > 100) {
            this.armor = 100;
        }
    }

    @Override
    public StackADT<Item> getBackPack() {
        return this.backPack;
    }

    /**
     * Method that returns the hero's health
     *
     * @return the hero's health
     */
    @Override
    public double getHealth() {
        return this.health;
    }

    /**
     * Setter for the hero's health
     *
     * @param health the hero's health
     * @throws IllegalArgumentException if the health is negative
     */
    @Override
    public void setHealth(double health) throws IllegalArgumentException {
        if (health < 0) {
            throw new IllegalArgumentException("Health must be positive");
        }
        this.health = health;
    }


    /**
     * Getter for the hero's attack power
     *
     * @return the hero's attack power
     */
    @Override
    public double getAttackPower() {
        return this.attackPower;
    }


    public String toString() {
        return "Hero: " + this.name + " Health: " + this.health + " Armor: " + this.armor;
    }
}