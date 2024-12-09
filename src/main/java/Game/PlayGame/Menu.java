package Game.PlayGame;

import Collections.Lists.OrderedListADT;
import Collections.Lists.UnorderedListADT;
import Game.Entities.HeroImp;
import Game.Exceptions.EnemyException;
import Game.Exceptions.HeroException;
import Game.Exceptions.RoomException;
import Game.Exceptions.TargetException;
import Game.IO.Exporter;
import Game.IO.Importer;
import Game.Interfaces.Hero;
import Game.Interfaces.Mission;
import Game.Interfaces.Missions;
import Game.Interfaces.Room;

import java.io.IOException;
import java.util.Iterator;

public class Menu {


    private final Input input ;
    private final Print print;
    private GameEngine gameEngine;
    private Missions missions;

    public Menu(Missions missions, Input input,Print print) {
        this.input = input;
        this.print = print;
        this.missions = missions;
        this.gameEngine = new GameEngine(input,print,this);
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void mainMenu(UnorderedListADT<String> missionCodes, Importer importer){
        Exporter exporter = new Exporter(missions);

        print.mainMenu();
        int choice = input.getValidNumberInput(1,2);

        if(choice == 1){
            startGameMenu(missions,missionCodes,importer,exporter);
        }else{
            System.out.println("Exiting Game");
            System.exit(0);
        }


        System.out.println("Exiting. Goodbye!");
        System.exit(0);
    }

    public void startGameMenu(Missions missions, UnorderedListADT<String> missionCodes, Importer importer, Exporter exporter) {
       boolean playAgain = true;

       while(playAgain) {
           print.availableMissions(missionCodes);
           String selectedMission = chooseMapMenu(missionCodes);
           Mission mission;

           try{
               mission = importer.importData(selectedMission);

               print.loadedMissionMenu(mission,selectedMission);

               int playModeChoise = input.getValidNumberInput(1,2);

               if(playModeChoise == 1){
                   gameEngine.playManually(missions, mission);
               }else if(playModeChoise == 2){
                   gameEngine.playAutomatically(mission);
               }

           } catch (IOException | HeroException | EnemyException | TargetException | RoomException e) {
               throw new RuntimeException(e);
           }

           print.playAgain();
           int playAgainChoice = input.getValidNumberInput(1, 2);
           playAgain = (playAgainChoice == 1);
       }

        try{
            exporter.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       System.out.println("Exiting. Goodbye!");
       System.exit(0);

    }

    public String chooseMapMenu(UnorderedListADT<String> missionCodes ) {
        int missionChoice = input.chooseMapValidationInput(missionCodes);

        Iterator<String> iterator = missionCodes.iterator();
        String selectedMission = null;
        for (int i = 0; i < missionChoice; i++) {
            selectedMission = iterator.next();
        }

        return selectedMission;
    }

    public int chooseNextRoom(UnorderedListADT<Room> connectedRooms){
        System.out.print("Select a connected room by number: ");
        return input.chooseNextRoom(connectedRooms);
    }

    public int MoveOrUseItemMenu(Hero hero){
        print.MoveOrUseItem(hero);
        return input.getValidMoveInput(hero);
    }

    public boolean confirmRoomEntry(Room selectedRoom, Hero hero, OrderedListADT<Room> path) throws HeroException {
        boolean roomConfirmed = input.confirmRoomEntry("The selected room contains enemies. Do you want to enter? (yes/no): ");
        if (roomConfirmed) {
            selectedRoom.addHero(hero);
            path.add(selectedRoom);
            clearConsole();
            print.heroWillStartRoom(selectedRoom);
        }
        return roomConfirmed;
    }

    public int fightOrUseItem(boolean hasItems) {
        int actionChoice;
        do {
            actionChoice = input.chooseHeroAction(hasItems);
            if (actionChoice != 1 && (!hasItems || actionChoice != 2)) {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (actionChoice != 1 && (!hasItems || actionChoice != 2));
        return actionChoice;
    }

    public int handleItemMenu() {
        return input.handleItemUsage("Hero's health is full. Do you still want to use the item? (yes/no): ", "Choose a number between 1 and 2: ");
    }

    public int handleItemWasteMenu() {
        return input.handleItemUsage("Using this item will waste points. Do you still want to use the item? (yes/no): ", "Choose a number between 1 and 2: ");
    }

    public Hero createHero() {
        int attackValue = input.getValidPositiveNumber("Enter the attack value for your hero (greater than 0): ");
        int backpackCapacity = input.getValidPositiveNumber("Enter the capacity of your hero's backpack (greater than 0): ");
        clearConsole();
        return new HeroImp(attackValue, backpackCapacity);
    }

    public int selectRoom(int maxRooms) {
        return input.getValidRoomChoice(maxRooms);
    }

    private void clearConsole(){
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
