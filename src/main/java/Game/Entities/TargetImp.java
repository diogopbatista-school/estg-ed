package Game.Entities;

import Game.Interfaces.Target;

/**
 * Represents a target in the game
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class TargetImp implements Target {

    /**
     * The target type
     */
    private final String targetName;

    /**
     * The name of the room the target is in
     */
    private final String roomName;

    /**
     * Constructor for the TargetImp class
     *
     * @param RoomName The name of the room the target is in
     * @param type     The type of the target
     */
    public TargetImp(String RoomName, String type) {
        this.targetName = type;
        this.roomName = RoomName;
    }


}
