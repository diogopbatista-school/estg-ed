package Game.Navigation;

import Collections.Graphs.Network;
import Collections.Graphs.NetworkADT;
import Game.CustomCollections.ExtendedNetwork;
import Game.CustomCollections.ExtendedNetworkADT;
import Game.Exceptions.RoomException;
import Interfaces.Map;
import Interfaces.Room;

import java.util.Iterator;

public class MapImp implements Map{

    private ExtendedNetworkADT<Room> network;

    public MapImp(){
        this.network = new ExtendedNetwork<>();
    }

    public Room getRoomByName(String name){
        return network.getRoomByName(name);
    }

    @Override
    public void addRoom(Room room) throws RoomException {
        if(room == null){
            throw new RoomException("Room cannot be null");
        }
        network.addVertex(room);
    }

    @Override
    public void addConnection(Room room1, Room room2, double weight) {
        network.addEdge(room1, room2, room2.getTotalRoomPower());
    }

    public void updateWeight(Room vertex1, Room vertex2, double newWeight){
        network.updateWeight(vertex1, vertex2, vertex2.getTotalRoomPower());
    }

    public double shortestPathWeight(Room vertex1, Room vertex2){
        return network.shortestPathWeight(vertex1, vertex2);
    }

    public Iterator<Room> iteratorBFS(Room start){
        return network.iteratorBFS(start);
    }

    public Iterator<Room> iteratorDFS(Room start){
        return network.iteratorDFS(start);
    }
}
