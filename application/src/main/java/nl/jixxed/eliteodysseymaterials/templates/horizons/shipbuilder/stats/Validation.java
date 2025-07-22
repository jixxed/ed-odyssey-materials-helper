package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.utility.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Validation extends Stats implements DestroyableTemplate {
    BooleanProperty isMaxExperimentalWeaponExceeded = new SimpleBooleanProperty(false);
    BooleanProperty isMaxShieldGeneratorHullMassExceeded = new SimpleBooleanProperty(false);
    BooleanProperty isMaxFighterHangarExceeded = new SimpleBooleanProperty(false);
    BooleanProperty isMaxDockingComputerExceeded = new SimpleBooleanProperty(false);
    BooleanProperty isMaxSupercruiseAssistExceeded = new SimpleBooleanProperty(false);
    BooleanProperty isMaxDetailedSurfaceScannerExceeded = new SimpleBooleanProperty(false);
    BooleanProperty isMaxExperimentalWeaponStabiliserExceeded = new SimpleBooleanProperty(false);
    BooleanProperty isMaxFrameShiftDriveBoosterExceeded = new SimpleBooleanProperty(false);
    BooleanProperty isMaxFrameShiftDriveInterdictorExceeded = new SimpleBooleanProperty(false);
    BooleanProperty isMaxWakeScannerExceeded = new SimpleBooleanProperty(false);
    BooleanProperty isMaxFuelScoopExceeded = new SimpleBooleanProperty(false);
    BooleanProperty isMaxKillWarrantScannerExceeded = new SimpleBooleanProperty(false);
    BooleanProperty isMaxManifestScannerExceeded = new SimpleBooleanProperty(false);
    BooleanProperty isMaxMultiLimpetControllerExceeded = new SimpleBooleanProperty(false);
    BooleanProperty isMaxPulseWaveAnalyzerExceeded = new SimpleBooleanProperty(false);
    BooleanProperty isMaxRefineryExceeded = new SimpleBooleanProperty(false);
    BooleanProperty isMaxShieldGeneratorExceeded = new SimpleBooleanProperty(false);
    BooleanProperty isMaxXenoScannerExceeded = new SimpleBooleanProperty(false);
    BooleanProperty isMaxBoostPowerExceeded = new SimpleBooleanProperty(false);
    BooleanProperty isMaxThrusterMassExceeded = new SimpleBooleanProperty(false);
    BooleanProperty canShow = new SimpleBooleanProperty(true);

    public Validation() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ShipConfigEvent.class, _ -> update()));
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("validation");
        addTitle("ship.stats.validation");
        addErrorLabel("ship.validation.error.max.experimental.weapon", isMaxExperimentalWeaponExceeded);
        addErrorLabel("ship.validation.error.max.shieldgenerator.mass", isMaxShieldGeneratorHullMassExceeded);
        addErrorLabel("ship.validation.error.max.fighter.hangar", isMaxFighterHangarExceeded);
        addErrorLabel("ship.validation.error.max.dockingcomputer", isMaxDockingComputerExceeded);
        addErrorLabel("ship.validation.error.max.supercruiseassist", isMaxSupercruiseAssistExceeded);
        addErrorLabel("ship.validation.error.max.dss", isMaxDetailedSurfaceScannerExceeded);
        addErrorLabel("ship.validation.error.max.stabiliser", isMaxExperimentalWeaponStabiliserExceeded);
        addErrorLabel("ship.validation.error.max.fsdbooster", isMaxFrameShiftDriveBoosterExceeded);
        addErrorLabel("ship.validation.error.max.fsdinterdictor", isMaxFrameShiftDriveInterdictorExceeded);
        addErrorLabel("ship.validation.error.max.wakescanner", isMaxWakeScannerExceeded);
        addErrorLabel("ship.validation.error.max.fuelscoop", isMaxFuelScoopExceeded);
        addErrorLabel("ship.validation.error.max.killwarrantscanner", isMaxKillWarrantScannerExceeded);
        addErrorLabel("ship.validation.error.max.manifestscanner", isMaxManifestScannerExceeded);
        addErrorLabel("ship.validation.error.max.multilimpetcontroller", isMaxMultiLimpetControllerExceeded);
        addErrorLabel("ship.validation.error.max.pwa", isMaxPulseWaveAnalyzerExceeded);
        addErrorLabel("ship.validation.error.max.refinery", isMaxRefineryExceeded);
        addErrorLabel("ship.validation.error.max.shieldgenerator", isMaxShieldGeneratorExceeded);
        addErrorLabel("ship.validation.error.max.xenoscanner", isMaxXenoScannerExceeded);
        //PD not enough to boost
        addWarningLabel("ship.validation.warning.max.power.delivery.boost", isMaxBoostPowerExceeded);
        //thruster mass exceeded
        addErrorLabel("ship.validation.error.max.thruster.mass", isMaxThrusterMassExceeded);

        BooleanBinding anyOf = getAnyOf(
                isMaxExperimentalWeaponExceeded,
                isMaxShieldGeneratorHullMassExceeded,
                isMaxFighterHangarExceeded,
                isMaxDockingComputerExceeded,
                isMaxSupercruiseAssistExceeded,
                isMaxDetailedSurfaceScannerExceeded,
                isMaxExperimentalWeaponStabiliserExceeded,
                isMaxFrameShiftDriveBoosterExceeded,
                isMaxFrameShiftDriveInterdictorExceeded,
                isMaxWakeScannerExceeded,
                isMaxFuelScoopExceeded,
                isMaxKillWarrantScannerExceeded,
                isMaxManifestScannerExceeded,
                isMaxMultiLimpetControllerExceeded,
                isMaxPulseWaveAnalyzerExceeded,
                isMaxRefineryExceeded,
                isMaxShieldGeneratorExceeded,
                isMaxXenoScannerExceeded,
                isMaxBoostPowerExceeded,
                isMaxThrusterMassExceeded
        );
        this.visibleProperty().bind(anyOf.and(canShow));
        this.managedProperty().bind(anyOf.and(canShow));
    }

    private BooleanBinding getAnyOf(BooleanProperty... values) {
        if (values.length == 0) {
            return Bindings.createBooleanBinding(() -> false);
        }

        BooleanBinding result = Bindings.createBooleanBinding(values[0]::get, values[0]);
        for (int i = 1; i < values.length; i++) {
            final ObservableBooleanValue value = values[i];
            result = result.or(value);
        }
        return result;
    }

    private void addErrorLabel(String localeKey, BooleanProperty visible) {
        this.getNodes().add(LabelBuilder.builder()
                .withStyleClass("error")
                .withText(localeKey)
                .withManagedProperty(visible)
                .withVisibilityProperty(visible)
                .build());
    }

    private void addWarningLabel(String localeKey, BooleanProperty visible) {
        this.getNodes().add(LabelBuilder.builder()
                .withStyleClass("warning")
                .withText(localeKey)
                .withManagedProperty(visible)
                .withVisibilityProperty(visible)
                .build());
    }

    @Override
    protected void update() {
        getShip().ifPresentOrElse(ship -> {
            testMaxExperimentalWeaponExceeded(ship);
            testShieldGeneratorHullMass(ship);
            testThrusterMass(ship);
            testPowerDeliveryBoost(ship);
            isMaxFighterHangarExceeded.set(isMaxModuleExceeded(ship.getOptionalSlots(), 1, FighterHangar.class));
            isMaxDockingComputerExceeded.set(isMaxModuleExceeded(ship.getOptionalSlots(), 1, Computer.STANDARD_DOCKING_COMPUTER, Computer.ADVANCED_DOCKING_COMPUTER));
            isMaxSupercruiseAssistExceeded.set(isMaxModuleExceeded(ship.getOptionalSlots(), 1, Computer.SUPERCRUISE_ASSIST));
            isMaxDetailedSurfaceScannerExceeded.set(isMaxModuleExceeded(ship.getOptionalSlots(), 1, DetailedSurfaceScanner.class));
            isMaxExperimentalWeaponStabiliserExceeded.set(isMaxModuleExceeded(ship.getOptionalSlots(), 1, ExperimentalWeaponStabiliser.class));
            isMaxFrameShiftDriveBoosterExceeded.set(isMaxModuleExceeded(ship.getOptionalSlots(), 1, FrameShiftDriveBooster.class));
            isMaxFrameShiftDriveInterdictorExceeded.set(isMaxModuleExceeded(ship.getOptionalSlots(), 1, FrameShiftDriveInterdictor.class));
            isMaxWakeScannerExceeded.set(isMaxModuleExceeded(ship.getUtilitySlots(), 1, FrameShiftWakeScanner.class));
            isMaxFuelScoopExceeded.set(isMaxModuleExceeded(ship.getOptionalSlots(), 1, FuelScoop.class));
            isMaxKillWarrantScannerExceeded.set(isMaxModuleExceeded(ship.getUtilitySlots(), 1, KillWarrantScanner.class));
            isMaxManifestScannerExceeded.set(isMaxModuleExceeded(ship.getUtilitySlots(), 1, ManifestScanner.class));
            isMaxMultiLimpetControllerExceeded.set(isMaxModuleExceeded(ship.getOptionalSlots(), 1, MultiLimpetController.class));
            isMaxPulseWaveAnalyzerExceeded.set(isMaxModuleExceeded(ship.getUtilitySlots(), 1, PulseWaveAnalyser.class));
            isMaxRefineryExceeded.set(isMaxModuleExceeded(ship.getOptionalSlots(), 1, Refinery.class));
            isMaxShieldGeneratorExceeded.set(isMaxModuleExceeded(ship.getOptionalSlots(), 1, ShieldGenerator.class));
            isMaxXenoScannerExceeded.set(isMaxModuleExceeded(ship.getUtilitySlots(), 1, Xeno.XENO_SCANNER_0_E, Xeno.ENHANCED_XENO_SCANNER_0_C, Xeno.PULSE_WAVE_XENO_SCANNER_0_C));
        }, this::resetAll);
    }

    private void testPowerDeliveryBoost(Ship ship) {
        final Optional<Slot> powerDistributor = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_POWER_DISTRIBUTION)).findFirst().filter(Slot::isOccupied);
        final double engineCapacity = (double) powerDistributor.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINES_CAPACITY)).orElse(0D);
        final double boostCost = (double) ship.getAttributes().getOrDefault(HorizonsModifier.BOOST_COST, 0D);
        final boolean engineCapacityEnough = engineCapacity < boostCost;
        isMaxBoostPowerExceeded.set(engineCapacityEnough);
    }

    private void testThrusterMass(Ship ship) {
        final Optional<Slot> thrusters = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_THRUSTERS)).findFirst().filter(Slot::isOccupied);
        double shipMaxMass = ship.getMaximumMass();
        final double maxMassForModule = (double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.MAXIMUM_MASS)).orElse(0D);
        isMaxThrusterMassExceeded.set(maxMassForModule < shipMaxMass);//tested: exact match does not give error
    }

    private void testShieldGeneratorHullMass(Ship ship) {
        AtomicBoolean exceeded = new AtomicBoolean(false);
        ship.getOptionalSlots().forEach(slot -> {
            if (slot.getShipModule() instanceof ShieldGenerator shieldGenerator) {
                exceeded.set(exceeded.get() || (double) shieldGenerator.getAttributeValue(HorizonsModifier.SHIELDGEN_MAXIMUM_MASS) < (double) ship.getAttributes().get(HorizonsModifier.MASS));
            }
        });
        isMaxShieldGeneratorHullMassExceeded.set(exceeded.get());
    }

    private boolean isMaxModuleExceeded(List<? extends Slot> slots, int limit, Class... moduleClasses) {
        AtomicInteger moduleCount = new AtomicInteger(0);
        slots.forEach(slot -> {
            if (Arrays.stream(moduleClasses).anyMatch(moduleClass -> moduleClass.isInstance(slot.getShipModule()))) {
                moduleCount.getAndIncrement();
            }
        });
        return moduleCount.get() > limit;
    }

    private boolean isMaxModuleExceeded(List<? extends Slot> slots, int limit, ShipModule... modules) {
        AtomicInteger moduleCount = new AtomicInteger(0);
        slots.forEach(slot -> {
            if (slot.getShipModule() != null && Arrays.stream(modules).anyMatch(module -> module.getId().equals(slot.getShipModule().getId()))) {
                moduleCount.getAndIncrement();
            }
        });
        return moduleCount.get() > limit;
    }

    private void testMaxExperimentalWeaponExceeded(Ship ship) {
        AtomicInteger moduleCount = new AtomicInteger(0);
        ship.getHardpointSlots().forEach(slot -> {
            if (slot.getShipModule() != null) {
                int moduleLimit = slot.getShipModule().getModuleLimit();
                if (moduleLimit == 4) {
                    moduleCount.getAndIncrement();
                }
            }
        });
        AtomicInteger maxIncreaseLimit = new AtomicInteger(0);
        ship.getOptionalSlots().forEach(slot -> {
            if (slot.getShipModule() instanceof ExperimentalWeaponStabiliser stabiliser) {
                maxIncreaseLimit.set(Math.max(maxIncreaseLimit.get(), stabiliser.getExperimentalModuleIncrease()));
            }
        });
        isMaxExperimentalWeaponExceeded.set(moduleCount.get() > 4 + maxIncreaseLimit.get());
    }

    private void resetAll() {
        isMaxExperimentalWeaponExceeded.set(false);
    }

    public void setCanShow(boolean show) {
        canShow.set(show);
    }
}
