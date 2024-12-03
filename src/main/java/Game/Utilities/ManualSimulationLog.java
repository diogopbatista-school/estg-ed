package Game.Utilities;

import Collections.Lists.UnorderedListADT;
import Game.Interfaces.Hero;
import Game.Interfaces.Room;

public class ManualSimulationLog {
    Hero hero;
    UnorderedListADT<Room> path;
    String timestamp;

    public ManualSimulationLog(Hero hero, UnorderedListADT<Room> path){
    this.hero = hero;
    this.path = path;
    this.timestamp = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date());
    }
    public Hero getHero(){
        return this.hero;
    }
    public UnorderedListADT<Room> getPath(){
        return this.path;
    }

    public String getTimestamp() {
        return this.timestamp;
    }
}
