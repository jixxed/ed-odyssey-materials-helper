package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.SegmentedBarBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ToggleButtonBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint.GuardianGaussCannon;
import nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint.RailGun;
import nl.jixxed.eliteodysseymaterials.enums.HardpointGroup;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class WeaponStats extends Stats implements DestroyableTemplate {
    private DestroyableLabel rawDamage;
    private RangeIndicator ammoIndicator;
    private RangeIndicator bustIndicator;
    Set<HardpointGroup> selectedHardpointGroups = new HashSet<>(Set.of(HardpointGroup.A, HardpointGroup.B, HardpointGroup.C, HardpointGroup.D));
    private DestroyableSegmentedBar<TypeSegment> segmentedBar;
    private TypeSegment absolutePercentageGroup;
    private TypeSegment kineticPercentageGroup;
    private TypeSegment thermalPercentageGroup;
    private TypeSegment explosivePercentageGroup;
    private TypeSegment causticPercentageGroup;
    private TypeSegment antiXenoPercentageGroup;
    private TypeSegment noDamageGroup;
    public static final Color HARDPOINT_THERMAL_COLOR = Color.web("#ff7c7c");
    public static final Color HARDPOINT_KINETIC_COLOR = Color.web("#2e92df");
    public static final Color HARDPOINT_EXPLOSIVE_COLOR = Color.web("#CE6C1E");
    public static final Color HARDPOINT_ABSOLUTE_COLOR = Color.web("#6D3DA8");
    public static final Color HARDPOINT_CAUSTIC_COLOR = Color.web("#F8FF2E");
    public static final Color HARDPOINT_ANTIXENO_COLOR = Color.web("#89D07F");

    public WeaponStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(new GrowingRegion(), createTitle("ship.stats.weapon"), new GrowingRegion())
                .buildHBox());
        this.getNodes().add(new DestroyableSeparator(Orientation.HORIZONTAL));

        DestroyableToggleButton[] buttons = Arrays.stream(HardpointGroup.values()).map(group -> {
            final DestroyableToggleButton groupButton = ToggleButtonBuilder.builder()
                    .withStyleClass("toggle-button-stats-hardpoint")
                    .withNonLocalizedText(group.name())
                    .build();
            groupButton.setSelected(true);
            groupButton.setFocusTraversable(false);
            groupButton.selectedProperty().addListener((_, _, newValue) -> {
                if (newValue) {
                    selectedHardpointGroups.add(group);
                } else {
                    selectedHardpointGroups.remove(group);
                }
                update();
            });
            return groupButton;
        }).toArray(DestroyableToggleButton[]::new);

        DestroyableHBox box = BoxBuilder.builder()
                .withNodes(buttons)
                .buildHBox();
        this.getNodes().add(box);
        this.rawDamage = createValueSmallLabel("ship.stats.weapon.rawdamage.value", String.format("%.2f", 0d));

        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.weapon.rawdamage"), new GrowingRegion(), this.rawDamage)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withStyleClass("weapon-stats-legend-box")
                .withNodes(BoxBuilder.builder()
                                .withNodes(BoxBuilder.builder()
                                                .withNodes(new GrowingRegion(), createLegendCircle(HARDPOINT_THERMAL_COLOR), new GrowingRegion())
                                                .buildHBox(),
                                        BoxBuilder.builder()
                                                .withNodes(new GrowingRegion(), createLegendLabel("ship.stats.weapon.thermalpercentage"), new GrowingRegion())
                                                .buildHBox()
                                )
                                .buildVBox(),
                        new GrowingRegion(),
                        BoxBuilder.builder()
                                .withNodes(BoxBuilder.builder()
                                                .withNodes(new GrowingRegion(), createLegendCircle(HARDPOINT_KINETIC_COLOR), new GrowingRegion())
                                                .buildHBox(),
                                        BoxBuilder.builder()
                                                .withNodes(new GrowingRegion(), createLegendLabel("ship.stats.weapon.kineticpercentage"), new GrowingRegion())
                                                .buildHBox()
                                )
                                .buildVBox(),
                        new GrowingRegion(),
                        BoxBuilder.builder()
                                .withNodes(BoxBuilder.builder()
                                                .withNodes(new GrowingRegion(), createLegendCircle(HARDPOINT_EXPLOSIVE_COLOR), new GrowingRegion())
                                                .buildHBox(),
                                        BoxBuilder.builder()
                                                .withNodes(new GrowingRegion(), createLegendLabel("ship.stats.weapon.explosivepercentage"), new GrowingRegion())
                                                .buildHBox()
                                )
                                .buildVBox()
                )
                .buildHBox());

        this.thermalPercentageGroup = new TypeSegment(0D, SegmentType.HARDPOINT_THERMAL);
        this.kineticPercentageGroup = new TypeSegment(0D, SegmentType.HARDPOINT_KINETIC);
        this.explosivePercentageGroup = new TypeSegment(0D, SegmentType.HARDPOINT_EXPLOSIVE);
        this.absolutePercentageGroup = new TypeSegment(0D, SegmentType.HARDPOINT_ABSOLUTE);
        this.causticPercentageGroup = new TypeSegment(0D, SegmentType.HARDPOINT_CAUSTIC);
        this.antiXenoPercentageGroup = new TypeSegment(0D, SegmentType.HARDPOINT_ANTIXENO);
        this.noDamageGroup = new TypeSegment(100D, SegmentType.HARDPOINT_NONE);

        this.segmentedBar = SegmentedBarBuilder.builder(TypeSegment.class)
                .withStyleClass("power-progressbar")
                .withOrientation(Orientation.HORIZONTAL)
                .withInfoNodeFactory(segment -> LabelBuilder.builder()
                        .withStyleClass("power-progressbar-label")
                        .withNonLocalizedText(segment.getText() + ": " + LocaleService.getLocalizedStringForCurrentLocale("ship.stats.weapon.value", String.format("%.2f", segment.getValue())))
                        .build())
                .withSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(
                        SegmentType.HARDPOINT_THERMAL, HARDPOINT_THERMAL_COLOR,
                        SegmentType.HARDPOINT_KINETIC, HARDPOINT_KINETIC_COLOR,
                        SegmentType.HARDPOINT_EXPLOSIVE, HARDPOINT_EXPLOSIVE_COLOR,
                        SegmentType.HARDPOINT_ABSOLUTE, HARDPOINT_ABSOLUTE_COLOR,
                        SegmentType.HARDPOINT_CAUSTIC, HARDPOINT_CAUSTIC_COLOR,
                        SegmentType.HARDPOINT_ANTIXENO, HARDPOINT_ANTIXENO_COLOR,
                        SegmentType.HARDPOINT_NONE, Color.rgb(128, 128, 128)
                ), false))
                .withSegments(absolutePercentageGroup, kineticPercentageGroup, thermalPercentageGroup, explosivePercentageGroup, causticPercentageGroup, antiXenoPercentageGroup, noDamageGroup)
                .build();

        this.getNodes().add(this.segmentedBar);
        this.getNodes().add(BoxBuilder.builder()
                .withStyleClass("weapon-stats-legend-box")
                .withNodes(
                        BoxBuilder.builder()
                                .withNodes(
                                        BoxBuilder.builder()
                                                .withNodes(new GrowingRegion(), createLegendLabel("ship.stats.weapon.absolutepercentage"), new GrowingRegion())
                                                .buildHBox(),
                                        BoxBuilder.builder()
                                                .withNodes(new GrowingRegion(), createLegendCircle(HARDPOINT_ABSOLUTE_COLOR), new GrowingRegion())
                                                .buildHBox()
                                ).buildVBox(),
                        new GrowingRegion(),
                        BoxBuilder.builder()
                                .withNodes(
                                        BoxBuilder.builder()
                                                .withNodes(new GrowingRegion(), createLegendLabel("ship.stats.weapon.causticpercentage"), new GrowingRegion())
                                                .buildHBox(),
                                        BoxBuilder.builder()
                                                .withNodes(new GrowingRegion(), createLegendCircle(HARDPOINT_CAUSTIC_COLOR), new GrowingRegion())
                                                .buildHBox()
                                ).buildVBox(),
                        new GrowingRegion(),
                        BoxBuilder.builder()
                                .withNodes(
                                        BoxBuilder.builder()
                                                .withNodes(new GrowingRegion(), createLegendLabel("ship.stats.weapon.antixenopercentage"), new GrowingRegion())
                                                .buildHBox(),
                                        BoxBuilder.builder()
                                                .withNodes(new GrowingRegion(), createLegendCircle(HARDPOINT_ANTIXENO_COLOR), new GrowingRegion())
                                                .buildHBox()
                                ).buildVBox()
                ).buildHBox());

        bustIndicator = new RangeIndicator(0D, 0D, 0D, "ship.stats.weapon.burstduration", "ship.stats.weapon.burstduration.value");
        ammoIndicator = new RangeIndicator(0D, 0D, 0D, "ship.stats.weapon.ammoduration", "ship.stats.weapon.ammoduration.value");
        this.getNodes().addAll(bustIndicator, ammoIndicator);

    }

    private DestroyableCircle createLegendCircle(Color powerGroupColor) {
        final DestroyableCircle circle = new DestroyableCircle();
        circle.addBinding(circle.radiusProperty(), ScalingHelper.getPixelDoubleBindingFromEm(0.5D));
        circle.setFill(powerGroupColor);
        return circle;
    }

    private DestroyableLabel createLegendLabel(String key) {
        return LabelBuilder.builder()
                .withStyleClass("weapon-stats-legend-label")
                .withText(key)
                .build();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ShipConfigEvent.class, _ -> update()));
    }

    private List<ShipModule> selectedHardPoints() {
        return getShip().map(ship -> ship.getHardpointSlots().stream()
                .filter(slot -> slot.getSlotType() == SlotType.HARDPOINT)
                .filter(Slot::isOccupied)
                .filter(slot -> selectedHardpointGroups.contains(slot.getHardpointGroup()))
                .map(Slot::getShipModule)
                .toList()).orElse(Collections.emptyList());
    }

    private double calculateRawDamage() {
        return selectedHardPoints().stream()
                .map(shipModule -> (double) shipModule.getAttributeValue(HorizonsModifier.DAMAGE_PER_SECOND))
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private double getRatio(ShipModule shipModule, HorizonsModifier modifier) {
        try {
            return (double) shipModule.getAttributeValue(modifier);
        } catch (IllegalArgumentException e) {
            return 0d;
        }
    }

    private double calculateAbsolutePercentage() {
        final double calculatedRawDamage = calculateRawDamage();
        if (calculatedRawDamage == 0d) {
            return 0d;
        }
        return selectedHardPoints().stream()
                .map(shipModule -> getRatio(shipModule, HorizonsModifier.ABSOLUTE_DAMAGE_RATIO) * (double) shipModule.getAttributeValue(HorizonsModifier.DAMAGE_PER_SECOND))
                .mapToDouble(Double::doubleValue)
                .sum() / calculatedRawDamage * 100d;
    }

    private double calculateKineticPercentage() {
        final double calculatedRawDamage = calculateRawDamage();
        if (calculatedRawDamage == 0d) {
            return 0d;
        }
        return selectedHardPoints().stream()
                .map(shipModule -> getRatio(shipModule, HorizonsModifier.KINETIC_DAMAGE_RATIO) * (double) shipModule.getAttributeValue(HorizonsModifier.DAMAGE_PER_SECOND))
                .mapToDouble(Double::doubleValue)
                .sum() / calculatedRawDamage * 100d;
    }

    private double calculateThermalPercentage() {
        final double calculatedRawDamage = calculateRawDamage();
        if (calculatedRawDamage == 0d) {
            return 0d;
        }
        return selectedHardPoints().stream()
                .map(shipModule -> getRatio(shipModule, HorizonsModifier.THERMAL_DAMAGE_RATIO) * (double) shipModule.getAttributeValue(HorizonsModifier.DAMAGE_PER_SECOND))
                .mapToDouble(Double::doubleValue)
                .sum() / calculatedRawDamage * 100d;
    }

    private double calculateExplosivePercentage() {
        final double calculatedRawDamage = calculateRawDamage();
        if (calculatedRawDamage == 0d) {
            return 0d;
        }
        return selectedHardPoints().stream()
                .map(shipModule -> getRatio(shipModule, HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO) * (double) shipModule.getAttributeValue(HorizonsModifier.DAMAGE_PER_SECOND))
                .mapToDouble(Double::doubleValue)
                .sum() / calculatedRawDamage * 100d;
    }

    private double calculateCausticPercentage() {
        final double calculatedRawDamage = calculateRawDamage();
        if (calculatedRawDamage == 0d) {
            return 0d;
        }
        return selectedHardPoints().stream()
                .map(shipModule -> getRatio(shipModule, HorizonsModifier.CAUSTIC_DAMAGE_RATIO) * (double) shipModule.getAttributeValue(HorizonsModifier.DAMAGE_PER_SECOND))
                .mapToDouble(Double::doubleValue)
                .sum() / calculatedRawDamage * 100d;
    }

    private double calculateAntiXenoPercentage() {
        final double calculatedRawDamage = calculateRawDamage();
        if (calculatedRawDamage == 0d) {
            return 0d;
        }
        return selectedHardPoints().stream()
                .map(shipModule -> getRatio(shipModule, HorizonsModifier.ANTI_XENO_DAMAGE_RATIO) * (double) shipModule.getAttributeValue(HorizonsModifier.DAMAGE_PER_SECOND))
                .mapToDouble(Double::doubleValue)
                .sum() / calculatedRawDamage * 100d;
    }

    private double calculateBurstDuration(int pips) {

        final double totalEps = selectedHardPoints().stream()
                .map(shipModule -> energyPerSecond(shipModule, false))
                .mapToDouble(Double::doubleValue)
                .sum();
        AtomicReference<Double> epsCur = new AtomicReference<>(totalEps);

        final Optional<Slot> powerDistributor = getShip().flatMap(ship -> ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_POWER_DISTRIBUTION)).findFirst().filter(Slot::isOccupied));

        final double multiplier = Math.pow(pips / 8.0, 1.1);
        final double weaponCapacity = (double) powerDistributor.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.WEAPONS_CAPACITY)).orElse(0D);
        final double weaponRecharge = (double) powerDistributor.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.WEAPONS_RECHARGE)).orElse(0D);
        //fixme div by 0
        AtomicReference<Double> wepcapBurstCur = new AtomicReference<>(weaponCapacity / Math.max(0, epsCur.get() - weaponRecharge * multiplier));
        selectedHardPoints().forEach(shipModule -> {
            if (wepcapBurstCur.get() >= secondsPerClip(shipModule, false)) {
                epsCur.set(epsCur.get() - energyPerSecond(shipModule, false) + energyPerSecond(shipModule, true));
                wepcapBurstCur.set(weaponCapacity / Math.max(0, epsCur.get() - weaponRecharge * multiplier));
            }
        });

        return wepcapBurstCur.get();
    }

    private double secondsPerClip(ShipModule shipModule, boolean sustained) {
        double duration = 0D;
        if ((shipModule instanceof RailGun || shipModule instanceof GuardianGaussCannon) && !(shipModule.equals(RailGun.IMPERIAL_HAMMER_RAIL_GUN_2_B_F) && sustained)) {
            duration = shipModule.getAttributeValueOrDefault(HorizonsModifier.CHARGE_TIME, 0D);
        }
        double burstSize = shipModule.getAttributeValueOrDefault(HorizonsModifier.BURST_SIZE, 1D);
        double burstRateOfFire = shipModule.getAttributeValueOrDefault(HorizonsModifier.BURST_RATE_OF_FIRE, 1D);
        double burstInterval = shipModule.getAttributeValueOrDefault(HorizonsModifier.BURST_INTERVAL, 0D);
        double secondsPerClip = (duration + (burstSize - 1) / burstRateOfFire + burstInterval);
        double ammoClipSize = shipModule.getAttributeValueOrDefault(HorizonsModifier.AMMO_CLIP_SIZE, Double.POSITIVE_INFINITY);
        double reloadTime = sustained ? shipModule.getAttributeValueOrDefault(HorizonsModifier.RELOAD_TIME, 0D) : 0D;
        if (Double.isFinite(ammoClipSize)) {
            if (shipModule.getExperimentalEffects().contains(HorizonsBlueprintType.AUTO_LOADER)) {
                ammoClipSize += ammoClipSize - 1;
            }
            secondsPerClip *= Math.ceil(ammoClipSize / burstSize);
        }
        return secondsPerClip + Math.max(0, reloadTime - duration - burstInterval);
    }

    private double shotsFiredPerClip(ShipModule shipModule) {
        double ammoClipSize = shipModule.getAttributeValueOrDefault(HorizonsModifier.AMMO_CLIP_SIZE, Double.POSITIVE_INFINITY);
        if (Double.isFinite(ammoClipSize)) {
            if (shipModule.getExperimentalEffects().contains(HorizonsBlueprintType.AUTO_LOADER)) {
                return ammoClipSize + ammoClipSize - 1;
            }
            return ammoClipSize;
        }
        return shipModule.getAttributeValueOrDefault(HorizonsModifier.BURST_SIZE, 1D);
    }

    private double rateOfFire(ShipModule shipModule, boolean sustained) {
        return shotsFiredPerClip(shipModule) / secondsPerClip(shipModule, sustained);
    }

    private double energyPerSecond(ShipModule shipModule, boolean sustained) {
        final double distributorDraw = shipModule.getAttributeValueOrDefault(HorizonsModifier.DISTRIBUTOR_DRAW, 0D);
        final double rateOfFire = rateOfFire(shipModule, sustained);
        return distributorDraw * (Double.isFinite(rateOfFire) ? rateOfFire : 1);
    }

    private double dps(ShipModule shipModule, boolean sustained) {
        final double damage = shipModule.getAttributeValueOrDefault(HorizonsModifier.DAMAGE, 0D);
        final double rounds = shipModule.getAttributeValueOrDefault(HorizonsModifier.ROUNDS_PER_SHOT, 1D);
        final double rateOfFire = rateOfFire(shipModule, sustained);
        return damage * rounds * (Double.isFinite(rateOfFire) ? rateOfFire : 1);
    }

    private double calculateMinimumBurstDuration() {
        return calculateBurstDuration(0);
    }

    private double calculateCurrentBurstDuration() {
        return calculateBurstDuration(ApplicationState.getInstance().getWeaponPips());
    }

    private double calculateMaximumBurstDuration() {
        return calculateBurstDuration(8);
    }

    private double calculateMinimumAmmoDuration() {
        return selectedHardPoints().stream()
                .map(shipModule -> calculateAmmoDuration(shipModule, 1))
                .mapToDouble(Double::doubleValue)
                .max().orElse(0D);

    }

    private double calculateCurrentAmmoDuration() {
        return selectedHardPoints().stream()
                .map(shipModule -> calculateAmmoDuration(shipModule, ApplicationState.getInstance().getWeaponPips()))
                .mapToDouble(Double::doubleValue)
                .max().orElse(0D);

    }

    private double calculateAmmoDuration(ShipModule shipModule, int pips) {
        if (pips == 0) {
            return Double.POSITIVE_INFINITY;
        }

        var dpsNoDistributorDraw = 0d;
        var dpsDistributorDraw = 0d;
        var ammoTimeWeaponCap = Double.POSITIVE_INFINITY;
        var ammoTimeNoCap = Double.POSITIVE_INFINITY;


        final double totalEps = selectedHardPoints().stream()
                .map(shipModule2 -> energyPerSecond(shipModule2, false))
                .mapToDouble(Double::doubleValue)
                .sum();
        final double totalEpsSustained = selectedHardPoints().stream()
                .map(shipModule2 -> energyPerSecond(shipModule2, true))
                .mapToDouble(Double::doubleValue)
                .sum();
        AtomicReference<Double> epsCurrent = new AtomicReference<>(totalEps);

        final Optional<Slot> powerDistributor = getShip().flatMap(ship -> ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_POWER_DISTRIBUTION)).findFirst());

        final double multiplier = Math.pow(pips / 8.0, 1.1);
        final double weaponCapacity = (double) powerDistributor.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.WEAPONS_CAPACITY)).orElse(0D);
        final double weaponRecharge = (double) powerDistributor.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.WEAPONS_RECHARGE)).orElse(0D);
        //fixme div by 0
        AtomicReference<Double> weaponCapBurstCurrent = new AtomicReference<>(weaponCapacity / Math.max(0, epsCurrent.get() - weaponRecharge * multiplier));
        selectedHardPoints().forEach(shipModule2 -> {
            if (weaponCapBurstCurrent.get() >= secondsPerClip(shipModule2, false)) {
                epsCurrent.set(epsCurrent.get() - energyPerSecond(shipModule2, false) + energyPerSecond(shipModule2, true));
                weaponCapBurstCurrent.set(weaponCapacity / Math.max(0, epsCurrent.get() - weaponRecharge * multiplier));
            }
        });

        var weaponChargeSustainedCurrent = Math.clamp(weaponRecharge * multiplier / totalEpsSustained, 0, 1);
        double distdraw = shipModule.getAttributeValueOrDefault(HorizonsModifier.DISTRIBUTOR_DRAW, 0D);
        double ammoClipSize = shipModule.getAttributeValueOrDefault(HorizonsModifier.AMMO_CLIP_SIZE, Double.POSITIVE_INFINITY);
        double ammoMaximum = shipModule.getAttributeValueOrDefault(HorizonsModifier.AMMO_MAXIMUM, Double.POSITIVE_INFINITY);
        var ammotime = Double.isFinite(ammoClipSize) ? (secondsPerClip(shipModule, true) * ((ammoClipSize + ammoMaximum) / shotsFiredPerClip(shipModule))) : Double.POSITIVE_INFINITY;
        if (distdraw > 0D) {
            dpsDistributorDraw += dps(shipModule, true);
            ammoTimeWeaponCap = Math.min(ammoTimeWeaponCap, ammotime);
        } else {
            dpsNoDistributorDraw += dps(shipModule, true);
            ammoTimeNoCap = Math.min(ammoTimeNoCap, ammotime);
        }
        var curWpnSus = ((dpsNoDistributorDraw + (dpsDistributorDraw > 0D ? (dpsDistributorDraw * weaponChargeSustainedCurrent) : 0)) / dps(shipModule, false));

        return Math.min(ammoTimeNoCap, ((ammoTimeWeaponCap <= weaponCapBurstCurrent.get()) ? ammoTimeWeaponCap : (weaponCapBurstCurrent.get() + (ammoTimeWeaponCap - weaponCapBurstCurrent.get()) / curWpnSus)));
    }

    private double calculateMaximumAmmoDuration() {
        return selectedHardPoints().stream()
                .map(shipModule -> {
                    return calculateAmmoDuration(shipModule, 8);
                })
                .mapToDouble(Double::doubleValue)
                .max().orElse(0D);
    }

    @Override
    protected void update() {
        thermalPercentageGroup.setValue(calculateThermalPercentage());
        kineticPercentageGroup.setValue(calculateKineticPercentage());
        explosivePercentageGroup.setValue(calculateExplosivePercentage());
        absolutePercentageGroup.setValue(calculateAbsolutePercentage());
        causticPercentageGroup.setValue(calculateCausticPercentage());
        antiXenoPercentageGroup.setValue(calculateAntiXenoPercentage());
        noDamageGroup.setValue(calculateRawDamage() == 0d ? 100d : 0d);
        this.rawDamage.addBinding(this.rawDamage.textProperty(), LocaleService.getStringBinding("ship.stats.weapon.rawdamage.value", String.format("%.2f", calculateRawDamage())));

        bustIndicator.updateValues(calculateMinimumBurstDuration(), calculateCurrentBurstDuration(), calculateMaximumBurstDuration());
        ammoIndicator.updateValues(calculateMinimumAmmoDuration(), calculateCurrentAmmoDuration(), calculateMaximumAmmoDuration());
    }
}
