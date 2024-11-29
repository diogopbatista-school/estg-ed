package Game.Mission;

import Game.Interfaces.Map;
import Game.Interfaces.Mission;
import Game.Navigation.MapImp;
import Game.Interfaces.*;

public class MissionImp implements Mission {


    private String code;
    private int version;
    private Map map;


    /**
     * Constructor for the MissionImp class.
     * Initializes a mission with a given code and version, sets up empty lists
     * for enemies and items, and initializes the target to null.
     *
     * @param cod_mission the unique code identifying the mission.
     * @param vers_mission the version of the mission.
     */
    public MissionImp(String cod_mission,int vers_mission) {
        this.code = cod_mission;
        this.version = vers_mission;
        this.map = new MapImp();
    }

    /**
     * Adds a room to the map
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

    /** fazer depois com map */

}