package Game.CustomCollections;

import Collections.Lists.ArrayUnorderedList;
import Collections.Lists.LinkedUnorderedList;
import Collections.Exceptions.NoSuchElementException;
import Collections.Graphs.Network;
import Collections.Lists.UnorderedListADT;
import Game.Interfaces.Room;
import java.util.Iterator;

/**
 * A class that represents a network of rooms.
 * @param <T> the type of the stored element.
 *
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
     * Method
     * @param vertex1 the starting vertex
     * @param vertex2 the ending vertex
     * @return
     */
    @Override
    public Iterator<T> iteratorShortestPath(T vertex1, T vertex2) {
        return super.iteratorShortestPath(getIndex(vertex1), getIndex(vertex2));
    }

    @Override
    public Iterator<T> iteratorRoutes() {
        LinkedUnorderedList<T> templist = new LinkedUnorderedList<>();
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (adjMatrix[i][j] != Double.POSITIVE_INFINITY && i != j) {
                    templist.addToRear(vertices[i]);
                    templist.addToRear(vertices[j]);
                }
            }
        }
        return templist.iterator();
    }

    @Override
    public Iterator<T> iteratorVertexes() {
        LinkedUnorderedList<T> templist = new LinkedUnorderedList<>();
        for (int i = 0; i < numVertices; i++) {
            templist.addToRear(vertices[i]);
        }
        return templist.iterator();
    }

    public UnorderedListADT<T> getRooms() {
        UnorderedListADT<T> rooms = new ArrayUnorderedList<>();
        for (int i = 0; i < numVertices; i++) {
            rooms.addToRear(vertices[i]);
        }
        return rooms;
    }

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

    private void addEdgeRooms(int index1, int index2, double weight) {
        adjMatrix[index1][index2] = weight;
    }

    @Override
    public void updateWeight(T vertex1, T vertex2, double newWeight) {
        if (areConnected(vertex1, vertex2)) {
            int index1 = getIndex(vertex1);
            int index2 = getIndex(vertex2);
            adjMatrix[index1][index2] = newWeight;
        } else {
            throw new IllegalArgumentException("Vertices are not neighbors");
        }
    }

    public boolean areConnected(T vertex1, T vertex2) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            return adjMatrix[index1][index2] < Double.POSITIVE_INFINITY;
        } else {
            throw new IllegalArgumentException("Invalid vertex index");
        }
    }

    @Override
    public boolean containsVertex(T vertex) throws IllegalArgumentException {
        if (vertex == null) {
            throw new IllegalArgumentException("The vertex cannot be null.");
        }
        return getIndex(vertex) != -1;
    }

    @Override
    public Room getRoomByName(String name) {
        for (int i = 0; i < numVertices; i++) {
            Room room = (Room) vertices[i];
            if (room.getRoomName().equals(name)) {
                return room;
            }
        }
        return null;
    }




    @Override
    public String toString(){
        return super.toString();
    }
}
