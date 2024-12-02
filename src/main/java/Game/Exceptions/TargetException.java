package Game.Exceptions;

/**
 *
 * @author diogo
 */
public class TargetException extends Exception {

    private static final String DEFAULT_MESSAGE = "Item Exception";

    /**
     * Creates a new TargetException with no message.
     */
    public TargetException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Creates a new TargetException with the specified message.
     *
     * @param message the message for this exception.
     */
    public TargetException(String message) {
        super(message);
    }
}
