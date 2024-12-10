package Collections.Queues;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Nodes.Node;

/**
 * A generic class that implements a queue using a linked list structure. This
 * queue follows the first-in-first-out (FIFO)
 *
 * @param <T> the type of the stored element.
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class LinkedQueue<T> implements QueueADT<T> {

    /**
     * The front node.
     */
    private Node<T> front;

    /**
     * The rear node.
     */
    private Node<T> rear;

    /**
     * The size of the linked queue.
     */
    private int size;

    /**
     * Constructor for an empty linked queue .
     */
    public LinkedQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /**
     * Method to verify if the linked queue is empty.
     *
     * @return {@true} if the linked queue is empty, {@false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Method to obtain the first element of the queue.
     *
     * @return the front element .
     * @throws EmptyCollectionException if the linked queue is empty.
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }

        return this.front.getElement();
    }

    /**
     * Method to obtain the number of elements.
     *
     * @return the number of elements.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Method to add a new element to the queue.
     *
     * @param element the element to add
     */
    @Override
    public void enqueue(T element) {
        Node<T> newNode = new Node<>(element);

        if (isEmpty()) {
            this.front = newNode;
        } else {
            rear.setNext(newNode);
        }
        this.rear = newNode;

        this.size++;
    }

    /**
     * Method to remove an element of the queue following the FIFO
     *
     * @return the removed element.
     * @throws EmptyCollectionException if the queue is empty .
     */
    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }

        T removed = this.front.getElement();
        this.front = front.getNext();
        size--;

        return removed;
    }

    /**
     * Returns a string representation of the list.
     *
     * @return a string containing all elements of the list.
     */
    @Override
    public String toString() {
        String result = " { ";
        Node<T> current = front;
        if (!isEmpty()) {
            do {
                result += current.getElement() + " ";
            } while ((current = current.getNext()) != null);
        }
        return result + "}";
    }

}
