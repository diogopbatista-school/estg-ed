package Collections.Lists;

import Collections.Exceptions.ElementNotFoundException;
import Collections.Exceptions.EmptyCollectionException;
import Collections.Nodes.Node;


public abstract class CircularLinkedList<T> implements ListADT<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;

    public CircularLinkedList() {
        this.front = null;
        this.size = 0;
    }

    private Node<T> findNode(T element){
        Node<T> current = this.front;
        while(current != rear && !current.getElement().equals(element)){
            current = current.getNext();
        }
        return current;
    }

    public T removeFirst() throws EmptyCollectionException{
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }

        T removed = this.front.getElement();
        this.front = this.front.getNext();
        rear.setNext(front);
        size--;
        return removed;
    }

    public T removeLast() throws EmptyCollectionException{
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }

        T removed = this.rear.getElement();

        Node<T> current = this.front;
        while (current.getNext() != rear) {
            current = current.getNext();
        }
        current.setNext(this.front);
        this.rear = current;
        this.size--;
        return removed;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }

        Node<T> current = findNode(element);

        if (current == this.rear && !current.getElement().equals(element)) {
            throw new ElementNotFoundException("Element not found");
        }

        if (current == this.front) {
            return removeFirst();
        } else if (current == this.rear) {
            return removeLast();
        }
        T result = current.getElement();
        Node<T> previous = this.front;
        while (previous.getNext() != current) {
            previous = previous.getNext();
        }
        previous.setNext(current.getNext());
        this.size--;
        return result;
        }

    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }

        return this.front.getElement();
    }

    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()){
            throw new EmptyCollectionException();
        }

        return rear.getElement();
    }

    @Override
    public boolean contains(T target) throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }

        Node<T> current = findNode(target);

        return current.getElement().equals(target);
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " { ";
        if (!isEmpty()) {
            Node<T> current = front;
            while (current != rear) {
                result += current.getElement() + " ";
                current = current.getNext();
            }
            result += current.getElement() + " ";
        }
        return result + "}";
    }
}
