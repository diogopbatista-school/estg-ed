package Game;


import Collections.Lists.LinkedUnorderedList;
import Collections.Lists.UnorderedListADT;
import Game.IO.Importer;
import Game.Interfaces.*;
import Game.PlayGame.Input;
import Game.PlayGame.Menu;
import Game.PlayGame.Print;

import java.util.Iterator;

/**
 * The main class of the game
 */
public class Play {
    /**
     * The main method of the game
     * @param args The arguments of the game
     */
    public static void main(String[] args) {
        Input input = new Input();
        Print print = new Print();
        Importer importer = new Importer();
        UnorderedListADT<String> missionCodes = null;
        Missions missions;

        try {
            System.out.print("Enter the name of the missions file: ");
            String fileName = input.readString();
            missions = importer.loadMissions(fileName);


            Iterator<Mission> it = missions.getMissions().iterator();
            while (it.hasNext()) {
                Mission mission = it.next();
                if (missionCodes == null) {
                    missionCodes = new LinkedUnorderedList<>();
                }
                missionCodes.addToRear(mission.getCode());
            }


            Menu menu = new Menu(missions, input, print);
            menu.mainMenu(missionCodes, importer);
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Error loading missions: " + e.getMessage());
            System.exit(1);
        }
    }
}













