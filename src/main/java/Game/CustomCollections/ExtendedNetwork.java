package Game.CustomCollections;

import Collections.Lists.ArrayUnorderedList;
import Collections.Lists.LinkedUnorderedList;
import Collections.Exceptions.NoSuchElementException;
import Collections.Graphs.Network;
import Collections.Lists.UnorderedListADT;

import java.util.Iterator;

/**
 * A class that represents a network of rooms.
 *
 * @param <T> the type of the stored element.
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class ExtendedNetwork<T> extends Network<T> implements ExtendedNetworkADT<T> {

    /**
     * Constructor for an empty Extended network.
     */
    public ExtendedNetwork() {
        super();
    }

    /**
     * Method that return the iterator of the shortest path between two vertexes.
     *
     * @param vertex1 the starting vertex
     * @param vertex2 the ending vertex
     * @return the iterator of the shortest path between two vertexes
     */
    @Override
    public Iterator<T> iteratorShortestPath(T vertex1, T vertex2) {
        return super.iteratorShortestPath(getIndex(vertex1), getIndex(vertex2));
    }


    /**
     * Method that return an iterator of vertexes.
     *
     * @return an iterator of vertexes
     */
    @Override
    public Iterator<T> iteratorVertexes() {
        LinkedUnorderedList<T> templist = new LinkedUnorderedList<>();
        for (int i = 0; i < numVertices; i++) {
            templist.addToRear(vertices[i]);
        }
        return templist.iterator();
    }

    /**
     * Method that gives me a list of vertexes.
     *
     * @return a list of vertexes
     */
    @Override
    public UnorderedListADT<T> getVertexes() {
        UnorderedListADT<T> vertexes = new ArrayUnorderedList<>();
        for (int i = 0; i < numVertices; i++) {
            vertexes.addToRear(vertices[i]);
        }
        return vertexes;
    }

    /**
     * Method that adds a new edge to the network.
     *
     * @param room1  the first vertex
     * @param room2  the second vertex
     * @param weight the weight
     * @throws NoSuchElementException   if the vertex is not in the network
     * @throws IllegalArgumentException if the vertex is null
     */
    @Override
    public void addEdge(T room1, T room2, double weight) throws NoSuchElementException, IllegalArgumentException {
        if (room1 == null || room2 == null) {
            throw new IllegalArgumentException("The vertex cannot be null.");
        }
        int index1 = getIndex(room1);
        int index2 = getIndex(room2);
        if (index1 == -1 || index2 == -1) {
            throw new NoSuchElementException("The vertex is not in the network.");
        }
        addEdgeRooms(index1, index2, weight);
    }

    /**
     * Method that adds edge between two rooms. with a weight.
     *
     * @param index1 The index of the first vertex
     * @param index2 The index of the second vertex
     * @param weight The weight of the edge
     */
    private void addEdgeRooms(int index1, int index2, double weight) {
        adjMatrix[index1][index2] = weight;
    }

    /**
     * Method to update the weight of a connection between two vertexes.
     *
     * @param vertex1   the first vertex
     * @param vertex2   the second vertex
     * @param newWeight the new weight of the connection
     * @throws IllegalArgumentException if the vertexes are not neighbors
     */
    @Override
    public void updateWeight(T vertex1, T vertex2, double newWeight) throws IllegalArgumentException {
        if (areConnected(vertex1, vertex2)) {
            int index1 = getIndex(vertex1);
            int index2 = getIndex(vertex2);
            adjMatrix[index1][index2] = newWeight;
        } else {
            throw new IllegalArgumentException("Vertices are not neighbors");
        }
    }

    /**
     * Method to verifiy if two vertexes are connected.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if the vertexes are connected, false otherwise
     */
    @Override
    public boolean areConnected(T vertex1, T vertex2) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            return adjMatrix[index1][index2] < Double.POSITIVE_INFINITY;
        } else {
            throw new IllegalArgumentException("Invalid vertex index");
        }
    }

    /**
     * Method to get the number of vertexes in the network.
     *
     * @return the number of vertexes in the network
     */
    @Override
    public int getNumVertices() {
        return numVertices;
    }

    /**
     * Method to get a vertex by index.
     *
     * @param index the index of the vertex
     * @return the vertex
     */
    @Override
    public T getVertex(int index) {
        return vertices[index];
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
