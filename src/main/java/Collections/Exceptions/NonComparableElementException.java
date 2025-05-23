package Collections.Exceptions;

/**
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 *
 * NonComparableElementException is thrown when an element is not comparable
 */
public class NonComparableElementException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Element is not comparable.";

    /**
     * Create a new instance of NonComparableElementException without a message
     */
    public NonComparableElementException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Create a new instance of NonComparableElementException with a message
     *
     * @param message The message to be displayed
     */
    public NonComparableElementException(String message) {
        super(message);
    }

}
