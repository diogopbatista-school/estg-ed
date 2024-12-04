package Game.Interfaces;

import Collections.Lists.UnorderedListADT;

public interface Missions {

    /**
     * Adds a mission to the list of missions
     * @param mission the mission to add
     */
    public void addMission(Mission mission);

    /**
     * Returns the list of missions
     * @return the list of missions
     */
    public UnorderedListADT<Mission> getMissions();
}
