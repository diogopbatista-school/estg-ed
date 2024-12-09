package Game.PlayGame;

import Collections.Lists.*;
import Collections.SortingandSearching;
import Game.Interfaces.*;
import Game.Utilities.ManualSimulationLog;

import java.util.Iterator;


public class Print {

    public Print() {

    }

    public void clearConsole() {
        for (int i = 0; i < 10; ++i) {
            System.out.println();
        }
    }

    public void mainMenu() {
        System.out.println("\n+------------------+");
        System.out.println("|       MENU       |");
        System.out.println("+---+--------------+");
        System.out.println("| 1 | Start Game   |");
        System.out.println("+---+--------------+");
        System.out.println("| 2 | Exit         |");
        System.out.println("+---+--------------+");
    }

    public void viewLogs(Mission mission) {
        OrderedListADT<ManualSimulationLog> logs = mission.getLogs().getManualSimulationLogs();

        SortingandSearching.insertionSort(listToArray(logs));

        Iterator<ManualSimulationLog> it = logs.iterator();

        System.out.println("+-----------------------------------------------+");
        while (it.hasNext()) {
            ManualSimulationLog log = it.next();
            System.out.println(log.toString());
        }


    }

    private ManualSimulationLog[] listToArray(OrderedListADT<ManualSimulationLog> list) {
        ManualSimulationLog[] array = new ManualSimulationLog[list.size()];
        Iterator<ManualSimulationLog> iterator = list.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            array[index++] = iterator.next();
        }
        return array;
    }

    public void availableMissions(UnorderedListADT<String> missionCodes) {
        System.out.println("+--------------------------+");
        System.out.println("|  Choose Mission to play  |");
        System.out.println("+--------------------------+");
        Iterator<String> iterator = missionCodes.iterator();
        int index = 1;
        while (iterator.hasNext()) {
            System.out.println(index + ". " + iterator.next());
            index++;
        }
        System.out.print("Choose a mission: ");
    }

    public void playAgain() {
        System.out.println("+------------------------------------+");
        System.out.println("| Do you want to play the game again |");
        System.out.println("+---+--------------------------------+");
        System.out.println("| 1 | Yes                            |");
        System.out.println("+---+--------------------------------+");
        System.out.println("| 2 | No                             |");
        System.out.println("+---+--------------------------------+");
    }

    public void MoveOrUseItem(Hero hero) {
        if (hero.isItemsOnBackPack()) {
            System.out.println("+---+----------------------+---+-----------------+ Name: "+ hero.getName());
            System.out.println("| 1 | Move to another room | 2 | Use Item        | Health: " + hero.getHealth());
            System.out.println("+---+----------------------+---+-----------------+ Armor: "+ hero.getArmorHealth());
            itemsInBackPack(hero);
        } else {
            System.out.println("+---+----------------------+ Name: "+ hero.getName());
            System.out.println("| 1 | Move to another room | Health: " + hero.getHealth());
            System.out.println("+---+----------------------+ Armor: "+ hero.getArmorHealth());
            itemsInBackPack(hero);
        }
    }

    public void itemsInBackPack(Hero hero) {
        String items = "Items in backpack:";
        if(!hero.isItemsOnBackPack()) {
            System.out.println("Empty BACKPACK");
            return;
        }
        Iterator<Item> itemIterator = hero.getBackPack().iterator();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            items += item.getName() + "("+ item.getPoints() + ") points | ";
        }
        System.out.println(items);
    }

    public void loadedMissionMenu(Mission mission,String selectedMission) {
        clearConsole();
        System.out.println(mission.getMap().toString());
        viewLogs(mission);
        System.out.println("Mission loaded successfully! Mission: " + selectedMission);

        System.out.println("+------------------+");
        System.out.println("| Choose play mode |");
        System.out.println("+---+--------------+");
        System.out.println("| 1 | Manual       |");
        System.out.println("+---+--------------+");
        System.out.println("| 2 | Automatic    |");
        System.out.println("+---+--------------+");
    }

    public void nextBestRoom(Map map, Room currentRoom, Room targetRoom) {
        Iterator<Room> pathIterator = map.shortestPath(currentRoom, targetRoom);
        System.out.println("----------------------------------------------------------------");
        System.out.println("Shortest path from " + currentRoom.getRoomName() + " to " + targetRoom.getRoomName() + ":");
        while (pathIterator.hasNext()) {
            Room room = pathIterator.next();
            System.out.print(room.getRoomName() + " -> ");
        }
        System.out.println("END\n");
    }



    public void nextRoomsAndInfos(UnorderedListADT<Room> connectedRooms) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Connected Rooms:");
        Iterator<Room> connectedRoomIterator = connectedRooms.iterator();
        int index = 1;
        while (connectedRoomIterator.hasNext()) {
            Room room = connectedRoomIterator.next();
            System.out.println(index + ". " + room.getRoomName());

            // Display information about enemies and items in the connected room
            System.out.println("Information about the room:");
            if (room.isInAndOutRoom()) {
                System.out.println("-> -> -> THIS IS AN EXIT ROOM <- <- <-");
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
                    System.out.println("- " + item.getName() + " (Type: " + item.getType().toString() + ")");
                }
            } else {
                System.out.println("No items in the room.");
            }

            System.out.println("+----------------------------------------------------------------+");
            index++;
        }
    }

    public UnorderedListADT<Room> inAndOutRooms(UnorderedListADT<Room> rooms) {
        UnorderedListADT<Room> inAndOutRooms = new LinkedUnorderedList<>(); // Linked porque eu nunca sei quantos elementos eu vou ter
        System.out.println("+------------------+");
        System.out.println("| In and Out Rooms |");
        System.out.println("+------------------+");

        Iterator<Room> roomIterator = rooms.iterator();
        while (roomIterator.hasNext()) {
            Room room = roomIterator.next();
            if (room.isInAndOutRoom()) {
                inAndOutRooms.addToRear(room);
                System.out.println(inAndOutRooms.size() + ". " + room.getRoomName());
            }
        }
        return inAndOutRooms;
    }

    public void noEnemiesInRoomStartMoving() {
        System.out.println("No enemies in the room.");
        System.out.println("+------------------------------------------------+");
        System.out.println("Enemy's started to move to another room.");
    }

    public void movedEnemies() {
        System.out.println("--------------------------------------------------------------- MOVED ENEMIES");
    }

    public void enemyTurn() {
        System.out.println("------------------------------------------------ ENEMY TURN");
    }

    public void heroMovedToRoom(Room selectedRoom) {
        clearConsole();
        System.out.println("Hero moved to the room: " + selectedRoom.getRoomName());

    }

    public void heroTurn() {
        System.out.println("+-----------+");
        System.out.println("| HERO TURN |");
        System.out.println("+-----------+");
        System.out.println("Hero's turn:");
    }

    public void heroTurnToAttack() {
        System.out.println("+-----------+");
        System.out.println("| HERO TURN |");
        System.out.println("+-----------+");
        System.out.println("Hero's turn to attack:");
    }

    public void enemyTurnToAttack() {
        System.out.println("+--------------+");
        System.out.println("| ENEMIES TURN |");
        System.out.println("+--------------+");
        System.out.println("Enemies turn:");
    }

    public void gameOverHeroDead() {
        System.out.println("+-------------------------+");
        System.out.println("| Game Over! Hero is dead |");
        System.out.println("+-------------------------+");
    }

    public void heroKilledAllEnemies() {
        System.out.println("+-----------------------------+");
        System.out.println("| Hero has killed all enemies |");
        System.out.println("+-----------------------------+");
    }

    public void gameOverHeroReachedExit() {
        System.out.println("+-----------------------------------------------------+");
        System.out.println("| Game Over! Hero reached the exit without the target |");
        System.out.println("+-----------------------------------------------------+");
    }


    public void gameWonHeroReachedExit(Hero hero) {
        System.out.println("+-----------------------------------------------------+ Hero: " + hero.getName());
        System.out.println("| Success! Hero has reached the exit with the target. | Health: " + hero.getHealth());
        System.out.println("+-----------------------------------------------------+ Armor: " + hero.getArmorHealth());
        itemsInBackPack(hero);
    }

    public void heroChoicedUseItem() {
        System.out.println("Hero is about to use an item");
    }

    public void heroUsedItem(Hero hero, Item item) {
        System.out.println("Hero grabbed an item: " + item.getName() + " with " + item.getPoints());
        System.out.println("Hero used an item. Hero health: " + hero.getHealth() + ", Hero armor: " + hero.getArmorHealth());
    }

    public void fightOverItemsToCollect(Room movedRoom) {
        System.out.println("+-----------------------------------+");
        System.out.println("| Fight over. Time to collect items |");
        System.out.println("+-----------------------------------+");
        Iterator<Item> itemIterator = movedRoom.getItems();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            System.out.println(item.getName());
        }
    }

    public void fightOverNoItemsToCollect() {
        System.out.println("+----------------------------------------------------------------+");
        System.out.println("| Fight over. No items to collect and target is not in this room |");
        System.out.println("+----------------------------------------------------------------+");
    }

    public void reachedTarget() {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("| Hero has reached the target.                                                    |");
        System.out.println("|   Your online device has been compromised. You need to find your way out alone! |");
        System.out.println("|   Available services:                                                           |");
        System.out.println("|       - Next room's information                                                 |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void enemiesInTheRoomTarget() {
        System.out.println("+-----------------------------------------------------------+");
        System.out.println("| You are in the room with the target but there are enemies |");
        System.out.println("+-----------------------------------------------------------+");
    }

    public void allEnemiesRoomTargetDefeated() {
        System.out.println("+------------------------------------------------------+");
        System.out.println("| All enemies in the room with the target are defeated |");
        System.out.println("| You can now reach the target.                        |");
        System.out.println("+------------------------------------------------------+");
    }

    public void enemyHasEnteredTheHeroRoom(Enemy enemy) {
        System.out.println(enemy.getName() + " has entered the room with the hero and attacked the hero.");
    }

    public void selectAnotherRoom() {
        System.out.println("Select another room.");
    }

    public void heroWillStartRoom(Room selectedRoom) {
        clearConsole();
        System.out.println("Hero will start the game at the room: " + selectedRoom.getRoomName());
    }

    public void noEnemyEnteredRoom() {
        System.out.println("No enemies have entered the room");
    }
}
