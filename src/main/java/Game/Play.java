package Game;

import Collections.Lists.ArrayUnorderedList;
import Collections.Lists.LinkedUnorderedList;
import Collections.Lists.UnorderedListADT;
import Game.Entities.HeroImp;
import Game.Exceptions.EnemyException;
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
    public static void main(String[] args) throws HeroException, ItemException, TargetException, EnemyException {
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

    public static void startGame(Scanner scanner) throws HeroException, ItemException, TargetException, EnemyException {
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
                System.out.println(map.toString());

            } catch (IllegalArgumentException | IOException e) {
                System.out.println("Error loading the map: " + e.getMessage());
                return;
            }
        }

        allRooms = map.getRooms();

        Hero hero = createHero(scanner);
        Room targetRoom = findRoomWithTarget(allRooms);
        selectStartRoom(map,hero, scanner, allRooms);


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
                Room movedRoom = listConnectedRooms(map, scanner, hero, allRooms, targetRoom);

                if (checkEndGame(hero, movedRoom)) {
                    break;
                }

                if (movedRoom.isThereAnEnemyAlive() && !movedRoom.isTargetInRoom()) {
                    ScenaryOne(map, movedRoom, hero, scanner);
                }

                if (!movedRoom.isThereAnEnemyAlive() && !movedRoom.isTargetInRoom()) {
                    ScenaryTwo(map, movedRoom, targetRoom, hero, scanner);
                }


                if (movedRoom.isThereAnEnemyAlive() && movedRoom.isTargetInRoom()) {
                    ScenaryFive(map, movedRoom, hero, scanner);
                }

                if (movedRoom.isTargetInRoom() && !movedRoom.isThereAnEnemyAlive()) {
                    ScenarySix(hero, movedRoom);
                }
            }else{
                hero.UseItem();
                System.out.println("Hero used an item. Hero health: " + hero.getHealth() + ", Hero armor: " + hero.getArmorHealth());
                System.out.println("------------------------------------------------ ENEMY TURN");
                System.out.println("------------------------------------------------ ENEMY MOVED");
                map.mapShuffle();
                System.out.println("------------------------------------------------");
                if(hero.getCurrentRoom().isThereAnEnemyAlive()){
                    ScenaryOne(map, hero.getCurrentRoom(), hero, scanner);
                }
            }

        }
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

    public static void ScenaryOne(Map map, Room movedRoom, Hero hero,Scanner scanner) throws HeroException, EnemyException {
        UnorderedListADT<Enemy> enemies = movedRoom.getEnemies();
        while (hero.isAlive() && movedRoom.isThereAnEnemyAlive()) {

            fight(enemies,hero,movedRoom,scanner);

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

    public static void ScenaryTwo(Map map , Room movedRoom, Room targetRoom, Hero hero, Scanner scanner) throws TargetException, EnemyException, HeroException {

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
                ScenarySix(hero, movedRoom);
            }
        } else {
                ScenaryOne(map ,movedRoom, hero, scanner);
        }
    }

    public static void ScenaryFive(Map map, Room movedRoom, Hero hero, Scanner scanner) throws HeroException, EnemyException, TargetException {
        System.out.println("Tó Cruz encontrou o alvo, mas há inimigos na sala!");

        ScenaryOne(map, movedRoom, hero, scanner);

        // Fim do turno: O próximo turno começa com Tó Cruz ainda na sala com o alvo, após eliminar os inimigos
        if (hero.isAlive() && !movedRoom.isThereAnEnemyAlive()) {
            System.out.println("Todos os inimigos na sala foram eliminados. Tó Cruz pode agora resgatar o alvo.");
            ScenarySix(hero, movedRoom);
        }
    }

    public static void ScenarySix(Hero hero, Room movedRoom) throws TargetException {
        hero.setTarget(movedRoom.getTarget());
        movedRoom.removeTarget(movedRoom.getTarget());
        System.out.println("Hero has reached the target.\n"
                + "Your online device has been compromised. You need to find your way out alone!\n" +
                "Available services: \n" +
                "1.Next room's information ");
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
                System.out.println("Item left in the room because : " + e.getMessage());
            }
        }
    }

    public static void fight(UnorderedListADT<Enemy> enemies,Hero hero, Room movedRoom,Scanner scanner) throws HeroException, EnemyException {
        if (enemies == null ||  !movedRoom.isThereAnEnemyAlive()) {
            System.out.println("No Enemies in the room to fight.");
            return;
        }
            heroTurn(hero,movedRoom,scanner);
            if (movedRoom.isThereAnEnemyAlive()) {
                enemyTurnAttack(movedRoom,hero); // inimigos atacam se ainda estiverem vivos
                if (hero.getHealth() <= 0) {
                    System.out.println("Hero has died.");
                }
            }else{
                System.out.println("Hero has killed all enemies");
            }
    }

    public static void heroTurn(Hero hero, Room movedRoom, Scanner scanner) {
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

    public static void enemyTurnAttack(Room movedRoom, Hero hero) {
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

    public static void selectStartRoom(Map map, Hero hero, Scanner scanner, UnorderedListADT<Room> rooms) throws HeroException, EnemyException {
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
                    clearConsole();
                    System.out.println("Hero will start the game at the room: " + selectedRoom.getRoomName());

                    System.out.println("!!!!!ENEMIES IN THE ROOM!!!!!:\n");
                        ScenaryOne(map, selectedRoom, hero, scanner);
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