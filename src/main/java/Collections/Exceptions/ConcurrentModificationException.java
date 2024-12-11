package Collections.Exceptions;


/**
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 *
 * ConcurrentModificationException is an exception that is thrown when a collection is modified while an iterator is's in use.
 */
public class ConcurrentModificationException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Concurrent modification detected in collection.";

    /**
     * Creates a new ConcurrentModificationException with no message.
     */
    public ConcurrentModificationException() {
        super(DEFAULT_MESSAGE);
    }

}