package Collections.MainTests.Stacks;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Stacks.StackQueue;

/**
 * StackQueueMain tests the StackQueue class.
 */
public class StackQueueMain {
    /**
     * Main method for testing the StackQueue class.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        StackQueue<Integer> queue = new StackQueue<>();

        try {
            // Enqueue elements
            System.out.println("Enqueueing elements to the queue:");
            queue.enqueue(10);
            queue.enqueue(20);
            queue.enqueue(30);
            System.out.println("Queue after enqueueing elements: " + queue);

            // Dequeue an element
            System.out.println("\nDequeuing an element:");
            Integer dequeued = queue.dequeue();
            System.out.println("Dequeued element: " + dequeued);
            System.out.println("Queue after dequeuing an element: " + queue);

            // Peek at the first element
            System.out.println("\nPeeking at the first element:");
            Integer first = queue.first();
            System.out.println("First element: " + first);
            System.out.println("Queue after peeking at the first element: " + queue);

            // Check if the queue is empty
            System.out.println("\nChecking if the queue is empty:");
            boolean isEmpty = queue.isEmpty();
            System.out.println("Queue is empty: " + isEmpty);

            // Get the size of the queue
            System.out.println("\nGetting the size of the queue:");
            int size = queue.size();
            System.out.println("Queue size: " + size);

            // Enqueue more elements
            System.out.println("\nEnqueueing more elements to the queue:");
            queue.enqueue(40);
            queue.enqueue(50);
            System.out.println("Queue after enqueueing more elements: " + queue);

            // Dequeue all elements
            System.out.println("\nDequeuing all elements:");
            while (!queue.isEmpty()) {
                System.out.println("Dequeued element: " + queue.dequeue());
            }
            System.out.println("Queue after dequeuing all elements: " + queue);

        } catch (EmptyCollectionException e) {
            System.out.println(e.getMessage());
        }
    }
}