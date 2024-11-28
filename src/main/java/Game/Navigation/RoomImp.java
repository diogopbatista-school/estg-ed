package Game.Navigation;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Lists.ArrayUnorderedList;
import Collections.Lists.LinkedUnorderedList;
import Collections.Lists.UnorderedListADT;
import Collections.Queues.LinkedQueue;
import Collections.Queues.QueueADT;
import Game.Enumerations.ItemType;
import Game.Exceptions.*;
import Interfaces.*;

import java.util.Iterator;

public class RoomImp implements Room {

    private UnorderedListADT<Enemy> enemies;
    private Hero hero;
    private Target target;
    private UnorderedListADT<Item> items;
    private String roomName;
    private Boolean heroHasAttackPriority;
    private int totalRoomPower;
    private Boolean isInAndOut;

    public RoomImp(String roomName) {
        this.enemies = new LinkedUnorderedList<>();
        this.target = null;
        this.items = new ArrayUnorderedList<>();
        this.hero = null;
        this.roomName = roomName;
        this.heroHasAttackPriority = true;
        this.totalRoomPower = 0;
        this.isInAndOut = false;
    }

    /**
     * Sets the boolean flag indicating if the room is an in and out room
     */
    public void setEntryAndExit(){
        this.isInAndOut = true;
    }

    /**
     * Getter for the room's power
     * @return the room's power
     */
    public int getRoomPower(){
        return this.totalRoomPower;
    }

    /**
     * Adds a target to the room
     * @param target the target to add
     */
    public void addTargetToRoom(Target target) throws TargetException {
        this.target = target;
    }

    @Override
    public void addEnemy(Enemy enemy) throws EnemyException {

        if(enemy == null){
            throw new EnemyException("Enemy cannot be null");
        }

        this.totalRoomPower += enemy.getAttackPower();

        if(this.enemies.isEmpty()){
            this.enemies.addToFront(enemy);
            return;
        }

        this.enemies.addAfter(enemy, this.enemies.last());
    }


    @Override
    public void addHero(Hero hero) throws HeroException {
        if(hero == null){
            throw new HeroException("Hero cannot be null");
        }
        this.hero = hero;
    }

    @Override
    public void removeHero(){
        this.hero = null;
    }


    @Override
    public void removeTarget(Target target) throws TargetException {
        if(target == null){
            throw new TargetException("Target cannot be null");
        }

        this.target = null;
    }

    @Override
    public void addItem(Item item) throws ItemException {
        if(item == null){
            throw new ItemException("Item cannot be null");
        }
        this.items.addToFront(item);
    }

    @Override
    public void removeItem(Item itemToRemove, Hero hero) throws ItemException { // iterar todos os items da sala

        if (itemToRemove == null || itemToRemove.getType() == ItemType.UNKNOWN) {
            throw new ItemException("Item cannot be null/unknown");
        }

        if (hero.isBackPackFull() && itemToRemove.getType().equals(ItemType.POTION)) {
            return;
        }else{
            Item removedItem = removeItemByType(itemToRemove);
            hero.addToBackPack(removedItem);
        }

        if(itemToRemove.getType().equals(ItemType.ARMOR)){
            hero.healArmor(itemToRemove.getPoints());
            return;
        }

    }

    private Item removeItemByType(Item itemToRemove) {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getType().equals(itemToRemove.getType())) {
                iterator.remove();
                return item;
            }
        }
        return null;
    }

    /**
     * Simulates a fight between the hero and the enemies in the room. The fight follows
     * a turn-based mechanism where either the hero or enemies have the attack priority.
     * If no enemies are present or if all enemies are dead, a message is printed and the
     * method returns. The method handles the following steps:
     * - The hero attacks first if they have the attack priority.
     * - The enemies attack if the hero does not have the attack priority.
     * - Checks if the hero is dead after each attack phase.
     * - Continues the fight recursively until all enemies are dead or the hero dies.
     * - If there is at least one enemy alive after the Hero's attack, then calls method shuffle();
     */
    @Override
    public void fight() {
        if (enemies == null || allEnemiesDead()) {
            System.out.println("No Enemies in the room to fight.");
            return;
        }

        // Prioridade de ataque: Hero ataca primeiro
        if (heroHasAttackPriority) {
            heroPhase();
        } else {
            enemiesPhase();
        }

        // Verificação se o herói está morto
        if (hero.getHealth() <= 0) {
            System.out.println("Tó Cruz has died. Game Over.");
            return;
        }

        // Se ainda houver inimigos vivos, continuar a lutar
        if (!allEnemiesDead()) {
            /**
             * meter o shuffle() aqui /
             * IMPORTANTE METER REGRA NO SHUFFLE!: se um inimigo entrar num room onde o hero esteja
             * entao definir o heroHasAttackPriority do respetivo room para false
             */
            fight(); // chamada recursiva até que todos os inimigos estejam mortos
        } else {
            System.out.println("All enemies defeated in the room.");
        }
    }

    /**
     * Executes the hero's phase during a turn-based fight.
     * The method handles the hero's attack on all enemies in the room.
     * If all enemies are defeated after the attack, it prints a victory message.
     * Otherwise, it sets the attack priority to the enemies for the next phase.
     */
    private void heroPhase() {
        System.out.println("Hero's turn to attack.");
        Iterator<Enemy> iterator = enemies.iterator();

        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            enemy.setHealth(enemy.getHealth() - hero.getAttackPower());
        }

        if (allEnemiesDead()) {
            System.out.println("All enemies have been defeated by Tó Cruz.");
        } else {
            heroHasAttackPriority = false;
        }
    }

    /**
     * Executes the enemies' phase during a turn-based fight.
     * This method handles the enemies' attack on the hero. Each enemy in the room
     * attacks the hero by reducing the hero's health based on the enemy's attack power.
     * If there are still enemies alive after the attack, the attack priority is set
     * to the hero for the next phase.
     */
    private void enemiesPhase() {
        System.out.println("Enemies' turn to attack.");
        Iterator<Enemy> iterator = enemies.iterator();

        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            hero.setHealth(hero.getHealth() - enemy.getAttackPower());
        }

        heroHasAttackPriority = true;

        System.out.println("Tó Cruz's current health: " + hero.getHealth());
    }

    /**
     * Checks if all enemies in the room are dead.
     *
     * @return true if all enemies are dead, false otherwise
     */
    private boolean allEnemiesDead() {
        Iterator<Enemy> iterator = enemies.iterator();

        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (!enemy.isDead()) {
                return false;
            }
        }

        return true;
    }

    public int getTotalRoomPower(){
        return this.totalRoomPower;
    }

    @Override
    public String getRoomName() {
        return this.roomName;
    }

    
    /**
     * Sets the boolean flag indicating if the room is an in and out room.
     * @param isInAndOut Boolean value to set the room as in and out (true) or not (false)
     */
    @Override
    public void setInAndOut(Boolean isInAndOut) {
        this.isInAndOut = isInAndOut;
    }

    /**
     * Checks if the room is an in and out room.
     * @return true if the room is an in and out, false otherwise.
     */
    @Override
    public boolean isIsAndOut() {
        return isInAndOut;
    }
}
