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

public class GameEngine {

    private final Print print;
    private final Menu menu;
    private final GameRule gameRule;

    public GameEngine(Input input,Print print ,Menu menu){
        this.print = print;
        this.menu = menu;
        this.gameRule = new GameRule(input,print,menu);
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
                roomConfirmed = menu.confirmRoomEntry(selectedRoom,hero,path);

                if(roomConfirmed){
                    gameRule.sceneryOne(map,hero,selectedRoom,false);
                }else{
                    print.selectAnotherRoom();
                }
            }else{
                roomConfirmed = true;

                selectedRoom.addHero(hero);
                path.add(selectedRoom);

                print.heroWillStartRoom(selectedRoom);

                if(selectedRoom.hasItems()) {
                    gameRule.itemsScenario(selectedRoom, hero);
                }
            }
        }
        return selectedRoom;
    }

    public void playManually(Missions missions, Mission mission) throws HeroException, EnemyException, TargetException, RoomException {
        Map map = mission.getMap();
        OrderedListADT<Room> path = new LinkedOrderedList<>();
        Hero hero = menu.createHero();
        Room targetRoom = findRoomWithTarget(map.getRooms());
        Room nextRoom ;
        nextRoom = selectStartRoom(map,hero,path);


        while (hero.isAlive()) {

            int selectNextMove = menu.MoveOrUseItemMenu(hero);


            if(selectNextMove == 1) {
                nextRoom = MoveToNextRoom(map, hero, targetRoom);
                    if(nextRoom != null) {

                        path.add(nextRoom);

                        if (gameRule.checkEndGame(hero, nextRoom)) {
                            break;
                        }

                        if (nextRoom.isThereAnEnemyAlive() && !nextRoom.isTargetInRoom()) {
                            gameRule.sceneryOne(map, hero , nextRoom, false);
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
            }else{
                hero.useItem();
                //print.heroUsedItem();
                print.enemyTurn();
                print.movedEnemies();
                map.mapShuffle();
                System.out.println("------------------------------------------------");
                if (hero.getCurrentRoom().isThereAnEnemyAlive()) {
                    gameRule.sceneryOne(map, hero , nextRoom, false);
                }
            }
        }

        ManualSimulationLog manualSimulationLog = new ManualSimulationLog(hero, path);

        mission.addManualSimulationLog(manualSimulationLog);

        missions.addMission(mission);
    }

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
            if(endGame){
                break;
            }
        }

    }

    private  boolean currentRoomActions(Map map, Hero hero, Room targetRoom) throws HeroException, EnemyException, TargetException {
        Iterator<Room> pathIterator = map.shortestPath(hero.getCurrentRoom(), targetRoom);

        if (pathIterator.hasNext()) {
            pathIterator.next(); // Skip the current room
            if (pathIterator.hasNext()) {
                Room nextRoom = pathIterator.next();
                moveHeroToRoom(hero, nextRoom);

                // Fase 1: Até o targetRoom
                if (!hero.doesHeroHaveTarget()) {
                    print.nextBestRoom(map,hero.getCurrentRoom(),targetRoom);
                    handleRoomEvents(map, hero, nextRoom, targetRoom, false);
                }
                // Fase 2: Do targetRoom até os exits
                else {
                    print.nextBestRoom(map,hero.getCurrentRoom(),targetRoom);
                    return handleRoomEvents(map, hero, nextRoom, targetRoom, true);
                }
            }
        }
        return false;
    }

    private  boolean handleRoomEvents(Map map, Hero hero, Room currentRoom, Room targetRoom, boolean isExitPhase) throws HeroException, EnemyException, TargetException {
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

        if (currentRoom.isIsAndOut() && isExitPhase && !currentRoom.isThereAnEnemyAlive()) {
            print.gameWonHeroReachedExit(hero);
            return true;
        }
        return false;
    }

    private void moveHeroToRoom(Hero hero, Room room) throws HeroException {
        if (hero.getCurrentRoom() != null) {
            hero.getCurrentRoom().removeHero();
        }
        room.addHero(hero);
        hero.setCurrentRoom(room);

        print.heroMovedToRoom(room);
    }

    private  UnorderedListADT<Room> findEntryRooms(UnorderedListADT<Room> allRooms) {
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

    private Room MoveToNextRoom(Map map ,Hero hero , Room targetRoom) throws HeroException {
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

    private Room selectRoom(UnorderedListADT<Room> connectedRooms, int roomChoice){
        Iterator<Room> connectedRoomIterator = connectedRooms.iterator();
        Room selectedRoom = null;
        for (int i = 1; i <= roomChoice; i++) {
            selectedRoom = connectedRoomIterator.next();
        }
        return selectedRoom;
    }

    private UnorderedListADT<Room> getConnectedRooms(Map map, Room currentRoom){

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
