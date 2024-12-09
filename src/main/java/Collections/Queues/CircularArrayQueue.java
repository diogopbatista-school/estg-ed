package Collections.Queues;

import Collections.Exceptions.EmptyCollectionException;

/**
 * A class representing a circular array-based implementation of a queue.
 * This queue follows the first-in-first-out (FIFO) .
 *
 * @param <T> the type of elements held in this queue
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class CircularArrayQueue<T> implements QueueADT<T> {

    /**
     * The default initial capacity of the queue.
     */
    private final static int DEFAULT_CAPACITY = 100;

    /**
     * The array of the queue.
     */
    private T[] queue;

    /**
     * The reference's number of the front of the queue.
     */
    private int front;

    /**
     * The reference's number of the rear of the queue.
     */
    private int rear;

    /**
     * The number of elements of the queue.
     */
    private int size;

    /**
     * Constructor for an empty queue with a specific capacity
     *
     * @param initialCapacity the capacity of the queue
     */
    public CircularArrayQueue(int initialCapacity) {
        this.queue = (T[]) (new Object[initialCapacity]);
        this.front = this.rear = this.size = 0;
    }

    /**
     * Constructor for an empty queue with a default capacity.
     */
    public CircularArrayQueue() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * The size of the queue
     *
     * @return the number of elements of the queue
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Method to verify if the queue is empty
     *
     * @return - {@code true} if is empty ; {@code false} otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Method to expand the capacity of the queue
     */
    private void expandCapacity() {
        T[] larger = (T[]) (new Object[queue.length * 2]);
        for (int index = front; index < rear; index++) {
            larger[index] = queue[index];
        }
        queue = larger; // arrastar tudo direito á esquerda
    }

    /**
     * Method to add a new element to the queue
     *
     * @param element the element to add
     */
    @Override
    public void enqueue(T element) {
        if (size() == this.queue.length) {
            expandCapacity();
        }

        this.queue[this.rear] = element;
        this.rear = (this.rear + 1) % this.queue.length;
        size++;
    }

    /**
     * Method to remove a element of the queue following the FIFO
     *
     * @return the removed element
     * @throws EmptyCollectionException if the queue is empty
     */
    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }

        T removed = this.queue[this.front];

        this.queue[this.front] = null;
        this.front = (this.front + 1) % this.queue.length;
        this.size--;

        return removed;
    }

    /**
     * Method to obtain the first element in the queue
     *
     * @return the first element of the queue
     * @throws EmptyCollectionException if the queue is empty
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }

        return this.queue[this.front];
    }

    /**
     * Returns a string representation of the list
     *
     * @return a string containing all elements of the list
     */
    @Override
    public String toString() {
        String result = " { ";
        if (!isEmpty()) {
            int current = this.front;
            do {
                result += this.queue[current] + " ";
                current = (current + 1) % this.queue.length;
            } while (current != this.rear);
        }
        return result + "}";
    }

}
