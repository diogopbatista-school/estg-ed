package Game.PlayGame;

import Collections.Lists.UnorderedListADT;
import Game.Interfaces.Enemy;
import Game.Interfaces.Hero;
import Game.Interfaces.Item;
import Game.Interfaces.Room;

import java.util.Iterator;

/**
 * Class that represents the combat between the hero and the enemies
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class Combat {

    /**
     * The print class
     */
    private final Print print;

    /**
     * The menu class
     */
    private final Menu menu;

    /**
     * Constructor for the Combat class
     * @param print the print class
     * @param menu the menu class
     */
    public Combat(Print print, Menu menu) {
        this.print = print;
        this.menu = menu;
    }

    /**
     * Method that represents the fight between the hero and the enemies
     *
     * @param movedRoom   the room where the hero is
     * @param isAutomatic if the fight is automatic or not
     */
    public void fight(Room movedRoom, boolean isAutomatic) {
        UnorderedListADT<Enemy> enemies = movedRoom.getEnemies();
        Hero hero = movedRoom.getHero();

        if (enemies == null || !movedRoom.isThereAnEnemyAlive()) {
            System.out.println("No Enemies in the room to fight.");
            return;
        }

        if (!isAutomatic) {
            heroTurnManually(hero, movedRoom);
        } else {
            heroTurnAutomatically(hero, movedRoom);
        }

        if (movedRoom.isThereAnEnemyAlive()) {
            attackTheHero(hero, movedRoom);
            if (!hero.isAlive()) {
                print.gameOverHeroDead();
            }
        } else {
            print.heroKilledAllEnemies();
        }


    }

    /**
     * Method that represents the hero turn in the fight manually
     *
     * @param hero      the hero
     * @param movedRoom the room where the hero is
     */
    private void heroTurnManually(Hero hero, Room movedRoom) {
        int actionChoice;

        print.heroTurn();

        actionChoice = menu.fightOrUseItem(hero);

        while (actionChoice == 2) {
            print.heroChoicedUseItem();

            double heroHealth = hero.getHealth();
            int maxHealth = 100; // Max health is always 100
            Item item = hero.getItemFirstItem();
            double itemPoints = item.getPoints();

            if (heroHealth == maxHealth) {
                actionChoice = menu.handleItemMenu();
                if (actionChoice == 1) {
                    break;
                }
                continue;
            } else if (heroHealth + itemPoints > maxHealth) {
                actionChoice = menu.handleItemWasteMenu();
                if (actionChoice == 1) {
                    break;
                }
                continue;
            }
            hero.useItem();
            break;
        }

        if (actionChoice == 1) {
            print.heroTurnToAttack();
            attackTheEnemies(hero, movedRoom);
        }
    }

    /**
     * Method that represents the hero turn in the fight automatically
     *
     * @param hero      the hero
     * @param movedRoom the room where the hero is
     */
    private void heroTurnAutomatically(Hero hero, Room movedRoom) {
        print.heroTurn();

        if (hero.getHealth() <= 50 && hero.isItemsOnBackPack()) {
            hero.useItem();
            System.out.println("Hero used an item. Hero health: " + hero.getHealth() + ", Hero armor: " + hero.getArmorHealth());
        } else {
            attackTheEnemies(hero, movedRoom);
        }
    }

    /**
     * Method that represents the attack of the hero to the enemies
     *
     * @param hero      the hero
     * @param movedRoom the room where the hero is
     */
    private void attackTheEnemies(Hero hero, Room movedRoom) {
        Iterator<Enemy> enemies = movedRoom.getEnemies().iterator();
        while (enemies.hasNext()) {
            Enemy enemy = enemies.next();
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

    /**
     * Method that represents the attack of the enemies to the hero
     *
     * @param hero      the hero
     * @param movedRoom the room where the hero is
     */
    private void attackTheHero(Hero hero, Room movedRoom) {
        print.enemyTurnToAttack();
        Iterator<Enemy> enemies = movedRoom.getEnemies().iterator();
        while (enemies.hasNext()) {
            Enemy enemy = enemies.next();
            if (enemy.isAlive()) {
                enemy.attack(hero);
                System.out.println(enemy.getName() + " attacked Hero. Hero health: " + hero.getHealth() + ", Hero armor: " + hero.getArmorHealth());
            }
        }
    }

    /**
     * This method is specific for the sceneryThree , an enemy entered the hero room and attacked him
     *
     * @param enemy The enemy that entered the room and has priority of attack
     * @param hero  The hero that is attacked
     */
    protected void attackTheHero(Enemy enemy, Hero hero) {
        enemy.attack(hero);
        System.out.println(enemy.getName() + " attacked Hero. Hero health: " + hero.getHealth() + ", Hero armor: " + hero.getArmorHealth());
    }
}






