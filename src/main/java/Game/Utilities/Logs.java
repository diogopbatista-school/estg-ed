package Game.Utilities;

import Collections.Lists.LinkedOrderedList;
import Collections.Lists.OrderedListADT;

/**
 * Represents the logs of the game
 *
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class Logs {

    /**
     * The logs of the manual simulation
     */
    private final OrderedListADT<ManualSimulationLog> manualsimulationlogs;

    /**
     *
     */
    public Logs() {

        this.manualsimulationlogs = new LinkedOrderedList<>();
    }


    public void addManualSimulationLog(ManualSimulationLog manualsimulationlog) {
        manualsimulationlogs.add(manualsimulationlog);
    }

    public OrderedListADT<ManualSimulationLog> getManualSimulationLogs() {
        return manualsimulationlogs;
    }
}
