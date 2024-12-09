package Collections.Lists;

import Collections.Exceptions.*;
import Collections.Exceptions.UnsupportedOperationException;
import Collections.Nodes.DoublyNode;

import java.util.Iterator;


/**
 * A generic abstract class that implements a doubly linked list.
 * @param <T> the type of the stored element.
 *
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public abstract class DoublyLinkedList<T> implements ListADT<T>, Iterable<T> {

    /**
     * The head and tail of the list
     */
    protected DoublyNode<T> head, tail;

    /**
     * The size of the list and the number of modifications
     */
    protected int count, modCount;

    /**
     * Default constructor for a DoublyLinkedList
     */
    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.count = 0;
        this.modCount = 0;
    }

    /**
     * Method that removes the first element from this list
     *
     * @return the element to be added to the front of this list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("The list is empty");
        }

        T removed = head.getElement();

        head = head.getNext();
        count--;

        if (isEmpty()) {
            tail = null;
        } else {
            head.setPrevious(null);
        }

        modCount++;
        return removed;
    }

    /**
     * Method that removes the last element from this list
     *
     * @return the removed element
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("The list is empty");
        }
        T removed = tail.getElement();
        tail = tail.getPrevious();
        count--;
        if (isEmpty()) {
            head = null;
        } else {
            tail.setNext(null);
        }
        modCount++;
        return removed;
    }

    private  DoublyNode<T> findCurrentNode(T element){
        DoublyNode<T> current = head;
        boolean found = false;
        while (current != null && !found) {
            if (element.equals(current.getElement())) {
                found = true;
            } else {
                current = current.getNext();
            }
        }
        if(!found){
            return null;
        }else{
            return current;
        }
    }

    /**
     * Method that removes a specific element from this list
     *
     * @param element the element to be removed and returned from the list
     * @return the removed element
     * @throws EmptyCollectionException if the list is empty
     * @throws ElementNotFoundException if the element is not found
     */
    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (isEmpty()) {
            throw new EmptyCollectionException("The list is empty");
        }

        DoublyNode<T> current = findCurrentNode(element);

        if (current == null) {
            throw new ElementNotFoundException("Element not found");
        }
        if (size() == 1) {
            head = tail = null;
        } else if (current.equals(head)) {
            head = current.getNext();
            head.setPrevious(null);
        } else if (current.equals(tail)) {
            tail = current.getPrevious();
            tail.setNext(null);
        } else {
            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());
        }
        count--;
        modCount++;
        return current.getElement();
    }

    /**
     * Method that gets the first element of the list
     *
     * @return the first element of the list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("The list is empty");
        }
        return head.getElement();
    }

    /**
     * Method that gets the last element of the list
     * @return the last element of the list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("The list is empty");
        }
        return tail.getElement();
    }

    /**
     * Method that checks if the list contains a target element
     * @param target the target that is being sought in the list
     * @return {@true} if the target element is on the list and {@false} otherwise
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public boolean contains(T target) throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("The list is empty");
        }

        DoublyNode<T> current = head;
        while (current != null) {
            if (target.equals(current.getElement())) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    /**
     * Method that checks if the list is empty
     * @return {@true} if the list is empty and {@false} otherwise
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Method that returns the size of the list
     * @return the size of the list
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Method that returns a string representation of the list
     * @return a string representation of the list
     */
    @Override
    public String toString() {
        String result = " { ";
        if (!isEmpty()) {
            DoublyNode<T> current = head;
            while (current != null) {
                result += current.getElement() + " ";
                current = current.getNext();
            }
        }
        return result + "}";
    }

    /**
     * Method that returns an iterator for the list
     * @return an iterator for the list
     */
    @Override
    public Iterator<T> iterator() {
        return new BasicIterator<T>(){};
    }

    /**
     * Inner class that implements an iterator for the list
     * @param <E> the type of the stored element
     */
    private class BasicIterator<E> implements Iterator<T> {

        /**
         * The current node of the iterator
         */
        private DoublyNode<T> current;

        /**
         * The expected number of modifications
         */
        private int expectedModCount;

        /**
         * A boolean that checks if it is ok to remove the element
         */
        private boolean okToRemove;

        /**
         * Default constructor for a BasicIterator
         */
        public BasicIterator() {
            this.current = head;
            this.expectedModCount = modCount;
            this.okToRemove = false;
        }

        /**
         * Method that checks if there is a next element
         * @return {@true} if there is a next element and {@false} otherwise
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Method that returns the next element in the list
         * @return the next element in the list
         */
        @Override
        public T next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new UnsupportedOperationException();
            }
            T result = current.getElement();
            current = current.getNext();
            okToRemove = true;
            return result;
        }

        /**
         * Method that removes the last element returned by the iterator
         */
        @Override
        public void remove() {
            if (modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!okToRemove) {
                throw new IllegalStateException();
            }
            DoublyLinkedList.this.remove(current.getPrevious().getElement());
            expectedModCount++;
            okToRemove = false;
        }
    }
}