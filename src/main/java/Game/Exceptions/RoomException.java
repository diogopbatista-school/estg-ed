package Game.Exceptions;

/**
 *
 * @author diogo
 */
public class RoomException extends Exception {

    private static final String DEFAULT_MESSAGE = "Room Exception";

    /**
     * Creates a new ElementNotFoundException with no message.
     */
    public RoomException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Creates a new ElementNotFoundException with the specified message.
     *
     * @param message the message for this exception.
     */
    public RoomException(String message) {
        super(message);
    }
}
