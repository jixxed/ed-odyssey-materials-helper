package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Data;

@Data
public class TechnologyBrokerJson {

    private String name;
    private TechnologyBrokerCoordsJson coords;
    private TechnologyBrokerStationJson station;

}
