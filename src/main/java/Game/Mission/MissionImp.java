package Game.Mission;

import Game.Interfaces.Map;
import Game.Interfaces.Mission;
import Game.Utilities.Logs;
import Game.Utilities.ManualSimulationLog;

/**
 * Represents a mission
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class MissionImp implements Mission {

    /**
     * The code of the mission
     */
    private String code;

    /**
     * The version of the mission
     */
    private int version;

    /**
     * The map of the mission
     */
    private Map map;

    /**
     * The logs of the mission
     */
    private final Logs logs;

    /**
     * Constructor for the mission
     */
    public MissionImp() {
        this.code = null;
        this.version = -1;
        this.map = null;
        this.logs = new Logs();
    }

    /**
     * Getter for the map
     *
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Setter for the code
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Setter for the version
     * @param version the version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Adds a room to the map
     *
     * @param map the map to add
     */
    public void addMap(Map map) {
        this.map = map;
    }

    /**
     * Returns the unique code identifying the mission.
     *
     * @return the mission code
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * Returns the version of the mission.
     *
     * @return the version of the mission as an integer
     */
    @Override
    public int getVersion() {
        return version;
    }


    /**
     * Adds a log from the manual simulation to the mission
     * @param manualsimulationlog the manual simulation log
     */
    public void addManualSimulationLog(ManualSimulationLog manualsimulationlog) {
        this.logs.addManualSimulationLog(manualsimulationlog);
    }

    /**
     * Getter for the logs
     * @return the logs
     */
    public Logs getLogs() {
        return logs;
    }

}