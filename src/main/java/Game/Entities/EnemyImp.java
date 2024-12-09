
package Game.Entities;

import Game.Interfaces.Enemy;
import Game.Interfaces.Room;
import Game.Navigation.RoomImp;

/**
 * Represents an enemy in the game
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class EnemyImp extends CharacterImp implements Enemy {

    /**
     * The room where the enemy is located
     */
    private Room currentRoom;


    /**
     * Constructor for the enemy
     *
     * @param name        The name of the enemy
     * @param health      The health of the enemy
     * @param attackPower The attack power of the enemy
     * @param currentRoom The room where the enemy is currently located
     */
    public EnemyImp(String name, double health, double attackPower, Room currentRoom) {
        super(name, health, attackPower);
        this.currentRoom = currentRoom;
    }

    /**
     * Getter for the current room
     *
     * @return The current room
     */
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * Setter for the current room
     *
     * @param currentRoom
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * Method that verifies if the enemy is in fight . He is only in fight if the hero is
     * in the same room as the enemy
     *
     * @return true if the enemy is in fight, false otherwise
     */
    @Override
    public boolean isInFight() {
        return currentRoom.getHero() != null;
    }

}