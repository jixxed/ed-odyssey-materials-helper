package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;

public enum Specialisation {
    STRATEGIC, DYNAMIC, FORCE, UNKNOWN;


    public String getLocalizationKey() {
        return "specialisation.name." + this.name().toLowerCase();
    }

    public EdAwesomeIcon getIcon(){
        return switch (this){
            case DYNAMIC -> EdAwesomeIcon.ONFOOT_DYNAMIC;
            case FORCE -> EdAwesomeIcon.ONFOOT_FORCE;
            case STRATEGIC -> EdAwesomeIcon.ONFOOT_STRATEGIC;
            case UNKNOWN -> EdAwesomeIcon.ONFOOT_STRATEGIC;
        };
    }
}
