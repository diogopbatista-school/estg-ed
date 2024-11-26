package Collections.Lists;

import Collections.Nodes.DoublyNode;
import Collections.Exceptions.ElementNotFoundException;
import Collections.Exceptions.EmptyCollectionException;

/**
 * A generic class that implements an unordered list using doubly linked lists.
 * @param <T> the type of the stored element.
 * @author ESTG LSIRC 8230367 - Diogo Pereira Batista
 */
public class DoublyLinkedUnorderedList<T> extends DoublyLinkedList<T> implements UnorderedListADT<T> {

    /**
     * Default constructor for an DoublyLinkedUnorderedList
     */
        public DoublyLinkedUnorderedList() {
            super();
        }

    /**
     * Method that adds a specific element to the front of this list
     *
     * @param element the element to be added to the front of this list
     */
    @Override
        public void addToFront(T element) {
            DoublyNode<T> newNode = new DoublyNode<>(element);
            if (isEmpty()) {
                head = newNode;
                tail = newNode;
            } else {
                newNode.setNext(head);
                head.setPrevious(newNode);
                head = newNode;
            }
            count++;
            modCount++;
        }

        @Override
        public void addToRear(T element) {
            DoublyNode<T> newNode = new DoublyNode<>(element);

            if (isEmpty()) {
                head = newNode;
                tail = newNode;
            } else {
                newNode.setPrevious(tail);
                tail.setNext(newNode);
                tail = newNode;
            }

            count++;
            modCount++;
        }

        @Override
        public void addAfter(T element, T target) throws ElementNotFoundException, EmptyCollectionException {
            if (isEmpty()) {
                throw new EmptyCollectionException();
            }

            DoublyNode<T> current = head;

            while (current != null && !current.getElement().equals(target)) {
                current = current.getNext();
            }

            if(current == null){
                throw new ElementNotFoundException("Element not found");
            }

            if(current == tail){
                addToRear(element);
            }else{
                DoublyNode<T> newNode = new DoublyNode<>(element);
                newNode.setNext(current.getNext());
                newNode.setPrevious(current);
                current.getNext().setPrevious(newNode);
                current.setNext(newNode);
                count++;
                modCount++;
            }
        }


}
