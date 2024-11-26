package Collections.MainTests.Trees;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Trees.ArrayHeap;

public class ArrayHeapMain {
    public static void main(String[] args) {
        ArrayHeap<Integer> heap = new ArrayHeap<>();

        // Adicionando elementos ao heap
        heap.addElement(10);
        heap.addElement(4);
        heap.addElement(15);
        heap.addElement(20);
        heap.addElement(0);

        // Imprimindo o heap
        System.out.println("Heap após adicionar elementos:");
        System.out.println(heap.toString());

        // Removendo o menor elemento
        try {
            System.out.println("Removendo o menor elemento: " + heap.removeMin());
        } catch (EmptyCollectionException e) {
            System.out.println(e.getMessage());
        }

        // Imprimindo o heap após remover o menor elemento
        System.out.println("Heap após remover o menor elemento:");

        System.out.println(heap.toString());

        // Encontrando o menor elemento
        try {
            System.out.println("Menor elemento no heap: " + heap.findMin());
        } catch (EmptyCollectionException e) {
            System.out.println(e.getMessage());
        }
    }
}