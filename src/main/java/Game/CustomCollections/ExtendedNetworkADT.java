package Game.CustomCollections;

import Collections.Graphs.NetworkADT;
import Interfaces.Room;

public interface ExtendedNetworkADT<T> extends NetworkADT<T> {

        public void updateWeight(T vertex1, T vertex2, double newWeight);

        /**
        * Returns the weight of the shortest path in this network.
        *
        * @param vertex1 the first vertex
        * @param vertex2 the second vertex
        * @return the weight of the shortest path in this network
        */
        double shortestPathWeight(T vertex1, T vertex2);

        /**
        * Returns the shortest path in this network.
        *
        * @param vertex1 the first vertex
        * @param vertex2 the second vertex
        * @return the shortest path in this network
        */
        Iterable<T> shortestPath(T vertex1, T vertex2);

        /**
         * Returns a room by its name
         * @param name the name of the room
         * @return the room with the given name
         */
        public Room getRoomByName(String name);

        public boolean containsVertex(T vertex) throws IllegalArgumentException;

}
