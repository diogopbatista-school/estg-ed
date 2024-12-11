package Collections.MainTests.Queue;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Queues.LinkedQueue;

/**
 * LinkedQueueMain tests the LinkedQueue class.
 */
public class LinkedQueueMain {
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        LinkedQueue<Integer> queue = new LinkedQueue<>();

        // Test isEmpty and size methods
        System.out.println("Is queue empty? " + queue.isEmpty());
        System.out.println("Size of queue: " + queue.size());

        // Enqueue elements
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        // Test isEmpty and size methods again
        System.out.println("Is queue empty? " + queue.isEmpty());
        System.out.println("Size of queue: " + queue.size());

        // Test first method
        try {
            System.out.println("First element: " + queue.first());
        } catch (EmptyCollectionException e) {
            System.out.println(e.getMessage());
        }

        // Test dequeue method
        try {
            System.out.println("Dequeued element: " + queue.dequeue());
            System.out.println("Dequeued element: " + queue.dequeue());
        } catch (EmptyCollectionException e) {
            System.out.println(e.getMessage());
        }

        // Test size and first method again
        System.out.println("Size of queue: " + queue.size());
        try {
            System.out.println("First element: " + queue.first());
        } catch (EmptyCollectionException e) {
            System.out.println(e.getMessage());
        }

        // Test toString method
        System.out.println("Queue: " + queue.toString());

        // Test dequeue method until empty
        try {
            System.out.println("Dequeued element: " + queue.dequeue());
            System.out.println("Dequeued element: " + queue.dequeue()); // This should throw an exception
        } catch (EmptyCollectionException e) {
            System.out.println(e.getMessage());
        }

        // Final state of the queue
        System.out.println("Final queue: " + queue.toString());
    }
}