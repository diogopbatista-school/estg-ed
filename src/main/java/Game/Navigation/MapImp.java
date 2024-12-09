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

/**
 * Represents a map of rooms
 */
public class MapImp implements Map {

    /**
     * The network of rooms
     */
    private final ExtendedNetworkADT<Room> network;

    /**
     * Constructor for an empty map
     */
    public MapImp() {
        this.network = new ExtendedNetwork<>();
    }


    /**
     * Returns the shortest path between two rooms
     * @param start the start room
     * @param end   the end room
     * @return The iterator of the shortest path
     */
    public Iterator<Room> shortestPath(Room start, Room end) {
        return network.iteratorShortestPath(start, end);
    }

    /**
     * To string method for the map to check the matrix of connections, weights and vertexes
     * @return the string representation of the map
     */
    public String toString() {
        return network.toString();
    }

    /**
     * Returns the weight of the shortest path between two rooms
     * @param start the start room
     * @param end   the end room
     * @return the weight of the shortest path
     */
    @Override
    public double shortestPathWeight(Room start, Room end) {
        return network.shortestPathWeight(start, end);
    }

    /**
     * verifies if two rooms are connected
     * @param room1 the first room
     * @param room2 the second room
     * @return true if the rooms are connected, false otherwise
     */
    public boolean areConnected(Room room1, Room room2) {
        return network.areConnected(room1, room2);
    }

    /**
     * Returns a list of the rooms
     * @return the iterator of the rooms
     */
    @Override
    public UnorderedListADT<Room> getRooms() {
        return network.getRooms();
    }

    /**
     * Getters for the room by name
     * @param name the name of the room
     * @return the room with the name
     */
    @Override
    public Room getRoomByName(String name) {
        return network.getRoomByName(name);
    }

    /**
     * Adds a room to the map
     * @param room the room to add
     * @throws RoomException if the room is null
     */
    @Override
    public void addRoom(Room room) throws RoomException {
        if (room == null) {
            throw new RoomException("Room cannot be null");
        }
        network.addVertex(room);
    }

    /**
     * Adds a connection between two rooms
     * @param room1  the first room
     * @param room2  the second room
     * @param weight the weight of the connection always from room1 to room2
     */
    @Override
    public void addConnection(Room room1, Room room2, double weight) {
        network.addEdge(room1, room2, room2.getTotalEnemiesAttackPower());
    }

    /**
     * Updates the weight of a connection between two rooms
     * @param vertex1   the first room
     * @param vertex2   the second room
     * @param newWeight the new weight of the connection
     */
    public void updateWeight(Room vertex1, Room vertex2, double newWeight) {
        network.updateWeight(vertex1, vertex2, vertex2.getTotalEnemiesAttackPower());
    }

    /**
     * This method updates the weights of the connections between all rooms in the map
     */
    private void updateWeights(){
        Iterator<Room> iterator = network.iteratorVertexes();
        while (iterator.hasNext()) {
            Room room1 = iterator.next();
            UnorderedListADT<Room> connectedRooms = getConnectedRooms(room1);
            Iterator<Room> connectedRoomIterator = connectedRooms.iterator();
            while (connectedRoomIterator.hasNext()) {
                Room room2 = connectedRoomIterator.next();
                double weight1 = room1.getTotalEnemiesAttackPower();
                double weight2 = room2.getTotalEnemiesAttackPower();

                updateWeight(room1, room2, weight2);
                updateWeight(room2, room1, weight1);
            }
        }
    }

    /**
     * This method shuffles the enemies in the map
     * Only the enemies that are not in fight with the hero can be removed
     * @throws EnemyException if the enemy cannot be removed
     */
    public void mapShuffle() throws EnemyException {
        Random rand = new Random();
        Iterator<Room> iterator = network.iteratorVertexes();
        UnorderedListADT<Enemy> movedEnemies = new ArrayUnorderedList<>();

        while (iterator.hasNext()) {
            Room currentRoom = iterator.next();
            if (currentRoom.isThereAnEnemyAlive()) {
                UnorderedListADT<Enemy> enemiesToMove = new LinkedUnorderedList<>();
                Iterator<Enemy> enemyIterator = currentRoom.getEnemies().iterator();
                while (enemyIterator.hasNext()) {
                    Enemy enemy = enemyIterator.next();
                    if (!enemy.isInFight() && enemy.isAlive() && (movedEnemies.isEmpty() || !movedEnemies.contains(enemy))) {
                        enemiesToMove.addToRear(enemy);
                    }
                }

                Iterator<Enemy> moveIterator = enemiesToMove.iterator();
                int jumps = rand.nextInt(2) + 1; // 1 or 2 jumps
                while (moveIterator.hasNext()) {
                    Enemy enemy = moveIterator.next();
                    System.out.println(enemy.getName() + " | old " + enemy.getCurrentRoom().getRoomName());
                    currentRoom.removeEnemy(enemy);
                    moveEnemyRecursively(currentRoom, enemy, rand, jumps);
                    movedEnemies.addToFront(enemy);
                    System.out.println(enemy.getName() + " | new " + enemy.getCurrentRoom().getRoomName());
                    System.out.println("Jumps: " + jumps);
                }
            }
        }

        updateWeights();
    }

    /**
     * This method moves the enemy recursively to a random room up to a certain number of jumps
     * @param currentRoom the current room of the enemy
     * @param enemy the enemy to move
     * @param rand the random object to generate random numbers
     * @param remainingJumps the remaining number of jumps
     * @throws EnemyException if the enemy cannot be removed
     */
    private void moveEnemyRecursively(Room currentRoom, Enemy enemy, Random rand, int remainingJumps) throws EnemyException {

        if (remainingJumps == 0) {
            currentRoom.addEnemy(enemy);
            return;
        }

        UnorderedListADT<Room> connectedRooms = getConnectedRooms(currentRoom);
        if (!connectedRooms.isEmpty()) {

            Room nextRoom = getRandomRoom(connectedRooms, rand);
            moveEnemyRecursively(nextRoom, enemy, rand, remainingJumps - 1); // Chamada recursiva para mover o inimigo
        } else {
            // nunca deve chegar aqui pois a sala atual sempre terá pelo menos uma sala conectada
            // so acontence se o mapa for invalido , e for impossivel mover o inimigo
            currentRoom.addEnemy(enemy);
            enemy.setCurrentRoom(currentRoom);
        }
    }

    /**
     * This method returns a list of rooms connected to a given room
     * @param room the room to get the connected rooms
     * @return the list of connected rooms
     */
    private UnorderedListADT<Room> getConnectedRooms(Room room) {
        UnorderedListADT<Room> connectedRooms = new ArrayUnorderedList<>();
        Iterator<Room> iterator = network.iteratorVertexes();


        while (iterator.hasNext()) {
            Room connectedRoom = iterator.next();
            if (network.areConnected(room, connectedRoom)) {
                connectedRooms.addToRear(connectedRoom);
            }
        }
        return connectedRooms;
    }

    /**
     * This method returns a random room from a list of rooms that are connected from a given room
     * @param rooms the list of rooms to choose from that are connected
     * @param rand the random object to generate random numbers
     * @return the random room that is connected to the given room
     */
    private Room getRandomRoom(UnorderedListADT<Room> rooms, Random rand) {
        int index = rand.nextInt(rooms.size());
        Iterator<Room> iterator = rooms.iterator();
        Room selectedRoom = null;


        for (int i = 0; i <= index; i++) {
            selectedRoom = iterator.next();
        }
        return selectedRoom;
    }



}









