package Game;

import Collections.Lists.*;
import Game.Entities.HeroImp;
import Game.Exceptions.EnemyException;
import Game.Exceptions.HeroException;
import Game.Exceptions.ItemException;
import Game.Exceptions.TargetException;
import Game.IO.Exporter;
import Game.IO.Importer;
import Game.Interfaces.*;
import Game.Navigation.MissionsImp;
import Game.PlayGame.GameEngine;
import Game.PlayGame.Input;
import Game.PlayGame.Menu;
import Game.PlayGame.Print;
import Game.Utilities.ManualSimulationLog;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Iterator;

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













