package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import lombok.Getter;

import java.util.List;
import java.util.stream.IntStream;

public class Ship {
    @Getter
    private final List<Slot> hardpointSlots;
    @Getter
    private final List<Slot> utilitySlots;
    @Getter
    private final List<Slot> coreSlots;
    @Getter
    private final List<Slot> optionalSlots;

    public Ship(final int hardpointSlots, final int utilitySlots, final int coreSlots, final int optionalSlots) {
        this.hardpointSlots = IntStream.range(0, hardpointSlots).mapToObj(i -> new Slot(this, SlotType.HARDPOINT,i)).toList();
        this.utilitySlots = IntStream.range(0, utilitySlots).mapToObj(i -> new Slot(this, SlotType.UTILITY,i)).toList();
        this.coreSlots = IntStream.range(0, coreSlots).mapToObj(i -> new Slot(this, SlotType.CORE,i)).toList();
        this.optionalSlots = IntStream.range(0, optionalSlots).mapToObj(i -> new Slot(this, SlotType.OPTIONAL,i)).toList();
    }
}
