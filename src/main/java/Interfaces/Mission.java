package Interfaces;

import Game.Exceptions.*;

public interface Mission {

    String getCode();

    int getVersion();

    /**
     *
     * FALTA FAZER O ROOMS E AS SUAS LIGACOES
     *
     *
     */


    /**
     * Adds an enemy to the specified room
     * @param room the room to add the enemy to
     * @param enemy the enemy to be added
     * @throws EnemyException if the room or enemy is null
     */
    void addEnemy(Room room, Enemy enemy) throws EnemyException;

    /**
     * Adds a target to the specified room
     * @param room the room to add the target to
     * @param target the target to be added
     * @throws TargetException if the room or target is null
     */
    void addTarget(Room room, Target target) throws TargetException;

    /**
     * Adds an item to the specified room
     * @param room the room to add the item to
     * @param item the item to be added
     * @throws ItemException if the room or item is null
     */
    void addItem(Room room, Item item) throws ItemException;

    /**
     * Sets whether a room is an entrance/exit
     * @param room the room to update
     * @param isInAndOut boolean value indicating if the room is an entrance/exit
     * @throws RoomException if the room is null
     */
    void setRoomInAndOut(Room room, Boolean isInAndOut) throws RoomException;
}