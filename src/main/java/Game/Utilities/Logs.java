package Game.Utilities;

import Collections.Lists.LinkedOrderedList;
import Collections.Lists.LinkedUnorderedList;
import Collections.Lists.OrderedListADT;
import Collections.Lists.UnorderedListADT;

import java.util.Iterator;

public class Logs {

    private final OrderedListADT<LogExceptions> exceptionlogs;

    private ManualSimulationLog manualsimulationlogs;

    public Logs(){
        this.exceptionlogs = new LinkedOrderedList<>();
        this.manualsimulationlogs = null;
    }

    public void addExceptionLog(LogExceptions log){
        this.exceptionlogs.add(log);
    }

    public OrderedListADT<LogExceptions> getExceptionLogs(){
        return exceptionlogs;
    }

    public void addManualSimulationLog(ManualSimulationLog manualsimulationlog){
        this.manualsimulationlogs = manualsimulationlog;
    }

    

}
