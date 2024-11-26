package Collections.Trees;

import Collections.Exceptions.ElementNotFoundException;
import Collections.Exceptions.EmptyCollectionException;
import Collections.Exceptions.NonComparableElementException;
import Collections.Lists.ArrayUnorderedList;

import java.util.Iterator;


public class ArrayBinarySearchTree<T> extends ArrayBinaryTree<T> implements BinarySearchTreeADT<T> {

    /**
     * Value to expand the tree capacity.
     */
    private final static int EXPAND_CAPACITY = 2;

    /**
     * Number of nodes in the tree.
     */
    protected int height;

    /**
     * The maximum index of the tree.
     */
    protected int maxIndex;

    /**
     * Constructor for the ArrayBinarySearchTree.
     */
    public ArrayBinarySearchTree() {
        super();
        height = 0;
        maxIndex = -1;
    }

    /**
     * Constructor for the ArrayBinarySearchTree with an element.
     * @param element the element to add to the tree.
     */
    public ArrayBinarySearchTree(T element) {
        super(element);
        height = 1;
        maxIndex = 0;
    }

    /**
     * Method to expand the capacity of the tree.
     */
    private void expandCapacity(){
        T[] expand = (T[])(new Object[tree.length * EXPAND_CAPACITY]);
        for (int i = 0; i < tree.length; i++)
            expand[i] = tree[i];
        tree = expand;
    }

    /**
     * Adds the specified element to the proper location in this tree.
     *
     * @param element the element to be added to this tree
     * @throws NonComparableElementException if the element is not an instance of Comparable
     */
    @Override
    public void addElement (T element)
    {
        if (tree.length < maxIndex*2+3)
            expandCapacity();
        Comparable<T> tempelement = (Comparable<T>)element;
        if (isEmpty())
        {
            tree[0] = element;
            maxIndex = 0;
        }
        else{
            boolean added = false;
            int currentIndex = 0;
            while (!added)
            {
                if (tempelement.compareTo((tree[currentIndex]) ) < 0)
                {
/** go left */
                    if (tree[currentIndex*2+1] == null) {
                        tree[currentIndex*2+1] = element;
                        added = true;
                        if (currentIndex*2+1 > maxIndex)
                            maxIndex = currentIndex*2+1;
                    }
                    else
                        currentIndex = currentIndex*2+1;
                }else {
/** go right */
                    if (tree[currentIndex*2+2] == null) {
                        tree[currentIndex*2+2] = element;
                        added = true;
                        if (currentIndex*2+2 > maxIndex)
                            maxIndex = currentIndex*2+2;
                    }
                    else
                        currentIndex = currentIndex*2+2;
                }
            }
        }
        height = (int)(Math.log(maxIndex + 1) / Math.log(2)) + 1;
        size++;
    }

    /**
     * Removes the node specified for removal and shifts the tree array accordingly.
     *
     * @param targetIndex the node to be removed
     */
    protected void replace(int targetIndex) {
        int currentIndex, parentIndex, temp, oldIndex, newIndex;
        ArrayUnorderedList<Integer> oldlist = new ArrayUnorderedList<>();
        ArrayUnorderedList<Integer> newlist = new ArrayUnorderedList<>();
        ArrayUnorderedList<Integer> templist = new ArrayUnorderedList<>();
        Iterator<Integer> oldIt, newIt;

        /**
         * if target node has no children
         */
        if ((targetIndex * 2 + 1 >= tree.length) || (targetIndex * 2 + 2 >= tree.length)) {
            tree[targetIndex] = null;
        } /**
         * if target node has no children
         */
        else if ((tree[targetIndex * 2 + 1] == null) && (tree[targetIndex * 2 + 2] == null)) {
            tree[targetIndex] = null;
        } /**
         * if target node only has a left child
         */
        else if ((tree[targetIndex * 2 + 1] != null) && (tree[targetIndex * 2 + 2] == null)) {
            /**
             * fill newlist with indices of nodes that will replace the corresponding indices in oldlist
             */
            currentIndex = targetIndex * 2 + 1;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                currentIndex = (templist.removeFirst());
                newlist.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    templist.addToRear(currentIndex * 2 + 1);
                    templist.addToRear(currentIndex * 2 + 2);
                }
            }

