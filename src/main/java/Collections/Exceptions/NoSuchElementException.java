package Collections.Exceptions;

/**
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 * <p>
 * <p>
 * NoSuchElementException is thrown when an element is not found in a collection.
 */
public class NoSuchElementException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "No such element found in the collection.";

    /**
     * Creates a new instance of NoSuchElementException without detail message.
     */
    public NoSuchElementException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Constructs an instance of NoSuchElementException with the specified detail message.
     *
     * @param message the detail message.
     */
    public NoSuchElementException(String message) {
        super(message);
    }

}
