package Game.Mission;

import Collections.Lists.ArrayUnorderedList;

import Collections.Queues.LinkedQueue;
import Collections.Queues.QueueADT;
import Game.Exceptions.*;
import Interfaces.*;

public class MissionImp implements Mission {

    /**
     * adicionar depois uma variavel p dar storage aos rooms
     */

    private String code;
    private int version;
    private QueueADT<Enemy> enemies;
    private Target target;
    private ArrayUnorderedList<Item> items;

    /**
     * Constructor for the MissionImp class.
     * Initializes a mission with a given code and version, sets up empty lists
     * for enemies and items, and initializes the target to null.
     *
     * @param cod_mission the unique code identifying the mission.
     * @param vers_mission the version of the mission.
     */
    public MissionImp(String cod_mission,int vers_mission) {
        this.code = cod_mission;
        this.version = vers_mission;
        this.enemies = new LinkedQueue<>();
        this.items = new ArrayUnorderedList<>();
        this.target = null;
    }

    /**
     * Returns the unique code identifying the mission.
     *
     * @return the mission code
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * Returns the version of the mission.
     *
     * @return the version of the mission as an integer
     */
    @Override
    public int getVersion() {
        return version;
    }

    /**
     *
     * FALTA FAZER O METODO P ADICIONAR ROOMS E AS SUAS LIGACOES
     *
     *
     */

    /**
     * Sets whether a room is an entrance/exit
     * @param room the room to update
     * @param isInAndOut boolean value indicating if the room is an entrance/exit
     * @throws RoomException if the room is null
     */
    public void setRoomInAndOut(Room room, Boolean isInAndOut) throws RoomException {
        if (room != null) {
            room.setInAndOut(isInAndOut);
        } else {
            throw new RoomException("Room cannot be null");
        }
    }

    @Override
    public void addEnemy(Room room, Enemy enemy) throws EnemyException {
        if (room != null && enemy != null) {
            room.addEnemy(enemy);
            enemies.enqueue(enemy);
        } else {
            throw new EnemyException("Room or enemy cannot be null");
        }
    }

    @Override
    public void addTarget(Room room, Target target) throws TargetException {
        if (room != null && target != null) {
            room.addTargetToRoom(target);
            this.target = target;
        } else {
            throw new TargetException("Room or target cannot be null");
        }
    }

    @Override
    public void addItem(Room room, Item item) throws ItemException {
        if (room != null && item != null) {
            room.addItem(item);
            items.addToFront(item);
        } else {
            throw new ItemException("Room or item cannot be null");
        }
    }
}