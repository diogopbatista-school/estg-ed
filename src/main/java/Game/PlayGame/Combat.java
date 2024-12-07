package Game.PlayGame;

import Collections.Lists.UnorderedListADT;
import Game.Interfaces.Enemy;
import Game.Interfaces.Hero;
import Game.Interfaces.Item;
import Game.Interfaces.Room;

import java.util.Iterator;


public class Combat {

    private final Print print;
    private final Menu menu;

    public Combat(Input input, Menu menu){
        this.print = new Print();
        this.menu = menu;
    }

    public void fight(Room movedRoom, boolean isAutomatic) {
        UnorderedListADT<Enemy> enemies = movedRoom.getEnemies();
        Hero hero = movedRoom.getHero();

        if (enemies == null ||  !movedRoom.isThereAnEnemyAlive()) {
            System.out.println("No Enemies in the room to fight.");
            return;
        }

        if(!isAutomatic) {
            heroTurnManually(hero, movedRoom);
        }else{
            heroTurnAutomatically(hero, movedRoom);
        }

        if (movedRoom.isThereAnEnemyAlive()) {
            attackTheHero(hero, movedRoom);
            if(!hero.isAlive()){
                print.gameOverHeroDead();
            }
        }else{
            print.heroKilledAllEnemies();
        }


    }

    private void heroTurnManually(Hero hero, Room movedRoom) {
        int actionChoice;
        boolean hasItems = hero.isItemsOnBackPack();

        print.heroTurn();

        actionChoice = menu.fightOrUseItem(hasItems);

        while (actionChoice == 2) {
            print.heroChoicedUseItem();

            int heroHealth = hero.getHealth();
            int maxHealth = 100; // Max health is always 100
            Item item = hero.getItemFirstItem();
            int itemPoints = item.getPoints();

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

    private void heroTurnAutomatically(Hero hero , Room movedRoom){
        print.heroTurn();

        if (hero.getHealth() < 50 && hero.isItemsOnBackPack()) {
            hero.useItem();
            System.out.println("Hero used an item. Hero health: " + hero.getHealth() + ", Hero armor: " + hero.getArmorHealth());
        } else {
            attackTheEnemies(hero, movedRoom);
        }
    }

    private void attackTheEnemies(Hero hero , Room movedRoom) {
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

    private void attackTheHero(Hero hero , Room movedRoom) {
        print.heroTurnToAttack();
        Iterator<Enemy> enemies = movedRoom.getEnemies().iterator();
        while (enemies.hasNext()) {
            Enemy enemy = enemies.next();
            if (enemy.isAlive()) {
                enemy.attack(hero);
                System.out.println(enemy.getName() + " attacked Hero. Hero health: " + hero.getHealth() + ", Hero armor: " + hero.getArmorHealth());
            }
        }
    }

    protected void attackTheHero(Enemy enemy , Hero hero) {
        enemy.attack(hero);
        System.out.println(enemy.getName() + " attacked Hero. Hero health: " + hero.getHealth() + ", Hero armor: " + hero.getArmorHealth());
    }
}






