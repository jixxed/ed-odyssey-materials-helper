package nl.jixxed.eliteodysseymaterials.enums;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SuitModification.class, name = "SUITMOD"),
        @JsonSubTypes.Type(value = WeaponModification.class, name = "WEAPONMOD"),
})
public interface Modification {
    String getImage();

    String getLocalizationKey();

    OdysseyBlueprintName getRecipe();
}
