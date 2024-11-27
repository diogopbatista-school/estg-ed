package Game.CustomCollection;

import Collections.Graphs.NetworkADT;

public interface ExtendedNetworkADT<T> extends NetworkADT<T> {

    public void setRoomWeight(T vertex, double weight);


}
