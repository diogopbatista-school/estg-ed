package Game.IO;

import Game.Entities.EnemyImp;
import Game.Entities.ItemImp;
import Game.Entities.TargetImp;
import Game.Exceptions.MapException;
import Game.Exceptions.RoomException;
import Game.Interfaces.Enemy;
import Game.Interfaces.Map;
import Game.Interfaces.Room;
import Game.Navigation.RoomImp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The Importer class is responsible for importing game data from a JSON file
 * and constructing the game map with rooms, enemies, items, and connections.
 */
public class Importer {


    private static final JSONParser PARSER = new JSONParser();
    private JSONObject mission;
    private JSONObject target;
    private JSONArray enemies;
    private JSONArray rooms;
    private JSONArray connections;
    private JSONArray items;
    private JSONArray exitsAndEntrances;

    /**
     * Default constructor for the Importer class.
     */
    public Importer() {
    }

    /**
     * Imports data from the JSON file and constructs the game map.
     *
     * @param map The map to be populated with data.
     * @throws FileNotFoundException if the JSON file is not found.
     * @throws IOException if an I/O error occurs.
     */
    public void importData(Map map, String nameMission) throws FileNotFoundException, IOException {
        try {
            importFiles(map,nameMission);
        } catch (ParseException | MapException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the JSON file and parses its content to populate the map.
     *
     * @param map The map to be populated with data.
     * @throws FileNotFoundException if the JSON file is not found.
     * @throws IOException if an I/O error occurs.
     * @throws ParseException if the JSON file cannot be parsed.
     * @throws MapException if the map is null.
     */
    private void importFiles(Map map, String nameMission) throws FileNotFoundException, IOException, ParseException, MapException {
        if(!Files.exists(Paths.get(nameMission))){
            throw new FileNotFoundException("File not found");
        }

        if(map == null){
            throw new MapException("Map cannot be null");
        }

        this.mission = (JSONObject) PARSER.parse(new String(Files.readAllBytes(Paths.get(nameMission))));
        this.rooms = (JSONArray) mission.get("edificio");
        this.enemies = (JSONArray) mission.get("inimigos");
        this.connections = (JSONArray) mission.get("ligacoes");
        this.items = (JSONArray) mission.get("itens");
        this.exitsAndEntrances = (JSONArray) mission.get("entradas-saidas");
        this.target = (JSONObject) mission.get("alvo");

        this.ConstructMap(map);
    }

    /**
     * Constructs the map by adding rooms, enemies, items, exits, entrances, and connections.
     *
     * @param map The map to be constructed.
     */
    private void ConstructMap(Map map) {
        try {
            for (Object obj : this.rooms) {
                String roomName = (String) obj;
                Room room = new RoomImp(roomName);
                map.addRoom(room);

                AddEnemiesToRoom(room);
                addItemsToRoom(room);
                addExitsAndEntrancesToRoom(room);
                addTargetToRoom(room);
            }

            addConnections(map);

        } catch (Exception e) {
            System.out.println("Error adding rooms");
        }
    }

    /**
     * Adds enemies to the specified room based on the JSON data.
     *
     * @param room The room to which enemies will be added.
     */
    private void AddEnemiesToRoom(Room room) {
        try {
            for (Object obj : this.enemies) {
                JSONObject jsonInimigo = (JSONObject) obj;
                String name = (String) jsonInimigo.get("nome");
                int power = ((Long) jsonInimigo.get("poder")).intValue();
                int health = ((Long) jsonInimigo.get("vida")).intValue();
                String division = (String) jsonInimigo.get("divisao");

                if (room.getRoomName().equals(division)) {
                    Enemy enemy = new EnemyImp(name, health, power, room);
                    room.addEnemy(enemy); // Adds enemy to the room
                }
            }
        } catch (Exception e) {
            System.out.println("Error adding enemies to room");
            e.printStackTrace();
        }
    }

    /**
     * Adds items to the specified room based on the JSON data.
     *
     * @param room The room to which items will be added.
     */
    private void addItemsToRoom(Room room) {
        try {
            for (Object obj : this.items) {
                JSONObject jsonItem = (JSONObject) obj;
                int points = 0;

                String name = (String) jsonItem.get("tipo");
                if (jsonItem.containsKey("pontos-recuperados")) {
                    points = ((Long) jsonItem.get("pontos-recuperados")).intValue();
                } else if (jsonItem.containsKey("pontos-extra")) {
                    points = ((Long) jsonItem.get("pontos-extra")).intValue();
                }
                String division = (String) jsonItem.get("divisao");

                if (room.getRoomName().equals(division)) {
                    try{
                        ItemImp item = new ItemImp(points, name);
                        room.addItem(item);
                    }catch(IllegalArgumentException e){
                        System.out.println("An item with the name " + name + " was not added to the room");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error adding items to room"); //------------------ Adicionar log
            e.printStackTrace();
        }
    }

    /**
     * Adds exits and entrances to the specified room based on the JSON data.
     *
     * @param room The room to which exits and entrances will be added.
     */
    private void addExitsAndEntrancesToRoom(Room room) {
        try {
            for (Object obj : this.exitsAndEntrances) {
                String division = (String) obj;

                if (room.getRoomName().equals(division)) {
                    room.setEntryAndExit();
                }
            }
        } catch (Exception e) {
            System.out.println("Error adding exits and entrances to room");
            e.printStackTrace();
        }
    }

    /**
     * Adds connections between rooms based on the JSON data.
     *
     * @param map The map to which connections will be added.
     * @throws RoomException if a room is not found.
     */
    private void addConnections(Map map) throws RoomException {
        for (Object obj : connections) {
            JSONArray connection = (JSONArray) obj;
            String room1Name = (String) connection.get(0);
            String room2Name = (String) connection.get(1);

            Room room1 = map.getRoomByName(room1Name);
            Room room2 = map.getRoomByName(room2Name);
            int weight1 = room1.getTotalEnemiesAttackPower();
            int weight2 = room2.getTotalEnemiesAttackPower();

            if (room1 != null && room2 != null) {
                map.addConnection(room1, room2, weight1);
                map.addConnection(room2, room1, weight2);
            }
        }
    }



    /**
     * Adds the target to the specified room based on the JSON data.
     *
     * @param room The room to which the target will be added.
     */
    private void addTargetToRoom(Room room) {
        try {
            String division = (String) target.get("divisao");

            if (room.getRoomName().equals(division)) {
                String type = (String) target.get("type");
                room.addTargetToRoom(new TargetImp(room.getRoomName(), type) {
                });
            }
        } catch (Exception e) {
            System.out.println("Error adding target to room");
            e.printStackTrace();
        }
    }
}