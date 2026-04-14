/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.*;
import lombok.experimental.SuperBuilder;
import nl.jixxed.eliteodysseymaterials.enums.HardpointGroup;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Slot {
    private static final List<String> slotMapping = List.of("U", "S", "M", "L", "H");
    @Getter
    private SlotType slotType;
    @Getter
    private int index;
    @Getter
    private int slotSize;
    private Integer namedIndex;
    @Setter
    private HardpointGroup hardpointGroup;
    @Getter
    @Setter
    private ShipModule shipModule;
    @Getter
    @Setter
    private ShipModule oldShipModule;

    public List<HorizonsBlueprintType> getEffectiveExperimentalEffects() {
        if (!this.shipModule.getModifications().isEmpty()) {
            return this.shipModule.getExperimentalEffects();
        }
        return null;
    }

    public String getSlotSizeName() {
        if (this.slotType == SlotType.HARDPOINT || this.slotType == SlotType.MINING_HARDPOINT || this.slotType == SlotType.UTILITY) {
            return LocaleService.getLocalizedStringForCurrentLocale("ships.slot.size." + slotMapping.get(this.slotSize).toLowerCase());
        }
        return String.valueOf(this.slotSize);
    }

    public Slot(final Slot slot) {
        this.slotType = slot.slotType;
        this.index = slot.index;
        this.namedIndex = slot.namedIndex;
        this.slotSize = slot.slotSize;
        if (slot.shipModule != null) {
            this.shipModule = slot.shipModule.Clone();
        }

    }
    //some ships have their last slotnumbers offset due to military slots
    public int getNamedIndex() {
        return hasNamedIndex() ? namedIndex : index + 1;
    }
    //some ships have their last slotnumbers offset due to military slots
    public boolean hasNamedIndex() {
        return namedIndex != null;
    }

    public boolean isOccupied() {
        return this.shipModule != null;
    }

    public HardpointGroup getHardpointGroup() {
        if(slotType != SlotType.HARDPOINT && slotType != SlotType.MINING_HARDPOINT) {
            return null;
        }
        return hardpointGroup;
    }
}
