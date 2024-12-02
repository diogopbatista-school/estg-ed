package Game.Enumerations;

import Game.Exceptions.ItemException;

/**
 * Represents the type of an item
 */
public enum ItemType {
    COLETE, KIT_DE_VIDA,UNKNOWN;


    
    public String toString(){
        if(this == COLETE){
            return "colete";
    }
        else if(this == KIT_DE_VIDA){
            return "kit de vida";
        }
        return "Unknown";
    }

    public static ItemType fromString(String type) throws ItemException {
        try {
            return ItemType.valueOf(type.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            throw new ItemException("Invalid item type: " + type);
        }
    }
}


