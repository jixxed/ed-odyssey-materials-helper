package nl.jixxed.eliteodysseymaterials.enums;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialTraderStationJson {

    private String name;
    private Double distanceToArrival;
    private Double varianceToArrival;
    private String primaryEconomy;
    private String secondaryEconomy;
    private String type;
    private Long id;
}
