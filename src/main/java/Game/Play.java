package Game;

import Collections.Lists.ArrayUnorderedList;
import Collections.Lists.UnorderedListADT;
import Game.Entities.HeroImp;
import Game.Enumerations.ItemType;
import Game.Exceptions.HeroException;
import Game.Exceptions.ItemException;
import Game.Exceptions.TargetException;
import Game.IO.Importer;
import Game.Interfaces.*;
import Game.Navigation.MapImp;

import java.io.IOException;
import java.util.Scanner;
import java.util.Iterator;

public class Play {
    public static void main(String[] args) throws HeroException, ItemException, TargetException {
        Scanner scanner = new Scanner(System.in);


        System.out.println("!!!!!MISSION IMPOSSIBLE!!!!");

        // Menu options
        System.out.println("\nMenu:");
        System.out.println("1.Start Game");
        System.out.println("2. Exit");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                startGame(scanner);
                break;
            case 2:
                System.out.println("Exiting. Goodbye!");
                System.exit(0);
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    public static void startGame(Scanner scanner) throws HeroException, ItemException, TargetException {
        System.out.println("\nCreate Game:");

        Map map = new MapImp();
        UnorderedListADT<Room> allRooms = new ArrayUnorderedList<>();

        System.out.print("Do you want to import a map from a JSON file? (yes/no): ");
        String restoreMapChoice = scanner.next().toLowerCase();
        if (restoreMapChoice.equals("yes")) {
            System.out.print("Enter the name of the file to load the map JSON: ");
            String fileName = scanner.next();

            try {
                Importer mapLoader = new Importer();
                mapLoader.importData(map, fileName);
                clearConsole();
                System.out.println("Map loaded successfully!");

            } catch (IllegalArgumentException | IOException e) {
                System.out.println("Error loading the map: " + e.getMessage());
                return;
            }
        }

        allRooms = map.getRooms();

        Hero hero = createHero(scanner);
        Room targetRoom = findRoomWithTarget(allRooms);
        selectStartRoom(map,hero, scanner, allRooms);


        while (hero.isHeroAlive()) {
            Room movedRoom = listConnectedRooms(map, scanner, hero, allRooms, targetRoom);

            if (movedRoom.isIsAndOut()) {
                if (hero.doesHeroHaveTarget()) {
                    System.out.println("Success! Hero has reached the exit with the target.");
                } else {
                    System.out.println("Game Over! Hero reached the exit without the target.");
                }
                break;
            }

            if (!movedRoom.isThereAnEnemyAlive()) {
                ScenaryTwo(movedRoom, targetRoom, hero, scanner); // ja faz o cenario 2 3 , 4 , 6
            } else {
                System.out.println("!!!!!ENEMIES IN THE ROOM!!!!!:\n");

                while (hero.isHeroAlive() && movedRoom.isThereAnEnemyAlive()) { // ja faz o cenario 1 , 4 , 5
                    ScenaryOne(map, movedRoom, hero, scanner);
                }
                if (movedRoom.hasItems()) {
                    itemsScenario(movedRoom, hero);
                }
                if (movedRoom.equals(targetRoom)) {
                    Target target = movedRoom.getTarget();
                    hero.setTarget(target);
                    movedRoom.removeTarget(target);
                    System.out.println("Hero has reached the target.\n"
                            + "Your online device has been compromised. You need to find your way out alone!\n" +
                            "Available services: \n" +
                            "1.Next room's information ");
                }
            }
        }


    }

    public static void ScenaryOne(Map map, Room movedRoom, Hero hero,Scanner scanner) throws HeroException {
        heroTurn(hero, movedRoom, scanner);

        if(!hero.isHeroAlive()){
            System.out.println("Hero is dead. Game over.");
            return;
        }

        if(!movedRoom.isThereAnEnemyAlive()){
            System.out.println("You have defeated all enemies in the room.");
            return;
        }
        //SHUFFFLEEE !!!-----------------------------------------------------------------------------------------------------------------------------------------------------
        // map.shuffleEnemies();

        enemyTurnAttack(movedRoom, hero);

    }


    public static void ScenaryTwo(Room movedRoom, Room targetRoom, Hero hero, Scanner scanner) throws TargetException {

        System.out.println("No enemies in the room.");
        System.out.println("Enemy's turn:");
        //map.shuffleEnemies();
        if (!movedRoom.isThereAnEnemyAlive()) {
            System.out.println("No enemies have entered the room.");
            System.out.println("Hero's turn:");

            if (movedRoom.hasItems()) {
                itemsScenario(movedRoom, hero);
            }
            if (movedRoom.equals(targetRoom)) {
                Target target = movedRoom.getTarget();
                hero.setTarget(target);
                movedRoom.removeTarget(target);
                System.out.println("Hero has reached the target.\n"
                        + "Your online device has been compromised. You need to find your way out alone!\n" +
                        "Available services: \n" +
                        "1.Next room's information ");
            }

        } else {
            while (hero.isHeroAlive() && !movedRoom.getEnemies().isEmpty()) {
                ScenaryThree(movedRoom, hero, scanner);
            }
        }
    }

    public static void ScenaryThree (Room movedRoom, Hero hero, Scanner scanner){
        System.out.println("ENEMIES HAVE ENTERED THE ROOM");

        while (hero.isHeroAlive() && !movedRoom.isThereAnEnemyAlive()) {
            enemyTurnAttack(movedRoom, hero);

            if (!hero.isHeroAlive()) {
                System.out.println("Hero is dead. Game over.");
                return;
            }

            heroTurn(hero, movedRoom, scanner);
        }

    }

    public static void itemsScenario(Room movedRoom, Hero hero){
        System.out.println("--------------------------------------");
        System.out.println("Items in the room:");
        Iterator<Item> itemIterator = movedRoom.getItems();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            try {
                Item itemAux = movedRoom.removeItem(item, hero); // ja faz as verificaçoes no metodo
                System.out.println("Hero picked up item: " + itemAux.getNameItem());
            } catch (ItemException e) {
                System.out.println("Failed to remove item: " + e.getMessage());
            }
        }
    }



