package Collections.Exceptions;

/**
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 *
 *
 * ElementNotFoundException is an exception that is thrown when an element is not found in a collection.
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
