package Game.Interfaces;


import Game.Utilities.Logs;
import Game.Utilities.ManualSimulationLog;

/**
 * Represents a mission in the game
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public interface Mission {

    /**
     * Getter for the logs
     *
     * @return the logs
     */
    Logs getLogs();

    /**
     * Getter for the map
     *
     * @return the map
     */
    Map getMap();

    /**
     * Setter for the code
     *
     * @param code the code
     */
    void setCode(String code);

    /**
     * Setter for the version
     *
     * @param version the version
     */
    void setVersion(int version);

    /**
     * Adds a manual simulation log
     *
     * @param manualsimulationlog the manual simulation log
     */
    void addManualSimulationLog(ManualSimulationLog manualsimulationlog);

    /**
     * Returns the unique code identifying the mission.
     *
     * @return the mission code
     */
    String getCode();

    /**
     * Returns the version of the mission.
     *
     * @return the version of the mission as an integer
     */
    int getVersion();

    /**
     * Adds the map to the mission
     *
     * @param map the map to add
     */
    void addMap(Map map);

}