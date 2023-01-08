package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Data;

@Data
public class TechnologyBrokerJson {

    private String name;
    private MaterialTraderCoordsJson coords;
    private MaterialTraderStationJson station;

}
