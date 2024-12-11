package Game.Interfaces;


/**
 * Represents a character in the game
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public interface Character {


    /**
     * Method verifies if the character is in fight
     *
     * @return true if the character is in fight, false otherwise
     */
    boolean isInFight();

    /**
     * Returns the name of the character
     *
     * @return the name of the character
     */
    String getName();

    /**
     * Returns the health of the character
     *
     * @return the health of the character
     */
    double getHealth();

    /**
     * Sets the health of the character
     *
     * @param health the health of the character
     * @throws IllegalArgumentException if health is negative
     */
    void setHealth(double health) throws IllegalArgumentException;

    /**
     * Attacks another character
     *
     * @param character the character to attack
     * @throws IllegalArgumentException if the character is invalid
     */
    void attack(Character character) throws IllegalArgumentException;

    /**
     * Returns the attack power of the character
     *
     * @return the attack power of the character
     */
    double getAttackPower();

    /**
     * Method that verifies if the character is alive
     * @return true if the character is alive, false otherwise
     */
    boolean isAlive();

}
