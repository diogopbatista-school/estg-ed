package Collections.MainTests.Lists;

import Collections.Exceptions.ElementNotFoundException;
import Collections.Exceptions.EmptyCollectionException;
import Collections.Lists.DoublyLinkedUnorderedList;
import Collections.Lists.UnorderedListADT;

import java.util.Iterator;

public class DoublyLinkedUnorderedListMain {
    public static void main(String[] args) {
        UnorderedListADT<Integer> list = new DoublyLinkedUnorderedList<>();

        try {
            // Adding elements to the front
            System.out.println("Adding elements to the front of the list:");
            list.addToFront(30);
            list.addToFront(10);
            list.addToFront(20);
            System.out.println("List after adding elements to the front: " + list);

            // Adding elements to the rear
            System.out.println("\nAdding elements to the rear of the list:");
            list.addToRear(40);
            list.addToRear(50);
            System.out.println("List after adding elements to the rear: " + list);

            // Adding an element after a specific target
            System.out.println("\nAdding an element (25) after a specific target (20):");
            list.addAfter(25, 20);
            System.out.println("List after adding 25 after 20: " + list);

            // Removing the first element
            System.out.println("\nRemoving the first element:");
            list.removeFirst();
            System.out.println("List after removing the first element: " + list);

            // Removing the last element
            System.out.println("\nRemoving the last element:");
            list.removeLast();
            System.out.println("List after removing the last element: " + list);

            // Removing a specific element
            System.out.println("\nRemoving a specific element (25):");
            list.remove(25);
            System.out.println("List after removing the element 25: " + list);

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
            Iterator<Integer> iterator = list.iterator();
            while (iterator.hasNext()) {
                System.out.print(iterator.next() + " ");
            }
            System.out.println();

        } catch (EmptyCollectionException | ElementNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}