package Game.Interfaces;

import Collections.Lists.UnorderedListADT;
import Game.Exceptions.EnemyException;
import Game.Exceptions.RoomException;

import java.util.Iterator;

/**
 * Represents a map of rooms
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public interface Map {

    /**
     * Returns the shortest path between two rooms
     *
     * @param start the start room
     * @param end   the end room
     * @return The iterator of the shortest path
     */
    public Iterator<Room> shortestPath(Room start, Room end);

    /**
     * Returns the weight of the shortest path between two rooms
     *
     * @param start the start room
     * @param end   the end room
     * @return the weight of the shortest path
     */
    public double shortestPathWeight(Room start, Room end);

    /**
     * Shuffles the map
     * Only the enemies that are not in fight with the hero can be removed
     *
     * @throws EnemyException if the enemy cannot be removed
     */
    public void mapShuffle() throws EnemyException;

    /**
     * Updates the weight of a connection between two rooms
     *
     * @param vertex1   the first room
     * @param vertex2   the second room
     * @param newWeight the new weight of the connection
     */
    public void updateWeight(Room vertex1, Room vertex2, double newWeight);

    /**
     * verifies if two rooms are connected
     * @param room1 the first room
     * @param room2 the second room
     * @return true if the rooms are connected, false otherwise
     */
    public boolean areConnected(Room room1, Room room2);

    /**
     * Returns an iterator of the rooms
     * @return the iterator of the rooms
     */
    public UnorderedListADT<Room> getRooms();

    /**
     * Adds a room to the map
     *
     * @param room the room to add
     * @throws RoomException if the room is null
     */
    public void addRoom(Room room) throws RoomException;

    /**
     * Adds a connection between two rooms
     *
     * @param room1  the first room
     * @param room2  the second room
     * @param weight the weight of the connection always from room1 to room2
     * @throws RoomException            if the rooms are null
     * @throws IllegalArgumentException if the weight is negative
     */
    public void addConnection(Room room1, Room room2, double weight) throws RoomException, IllegalArgumentException;

    /**
     * Returns the room by its name
     *
     * @param name the name of the room
     * @return the room with the given name
     */
    public Room getRoomByName(String name);
}
