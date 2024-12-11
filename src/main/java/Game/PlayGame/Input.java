package Game.PlayGame;

import Collections.Lists.UnorderedListADT;
import Game.Interfaces.Hero;
import Game.Interfaces.Room;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class to handle the input of the game
 */
public class Input {

    /**
     * The scanner to read input
     */
    private final Scanner scanner;

    /**
     * Constructor for the input
     */
    public Input() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Method to read a string
     * @return the string read
     */
    public String readString() {
        return scanner.nextLine();
    }

    /**
     * Method to choose a mission from the available missions
     *
     * @param missionCodes the available missions
     * @return the selected mission
     */
    protected int chooseMapValidationInput(UnorderedListADT<String> missionCodes) {
        int missionChoice = -1;
        while (missionChoice < 1 || missionChoice > missionCodes.size()) {
            try {
                missionChoice = scanner.nextInt();
                if (missionChoice < 1 || missionChoice > missionCodes.size()) {
                    System.out.println("Invalid choice. Please try again.");
                    scanner.nextLine(); // Consume the invalid input
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        return missionChoice;
    }

    /**
     * Input method to get the next move
     *
     * @param hero the hero to check if there are items to use
     * @return the next move
     */
    protected int getValidMoveInput(Hero hero) {
        int selectNextMove = getValidNumberInput(2);

        while (selectNextMove == 2 && !hero.isItemsOnBackPack()) {
            System.out.println("Invalid choice. You don't have any items to use.");
            System.out.println("1. Move to another room");
            selectNextMove = getValidNumberInput(1);
        }

        return selectNextMove;
    }

    /**
     * Method to get a valid number input
     *
     * @param max the maximum value
     * @return the valid number input
     */
    protected int getValidNumberInput(int max) {
        int choice = -1;
        while (choice < 1 || choice > max) {
            try {
                if (1 == max) {
                    System.out.print("Choose a number between " + 1 + " and " + max + ": ");
                } else {
                    System.out.print("Choose a number: ");
                }
                choice = scanner.nextInt();
                if (choice < 1 || choice > max) {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
        return choice;
    }

    /**
     * Input to choose the next room to move to from the connected rooms
     *
     * @param connectedRooms the connected rooms
     * @return the next room option to move to
     */
    protected int chooseNextRoom(UnorderedListADT<Room> connectedRooms) {
        int roomChoice = scanner.nextInt();
        while (roomChoice < 1 || roomChoice > connectedRooms.size()) {
            System.out.println("Invalid choice. Please try again.");
            System.out.print("Select a connected room by number: ");
            scanner.nextLine(); // Consume the invalid input
            roomChoice = scanner.nextInt();
        }
        return roomChoice;
    }

    /**
     * Input to choose if the hero wants to use an item or attack an enemy
     *
     * @param hasItems if the hero has items
     * @return the action choice
     */
    protected int chooseHeroAction(boolean hasItems) {
        int actionChoice;
        do {
            if (hasItems) {
                System.out.println("+---+--------+---+----------+");
                System.out.println("| 1 | Attack | 2 | Use Item |");
                System.out.println("+---+--------+---+----------+");

            } else {
                System.out.println("+---+--------+");
                System.out.println("| 1 | Attack |");
                System.out.println("+---+--------+");
            }
            actionChoice = scanner.nextInt();
            if (actionChoice != 1 && (!hasItems || actionChoice != 2)) {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (actionChoice != 1 && (!hasItems || actionChoice != 2));
        return actionChoice;
    }

    /**
     * Input to get a valid number input for the creation of the hero
     *
     * @param prompt the prompt to display
     * @return the valid positive number
     */
    protected int getValidPositiveNumber(String prompt) {
        int value = -1;
        while (value <= 0) {
            try {
                System.out.print(prompt);
                value = scanner.nextInt();
                if (value <= 0) {
                    System.out.println("Value must be greater than 0. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        return value;
    }

    /**
     * Input to get a valid room choice
     *
     * @param max the maximum room choice
     * @return the valid room choice
     */
    public int getValidRoomChoice(int max) {
        int roomChoice = -1;
        while (roomChoice < 1 || roomChoice > max) {
            try {
                System.out.print("Select a room by number: ");
                roomChoice = scanner.nextInt();
                if (roomChoice < 1 || roomChoice > max) {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        return roomChoice;
    }

    /**
     * Input to handle the usage of an item
     *
     * @param message the message to display
     * @return the action choice
     */
    public int handleItemUsage(String message) {
        int actionChoice;
        while (true) {
            System.out.println(message);
            String choice = scanner.next().toLowerCase();
            if (!choice.equals("yes")) {
                actionChoice = getValidNumberInput(2);
                if (actionChoice == 1) {
                    break;
                }
            } else {
                actionChoice = 2;
                break;
            }
        }
        return actionChoice;
    }

    /**
     * Input to confirm the room entry
     *
     * @param message the message to display
     * @return true if the room entry is confirmed, false otherwise
     */
    public boolean confirmRoomEntry(String message) {
        System.out.println(message);
        String choice = scanner.next().toLowerCase();
        return choice.equals("yes");
    }

}