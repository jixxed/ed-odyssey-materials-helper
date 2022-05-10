package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        defaultImpl = OdysseyWishlistBlueprint.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HorizonsExperimentalWishlistBlueprint.class, name = "experimental"),
        @JsonSubTypes.Type(value = HorizonsModuleWishlistBlueprint.class, name = "module"),
        @JsonSubTypes.Type(value = HorizonsSynthesisWishlistBlueprint.class, name = "synthesis"),
        @JsonSubTypes.Type(value = HorizonsTechBrokerWishlistBlueprint.class, name = "techbroker"),
        @JsonSubTypes.Type(value = HorizonsEngineerWishlistBlueprint.class, name = "engineer"),
        @JsonSubTypes.Type(value = OdysseyWishlistBlueprint.class, name = "odyssey")
})
public interface WishlistBlueprint<E extends BlueprintName<E>> {
    BlueprintName<E> getRecipeName();

    boolean isVisible();

    void setVisible(boolean visible);
}
