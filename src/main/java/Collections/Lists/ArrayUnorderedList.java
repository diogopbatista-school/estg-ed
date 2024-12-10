package Collections.Lists;

import Collections.Exceptions.ElementNotFoundException;
import Collections.Exceptions.EmptyCollectionException;

/**
 * A generic class that implements an ordered list using arrays.
 *
 * @param <T> the type of the stored element.
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {

    /**
     * Default constructor for an ArrayUnorderedList
     */
    public ArrayUnorderedList() {
        super();
    }

    /**
     * Constructor for an ArrayUnorderedList with an initial capacity
     *
     * @param initialCapacity the capacity of the array
     */
    public ArrayUnorderedList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Method that shifts all elements to the right starting from a specific index
     *
     * @param index the index to start shifting elements
     */
    private void shiftElementRight(int index) {
        for (int i = size(); i > index; i--) {
            list[i] = list[i - 1];
        }
    }

    /**
     * Method that adds a specific element to the front of this list
     *
     * @param element the element to be added to the front of this list
     */
    @Override
    public void addToFront(T element) {
        if (size() == list.length) {
            expandCapacity();
        }

        shiftElementRight(0);

        list[0] = element;
        size++;
        modCount++;
    }

    /**
     * Method that adds a specific element to the rear of this list
     *
     * @param element the element to be added to the rear of this list
     */
    @Override
    public void addToRear(T element) {
        if (size == list.length) {
            expandCapacity();
        }

        list[size] = element;
        size++;
        modCount++;
    }

    /**
     * Method that adds a specific element after a target element
     *
     * @param element the element to be added after the target
     * @param target  the target is the item that the element is added after
     * @throws EmptyCollectionException if the list is empty
     * @throws ElementNotFoundException if the target is not found
     */
    @Override
    public void addAfter(T element, T target) throws EmptyCollectionException, ElementNotFoundException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }

        if (size() == list.length) {
            expandCapacity();
        }

        int index = 0;
        while (index < size() && !list[index].equals(target)) {
            index++;
        }

        if (index == size()) {
            throw new ElementNotFoundException("Element not found");
        }

        shiftElementRight(index);

        list[index + 1] = element;
        size++;
        modCount++;
    }
}
