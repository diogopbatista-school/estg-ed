package Game.Exceptions;

/**
 * * Represents an exception that occurs in the Map class.
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
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
