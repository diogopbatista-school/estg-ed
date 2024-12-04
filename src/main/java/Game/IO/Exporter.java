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

    public void save() throws IOException{
        this.saveLogs();
    }

    public void saveLogs() throws IOException{
        JSONArray allLogs = new JSONArray();
        JSONObject MissionsLogs = new JSONObject();

        Iterator<Mission> it = missions.getMissions().iterator();
        while(it.hasNext()){
            Mission mission = it.next();
            Logs logs = mission.getLogs();
            JSONArray ManualLogs = saveManualSimulationLogs(logs);
            MissionsLogs.put(mission.getCode(),ManualLogs);


            allLogs.add(MissionsLogs);
        }


        try (FileWriter file = new FileWriter("MissionsLogs.json")) {
            file.write(allLogs.toJSONString());
        }
    }

    private JSONArray saveManualSimulationLogs(Logs logs) throws IOException{
        JSONArray ManualLogs = new JSONArray();

        Iterator<ManualSimulationLog> it = logs.getManualSimulationLogs().iterator();
        while(it.hasNext()){
            ManualSimulationLog log = it.next();
            ManualLogs.add(pathToJsonObject(log));
            ManualLogs.add(heroToJsonObject(log));

            JSONObject time = new JSONObject();
            String date= log.getTimestamp();
            time.put("timestamp", date);

            ManualLogs.add(time);
        }
        return ManualLogs;

    }

    private JSONObject pathToJsonObject(ManualSimulationLog log){
        JSONObject jsonObject = new JSONObject();
        JSONArray pathArray = new JSONArray();
        Iterator<Room> it = log.getPath().iterator();

        while (it.hasNext()) {
            Room room = it.next();
            pathArray.add(room.getRoomName());
        }

        jsonObject.put("path", pathArray);
        return jsonObject;
    }

    private JSONObject heroToJsonObject(ManualSimulationLog log){
        JSONObject jsonObject = new JSONObject();
        JSONObject hero = new JSONObject();
        hero.put("health",log.getHero().getHealth());
        hero.put("armorHealth", log.getHero().getArmorHealth());
        jsonObject.put("hero", hero);
        return jsonObject;
    }

    private void saveExceptionsLogs(Logs logs) throws IOException{

    }

}





