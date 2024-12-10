package Game.CustomCollections;

import Collections.Graphs.NetworkADT;
import Collections.Lists.UnorderedListADT;

import java.util.Iterator;

/**
 * Represents an extended network.
 *
 * @param <T> the type of the stored element.
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public interface ExtendedNetworkADT<T> extends NetworkADT<T> {

    /**
     * Returns an iterator of vertexes.
     *
     * @return an iterator of vertexes
     */
    Iterator<T> iteratorVertexes();

    /**
     * Method to check if two vertexes are connected.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if the vertexes are connected, false otherwise
     * @throws IllegalArgumentException if the vertexes are not in the network
     */
    boolean areConnected(T vertex1, T vertex2) throws IllegalArgumentException;

    /**
     * Returns a list of the vertexes in the network.
     *
     * @return a list of the vertexes in the network
     */
    UnorderedListADT<T> getVertexes();

    /**
     * Updates the weight of the edge between two vertexes.
     *
     * @param vertex1   the first vertex
     * @param vertex2   the second vertex
     * @param newWeight the new weight
     */
    void updateWeight(T vertex1, T vertex2, double newWeight);

    /**
     * Returns the weight of the shortest path in this network.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return the weight of the shortest path in this network
     */
    double shortestPathWeight(T vertex1, T vertex2);

    /**
     * Gets the vertex at the specified index.
     *
     * @param index the index of the vertex
     * @return the vertex at the specified index
     */
    T getVertex(int index);

    /**
     * Gets the number of vertexes in the network.
     *
     * @return the number of vertexes in the network
     */
    int getNumVertices();


}
