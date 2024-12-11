package Game.Utilities;

import Collections.Lists.LinkedOrderedList;
import Collections.Lists.OrderedListADT;
import Game.Interfaces.Hero;
import Game.Interfaces.Room;

/**
 * Represents the logs of the game
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class ManualSimulationLog implements Comparable<ManualSimulationLog> {

    /**
     * The hero to show how much health he has left
     */
    private Hero hero;

    /**
     * The path the hero took
     */
    private OrderedListADT<Room> path;

    /**
     * The timestamp of the log
     */
    private final String timestamp;

    /**
     * Constructor for the ManualSimulationLog class .
     * This constructor is used for the import of logs from a file
     *
     * @param timestamp The timestamp of the log
     */
    public ManualSimulationLog(String timestamp) {
        this.hero = null;
        this.path = new LinkedOrderedList<>();
        this.timestamp = timestamp;
    }

    /**
     * Constructor for the ManualSimulationLog class
     *
     * @param hero The hero to show how much health he has left
     * @param path The path the hero took
     */
    public ManualSimulationLog(Hero hero, OrderedListADT<Room> path) {
        this.hero = hero;
        this.path = path;
        this.timestamp = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date());
    }

    /**
     * Method that sets the hero of the log
     *
     * @param hero The hero to set
     */
    public void setHero(Hero hero) {
        this.hero = hero;
    }

    /**
     * Method that sets the path of the log
     *
     * @param path The path to set
     */
    public void setPath(OrderedListADT<Room> path) {
        this.path = path;
    }

    /**
     * Method that adds a room to the path
     *
     * @param room The room to add
     */
    public void addRoom(Room room) {
        this.path.add(room);
    }

    /**
     * Method that returns the hero of the log
     *
     * @return The hero of the log
     */
    public Hero getHero() {
        return this.hero;
    }

    /**
     * Method that returns the path of the log
     *
     * @return The path of the log in a list of rooms
     */
    public OrderedListADT<Room> getPath() {
        return this.path;
    }

    /**
     * Method that returns the timestamp of the log
     *
     * @return The timestamp of the log
     */
    public String getTimestamp() {
        return this.timestamp;
    }

    /**
     * Method that returns the log in a string format
     *
     * @return The log in a string format
     */
    @Override
    public String toString() {
        return "| Log | \n" +
                "+-----+\n" +
                "   path='" + path.toString() + "\n" +
                "   hero='" + hero.toString() + "\n" +
                "   timestamp='" + timestamp + "\n" +
                "+-----------------------------------------------+";
    }

    /**
     * Method that compares two logs by the total health of the hero
     *
     * @param other the object to be compared.
     * @return the value 0 if the argument hero is equal to this hero; a value less than 0
     * if this hero is less than the hero argument; and a value greater than 0 if this hero is greater than the hero argument.
     */
    @Override
    public int compareTo(ManualSimulationLog other) {
        double thisTotalHealth = this.getHero().getArmorHealth() + this.getHero().getHealth();
        double otherTotalHealth = other.getHero().getArmorHealth() + other.getHero().getHealth();
        return Double.compare(otherTotalHealth, thisTotalHealth);
    }

}
