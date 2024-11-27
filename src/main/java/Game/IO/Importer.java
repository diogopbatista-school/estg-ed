package Game.IO;

import Game.Entities.EnemyImp;
import Game.Entities.ItemImp;
import Game.Entities.TargetImp;
import Game.Exceptions.MapException;
import Game.Exceptions.RoomException;
import Game.Navigation.MapImp;
import Game.Navigation.RoomImp;
import Interfaces.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Importer {

    private static final String PATH = "mission.json";
    private static final JSONParser PARSER = new JSONParser();
    private JSONObject mission;
    private JSONObject target;
    private JSONArray enemies;
    private JSONArray rooms;
    private JSONArray connections;
    private JSONArray items;
    private JSONArray exitsAndEntrances;

    public Importer() {
    }

    public void importData(Map map) throws FileNotFoundException, IOException {

    }

    private void importFiles(Map map) throws FileNotFoundException, IOException, ParseException, MapException {
        if(!Files.exists(Paths.get(PATH))){
            throw new FileNotFoundException("File not found");
        }

        if(map == null){
            throw new MapException("Map cannot be null");
        }


        
        this.mission = (JSONObject) PARSER.parse(new String(Files.readAllBytes(Paths.get(PATH))));
        this.rooms = (JSONArray) mission.get("edificio");
        this.enemies = (JSONArray) mission.get("inimigos");
        this.connections = (JSONArray) mission.get("ligacoes");
        this.items = (JSONArray) mission.get("itens");
        this.exitsAndEntrances = (JSONArray) mission.get("entradas-saidas");
        this.target = (JSONObject) mission.get("alvo");

        this.ConstructMap(map);
    }

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

    private void AddEnemiesToRoom(Room room) {
        try {
            for (Object obj : this.enemies) {
                JSONObject jsonInimigo = (JSONObject) obj;
                String name = (String) jsonInimigo.get("nome");
                int power = ((Long) jsonInimigo.get("poder")).intValue();
                int health = ((Long) jsonInimigo.get("vida")).intValue();
                String division = (String) jsonInimigo.get("divisao");


                if (room.getRoomName().equals(division)) {
                    Enemy enemy = new EnemyImp(name, power, health);
                    room.addEnemy(enemy); // Adiciona inimigo à sala
                }
            }
        } catch (Exception e) {
            System.out.println("Error adding enemies to room");
            e.printStackTrace();
        }
    }

    private void addItemsToRoom(Room room) {
        try {
            for (Object obj : this.items) {
                JSONObject jsonItem = (JSONObject) obj;
                String name = (String) jsonItem.get("nome");
                int power = ((Long) jsonItem.get("poder")).intValue();
                String division = (String) jsonItem.get("divisao");

                if (room.getRoomName().equals(division)) {
                    Item item = new ItemImp(power, name);
                    room.addItem(item);
                }
            }
        } catch (Exception e) {
            System.out.println("Error adding items to room");
            e.printStackTrace();
        }
    }

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

    private void addConnections(Map map) throws RoomException {
        for (Object obj : connections) {
            JSONArray connection = (JSONArray) obj;
            String room1Name = (String) connection.get(0);
            String room2Name = (String) connection.get(1);

            Room room1 = map.getRoomByName(room1Name);
            Room room2 = map.getRoomByName(room2Name);

            if (room1 != null && room2 != null) {
                map.addConnection(room1, room2, room2.getRoomPower());
            }
        }
    }

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