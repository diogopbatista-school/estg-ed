package Collections.Exceptions;

/**
 *
 * @author diogo
 */
    public class ElementNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Element not found in the collection.";

        /**
         * Creates a new ElementNotFoundException with no message.
         */
        public ElementNotFoundException() {
            super(DEFAULT_MESSAGE);
        }

        /**
         * Creates a new ElementNotFoundException with the specified message.
         *
         * @param message the message for this exception.
         */
        public ElementNotFoundException(String message) {
            super(message);
        }
}
