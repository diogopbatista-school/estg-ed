package Collections.Stacks;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Nodes.Node;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generic class that implements a stack using a linked list as the underlying
 * data structure. This stack follows the last-in-first-out (LIFO)
 *
 * @param <T> the type of the stored element.
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class LinkedStack<T> implements StackADT<T> {

    /**
     * The node at the top of the stack.
     */
    private Node<T> top;

    /**
     * The size of the stack.
     */
    private int size;

    /**
     * Constructor for an empty linked stack .
     */
    public LinkedStack() {
        this.top = null;
        this.size = 0;
    }

    /**
     * Method to verify if the list is empty .
     *
     * @return - {@code true} if is empty ; {@code false} otherwise .
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Method to obtain the number of elements.
     *
     * @return the number of elements.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Method to add an element to the stack.
     *
     * @param element the element to add .
     */
    @Override
    public void push(T element) {
        Node<T> newNode = new Node<>(element);
        newNode.setNext(top);
        top = newNode;
        size++;
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
            throw new EmptyCollectionException();
        }

        T removed = top.getElement();
        this.top = this.top.getNext();
        size--;

        return removed;
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
            throw new EmptyCollectionException();
        }

        return top.getElement();
    }

    /**
     * Returns a string representation of the stack.
     *
     * @return a string containing all elements of the stack.
     */
    @Override
    public String toString() {
        String result = " { ";
        if (!isEmpty()) {
            Node<T> current = top;
            do {
                result += current.getElement() + " ";
            } while ((current = current.getNext()) != null);
        }
        return result + "}";
    }

    /**
     * Method that returns an iterator for the stack
     *
     * @return an iterator for the stack
     */
    @Override
    public Iterator<T> iterator() {
        return new LinkedStackIterator();
    }

    private class LinkedStackIterator implements Iterator<T> {
        private Node<T> current = top;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T element = current.getElement();
            current = current.getNext();
            return element;
        }
    }

}
