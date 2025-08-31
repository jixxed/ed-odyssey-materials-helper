package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.beans.binding.StringBinding;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfigurations;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.FrameShiftDriveBooster;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.enums.PassengerCabinType;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsShipChoiceEvent;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.text.NumberFormat;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ShipSelectView extends DestroyableVBox implements DestroyableTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private Table<Ship, String, Supplier<Object>> table;

    private String sortKey = "ship.view.header.ship.name";
    private boolean sortAscending = true;
    private DestroyableHBox header;
    private Map<Map<String, Supplier<Object>>, DestroyableHBox> rows;


    public ShipSelectView() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("ship-select-view");
        table = new Table<>();
        Ship.ALL.forEach(ship -> {
            final LinkedHashMap<String, Supplier<Object>> values = new LinkedHashMap<>();
            values.put("ship.view.header.ship.name", () -> LocaleService.LocalizationKey.of(ship.getShipType().getLocalizationKey()));
            values.put("ship.view.header.price", () -> new FormattedLong(ship.getRetailPrice()));
            values.put("ship.view.header.size", () -> LocaleService.LocalizationKey.of(ship.getShipType().getShipSize().getLocalizationKey()));
            values.put("ship.view.header.crew", () -> "1 + " + ship.getShipType().getMultiCrewSeats());
            values.put("ship.view.header.fighterbay", () -> new FormattedBoolean(ship.getShipType().isFighterBay()));
            values.put("ship.view.header.masslock", () -> new FormattedDouble((Double) ship.getAttributes().get(HorizonsModifier.MASS_LOCK)));
            values.put("ship.view.header.mass", () -> new FormattedDouble((Double) ship.getAttributes().get(HorizonsModifier.MASS)));
            values.put("ship.view.header.jumprange", () -> new FormattedDouble(calculateJumpRangeMax(ship), Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL));
            values.put("ship.view.header.speed", () -> new FormattedDouble((Double) ship.getAttributes().get(HorizonsModifier.TOP_SPEED)));
            values.put("ship.view.header.boost", () -> new FormattedDouble((Double) ship.getAttributes().get(HorizonsModifier.BOOST_SPEED)));
            values.put("ship.view.header.boostinterval", () -> new FormattedDouble((Double) ship.getAttributes().get(HorizonsModifier.BOOST_INTERVAL), Formatters.NUMBER_FORMAT_1));
            values.put("ship.view.header.shield", () -> new FormattedDouble((Double) ship.getAttributes().get(HorizonsModifier.SHIELDS)));
            values.put("ship.view.header.armor", () -> new FormattedDouble((Double) ship.getAttributes().get(HorizonsModifier.ARMOUR)));
            values.put("ship.view.header.hardness", () -> new FormattedDouble((Double) ship.getAttributes().get(HorizonsModifier.ARMOUR_HARDNESS)));
            values.put("ship.view.header.fuel", () -> new FormattedDouble(ship.getMaxFuel()));
            values.put("ship.view.header.cargo", () -> new FormattedDouble(ship.getMaxPotentialCargo()));
            values.put("ship.view.header.passenger", () -> new FormattedMultiLongValue(new Long[]{(long) ship.getMaxPotentialPassenger(PassengerCabinType.ECONOMY), (long) ship.getMaxPotentialPassenger(PassengerCabinType.BUSINESS), (long) ship.getMaxPotentialPassenger(PassengerCabinType.FIRSTCLASS), (long) ship.getMaxPotentialPassenger(PassengerCabinType.LUXURY)}));
            values.put("ship.view.header.slot.hardpoints", () -> ship.getHardpointSlots().stream().map(Slot::getSlotSizeName).collect(Collectors.joining(" ")));
            values.put("ship.view.header.slot.utility", () -> new FormattedLong(ship.getUtilitySlots().size()));
            values.put("ship.view.header.slot.powerplant", () -> Formatters.NUMBER_FORMAT_0.format(ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_POWER_PLANT)).findFirst().map(Slot::getSlotSize).orElse(0)));
            values.put("ship.view.header.slot.thrusters", () -> Formatters.NUMBER_FORMAT_0.format(ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_THRUSTERS)).findFirst().map(Slot::getSlotSize).orElse(0)));
            values.put("ship.view.header.slot.frameshiftdrive", () -> Formatters.NUMBER_FORMAT_0.format(ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_FRAME_SHIFT_DRIVE)).findFirst().map(Slot::getSlotSize).orElse(0)));
            values.put("ship.view.header.slot.lifesupport", () -> Formatters.NUMBER_FORMAT_0.format(ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_LIFE_SUPPORT)).findFirst().map(Slot::getSlotSize).orElse(0)));
            values.put("ship.view.header.slot.powerdistribution", () -> Formatters.NUMBER_FORMAT_0.format(ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_POWER_DISTRIBUTION)).findFirst().map(Slot::getSlotSize).orElse(0)));
            values.put("ship.view.header.slot.sensors", () -> Formatters.NUMBER_FORMAT_0.format(ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_SENSORS)).findFirst().map(Slot::getSlotSize).orElse(0)));
            values.put("ship.view.header.slot.fueltank", () -> Formatters.NUMBER_FORMAT_0.format(ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_FUEL_TANK)).findFirst().map(Slot::getSlotSize).orElse(0)));
            values.put("ship.view.header.slot.optional", () -> ship.getOptionalSlots().stream().map(ShipSelectView::getSlotSizeName).collect(Collectors.joining(" ")));
            table.add(ship, values);
        });

        header = BoxBuilder.builder()
                .withStyleClass("header")
                .withNodes(
                        table.backingMap.values().stream()
                                .findFirst()
                                .map(Map::keySet)
                                .map(keys -> keys.stream()
                                        .map(key -> LabelBuilder.builder()
                                                .withOnMouseClicked(event -> sortTable(key))
                                                .withStyleClasses("cell", key.replace("ship.view.header", "col").replace(".", "-"))
                                                .withText(key)
                                                .build())
                                        .toList()
                                        .toArray(DestroyableLabel[]::new))
                                .orElseThrow(IllegalArgumentException::new)
                ).buildHBox();
        rows = new HashMap<>();
        table.backingMap.forEach((key, value) -> rows.put(value,
                BoxBuilder.builder()
                        .withStyleClass("row")
                        .withNodes(
                                value.entrySet().stream()
                                        .map(entry -> LabelBuilder.builder()
                                                .withStyleClasses("cell", entry.getKey().replace("ship.view.header", "col").replace(".", "-"))
                                                .withText(getStringBinding(entry.getValue()))
                                                .build())
                                        .toArray(DestroyableLabel[]::new))
                        .withOnMouseClicked(_ ->
                                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                                    final ShipConfigurations shipConfigurations = ShipService.getShipConfigurations(commander);
                                    shipConfigurations.getSelectedShipConfiguration().ifPresent(configuration -> {
                                        final Ship ship = new Ship(key);
                                        //set all old slot modules
                                        ship.getHardpointSlots().forEach(slot -> slot.setOldShipModule(slot.getShipModule()));
                                        ship.getCoreSlots().forEach(slot -> slot.setOldShipModule(slot.getShipModule()));
                                        ship.getOptionalSlots().forEach(slot -> slot.setOldShipModule(slot.getShipModule()));
                                        ship.getUtilitySlots().forEach(slot -> slot.setOldShipModule(slot.getShipModule()));
                                        APPLICATION_STATE.setShip(ship);
                                        ShipMapper.toShipConfiguration(APPLICATION_STATE.getShip(), configuration, configuration.getName());
                                        ShipService.saveShipConfigurations(commander, shipConfigurations);
                                        EventService.publish(new HorizonsShipChoiceEvent(configuration.getUuid()));
                                    });
                                }))
                        .buildHBox()));
        update();
    }

    private static String getSlotSizeName(Slot slot) {
        return switch (slot.getSlotType()) {
            case CARGO -> "C" + slot.getSlotSizeName();
            case MILITARY -> "M" + slot.getSlotSizeName();
            default -> slot.getSlotSizeName();
        };
    }

    private void sortTable(String key) {
        if (this.sortKey.equals(key)) {
            sortAscending = !sortAscending;
        } else {
            sortAscending = true;
            this.sortKey = key;
        }
        update();
    }

    private void update() {
        this.getChildren().clear();
        this.getChildren().add(header);
        this.getChildren().addAll(rows.entrySet().stream().sorted(getComparator()).map(Map.Entry::getValue).toArray(DestroyableHBox[]::new));
    }

    private Comparator<Map.Entry<Map<String, Supplier<Object>>, DestroyableHBox>> getComparator() {
        var entryComparator = Comparator.comparing(this::getObjectComparable);
        if (sortAscending) {
            return entryComparator;
        }
        return entryComparator.reversed();
    }

    private Comparable<Object> getObjectComparable(Map.Entry<Map<String, Supplier<Object>>, DestroyableHBox> x) {
        final Object o = x.getKey().get(sortKey).get();
        if (o instanceof LocaleService.LocalizationKey key)
            return (Comparable<Object>) (Comparable<?>) LocaleService.getLocalizedStringForCurrentLocale(key.getKey());
        if (o instanceof Comparable<?>)
            return (Comparable<Object>) o;
        throw new IllegalArgumentException("Incomparable type");
    }

    private static StringBinding getStringBinding(Supplier<Object> valueSupplier) {
        final Object value = valueSupplier.get();
        if (value instanceof LocaleService.LocalizationKey key)
            return LocaleService.getStringBinding(key.getKey());
        if (value instanceof FormattedLong fmt)
            return LocaleService.getStringBinding(fmt::toString);
        if (value instanceof FormattedDouble fmt)
            return LocaleService.getStringBinding(fmt::toString);
        if (value instanceof FormattedMultiLongValue fmt)
            return LocaleService.getStringBinding(fmt::toString);
        if (value instanceof FormattedBoolean fmt)
            return LocaleService.getStringBinding(fmt::toString);
        if (value instanceof String)
            return LocaleService.getStringBinding((Supplier<String>) (Supplier<?>) valueSupplier);
        throw new IllegalArgumentException("unsupported format");
    }

    public double calculateJumpRangeMax(Ship ship) {
        final double fuel = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, false)).orElse(1D);
        return calculateJumpRange(ship, ship.getEmptyMass() + fuel, fuel);
    }

    public double calculateJumpRange(Ship ship, final double mass, final double fuel) {
        final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, false)).orElse(1D);
        final double optimisedMass = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FSD_OPTIMISED_MASS, 1D, false)).orElse(1D);
        final double fuelMultiplier = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FUEL_MULTIPLIER, 1D, false)).orElse(1D);
        final double fuelPower = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FUEL_POWER, 1D, false)).orElse(1D);
        final double jumpRangeIncrease = ship.getOptionalSlots().stream().filter(slot -> slot.getShipModule() instanceof FrameShiftDriveBooster).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.JUMP_RANGE_INCREASE, 1D, false)).orElse(0D);
        return calculateJumpDistance(mass, Math.min(fuel, maxFuelPerJump), optimisedMass, fuelMultiplier, fuelPower, jumpRangeIncrease);
    }

    private double calculateJumpDistance(final double mass, final double fuel, final double optimisedMass, final double fuelMultiplier, final double fuelPower, final double jumpRangeIncrease) {
        return Math.pow(fuel / fuelMultiplier, 1 / fuelPower) * optimisedMass / mass + jumpRangeIncrease;
    }

    @AllArgsConstructor
    @RequiredArgsConstructor
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    class FormattedLong implements Comparable<FormattedLong> {
        @EqualsAndHashCode.Include
        final long value;
        NumberFormat formatter = Formatters.NUMBER_FORMAT_0;

        @Override
        public String toString() {
            return formatter.format(value);
        }

        public int compareTo(FormattedLong anotherLong) {
            return Long.compare(this.value, anotherLong.value);
        }

    }

    @AllArgsConstructor
    @RequiredArgsConstructor
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    class FormattedDouble implements Comparable<FormattedDouble> {
        @EqualsAndHashCode.Include
        final double value;
        NumberFormat formatter = Formatters.NUMBER_FORMAT_0;

        @Override
        public String toString() {
            return formatter.format(value);
        }

        public int compareTo(FormattedDouble anotherDouble) {
            return Double.compare(this.value, anotherDouble.value);
        }
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    class FormattedBoolean implements Comparable<FormattedBoolean> {
        @EqualsAndHashCode.Include
        final boolean value;

        @Override
        public String toString() {
            return LocaleService.getLocalizedStringForCurrentLocale(value ? "ship.view.yes" : "ship.view.no");
        }

        public int compareTo(FormattedBoolean anotherBoolean) {
            return Boolean.compare(this.value, anotherBoolean.value);
        }
    }

    @AllArgsConstructor
    @RequiredArgsConstructor
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    class FormattedMultiLongValue implements Comparable<FormattedMultiLongValue> {
        @EqualsAndHashCode.Include
        final Long[] values;
        NumberFormat formatter = Formatters.NUMBER_FORMAT_0;

        @Override
        public String toString() {
            return Arrays.stream(values)
                    .map(String::valueOf)
                    .collect(Collectors.joining("/"));
        }

        public int compareTo(FormattedMultiLongValue anotherMultiLong) {
            return Long.compare(this.values[0], anotherMultiLong.values[0]);
        }

    }
}
