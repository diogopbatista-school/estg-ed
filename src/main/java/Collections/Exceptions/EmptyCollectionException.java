package Collections.Exceptions;


/**
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 * <p>
 * <p>
 * EmptyCollectionException is an exception that is thrown when a collection is empty.
 */
public class EmptyCollectionException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "The collection is empty.";

    /**
     * Creates an EmptyCollectionException with no message
     */
    public EmptyCollectionException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Creates an EmptyCollectionException with the specified message
     *
     * @param message the message to be displayed with the exception
     */
    public EmptyCollectionException(String message) {
        super(message);
    }
}
