package Collections.MainTests.Trees;


import Collections.Trees.ArrayBinarySearchTree;

import Collections.Exceptions.ElementNotFoundException;
import Collections.Exceptions.EmptyCollectionException;
import Collections.Exceptions.NonComparableElementException;

import java.util.Iterator;

public class ArrayBinarySearchTreeMain {
    public static void main(String[] args) {
        try {
            // Cria uma árvore de busca binária com um elemento raiz
            ArrayBinarySearchTree<Integer> tree = new ArrayBinarySearchTree<>(1);

            // Adiciona elementos à árvore
            tree.addElement(5);
            tree.addElement(15);
            tree.addElement(3);
            tree.addElement(7);
            tree.addElement(12);
            tree.addElement(2);



            // Exibe a árvore
            System.out.println("Tree: " + tree.toString());

            // Testa o método getRoot
            System.out.println("Root: " + tree.getRoot());

            // Testa o método isEmpty
            System.out.println("Is tree empty? " + tree.isEmpty());

            // Testa o método size
            System.out.println("Size: " + tree.size());

            // Testa o método contains
            System.out.println("Contains 7? " + tree.contains(7));
            System.out.println("Contains 20? " + tree.contains(20));

            // Testa o método find
            try {
                System.out.println("Find 12: " + tree.find(12));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // Testa o método removeElement
            try {
                System.out.println("Remove 5: " + tree.removeElement(5));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // Testa o método removeAllOccurrences
            try {
                tree.removeAllOccurrences(7);
                System.out.println("Removed all occurrences of 7");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // Testa o método removeMin
            try {
                System.out.println("Remove Min: " + tree.removeMin());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // Testa o método removeMax
            try {
                System.out.println("Remove Max: " + tree.removeMax());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // Testa o método findMin
            try {
                System.out.println("Find Min: " + tree.findMin());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // Testa o método findMax
            try {
                System.out.println("Find Max: " + tree.findMax());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println("\nIterating over the list level-order:");
            Iterator<Integer> iteratorLevelOrder = tree.iteratorLevelOrder();
            while (iteratorLevelOrder.hasNext()) {
                System.out.print(iteratorLevelOrder.next() + " ");
            }

        } catch (NonComparableElementException e) {
            System.out.println(e.getMessage());
        } catch (EmptyCollectionException e) {
            System.out.println(e.getMessage());
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}