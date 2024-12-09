package Collections.Lists;

import Collections.Exceptions.NonComparableElementException;

/**
 * A generic class that implements an ordered list using arrays.
 * @param <T> the type of the stored element.
 *
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T> {

    /**
     * Default constructor for an ArrayOrderedList
     */
    public ArrayOrderedList() {
        super();
    }

    /**
     * Constructor for an ArrayOrderedList with a initial capacity
     * @param initialCapacity the capacity of the array
     */
    public ArrayOrderedList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Method that adds the specified element to this list
     * @param element the element to be added to this list
     * @throws NonComparableElementException if the element is not comparable
     */
    @Override
    public void add(T element) throws NonComparableElementException {
        if (!(element instanceof Comparable)) {
            throw new NonComparableElementException();
        }

        if (size() == list.length){
            expandCapacity();
        }

        int index = 0;

        while(index < size && ((Comparable<T>) element).compareTo(list[index]) > 0){
            index++;
        }

        for ( int i = size; i > index ; i--){
            list[i] = list[i - 1];
        }

        list[index] = element;
        size++;
        modCount++;
    }
}