    public static void heroTurn(Hero hero, Room movedRoom, Scanner scanner) {
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
        System.out.println("Hero's turn to attack:");
        enemyIterator = movedRoom.getEnemies().iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            if (!enemy.isDead()) {
                hero.attack(enemy);
                if (enemy.isDead()) {
                    System.out.println("Enemy " + enemy.getName() + " is dead.");
                    movedRoom.addEnemyDeadCounter();
                } else {
                    System.out.println("Hero attacked " + enemy.getName() + ". Enemy health: " + enemy.getHealth());
                }
            }
        }
    }
}

    public static void enemyTurnAttack(Room movedRoom, Hero hero) {
        System.out.println("Enemies' turn to attack:");
        Iterator<Enemy> enemyIterator = movedRoom.getEnemies().iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            if (!enemy.isDead()) {
                enemy.attack(hero);
                System.out.println(enemy.getName() + " attacked Hero. Hero health: " + hero.getHealth() + ", Hero armor: " + hero.getArmorHealth());
            }
        }
    }




    public static Hero createHero(Scanner scanner) {

        int attackValue;
        do {
            System.out.print("Enter the attack value for your hero (non-negative): ");
            attackValue = scanner.nextInt();
            if (attackValue < 0) {
                System.out.println("Attack value cannot be negative. Please try again.");
            }
        } while (attackValue < 0);

        int backpackCapacity;
        do {
            System.out.print("Enter the capacity of your hero's backpack (non-negative): ");
            backpackCapacity = scanner.nextInt();
            if (backpackCapacity < 0) {
                System.out.println("Backpack capacity cannot be negative. Please try again.");
            }
        } while (backpackCapacity < 0);
        clearConsole();

        return new HeroImp(attackValue, backpackCapacity);
    }


    public static void selectStartRoom(Map map, Hero hero, Scanner scanner, UnorderedListADT<Room> rooms) throws HeroException {
        UnorderedListADT<Room> inAndOutRooms = new ArrayUnorderedList<>();
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
                    clearConsole();
                    System.out.println("Hero will start the game at the room: " + selectedRoom.getRoomName());

                    System.out.println("!!!!!ENEMIES IN THE ROOM!!!!!:\n");

                    while (hero.isHeroAlive() && selectedRoom.isThereAnEnemyAlive()) { // ja faz o cenario 1 , 4 , 5
                        ScenaryOne(map, selectedRoom, hero, scanner);
                    }
                } else {
                    System.out.println("Select another room.");
                }
            } else {
                roomConfirmed = true;
                selectedRoom.addHero(hero);
                clearConsole();
                System.out.println("Hero will start the game at the room: " + selectedRoom.getRoomName());
            }
        }
}

    public static Room listConnectedRooms(Map map, Scanner scanner, Hero hero, UnorderedListADT<Room> allRooms, Room targetRoom) throws HeroException {
    Room currentRoom = hero.getCurrentRoom();
    hero.setCurrentRoom(null);
    currentRoom.removeHero();

    Iterator<Room> iterator = allRooms.iterator();
    UnorderedListADT<Room> connectedRooms = new ArrayUnorderedList<>();

    while (iterator.hasNext()) {
        Room room = iterator.next();
        if (map.isConnected(currentRoom, room)) {
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

    public static Room findRoomWithTarget(UnorderedListADT<Room> rooms) {
        Iterator<Room> roomIterator = rooms.iterator();
        while (roomIterator.hasNext()) {
            Room room = roomIterator.next();
            if (room.isTargetInRoom()) {
                return room;
            }
        }
        return null; // Return null if no room contains the target
    }

    public static void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}