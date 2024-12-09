package Game.Interfaces;

import Collections.Lists.UnorderedListADT;

/**
 * Represents a list of missions for the simulation
 * Every time a simulation is made , it will be added to the list of missions
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
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
