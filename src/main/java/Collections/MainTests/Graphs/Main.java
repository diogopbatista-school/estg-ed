package Collections.MainTests.Graphs;

import Collections.Graphs.Network;

import java.util.Iterator;

/**
 * Main class for testing the network
 */
public class Main {
    /**
     * Main method
     * @param args arguments
     */
    public static void main(String[] args) {
        Network<String> network = new Network<>();

        // Adding vertices
        network.addVertex("A");
        network.addVertex("B");
        network.addVertex("C");
        network.addVertex("D");
        network.addVertex("E");
        network.addVertex("F");

        // Adding edges
        network.addEdge("A", "B", 1.0);
        network.addEdge("A", "C", 2.0);
        network.addEdge("B", "C", 3.0);
        network.addEdge("C", "D", 4.0);
        network.addEdge("D", "E", 5.0);
        network.addEdge("E", "F", 6.0);
        network.addEdge("F", "A", 7.0);
        network.addEdge("B", "E", 8.0);

        // Removing an edge
        network.removeEdge("A", "B");

        // Removing a vertex
        network.removeVertex("D");

        // Testing DFS
        System.out.println("DFS starting from vertex A:");
        Iterator<String> dfsIterator = network.iteratorDFS("A");
        while (dfsIterator.hasNext()) {
            System.out.print(dfsIterator.next() + " ");
        }
        System.out.println();

        // Testing BFS
        System.out.println("BFS starting from vertex A:");
        Iterator<String> bfsIterator = network.iteratorBFS("A");
        while (bfsIterator.hasNext()) {
            System.out.print(bfsIterator.next() + " ");
        }
        System.out.println();

        // Testing shortest path
        System.out.println("Shortest path from A to E:");
        Iterator<String> shortestPathIterator = network.iteratorShortestPath("A", "E");
        while (shortestPathIterator.hasNext()) {
            System.out.print(shortestPathIterator.next() + " ");
        }
        System.out.println();

        // Testing shortest path weight
        double shortestPathWeight = network.shortestPathWeight("A", "E");
        System.out.println("Shortest path weight from A to E: " + shortestPathWeight);



        // Printing the network representation
        System.out.println("Network representation:");
        System.out.println(network);
    }
}