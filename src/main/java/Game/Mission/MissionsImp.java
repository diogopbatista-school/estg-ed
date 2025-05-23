package Game.Mission;

import Collections.Lists.LinkedUnorderedList;
import Collections.Lists.UnorderedListADT;
import Game.Interfaces.Mission;
import Game.Interfaces.Missions;

import java.util.Iterator;

/**
 * This class represents all the simulations of the missions played
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class MissionsImp implements Missions {

    /**
     * The list of missions
     */
    private final UnorderedListADT<Mission> missions;

    /**
     * Constructor for the missions
     */
    public MissionsImp() {
        this.missions = new LinkedUnorderedList<>();
    }

    /**
     * Method that adds a mission to the list of missions
     *
     * @param mission the mission to add
     */
    public void addMission(Mission mission) {
        this.missions.addToRear(mission);
    }

    /**
     * Method that removes a mission from the list of missions
     *
     * @return the mission removed
     */
    public UnorderedListADT<Mission> getMissions() {
        return missions;
    }

    /**
     * Method that returns a mission by its code
     *
     * @param missionName the name of the mission
     * @return the mission
     */
    public Mission getMissionByCode(String missionName) {
        Iterator<Mission> it = missions.iterator();
        while (it.hasNext()) {
            Mission mission = it.next();
            if (mission.getCode().equals(missionName)) {
                return mission;
            }
        }
        return null;
    }

}
