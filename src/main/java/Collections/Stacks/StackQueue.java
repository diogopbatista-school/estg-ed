package Collections.Stacks;

import Collections.Queues.QueueADT;
import Collections.Exceptions.EmptyCollectionException;

/**
 * A class that represents a queue using two stacks.
 * @param <T> the type of the stored element.
 */
public class StackQueue<T> implements QueueADT<T> {

    /**
     * The two stacks used to represent the queue.
     */
    private final LinkedStack<T> stack1;
    private final LinkedStack<T> stack2;

    /**
     * Default constructor for a StackQueue.
     */
    public StackQueue() {
        this.stack1 = new LinkedStack<>();
        this.stack2 = new LinkedStack<>();
    }

    /**
     * Method that transfers elements from stack1 to stack2.
     *
     * @throws EmptyCollectionException
     */
    private void transfer() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException();
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
    }

    /**
     * Method that adds a specific element to the rear of the queue.
     * @param element the element to be added to the rear of the queue
     */
    @Override
    public void enqueue(T element) {
        stack1.push(element);
    }

    /**
     * Method that removes and returns the element at the front of the queue.
     *
     * @return the element at the front of the queue
     * @throws EmptyCollectionException
     */
    @Override
    public T dequeue() throws EmptyCollectionException {
        transfer();
        return stack2.pop();
    }

    /**
     * Method that returns the element at the front of the queue without removing it
     * 
     * @return the element at the front of the queue
     * @throws EmptyCollectionException if the queue is empty
     */
    @Override
    public T first() throws EmptyCollectionException {
        transfer();
        return stack2.peek();
    }

    /**
     * Method that checks if the queue is empty.
     * @return {@true} if the queue is empty; {@false} otherwise
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Method that returns the size of the queue
     * @return
     */
    @Override
    public int size() {
        return stack1.size() + stack2.size();
    }

    /**
     * Method that returns a string representation of the queue.
     * @return a string representation of the queue
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " { " +
                "stack1= " + stack1 +
                ", stack2= " + stack2 +
                " }";
    }
}