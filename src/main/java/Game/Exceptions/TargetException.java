package Game.Exceptions;

/**
 *
 * @author diogo
 */
public class TargetException extends Exception {

    private static final String DEFAULT_MESSAGE = "Item Exception";

    /**
     * Creates a new ElementNotFoundException with no message.
     */
    public TargetException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Creates a new ElementNotFoundException with the specified message.
     *
     * @param message the message for this exception.
     */
    public TargetException(String message) {
        super(message);
    }
}
