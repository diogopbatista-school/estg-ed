
package Game.Entities;

import Game.Interfaces.Enemy;
import Game.Interfaces.Room;
import Game.Navigation.RoomImp;

public class EnemyImp extends CharacterImp implements Enemy {

    private Room currentRoom;

    public EnemyImp(String name, int health, int attackPower, Room currentRoom) {
        super(name, health, attackPower);
        this.currentRoom = currentRoom;
    }

    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    @Override
    public boolean isDead() {
        return this.health <= 0;
    }

    @Override
    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name must be valid");
        }
        this.name = name;
    }
}