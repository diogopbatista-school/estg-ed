package Game.Navigation;

import Collections.Lists.ArrayUnorderedList;
import Collections.Lists.LinkedUnorderedList;
import Collections.Lists.UnorderedListADT;
import Collections.Queues.LinkedQueue;
import Game.CustomCollections.ExtendedNetwork;
import Game.CustomCollections.ExtendedNetworkADT;
import Game.Exceptions.EnemyException;
import Game.Exceptions.RoomException;
import Game.Interfaces.Enemy;
import Game.Interfaces.Map;
import Game.Interfaces.Room;
import java.util.Iterator;
import java.util.Random;

public class MapImp implements Map{

    private ExtendedNetworkADT<Room> network;

    public MapImp(){
        this.network = new ExtendedNetwork<>();
    }

    public boolean isConnected(Room room1, Room room2){
        return network.areConnected(room1, room2);
    }

    @Override
    public Room[] getRooms(){
        return network.getRooms();
    }

    @Override
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

    /*public void shuffleEnemiesInConnectedRooms() throws RoomException, EnemyException {
        UnorderedListADT<Room> rooms = new LinkedUnorderedList<>();
        Iterator<Room> iterator = network.iteratorBFS(network.getRoomByName("Heliporto"));
        while (iterator.hasNext()) {
            rooms.addToRear(iterator.next());
        }

        Random random = new Random();
        UnorderedListADT<Enemy> enemiesToMove = new ArrayUnorderedList<>();

        // Collect all enemies that can be moved
        iterator = rooms.iterator();
        while (iterator.hasNext()) {
            Room room = iterator.next();
            Iterator<Enemy> enemyIterator = ((RoomImp) room).getEnemiesIterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                if (!enemy.isInFight() && !enemy.isDead()) {
                    enemiesToMove.addToRear(enemy);
                    enemyIterator.remove();
                }
            }
        }

        // Redistribute enemies randomly to up to two connected rooms
        iterator = rooms.iterator();
        while (iterator.hasNext()) {
            Room targetRoom1 = iterator.next();
            Room targetRoom2 = null;

            while (targetRoom2 == null || !network.areConnected(targetRoom1, targetRoom2)) {
                targetRoom2 = rooms.iterator().next();
            }

            Iterator<Enemy> enemyIterator = enemiesToMove.iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                targetRoom1.addEnemy(enemy);
                if (random.nextBoolean()) {
                    targetRoom2.addEnemy(enemy);
                }
            }
        }
    }*/



    public void shuffleEnemiesInConnectedRooms() throws RoomException, EnemyException {
        // Obter todas as salas no mapa
        Room[] allRooms = network.getRooms();

        // Estruturas auxiliares
        Random random = new Random();
        UnorderedListADT<Enemy> enemiesToMove = new ArrayUnorderedList<>();

        // **1. Iterar sobre todas as salas e coletar inimigos elegíveis para movimentação**
        for (Room currentRoom : allRooms) {
            if (currentRoom == null) continue; // Ignorar salas nulas

            // Obter os inimigos da sala atual
            Iterator<Enemy> enemyIterator = ((RoomImp) currentRoom).getEnemiesIterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                if (!enemy.isInFight() && !enemy.isDead()) {
                    enemiesToMove.addToRear(enemy); // Adicionar o inimigo à lista temporária
                    enemyIterator.remove(); // Remover o inimigo da sala atual
                }
            }
        }

        // **2. Redistribuir os inimigos para salas aleatórias conectadas**
        Iterator<Enemy> enemiesIterator = enemiesToMove.iterator();
        while (enemiesIterator.hasNext()) {
            Enemy enemy = enemiesIterator.next();

            // Escolher uma sala inicial aleatória para redistribuição
            Room sourceRoom = allRooms[random.nextInt(allRooms.length)];

            // Encontrar salas conectadas até um limite de distância (exemplo: 2 conexões)
            UnorderedListADT<Room> connectedRooms = new LinkedUnorderedList<>();
            Iterator<Room> bfsIterator = network.iteratorBFS(sourceRoom);
            int depth = 0;
            while (bfsIterator.hasNext() && depth < 2) {
                Room connectedRoom = bfsIterator.next();
                if (!connectedRoom.equals(sourceRoom)) {
                    connectedRooms.addToRear(connectedRoom); // Adiciona salas conectadas até 2 níveis de distância
                }
                depth++;
            }

            // Escolher uma sala final aleatória entre as conectadas
            Room[] connectedRoomsArray = new Room[connectedRooms.size()];
            Iterator<Room> connectedRoomsIterator = connectedRooms.iterator();
            int index = 0;
            while (connectedRoomsIterator.hasNext()) {
                connectedRoomsArray[index++] = connectedRoomsIterator.next();
            }

            if (connectedRoomsArray.length > 0) {
                Room destinationRoom = connectedRoomsArray[random.nextInt(connectedRoomsArray.length)];
                destinationRoom.addEnemy(enemy); // Mover o inimigo para a sala destino
            }
        }
    }

    public void shuffleEnemiesInConnectedRooms2() throws RoomException, EnemyException {
        // Obter todas as salas no mapa
        Room[] allRooms = network.getRooms();

        // Estruturas auxiliares
        Random random = new Random();
        UnorderedListADT<Enemy> enemiesToMove = new ArrayUnorderedList<>();

        // **1. Iterar sobre todas as salas e coletar inimigos elegíveis para movimentação**
        for (Room currentRoom : allRooms) {
            if (currentRoom == null) continue; // Ignorar salas nulas

            // Obter os inimigos da sala atual
            Iterator<Enemy> enemyIterator = ((RoomImp) currentRoom).getEnemiesIterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                if (!enemy.isInFight() && !enemy.isDead()) {
                    enemiesToMove.addToRear(enemy); // Adicionar o inimigo à lista temporária
                    enemyIterator.remove(); // Remover o inimigo da sala atual
                }
            }
        }

        // **2. Redistribuir os inimigos para salas aleatórias conectadas**
        Iterator<Enemy> enemiesIterator = enemiesToMove.iterator();
        while (enemiesIterator.hasNext()) {
            Enemy enemy = enemiesIterator.next();

            // Obter a sala onde o inimigo está atualmente
            Room sourceRoom = enemy.getCurrentRoom(); // Supondo que exista um método getCurrentRoom() para o inimigo

            // Encontrar salas conectadas até um limite de distância (exemplo: 2 conexões)
            UnorderedListADT<Room> connectedRooms = new LinkedUnorderedList<>();
            Iterator<Room> bfsIterator = network.iteratorBFS(sourceRoom);
            int depth = 0;
            while (bfsIterator.hasNext() && depth < 2) {
                Room connectedRoom = bfsIterator.next();
                if (!connectedRoom.equals(sourceRoom)) {
                    connectedRooms.addToRear(connectedRoom); // Adiciona salas conectadas até 2 níveis de distância
                }
                depth++;
            }

            // Escolher uma sala final aleatória entre as conectadas
            Room[] connectedRoomsArray = new Room[connectedRooms.size()];
            Iterator<Room> connectedRoomsIterator = connectedRooms.iterator();
            int index = 0;
            while (connectedRoomsIterator.hasNext()) {
                connectedRoomsArray[index++] = connectedRoomsIterator.next();
            }

            if (connectedRoomsArray.length > 0) {
                Room destinationRoom = connectedRoomsArray[random.nextInt(connectedRoomsArray.length)];
                destinationRoom.addEnemy(enemy); // Mover o inimigo para a sala destino
            }
        }

    }
}





