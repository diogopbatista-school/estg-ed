
package Game.Entities;

import Game.Interfaces.Character;
import Game.Interfaces.Hero;

public abstract class CharacterImp implements Character {
    protected String name;
    protected int health;
    protected int attackPower;
    protected boolean isInFight;

    public CharacterImp(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.isInFight = false;
    }

    @Override
    public void setInFight(boolean inFight) {
        this.isInFight = inFight;
    }

    @Override
    public boolean isInFight() {
        return this.isInFight;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(int health) throws IllegalArgumentException {
        if (health < 0) {
            throw new IllegalArgumentException("Health must be positive");
        }
        this.health = health;
    }

    @Override
    public int getAttackPower() {
        return this.attackPower;
    }

    @Override
    public void setAttackPower(int attackPower) throws IllegalArgumentException {
        if (attackPower < 0) {
            throw new IllegalArgumentException("Attack power must be positive");
        }
        this.attackPower = attackPower;
    }

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
}