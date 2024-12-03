// src/main/java/Game/Entities/HeroImp.java
package Game.Entities;

import Collections.Stacks.ArrayStack;
import Collections.Stacks.StackADT;
import Game.Enumerations.ItemType;
import Game.Exceptions.ItemException;
import Game.Interfaces.Hero;
import Game.Interfaces.Item;
import Game.Interfaces.Room;
import Game.Interfaces.Target;


/**
 * Represents a hero in the game
 */
public class HeroImp extends CharacterImp implements Hero {

    /**
     * The hero's armor health
     */
    private int armor;

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
     */
    public HeroImp(int attackPower, int maxSize) {
        super("Tó Cruz", 100, attackPower); // Em memória ao grande Tom Cruise
        this.armor = 100;
        this.backPack = new ArrayStack<>(maxSize);
        this.sizeBackPack = maxSize;
        this.isBackPackFull = false;
        this.currentRoom = null;
        this.target = null;
    }

    /**
     * Getter that returns the hero's first item in the backpack
     * @return the hero's first item in the backpack
     */
    public Item getItemFirstItem(){
        return this.backPack.peek();
    }

    /**
     * Setter for the hero's target
     * @param target the target to set
     */
    public void setTarget(Target target) {
        this.target = target;
    }

    /**
     * Method that verifies if the hero has items on the backpack
     * @return true if the hero has items on the backpack, false otherwise
     */
    public boolean isItemsOnBackPack() {
        return !this.backPack.isEmpty();
    }

    /**
     * Method that verifies if the hero is alive
     * @return true if the hero is alive, false otherwise
     */
    public boolean isAlive() {
        return this.health > 0;
    }

    /**
     * Method that verifies if the hero has a target
     * @return true if the hero has a target, false otherwise
     */
    public boolean doesHeroHaveTarget() {
        return this.target != null;
    }

    /**
     * Setter for the hero's current room
     * @param room the room to set
     */
    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    /**
     * Getter for the hero's current room
     * @return the hero's current room
     */
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * Method that verifies if the hero's backpack is full
     * @return true if the backpack is full, false otherwise
     */
    @Override
    public boolean isBackPackFull() {
        return this.isBackPackFull;
    }

    /**
     * Method that adds a new item to the hero's backpack with the FILO principle
     * @param item the item to add
     * @throws ItemException if the item is invalid or null
     */
    @Override
    public void addToBackPack(Item item) throws ItemException {
        if (item == null) {
            throw new IllegalArgumentException("Item must be valid");
        }
        if (this.backPack.size() == this.sizeBackPack) {
            this.isBackPackFull = true;
            throw new ItemException("Backpack is full");
        }
        this.backPack.push(item);
    }

    /**
     * Method that removes an item from the hero's backpack
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
    public void UseItem() {
        try {
            Item item = RemoveItem();
            if (item.getType() == ItemType.KIT_DE_VIDA) {
                heal(item.getPoints());
            } else if (item.getType() == ItemType.COLETE) {
                healArmor(item.getPoints());
            }
        } catch (ItemException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Getter for the hero's name
     * @return the hero's name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Method that heals the hero's health
     * @param amount the amount of health to be healed
     * @throws IllegalArgumentException if the amount is negative
     */
    @Override
    public void heal(int amount) throws IllegalArgumentException {
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
     * @return the hero's armor health
     */
    @Override
    public int getArmorHealth() {
        return this.armor;
    }

    /**
     * Method that sets the hero's armor health
     * @param armorHealth the hero's armor health
     * @throws IllegalArgumentException if the armor health is negative
     */
    @Override
    public void setArmorHealth(int armorHealth) throws IllegalArgumentException {
        if (armorHealth < 0) {
            throw new IllegalArgumentException("Armor health must be positive");
        }
        this.armor = armorHealth;
    }

    /**
     * Method that heals the hero's armor health
     * @param amount the amount of armor health to be healed
     * @throws IllegalArgumentException if the amount is negative
     */
    @Override
    public void healArmor(int amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.armor += amount;
        if (this.armor > 100) {
            this.armor = 100;
        }
    }

    /**
     * Method that returns the hero's health
     * @return the hero's health
     */
    @Override
    public int getHealth() {
        return this.health;
    }

    /**
     * Setter for the hero's health
     * @param health the hero's health
     * @throws IllegalArgumentException if the health is negative
     */
    @Override
    public void setHealth(int health) throws IllegalArgumentException {
        if (health < 0) {
            throw new IllegalArgumentException("Health must be positive");
        }
        this.health = health;
    }


    /**
     * Getter for the hero's attack power
     * @return the hero's attack power
     */
    @Override
    public int getAttackPower() {
        return this.attackPower;
    }

    /**
     * Setter for the hero's attack power
     * @param attackPower the hero's attack power
     * @throws IllegalArgumentException if the attack power is negative
     */
    @Override
    public void setAttackPower(int attackPower) throws IllegalArgumentException {
        if (attackPower < 0) {
            throw new IllegalArgumentException("Attack power must be positive");
        }
        this.attackPower = attackPower;
    }
}