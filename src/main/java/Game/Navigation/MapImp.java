package Game.Navigation;

import Collections.Lists.ArrayUnorderedList;
import Collections.Lists.LinkedUnorderedList;
import Collections.Lists.UnorderedListADT;
import Game.CustomCollections.ExtendedNetwork;
import Game.CustomCollections.ExtendedNetworkADT;
import Game.Exceptions.EnemyException;
import Game.Exceptions.RoomException;
import Game.Interfaces.Enemy;
import Game.Interfaces.Map;
import Game.Interfaces.Room;
import java.util.Iterator;
import java.util.Random;

public class MapImp implements Map {

    private ExtendedNetworkADT<Room> network;

    public MapImp() {
        this.network = new ExtendedNetwork<>();
    }

    public Iterator<Room> shortestPath(Room start, Room end) {
        return network.iteratorShortestPath(start, end);
    }

    public String toString() {
        return network.toString();
    }

    public Iterator<Room> iteratorVertexes() {
        return network.iteratorVertexes();
    }

    public Iterator<Room> iteratorRoutes() {
        return network.iteratorRoutes();
    }

    public boolean isConnected(Room room1, Room room2) {
        return network.areConnected(room1, room2);
    }

    @Override
    public UnorderedListADT<Room> getRooms() {

        return network.getRooms();
    }

    @Override
    public Room getRoomByName(String name) {
        return network.getRoomByName(name);
    }

    @Override
    public void addRoom(Room room) throws RoomException {
        if (room == null) {
            throw new RoomException("Room cannot be null");
        }
        network.addVertex(room);
    }

    @Override
    public void addConnection(Room room1, Room room2, double weight) {
        network.addEdge(room1, room2, room2.getTotalRoomPower());
    }

    public void updateWeight(Room vertex1, Room vertex2, double newWeight) {
        network.updateWeight(vertex1, vertex2, vertex2.getTotalRoomPower());
    }

    public void moveAllEnemies() {

    }




}









