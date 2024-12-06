package Game.PlayGame;

import Collections.Lists.UnorderedListADT;
import Game.Interfaces.Hero;
import Game.Interfaces.Room;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {

    private final Scanner scanner;

    public Input() {
        this.scanner = new Scanner(System.in);
    }


    protected int chooseMapValidationInput(UnorderedListADT<String> missionCodes) {
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
        return missionChoice;
    }

    protected int getValidMoveInput(Hero hero) {
        int selectNextMove = getValidNumberInput(1, 2);

        while (selectNextMove == 2 && !hero.isItemsOnBackPack()) {
            System.out.println("Invalid choice. You don't have any items to use.");
            System.out.println("1. Move to another room");
            selectNextMove = getValidNumberInput(1, 1);
        }

        return selectNextMove;
    }

    protected int getValidNumberInput(int min, int max) {
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

    protected int chooseNextRoom(UnorderedListADT<Room> connectedRooms){
        int roomChoice = scanner.nextInt();
        while (roomChoice < 1 || roomChoice > connectedRooms.size()) {
            System.out.println("Invalid choice. Please try again.");
            System.out.print("Select a connected room by number: ");
            roomChoice = scanner.nextInt();
        }
        return roomChoice;
    }

    protected int chooseHeroAction(boolean hasItems) {
        int actionChoice;
        do {
            System.out.print("Choose an action (1: Attack, 2: Use Item): ");
            actionChoice = scanner.nextInt();
            if (actionChoice != 1 && (!hasItems || actionChoice != 2)) {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (actionChoice != 1 && (!hasItems || actionChoice != 2));
        return actionChoice;
    }

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
                scanner.next();
            }
        }
        return value;
    }

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
                scanner.next();
            }
        }
        return roomChoice;
    }

    public int handleItemUsage(String message, String prompt) {
        int actionChoice;
        while (true) {
            System.out.println(message);
            String choice = scanner.next().toLowerCase();
            if (!choice.equals("yes")) {
                actionChoice = getValidNumberInput(1, 2);
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

    public boolean confirmRoomEntry(String message) {
        System.out.println(message);
        String choice = scanner.next().toLowerCase();
        return choice.equals("yes");
    }

}
