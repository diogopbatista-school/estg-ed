package Game.IO;

import Collections.Lists.UnorderedListADT;
import Game.Interfaces.Hero;
import Game.Interfaces.Room;
import Game.Utilities.Logs;
import Game.Utilities.ManualSimulationLog;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class Exporter {

    public Exporter() {

    }

    public void saveManualSimulation(Logs manualSimulationLogs) {
        //mudar depois, pega nos manualsimlationlog antigos primeiro
        JSONParser jsonParser = new JSONParser();
        JSONArray existingLogs = new JSONArray();
        try (FileReader reader = new FileReader("ManualSimulations.json")) {
            Object obj = jsonParser.parse(reader);
            existingLogs = (JSONArray) obj;
        } catch (IOException | ParseException e) {
            System.out.println("No existing logs found, creating a new file.");
        }

        //iterar
        Iterator<ManualSimulationLog> iterator = manualSimulationLogs.getManualSimulationLogs().iterator();

        while (iterator.hasNext()) {
            ManualSimulationLog manualSimulationLog = iterator.next();
            JSONObject simulationDetails = new JSONObject();


            simulationDetails.put("timestamp", manualSimulationLog.getTimestamp());
            Hero hero = manualSimulationLog.getHero();
            JSONObject heroDetails = new JSONObject();
            heroDetails.put("health", hero.getHealth());
            heroDetails.put("armorHealth", hero.getArmorHealth());
            // depois aq metemos as cenas novas

            simulationDetails.put("hero", heroDetails);

            //itera o path
            JSONArray pathArray = new JSONArray();
            UnorderedListADT<Room> path = manualSimulationLog.getPath();
            Iterator<Room> pathIterator = path.iterator();

            while (pathIterator.hasNext()) {
                Room room = pathIterator.next();
                System.out.println(room.getRoomName());
                pathArray.add(room.getRoomName());
            }

            simulationDetails.put("path", pathArray);
            existingLogs.add(simulationDetails);
        }

        //escreve no json / sabes alguma maneira de escrever ja formatado??
        try (FileWriter file = new FileWriter("ManualSimulations.json")) {
            file.write(existingLogs.toJSONString());
            file.flush();
            System.out.println("File was written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}