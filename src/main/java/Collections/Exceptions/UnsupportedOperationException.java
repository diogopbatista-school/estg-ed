package Collections.Exceptions;

/**
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 *
 *
 * UnsupportedOperationException is thrown when an operation is not supported.
 */
public class UnsupportedOperationException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "This operation is not supported.";

    /**
     * Constructs a new UnsupportedOperationException with no detail message.
     */
    public UnsupportedOperationException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Constructs a new UnsupportedOperationException with the specified detail message.
     *
     * @param message the detail message
     */
    public UnsupportedOperationException(String message) {
        super(message);
    }
}