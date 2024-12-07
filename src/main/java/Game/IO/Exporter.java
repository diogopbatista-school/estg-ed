package Game.IO;

import Collections.Lists.UnorderedListADT;
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

public class Exporter {

    private Missions missions;

    public Exporter(Missions missions) {
        this.missions = missions;
    }

    public void save() throws IOException {
        this.saveLogs();
    }

    public void saveLogs() throws IOException {
        JSONObject root = new JSONObject();
        JSONArray missionsArray = new JSONArray();

        // Iterar sobre as missões
        Iterator<Mission> it = missions.getMissions().iterator();
        while (it.hasNext()) {
            Mission mission = it.next();
            Logs logs = mission.getLogs();

            // Criar os logs manuais
            JSONArray manualLogs = saveManualSimulationLogs(logs);

            // Verificar se a missão já existe no array
            JSONObject existingMission = findMissionByName(missionsArray, mission.getCode());
            if (existingMission != null) {
                JSONArray existingLogs = (JSONArray) existingMission.get("logs");
                existingLogs.addAll(manualLogs); // Adicionar logs à missão existente
            } else {
                // Criar uma nova entrada de missão
                JSONObject missionObject = new JSONObject();
                missionObject.put("name", mission.getCode());
                missionObject.put("vers", mission.getVersion());
                missionObject.put("logs", manualLogs);
                missionsArray.add(missionObject);
            }
        }

        // Adicionar as missões ao objeto raiz
        root.put("missions", missionsArray);

        // Escrever no arquivo
        try (FileWriter file = new FileWriter("MissionsLogs.json")) {
            file.write(root.toJSONString());
        }
    }

    private JSONObject findMissionByName(JSONArray missionsArray, String missionName) {
        for (Object obj : missionsArray) {
            JSONObject mission = (JSONObject) obj;
            if (missionName.equals(mission.get("name"))) {
                return mission;
            }
        }
        return null;
    }

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

    private JSONArray pathToJsonArray(ManualSimulationLog log) {
        JSONArray pathArray = new JSONArray();
        Iterator<Room> it = log.getPath().iterator();

        while (it.hasNext()) {
            Room room = it.next();
            pathArray.add(room.getRoomName());
        }

        return pathArray;
    }

    private JSONObject heroToJsonObject(ManualSimulationLog log) {
        JSONObject hero = new JSONObject();
        hero.put("health", log.getHero().getHealth());
        hero.put("armorHealth", log.getHero().getArmorHealth());
        return hero;
    }
}
