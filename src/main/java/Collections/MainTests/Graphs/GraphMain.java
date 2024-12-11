package Collections.MainTests.Graphs;

import Collections.Graphs.Graph;

import java.util.Iterator;

/**
 * GraphMain tests the Graph class.
 */
public class GraphMain {
    /**
     * Main method for testing the Graph class.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Graph<String> graph = new Graph<>();

        // Adding vertices
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");

        // Adding edges to create two disconnected components
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("D", "E");

        // Printing the graph
        System.out.println("Graph:");
        System.out.println(graph.toString());

        // Checking if the graph is connected
        System.out.println("Is the graph connected? " + graph.isConnected());

        // Finding the shortest path length in a disconnected graph
        System.out.println("Shortest path length from A to E: " + graph.shortestPathLength("A", "E"));

        // Performing BFS traversal from a vertex in the first component
        System.out.println("BFS traversal starting from A:");
        Iterator<String> bfsIterator = graph.iteratorBFS("A");
        while (bfsIterator.hasNext()) {
            System.out.print(bfsIterator.next() + " ");
        }
        System.out.println();

        // Performing DFS traversal from a vertex in the first component
        System.out.println("DFS traversal starting from A:");
        Iterator<String> dfsIterator = graph.iteratorDFS("A");
        while (dfsIterator.hasNext()) {
            System.out.print(dfsIterator.next() + " ");
        }
        System.out.println();

        // Performing BFS traversal from a vertex in the second component
        System.out.println("BFS traversal starting from D:");
        bfsIterator = graph.iteratorBFS("D");
        while (bfsIterator.hasNext()) {
            System.out.print(bfsIterator.next() + " ");
        }
        System.out.println();

        // Performing DFS traversal from a vertex in the second component
        System.out.println("DFS traversal starting from D:");
        dfsIterator = graph.iteratorDFS("D");
        while (dfsIterator.hasNext()) {
            System.out.print(dfsIterator.next() + " ");
        }
        System.out.println();
    }
}