package Game.Interfaces;

/**
 * Represents an enemy in the game
 */
public interface Enemy extends Character {

    /**
     * Setter for the current room
     * @param currentRoom the current room
     */
    public void setCurrentRoom(Room currentRoom);

    /**
     * Getter for the current room
     * @return the current room
     */
    public Room getCurrentRoom();

    /**
     * Returns the name of the enemy
     * @return the name of the enemy
     */
    public String getName();
}
