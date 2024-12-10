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

/**
 * Class that represents all menus of the game
 */
public class Menu {

    /**
     * The input class
     */
    private final Input input;

    /**
     * The print class
     */
    private final Print print;

    /**
     * The game engine
     */
    private final GameEngine gameEngine;

    /**
     * The missions class to store the simulations of the game
     */
    private final Missions missions;

    public Menu(Missions missions, Input input, Print print) {
        this.input = input;
        this.print = print;
        this.missions = missions;
        this.gameEngine = new GameEngine(print, this);
    }


    /**
     * Method that represents the main menu of the game
     *
     * @param missionCodes the available missions
     * @param importer     the importer to load the missions
     */
    public void mainMenu(UnorderedListADT<String> missionCodes, Importer importer) {
        Exporter exporter = new Exporter(missions);

        print.mainMenu();
        int choice = input.getValidNumberInput(2);

        if (choice == 1) {
            startGameMenu(missions, missionCodes, importer, exporter);
        } else {
            System.out.println("Exiting Game");
            System.exit(0);
        }


        System.out.println("Exiting. Goodbye!");
        System.exit(0);
    }

    /**
     * Method that represents the start game menu
     *
     * @param missions     the missions class to store the simulations of the game
     * @param missionCodes the available missions
     * @param importer     the importer to load the missions
     * @param exporter     the exporter to save the missions
     */
    public void startGameMenu(Missions missions, UnorderedListADT<String> missionCodes, Importer importer, Exporter exporter) {
        boolean playAgain = true;

        while (playAgain) {
            print.availableMissions(missionCodes);
            String selectedMission = chooseMapMenu(missionCodes);
            Mission mission;

            try {
                mission = importer.importData(selectedMission);

                print.loadedMissionMenu(mission, selectedMission);

                int playModeChoise = input.getValidNumberInput(2);

                if (playModeChoise == 1) {
                    gameEngine.playManually(missions, mission);
                } else if (playModeChoise == 2) {
                    gameEngine.playAutomatically(mission);
                }

            } catch (IOException | HeroException | EnemyException | TargetException | RoomException e) {
                throw new RuntimeException(e);
            }

            print.playAgain();
            int playAgainChoice = input.getValidNumberInput(2);
            playAgain = (playAgainChoice == 1);
        }

        try {
            exporter.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Exiting. Goodbye!");
        System.exit(0);

    }

    /**
     * Method that represents the choose map menu
     *
     * @param missionCodes the available missions
     * @return the selected mission
     */
    public String chooseMapMenu(UnorderedListADT<String> missionCodes) {
        int missionChoice = input.chooseMapValidationInput(missionCodes);

        Iterator<String> iterator = missionCodes.iterator();
        String selectedMission = null;
        for (int i = 0; i < missionChoice; i++) {
            selectedMission = iterator.next();
        }

        return selectedMission;
    }

    /**
     * Method that represents the next room menu to choose the next room to go to
     *
     * @param connectedRooms the connected rooms
     * @return the selected room
     */
    public int chooseNextRoom(UnorderedListADT<Room> connectedRooms) {
        System.out.print("Select a connected room by number: ");
        return input.chooseNextRoom(connectedRooms);
    }

    /**
     * Method that represents the options menu for the hero to move or use an item
     *
     * @param hero the hero to move or to use item
     * @return the selected option
     */
    public int MoveOrUseItemMenu(Hero hero) {
        print.MoveOrUseItem(hero);
        return input.getValidMoveInput(hero);
    }

    /**
     * Method that represents the menu confirmation to enter the start room
     *
     * @param selectedRoom the selected room
     * @param hero         the hero to add to the room
     * @param path         the path to add the room to the path list
     * @return true if the room is confirmed, false otherwise
     * @throws HeroException if the hero cannot be added to the room
     */
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

    /**
     * Method that represents if the hero wants to fight or use an item
     *
     * @param hero The hero
     * @return the selected action
     */
    public int fightOrUseItem(Hero hero) {
        int actionChoice;
        boolean hasItems = hero.isItemsOnBackPack();
        do {
            print.itemsInBackPack(hero);
            actionChoice = input.chooseHeroAction(hasItems);
            if (actionChoice != 1 && (!hasItems || actionChoice != 2)) {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (actionChoice != 1 && (!hasItems || actionChoice != 2));
        return actionChoice;
    }

    /**
     * Method that represents the menu to warn the user that the hero's health is full
     *
     * @return the selected option
     */
    public int handleItemMenu() {
        return input.handleItemUsage("Hero's health is full. Do you still want to use the item? (yes/no): ");
    }

    /**
     * Method that represents the menu to warn the user that the item will waste points
     *
     * @return the selected option
     */
    public int handleItemWasteMenu() {
        return input.handleItemUsage("Using this item will waste points. Do you still want to use the item? (yes/no): ");
    }

    /**
     * Method that represents the menu to create a hero
     *
     * @return the created hero
     */
    public Hero createHero() {
        int attackValue = input.getValidPositiveNumber("Enter the attack value for your hero (greater than 0): ");
        int backpackCapacity = input.getValidPositiveNumber("Enter the capacity of your hero's backpack (greater than 0): ");
        clearConsole();
        return new HeroImp(attackValue, backpackCapacity);
    }

    /**
     * Method to select a room from the list of rooms that are in and out rooms
     *
     * @param maxRooms the maximum number of rooms
     * @return the selected room
     */
    public int selectRoom(int maxRooms) {
        return input.getValidRoomChoice(maxRooms);
    }

    /**
     * Method to "clean" the console
     */
    private void clearConsole() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }
}
