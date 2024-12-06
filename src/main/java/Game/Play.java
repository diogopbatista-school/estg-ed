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
    public static void main(String[] args){
        Input input = new Input();
        Print print = new Print();
        Menu menu = new Menu(input,print);
        Importer importer = new Importer();
        UnorderedListADT<String> missionCodes = new LinkedUnorderedList<>();

        try {
            missionCodes = importer.loadMissions("Missoes.json");
        } catch (Exception e) {
            System.out.println("Error loading missions: " + e.getMessage());
            System.exit(1);
        }

        menu.mainMenu(missionCodes,importer);
    }

    /**
    private static void displayMenu(Scanner scanner, Importer importer, UnorderedListADT<String> missionCodes) {
        System.out.println("!!!!!MISSION IMPOSSIBLE!!!!");

        while (true) {
            menu.MainMenu();

            int choice = getValidNumberInput(scanner, 1, 2);

            switch (choice) {
                case 1:
                    Missions missions = new MissionsImp();
                    startGame(missions,scanner, importer, missionCodes);
                    try {
                        Exporter exporter = new Exporter(missions);
                        exporter.save();
                    } catch (IOException e) {
                        System.out.println("Error saving logs: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Exiting. Goodbye!");
                    System.exit(0);
            }
        }
    }

    private static void startGame(Missions missions, Scanner scanner, Importer importer, UnorderedListADT<String> missionCodes) {

        boolean playAgain = true;

        while (playAgain) {
            menu.startGameMenu(missionCodes);

            String selectedMission = menu.chooseMapMenu(missionCodes);

            Mission mission;

            try {
                mission = importer.importData(selectedMission);
                menu.printLoadedMissionMenu(mission, selectedMission);

                int playModeChoice = getValidNumberInput(scanner, 1, 2);

                if (playModeChoice == 1) {
                    try {
                        gameEngine.playManually(missions,mission, scanner);
                    } catch (HeroException | ItemException | TargetException | EnemyException e) {
                        System.out.println("Error playing the game: " + e.getMessage());
                    }
                } else if (playModeChoice == 2) {
                     try {
                     playAutomatically(mission,scanner);
                     } catch (HeroException | TargetException | EnemyException e) {
                     System.out.println("Error playing the game: " + e.getMessage());
                     }

                }
            } catch (Exception e) {
                System.out.println("Error loading mission: " + e.getMessage());
            }

            System.out.println("Do you want to play the game again?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            int playAgainChoice = getValidNumberInput(scanner, 1, 2);
            playAgain = (playAgainChoice == 1);

            System.out.println("Exiting. Goodbye!");
        }
    }





    private static void playAutomatically(Mission mission, Scanner scanner) throws HeroException, EnemyException, TargetException {
        Map map = mission.getMap();
        UnorderedListADT<Room> allRooms = map.getRooms();
        Room targetRoom = findRoomWithTarget(allRooms);
        UnorderedListADT<Room> entryExitRooms = findEntryRooms(allRooms);


        if (entryExitRooms.isEmpty()) {
            System.out.println("No entry rooms available.");
            return;
        }

        Hero hero = createHero(scanner);
        Iterator<Room> entryRoomsIterator = entryExitRooms.iterator();
        Room bestEntryRoom = findBestRoom(map, entryRoomsIterator, targetRoom);

        if (bestEntryRoom == null) {
            System.out.println("No valid path found.");
            return;
        }

        System.out.println("Best entry point: " + bestEntryRoom.getRoomName());
        moveHeroToRoom(hero, bestEntryRoom);

        while (!hero.doesHeroHaveTarget() && hero.isAlive()) {
            currentRoomActions(map, hero, targetRoom, scanner);
        }



        while (hero.doesHeroHaveTarget() && hero.isAlive()) {
            Iterator<Room> exitRoomsIterator = entryExitRooms.iterator();
            Room bestExitRoom = findBestRoom(map, exitRoomsIterator, hero.getCurrentRoom());

            if (bestExitRoom == null) {
                System.out.println("No exit rooms available.");
                break;
            }

            boolean endGame = currentRoomActions(map, hero, bestExitRoom, scanner);
            if(endGame){
                break;
            }
        }
    }

    private static UnorderedListADT<Room> findEntryRooms(UnorderedListADT<Room> allRooms) {
        UnorderedListADT<Room> entryRooms = new LinkedUnorderedList<>();
        Iterator<Room> iterator = allRooms.iterator();
        while (iterator.hasNext()) {
            Room room = iterator.next();
            if (room.isIsAndOut()) {
                entryRooms.addToRear(room);
            }
        }
        return entryRooms;
    }

    private static Room findBestRoom(Map map, Iterator<Room> roomsIterator, Room target) {
        Room bestRoom = null;
        double minWeight = Double.MAX_VALUE;

        while (roomsIterator.hasNext()) {
            Room room = roomsIterator.next();
            double weight = map.shortestPathWeight(room, target);
            if (weight < minWeight) {
                minWeight = weight;
                bestRoom = room;
            }
        }
        return bestRoom;
    }

    private static void moveHeroToRoom(Hero hero, Room room) throws HeroException {
        if (hero.getCurrentRoom() != null) {
            hero.getCurrentRoom().removeHero();
        }
        room.addHero(hero);
        hero.setCurrentRoom(room);
        System.out.println("Hero moved to: " + room.getRoomName());
    }

    private static boolean currentRoomActions(Map map, Hero hero, Room targetRoom, Scanner scanner) throws HeroException, EnemyException, TargetException {
        Iterator<Room> pathIterator = map.shortestPath(hero.getCurrentRoom(), targetRoom);

        if (pathIterator.hasNext()) {
            pathIterator.next(); // Skip the current room
            if (pathIterator.hasNext()) {
                Room nextRoom = pathIterator.next();
                moveHeroToRoom(hero, nextRoom);

                // Fase 1: Até o targetRoom
                if (!hero.doesHeroHaveTarget()) {
                    handleRoomEvents(map, hero, nextRoom, targetRoom, scanner, false);
                }
                // Fase 2: Do targetRoom até os exits
                else {
                    boolean endGame = handleRoomEvents(map, hero, nextRoom, targetRoom, scanner, true);
                    return endGame;
                }
            }
        }
        return false;
    }


    private static boolean handleRoomEvents(Map map, Hero hero, Room currentRoom, Room targetRoom, Scanner scanner, boolean isExitPhase) throws HeroException, EnemyException, TargetException {
        if (currentRoom.isThereAnEnemyAlive() && !currentRoom.isTargetInRoom()) {
            ScenaryOne(map, currentRoom, hero, scanner, true);
        }

        if (!currentRoom.isThereAnEnemyAlive() && !currentRoom.isTargetInRoom()) {
            ScenaryTwo(map, currentRoom, targetRoom, hero, scanner, true);
            if (hero.isItemsOnBackPack() && hero.getHealth() < 50) {
                hero.useItem();
                System.out.println("Hero used an item. Health: " + hero.getHealth() + ", Armor: " + hero.getArmorHealth());
                System.out.println("------------------------------------------------ ENEMY TURN");
                map.mapShuffle();
                if (currentRoom.isThereAnEnemyAlive()) {
                    ScenaryOne(map, currentRoom, hero, scanner, true);
                }
            }
        }

        if (currentRoom.isThereAnEnemyAlive() && currentRoom.isTargetInRoom()) {
            ScenaryFive(map, currentRoom, hero, scanner, true);
        }

        if (currentRoom.isTargetInRoom() && !currentRoom.isThereAnEnemyAlive() && !isExitPhase) {
            ScenarySix(map ,hero, currentRoom,scanner);
        }

        if (currentRoom.isIsAndOut() && isExitPhase && !currentRoom.isThereAnEnemyAlive()) {
            System.out.println("Hero safely reached the exit room!");
            return true;
        }
        return false;
    }


    private static Room findRoomWithTarget(UnorderedListADT<Room> rooms) {
        Iterator<Room> roomIterator = rooms.iterator();
        while (roomIterator.hasNext()) {
            Room room = roomIterator.next();
            if (room.isTargetInRoom()) {
                return room;
            }
        }
        return null; // Return null if no room contains the target
    }

     */

}