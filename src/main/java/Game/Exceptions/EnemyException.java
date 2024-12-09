package Game.Exceptions;

/**
 * Represents an exception that occurs in the Enemy class.
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */

public class EnemyException extends Exception {

    private static final String DEFAULT_MESSAGE = "Enemy Exception";

    /**
     * Creates a new EnemyException with no message.
     */
    public EnemyException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Creates a new EnemyException with the specified message.
     *
     * @param message the message for this exception.
     */
    public EnemyException(String message) {
        super(message);
    }
}
