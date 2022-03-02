package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.Equipment;

import java.util.function.BooleanSupplier;

@Data
@NoArgsConstructor
public class Loadout {
    private Equipment equipment;
    @ClipboardJsonIgnore
    private Integer currentLevel = 1;
    @ClipboardJsonIgnore
    private Integer targetLevel = 5;
    @JsonDeserialize(using = SelectedModificationDeserializer.class)
    private SelectedModification[] modifications;
    @ClipboardJsonIgnore
    private boolean showChanged = false;

    public Loadout(final Equipment equipment, final Integer currentLevel, final Integer targetLevel) {
        this(equipment, new SelectedModification[4], currentLevel, targetLevel);
    }

    public Loadout(final Equipment equipment, final SelectedModification[] modifications, final Integer currentLevel, final Integer targetLevel) {
        validate(() -> modifications.length > 4, "More than 4 modifications supplied");
        validate(() -> currentLevel < 1 || currentLevel > 5, "Current level must be between 1 and 5");
        validate(() -> targetLevel < 1 || targetLevel > 5, "Target level must be between 1 and 5");
        this.equipment = equipment;
        this.modifications = modifications;
        this.currentLevel = currentLevel;
        this.targetLevel = targetLevel;
    }

    public void setCurrentLevel(final Integer currentLevel) {
        validate(() -> currentLevel < 1 || currentLevel > 5, "Current level must be between 1 and 5");
        this.currentLevel = currentLevel;
    }

    public void setTargetLevel(final Integer targetLevel) {
        validate(() -> targetLevel < 1 || targetLevel > 5, "Target level must be between 1 and 5");
        this.targetLevel = targetLevel;
    }

    public void setModifications(final SelectedModification[] modifications) {
        validate(() -> modifications.length != 4, "Modifications must have length of 4");
        this.modifications = modifications;
    }

    private void validate(final BooleanSupplier check, final String errorMessage) {
        if (check.getAsBoolean()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

}
