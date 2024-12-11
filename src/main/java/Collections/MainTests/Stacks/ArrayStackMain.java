package Collections.MainTests.Stacks;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Stacks.ArrayStack;

/**
 * ArrayStackMain tests the ArrayStack class.
 */
public class ArrayStackMain {
    /**
     * Main method for testing the ArrayStack class.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        ArrayStack<Integer> stack = new ArrayStack<>();

        // Test isEmpty and size methods
        System.out.println("Is stack empty? " + stack.isEmpty());
        System.out.println("Size of stack: " + stack.size());

        // Push elements
        stack.push(1);
        stack.push(2);
        stack.push(3);

        // Test isEmpty and size methods again
        System.out.println("Is stack empty? " + stack.isEmpty());
        System.out.println("Size of stack: " + stack.size());

        // Test peek method
        try {
            System.out.println("Top element: " + stack.peek());
        } catch (EmptyCollectionException e) {
            System.out.println(e.getMessage());
        }

        // Test pop method
        try {
            System.out.println("Popped element: " + stack.pop());
            System.out.println("Popped element: " + stack.pop());
        } catch (EmptyCollectionException e) {
            System.out.println(e.getMessage());
        }

        // Test size and peek method again
        System.out.println("Size of stack: " + stack.size());
        try {
            System.out.println("Top element: " + stack.peek());
        } catch (EmptyCollectionException e) {
            System.out.println(e.getMessage());
        }

        // Test toString method
        System.out.println("Stack: " + stack.toString());

        // Test pop method until empty
        try {
            while (!stack.isEmpty()) {
                System.out.println("Popped element: " + stack.pop());
            }
            // This should throw an exception
            System.out.println("Popped element: " + stack.pop());
        } catch (EmptyCollectionException e) {
            System.out.println(e.getMessage());
        }

        // Final state of the stack
        System.out.println("Final stack: " + stack.toString());
    }
}