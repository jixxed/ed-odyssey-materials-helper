package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    static final Ship ADDER = new Ship(
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(4).shipModule(HardpointModule.PULSE_LASER_1_A).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(2).shipModule(HardpointModule.PULSE_LASER_1_A).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(4).slotSize(1).shipModule(HardpointModule.PULSE_LASER_1_A).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).shipModule(UtilityModule.XENO_SCANNER_0_E).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).shipModule(new UtilityModule(UtilityModule.XENO_SCANNER_0_E)).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).shipModule(new UtilityModule(UtilityModule.XENO_SCANNER_0_E)).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(4).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE).index(0).slotSize(6).shipModule(CoreModule.POWER_PLANT_4_A).build(),
                    Slot.builder().slotType(SlotType.CORE).index(1).slotSize(6).shipModule(CoreModule.POWER_PLANT_4_A).build(),
                    Slot.builder().slotType(SlotType.CORE).index(2).slotSize(6).shipModule(CoreModule.POWER_PLANT_4_A).build(),
                    Slot.builder().slotType(SlotType.CORE).index(3).slotSize(6).shipModule(CoreModule.POWER_PLANT_4_A).build(),
                    Slot.builder().slotType(SlotType.CORE).index(4).slotSize(6).shipModule(CoreModule.POWER_PLANT_4_A).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(8).shipModule(OptionalModule.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(6).shipModule(OptionalModule.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(5).shipModule(OptionalModule.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(3).slotSize(4).shipModule(MilitaryOptionalModule.MILITARY_2_E).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(4).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).build()
            ));
    @Getter
    private final List<Slot> hardpointSlots;
    @Getter
    private final List<Slot> utilitySlots;
    @Getter
    private final List<Slot> coreSlots;
    @Getter
    private final List<Slot> optionalSlots;

    private Ship(final List<Slot> hardpointSlots, final List<Slot> utilitySlots, final List<Slot> coreSlots, final List<Slot> optionalSlots) {
        this.hardpointSlots = new ArrayList<>(hardpointSlots);
        this.utilitySlots = new ArrayList<>(utilitySlots);
        this.coreSlots = new ArrayList<>(coreSlots);
        this.optionalSlots = new ArrayList<>(optionalSlots);
    }
    public Ship(final Ship ship) {
        this.hardpointSlots = new ArrayList<>(ship.hardpointSlots.stream().map(Slot::new).toList());
        this.utilitySlots = new ArrayList<>(ship.utilitySlots.stream().map(Slot::new).toList());
        this.coreSlots = new ArrayList<>(ship.coreSlots.stream().map(Slot::new).toList());
        this.optionalSlots = new ArrayList<>(ship.optionalSlots.stream().map(Slot::new).toList());
    }
}
