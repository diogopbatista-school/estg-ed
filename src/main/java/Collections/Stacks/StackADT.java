package Collections.Stacks;

import Collections.Exceptions.EmptyCollectionException;

import java.util.Iterator;

/**
 * StackADT represents a stack.
 *
 * @param <T> the type of elements in this stack
 */
public interface StackADT<T> {

    /**
     * Adds one element to the top of this stack.
     *
     * @param element element to be pushed onto stack
     */
    public void push(T element);

    /**
     * Removes and returns the top element from this stack.
     *
     * @return T element removed from the top of the stack
     * @throws EmptyCollectionException if the stack is empty
     */
    public T pop() throws EmptyCollectionException;

    /**
     * Returns without removing the top element of this stack.
     *
     * @return T element on top of the stack
     * @throws EmptyCollectionException if the stack is empty
     */
    public T peek() throws EmptyCollectionException;

    /**
     * Returns true if this stack contains no elements.
     *
     * @return boolean whether or not this stack is empty
     */
    public boolean isEmpty();

    /**
     * Returns the number of elements in this stack.
     *
     * @return int number of elements in this stack
     */
    public int size();

    /**
     * Returns a string representation of this stack.
     *
     * @return String representation of this stack
     */
    @Override
    public String toString();

    /**
     * Method that returns an iterator for the stack
     *
     * @return an iterator for the list
     */
    public Iterator<T> iterator();
}

