package nl.jixxed.eliteodysseymaterials.enums;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TechnologyBrokerStationJson {

    private String name;
    private Double distanceToArrival = 0D;
    private Double varianceToArrival = 0D;
    private String primaryEconomy;
    private String secondaryEconomy;
    private String type;
    private Long id;
}
