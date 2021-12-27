package nl.jixxed.eliteodysseymaterials.enums;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import nl.jixxed.eliteodysseymaterials.domain.LevelValue;

import java.util.Map;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Suit.class, name = "SUIT"),
        @JsonSubTypes.Type(value = Weapon.class, name = "WEAPON"),
})
public interface Equipment {
    String getImage();

    String getLocalizationKey();

    Map<Stat, Object> getStats();

    LevelValue getRecipes();
}
