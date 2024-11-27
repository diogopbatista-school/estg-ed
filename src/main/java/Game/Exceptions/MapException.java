package Game.Exceptions;

/**
 *
 * @author diogo
 */
public class MapException extends Exception {

    private static final String DEFAULT_MESSAGE = "Map Exception";

    /**
     * Creates a new ElementNotFoundException with no message.
     */
    public MapException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Creates a new ElementNotFoundException with the specified message.
     *
     * @param message the message for this exception.
     */
    public MapException(String message) {
        super(message);
    }
}
