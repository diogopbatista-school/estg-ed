package Collections.Lists;

import Collections.Exceptions.ElementNotFoundException;
import Collections.Exceptions.EmptyCollectionException;
import Collections.Nodes.Node;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * A class that represents a linked list.
 *
 * @param <T> the type of the stored element.
 * @author ESTG LSIRC 8230367 - Diogo Pereira Batista
 */
public abstract class LinkedList<T> implements ListADT<T> {

    /**
     * The head node of the list.
     */
    protected Node<T> head;

    /**
     * The tail node of the list.
     */
    protected  Node<T> tail;

    /**
     * The size of the list.
     */
    protected int size;

    /**
     *The number of modifications
     */
    protected int modCount;

    /**
     * Constructor for an empty linked list.
     */
    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Method to obtain the element in the head node
     *
     * @return - The type of the stored element in the head node
     * @throws EmptyCollectionException - If the list in empty
     */
    @Override
    public T first() throws EmptyCollectionException{
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }

        return this.head.getElement();
    }

    /**
     * Method to obtain the element in the tail node.
     *
     * @return - The type of the stored element in the head node
     * @throws EmptyCollectionException - If the list in empty
     */
    @Override
    public T last() throws EmptyCollectionException{
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }

        return this.tail.getElement();
    }

    /**
     * Method to verify if the list is empty
     *
     * @return - {@code true} if is empty ; {@code false} otherwise .
     */
    @Override
    public boolean isEmpty(){
        return this.size == 0;
    }

    /**
     * Method to obtain the size of the list.
     * @return - The size of the list .
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Method to verify if the list has a specific element
     *
     * @param target - The type of the stored element
     * @return - True if exists in the list , false otherwise.
     * @throws EmptyCollectionException - If the list is empty.
     */
    @Override
    public boolean contains(T target) throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }

        Node<T> current = this.head;

        while (current != null){
            if(current.getElement().equals(target)){
                return true;
            }
            current = current.getNext();
        }

        return false;
    }

    /**
     * Method to remove the first node of the list
     *
     * @return - The removed node
     * @throws EmptyCollectionException - If the list is empty
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {

        if(isEmpty()){
            throw new EmptyCollectionException("List is empty");
        }

        T removed = this.head.getElement();
        this.head = this.head.getNext();
        this.size--;
        this.modCount++;

        return removed;
    }

    /**
     * Method to remove the last node of the list.
     * @return - The removed node .
     * @throws EmptyCollectionException - If the list is empty .
     */
    @Override
    public T removeLast() throws EmptyCollectionException {

        if(isEmpty()){
            throw new EmptyCollectionException("List is empty");
        }

        T removed = this.tail.getElement();
        Node<T> current = this.head;

        while(current.getNext() != this.tail){
            current = current.getNext();
        }

        current.setNext(null);
        this.tail = current;
        this.size--;
        this.modCount++;

        return removed;
    }

    /**
     * Method to remove a node with a specific element
     *
     * @param element - The element we want to remove
     * @return the removed element .
     * @throws EmptyCollectionException if the list is empty
     * @throws ElementNotFoundException if the element is not into the list
     */
    @Override
    public T remove (T element) throws EmptyCollectionException, ElementNotFoundException{
        if(isEmpty()){
            throw new EmptyCollectionException("List is empty");
        }

        if(!contains(element)){
            throw new EmptyCollectionException("Element not found");
        }

        T removed = null;
        Node<T> current = this.head;

        if(this.head.getElement().equals(element)){
           removed = removeFirst();
        }
        else if(this.tail.getElement().equals(element)){
           removed = removeLast();
        }
        else{
            while(!current.getNext().getElement().equals(element)){
                current = current.getNext();
            }

            removed = current.getNext().getElement();
            current.setNext(current.getNext().getNext());
            this.size--;
            this.modCount++;
        }

        return removed;
    }

    /**
     * Converts the linked list into an array of the same type as the elements
     * in the list. The elements in the array will be in the same order as they
     * appear in the list.
     *
     * @return an array containing all the elements of the list in order.
     */
    public T[] toArray() {
        T[] array = (T[]) new Comparable[size()];
        Node<T> current = this.head;
        for (int i = 0; i < size(); i++) {
            current = current.getNext();
            array[i] = current.getElement();
        }
        return array;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public void replace(T existingElement, T newElement) throws EmptyCollectionException, ElementNotFoundException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }
        if (!contains(existingElement)) {
            throw new ElementNotFoundException("Element not found");
        }
        Node<T> current = head;
        while (current != null) {
            if (current.getElement().equals(existingElement)) {
                current.setElement(newElement);
            }
            current = current.getNext();
        }
    }


    /**
     * Returns a string representation of the list
     *
     * @return a string containing all elements of the list
     */
    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " { ";
        if (!isEmpty()) {
            Node<T> current = head;
            while (current != null) {
                result += current.getElement() + " ";
                current = current.getNext();
            }
        }
        else{
            return "No data";
        }
        return result + "}";
    }

    /**
     * Returns an iterator for the list
     * @return an iterator for the list
     */
    @Override
    public Iterator<T> iterator() {
        return new BasicIterator<T>() {};
    }

    /**
     * Inner class that represents an iterator for the list
     * @param <E> the type of the stored element
     */
    private abstract class BasicIterator<E> implements Iterator<T>{

        /**
         * The current node
         */
        private Node<T> current;

        /**
         * The expected number of modifications
         */
        private int expectedModCount;

        /**
         * boolean to check if it is possible to remove the element
         */
        private boolean okToRemove;

        /**
         * Constructor for the iterator
         */
        public BasicIterator(){
            this.current = head;
            this.expectedModCount = modCount;
            this.okToRemove = false;
        }

        /**
         * Method to verify if there is a next element
         * @return - {@code true} if there is a next element ; {@code false} otherwise.
         */
        public boolean hasNext(){
            return this.current != null;
        }

        /**
         * Method to get the next element
         * @return - The next element
         */
        public T next(){
            if(modCount != expectedModCount){
                throw new ConcurrentModificationException();
            }

            if(!hasNext()){
                throw new UnsupportedOperationException();
            }

            T next = this.current.getElement();
            this.current = this.current.getNext();
            okToRemove = true;

            return next;
        }

        /**
         * Method to remove the current element
         */
        public void remove() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (!okToRemove) {
                throw new IllegalStateException();
            }
            LinkedList.this.remove(current.getElement());
            this.expectedModCount++;
            this.okToRemove = false;
        }
    }

}
