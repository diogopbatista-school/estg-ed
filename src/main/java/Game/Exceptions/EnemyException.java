package Game.Exceptions;

/**
 *
 * @author diogo
 */
public class EnemyException extends Exception {

    private static final String DEFAULT_MESSAGE = "Enemy Exception";

    /**
     * Creates a new ElementNotFoundException with no message.
     */
    public EnemyException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Creates a new ElementNotFoundException with the specified message.
     *
     * @param message the message for this exception.
     */
    public EnemyException(String message) {
        super(message);
    }
}
