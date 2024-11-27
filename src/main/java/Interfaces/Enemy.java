package Interfaces;

/**
 * Represents an enemy in the game
 */
public interface Enemy extends Character{

    /**
     * Method that verifies if the enemy is dead
     * @return true if the enemy is dead, false otherwise
     */
    public boolean isDead();

    /**
     * Sets the name of the enemy
     * @param name the name of the enemy
     * @throws IllegalArgumentException if the name is null
     */
    public void setName(String name) throws IllegalArgumentException;

    /**
     * Returns the name of the enemy
     * @return the name of the enemy
     */
    public String getName();
}
