package Game.Exceptions;

/**
 *
 * @author diogo
 */
public class ItemException extends Exception {

    private static final String DEFAULT_MESSAGE = "Item Exception";

    /**
     * Creates a new ElementNotFoundException with no message.
     */
    public ItemException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Creates a new ElementNotFoundException with the specified message.
     *
     * @param message the message for this exception.
     */
    public ItemException(String message) {
        super(message);
    }
}
