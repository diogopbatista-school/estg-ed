package Collections.Trees;

import Collections.Exceptions.ElementNotFoundException;
import Collections.Exceptions.EmptyCollectionException;
import Collections.Lists.OrderedListADT;
import Collections.Nodes.BinaryTreeNode;

import java.util.Iterator;

public class LinkedOrderedBinarySearchTree<T> extends LinkedBinarySearchTree<T> implements OrderedListADT<T> {


    public LinkedOrderedBinarySearchTree() {
        super();
    }

    public LinkedOrderedBinarySearchTree(T element) {
        super(element);
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        return removeMin();
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        return removeMax();
    }

    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        return removeElement(element);
    }

    @Override
    public T first() throws EmptyCollectionException {
        return findMin();
    }

    @Override
    public T last() throws EmptyCollectionException {
        return findMax();
    }

    @Override
    public Iterator<T> iterator() {
        return iteratorInOrder();
    }

    private void addElement(BinaryTreeNode<T> root, Comparable<T> comparableElement) {
        if (comparableElement.compareTo(root.getElement()) < 0) {
            if (root.getLeft() == null) {
                root.setLeft((BinaryTreeNode<T>) new BinaryTreeNode<>(comparableElement));
            } else {
                addElement(root.getLeft(), comparableElement);
            }
        } else {
            if (root.getRight() == null) {
                root.setRight((BinaryTreeNode<T>) new BinaryTreeNode<>(comparableElement));
            } else {
                addElement(root.getRight(), comparableElement);
            }
        }
    }

    @Override
    public void add(T element) throws ElementNotFoundException {
        if (!(element instanceof Comparable)) {
            throw new ElementNotFoundException("Element not found");
        }
        Comparable<T> comparableElement = (Comparable<T>) element;
        if (isEmpty()) {
            root = new BinaryTreeNode<>(element);
        } else {
            addElement(root, comparableElement);
        }
        size++;
    }
}


