package Game.IO;

import Game.Interfaces.Mission;
import Game.Interfaces.Missions;
import Game.Interfaces.Room;
import Game.Utilities.Logs;
import Game.Utilities.ManualSimulationLog;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * Class that exports the logs of the manual simulations to a JSON file
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class Exporter {

    /**
     * The missions to export ( simulation logs )
     */
    private final Missions missions;


    /**
     * The constructor for the Exporter class
     *
     * @param missions The missions to export
     */
    public Exporter(Missions missions) {
        this.missions = missions;
    }

    /**
     * Method that saves the logs to a JSON file
     *
     * @throws IOException if an error occurs while writing to the file
     */
    public void save(String fileName) throws IOException {
        this.saveLogs(fileName);
    }

    /**
     * Method that saves the logs to a JSON file
     * @param FileName The name of the file to save the logs ( with the .json extension ) and comes from the user input
     * in the main menu . If the default mission was loaded it will be DefaultMissionLogs.json
     * @throws IOException if an error occurs while writing to the file
     */
    public void saveLogs(String FileName) throws IOException {
        JSONObject root = new JSONObject();
        JSONArray missionsArray = new JSONArray();


        Iterator<Mission> it = missions.getMissions().iterator();
        while (it.hasNext()) {
            Mission mission = it.next();
            Logs logs = mission.getLogs();


            JSONArray manualLogs = saveManualSimulationLogs(logs);


            JSONObject existingMission = findMissionByName(missionsArray, mission.getCode());
            if (existingMission != null) {
                JSONArray existingLogs = (JSONArray) existingMission.get("logs");
                existingLogs.addAll(manualLogs);
            } else {

                JSONObject missionObject = new JSONObject();
                missionObject.put("name", mission.getCode());
                missionObject.put("vers", mission.getVersion());
                missionObject.put("logs", manualLogs);
                missionsArray.add(missionObject);
            }
        }


        root.put("missions", missionsArray);

        try (FileWriter file = new FileWriter(FileName)) {
            file.write(root.toJSONString());
        }
    }

    /**
     * Method that finds a mission by its name
     *
     * @param missionsArray The array of missions
     * @param missionName   The name of the mission to find
     * @return The mission if found, null otherwise
     */
    private JSONObject findMissionByName(JSONArray missionsArray, String missionName) {
        for (Object obj : missionsArray) {
            JSONObject mission = (JSONObject) obj;
            if (missionName.equals(mission.get("name"))) {
                return mission;
            }
        }
        return null;
    }

    /**
     * Method that saves the manual simulation logs to a JSON array
     *
     * @param logs The logs to save
     * @return The JSON array with the logs
     */
    private JSONArray saveManualSimulationLogs(Logs logs) {
        JSONArray manualLogs = new JSONArray();

        Iterator<ManualSimulationLog> it = logs.getManualSimulationLogs().iterator();
        while (it.hasNext()) {
            ManualSimulationLog log = it.next();

            JSONObject logObject = new JSONObject();
            logObject.put("path", pathToJsonArray(log));
            logObject.put("hero", heroToJsonObject(log));
            logObject.put("timestamp", log.getTimestamp());

            manualLogs.add(logObject);
        }

        return manualLogs;
    }

    /**
     * Method that converts a path to a JSON array
     *
     * @param log The log to convert
     * @return The JSON array with the path
     */
    private JSONArray pathToJsonArray(ManualSimulationLog log) {
        JSONArray pathArray = new JSONArray();
        Iterator<Room> it = log.getPath().iterator();

        while (it.hasNext()) {
            Room room = it.next();
            pathArray.add(room.getRoomName());
        }

        return pathArray;
    }

    /**
     * Method that converts a hero to a JSON object
     *
     * @param log The log to convert
     * @return The JSON object with the hero
     */
    private JSONObject heroToJsonObject(ManualSimulationLog log) {
        JSONObject hero = new JSONObject();
        hero.put("health", log.getHero().getHealth());
        hero.put("armorHealth", log.getHero().getArmorHealth());
        return hero;
    }
}
