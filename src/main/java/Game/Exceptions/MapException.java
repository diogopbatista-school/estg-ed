package Game.Exceptions;

/**
 *
 * @author diogo
 */
public class MapException extends Exception {

    private static final String DEFAULT_MESSAGE = "Map Exception";

    /**
     * Creates a new MapException with no message.
     */
    public MapException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Creates a new MapException with the specified message.
     *
     * @param message the message for this exception.
     */
    public MapException(String message) {
        super(message);
    }
}
