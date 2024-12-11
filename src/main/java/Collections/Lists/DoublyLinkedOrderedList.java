package Collections.Lists;

import Collections.Exceptions.NonComparableElementException;
import Collections.Nodes.DoublyNode;

/**
 * A generic class that implements an ordered list using doubly linked lists.
 *
 * @param <T> the type of the stored element.
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class DoublyLinkedOrderedList<T> extends DoublyLinkedList<T> implements OrderedListADT<T> {

    /**
     * Default constructor for a DoublyLinkedOrderedList
     */
    public DoublyLinkedOrderedList() {
        super();
    }

    /**
     * Method that add a specific element to this list in the proper location
     *
     * @param element the element to be added to this list
     * @throws NonComparableElementException if the element is not comparable
     */
    @Override
    public void add(T element) throws NonComparableElementException {
        if (!(element instanceof Comparable)) {
            throw new NonComparableElementException();
        }

        Comparable<T> comparableElement = (Comparable<T>) element;
        DoublyNode<T> newNode = new DoublyNode<>(element);

        if (isEmpty()) {
            this.tail = this.head = newNode;
        } else {
            DoublyNode<T> current = this.head;
            DoublyNode<T> previous = null;

            while (current != null && comparableElement.compareTo(current.getElement()) > 0) {
                previous = current;
                current = current.getNext();
            }

            if (previous == null) { // para o inicio
                newNode.setNext(this.head);
                this.head.setPrevious(newNode);
                this.head = newNode;
            } else if (current == null) { // para o fim
                previous.setNext(newNode);
                newNode.setPrevious(previous);
                this.tail = newNode;
            } else { // para o meio
                previous.setNext(newNode);
                newNode.setPrevious(previous);
                newNode.setNext(current);
                current.setPrevious(newNode);
            }
        }
        this.count++;
        this.modCount++;
    }
}