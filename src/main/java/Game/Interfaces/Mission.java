package Game.Interfaces;

import Game.Exceptions.*;
import Game.Utilities.Logs;
import Game.Utilities.ManualSimulationLog;

public interface Mission {

    public Logs getLogs();

    public Map getMap();

    public void setCode(String code);

    public void setVersion(int version);

    public void addManualSimulationLog(ManualSimulationLog manualsimulationlog);


    public String getCode();

    public int getVersion();

    public void addMap(Map map);

}