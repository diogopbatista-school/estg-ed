package Game.IO;

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
import java.nio.file.StandardOpenOption;

/**
 * The Importer class is responsible for importing game data from JSON files
 * and constructing the game map with rooms, enemies, items, and connections.
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */

public class Importer {

    // JSON data for the missions , in case the file that the player wants to load does not exist
    // ITS like a default file for the missions;
    private static final String MISSIONS_JSON_DEFAULT ="{\n" +
            "  \"missoes\": [\n" +
            "    {\n" +
            "      \"cod-missao\": \"infiltração no bunker\",\n" +
            "      \"versao\": 1,\n" +
            "      \"edificio\": [\n" +
            "        \"Entrada Lateral\",\n" +
            "        \"Posto Avançado\",\n" +
            "        \"Escada de Serviço\",\n" +
            "        \"Sala de Comunicações\",\n" +
            "        \"Depósito de Armamentos\",\n" +
            "        \"Laboratório de Pesquisa\",\n" +
            "        \"Escada Principal\",\n" +
            "        \"Sala de Comando\"\n" +
            "      ],\n" +
            "      \"ligacoes\": [\n" +
            "        [\"Entrada Lateral\", \"Posto Avançado\"],\n" +
            "        [\"Posto Avançado\", \"Escada de Serviço\"],\n" +
            "        [\"Escada de Serviço\", \"Sala de Comunicações\"],\n" +
            "        [\"Sala de Comunicações\", \"Depósito de Armamentos\"],\n" +
            "        [\"Depósito de Armamentos\", \"Laboratório de Pesquisa\"],\n" +
            "        [\"Laboratório de Pesquisa\", \"Escada Principal\"],\n" +
            "        [\"Escada Principal\", \"Sala de Comando\"]\n" +
            "      ],\n" +
            "      \"inimigos\": [\n" +
            "        {\n" +
            "          \"nome\": \"guarda de elite\",\n" +
            "          \"poder\": 30,\n" +
            "          \"vida\": 120,\n" +
            "          \"divisao\": \"Posto Avançado\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"nome\": \"cientista hostil\",\n" +
            "          \"poder\": 25,\n" +
            "          \"vida\": 80,\n" +
            "          \"divisao\": \"Laboratório de Pesquisa\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"nome\": \"comandante\",\n" +
            "          \"poder\": 40,\n" +
            "          \"vida\": 150,\n" +
            "          \"divisao\": \"Sala de Comando\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"nome\": \"sniper\",\n" +
            "          \"poder\": 20,\n" +
            "          \"vida\": 100,\n" +
            "          \"divisao\": \"Escada de Serviço\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"nome\": \"soldado pesado\",\n" +
            "          \"poder\": 35,\n" +
            "          \"vida\": 200,\n" +
            "          \"divisao\": \"Depósito de Armamentos\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"nome\": \"técnico hostil\",\n" +
            "          \"poder\": 15,\n" +
            "          \"vida\": 60,\n" +
            "          \"divisao\": \"Sala de Comunicações\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"entradas-saidas\": [\n" +
            "        \"Entrada Lateral\",\n" +
            "        \"Sala de Comando\"\n" +
            "      ],\n" +
            "      \"alvo\": {\n" +
            "        \"divisao\": \"Sala de Comando\",\n" +
            "        \"tipo\": \"extrair informações\"\n" +
            "      },\n" +
            "      \"itens\": [\n" +
            "        {\n" +
            "          \"divisao\": \"Sala de Comunicações\",\n" +
            "          \"pontos-recuperados\": 10,\n" +
            "          \"tipo\": \"kit de vida\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"divisao\": \"Laboratório de Pesquisa\",\n" +
            "          \"pontos-extra\": 20,\n" +
            "          \"tipo\": \"colete\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"divisao\": \"Sala de Comando\",\n" +
            "          \"pontos-recuperados\": 25,\n" +
            "          \"tipo\": \"kit de vida\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"divisao\": \"Escada de Serviço\",\n" +
            "          \"pontos-extra\": 15,\n" +
            "          \"tipo\": \"granada\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"divisao\": \"Depósito de Armamentos\",\n" +
            "          \"pontos-recuperados\": 20,\n" +
            "          \"tipo\": \"kit de vida\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"divisao\": \"Posto Avançado\",\n" +
            "          \"pontos-extra\": 30,\n" +
            "          \"tipo\": \"armadura leve\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}\n";


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

    private String fileNameForLogs;

    /**
     * Default constructor for the Importer class.
     */
    public Importer() {
        this.fileNameForLogs = "";
    }

    /**
     * Gets the file name for the logs.
     * @return The file name for the logs.
     */
    public String getFileNameForLogs() {
        return fileNameForLogs;
    }

