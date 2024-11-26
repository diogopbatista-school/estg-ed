package Collections.MainTests.Queue;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Queues.CircularArrayQueue;

public class CircularArrayQueueMain2 {
    public static void main(String[] args) {
        CircularArrayQueue<Integer> queue = new CircularArrayQueue<>();

        System.out.println("Is queue empty? " + queue.isEmpty());
        System.out.println("Size of queue: " + queue.size());

        // Enqueue elements
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);


        System.out.println("Is queue empty? " + queue.isEmpty());
        System.out.println("Size of queue: " + queue.size());


        try {
            System.out.println("First element: " + queue.first());
        } catch (EmptyCollectionException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("Dequeued element: " + queue.dequeue());
            System.out.println("Dequeued element: " + queue.dequeue());
        } catch (EmptyCollectionException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Size of queue: " + queue.size());
        try {
            System.out.println("First element: " + queue.first());
        } catch (EmptyCollectionException e) {
            System.out.println(e.getMessage());
        }

        // Test toString method
        System.out.println("Queue: " + queue.toString());


        try {
            System.out.println("Dequeued element: " + queue.dequeue());
            System.out.println("Dequeued element: " + queue.dequeue()); // This should throw an exception
        } catch (EmptyCollectionException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Final queue: " + queue.toString());
    }
}