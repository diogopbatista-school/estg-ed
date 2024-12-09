package Game.PlayGame;


import Game.Exceptions.*;
import Game.Interfaces.*;

import java.util.Iterator;

/**
 * Represents the game rules of the game with the different scenarios and logics
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class GameRule {

    /**
     * The combat class
     */
    private final Combat combat;

    /**
     * The print class
     */
    private final Print print;

    /**
     * Constructor for the game rules
     *
     * @param print the print class
     * @param menu  the menu class
     */
    public GameRule(Print print, Menu menu) {
        this.combat = new Combat(print, menu);
        this.print = print;
    }

    /**
     * Method that represents the items scenario in the game
     *
     * @param movedRoom the room where the hero is
     * @param hero      the hero to collect the items
     */
    protected void itemsScenario(Room movedRoom, Hero hero) {
        if (!movedRoom.getEnemies().isEmpty())
            print.fightOverItemsToCollect(movedRoom);
        try {
            movedRoom.removeItems(hero);
        } catch (ItemException | HeroException | RoomException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method that represents the first scenario of the game
     * Scenario 1: Confrontation upon entering the room
     * - Tó Cruz enters the room and gains attack priority.
     * - Player phase:
     * - Tó Cruz's attack deals simultaneous damage to all enemies in the room.
     * - Enemy phase:
     * - If the enemies are eliminated, the turn ends with no additional enemy movements.
     * - If not eliminated, all other enemies in the building move randomly, except
     * those in the current room, which remain stationary until the combat ends.
     * - Turn end:
     * - The turn ends with Tó Cruz still in the room. If Tó Cruz loses all health points, the game ends.
     *
     * @param map         the map of the game
     * @param hero        the hero to play the game
     * @param movedRoom   the room where the hero is and the scenario is played
     * @param isAutomatic if the fight is automatic or not
     * @throws EnemyException if an enemy exception occurs ( removed from the room and add in other room)
     */
    protected void sceneryOne(Map map, Hero hero, Room movedRoom, boolean isAutomatic) throws EnemyException {

        while (hero.isAlive() && movedRoom.isThereAnEnemyAlive()) {
            combat.fight(movedRoom, isAutomatic);

            if (movedRoom.isThereAnEnemyAlive()) {
                print.movedEnemies();
                map.mapShuffle();
                print.movedEnemies();
            } else if (movedRoom.hasItems()) {
                itemsScenario(movedRoom, hero);
            }
        }
        print.fightOverNoItemsToCollect();
    }

    /**
     *Method that represents the second scenario of the game
     * Scenario 2: No enemies in the room
     * - Player phase:
     *   - Tó Cruz enters the room and finds no enemies.
     * - Enemy phase:
     *   - All enemies in the building move randomly.
     * - Turn end:
     *   - The turn ends, and the next turn begins, allowing the player to choose a new action.
     *
     * @param map the map of the game
     * @param movedRoom the room where the hero is and the scenario is played
     * @param targetRoom the room where the hero needs to reach
     * @param hero the hero to play the game
     * @param isAutomatic if the fight is automatic or not
     * @throws EnemyException if an enemy exception occurs ( removed from the room and add in other room)
     * @throws TargetException if the target is invalid or null
     */
    protected void sceneryTwo(Map map, Room movedRoom, Room targetRoom, Hero hero, boolean isAutomatic) throws EnemyException, TargetException {
        if (!movedRoom.isThereAnEnemyAlive()) {
            print.noEnemiesInRoomStartMoving();
            map.mapShuffle();

            if (movedRoom.isThereAnEnemyAlive()) {
                Iterator<Enemy> enemies = movedRoom.getEnemies().iterator();
                while (enemies.hasNext()) {
                    Enemy enemy = enemies.next();
                    sceneryThree(enemy, hero);
                }
                sceneryOne(map, hero, movedRoom, isAutomatic);
            } else {
                print.noEnemyEnteredRoom();
            }

            if (movedRoom.hasItems()) {
                itemsScenario(movedRoom, hero);
            }

            if (movedRoom.equals(targetRoom)) {
                scenerySix(map, movedRoom, hero, isAutomatic);
            }
        } else {
            sceneryOne(map, hero, movedRoom, isAutomatic);
        }
    }


    /**
     * Method that represents the third scenario of the game
     * Scenario 3: Enemies move into Tó Cruz's room
     * - Enemy phase:
     *   - After their movement, enemies enter the room where Tó Cruz is located.
     * - Trigger:
     *   - Scenario 1 is activated, but enemies gain attack priority.
     * @param enemy
     * @param hero
     */
    protected void sceneryThree(Enemy enemy, Hero hero) {
        print.enemyHasEnteredTheHeroRoom(enemy);

        combat.attackTheHero(enemy, hero);

    }

    /**
     * Method that represents the fourth scenario of the game
     Scenario 4: Tó Cruz uses recovery items
     * - Player phase:
     *   - If Tó Cruz chooses to use a health kit to heal, he cannot move during this phase.
     *   - Using health kits consumes Tó Cruz's player phase, even during combat.
     *
     * @param map the map of the game
     * @param hero the hero to play the game
     * @param currentRoom the room where the hero is and the scenario is played
     * @throws EnemyException if an enemy exception occurs ( removed from the room and add in other room)
     */
    protected void sceneryFour(Map map , Hero hero , Room currentRoom) throws EnemyException {
        Item item = hero.useItem();
        print.heroUsedItem(hero, item);
        print.enemyTurn();
        print.movedEnemies();
        map.mapShuffle();
        System.out.println("------------------------------------------------");
        if (hero.getCurrentRoom().isThereAnEnemyAlive()) {
            Iterator<Enemy> enemies = currentRoom.getEnemies().iterator();
            while(enemies.hasNext()){
                Enemy enemy = enemies.next();
                if(enemy.isAlive()){
                    sceneryThree(enemy, hero);
                }
            }
            sceneryOne(map, hero, currentRoom, false);
        }
    }

    /**
     * Method that represents the fifth scenario of the game
     * Scenario 5: Tó Cruz encounters the target but there are enemies in the room
     * - Player phase:
     *   - Tó Cruz prioritizes confrontation. He must eliminate all enemies in the room before interacting with the target (e.g., hostage rescue or valuable item).
     * - Enemy phase:
     *   - Other enemies in the building move according to their movement rules.
     * - Turn end:
     *   - The next turn begins with Tó Cruz still in the room with the target after eliminating the enemies.
     *
     * @param map the map of the game
     * @param movedRoom the room where the hero is and the scenario is played
     * @param hero the hero to play the game
     * @param isAutomatic if the fight is automatic or not
     * @throws EnemyException if an enemy exception occurs ( removed from the room and add in other room)
     * @throws TargetException if the target is invalid or null
     */
    protected void sceneryFive(Map map, Room movedRoom, Hero hero, boolean isAutomatic) throws EnemyException, TargetException {
        print.enemiesInTheRoomTarget();

        sceneryOne(map, hero, movedRoom, isAutomatic);

        if (hero.isAlive() && !movedRoom.isThereAnEnemyAlive()) {
            print.allEnemiesRoomTargetDefeated();
            scenerySix(map, movedRoom, hero, isAutomatic);
        }
    }

    /**
     * Method that represents the sixth scenario of the game
     * Scenario 6: Tó Cruz encounters the target without enemies
     * - Player phase:
     *   - If Tó Cruz enters the room with the target and no enemies are present, he can interact with the target
     *     (e.g., rescue the hostage, recover the item, or disarm the weapon).
     * - Mission success:
     *   - The mission is completed successfully if Tó Cruz exits the building alive.
     *
     * @param map the map of the game
     * @param movedRoom the room where the hero is and the scenario is played
     * @param hero the hero to play the game
     * @param isAutomatic if the fight is automatic or not
     * @throws TargetException if the target is invalid or null
     * @throws EnemyException if an enemy exception occurs ( removed from the room and add in other room)
     */
    protected void scenerySix(Map map, Room movedRoom, Hero hero, boolean isAutomatic) throws TargetException, EnemyException {
        if (movedRoom.getTarget() == null) {
            return;
        }

        map.mapShuffle();

        if (movedRoom.isThereAnEnemyAlive()) {
            sceneryOne(map, hero, movedRoom, isAutomatic);
        }

        hero.setTarget(movedRoom.getTarget());
        movedRoom.removeTarget(movedRoom.getTarget());

        print.reachedTarget();
    }


    /**
     * Method that checks if the game over or won by checking if the hero reached the exit room with the target or not
     * @param hero the hero to play the game
     * @param movedRoom the room where the hero is and the scenario is played
     * @return true if the game is over or won, false otherwise
     */
    protected boolean checkEndGame(Hero hero, Room movedRoom) {
        if (movedRoom.isInAndOutRoom()) {
            if (hero.doesHeroHaveTarget()) {
                print.gameWonHeroReachedExit(hero);
            } else {
                print.gameOverHeroReachedExit();
            }
            return true;
        }
        return false;
    }


}


