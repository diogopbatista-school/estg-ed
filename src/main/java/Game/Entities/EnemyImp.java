package Game.Entities;

import Interfaces.Character;
import Interfaces.Enemy;

/**
 * Represents an enemy in the game
 */
public class EnemyImp implements Enemy {

    /**
     * Enemy's name
     */
    private String name;

    /**
     * Enemy's health
     */
    private int health;

    /**
     * Enemy's attackPower
     */
    private int attackPower;

    /**
     * The name of the room the enemy is in
     */
    private String roomName;

    /**
     * Constructor for EnemyImp
     * @param name The name of the enemy
     * @param attackPower The attack power of the enemy
     * @param RoomName The name of the room the enemy is in
     */
    public EnemyImp(String name,int attackPower, String RoomName){
        this.name = name;
        this.health = 100;
        this.attackPower = attackPower;
        this.roomName = RoomName;
    }

    /**
     * Getter for the name of the room the enemy is in
     * @return The name of the room the enemy is in
     */
    public String getRoomName() {
        return roomName;
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
     * Setter for the name of the enemy
     * @param name The name of the enemy
     */
    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name == ""){
            throw new IllegalArgumentException("Name must be valid");
        }
        this.name = name;
    }

    /**
     * Getter for the name of the enemy
     * @return The name of the enemy
     */
    public String getName(){
        return name;
    }

    /**
     * Getter for the health of the enemy
     * @return The health of the enemy
     */
    @Override
    public int getHealth() {
        return health;
    }

    /**
     * Setter for the health of the enemy
     * @param health The health of the enemy
     * @throws IllegalArgumentException if health is negative
     */
    @Override
    public void setHealth(int health) throws IllegalArgumentException {
        if(health < 0){
            throw new IllegalArgumentException("Health must be positive");
        }
        this.health = health;
    }

    /**
     * Method that has the logic for the enemy to attack
     * @param character The character that the enemy is attacking
     * @throws IllegalArgumentException if the character is invalid
     */
    @Override
    public void attack(Character character) throws IllegalArgumentException{
        if(character == null || character instanceof Enemy){
            throw new IllegalArgumentException("Character must be valid");
        }
        this.setHealth(this.getHealth() - character.getAttackPower());
    }

    /**
     * Getter for the attack power of the enemy
     * @return The attack power of the enemy
     */
    @Override
    public int getAttackPower() {
        return attackPower;
    }

    /**
     * Setter for the attack power of the enemy
     * @param attackPower The attack power of the enemy
     */
    @Override
    public void setAttackPower(int attackPower) throws IllegalArgumentException {
        if(attackPower < 0){
            throw new IllegalArgumentException("Attack power must be positive");
        }
        this.attackPower = attackPower;
    }

}
