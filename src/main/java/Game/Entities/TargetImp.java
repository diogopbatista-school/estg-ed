package Game.Entities;

import Game.Exceptions.TargetException;
import Game.Interfaces.Target;

/**
 * Represents a target in the game
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class TargetImp implements Target {
    /**
     * The target aquired status
     */
    private boolean targetAquired;

    /**
     * The target type
     */
    private String targetName;

    /**
     * The name of the room the target is in
     */
    private String roomName;

    /**
     * Constructor for the TargetImp class
     * @param RoomName The name of the room the target is in
     * @param type The type of the target
     */
    public TargetImp(String RoomName, String type) {
        this.targetAquired = false;
        this.targetName = type;
        this.roomName = RoomName;
    }

    /**
     * Getter for the room name the target is in
     * @return The name of the room the target is in
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Setter for the target's aquired status
     * @param targetAquired the target aquired status
     */
    public void setTargetAquired(boolean targetAquired) {
        this.targetAquired = targetAquired;
    }

    /**
     * Returns the target aquired status
     * @return true if the target is aquired by the hero, false otherwise
     */
    public boolean isTargetAquired() {
        return targetAquired;
    }

    /**
     * Setter for the target type
     * @param targetName the target type
     * @throws TargetException if the target type is invalid
     */
    public void setTargetType(String targetName) throws TargetException {
        if(targetName == null || targetName == "") {
            throw new TargetException("Target type cannot be invalid");
        }
        this.targetName = targetName;
    }

    /**
     * Getter for the target name
     * @return the target name
     */
    public String getTargetName() {
        return targetName;
    }

}