    /**
     * Loads the mission list from the specified JSON file.
     *
     * @param filePath Path to the mission JSON file.
     * @return A list of mission codes.
     * @throws IOException    If the file cannot be read.
     * @throws ParseException If the file cannot be parsed.
     */
    public Missions loadMissions(String filePath) throws IOException, ParseException {

        String PATH = filePath + ".json";
        String LOG_JSON = filePath + "Logs.json";
        this.fileNameForLogs = LOG_JSON;

        if (!Files.exists(Paths.get(PATH)) || Files.size(Paths.get(PATH)) == 0) {
            String name = "DefaultMission";
            String json = ".json";
            PATH = name + json;

            LOG_JSON = name + "Logs" + json;

            Files.write(Paths.get(PATH), MISSIONS_JSON_DEFAULT.getBytes(), StandardOpenOption.CREATE);
            System.out.println("Warning: File not found or empty, loading default missions");
            this.fileNameForLogs = LOG_JSON;
        }


        JSONObject missionsJSON = (JSONObject) PARSER.parse(new String(Files.readAllBytes(Paths.get(PATH))));


        if (Files.exists(Paths.get(LOG_JSON)) && Files.size(Paths.get(LOG_JSON)) > 0) {
            JSONObject missionsLogsJson = (JSONObject) PARSER.parse(new String(Files.readAllBytes(Paths.get(LOG_JSON))));
            missionsLogs = (JSONArray) missionsLogsJson.get("missions");
        } else {
            missionsLogs = new JSONArray();
        }

        this.missions = (JSONArray) missionsJSON.get("missoes");
        Missions newMissions = new MissionsImp();

        for (Object obj : missions) {
            JSONObject mission = (JSONObject) obj;
            String codMission = (String) mission.get("cod-missao");
            int versMission = ((Long) mission.get("versao")).intValue();
            Mission newMission = new MissionImp(codMission, versMission);
            importFiles(newMission, mission);
            newMissions.addMission(newMission);
        }

        addLogsToMissions(newMissions);
        return newMissions;
    }


    private void importFiles(Mission msn, JSONObject mission) {
        Map map = new MapImp();

        this.rooms = (JSONArray) mission.get("edificio");
        this.enemies = (JSONArray) mission.get("inimigos");
        this.connections = (JSONArray) mission.get("ligacoes");
        this.items = (JSONArray) mission.get("itens");
        this.exitsAndEntrances = (JSONArray) mission.get("entradas-saidas");
        this.target = (JSONObject) mission.get("alvo");

        msn.addMap(this.constructMap(map));
    }


    /**
     * Imports a new map from the specified JSON file and constructs the game map.
     * @param nameMission The name of the mission to import.
     * @return The constructed mission.
     * @throws ParseException If the file cannot be parsed.
     */
    public Mission importFiles(String nameMission) throws ParseException {
        Mission msn = null;
        Map map = new MapImp();

        for (Object obj : missions) {
            JSONObject mission = (JSONObject) obj;
            if (nameMission.equals(mission.get("cod-missao"))) {
                String codMission = (String) mission.get("cod-missao");
                int versMission = ((Long) mission.get("versao")).intValue();
                msn = new MissionImp(codMission, versMission);
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


    private void addLogsToMissions(Missions missions) {
        try {
            for (Object missionObj : missionsLogs) {
                JSONObject missionJson = (JSONObject) missionObj;
                String missionName = (String) missionJson.get("name");
                Mission mission = missions.getMissionByCode(missionName);
                if (mission == null) {
                    continue;
                }

                JSONArray logsArray = (JSONArray) missionJson.get("logs");
                for (Object logObj : logsArray) {
                    JSONObject logJson = (JSONObject) logObj;
                    String timestamp = (String) logJson.get("timestamp");
                    ManualSimulationLog manualSimulationLog = new ManualSimulationLog(timestamp);

                    JSONArray pathArray = (JSONArray) logJson.get("path");
                    for (Object pathElement : pathArray) {
                        String roomName = (String) pathElement;
                        Room room = mission.getMap().getRoomByName(roomName);
                        manualSimulationLog.addRoom(room);
                    }

                    JSONObject heroJson = (JSONObject) logJson.get("hero");
                    if (heroJson != null) {
                        double health = ((Double) heroJson.get("health"));
                        double armorHealth = (Double) heroJson.get("armorHealth");
                        Hero hero = new HeroImp(health, armorHealth);
                        manualSimulationLog.setHero(hero);
                    }

                    mission.addManualSimulationLog(manualSimulationLog);
                }
            }
        } catch (Exception e) {
            System.out.println("Error adding manual simulation logs");
        }
    }
}
