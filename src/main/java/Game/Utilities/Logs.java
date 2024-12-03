package Game.Utilities;

import Collections.Lists.LinkedOrderedList;
import Collections.Lists.LinkedUnorderedList;
import Collections.Lists.OrderedListADT;
import Collections.Lists.UnorderedListADT;

public class Logs {

    private OrderedListADT<Log> logs;

    private UnorderedListADT<ManualSimulationLog> manualsimulationlogs;

    public Logs(){
        this.logs = new LinkedOrderedList<>();
        this.manualsimulationlogs = new LinkedUnorderedList<>();
    }

    public void addLog(Log log){
        this.logs.add(log);
    }

    public void addManualSimulationLog(ManualSimulationLog manualsimulationlog){
        this.manualsimulationlogs.addToFront(manualsimulationlog);
    }

    public UnorderedListADT<ManualSimulationLog> getManualSimulationLogs(){
       return manualsimulationlogs;
    }

}
