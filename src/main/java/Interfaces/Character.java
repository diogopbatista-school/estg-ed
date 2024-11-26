package Interfaces;


/**
 * Represents a character in the game
 */
public interface Character {
    /**
     * Returns the health of the character
     * @return the health of the character
     */
    public int getHealth();

    /**
     * Sets the health of the character
     * @param health the health of the character
     * @throws IllegalArgumentException if health is negative
     */
    public void setHealth(int health) throws IllegalArgumentException;

    /**
     * Attacks another character
     * @param character the character to attack
     * @throws IllegalArgumentException if the character is invalid
     */
    public void attack(Character character) throws IllegalArgumentException;

    /**
     * Returns the attack power of the character
     * @return the attack power of the character
     */
    public int getAttackPower();

    /**
     * Sets the attack power of the character
     * @param attackPower the attack power of the character
     * @throws IllegalArgumentException if attack power is negative
     */
    public void setAttackPower(int attackPower) throws IllegalArgumentException;

    /**
     * Setter for the room name the character is going to be in
     * @param roomName The name of the room the character is going to be in
     * @throws IllegalArgumentException if the room name is invalid
     */
    public void setRoomName(String roomName) throws IllegalArgumentException;

    /**
     * Getter for the room name the character is in
     * @return The name of the room the character is in
     */
    public String getRoomName();
}
