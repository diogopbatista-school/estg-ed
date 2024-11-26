package Collections.Exceptions;;

/**
 *
 * @author diogo
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
