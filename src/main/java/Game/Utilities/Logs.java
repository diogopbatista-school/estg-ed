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
     * The constructor for the Logs class
     */
    public Logs() {

        this.manualsimulationlogs = new LinkedOrderedList<>();
    }


    /**
     * Method that adds a manual simulation log to the logs
     *
     * @param manualsimulationlog The manual simulation log to add
     */
    public void addManualSimulationLog(ManualSimulationLog manualsimulationlog) {
        manualsimulationlogs.add(manualsimulationlog);
    }

    /**
     * Method that returns the manual simulation logs
     *
     * @return The manual simulation logs
     */
    public OrderedListADT<ManualSimulationLog> getManualSimulationLogs() {
        return manualsimulationlogs;
    }
}
