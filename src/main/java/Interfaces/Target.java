package Interfaces;

import Game.Exceptions.TargetException;

/**
 * Represents a target in the game
 */
public interface Target {
    /**
     * Sets the target aquired status
     * @param targetAquired the target aquired status
     */
    public void setTargetAquired(boolean targetAquired);

    /**
     * Returns the target aquired status
     * @return the target aquired status
     */
    public boolean isTargetAquired();

    /**
     * Sets the target type
     * @param targetName the target type
     * @throws TargetException if the target type is invalid
     */
    public void setTargetType(String targetName) throws TargetException;

    /**
     * Returns the target type
     * @return the target type
     */
    public String getTargetName();

    /**
     * Getter of the room name
     * @return the room name
     */
    public String getRoomName();
}
