package Game.IO;

import Collections.Lists.LinkedUnorderedList;
import Collections.Lists.UnorderedListADT;
import Game.Entities.*;
import Game.Exceptions.ItemException;
import Game.Exceptions.RoomException;
import Game.Interfaces.*;
import Game.Navigation.MapImp;
import Game.Mission.MissionImp;
import Game.Mission.MissionsImp;
import Game.Navigation.RoomImp;
import Game.Utilities.ManualSimulationLog;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The Importer class is responsible for importing game data from JSON files
 * and constructing the game map with rooms, enemies, items, and connections.
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */

public class Importer {

    /**
     * Constants for the Importer class.
     */
    private static final String MISSION_LOG_PATH = "MissionsLogs.json";
    private static final JSONParser PARSER = new JSONParser();

    /**
     * Attributes for the Importer class.
     */
    private JSONArray missionsLogs;
    private JSONArray missions;
    private JSONObject mission;
    private JSONObject target;
    private JSONArray enemies;
    private JSONArray rooms;
    private JSONArray connections;
    private JSONArray items;
    private JSONArray exitsAndEntrances;
    private JSONArray manualSimulationLogs;

    /**
     * Default constructor for the Importer class.
     */
    public Importer() {
    }

    /**
     * Loads the mission list from the specified JSON file.
     *
     * @param filePath Path to the mission JSON file.
     * @return A list of mission codes.
     * @throws IOException    If the file cannot be read.
     * @throws ParseException If the file cannot be parsed.
     */
    public UnorderedListADT<String> loadMissions(String filePath) throws IOException, ParseException {
        UnorderedListADT<String> missionsList = new LinkedUnorderedList<>();
        JSONObject missionsJSON = (JSONObject) PARSER.parse(new String(Files.readAllBytes(Paths.get(filePath))));
        JSONObject missionsLogsJson = (JSONObject) PARSER.parse(new String(Files.readAllBytes(Paths.get(MISSION_LOG_PATH))));
        missionsLogs = (JSONArray) missionsLogsJson.get("missions");
        missions = (JSONArray) missionsJSON.get("missoes");

        for (Object obj : missions) {
            JSONObject mission = (JSONObject) obj;
            missionsList.addToRear((String) mission.get("cod-missao"));
        }

        return missionsList;
    }

    /**
     * Loads the missions from the specified JSON file back to the Mission object to
     * be added to the Missions object.
     *
     * @return The loaded missions.
     */
    public Missions loadMissions() {
        Missions missionsLoaded = new MissionsImp();
        for (Object obj : missions) {
            JSONObject mission = (JSONObject) obj;
            Mission msn = new MissionImp();
            msn.setCode((String) mission.get("cod-missao"));
            msn.setVersion(((Long) mission.get("versao")).intValue());
            missionsLoaded.addMission(msn);
        }
        return missionsLoaded;

    }

