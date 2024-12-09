package Game.Exceptions;

/**
 * Represents an exception that occurs in the Room class.
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class RoomException extends Exception {

    private static final String DEFAULT_MESSAGE = "Room Exception";

    /**
     * Creates a new RoomException with no message.
     */
    public RoomException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Creates a new RoomException with the specified message.
     *
     * @param message the message for this exception.
     */
    public RoomException(String message) {
        super(message);
    }
}
