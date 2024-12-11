package Collections.MainTests.Lists;

import Collections.Exceptions.ElementNotFoundException;
import Collections.Exceptions.EmptyCollectionException;
import Collections.Exceptions.NonComparableElementException;
import Collections.Lists.ArrayOrderedList;
import Collections.Lists.OrderedListADT;

import java.util.Iterator;

/**
 * ArrayOrderedListMain tests the ArrayOrderedList class.
 */
public class ArrayOrderedListMain {
    /**
     * Main method for testing the ArrayOrderedList class.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        OrderedListADT<Integer> list = new ArrayOrderedList<>();

        try {
            // Adding elements
            System.out.println("Adding elements to the list:");
            list.add(30);
            list.add(10);
            list.add(20);
            System.out.println("List after adding elements: " + list);

            // Removing the first element
            System.out.println("\nRemoving the first element:");
            list.removeFirst();
            System.out.println("List after removing the first element: " + list);

            // Removing the last element
            System.out.println("\nRemoving the last element:");
            list.removeLast();
            System.out.println("List after removing the last element: " + list);

            // Adding more elements
            System.out.println("\nAdding more elements to the list:");
            list.add(40);
            list.add(50);
            System.out.println("List after adding more elements: " + list);

            // Removing a specific element
            System.out.println("\nRemoving a specific element (40):");
            list.remove(40);
            System.out.println("List after removing the element 40: " + list);

            // Checking if the list contains an element
            System.out.println("\nChecking if the list contains 50:");
            boolean contains = list.contains(50);
            System.out.println("List contains 50: " + contains);

            // Getting the first and last elements
            System.out.println("\nGetting the first and last elements:");
            Integer first = list.first();
            Integer last = list.last();
            System.out.println("First element: " + first);
            System.out.println("Last element: " + last);

            // Iterating over the list
            System.out.println("\nIterating over the list:");
            Iterator<Integer> iterator = list.iterator();
            while (iterator.hasNext()) {
                System.out.print(iterator.next() + " ");
            }
            System.out.println();

        } catch (EmptyCollectionException | ElementNotFoundException | NonComparableElementException e) {
            System.out.println(e.getMessage());
        }
    }
}