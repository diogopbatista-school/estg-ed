package Game.Utilities;

import Collections.Lists.LinkedOrderedList;
import Collections.Lists.LinkedUnorderedList;
import Collections.Lists.OrderedListADT;
import Collections.Lists.UnorderedListADT;

import java.util.Iterator;

public class Logs {

    private final OrderedListADT<LogExceptions> exceptionlogs;

    private final OrderedListADT<ManualSimulationLog> manualsimulationlogs;

    public Logs(){
        this.exceptionlogs = new LinkedOrderedList<>();
        this.manualsimulationlogs = new LinkedOrderedList<>();
    }

    public void addExceptionLog(LogExceptions log){
        this.exceptionlogs.add(log);
    }

    public OrderedListADT<LogExceptions> getExceptionLogs(){
        return exceptionlogs;
    }

    public void addManualSimulationLog(ManualSimulationLog manualsimulationlog){
        this.manualsimulationlogs.add(manualsimulationlog);
    }


    public OrderedListADT<ManualSimulationLog> getManualSimulationLogs() {
        // Convert the OrderedListADT to an array for sorting
        ManualSimulationLog[] logArray = new ManualSimulationLog[manualsimulationlogs.size()];
        Iterator<ManualSimulationLog> it = manualsimulationlogs.iterator();
        int index = 0;
        while (it.hasNext()) {
            logArray[index++] = it.next();
        }

        // Selection sort the array by hero's health
        for (int i = 0; i < logArray.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < logArray.length; j++) {
                if (logArray[j].compareTo(logArray[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            ManualSimulationLog temp = logArray[minIndex];
            logArray[minIndex] = logArray[i];
            logArray[i] = temp;
        }

        OrderedListADT<ManualSimulationLog> sortedLogs = new LinkedOrderedList<>();
        for (ManualSimulationLog log : logArray) {
            sortedLogs.add(log);
        }

        return sortedLogs;
    }

}
