package nl.jixxed.eliteodysseymaterials.service.hge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    private String timestamp;//always
    private String event;//always
    //location
    private String system;//always
    private Double[] starPos;//always
    private String systemAllegiance;//always
    //faction
    private String faction;//fss
    private Double influence;//fss
    private String state;//fss
    private String allegiance;//fss
    private String government;//fss
    //system
    private Set<String> systemEconomies;//always
    //data
    private Set<String> materialsFound; //ussdrop
    private String category; //lastFound

    private Double timeRemaining;//fss
}