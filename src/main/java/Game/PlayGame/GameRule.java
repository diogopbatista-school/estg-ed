package Game.PlayGame;


import Collections.Lists.UnorderedListADT;
import Game.Exceptions.EnemyException;
import Game.Exceptions.ItemException;
import Game.Exceptions.TargetException;
import Game.Interfaces.*;

import java.util.Iterator;

public class GameRule {

    private final Combat combat;
    private final Print print;

    public GameRule(Input input,Print print,Menu menu) {
        this.combat = new Combat(input,menu);
        this.print = print;
    }

    protected void itemsScenario(Room movedRoom , Hero hero){
        if(!movedRoom.getEnemies().isEmpty())
            print.fightOverItemsToCollect(movedRoom);
        try{
            movedRoom.removeItem(hero);
        }catch (ItemException e){
            System.out.println(e.getMessage());
        }
    }

    public void sceneryOne(Map map, Hero hero, Room movedRoom, boolean isAutomatic) throws EnemyException {

        while (hero.isAlive() && movedRoom.isThereAnEnemyAlive()) {
            combat.fight(movedRoom, isAutomatic);

            if (movedRoom.isThereAnEnemyAlive()) {
                print.movedEnemies();
                map.mapShuffle();
                print.movedEnemies();
            } else if(movedRoom.hasItems()) {
                itemsScenario(movedRoom, hero);
            }
        }
        print.fightOverNoItemsToCollect();
    }

    public void sceneryTwo(Map map, Room movedRoom,Room targetRoom, Hero hero,boolean isAutomatic) throws EnemyException, TargetException {
        if (!movedRoom.isThereAnEnemyAlive()) {
            print.noEnemiesInRoomStartMoving();
            map.mapShuffle();

            if(movedRoom.isThereAnEnemyAlive()){
                Iterator<Enemy> enemies = movedRoom.getEnemies().iterator();
                while(enemies.hasNext()){
                    Enemy enemy = enemies.next();
                    sceneryThree(enemy,hero);
                }
                sceneryOne(map, hero, movedRoom, isAutomatic);
            }else{
                print.noEnemyEnteredRoom();
            }

            if (movedRoom.hasItems()) {
                itemsScenario(movedRoom, hero);
            }

            if (movedRoom.equals(targetRoom)) {
                scenerySix(map, movedRoom, hero, isAutomatic);
            }
        }else {
            sceneryOne(map, hero ,movedRoom,isAutomatic);
        }
    }

    public void sceneryThree(Enemy enemy, Hero hero){
        print.enemyHasEnteredTheHeroRoom(enemy);

        combat.attackTheHero(enemy, hero);
        
    }

    public void sceneryFive(Map map, Room movedRoom, Hero hero, boolean isAutomatic) throws EnemyException, TargetException {
        print.enemiesInTheRoomTarget(hero);

        sceneryOne(map, hero, movedRoom, isAutomatic);

        if (hero.isAlive() && !movedRoom.isThereAnEnemyAlive()) {
            print.allEnemiesRoomTargetDefeated(hero);
            scenerySix(map, movedRoom, hero, isAutomatic);
        }
    }

    public void scenerySix(Map map, Room movedRoom, Hero hero, boolean isAutomatic) throws EnemyException, TargetException, TargetException, EnemyException {
        if(movedRoom.getTarget() == null){
            return;
        }

        map.mapShuffle();

        if(movedRoom.isThereAnEnemyAlive()){
            sceneryOne(map, hero, movedRoom,isAutomatic);
        }

        hero.setTarget(movedRoom.getTarget());
        movedRoom.removeTarget(movedRoom.getTarget());

        print.reachedTarget();
    }



    public boolean checkEndGame (Hero hero, Room movedRoom){
        if (movedRoom.isIsAndOut()) {
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


