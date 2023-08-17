package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;

import java.util.List;

@Builder
@AllArgsConstructor
public class Slot {
    private static final List<String> slotMapping = List.of("U", "S", "M", "L", "H");
    @Getter
    private final SlotType slotType;
    @Getter
    private final int index;
    @Getter
    private final int slotSize;

    @Getter
    @Setter
    private ShipModule shipModule;

//    @Getter
//    @Setter
//    private HorizonsBlueprintType engineering;
//    @Getter
//    @Setter
//    private HorizonsBlueprintType experimentalEffect;

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
