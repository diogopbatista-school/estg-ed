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
        JSONObject allLogs = new JSONObject();

        Iterator<Mission> it = missions.getMissions().iterator();
        while (it.hasNext()) {
            Mission mission = it.next();
            Logs logs = mission.getLogs();


            JSONArray manualLogs = saveManualSimulationLogs(logs);


            if (allLogs.containsKey(mission.getCode())) {
                JSONArray existingLogs = (JSONArray) allLogs.get(mission.getCode());
                existingLogs.addAll(manualLogs);
            } else {
                allLogs.put(mission.getCode(), manualLogs);
            }
        }

        try (FileWriter file = new FileWriter("MissionsLogs.json")) {
            file.write(allLogs.toJSONString());
        }
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
