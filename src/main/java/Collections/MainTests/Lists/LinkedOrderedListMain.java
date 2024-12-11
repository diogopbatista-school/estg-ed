package Collections.MainTests.Lists;

import Collections.Exceptions.NonComparableElementException;
import Collections.Lists.LinkedOrderedList;

import java.util.Iterator;

/**
 * LinkedOrderedListMain tests the LinkedOrderedList class.
 */
public class LinkedOrderedListMain {
    /**
     * Main method for testing the LinkedOrderedList class.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        LinkedOrderedList<Integer> list = new LinkedOrderedList<>();

        try {
            // Add elements to the list
            System.out.println("Adding 5 to the list.");
            list.add(5);
            System.out.println("List after adding 5: " + list);

            System.out.println("Adding 3 to the list.");
            list.add(3);
            System.out.println("List after adding 3: " + list);

            System.out.println("Adding 7 to the list.");
            list.add(7);
            System.out.println("List after adding 7: " + list);

            System.out.println("Adding 1 to the list.");
            list.add(1);
            System.out.println("List after adding 1: " + list);

            System.out.println("Adding 9 to the list.");
            list.add(9);
            System.out.println("List after adding 9: " + list);

            System.out.println("Adding 4 to the list.");
            list.add(4);
            System.out.println("List after adding 4: " + list);

            Iterator<Integer> iterator = list.iterator();
            while (iterator.hasNext()) {
                System.out.print(iterator.next() + " ");
            }

        } catch (NonComparableElementException e) {
            System.out.println(e.getMessage());
        }
    }
}