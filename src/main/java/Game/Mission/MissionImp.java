package Game.Mission;

import Game.Interfaces.Map;
import Game.Interfaces.Mission;
import Game.Utilities.LogExceptions;
import Game.Utilities.Logs;
import Game.Utilities.ManualSimulationLog;

public class MissionImp implements Mission {


    private String code;
    private int version;
    private Map map;
    private Logs logs;


    public MissionImp() {
        this.code = null;
        this.version = -1;
        this.map = null;
        this.logs = new Logs();
    }

    public Map getMap() {
        return map;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public void addLogException(LogExceptions log) {
        this.logs.addExceptionLog(log);
    }

    public void addManualSimulationLog(ManualSimulationLog manualsimulationlog) {
        this.logs.addManualSimulationLog(manualsimulationlog);
    }

    public Logs getLogs() {
        return logs;
    }

}