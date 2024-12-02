package Game.Exceptions;

/**
 *
 * @author diogo
 */
public class HeroException extends Exception {

    private static final String DEFAULT_MESSAGE = "Hero Exception";

    /**
     * Creates a new HeroException with no message.
     */
    public HeroException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Creates a new HeroException with the specified message.
     *
     * @param message the message for this exception.
     */
    public HeroException(String message) {
        super(message);
    }
}
