package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.Equipment;
import nl.jixxed.eliteodysseymaterials.enums.Modification;

import java.util.function.BooleanSupplier;

@Data
@NoArgsConstructor
public class Loadout {
    private Equipment equipment;
    private Integer currentLevel;
    private Integer targetLevel;
    private Modification[] modifications;
    private boolean showChanged;

    public Loadout(final Equipment equipment, final Integer currentLevel, final Integer targetLevel) {
        this(equipment, new Modification[4], currentLevel, targetLevel);
    }

    public Loadout(final Equipment equipment, final Modification[] modifications, final Integer currentLevel, final Integer targetLevel) {
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

    public void setModifications(final Modification[] modifications) {
        validate(() -> modifications.length != 4, "Modifications must have length of 4");
        this.modifications = modifications;
    }

    private void validate(final BooleanSupplier check, final String errorMessage) {
        if (check.getAsBoolean()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

}
