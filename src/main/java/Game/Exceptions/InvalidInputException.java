package Game.Exceptions;

/**
 * Represents an exception that occurs when an invalid input is provided.
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class InvalidInputException extends Exception {

    private static final String DEFAULT_MESSAGE = "Invalid input exception";

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
