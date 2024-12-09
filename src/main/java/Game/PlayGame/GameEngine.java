package Game.PlayGame;

import Collections.Lists.LinkedOrderedList;
import Collections.Lists.LinkedUnorderedList;
import Collections.Lists.OrderedListADT;
import Collections.Lists.UnorderedListADT;
import Game.Exceptions.EnemyException;
import Game.Exceptions.HeroException;
import Game.Exceptions.RoomException;
import Game.Exceptions.TargetException;
import Game.Interfaces.*;
import Game.Utilities.ManualSimulationLog;


import java.util.Iterator;

/**
 * Represents the game engine
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class GameEngine {

    /**
     * The print class
     */
    private final Print print;

    /**
     * The menu class
     */
    private final Menu menu;

    /**
     * The game rule class
     */
    private final GameRule gameRule;

    /**
     * Constructor for the game engine
     * @param print the print
     * @param menu the menu
     */
    public GameEngine( Print print, Menu menu) {
        this.print = print;
        this.menu = menu;
        this.gameRule = new GameRule(print, menu);
    }

    /**
     * Method that plays the game manually
     * @param rooms the rooms
     * @return the path
     */
    private Room findRoomWithTarget(UnorderedListADT<Room> rooms) {
        Iterator<Room> roomIterator = rooms.iterator();
        while (roomIterator.hasNext()) {
            Room room = roomIterator.next();
            if (room.isTargetInRoom()) {
                return room;
            }
        }
        return null; // Return null if no room contains the target
    }

    /**
     * Method of the game engine that plays the game manually, that will allow the user to choose the start room
     * @param map the map
     * @param hero the hero
     * @param path the path of the hero so we can add the rooms to the path and later export to the logs
     * @return the room where the hero will start
     * @throws HeroException if the hero has an error
     * @throws EnemyException if the enemy has an error
     * @throws RoomException if the room has an error
     */
    private Room selectStartRoom(Map map, Hero hero, OrderedListADT<Room> path) throws HeroException, EnemyException, RoomException {

        UnorderedListADT<Room> allRooms = map.getRooms();

        UnorderedListADT<Room> inAndOutRooms = print.inAndOutRooms(allRooms);

        if (inAndOutRooms.isEmpty()) {
            throw new RoomException("No In and Out rooms available.");
        }

        Room selectedRoom = null;
        boolean roomConfirmed = false;

        while (!roomConfirmed) {
            int roomChoice = menu.selectRoom(inAndOutRooms.size());

            for (int i = 1; i <= roomChoice; i++) {
                selectedRoom = inAndOutRooms.iterator().next();
            }

            if (selectedRoom.isThereAnEnemyAlive()) {
                roomConfirmed = menu.confirmRoomEntry(selectedRoom, hero, path);

                if (roomConfirmed) {
                    gameRule.sceneryOne(map, hero, selectedRoom, false);
                } else {
                    print.selectAnotherRoom();
                }
            } else {
                roomConfirmed = true;

                selectedRoom.addHero(hero);
                path.add(selectedRoom);

                print.heroWillStartRoom(selectedRoom);

                if (selectedRoom.hasItems()) {
                    gameRule.itemsScenario(selectedRoom, hero);
                }
            }
        }
        return selectedRoom;
    }

    /**
     * Method that plays the game manually
     *
     *
     * @param missions the missions of the game so we can later add the mission to the missions
     * @param mission the mission that we are playing
     * @throws HeroException if the hero has an error
     * @throws EnemyException if the enemy has an error
     * @throws TargetException if the target has an error
     * @throws RoomException if the room has an error
     */
    public void playManually(Missions missions, Mission mission) throws HeroException, EnemyException, TargetException, RoomException {
        Map map = mission.getMap();
        OrderedListADT<Room> path = new LinkedOrderedList<>();
        Hero hero = menu.createHero();
        Room targetRoom = findRoomWithTarget(map.getRooms());
        Room nextRoom;
        nextRoom = selectStartRoom(map, hero, path);


        while (hero.isAlive()) {

            int selectNextMove = menu.MoveOrUseItemMenu(hero);


            if (selectNextMove == 1) {
                nextRoom = MoveToNextRoom(map, hero, targetRoom);
                if (nextRoom != null) {

                    path.add(nextRoom);

                    if (gameRule.checkEndGame(hero, nextRoom)) {
                        break;
                    }

                    if (nextRoom.isThereAnEnemyAlive() && !nextRoom.isTargetInRoom()) {
                        gameRule.sceneryOne(map, hero, nextRoom, false);
                    }

                    if (!nextRoom.isThereAnEnemyAlive() && !nextRoom.isTargetInRoom()) {
                        gameRule.sceneryTwo(map, nextRoom, targetRoom, hero, false);
                    }

                    if (nextRoom.isThereAnEnemyAlive() && nextRoom.isTargetInRoom()) {
                        gameRule.sceneryFive(map, nextRoom, hero, false);
                    }

                    if (nextRoom.isTargetInRoom() && !nextRoom.isThereAnEnemyAlive()) {
                        gameRule.scenerySix(map, nextRoom, hero, false);
                    }
                }
            } else {
                gameRule.sceneryFour(map, hero , nextRoom);
            }
        }

        ManualSimulationLog manualSimulationLog = new ManualSimulationLog(hero, path);

        mission.addManualSimulationLog(manualSimulationLog);

        missions.addMission(mission);
    }

    /**
     * Method that plays the game automatically
     * @param mission the mission that we are playing
     * @throws HeroException if the hero has an error or backpack is full
     * @throws TargetException if the target has an error or is null
     * @throws EnemyException if the enemy has an error or is null
     */
    public void playAutomatically(Mission mission) throws HeroException, TargetException, EnemyException {
        Map map = mission.getMap();
        UnorderedListADT<Room> allRooms = map.getRooms();
        Room targetRoom = findRoomWithTarget(allRooms);
        UnorderedListADT<Room> entryExitRooms = findEntryRooms(allRooms);

        if (entryExitRooms.isEmpty()) {
            System.out.println("No entry rooms available.");
            return;
        }

        Hero hero = menu.createHero();
        Iterator<Room> entryRoomsIterator = entryExitRooms.iterator();
        Room bestEntryRoom = findBestRoom(map, entryRoomsIterator, targetRoom);

        if (bestEntryRoom == null) {
            System.out.println("No valid path found.");
            return;
        }

        System.out.println("Best entry point: " + bestEntryRoom.getRoomName());
        moveHeroToRoom(hero, bestEntryRoom);

        while (!hero.doesHeroHaveTarget() && hero.isAlive()) {
            currentRoomActions(map, hero, targetRoom);
        }

        while (hero.doesHeroHaveTarget() && hero.isAlive()) {
            Iterator<Room> exitRoomsIterator = entryExitRooms.iterator();
            Room bestExitRoom = findBestRoom(map, exitRoomsIterator, hero.getCurrentRoom());

            if (bestExitRoom == null) {
                System.out.println("No exit rooms available.");
                break;
            }

            boolean endGame = currentRoomActions(map, hero, bestExitRoom);
            if (endGame) {
                break;
            }
        }

    }

    /**
     * Method that represents the actions that the hero will take in the current room
     * @param map the map
     * @param hero the hero
     * @param targetRoom the target room
     * @return true if the game is won, false otherwise
     * @throws HeroException if the hero has an error or the backpack is full
     * @throws EnemyException if the enemy has an error or is null
     * @throws TargetException if the target has an error or is null
     */
    private boolean currentRoomActions(Map map, Hero hero, Room targetRoom) throws HeroException, EnemyException, TargetException {
        Iterator<Room> pathIterator = map.shortestPath(hero.getCurrentRoom(), targetRoom);

        if (pathIterator.hasNext()) {
            pathIterator.next(); // Skip the current room
            if (pathIterator.hasNext()) {
                Room nextRoom = pathIterator.next();
                moveHeroToRoom(hero, nextRoom);

                // Fase 1: Até o targetRoom
                if (!hero.doesHeroHaveTarget()) {
                    print.nextBestRoom(map, hero.getCurrentRoom(), targetRoom);
                    handleRoomEvents(map, hero, nextRoom, targetRoom, false);
                }
                // Fase 2: Do targetRoom até os exits
                else {
                    print.nextBestRoom(map, hero.getCurrentRoom(), targetRoom);
                    return handleRoomEvents(map, hero, nextRoom, targetRoom, true);
                }
            }
        }
        return false;
    }

    /**
     * Method that handles the events of the room
     * @param map the map
     * @param hero the hero
     * @param currentRoom the current room
     * @param targetRoom the target room
     * @param isExitPhase if the game is in the exit phase ( this is calculated when the target is reached and the hero is moving to the exit rooms)
     * @return true if the game is won, false otherwise
     * @throws EnemyException if the enemy has an error or is null
     * @throws TargetException if the target has an error or is null
     */
    private boolean handleRoomEvents(Map map, Hero hero, Room currentRoom, Room targetRoom, boolean isExitPhase) throws EnemyException, TargetException {
        if (currentRoom.isThereAnEnemyAlive() && !currentRoom.isTargetInRoom()) {
            gameRule.sceneryOne(map, hero, currentRoom, true);
        }

        if (!currentRoom.isThereAnEnemyAlive() && !currentRoom.isTargetInRoom()) {
            gameRule.sceneryTwo(map, currentRoom, targetRoom, hero, true);
        }

        if (currentRoom.isThereAnEnemyAlive() && currentRoom.isTargetInRoom()) {
            gameRule.sceneryFive(map, currentRoom, hero, true);
        }

        if (currentRoom.isThereAnEnemyAlive() && currentRoom.isTargetInRoom()) {
            gameRule.sceneryFive(map, currentRoom, hero, true);
        }

        if (currentRoom.isTargetInRoom() && !currentRoom.isThereAnEnemyAlive()) {
            gameRule.scenerySix(map, currentRoom, hero, true);
        }

        if (currentRoom.isInAndOutRoom() && isExitPhase && !currentRoom.isThereAnEnemyAlive()) {
            print.gameWonHeroReachedExit(hero);
            return true;
        }
        return false;
    }

    /**
     * Method that moves the hero to a room
     * @param hero the hero
     * @param room the room
     * @throws HeroException if the hero has an error ( null )
     */
    private void moveHeroToRoom(Hero hero, Room room) throws HeroException {
        if (hero.getCurrentRoom() != null) {
            hero.getCurrentRoom().removeHero();
        }
        room.addHero(hero);

        print.heroMovedToRoom(room);
    }

    /**
     * Method that finds the entry rooms
     * @param allRooms all the rooms
     * @return the entry rooms
     */
    private UnorderedListADT<Room> findEntryRooms(UnorderedListADT<Room> allRooms) {
        UnorderedListADT<Room> entryRooms = new LinkedUnorderedList<>();
        Iterator<Room> iterator = allRooms.iterator();
        while (iterator.hasNext()) {
            Room room = iterator.next();
            if (room.isInAndOutRoom()) {
                entryRooms.addToRear(room);
            }
        }
        return entryRooms;
    }

    /**
     * Method that finds the best room based in the shortestpath weight
     * So this method is always used everytime the hero moves from a room to another because the weight is always changing
     * because the enemies are moving in the map , so the first path from the room he came from will or will not be the best
     *
     * Example: The hero already has the best path to the best exit with the lowest weight, but the enemies moved and now the weight is higher
     * so he needs to find the best path again ( can be the same exit or another exit)
     *
     * @param map the map
     * @param roomsIterator the rooms iterator
     * @param target the target room
     * @return the best room
     */
    private Room findBestRoom(Map map, Iterator<Room> roomsIterator, Room target) {
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

    /**
     * So this method is used in manual mode, everytime the hero moves from a room to another, the hero will choose the next room
     * @param map the map
     * @param hero the hero
     * @param targetRoom the target room
     * @return the next room
     * @throws HeroException if the hero has an error or is null
     */
    private Room MoveToNextRoom(Map map, Hero hero, Room targetRoom) throws HeroException {
        Room currentRoom = hero.getCurrentRoom();
        hero.setCurrentRoom(null);
        currentRoom.removeHero();


        UnorderedListADT<Room> connectedRooms = getConnectedRooms(map, currentRoom);

        if (connectedRooms.isEmpty()) {
            System.out.println("No connected rooms available.");
            return null;
        }

        if (!hero.doesHeroHaveTarget()) {
            print.nextBestRoom(map, currentRoom, targetRoom);
        }

        print.nextRoomsAndInfos(connectedRooms);

        Room selectedRoom = selectRoom(connectedRooms, menu.chooseNextRoom(connectedRooms));

        selectedRoom.addHero(hero);

        print.heroMovedToRoom(selectedRoom);

        return selectedRoom;

    }

    /**
     * So this method will choose the selectRoom depending on the gamer input number of the menu
     * It was listed to in the rooms that are connected to the current room and then the gamer will choose the room he wants to go
     *
     * @param connectedRooms the connected rooms
     * @param roomChoice the room choice
     * @return the selected room
     */
    private Room selectRoom(UnorderedListADT<Room> connectedRooms, int roomChoice) {
        Iterator<Room> connectedRoomIterator = connectedRooms.iterator();
        Room selectedRoom = null;
        for (int i = 1; i <= roomChoice; i++) {
            selectedRoom = connectedRoomIterator.next();
        }
        return selectedRoom;
    }

    /**
     * So this method will get the connected rooms of the current room
     * @param map the map
     * @param currentRoom the current room
     * @return the connected rooms
     */
    private UnorderedListADT<Room> getConnectedRooms(Map map, Room currentRoom) {

        Iterator<Room> iterator = map.getRooms().iterator();
        UnorderedListADT<Room> connectedRooms = new LinkedUnorderedList<>();

        while (iterator.hasNext()) {
            Room room = iterator.next();
            if (map.areConnected(currentRoom, room)) {
                connectedRooms.addToRear(room);
            }
        }
        return connectedRooms;
    }


}
