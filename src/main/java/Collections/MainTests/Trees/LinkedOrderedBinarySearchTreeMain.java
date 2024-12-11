package Collections.MainTests.Trees;

import Collections.Trees.LinkedOrderedBinarySearchTree;
import Collections.Exceptions.ElementNotFoundException;
import Collections.Exceptions.EmptyCollectionException;

/**
 * LinkedOrderedBinarySearchTreeMain tests the LinkedOrderedBinarySearchTree class.
 */
public class LinkedOrderedBinarySearchTreeMain {
    /**
     * Main method for testing the LinkedOrderedBinarySearchTree class.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Cria uma árvore de busca binária ordenada com um elemento raiz
            LinkedOrderedBinarySearchTree<Integer> tree = new LinkedOrderedBinarySearchTree<>(10);

            // Adiciona elementos à árvore
            tree.add(5);
            tree.add(15);
            tree.add(3);
            tree.add(7);
            tree.add(12);
            tree.add(2);

            // Exibe a árvore
            System.out.println("Tree: " + tree.toString());

            // Testa o método first
            try {
                System.out.println("First: " + tree.first());
            } catch (EmptyCollectionException e) {
                System.out.println(e.getMessage());
            }

            // Testa o método last
            try {
                System.out.println("Last: " + tree.last());
            } catch (EmptyCollectionException e) {
                System.out.println(e.getMessage());
            }

            // Testa o método removeFirst
            try {
                System.out.println("Remove First: " + tree.removeFirst());
            } catch (EmptyCollectionException e) {
                System.out.println(e.getMessage());
            }

            // Testa o método removeLast
            try {
                System.out.println("Remove Last: " + tree.removeLast());
            } catch (EmptyCollectionException e) {
                System.out.println(e.getMessage());
            }

            // Testa o método remove
            try {
                System.out.println("Remove 7: " + tree.remove(7));
            } catch (EmptyCollectionException | ElementNotFoundException e) {
                System.out.println(e.getMessage());
            }

            // Exibe a árvore após remoções
            System.out.println("Tree after removals: " + tree.toString());

        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
