package Game;



import Collections.Lists.UnorderedListADT;
import Game.IO.Importer;
import Game.Interfaces.*;
import Game.PlayGame.Input;
import Game.PlayGame.Menu;
import Game.PlayGame.Print;

/**
 * The main class of the game
 */
public class Play {
    public static void main(String[] args) {
        Input input = new Input();
        Print print = new Print();
        Importer importer = new Importer();
        UnorderedListADT<String> missionCodes ;
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













