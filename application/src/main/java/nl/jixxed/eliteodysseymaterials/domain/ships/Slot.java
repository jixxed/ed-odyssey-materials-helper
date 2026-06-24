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
    private String fdevName;
    @Setter
    private HardpointGroup hardpointGroup;
    @Getter
    @Setter
    private ShipModule shipModule;
    @Getter
    @Setter
    private ShipModule oldShipModule;

    public Slot(final Slot slot) {
        this.slotType = slot.slotType;
        this.index = slot.index;
        this.namedIndex = slot.namedIndex;
        this.slotSize = slot.slotSize;
        this.fdevName = slot.fdevName;
        if (slot.shipModule != null) {
            this.shipModule = slot.shipModule.Clone();
        }

    }

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
        if (slotType != SlotType.HARDPOINT && slotType != SlotType.MINING_HARDPOINT) {
            return null;
        }
        return hardpointGroup;
    }

    public boolean matches(String slotName) {
        return getFdevName().equalsIgnoreCase(slotName);
    }

    public String getFdevName() {
        if (fdevName != null) {//name override for slots that do not follow convention
            return fdevName;
        }
        final String base = getFdevBaseName() + getFdevSlotIndex();
        final String suffix = (slotType == SlotType.OPTIONAL) ? "_Size" + slotSize : "";
        return base + suffix;
    }

    private String getFdevSlotIndex() {
        return switch (slotType) {
            case HARDPOINT, MINING_HARDPOINT, UTILITY -> String.format("%d", getNamedIndex());
            case OPTIONAL, MILITARY, SLF, LIMPET, CARGO, PASSENGER -> String.format("%02d", getNamedIndex());
            default -> "";
        };
    }

    private String getFdevBaseName() {
        return switch (slotType) {
            case CARGO_HATCH -> "CargoHatch";
            case HARDPOINT -> getFdevHardpointSize() + "Hardpoint";
            case MINING_HARDPOINT -> getFdevHardpointSize() + "MiningHardpoint";
            case UTILITY -> "TinyHardpoint";
            case CORE_ARMOUR -> "Armour";
            case CORE_POWER_PLANT -> "PowerPlant";
            case CORE_THRUSTERS -> "MainEngines";
            case CORE_FRAME_SHIFT_DRIVE -> "FrameShiftDrive";
            case CORE_LIFE_SUPPORT -> "LifeSupport";
            case CORE_POWER_DISTRIBUTION -> "PowerDistributor";
            case CORE_SENSORS -> "Radar";
            case CORE_FUEL_TANK -> "FuelTank";
            case OPTIONAL -> "Slot";
            case MILITARY -> "Military";
            case SLF -> "FighterBay";
            case LIMPET -> "LimpetController";
            case CARGO -> "Cargo";
            case PASSENGER -> "Passenger";
        };
    }

    private String getFdevHardpointSize() {
        return switch (slotSize) {
            case 1 -> "Small";
            case 2 -> "Medium";
            case 3 -> "Large";
            case 4 -> "Huge";
            default -> throw new IllegalArgumentException("Unsupported hardpoint size");
        };
    }
}
