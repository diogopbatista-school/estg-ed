package Game.Utilities;

import Collections.Lists.OrderedListADT;
import Collections.Lists.UnorderedListADT;
import Game.Interfaces.Hero;
import Game.Interfaces.Room;

public class ManualSimulationLog implements Comparable<ManualSimulationLog> {
    private Hero hero;
    private UnorderedListADT<Room> path;
    private String timestamp;

    public ManualSimulationLog(String timestamp) {
        this.hero = null;
        this.path = null;
        this.timestamp = timestamp;
    }

    public ManualSimulationLog(Hero hero, OrderedListADT<Room> path) {
        this.hero = hero;
        this.path = null;
        this.timestamp = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date());
    }

    public void setHero(Hero hero){
        this.hero = hero;
    }

    public void setPath(UnorderedListADT<Room> path){
        this.path = path;
    }

    public void addRoom(Room room){
        this.path.addToRear(room);
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

    @Override
    public int compareTo(ManualSimulationLog other) {
        return Integer.compare(other.getHero().getHealth(), this.getHero().getHealth());
    }


}