    /**
     * Imports the game data from the JSON files and constructs the mission object.
     *
     * @param nameMission The mission code to import.
     * @return The imported mission object.
     * @throws IOException If a file cannot be read.
     */
    public Mission importData(String nameMission) throws IOException {
        try {
            Mission msn = new MissionImp();
            return importFiles(msn, nameMission);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Filters logs for the specified mission from the logs JSON.
     *
     * @param missionCode The mission code.
     * @return A JSONArray of logs filtered by mission.
     */
    private JSONArray getLogsForMission(String missionCode) {
        JSONArray filteredLogs = new JSONArray();
        for (Object obj : missionsLogs) {
            JSONObject logMission = (JSONObject) obj;
            String logMissionName = (String) logMission.get("name");
            if (logMissionName != null && logMissionName.equals(missionCode)) {
                JSONArray logs = (JSONArray) logMission.get("logs");
                filteredLogs.addAll(logs);
            }
        }
        return filteredLogs;
    }

    /**
     * Reads the JSON files and parses their content to populate the mission object.
     *
     * @param msn         The mission object to populate.
     * @param nameMission The mission code to load.
     * @return The populated mission object.
     * @throws ParseException If a JSON file cannot be parsed.
     */
    private Mission importFiles(Mission msn, String nameMission) throws ParseException {

        Map map = new MapImp();

        for (Object obj : missions) {
            JSONObject mission = (JSONObject) obj;
            if (nameMission.equals(mission.get("cod-missao"))) {
                String codMission = (String) mission.get("cod-missao");
                int versMission = ((Long) mission.get("versao")).intValue();
                this.mission = mission;
                this.rooms = (JSONArray) mission.get("edificio");
                this.enemies = (JSONArray) mission.get("inimigos");
                this.connections = (JSONArray) mission.get("ligacoes");
                this.items = (JSONArray) mission.get("itens");
                this.exitsAndEntrances = (JSONArray) mission.get("entradas-saidas");
                this.target = (JSONObject) mission.get("alvo");

                msn.setCode(codMission);
                msn.setVersion(versMission);

                break;
            }
        }

        if (this.mission == null) {
            throw new IllegalArgumentException("Mission code not found: " + nameMission);
        }

        msn.addMap(this.constructMap(map));

        // Load simulation logs for this mission
        this.manualSimulationLogs = getLogsForMission(nameMission);
        addManualSimulationLog(msn);

        return msn;
    }

    /**
     * Constructs the game map with rooms, enemies, items, and connections.
     *
     * @param map The map to construct.
     * @return The constructed map.
     */
    private Map constructMap(Map map) {
        try {
            for (Object obj : this.rooms) {
                String roomName = (String) obj;
                Room room = new RoomImp(roomName);
                map.addRoom(room);

                addEnemiesToRoom(room);
                addItemsToRoom(room);
                addExitsAndEntrancesToRoom(room);
                addTargetToRoom(room);
            }

            addConnections(map);

        } catch (Exception e) {
            System.out.println("Error adding rooms");
        }

        return map;
    }

    /**
     * Adds enemies to the specified room.
     *
     * @param room The room to add enemies to.
     */
    private void addEnemiesToRoom(Room room) {
        try {
            for (Object obj : this.enemies) {
                JSONObject jsonEnemy = (JSONObject) obj;
                String name = (String) jsonEnemy.get("nome");
                double power = ((Number) jsonEnemy.get("poder")).doubleValue();
                double health = ((Number) jsonEnemy.get("vida")).doubleValue();
                String division = (String) jsonEnemy.get("divisao");

                if (room.getRoomName().equals(division)) {
                    Enemy enemy = new EnemyImp(name, health, power, room);
                    room.addEnemy(enemy);
                }
            }
        } catch (Exception e) {
            System.out.println("Error adding enemies to room");
        }
    }

    /**
     * Adds items to the specified room.
     *
     * @param room The room to add items to.
     */
    private void addItemsToRoom(Room room) {
        try {
            for (Object obj : this.items) {
                JSONObject jsonItem = (JSONObject) obj;
                double points = 0;

                String name = (String) jsonItem.get("tipo");
                if (jsonItem.containsKey("pontos-recuperados")) {
                    points = ((Long) jsonItem.get("pontos-recuperados")).intValue();
                } else if (jsonItem.containsKey("pontos-extra")) {
                    points = ((Long) jsonItem.get("pontos-extra")).intValue();
                }
                String division = (String) jsonItem.get("divisao");

                if (room.getRoomName().equals(division)) {
                    try {
                        Item item;
                        if (name.equals("kit de vida")) {
                            item = new ItemHealer(name, points);
                            room.addItem(item);
                        } else if (name.equals("colete")) {
                            item = new ItemArmor(name, points);
                            room.addItem(item);
                        }
                    } catch (ItemException e) {
                        System.out.println("An item with the name " + name + " was not added to the room");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error adding items to room");
        }
    }

    /**
     * Adds exits and entrances to the specified room.
     *
     * @param room The room to add exits and entrances to.
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
        }
    }

    /**
     * Adds connections between rooms in the map.
     *
     * @param map The map to add connections to.
     * @throws RoomException If a room is invalid.
     */
    private void addConnections(Map map) throws RoomException {
        for (Object obj : connections) {
            JSONArray connection = (JSONArray) obj;
            String room1Name = (String) connection.get(0);
            String room2Name = (String) connection.get(1);

            Room room1 = map.getRoomByName(room1Name);
            Room room2 = map.getRoomByName(room2Name);


            map.addConnection(room1, room2, room2.getTotalEnemiesAttackPower());
            map.addConnection(room2, room1, room1.getTotalEnemiesAttackPower());
        }
    }

    /**
     * Adds the target to the specified room.
     *
     * @param room The room to add the target to.
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
        }
    }

    /**
     * Adds manual simulation logs to the specified mission.
     *
     * @param mission The mission to add manual simulation logs to.
     */
    private void addManualSimulationLog(Mission mission) {
        try {
            for (Object obj : manualSimulationLogs) {
                JSONObject logObject = (JSONObject) obj;

                String timestamp = (String) logObject.get("timestamp");
                ManualSimulationLog manualSimulationLog = new ManualSimulationLog(timestamp);

                JSONArray pathArray = (JSONArray) logObject.get("path");
                for (Object pathElement : pathArray) {
                    String roomName = (String) pathElement;
                    Room room = mission.getMap().getRoomByName(roomName);

                    manualSimulationLog.addRoom(room);
                }

                JSONObject heroObject = (JSONObject) logObject.get("hero");
                if (heroObject != null) {
                    double health = ((Double) heroObject.get("health"));
                    double armorHealth = (Double) heroObject.get("armorHealth");
                    Hero hero = new HeroImp(health, armorHealth);
                    manualSimulationLog.setHero(hero);
                }

                mission.addManualSimulationLog(manualSimulationLog);
            }
        } catch (Exception e) {
            System.out.println("Error adding manual simulation logs");
        }
    }
}
