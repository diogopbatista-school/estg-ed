package Collections.Stacks;

import Collections.Exceptions.EmptyCollectionException;

import java.util.ConcurrentModificationException;
import java.util.Iterator;


/**
 * A generic class that implements a stack using an array as the underlying data
 * structure. This stack follows the last-in-first-out (LIFO)
 *
 * @param <T> the type of the stored element.
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class ArrayStack<T> implements StackADT<T> {

    /**
     * The default capacity of the stack.
     */
    protected static final int DEFAULT_CAPACITY = 100;

    /**
     * The factor to expand the array.
     */
    protected static final int EXPAND = 2;

    /**
     * The array representing the stack.
     */
    protected T[] stack;

    /**
     * The index representing the top of the stack.
     */
    protected int top;

    /**
     * Constructor for an empty stack with the default capacity.
     */
    public ArrayStack(int initialCapacity) {
        this.stack = (T[]) (new Object[initialCapacity]);
        this.top = 0;
    }

    /**
     * Constructor for an empty stack with the default capacity.
     */
    public ArrayStack() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Method to verify if the list is empty .
     *
     * @return - {@code true} if is empty ; {@code false} otherwise .
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Method to obtain the number of elements.
     *
     * @return the number of elements.
     */
    @Override
    public int size() {
        return this.top;
    }

    /**
     * Method to expand the capacity of the array stack.
     */
    private void expandCapacity() {
        T[] aux = (T[]) (new Object[this.stack.length * EXPAND]);
        for (int i = 0; i < stack.length; i++) {
            aux[i] = stack[i];
        }

        stack = aux;
    }

    /**
     * Method to add an element to the stack.
     *
     * @param element the element to add
     */
    @Override
    public void push(T element) {
        if (size() == this.stack.length) {
            expandCapacity();
        }

        this.stack[this.top++] = element;
    }

    /**
     * Method to remove the element of the stack following the LIFO
     *
     * @return the removed generic element
     * @throws EmptyCollectionException if the stack is empty.
     */
    @Override
    public T pop() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack is empty");
        }

        this.top--;

        T result = this.stack[this.top];
        this.stack[this.top] = null;

        return result;
    }

    /**
     * Method to obtain the generic element from the top of the stack
     *
     * @return the element on top of the stack .
     * @throws EmptyCollectionException if the stack is empty.
     */
    @Override
    public T peek() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack is empty");
        }

        return this.stack[top - 1];
    }

    /**
     * Returns a string representation of the stack.
     *
     * @return a string containing all elements of the stack.
     */
    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " { ";
        for (int i = 0; i < top; i++) {
            result += stack[i] + " ";
        }
        return result + "}";
    }


    /**
     * Method that returns an iterator for the stack
     *
     * @return an iterator for the list
     */
    public Iterator<T> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<T> {
        private int current;
        private int expectedModCount;
        private boolean okToRemove;

        public StackIterator() {
            this.current = 0;
            this.expectedModCount = top;
            this.okToRemove = false;
        }

        @Override
        public boolean hasNext() {
            return this.current < top;
        }

        @Override
        public T next() {
            if (top != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new UnsupportedOperationException();
            }
            okToRemove = true;
            return stack[current++];
        }

        @Override
        public void remove() {
            if (expectedModCount != top) {
                throw new ConcurrentModificationException();
            }
            if (!okToRemove) {
                throw new IllegalStateException();
            }
            Collections.Stacks.ArrayStack.this.pop();
            current--;
            this.expectedModCount++;
            this.okToRemove = false;
        }
    }

}






