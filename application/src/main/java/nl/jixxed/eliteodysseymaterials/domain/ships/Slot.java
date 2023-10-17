package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.*;
import lombok.experimental.SuperBuilder;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;

import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Slot {
    private static final List<String> slotMapping = List.of("U", "S", "M", "L", "H");
    @Getter
    private  SlotType slotType;
    @Getter
    private  int index;
    @Getter
    private  int slotSize;
    private  Integer namedIndex;

    @Getter
    @Setter
    private ShipModule shipModule;

    public List<HorizonsBlueprintType> getEffectiveExperimentalEffects() {
        if (!this.shipModule.getModifications().isEmpty()) {
            return this.shipModule.getExperimentalEffects();
        }
        return null;
    }

    public String getSlotSizeName() {
        if (this.slotType == SlotType.HARDPOINT || this.slotType == SlotType.UTILITY) {
            return slotMapping.get(this.slotSize);
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
        return namedIndex != null ? namedIndex : index + 1;
    }

    public boolean isOccupied() {
        return this.shipModule != null;
    }
}
