package Collections.Lists;

import Collections.Exceptions.*;
import Collections.Nodes.Node;

/**
 * A class that represents a singly linked list sentinel . This implementation
 * uses a sentinel head node and maintains a reference to the tail.
 *
 * @param <T> the type of the stored element.
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class LinkedListSentinel<T> {

    /**
     * The reference to the sentinel head node. The head node does not store any
     * actual data.
     */
    private final Node<T> head;

    /**
     * The reference to the tail node
     */
    private Node<T> tail;

    /**
     * The number of elements stored in the list.
     */
    private int size;

    /**
     * Constructor for an empty linked list with sentinel head and tail nodes.
     */
    public LinkedListSentinel() {
        head = new Node<>(null);
        tail = new Node<>(null);
        head.setNext(head);
        tail.setNext(tail);
        this.size = 0;
    }

    /**
     * Method that adds a new element to the end of the list. The new element is
     * inserted after the current tail node.
     *
     * @param element the element to be added to the list.
     */
    public void add(T element) {
        Node<T> newNode = new Node<>(element);
        tail.setNext(newNode);
        tail = newNode;
        this.size++;
    }

    /**
     * Method that removes a specific element from the list.
     *
     * @param element the element to be removed from the list.
     * @return {@code true} if the element was successfully removed;
     * {@code false} otherwise.
     * @throws EmptyCollectionException if the list is empty .
     * @throws ElementNotFoundException if the specific element is not found in
     * the list.
     */
    public boolean remove(T element) throws EmptyCollectionException, ElementNotFoundException {

        if (this.head.getNext() == null) {
            throw new EmptyCollectionException("Error");
        }

        boolean found = false;
        Node<T> current = head.getNext(); // current é a verdadeira posiçao
        Node<T> previous = this.head; // ficticio

        while (current != this.tail && !found) { // para procurar
            if (current.getElement().equals(element)) {
                found = true;
            } else {
                previous = current;
                current = current.getNext();
            }
        }
        if (!found) {
            throw new ElementNotFoundException("The element does not exist");
        }

        previous.setNext(current.getNext());
        current.setNext(null);
        this.size--;
        return true;
    }

    /**
     * Method to verify if the list is empty .
     *
     * @return - {@code true} if is empty ; {@code false} otherwise .
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Method to obtain the size of the list.
     *
     * @return - The size of the list .
     */
    public int size() {
        return size;
    }

    /**
     * Converts the linked list into an array of the same type as the elements
     * in the list. The elements in the array will be in the same order as they
     * appear in the list.
     *
     * @return an array containing all the elements of the list in order.
     */
    public T[] toArray() {
        T[] array = (T[]) new Object[size()];
        Node<T> current = head;
        for (int i = 0; i < size(); i++) {
            current = current.getNext();
            array[i] = current.getElement();
        }
        return array;
    }

    /**
     * Returns a string representation of the linked list.
     *
     * @return a string representation of the list.
     */
    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " { ";
        if (!isEmpty()) {
            Node<T> current = head.getNext();
            do {
                result += current.getElement() + " ";
            } while ((current = current.getNext()) != null);
        }
        return result + "}";
    }

}
