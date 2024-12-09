package Game.Mission;

import Collections.Lists.LinkedUnorderedList;
import Collections.Lists.UnorderedListADT;
import Game.Interfaces.Mission;
import Game.Interfaces.Missions;

public class MissionsImp implements Missions {

    private UnorderedListADT<Mission> missions;


    public MissionsImp() {
        this.missions = new LinkedUnorderedList<>();
    }

    public void addMission(Mission mission) {
        this.missions.addToRear(mission);
    }

    public UnorderedListADT<Mission> getMissions() {
        return missions;
    }

}
