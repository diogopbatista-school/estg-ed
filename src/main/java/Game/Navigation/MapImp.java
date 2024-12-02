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

    public boolean areConnected(Room room1, Room room2) {
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
        network.addEdge(room1, room2, room2.getTotalEnemiesAttackPower());
    }

    public void updateWeight(Room vertex1, Room vertex2, double newWeight) {
        network.updateWeight(vertex1, vertex2, vertex2.getTotalEnemiesAttackPower());
    }

    private void updateWeights(){
        Iterator<Room> iterator = network.iteratorVertexes();
        while (iterator.hasNext()) {
            Room room1 = iterator.next();
            UnorderedListADT<Room> connectedRooms = getConnectedRooms(room1);
            Iterator<Room> connectedRoomIterator = connectedRooms.iterator();
            while (connectedRoomIterator.hasNext()) {
                Room room2 = connectedRoomIterator.next();
                int weight1 = room1.getTotalEnemiesAttackPower();
                int weight2 = room2.getTotalEnemiesAttackPower();
                updateWeight(room1, room2, weight2);
                updateWeight(room2, room1, weight1);
            }
        }
    }



    public void mapShuffle() throws EnemyException {
        Random rand = new Random();
        Random numberOfEdgesToMove = new Random();
        Iterator<Room> iterator = network.iteratorVertexes();
        UnorderedListADT<Enemy> movedEnemies = new ArrayUnorderedList<>(); // Initialize the list

        while (iterator.hasNext()) {
            Room currentRoom = iterator.next();
            if (currentRoom.isThereAnEnemyAlive()) {
                UnorderedListADT<Enemy> enemiesToMove = new LinkedUnorderedList<>();
                Iterator<Enemy> enemyIterator = currentRoom.getEnemies().iterator();
                while (enemyIterator.hasNext()) {
                    Enemy enemy = enemyIterator.next();
                    if (!enemy.isInFight() && enemy.isAlive() && (movedEnemies.isEmpty() || !movedEnemies.contains(enemy))) {
                        enemiesToMove.addToRear(enemy); // Add enemy to the list to move later
                    }
                }

                Iterator<Enemy> moveIterator = enemiesToMove.iterator();
                int jumps = numberOfEdgesToMove.nextInt(2) + 1; // 1 or 2 jumps
                while (moveIterator.hasNext()) {
                    Enemy enemy = moveIterator.next();
                    System.out.println(enemy.getName() + " | old " + enemy.getCurrentRoom().getRoomName());
                    currentRoom.removeEnemy(enemy);
                    moveEnemyRecursively(currentRoom, enemy, rand, jumps); // Move the enemy with up to 2 jumps
                    movedEnemies.addToFront(enemy); // Mark the enemy as moved
                    System.out.println(enemy.getName() + " | new " + enemy.getCurrentRoom().getRoomName());
                    System.out.println("Jumps: " + jumps);
                }
            }
        }

        updateWeights();
    }

    private void moveEnemyRecursively(Room currentRoom, Enemy enemy, Random rand, int remainingJumps) throws EnemyException {

        if (remainingJumps == 0) {
            currentRoom.addEnemy(enemy); // Adiciona o inimigo à sala se não houver mais pulos
            enemy.setCurrentRoom(currentRoom); // Atualiza a sala atual do inimigo
            return;
        }

        // Obtém as salas conectadas à sala atual
        UnorderedListADT<Room> connectedRooms = getConnectedRooms(currentRoom);
        if (!connectedRooms.isEmpty()) {
            // Seleciona uma sala aleatória entre as conectadas
            Room nextRoom = getRandomRoom(connectedRooms, rand);
            moveEnemyRecursively(nextRoom, enemy, rand, remainingJumps - 1); // Chamada recursiva para mover o inimigo
        } else {
            // Caso não haja salas conectadas, o inimigo volta para a sala atual
            currentRoom.addEnemy(enemy);
            enemy.setCurrentRoom(currentRoom);
        }
    }

    private UnorderedListADT<Room> getConnectedRooms(Room room) {
        UnorderedListADT<Room> connectedRooms = new ArrayUnorderedList<>();
        Iterator<Room> iterator = network.iteratorVertexes();

        // Itera sobre as salas conectadas e adiciona à lista
        while (iterator.hasNext()) {
            Room connectedRoom = iterator.next();
            if (network.areConnected(room, connectedRoom)) {
                connectedRooms.addToRear(connectedRoom);
            }
        }
        return connectedRooms;
    }

    private Room getRandomRoom(UnorderedListADT<Room> rooms, Random rand) {
        int index = rand.nextInt(rooms.size());
        Iterator<Room> iterator = rooms.iterator();
        Room selectedRoom = null;

        // Itera até o índice sorteado
        for (int i = 0; i <= index; i++) {
            selectedRoom = iterator.next();
        }
        return selectedRoom;
    }



}









