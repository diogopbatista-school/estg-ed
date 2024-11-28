package Game.Interfaces;


/**
 * Represents a character in the game
 */
public interface Character {

    public void setInFight(boolean inFight);

    /**
     * Method verifies if the character is in fight
     * @return true if the character is in fight, false otherwise
     */
    public boolean isInFight();

    /**
     * Returns the name of the character
     * @return the name of the character
     */
    public String getName();

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

}
