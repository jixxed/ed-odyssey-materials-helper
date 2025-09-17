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
