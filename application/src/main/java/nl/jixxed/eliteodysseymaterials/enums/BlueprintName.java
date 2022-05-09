package nl.jixxed.eliteodysseymaterials.enums;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = HorizonsBlueprintName.class, name = "horizons"),
        @JsonSubTypes.Type(value = OdysseyBlueprintName.class, name = "odyssey")
})
public interface BlueprintName<E extends BlueprintName> extends Comparable<E> {
    String getLocalizationKey();

    String getDescriptionLocalizationKey();
}
