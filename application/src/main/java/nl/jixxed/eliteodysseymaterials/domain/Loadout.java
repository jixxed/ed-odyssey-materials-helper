/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.Equipment;

import java.util.Arrays;
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

    Loadout cloneLoadout() {
        final Loadout newLoadout = new Loadout();
        newLoadout.currentLevel = this.currentLevel;
        newLoadout.targetLevel = this.targetLevel;
        newLoadout.equipment = this.equipment;
        newLoadout.showChanged = this.showChanged;
        newLoadout.modifications = Arrays.copyOf(this.modifications, 4);
        return newLoadout;
    }
}
