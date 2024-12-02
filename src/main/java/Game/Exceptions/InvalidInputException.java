package Game.Exceptions;

/**
 *
 * @author diogo
 */
public class InvalidInputException extends Exception {

    private static final String DEFAULT_MESSAGE = "Hero Exception";

    /**
     * Creates a new InvalidInputException with no message.
     */
    public InvalidInputException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Creates a new InvalidInputException with the specified message.
     *
     * @param message the message for this exception.
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
