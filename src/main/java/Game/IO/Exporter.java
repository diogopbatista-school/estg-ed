package Game.IO;


import Game.Interfaces.Room;
import Game.Utilities.Logs;
import Game.Utilities.ManualSimulationLog;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;


public class Exporter {

    private Logs logs;

    public Exporter(Logs logs) {
        this.logs = logs;
    }

    public void save() throws IOException{
        this.saveLogs();
    }

    public void saveLogs() throws IOException{
        saveManualSimulationLogs();
        //saveExceptionsLogs(logs);
    }

    private void saveManualSimulationLogs() throws IOException{
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

        try(FileWriter file = new FileWriter("ManualSimulationLogs.json")){
            file.write(ManualLogs.toJSONString());
        }

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





