package Collections.Trees;

import Collections.Exceptions.UnsupportedOperationException;
import Collections.Nodes.NodeAVL;

/**
 * This class represents an AVLTree for the exercise of the lesson
 * This exercise was made with some small helps of outside sources
 * like stackoverflow and geeksforgeeks
 * We won´t use this code for our project but just to try the AVLTree
 * like the professor asked us to do
 * @param <T> the type of the stored element
 *
 *
 */
public class AVLTree<T> extends LinkedBinarySearchTree<T> {

    /**
     * Default constructor for an AVLTree.
     */
    public AVLTree() {
        super();
    }

    /**
     * Constructor for an AVLTree with a root element.
     * @param element the element to be added to the root of the tree
     */
    public AVLTree(T element) {
        super(element);
        root = new NodeAVL<>(element);
        size = 1;
    }


    /**
     * Method that returns the height of a node.
     * @param node the node to get the height
     * @return the height of the node
     */
    private int height(NodeAVL<T> node) {
        if (node == null) {
            return 0;
        }
        return node.getHeight();
    }

    /**
     * Method that returns the balance of a node.
     * @param node the node to get the balance
     * @return the balance of the node
     */
    private int getBalance(NodeAVL<T> node) {
        if (node == null) {
            return 0;
        }

        return height((NodeAVL<T>) node.getLeft()) - height((NodeAVL<T>) node.getRight());
    }

    /**
     * Method that rotates a node to the right.
     * @param y the node to rotate
     * @return the new root of the subtree
     */
    private NodeAVL<T> rotateRight(NodeAVL<T> y) {
        NodeAVL<T> x = (NodeAVL<T>) y.getLeft();
        NodeAVL<T> T2 = (NodeAVL<T>) x.getRight();


        x.setRight(y);
        y.setLeft(T2);


        y.setHeight(Math.max(height((NodeAVL<T>) y.getLeft()), height((NodeAVL<T>) y.getRight())) + 1);
        x.setHeight(Math.max(height((NodeAVL<T>) x.getLeft()), height((NodeAVL<T>) x.getRight())) + 1);


        return x;
    }


    /**
     * Method that rotates a node to the left.
     * @param x the node to rotate
     * @return the new root of the subtree
     */
    private NodeAVL<T> rotateLeft(NodeAVL<T> x) {
        NodeAVL<T> y = (NodeAVL<T>) x.getRight();
        NodeAVL<T> T2 = (NodeAVL<T>) y.getLeft();


        y.setLeft(x);
        x.setRight(T2);


        x.setHeight(Math.max(height((NodeAVL<T>) x.getLeft()), height((NodeAVL<T>) x.getRight())) + 1);
        y.setHeight(Math.max(height((NodeAVL<T>) y.getLeft()), height((NodeAVL<T>) y.getRight())) + 1);


        return y;
    }


    /**
     * Method that adds an element to this tree.
     * @param element the element to be added to this tree
     * @throws UnsupportedOperationException if the element is not comparable
     */
    @Override
    public void addElement(T element) throws UnsupportedOperationException {

        if (!(element instanceof Comparable)) {
            throw new UnsupportedOperationException();
        }


        if (isEmpty()) {
            root = new NodeAVL<>(element);
        } else {

            root = add((NodeAVL<T>) root, element);
        }
        size++;
    }

    /**
     * Method that adds an element to this tree.
     * @param node the node to add the element
     * @param element  the element to be added to this tree
     * @return the new root of the subtree
     */
    private NodeAVL<T> add(NodeAVL<T> node, T element) {

        if (node == null) {
            return new NodeAVL<>(element);
        }

        Comparable<T> comparableElement = (Comparable<T>) element;


        if (comparableElement.compareTo(node.getElement()) < 0) {
            node.setLeft(add((NodeAVL<T>) node.getLeft(), element));
        } else if (comparableElement.compareTo(node.getElement()) > 0) {
            node.setRight(add((NodeAVL<T>) node.getRight(), element));
        } else {
            return node;
        }


        node.setHeight(1 + Math.max(height((NodeAVL<T>) node.getLeft()), height((NodeAVL<T>) node.getRight())));


        int balance = getBalance(node);



        if (balance > 1 && comparableElement.compareTo(node.getLeft().getElement()) < 0) {
            return rotateRight(node);
        }


        if (balance < -1 && comparableElement.compareTo(node.getRight().getElement()) > 0) {
            return rotateLeft(node);
        }


        if (balance > 1 && comparableElement.compareTo(node.getLeft().getElement()) > 0) {
            node.setLeft(rotateLeft((NodeAVL<T>) node.getLeft()));
            return rotateRight(node);
        }


        if (balance < -1 && comparableElement.compareTo(node.getRight().getElement()) < 0) {
            node.setRight(rotateRight((NodeAVL<T>) node.getRight()));
            return rotateLeft(node);
        }

        return node;
    }
}
