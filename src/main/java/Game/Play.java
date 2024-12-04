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
import Game.Navigation.MapImp;
import Game.Navigation.MissionsImp;
import Game.Utilities.Logs;
import Game.Utilities.ManualSimulationLog;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Iterator;

public class Play {
    public static void main(String[] args) throws HeroException, ItemException, TargetException, EnemyException {
        Scanner scanner = new Scanner(System.in);
        Importer importer = new Importer();
        UnorderedListADT<String> missionCodes = new LinkedUnorderedList<>();

        try {
            missionCodes = importer.loadMissions("Missoes.json");
        } catch (Exception e) {
            System.out.println("Error loading missions: " + e.getMessage());
            System.exit(1);
        }

        displayMenu(scanner, importer, missionCodes);
    }

    private static void displayMenu(Scanner scanner, Importer importer, UnorderedListADT<String> missionCodes) {
        System.out.println("!!!!!MISSION IMPOSSIBLE!!!!");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Start Game");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

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
            System.out.println("\nCreate Game:");

            System.out.println("\nAvailable Missions:");
            Iterator<String> iterator = missionCodes.iterator();
            int index = 1;
            while (iterator.hasNext()) {
                System.out.println(index + ". " + iterator.next());
                index++;
            }
            System.out.print("Choose a mission: ");

            int missionChoice = -1;
            while (missionChoice < 1 || missionChoice > missionCodes.size()) {
                try {
                    missionChoice = scanner.nextInt();
                    if (missionChoice < 1 || missionChoice > missionCodes.size()) {
                        System.out.println("Invalid choice. Please try again.");
                        scanner.next();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();
                }
            }

            iterator = missionCodes.iterator();
            String selectedMission = null;
            for (int i = 0; i < missionChoice; i++) {
                selectedMission = iterator.next();
            }

            Mission mission;

            try {
                mission = importer.importData(selectedMission);
                clearConsole();
                System.out.println("Mission loaded successfully! Mission: " + selectedMission);
                System.out.println(mission.getMap().toString());

                System.out.println("Choose play mode:");
                System.out.println("1. Manual");
                System.out.println("2. Automatic");
                int playModeChoice = getValidNumberInput(scanner, 1, 2);

                if (playModeChoice == 1) {
                    try {
                        playManually(missions,mission, scanner);
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

    private static int getValidNumberInput(Scanner scanner, int min, int max) {
        int choice = -1;
        while (choice < min || choice > max) {
            try {
                System.out.print("Choose a number between " + min + " and " + max + ": ");
                choice = scanner.nextInt();
                if (choice < min || choice > max) {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
        return choice;
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

    // Novo método para gerenciar eventos na sala
    private static boolean handleRoomEvents(Map map, Hero hero, Room currentRoom, Room targetRoom, Scanner scanner, boolean isExitPhase) throws HeroException, EnemyException, TargetException {
        if (currentRoom.isThereAnEnemyAlive() && !currentRoom.isTargetInRoom()) {
            ScenaryOne(map, currentRoom, hero, scanner, true);
        }

        if (!currentRoom.isThereAnEnemyAlive() && !currentRoom.isTargetInRoom()) {
            ScenaryTwo(map, currentRoom, targetRoom, hero, scanner, true);
            if (hero.isItemsOnBackPack() && hero.getHealth() < 50) {
                hero.UseItem();
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

    private static void playManually(Missions missions,Mission mission ,Scanner scanner) throws HeroException, ItemException, TargetException, EnemyException, IOException {
        Map mapOfGame = mission.getMap();

        UnorderedListADT<Room> allRooms = mapOfGame.getRooms();
        OrderedListADT<Room> path = new LinkedOrderedList<>();
        Hero hero = createHero(scanner);
        Room targetRoom = findRoomWithTarget(allRooms);
        selectStartRoom(mapOfGame,hero, scanner, allRooms,path);

        while (hero.isAlive()) {
            int selectNextMove;

            if (hero.isItemsOnBackPack()) {
                System.out.println("1. Move to another room");
                System.out.println("2. Use Item");
            } else {
                System.out.println("1. Move to another room");
            }

            selectNextMove = scanner.nextInt();

            while (selectNextMove == 2 && !hero.isItemsOnBackPack()) {
                System.out.println("Invalid choice. You don't have any items to use.");
                System.out.println("1. Move to another room");
                selectNextMove = scanner.nextInt();
            }

            if(selectNextMove == 1) {
                Room movedRoom = listConnectedRooms(mapOfGame, scanner, hero, allRooms, targetRoom);

                if (movedRoom != null) {
                    path.add(movedRoom);
                }

                if (checkEndGame(hero, movedRoom)) {
                    break;
                }

                if (movedRoom.isThereAnEnemyAlive() && !movedRoom.isTargetInRoom()) {
                    ScenaryOne(mapOfGame, movedRoom, hero, scanner, false);
                }

                if (!movedRoom.isThereAnEnemyAlive() && !movedRoom.isTargetInRoom()) {
                    ScenaryTwo(mapOfGame, movedRoom, targetRoom, hero, scanner, false);
                }


                if (movedRoom.isThereAnEnemyAlive() && movedRoom.isTargetInRoom()) {
                    ScenaryFive(mapOfGame, movedRoom, hero, scanner,false);
                }

                if (movedRoom.isTargetInRoom() && !movedRoom.isThereAnEnemyAlive()) {
                    ScenarySix(mapOfGame,hero, movedRoom,scanner);
                }
            }else {
                hero.UseItem();
                System.out.println("Hero used an item. Hero health: " + hero.getHealth() + ", Hero armor: " + hero.getArmorHealth());
                System.out.println("------------------------------------------------ ENEMY TURN");
                System.out.println("------------------------------------------------ ENEMY MOVED");
                mapOfGame.mapShuffle();
                System.out.println("------------------------------------------------");
                if (hero.getCurrentRoom().isThereAnEnemyAlive()) {
                    ScenaryOne(mapOfGame, hero.getCurrentRoom(), hero, scanner, false);
                }
            }
        }


        ManualSimulationLog manualSimulationLog = new ManualSimulationLog(hero, path);

        mission.addManualSimulationLog(manualSimulationLog);

        missions.addMission(mission);

    }

    private static boolean checkEndGame(Hero hero, Room movedRoom) {
        if (movedRoom.isIsAndOut()) {
            if (hero.doesHeroHaveTarget()) {
                System.out.println("Success! Hero has reached the exit with the target.");
            } else {
                System.out.println("Game Over! Hero reached the exit without the target.");
            }
            return true;
        }
        return false;
    }

    private static void ScenaryOne(Map map, Room movedRoom, Hero hero,Scanner scanner, boolean isAutomatic) throws EnemyException {
        UnorderedListADT<Enemy> enemies = movedRoom.getEnemies();
        while (hero.isAlive() && movedRoom.isThereAnEnemyAlive()) {

            fight(enemies,hero,movedRoom,scanner,isAutomatic);

            if(movedRoom.isThereAnEnemyAlive()) {
                System.out.println("--------------------------------------------------------------- MOVED ENEMIES");
                map.mapShuffle();
                System.out.println("------------------------------------------------");
            }else{
                if(movedRoom.hasItems()) {
                    System.out.println("Fight over . Time to collect items.");
                    itemsScenario(movedRoom, hero);
                }
                System.out.println("Fight over. No items to collect and target is not in this room.");
            }
        }
    }

    private static void ScenaryTwo(Map map , Room movedRoom, Room targetRoom, Hero hero, Scanner scanner, boolean isAutomatic) throws TargetException, EnemyException {

        System.out.println("No enemies in the room.");
        System.out.println("------------------------------------------------");
        System.out.println("Enemy's started to move to another room.");
        map.mapShuffle();
        System.out.println("------------------------------------------------");
        if (!movedRoom.isThereAnEnemyAlive()) {
            System.out.println("No enemies have entered in your room.");
            System.out.println("Hero's turn:");
            if (movedRoom.hasItems()) {
                itemsScenario(movedRoom, hero);
            }
            if (movedRoom.equals(targetRoom)) {
                ScenarySix(map,hero, movedRoom,scanner);
            }
        } else {
                ScenaryOne(map ,movedRoom, hero, scanner,isAutomatic);
        }
    }

    private static void ScenaryFive(Map map, Room movedRoom, Hero hero, Scanner scanner,boolean isAutomatic) throws EnemyException, TargetException {
        System.out.println("Tó Cruz encontrou o alvo, mas há inimigos na sala!");

        ScenaryOne(map, movedRoom, hero, scanner, isAutomatic);

        // Fim do turno: O próximo turno começa com Tó Cruz ainda na sala com o alvo, após eliminar os inimigos
        if (hero.isAlive() && !movedRoom.isThereAnEnemyAlive()) {
            System.out.println("Todos os inimigos na sala foram eliminados. Tó Cruz pode agora resgatar o alvo.");
            ScenarySix(map,hero, movedRoom,scanner);
        }
    }

    private static void ScenarySix(Map map,Hero hero, Room movedRoom, Scanner scanner) throws TargetException, EnemyException {
        if(movedRoom.getTarget() == null){
            return;
        }
        map.mapShuffle();
        if(movedRoom.isThereAnEnemyAlive()){
            ScenaryOne(map, movedRoom, hero, scanner,false);
        }

        hero.setTarget(movedRoom.getTarget());
        movedRoom.removeTarget(movedRoom.getTarget());
        System.out.println("Hero has reached the target."
                + "Your online device has been compromised. You need to find your way out alone!\n" +
                "Available services:\n " +
                "1.Next room's information ");
    }

    private static void itemsScenario(Room movedRoom, Hero hero){
        System.out.println("--------------------------------------");
        System.out.println("Items in the room:");
        Iterator<Item> itemIterator = movedRoom.getItems();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            System.out.println(item.getNameItem());
        }
        try{
        movedRoom.removeItem(hero);
        }catch (ItemException e){
            System.out.println("Item left in the room because: " + e.getMessage());
        }
    }

    private static void fight(UnorderedListADT<Enemy> enemies,Hero hero, Room movedRoom,Scanner scanner, boolean isAutomatic) {
        if (enemies == null ||  !movedRoom.isThereAnEnemyAlive()) {
            System.out.println("No Enemies in the room to fight.");
            return;
        }
            if(!isAutomatic) {
                heroTurnManually(hero, movedRoom, scanner);
            }else{
                heroTurnAutomatically(hero, movedRoom);
            }

            if (movedRoom.isThereAnEnemyAlive()) {
                enemyTurnAttack(movedRoom,hero); // inimigos atacam se ainda estiverem vivos
                if (hero.getHealth() <= 0) {
                    System.out.println("Hero has died.");
                }
            }else{
                System.out.println("Hero has killed all enemies");
            }
    }

    private static void heroTurnManually(Hero hero, Room movedRoom, Scanner scanner) {
    System.out.println("------------------------------------------------ HERO TURN");
    System.out.println("Hero's turn:");
    int actionChoice;
    boolean hasItems = hero.isItemsOnBackPack();
    Iterator<Enemy> enemyIterator; // Check if the hero has items in the backpack

    do {
        if (hasItems) {
            System.out.print("Choose an action (1: Attack, 2: Use Item): ");
        } else {
            System.out.print("Choose an action (1: Attack): ");
        }
        actionChoice = scanner.nextInt();
        if (actionChoice != 1 && (!hasItems || actionChoice != 2)) {
            System.out.println("Invalid choice. Please try again.");
        }
    } while (actionChoice != 1 && (!hasItems || actionChoice != 2));

    while (actionChoice == 2) {
        System.out.println("Hero's turn to use an item:");
        int heroHealth = hero.getHealth();
        int maxHealth = 100; // Max health is always 100
        Item item = hero.getItemFirstItem();
        int itemPoints = item.getPoints();

        if (heroHealth == maxHealth) {
            System.out.println("Hero's health is full. Do you still want to use the item? (yes/no): ");
            String choice = scanner.next().toLowerCase();
            if (!choice.equals("yes")) {
                System.out.print("Choose an action (1: Attack, 2: Use Item): ");
                actionChoice = scanner.nextInt();
                if (actionChoice == 1){
                    break;
                }
                continue;
            }
        } else if (heroHealth + itemPoints > maxHealth) {
            System.out.println("Using this item will waste points. Do you still want to use the item? (yes/no): ");
            String choice = scanner.next().toLowerCase();
            if (!choice.equals("yes")) {
                System.out.print("Choose an action (1: Attack, 2: Use Item): ");
                actionChoice = scanner.nextInt();
                if (actionChoice == 1){
                    break;
                }
                continue;
            }
        }

        hero.UseItem();
        break;
    }

    if (actionChoice == 1) {
        System.out.println("---------------------------------------------------- HERO TURN");
        System.out.println("Hero's turn to attack:");
        enemyIterator = movedRoom.getEnemies().iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            if (enemy.isAlive()) {
                hero.attack(enemy);
                if (!enemy.isAlive()) {
                    System.out.println("Enemy " + enemy.getName() + " is dead.");
                } else {
                    System.out.println("Hero attacked " + enemy.getName() + ". Enemy health: " + enemy.getHealth());
                }
            }
        }
    }
}

    private static void heroTurnAutomatically(Hero hero, Room movedRoom) {
        System.out.println("------------------------------------------------ HERO TURN");
        System.out.println("Hero's turn:");

        if (hero.getHealth() < 50 && hero.isItemsOnBackPack()) {
            hero.UseItem();
            System.out.println("Hero used an item. Hero health: " + hero.getHealth() + ", Hero armor: " + hero.getArmorHealth());
        } else {
            Iterator<Enemy> enemyIterator = movedRoom.getEnemies().iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                if (enemy.isAlive()) {
                    hero.attack(enemy);
                    if (!enemy.isAlive()) {
                        System.out.println("Enemy " + enemy.getName() + " is dead.");
                    } else {
                        System.out.println("Hero attacked " + enemy.getName() + ". Enemy health: " + enemy.getHealth());
                    }
                }
            }
        }
    }

    private static void enemyTurnAttack(Room movedRoom, Hero hero) {
        System.out.println("------------------------------------------------ ENEMY TURN");
        System.out.println("Enemies' turn to attack:");
        Iterator<Enemy> enemyIterator = movedRoom.getEnemies().iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            if (enemy.isAlive()) {
                enemy.attack(hero);
                System.out.println(enemy.getName() + " attacked Hero. Hero health: " + hero.getHealth() + ", Hero armor: " + hero.getArmorHealth());
            }
        }
    }

    private static Hero createHero(Scanner scanner) {
        int attackValue = -1;
        while (attackValue <= 0) {
            try {
                System.out.print("Enter the attack value for your hero (greater than 0): ");
                attackValue = scanner.nextInt();
                if (attackValue <= 0) {
                    System.out.println("Attack value must be greater than 0. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }

        int backpackCapacity = -1;
        while (backpackCapacity <= 0) {
            try {
                System.out.print("Enter the capacity of your hero's backpack (greater than 0): ");
                backpackCapacity = scanner.nextInt();
                if (backpackCapacity <= 0) {
                    System.out.println("Backpack capacity must be greater than 0. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
        clearConsole();

        return new HeroImp(attackValue, backpackCapacity);
    }

    private static void selectStartRoom(Map map, Hero hero, Scanner scanner, UnorderedListADT<Room> rooms,OrderedListADT<Room> path) throws HeroException, EnemyException {
        UnorderedListADT<Room> inAndOutRooms = new LinkedUnorderedList<>(); // Linked porque eu nunca sei quantos elementos eu vou ter
        System.out.println("In and Out Rooms:");

        Iterator<Room> roomIterator = rooms.iterator();
        while (roomIterator.hasNext()) {
            Room room = roomIterator.next();
            if (room.getIsInAndOut()) {
                inAndOutRooms.addToRear(room);
                System.out.println(inAndOutRooms.size() + ". " + room.getRoomName());
            }
        }

        if (inAndOutRooms.isEmpty()) {
            System.out.println("No In and Out rooms available.");
            return;
        }

        Room selectedRoom = null;
        boolean roomConfirmed = false;

        while (!roomConfirmed) {
            System.out.print("Select a room by number: ");
            int roomChoice = scanner.nextInt();
            while (roomChoice < 1 || roomChoice > inAndOutRooms.size()) {
                System.out.println("Invalid choice. Please try again.");
                System.out.print("Select a room by number: ");
                roomChoice = scanner.nextInt();
            }

            Iterator<Room> inAndOutRoomIterator = inAndOutRooms.iterator();
            for (int i = 1; i <= roomChoice; i++) {
                selectedRoom = inAndOutRoomIterator.next();
            }

            if (selectedRoom.isThereAnEnemyAlive()) {
                System.out.println("The selected room contains enemies. Do you want to enter? (yes/no): ");
                String enterChoice = scanner.next().toLowerCase();
                if (enterChoice.equals("yes")) {
                    roomConfirmed = true;
                    selectedRoom.addHero(hero);
                    path.add(selectedRoom);
                    clearConsole();
                    System.out.println("Hero will start the game at the room: " + selectedRoom.getRoomName());

                    System.out.println("!!!!!ENEMIES IN THE ROOM!!!!!:\n");
                        ScenaryOne(map, selectedRoom, hero, scanner,false);
                } else {
                    System.out.println("Select another room.");
                }
            } else {
                roomConfirmed = true;
                selectedRoom.addHero(hero);
                path.add(selectedRoom);
                clearConsole();
                System.out.println("Hero will start the game at the room: " + selectedRoom.getRoomName());
                if(selectedRoom.hasItems()) {
                    itemsScenario(selectedRoom, hero);
                }
            }
        }
}

    private static Room listConnectedRooms(Map map, Scanner scanner, Hero hero, UnorderedListADT<Room> allRooms, Room targetRoom) throws HeroException {
        Room currentRoom = hero.getCurrentRoom();
        hero.setCurrentRoom(null);
        currentRoom.removeHero();

        Iterator<Room> iterator = allRooms.iterator();
        UnorderedListADT<Room> connectedRooms = new ArrayUnorderedList<>();

        while (iterator.hasNext()) {
            Room room = iterator.next();
            if (map.areConnected(currentRoom, room)) {
                connectedRooms.addToRear(room);
            }
        }

        if (connectedRooms.isEmpty()) {
            System.out.println("No connected rooms available.");
            return null;
        }

        if (!hero.doesHeroHaveTarget()) {
            int count = 0;
            Iterator<Room> pathIterator = map.shortestPath(currentRoom, targetRoom);
            System.out.println("----------------------------------------------------------------");
            System.out.println("Shortest path from " + currentRoom.getRoomName() + " to " + targetRoom.getRoomName() + ":");
            while (pathIterator.hasNext() && count < 2) {
                Room room = pathIterator.next();
                System.out.println(room.getRoomName());
                count++;
            }
        }

        System.out.println("----------------------------------------------------------------");
        System.out.println("Connected Rooms:");
        Iterator<Room> connectedRoomIterator = connectedRooms.iterator();
        int index = 1;
        while (connectedRoomIterator.hasNext()) {
            Room room = connectedRoomIterator.next();
            System.out.println(index + ". " + room.getRoomName());

            // Display information about enemies and items in the connected room
            System.out.println("Information about the room:");
            if (room.isIsAndOut()) {
                System.out.println("!!!!!THIS IS AN EXIT ROOM!!!!!");
            }
            if (room.isThereAnEnemyAlive()) {
                System.out.println("Enemies in the room:");
                Iterator<Enemy> enemyIterator = room.getEnemies().iterator();
                while (enemyIterator.hasNext()) {
                    Enemy enemy = enemyIterator.next();
                    System.out.println("- " + enemy.getName() + " (Health: " + enemy.getHealth() + ")");
                }
            } else {
                System.out.println("No enemies in the room.");
            }

            if (room.hasItems()) {
                System.out.println("Items in the room:");
                Iterator<Item> itemIterator = room.getItems();
                while (itemIterator.hasNext()) {
                    Item item = itemIterator.next();
                    System.out.println("- " + item.getNameItem() + " (Type: " + item.getType().toString() + ")");
                }
            } else {
                System.out.println("No items in the room.");
            }

            System.out.println("------------------------------------------------");
            index++;
        }

        System.out.print("Select a connected room by number: ");
        int roomChoice = scanner.nextInt();
        while (roomChoice < 1 || roomChoice > connectedRooms.size()) {
            System.out.println("Invalid choice. Please try again.");
            System.out.print("Select a connected room by number: ");
            roomChoice = scanner.nextInt();
        }

        connectedRoomIterator = connectedRooms.iterator();
        Room selectedRoom = null;
        for (int i = 1; i <= roomChoice; i++) {
            selectedRoom = connectedRoomIterator.next();
        }

        selectedRoom.addHero(hero);
        clearConsole();
        System.out.println("Hero moved to the room: " + selectedRoom.getRoomName() + "\n\n");
        return selectedRoom;
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

    private static void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}