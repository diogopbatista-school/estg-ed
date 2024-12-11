package Collections.Lists;

import Collections.Exceptions.ElementNotFoundException;
import Collections.Exceptions.EmptyCollectionException;
import Collections.Nodes.Node;

/**
 * A generic class that implements a circular linked list.
 *
 * @param <T> the type of the stored element.
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public abstract class CircularLinkedList<T> implements ListADT<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;

    /**
     * Creates an empty list.
     */
    public CircularLinkedList() {
        this.front = null;
        this.size = 0;
    }

    private Node<T> findNode(T element) {
        Node<T> current = this.front;
        while (current != rear && !current.getElement().equals(element)) {
            current = current.getNext();
        }
        return current;
    }

    /**
     * Removes and returns the first element from this list.
     *
     * @return T the first element from this list
     * @throws EmptyCollectionException if the list is empty
     */
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }

        T removed = this.front.getElement();
        this.front = this.front.getNext();
        rear.setNext(front);
        size--;
        return removed;
    }

    /**
     * Removes and returns the last element from this list.
     *
     * @return T the last element from this list
     * @throws EmptyCollectionException if the list is empty
     */
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }

        T removed = this.rear.getElement();

        Node<T> current = this.front;
        while (current.getNext() != rear) {
            current = current.getNext();
        }
        current.setNext(this.front);
        this.rear = current;
        this.size--;
        return removed;
    }

    /**
     * Removes and returns the specified element.
     *
     * @param element the element to be removed and returned from the list
     * @return T the removed element
     * @throws EmptyCollectionException if the list is empty
     * @throws ElementNotFoundException if the element is not in the list
     */
    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }

        Node<T> current = findNode(element);

        if (current == this.rear && !current.getElement().equals(element)) {
            throw new ElementNotFoundException("Element not found");
        }

        if (current == this.front) {
            return removeFirst();
        } else if (current == this.rear) {
            return removeLast();
        }
        T result = current.getElement();
        Node<T> previous = this.front;
        while (previous.getNext() != current) {
            previous = previous.getNext();
        }
        previous.setNext(current.getNext());
        this.size--;
        return result;
    }

    /**
     * Returns a reference to the first element in this list.
     *
     * @return T a reference to the first element in this list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }

        return this.front.getElement();
    }

    /**
     * Returns a reference to the last element in this list.
     *
     * @return T a reference to the last element in this list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }

        return rear.getElement();
    }

    /**
     * Returns true if this list contains the specified target element.
     *
     * @param target the target that is being sought in the list
     * @return true if the list contains this element
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public boolean contains(T target) throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }

        Node<T> current = findNode(target);

        return current.getElement().equals(target);
    }

    /**
     * Method that checks if the list is empty
     *
     * @return {@true} if the list is empty and {@false} otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the integer representation of number of elements in this list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns a string representation of this list.
     *
     * @return the string representation of this list
     */
    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " { ";
        if (!isEmpty()) {
            Node<T> current = front;
            while (current != rear) {
                result += current.getElement() + " ";
                current = current.getNext();
            }
            result += current.getElement() + " ";
        }
        return result + "}";
    }
}
