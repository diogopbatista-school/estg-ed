package Game.Entities;

import Collections.Stacks.ArrayStack;
import Game.Enumerations.ItemType;
import Game.Exceptions.ItemException;
import Interfaces.Character;
import Interfaces.Hero;
import Interfaces.Item;


/**
 * Represents a hero in the game
 */
public class HeroImp implements Hero {

    /**
     * Hero's name
     */
    private String name;

    /**
     * Hero's health
     */
    private int health;

    /**
     * Hero's armor
     */
    private int armor;

    /**
     *The hero's attack power
     */
    private int attackPower;

    /**
     * The name of the room the hero is in
     */
    private String roomName;

    private ArrayStack<Item> backPack;


    /**
     * Constructor for the HeroImp class
     */
    public HeroImp(int attackPower, String roomName) {
        this.name = "Tó Cruz"; // Em memoria ao grande Tom Cruise
        this.health = 100;
        this.armor = 100;
        this.attackPower = attackPower;
        this.roomName = roomName;
        this.backPack = new ArrayStack<>();
    }

    /**
     * Method that adds a new item to the hero's backpack with the FILO principle
     * @param item the item to add
     * @throws ItemException if the item is invalid or null
     */
    @Override
    public void addItem(Item item) throws ItemException {
        if(item == null){
            throw new IllegalArgumentException("Item must be valid");
        }
        this.backPack.push(item);
    }

    /**
     * Method that removes an item from the hero's backpack
     * @return the item removed
     * @throws ItemException if the item is invalid or null
     */
    private Item RemoveItem() throws ItemException {
        if(this.backPack.isEmpty()){
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
            if (item.getType() == ItemType.POTION) {
                heal(item.getPoints());
            } else if (item.getType() == ItemType.ARMOR) {
                healArmor(item.getPoints());
            }
        } catch (ItemException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Setter for the room name the character is going to be in
     * @param roomName The name of the room the character is going to be in
     * @throws IllegalArgumentException if the room name is invalid
     */
    public void setRoomName(String roomName) throws IllegalArgumentException {
        if(roomName == null || roomName == ""){
            throw new IllegalArgumentException("Room name must be valid");
        }
        this.roomName = roomName;
    }

    /**
     * Getter for the room name the character is in
     * @return The name of the room the character is in
     */
    public String getRoomName() {
        return this.roomName;
    }

    /**
     * Getter for the hero's name
     * @return
     */
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
        if(amount < 0){
            throw new IllegalArgumentException("Amount must be positive");
        }

        this.health += amount;
        if(this.health > 100){
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
        if(armorHealth < 0){
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
    public void healArmor(int amount) throws IllegalArgumentException{
        if(amount < 0){
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.armor += amount;
        if(this.armor > 100){
            this.armor = 100;
        }
    }

    /**
     * Method that returns the hero's health
     * @return The hero's health
     */
    @Override
    public int getHealth() {
        return this.health;
    }

    /**
     * Setter for the hero's health
     * @param health The hero's health
     * @throws IllegalArgumentException if the health is negative
     */
    @Override
    public void setHealth(int health) throws IllegalArgumentException {
        if(health < 0){
            throw new IllegalArgumentException("Health must be positive");
        }
        this.health = health;
    }

    /**
     * Method that has the logic for the hero to attack
     * @param character The character that the hero is attacking
     * @throws IllegalArgumentException if the character is invalid
     */
    @Override
    public void attack(Character character) throws IllegalArgumentException{
        if(character == null || character instanceof Hero){
            throw new IllegalArgumentException("Character must be valid");
        }
        this.setHealth(this.getHealth() - character.getAttackPower());
    }

    /**
     * Getter for the hero's attack power
     * @return The hero's attack power
     */
    @Override
    public int getAttackPower() {
        return this.attackPower;
    }

    /**
     * Setter for the hero's attack power
     * @param attackPower The hero's attack power
     * @throws IllegalArgumentException if the attack power is negative
     */
    @Override
    public void setAttackPower(int attackPower) throws IllegalArgumentException {
        if(attackPower < 0){
            throw new IllegalArgumentException("Attack power must be positive");
        }
        this.attackPower = attackPower;
    }
}
