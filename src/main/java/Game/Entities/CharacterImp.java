
package Game.Entities;

import Game.Interfaces.Character;
import Game.Interfaces.Hero;

/**
 * Represents a character in the game
 */
public abstract class CharacterImp implements Character {

    /**
     * The name of the character
     */
    protected String name;

    /**
     * The health of the character
     */
    protected int health;

    /**
     * The attack power of the character
     */
    protected int attackPower;

    /**
     * Boolean value indicating if the character is in fight
     */
    protected boolean isInFight;

    /**
     * Constructor for the character
     * @param name the name of the character
     * @param health the health of the character
     * @param attackPower the attack power of the character
     */
    public CharacterImp(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.isInFight = false;
    }

    /**
     * Setter for the isInFight attribute
     * @param inFight the value to set
     */
    @Override
    public void setInFight(boolean inFight) {
        this.isInFight = inFight;
    }

    /**
     * Getter for the isInFight attribute
     * @return the value of the isInFight attribute
     */
    @Override
    public boolean isInFight() {
        return this.isInFight;
    }

    /**
     * Getter for the name
     * @return
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the health
     * @return the health of the character
     */
    @Override
    public int getHealth() {
        return this.health;
    }

    /**
     * Setter for the health
     * @param health the health of the character
     * @throws IllegalArgumentException if health is negative
     */
    @Override
    public void setHealth(int health) throws IllegalArgumentException {
        if (health < 0) {
            throw new IllegalArgumentException("Health must be positive");
        }
        this.health = health;
    }

    /**
     * Getter for the attack power
     * @return the attack power of the character
     */
    @Override
    public int getAttackPower() {
        return this.attackPower;
    }

    /**
     * Setter for the attack power
     * @param attackPower the attack power of the character
     * @throws IllegalArgumentException if attack power is negative
     */
    @Override
    public void setAttackPower(int attackPower) throws IllegalArgumentException {
        if (attackPower < 0) {
            throw new IllegalArgumentException("Attack power must be positive");
        }
        this.attackPower = attackPower;
    }

    /**
     * Attacks another character .
     * @param character the character to attack
     * @throws IllegalArgumentException if the character is invalid
     */
    @Override
    public void attack(Character character) throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException("Character must be valid");
        }

        int attackPower = this.getAttackPower();

        if (character instanceof Hero) {
            Hero hero = (Hero) character;
            int armor = hero.getArmorHealth();

            if (armor > 0) {
                if (armor >= attackPower) {
                    hero.setArmorHealth(armor - attackPower);
                    return;
                } else {
                    attackPower -= armor;
                    hero.setArmorHealth(0);
                }
            }
        }

        int damage = character.getHealth() - attackPower;

        if (damage < 0) {
            character.setHealth(0);
        } else {
            character.setHealth(damage);
        }
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }
}