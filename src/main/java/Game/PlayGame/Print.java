package Game.PlayGame;

import Collections.Lists.LinkedUnorderedList;
import Collections.Lists.UnorderedListADT;
import Game.Interfaces.*;

import java.util.Iterator;


public class Print {

    public Print(){

    }

    public void clearConsole(){
        for (int i = 0; i < 50; ++i) {
            System.out.println();
        }
    }

    public void mainMenu(){
        System.out.println("\nMenu:");
        System.out.println("1. Start Game");
        System.out.println("2. Exit");
        System.out.print("Choose an option: ");
    }

    public void availableMissions(UnorderedListADT<String> missionCodes){
        System.out.println("\nChoose Mission to play\n:");
        System.out.println("\nAvailable Missions:");
        Iterator<String> iterator = missionCodes.iterator();
        int index = 1;
        while (iterator.hasNext()) {
            System.out.println(index + ". " + iterator.next());
            index++;
        }
        System.out.print("Choose a mission: ");
    }

    public void playAgain(){
        System.out.println("Do you want to play the game again?");
        System.out.println("1. Yes");
        System.out.println("2. No");
    }

    public void MoveOrUseItem(Hero hero){
        if (hero.isItemsOnBackPack()) {
            System.out.println("1. Move to another room");
            System.out.println("2. Use Item");
        } else {
            System.out.println("1. Move to another room");
        }
    }

    public void loadedMissionMenu(String selectedMission){
        clearConsole();
        System.out.println("Mission loaded successfully! Mission: " + selectedMission);

        System.out.println("Choose play mode:");
        System.out.println("1. Manual");
        System.out.println("2. Automatic");
    }

    public void NextBestRoom(Map map , Room currentRoom, Room targetRoom){
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

    public void nextRoomsAndInfos(UnorderedListADT<Room> connectedRooms){
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
    }

    public UnorderedListADT<Room> inAndOutRooms(UnorderedListADT<Room> rooms){
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
        return inAndOutRooms;
    }

    public void noEnemiesInRoomStartMoving() {
        System.out.println("No enemies in the room.");
        System.out.println("------------------------------------------------");
        System.out.println("Enemy's started to move to another room.");
    }

    public void movedEnemies() {
        System.out.println("--------------------------------------------------------------- MOVED ENEMIES");
    }

    public void enemyTurn(){
        System.out.println("------------------------------------------------ ENEMY TURN");
    }

    public void heroMovedToRoom(Room selectedRoom){
        clearConsole();
        System.out.println("Hero moved to the room" + selectedRoom.getRoomName());
    }

    public void heroTurn(){
        System.out.println("------------------------------------------------ HERO TURN");
        System.out.println("Hero's turn:");
    }

    public void heroTurnToAttack(){
        System.out.println("---------------------------------------------------- HERO TURN");
        System.out.println("Hero's turn to attack:");
    }

    public void enemyTurnToAttack(){
        System.out.println("------------------------------------------------ ENEMY TURN");
        System.out.println("Enemies' turn to attack:");
    }

    public void gameOverHeroDead(){
        System.out.println("Game Over! Hero is dead.");
    }

    public void heroKilledAllEnemies(){
        System.out.println("Hero has killed all enemies");
    }

    public void gameOverHeroReachedExit(){
        System.out.println("Game Over! Hero reached the exit without the target.");
    }


    public void gameWonHeroReachedExit(){
        System.out.println("Success! Hero has reached the exit with the target.");
    }

    public void heroChoicedUseItem(){
        System.out.println("Hero is about to use an item");
    }

    public void heroUsedItem(Hero hero, Item item){
        System.out.println("Hero grabbed an item:" + item.getNameItem() + " with " + item.getPoints());
        System.out.println("Hero used an item. Hero health: " + hero.getHealth() + ", Hero armor: " + hero.getArmorHealth());
    }

    public void fightOverItemsToCollect(Room movedRoom){
        System.out.println("Fight over. Time to collect items.");
        System.out.println("--------------------------------------");
        System.out.println("Items in the room:");
        Iterator<Item> itemIterator = movedRoom.getItems();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            System.out.println(item.getNameItem());
        }
    }

    public void fightOverNoItemsToCollect(){
        System.out.println("Fight over. No items to collect and target is not in this room.");
    }

    public void reachedTarget(){
        System.out.println("Hero has reached the target."
                + "Your online device has been compromised. You need to find your way out alone!\n" +
                "Available services:\n " +
                "- Next room's information ");
    }

    public void enemiesInTheRoomTarget(Hero hero){
        System.out.println(hero.getName() + " is in the room with the target but there are enemies");
    }

    public void allEnemiesRoomTargetDefeated(Hero hero){
        System.out.println("All enemies in the room with the target are defeated." + hero.getName() + " can now reach the target.");
    }

    public void enemyHasEnteredTheHeroRoom(Enemy enemy){
        System.out.println(enemy.getName() + " has entered the room with the hero and attacked the hero.");
    }

    public void selectAnotherRoom(){
        System.out.println("Select another room.");
    }

    public void heroWillStartRoom(Room selectedRoom){
        clearConsole();
        System.out.println("Hero will start the game at the room: " + selectedRoom.getRoomName());
    }

    public void noEnemyEnteredRoom(){
        System.out.println("No enemies have entered the room");
    }
}
