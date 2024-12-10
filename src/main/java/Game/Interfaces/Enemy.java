package Game.Interfaces;

/**
 * Represents an enemy in the game
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public interface Enemy extends Character {

    /**
     * Setter for the current room
     *
     * @param currentRoom the current room
     */
    void setCurrentRoom(Room currentRoom);

    /**
     * Getter for the current room
     *
     * @return the current room
     */
    Room getCurrentRoom();

    /**
     * Returns the name of the enemy
     *
     * @return the name of the enemy
     */
    String getName();
}
