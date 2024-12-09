package Game;

import Collections.Lists.*;
import Game.IO.Importer;
import Game.Interfaces.*;
import Game.PlayGame.Input;
import Game.PlayGame.Menu;
import Game.PlayGame.Print;

public class Play {
    public static void main(String[] args) {
        Input input = new Input();
        Print print = new Print();
        Importer importer = new Importer();
        UnorderedListADT<String> missionCodes = new LinkedUnorderedList<>();
        Missions missions;

        try {
            missionCodes = importer.loadMissions("Missoes.json");
            missions = importer.loadMissions();
            Menu menu = new Menu(missions, input, print);
            menu.mainMenu(missionCodes, importer);
        } catch (Exception e) {
            System.out.println("Error loading missions: " + e.getMessage());
            System.exit(1);
        }
    }
}













