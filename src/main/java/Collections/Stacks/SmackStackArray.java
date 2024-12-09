package Collections.Stacks;

import Collections.Exceptions.EmptyCollectionException;

/**
 * SmackStackArray represents a smack stack.
 * @param <T> the type of elements in this stack
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class SmackStackArray<T> extends ArrayStack<T> implements SmackStackADT<T> {

    /**
     * Creates an empty stack using the default capacity.
     */
    public SmackStackArray() {
        super();
    }

    /**
     * Creates an empty stack using the specified capacity.
     * @param initialCapacity the initial size of the stack
     */
    public SmackStackArray(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Removes and returns the bottom element from this stack.
     *
     * @return T element removed from the bottom of the stack
     * @throws EmptyCollectionException if the stack is empty
     */
    @Override
    public T smack() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack is empty");
        }

        T result = stack[0];
        for (int i = 0; i < top - 1; i++)
            stack[i] = stack[i + 1];
        top--;
        return result;
    }
}