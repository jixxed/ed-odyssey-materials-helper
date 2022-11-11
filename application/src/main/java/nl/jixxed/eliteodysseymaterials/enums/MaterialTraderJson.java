package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Data;

@Data
public class MaterialTraderJson {

    private String name;
    private MaterialTraderCoordsJson coords;
    private MaterialTraderStationJson station;

}
