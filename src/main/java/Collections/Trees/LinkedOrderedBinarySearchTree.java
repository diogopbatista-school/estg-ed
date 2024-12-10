package Collections.Trees;

import Collections.Exceptions.ElementNotFoundException;
import Collections.Exceptions.EmptyCollectionException;
import Collections.Exceptions.NonComparableElementException;
import Collections.Lists.OrderedListADT;
import Collections.Nodes.BinaryTreeNode;

import java.util.Iterator;

/**
 * LinkedOrderedBinarySearchTree represents a linked binary search tree implementation.
 *
 * @param <T> the type of the stored element.
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class LinkedOrderedBinarySearchTree<T> extends LinkedBinarySearchTree<T> implements OrderedListADT<T> {

    /**
     * Default constructor for a LinkedOrderedBinarySearchTree.
     */
    public LinkedOrderedBinarySearchTree() {
        super();
    }

    public LinkedOrderedBinarySearchTree(T element) {
        super(element);
    }

    /**
     * Removes and returns the first element from the tree.
     *
     * @return T the min element from this list
     * @throws EmptyCollectionException if the tree is empty
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {
        return removeMin();
    }

    /**
     * Removes and returns the last element from the tree.
     *
     * @return T the max element from this list
     * @throws EmptyCollectionException if the tree is empty
     */
    @Override
    public T removeLast() throws EmptyCollectionException {
        return removeMax();
    }

    /**
     * Removes and returns the specified element.
     *
     * @param element the element to be removed and returned from the tree
     * @return T the removed element
     * @throws EmptyCollectionException if the tree is empty
     * @throws ElementNotFoundException if the element is not in the tree
     */
    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        return removeElement(element);
    }

    /**
     * Returns a reference to the first element in this tree.
     *
     * @return T a reference to the first element in this tree
     * @throws EmptyCollectionException if the tree is empty
     */
    @Override
    public T first() throws EmptyCollectionException {
        return findMin();
    }

    /**
     * Returns a reference to the last element in this tree.
     *
     * @return T a reference to the last element in this tree
     * @throws EmptyCollectionException if the tree is empty
     */
    @Override
    public T last() throws EmptyCollectionException {
        return findMax();
    }

    /**
     * Returns an iterator for the elements in this tree.
     *
     * @return Iterator an iterator over the elements in this tree
     */
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

    /**
     * Adds the specified element to this tree at the proper location according to its value.
     *
     * @param element the element to be added to this tree
     * @throws NonComparableElementException if the element is not Comparable
     */
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


