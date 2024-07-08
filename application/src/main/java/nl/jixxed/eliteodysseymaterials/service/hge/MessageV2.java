package nl.jixxed.eliteodysseymaterials.service.hge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import nl.jixxed.eliteodysseymaterials.enums.Allegiance;
import nl.jixxed.eliteodysseymaterials.enums.Economy;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterialType;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageV2 {
    private String timestamp;//always
    private String event;//always
    //location
    private String system;//always
    private Double[] starPos;//always
    private Allegiance systemAllegiance;//always
    private Long population;//always
    //faction
    private String faction;//fss
    //system
    private Set<Economy> systemEconomies;//always
    private Economy primaryEconomy;//always
    private Economy secondaryEconomy;//always
    private Set<FactionV2> factions; //always
    //data
    private HorizonsMaterialType category; //lastFound
}