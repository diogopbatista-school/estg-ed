package Game.Exceptions;

/**
 * Represents an exception that occurs in the Item class.
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class ItemException extends Exception {

    private static final String DEFAULT_MESSAGE = "Item Exception";

    /**
     * Creates a new ItemException with no message.
     */
    public ItemException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Creates a new ItemException with the specified message.
     *
     * @param message the message for this exception.
     */
    public ItemException(String message) {
        super(message);
    }
}
