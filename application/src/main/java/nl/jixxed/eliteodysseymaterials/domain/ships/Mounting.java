package nl.jixxed.eliteodysseymaterials.domain.ships;

public enum Mounting {
    FIXED,
    GIMBALLED,
    TURRETED,
    NA;

    public String getShortName(){
        return switch (this){
            case FIXED -> "F";
            case GIMBALLED -> "G";
            case TURRETED -> "T";
            case NA -> "-";
        };
    }
}
