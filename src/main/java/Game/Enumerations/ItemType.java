package Game.Enumerations;

public enum ItemType {
    ARMOR,POTION,UNKNOWN;

    public String toString(){
        if(this == ARMOR){
            return "colete";
    }
        else if(this == POTION){
            return "kit de vida";
        }
        return "Unknown";
    }
}
