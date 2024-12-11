package Collections.Lists;

import Collections.Exceptions.NonComparableElementException;
import Collections.Nodes.Node;

/**
 * A class that represents an ordered linked list.
 *
 * @param <T> the type of the stored element.
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class LinkedOrderedList<T> extends LinkedList<T> implements OrderedListADT<T> {

    /**
     * Default constructor for a LinkedOrderedList.
     */
    public LinkedOrderedList() {
        super();
    }

    /**
     * This method adds a specific element to the list
     *
     * @param element the element to be added to this list
     * @throws NonComparableElementException if the element is not comparable
     */
    public void add(T element) throws NonComparableElementException {
        if (!(element instanceof Comparable)) {
            throw new NonComparableElementException();
        }

        Comparable<T> comparableElement = (Comparable<T>) element;
        Node<T> newNode = new Node<>(element);
        if (isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            Node<T> current = head;
            Node<T> previous = null;
            while (current != null && comparableElement.compareTo(current.getElement()) > 0) {
                previous = current;
                current = current.getNext();
            }

            if (previous == null) {
                newNode.setNext(head);
                head = newNode;
            } else {
                newNode.setNext(current);
                previous.setNext(newNode);
            }

            if (newNode.getNext() == null) {
                tail = newNode;
            }
        }

        size++;
        modCount++;
    }
}
