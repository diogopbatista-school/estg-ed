package Game.Utilities;

import Collections.Lists.LinkedOrderedList;
import Collections.Lists.OrderedListADT;

public class Logs {

    OrderedListADT<Log> logs;

    public Logs(){
        this.logs = new LinkedOrderedList<>();
    }

    public void addLog(Log log){
        this.logs.add(log);
    }
}
