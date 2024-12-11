package Game.PlayGame;

import Collections.Lists.*;
import Collections.SortingandSearching;
import Game.Interfaces.*;
import Game.Utilities.ManualSimulationLog;

import java.util.Iterator;


/**
 * This class represents the print class of the game
 * This class is responsible for printing the game information
 * We separated the console prints into this class in order to make the code more organized
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class Print {

    /**
     * Constructor for the Print class
     */
    public Print() {
    }

    protected void clearConsole() {
        for (int i = 0; i < 10; ++i) {
            System.out.println();
        }
    }

    /**
     * Method that represents the main menu print of the game
     */
    protected void mainMenu() {
        System.out.println("\n+------------------+");
        System.out.println("|       MENU       |");
        System.out.println("+---+--------------+");
        System.out.println("| 1 | Start Game   |");
        System.out.println("+---+--------------+");
        System.out.println("| 2 | Exit         |");
        System.out.println("+---+--------------+");
    }

    /**
     * Method that represents the view logs of the mission in the console
     *
     */
    protected void viewLogs(Missions missions, String missionName) {
        Mission mission = missions.getMissionByCode(missionName);
        OrderedListADT<ManualSimulationLog> logs = mission.getLogs().getManualSimulationLogs();

        SortingandSearching.insertionSort(listToArray(logs));

        Iterator<ManualSimulationLog> it = logs.iterator();

        System.out.println("+-----------------------------------------------+");
        while (it.hasNext()) {
            ManualSimulationLog log = it.next();
            System.out.println(log.toString());
        }
    }

    /**
     * This method converts a list of logs to an array of logs for the sorting algorithm
     *
     * @param list the list of logs
     * @return the array of logs
     */
    private ManualSimulationLog[] listToArray(OrderedListADT<ManualSimulationLog> list) {
        ManualSimulationLog[] array = new ManualSimulationLog[list.size()];
        Iterator<ManualSimulationLog> iterator = list.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            array[index++] = iterator.next();
        }
        return array;
    }

    /**
     * Method that represents the available missions print to play
     *
     * @param missionCodes the available missions
     */
    protected void availableMissions(UnorderedListADT<String> missionCodes) {
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

    /**
     * Method that represents the want to play again print
     */
    protected void playAgain() {
        System.out.println("+------------------------------------+");
        System.out.println("| Do you want to play the game again |");
        System.out.println("+---+--------------------------------+");
        System.out.println("| 1 | Yes                            |");
        System.out.println("+---+--------------------------------+");
        System.out.println("| 2 | No                             |");
        System.out.println("+---+--------------------------------+");
    }

    /**
     * Method that represents the print move or use item
     *
     * @param hero the hero to move or to use item
     */
    protected void MoveOrUseItem(Hero hero) {
        if (hero.isItemsOnBackPack()) {
            System.out.println("+---+----------------------+---+-----------------+ Name: " + hero.getName());
            System.out.println("| 1 | Move to another room | 2 | Use Item        | Health: " + hero.getHealth());
            System.out.println("+---+----------------------+---+-----------------+ Armor: " + hero.getArmorHealth());
            itemsInBackPack(hero);
        } else {
            System.out.println("+---+----------------------+ Name: " + hero.getName());
            System.out.println("| 1 | Move to another room | Health: " + hero.getHealth());
            System.out.println("+---+----------------------+ Armor: " + hero.getArmorHealth());
            itemsInBackPack(hero);
        }
    }

    /**
     * Method that represents the print of the items in the backpack
     *
     * @param hero the hero to print the items
     */
    protected void itemsInBackPack(Hero hero) {
        String items = "Items in backpack:";
        if (!hero.isItemsOnBackPack()) {
            System.out.println("Empty BACKPACK");
            return;
        }
        Iterator<Item> itemIterator = hero.getBackPack().iterator();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            items += item.getName() + "(" + item.getPoints() + ") points | ";
        }
        System.out.println(items);
    }

    /**
     * Method to print the loadedMissionMenu print where it will contain the logs print to that missions
     * and the options to play the game
     *
     * @param mission         the mission to load
     * @param selectedMission the selected mission
     */
    protected void loadedMissionMenu(Missions missions,Mission mission, String selectedMission) {
        clearConsole();
        System.out.println(mission.getMap().toString());
        viewLogs(missions, selectedMission);
        System.out.println("Mission loaded successfully! Mission: " + selectedMission);

        System.out.println("+------------------+");
        System.out.println("| Choose play mode |");
        System.out.println("+---+--------------+");
        System.out.println("| 1 | Manual       |");
        System.out.println("+---+--------------+");
        System.out.println("| 2 | Automatic    |");
        System.out.println("+---+--------------+");
    }

    /**
     * Method that prints the shortestpath to the target room
     *
     * @param map         the map
     * @param currentRoom the current room
     * @param targetRoom  the target room
     */
    protected void nextBestRoom(Map map, Room currentRoom, Room targetRoom, String Prompt) {
        Iterator<Room> pathIterator = map.shortestPath(currentRoom, targetRoom);
        System.out.println("----------------------------------------------------------------");
        System.out.println(Prompt + " | Shortest path from " + currentRoom.getRoomName() + " to " + targetRoom.getRoomName() + ":");
        while (pathIterator.hasNext()) {
            Room room = pathIterator.next();
            System.out.print(room.getRoomName() + " -> ");
        }
        System.out.println("END\n");
    }

    /**
     * Method that prints the next rooms and infos
     *
     * @param connectedRooms the connected rooms
     */
    protected void nextRoomsAndInfos(UnorderedListADT<Room> connectedRooms) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Connected Rooms:");
        Iterator<Room> connectedRoomIterator = connectedRooms.iterator();
        int index = 1;
        while (connectedRoomIterator.hasNext()) {
            Room room = connectedRoomIterator.next();
            System.out.println(index + ". " + room.getRoomName());


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

    /**
     * Method that represents the in and out rooms print
     *
     * @param rooms the rooms
     * @return the in and out rooms
     */
    protected UnorderedListADT<Room> inAndOutRooms(UnorderedListADT<Room> rooms) {
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

    /**
     * Method that prints when the hero entered the room and no enemies are in the room
     */
    protected void noEnemiesInRoomStartMoving() {
        System.out.println("No enemies in the room.");
        System.out.println("+------------------------------------------------+");
        System.out.println("Enemy's started to move to another room.");
    }

    /**
     * Method that prints indicanting that the enemies are moving
     */
    protected void movedEnemies() {
        System.out.println("--------------------------------------------------------------- MOVED ENEMIES");
    }

    /**
     * Method to print the enemy turn in the console
     */
    protected void enemyTurn() {
        System.out.println("------------------------------------------------ ENEMY TURN");
    }

    /**
     * Method to print the hero moved to the room
     *
     * @param selectedRoom the selected room
     */
    protected void heroMovedToRoom(Room selectedRoom) {
        clearConsole();
        System.out.println("Hero moved to the room: " + selectedRoom.getRoomName());

    }

    /**
     * Method to print the hero turn in the console
     */
    protected void heroTurn() {
        System.out.println("+-----------+");
        System.out.println("| HERO TURN |");
        System.out.println("+-----------+");
        System.out.println("Hero's turn:");
    }

    /**
     * Method to print the hero turn to attack in the console
     */
    protected void heroTurnToAttack() {
        System.out.println("+-----------+");
        System.out.println("| HERO TURN |");
        System.out.println("+-----------+");
        System.out.println("Hero's turn to attack:");
    }

    /**
     * Method to print the enemy turn to attack in the console
     */
    protected void enemyTurnToAttack() {
        System.out.println("+--------------+");
        System.out.println("| ENEMIES TURN |");
        System.out.println("+--------------+");
        System.out.println("Enemies turn:");
    }

    /**
     * Method to print the game over hero dead in the console
     */
    protected void gameOverHeroDead() {
        System.out.println("+-------------------------+");
        System.out.println("| Game Over! Hero is dead |");
        System.out.println("+-------------------------+");
    }

    /**
     * Method to print the hero killed all enemies in the console
     */
    protected void heroKilledAllEnemies() {
        System.out.println("+-----------------------------+");
        System.out.println("| Hero has killed all enemies |");
        System.out.println("+-----------------------------+");
    }

    /**
     * Method to print the game over hero reached the exit without the target in the console
     */
    protected void gameOverHeroReachedExit() {
        System.out.println("+-----------------------------------------------------+");
        System.out.println("| Game Over! Hero reached the exit without the target |");
        System.out.println("+-----------------------------------------------------+");
    }

    /**
     * Method to print the game won hero reached the exit with the target in the console
     *
     * @param hero the hero
     */
    protected void gameWonHeroReachedExit(Hero hero) {
        System.out.println("+-----------------------------------------------------+ Hero: " + hero.getName());
        System.out.println("| Success! Hero has reached the exit with the target. | Health: " + hero.getHealth());
        System.out.println("+-----------------------------------------------------+ Armor: " + hero.getArmorHealth());
        itemsInBackPack(hero);
    }

    /**
     * Print is about to use an item
     */
    protected void heroChoicedUseItem() {
        System.out.println("Hero is about to use an item");
    }

    /**
     * Print hero grabbed an item and used it
     *
     * @param hero the hero
     * @param item the item
     */
    protected void heroUsedItem(Hero hero, Item item) {
        System.out.println("Hero grabbed an item: " + item.getName() + " with " + item.getPoints());
        System.out.println("Hero used an item. Hero health: " + hero.getHealth() + ", Hero armor: " + hero.getArmorHealth());
    }

    /**
     * Print that the fight is over and the hero has collected the items
     *
     * @param movedRoom the room where the hero is
     */
    protected void fightOverItemsToCollect(Room movedRoom) {
        System.out.println("+-----------------------------------+");
        System.out.println("| Fight over. Time to collect items |");
        System.out.println("+-----------------------------------+");
        Iterator<Item> itemIterator = movedRoom.getItems();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            System.out.println(item.getName());
        }
    }

    /**
     * Print that the fight is over and there are no items to collect and the target is not in the room
     */
    protected void fightOverNoItemsToCollect() {
        System.out.println("+----------------------------------------------------------------+");
        System.out.println("| Fight over. No items to collect and target is not in this room |");
        System.out.println("+----------------------------------------------------------------+");
    }

    /**
     * Print that the hero has reached the target and the online device has been compromised such as the shortest path to the exit
     */
    protected void reachedTarget() {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("| Hero has reached the target.                                                    |");
        System.out.println("|   Your online device has been compromised. You need to find your way out alone! |");
        System.out.println("|   Available services:                                                           |");
        System.out.println("|       - Next room's information                                                 |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    /**
     * Print that the hero has reached the target room but there are enemies in the room
     */
    protected void enemiesInTheRoomTarget() {
        System.out.println("+-----------------------------------------------------------+");
        System.out.println("| You are in the room with the target but there are enemies |");
        System.out.println("+-----------------------------------------------------------+");
    }

    /**
     * Print that all enemies in the room with the target are defeated
     */
    protected void allEnemiesRoomTargetDefeated() {
        System.out.println("+------------------------------------------------------+");
        System.out.println("| All enemies in the room with the target are defeated |");
        System.out.println("| You can now reach the target.                        |");
        System.out.println("+------------------------------------------------------+");
    }

    /**
     * Print that an enemy has entered the room with the hero and attacked the hero
     *
     * @param enemy the enemy
     */
    protected void enemyHasEnteredTheHeroRoom(Enemy enemy) {
        System.out.println(enemy.getName() + " has entered the room with the hero and attacked the hero.");
    }

    /**
     * print select another room
     */
    protected void selectAnotherRoom() {
        System.out.println("Select another room.");
    }

    /**
     * print hero will start the game at the room
     *
     * @param selectedRoom the selected room
     */
    protected void heroWillStartRoom(Room selectedRoom) {
        clearConsole();
        System.out.println("Hero will start the game at the room: " + selectedRoom.getRoomName());
    }

    /**
     * Print that no enemy entered the room
     */
    protected void noEnemyEnteredRoom() {
        System.out.println("No enemies have entered the room");
    }
}
