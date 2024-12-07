package Game.Utilities;

import Collections.Lists.LinkedOrderedList;
import Collections.Lists.LinkedUnorderedList;
import Collections.Lists.OrderedListADT;
import Collections.Lists.UnorderedListADT;

import java.util.Iterator;

public class Logs {

    private final OrderedListADT<LogExceptions> exceptionlogs;

    private OrderedListADT<ManualSimulationLog> manualsimulationlogs;

    public Logs() {
        this.exceptionlogs = new LinkedOrderedList<>();
        this.manualsimulationlogs = new LinkedOrderedList<>();
    }

    public void addExceptionLog(LogExceptions log) {
        this.exceptionlogs.add(log);
    }

    public OrderedListADT<LogExceptions> getExceptionLogs() {
        return exceptionlogs;
    }

    public void addManualSimulationLog(ManualSimulationLog manualsimulationlog) {
        manualsimulationlogs.add(manualsimulationlog);
    }

    public OrderedListADT<ManualSimulationLog> getManualSimulationLogs() {
        return manualsimulationlogs;
    }
}
