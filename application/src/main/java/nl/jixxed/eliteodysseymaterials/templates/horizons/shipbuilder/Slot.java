package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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


    String getSlotSizeName() {
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
            this.shipModule = switch (this.slotType) {
                case CORE -> new CoreModule((CoreModule) slot.shipModule);
                case UTILITY -> new UtilityModule((UtilityModule) slot.shipModule);
                case HARDPOINT -> new HardpointModule((HardpointModule) slot.shipModule);
                case MILITARY -> new MilitaryOptionalModule((MilitaryOptionalModule) slot.shipModule);
                case OPTIONAL -> new OptionalModule((OptionalModule) slot.shipModule);
            };
        }

    }
}