            /**
             * fill oldlist
             */
            currentIndex = targetIndex;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                currentIndex = (templist.removeFirst());
                oldlist.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    templist.addToRear(currentIndex * 2 + 1);
                    templist.addToRear(currentIndex * 2 + 2);
                }
            }

            /**
             * do replacement
             */
            oldIt = oldlist.iterator();
            newIt = newlist.iterator();
            while (newIt.hasNext()) {
                oldIndex = oldIt.next();
                newIndex = newIt.next();
                tree[oldIndex] = tree[newIndex];
                tree[newIndex] = null;
            }
        } /**
         * if target node only has a right child
         */
        else if ((tree[targetIndex * 2 + 1] == null) && (tree[targetIndex * 2 + 2] != null)) {
            /**
             * fill newlist with indices of nodes that will replace the corresponding indices in oldlist
             */
            currentIndex = targetIndex * 2 + 2;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                currentIndex = (templist.removeFirst());
                newlist.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    templist.addToRear(currentIndex * 2 + 1);
                    templist.addToRear(currentIndex * 2 + 2);
                }
            }

            /**
             * fill oldlist
             */
            currentIndex = targetIndex;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                currentIndex = (templist.removeFirst());
                oldlist.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    templist.addToRear(currentIndex * 2 + 1);
                    templist.addToRear(currentIndex * 2 + 2);
                }
            }

            /**
             * do replacement
             */
            oldIt = oldlist.iterator();
            newIt = newlist.iterator();
            while (newIt.hasNext()) {
                oldIndex = oldIt.next();

                newIndex = newIt.next();
                tree[oldIndex] = tree[newIndex];
                tree[newIndex] = null;
            }
        } /**
         * if target node has two children
         */
        else {
            currentIndex = targetIndex * 2 + 2;

            while (tree[currentIndex * 2 + 1] != null) {
                currentIndex = currentIndex * 2 + 1;
            }

            tree[targetIndex] = tree[currentIndex];

            /**
             * the index of the root of the subtree to be replaced
             */
            int currentRoot = currentIndex;

            /**
             * if currentIndex has a right child
             */
            if (tree[currentRoot * 2 + 2] != null) {
                /**
                 * fill newlist with indices of nodes that will replace the corresponding indices in oldlist
                 */
                currentIndex = currentRoot * 2 + 2;
                templist.addToRear(currentIndex);
                while (!templist.isEmpty()) {
                    currentIndex = (templist.removeFirst());
                    newlist.addToRear(currentIndex);
                    if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                        templist.addToRear(currentIndex * 2 + 1);
                        templist.addToRear(currentIndex * 2 + 2);
                    }
                }

                /**
                 * fill oldlist
                 */
                currentIndex = currentRoot;
                templist.addToRear(currentIndex);
                while (!templist.isEmpty()) {
                    currentIndex = (templist.removeFirst());
                    oldlist.addToRear(currentIndex);
                    if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                        templist.addToRear(currentIndex * 2 + 1);
                        templist.addToRear(currentIndex * 2 + 2);
                    }
                }

                /**
                 * do replacement
                 */
                oldIt = oldlist.iterator();
                newIt = newlist.iterator();
                while (newIt.hasNext()) {
                    oldIndex = oldIt.next();
                    newIndex = newIt.next();

                    tree[oldIndex] = tree[newIndex];
                    tree[newIndex] = null;
                }
            } else {
                tree[currentRoot] = null;
            }
        }
    }


    private int findIndex(Comparable<T> targetElement, int index) {
        if (tree[index] == null) {
            throw new ElementNotFoundException("");
        }

        if (targetElement.compareTo(tree[index]) == 0) {
            return index;
        } else if (targetElement.compareTo(tree[index]) < 0) {
            return findIndex(targetElement, index * 2 + 1);
        } else {
            return findIndex(targetElement, index * 2 + 2);
        }
    }

    /**
     * Removes a specified element from the tree.
     *
     * @param targetElement The element to be removed from the tree.
     * @return The element that was removed.
     * @throws EmptyCollectionException If the tree is empty.
     * @throws ElementNotFoundException If the target element is not found in the tree.
     */
    @Override
    public T removeElement(T targetElement) throws ElementNotFoundException {
        T result = null;
        boolean found = false;

        if (isEmpty())
            return result;

        for (int i = 0; (i <= maxIndex) && !found; i++) {
            if ((tree[i] != null) && targetElement.equals(tree[i]))
            {
                found = true;
                result = tree[i] ;
                replace(i);
                size--;
            }
        }

        if (!found)
            throw new ElementNotFoundException("Element not found in the binary tree");

        int temp = maxIndex;
        maxIndex = -1;
        for (int i = 0; i <= temp; i++)
            if (tree[i] != null)
                maxIndex = i;

        height = (int)(Math.log(maxIndex + 1) / Math.log(2)) + 1;

        return result;
    }

    /**
     * Method to all occurrences of the specified element from the tree.
     * @param targetElement the element that the list will
     * have all instances of it removed
     */
    public void removeAllOccurrences (T targetElement) throws ElementNotFoundException {
        removeElement(targetElement);
        while (contains(targetElement))
            removeElement(targetElement);

    }

    /**
     * Removes the minimum element from the tree.
     * @return the minimum element that was removed
     * @throws EmptyCollectionException if the tree is empty
     */
    @Override
    public T removeMin() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        return removeElement(findMin());
    }

    /**
     * Removes the maximum element from the tree.
     * @return the maximum element that was removed
     * @throws EmptyCollectionException if the tree is empty
     */
    @Override
    public T removeMax() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        return removeElement(findMax());
    }

    /**
     * Returns a reference to the smallest element in the tree.
     * @return a reference to the smallest element in the tree
     * @throws EmptyCollectionException if the tree is empty
     */
    @Override
    public T findMin() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Array is empty");
        }
        T min = tree[0];
        for (int i = 0; i <= maxIndex; i++) {
            if ((tree[i] != null) && (((Comparable<T>) tree[i]).compareTo(min) < 0)) {
                min = tree[i];
            }
        }
        return min;
    }

    /**
     * Returns a reference to the largest element in the tree.
     * @return a reference to the largest element in the tree
     */
    @Override
    public T findMax() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        T max = tree[0];
        for (int i = 0; i <= maxIndex; i++) {
            if ((tree[i] != null) && (((Comparable<T>) tree[i]).compareTo(max) > 0)) {
                max = tree[i];
            }
        }
        return max;
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] != null) {
                result += tree[i].toString() + " ";
            }
        }
        return result;
    }


}

