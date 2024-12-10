
package Game.Entities;

import Game.Interfaces.Character;
import Game.Interfaces.Hero;

/**
 * Represents a character in the game
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public abstract class CharacterImp implements Character {

    /**
     * The name of the character
     */
    protected String name;

    /**
     * The health of the character
     */
    protected double health;

    /**
     * The attack power of the character
     */
    protected double attackPower;

    /**
     * Boolean value indicating if the character is in fight
     */
    protected boolean isInFight;

    /**
     * Constructor for the character
     *
     * @param name        the name of the character
     * @param health      the health of the character
     * @param attackPower the attack power of the character
     */
    public CharacterImp(String name, double health, double attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.isInFight = false;
    }


    /**
     * Getter for the isInFight attribute
     *
     * @return the value of the isInFight attribute
     */
    @Override
    public boolean isInFight() {
        return this.isInFight;
    }

    /**
     * Getter for the name
     *
     * @return the name of the character
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the health
     *
     * @return the health of the character
     */
    @Override
    public double getHealth() {
        return this.health;
    }

    /**
     * Setter for the health
     *
     * @param health the health of the character
     * @throws IllegalArgumentException if health is negative
     */
    @Override
    public void setHealth(double health) throws IllegalArgumentException {
        if (health < 0) {
            throw new IllegalArgumentException("Health must be positive");
        }
        this.health = health;
    }

    /**
     * Getter for the attack power
     *
     * @return the attack power of the character
     */
    @Override
    public double getAttackPower() {
        return this.attackPower;
    }


    /**
     * Attacks another character .
     *
     * @param character the character to attack
     * @throws IllegalArgumentException if the character is invalid
     */
    @Override
    public void attack(Character character) throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException("Character must be valid");
        }

        double attackPower = this.getAttackPower();

        if (character instanceof Hero hero) {
            double armor = hero.getArmorHealth();

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

        double damage = character.getHealth() - attackPower;

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