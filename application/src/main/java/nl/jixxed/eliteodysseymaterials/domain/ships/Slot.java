package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;

import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Slot {
    private static final List<String> slotMapping = List.of("U", "S", "M", "L", "H");
    @Getter
    private  SlotType slotType;
    @Getter
    private  int index;
    @Getter
    private  int slotSize;

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
        this.slotSize = slot.slotSize;
        if (slot.shipModule != null) {
            this.shipModule = slot.shipModule.Clone();
        }

    }
}
