package Collections.MainTests.Trees;

import Collections.Trees.AVLTree;
import Collections.Exceptions.ElementNotFoundException;
import Collections.Exceptions.EmptyCollectionException;

import java.util.Iterator;

public class AVLTreeMain {
    public static void main(String[] args) {
        try {
            // Cria uma árvore AVL com um elemento raiz
            AVLTree<Integer> tree = new AVLTree<>(1);

            // Adiciona elementos à árvore
            tree.addElement(20);
            tree.addElement(30);
            tree.addElement(40);
            tree.addElement(50);
            tree.addElement(25);

            System.out.println("Tree root: " + tree.getRoot());

            // Exibe a árvore
            System.out.println("Tree: " + tree.toString());

            // Testa o método findMin
            try {
                System.out.println("Find Min: " + tree.findMin());
            } catch (EmptyCollectionException e) {
                System.out.println(e.getMessage());
            }

            // Testa o método findMax
            try {
                System.out.println("Find Max: " + tree.findMax());
            } catch (EmptyCollectionException e) {
                System.out.println(e.getMessage());
            }

            // Testa o método removeElement
            try {
                System.out.println("Remove 20: " + tree.removeElement(999));
            } catch (ElementNotFoundException e) {
                System.out.println(e.getMessage());
            }

            // Exibe a árvore após remoções
            System.out.println("Tree after removals: " + tree.toString());

            System.out.println("\nIterating over the list in order:");
            Iterator<Integer> iteratorInOrder = tree.iteratorInOrder();
            while (iteratorInOrder.hasNext()) {
                System.out.print(iteratorInOrder.next() + " ");
            }

            System.out.println("\nIterating over the list pre-order:");
            Iterator<Integer> iteratorPreOrder = tree.iteratorPreOrder();
            while (iteratorPreOrder.hasNext()) {
                System.out.print(iteratorPreOrder.next() + " ");
            }

            System.out.println("\nIterating over the list post-order:");
            Iterator<Integer> iteratorPostOrder = tree.iteratorPostOrder();
            while (iteratorPostOrder.hasNext()) {
                System.out.print(iteratorPostOrder.next() + " ");
            }

            System.out.println("\nIterating over the list level-order:");
            Iterator<Integer> iteratorLevelOrder = tree.iteratorLevelOrder();
            while (iteratorLevelOrder.hasNext()) {
                System.out.print(iteratorLevelOrder.next() + " ");
            }

        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}