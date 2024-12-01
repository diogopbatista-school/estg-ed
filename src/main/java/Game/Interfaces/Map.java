package Game.Interfaces;

import Collections.Lists.UnorderedListADT;
import Game.Exceptions.EnemyException;
import Game.Exceptions.RoomException;

import java.util.Iterator;

public interface Map {

    public Iterator<Room> shortestPath(Room start, Room end);


    public void mapShuffle() throws EnemyException;

    public void updateWeight(Room vertex1, Room vertex2, double newWeight);

    public boolean isConnected(Room room1, Room room2);

    public UnorderedListADT<Room> getRooms();

    /**
     * Adds a room to the map
     * @param room the room to add
     * @throws RoomException if the room is null
     */
    public void addRoom(Room room) throws RoomException;

    /**
     * Adds a connection between two rooms
     * @param room1 the first room
     * @param room2 the second room
     * @param weight the weight of the connection always from room1 to room2
     * @throws RoomException if the rooms are null
     * @throws IllegalArgumentException if the weight is negative
     */
    public void addConnection(Room room1, Room room2, double weight) throws RoomException, IllegalArgumentException;

    /**
     * Returns the room by its name
     * @param name the name of the room
     * @return the room with the given name
     */
    public Room getRoomByName(String name);
}
