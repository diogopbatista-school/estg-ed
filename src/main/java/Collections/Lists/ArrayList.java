package Collections.Lists;

import Collections.Exceptions.ElementNotFoundException;
import Collections.Exceptions.EmptyCollectionException;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * A generic abstract class that implements a list using arrays.
 *
 * @param <T> the type of the stored element.
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public abstract class ArrayList<T> implements ListADT<T>, Iterable<T> {

    /**
     * Default number to inicialize the array
     */
    private final static int DEFAULT_CAPACITY = 100;

    /**
     * The generic array list
     */
    protected T[] list;

    /**
     * The size of the array and the number of modifications
     */
    protected int size, modCount;

    /**
     * Constructor for an ArrayList with a initial capacity
     *
     * @param initialCapacity the capacity of the array
     */
    public ArrayList(int initialCapacity) {
        this.list = (T[]) (new Object[initialCapacity]);
        this.size = 0;
        this.modCount = 0;
    }

    /**
     * Constructor for an ArrayList with a default capacity
     */
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * This method expands the capacity of the array
     */
    protected void expandCapacity() {
        T[] newList = (T[]) (new Object[this.list.length * 2]);
        for (int i = 0; i < this.list.length; i++) {
            newList[i] = this.list[i];
        }
        this.list = newList;
    }

    /**
     * Method that return the index in the array of a target element
     *
     * @param target the target element
     * @return the index of the target element
     */
    private int findIndex(T target) {
        int index = 0;
        while (index < this.size && !this.list[index].equals(target)) {
            index++;
        }
        return index;
    }

    /**
     * This method removes an element from the array and resizes it
     *
     * @param index the index of the element to be removed
     * @return the removed element
     */
    private T resizeArray(int index) {
        T removed = this.list[index];
        for (int i = index; i < this.size - 1; i++) {
            this.list[i] = this.list[i + 1];
        }
        return removed;
    }

    /**
     * Removes the first element of the array
     *
     * @return the removed element
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }

        T removed = resizeArray(0);

        this.list[list.length - 1] = null;
        this.size--;
        this.modCount++;
        return removed;

    }

    /**
     * Removes the last element of the array
     *
     * @return the removed element
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }

        T removed = this.list[this.size - 1];
        this.list[this.size - 1] = null;
        this.size--;
        this.modCount++;
        return removed;
    }

    /**
     * Removes an element from the list
     *
     * @param element the element to be removed and returned from the list
     * @return the removed element
     * @throws EmptyCollectionException if the list is empty
     * @throws ElementNotFoundException if the element is not found
     */
    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }

        int index = findIndex(element);

        if (index == this.size) {
            throw new ElementNotFoundException("Element not found");
        }

        T removed = resizeArray(index);

        this.list[this.size - 1] = null;
        this.size--;
        this.modCount++;
        return removed;

    }

    /**
     * Method that returns the first element of the array list
     *
     * @return the first element of the array list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }
        return this.list[0];
    }

    /**
     * Method that returns the last element of the array list
     *
     * @return the last element of the array list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }
        return this.list[this.size - 1];
    }

    /**
     * Method that checks if the list contains a target element
     *
     * @param target the target that is being sought in the list
     * @return {@true} if the target element is on the list and {@false} otherwise
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public boolean contains(T target) throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }

        int index = findIndex(target);

        return index < this.size;
    }

    /**
     * Method that checks if the list is empty
     *
     * @return {@true} if the list is empty and {@false} otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Method that returns the size of the list
     *
     * @return the size of the list
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Method that returns the list as a string
     *
     * @return the list as a string
     */
    @Override
    public String toString() {
        String result = " { ";
        if (!isEmpty()) {
            for (int i = 0; i < size(); i++) {
                result += list[i] + " ";
            }
        }
        return result + "}";
    }

    /**
     * Method that returns an iterator for the list
     *
     * @return an iterator for the list
     */
    @Override
    public Iterator<T> iterator() {
        return new BasicIterator<T>() {
        };
    }

    /**
     * Inner class that implements the iterator for the list
     *
     * @param <E> the type of the stored element
     */
    private abstract class BasicIterator<E> implements Iterator<T> {

        /**
         * The current index of the iterator
         */
        private int current;

        /**
         * The expected number of modifications
         */
        private int expectedModCount;

        /**
         * A boolean that checks if it is possible to remove an element
         */
        private boolean okToRemove;

        /**
         * Constructor for the iterator
         */
        public BasicIterator() {
            this.current = 0;
            this.expectedModCount = modCount;
            this.okToRemove = false;
        }

        /**
         * Method that checks if there is a next element in the list
         *
         * @return {@true} if there is a next element and {@false} otherwise
         */
        public boolean hasNext() {
            return this.current < size();
        }

        /**
         * Method that returns the next element in the list
         *
         * @return the next element in the list
         */
        public T next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }

            if (!hasNext()) {
                throw new UnsupportedOperationException();
            }

            okToRemove = true;
            return list[current++];
        }

        /**
         * Method that removes the current element from the list
         */
        public void remove() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (!okToRemove) {
                throw new IllegalStateException();
            }
            ArrayList.this.remove(list[current - 1]);
            current--;
            this.expectedModCount++;
            this.okToRemove = false;
        }
    }
}