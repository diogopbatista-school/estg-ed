package Game.Exceptions;

/**
 * Represents an exception that occurs in the Target class.
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
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
