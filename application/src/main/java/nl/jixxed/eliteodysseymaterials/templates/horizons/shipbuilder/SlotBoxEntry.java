package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ShipModuleButtonBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipLegacyModule;
import nl.jixxed.eliteodysseymaterials.domain.ShipLegacyModules;
import nl.jixxed.eliteodysseymaterials.domain.ships.ExternalModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Mounting;
import nl.jixxed.eliteodysseymaterials.domain.ships.Origin;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.Armour;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.PowerDistributor;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.PowerPlant;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.Thrusters;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.ShieldGenerator;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ModuleHighlightEvent;
import nl.jixxed.eliteodysseymaterials.service.event.ShipBuilderEvent;
import nl.jixxed.eliteodysseymaterials.service.ships.LegacyModuleService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class SlotBoxEntry extends DestroyableVBox {
    private static final double BOOST_MARGIN = 0.005;
    DestroyableLabel name;
    List<DestroyableHBox> options;
    private ModulesLayer modulesLayer;

    public SlotBoxEntry(final ModulesLayer modulesLayer, final SlotBox slotBox, final List<ShipModule> shipModulesList) {
        this.modulesLayer = modulesLayer;
        this.getStyleClass().add("ships-modules");
        //add ship modules
        final ShipModule firstModule = shipModulesList.get(0);
        final List<ShipModule> shipModules = (firstModule instanceof Armour)
                ? shipModulesList.stream().filter(shipModule -> ((Armour) shipModule).getShipType().equals(ApplicationState.getInstance().getShip().getShipType())).filter(ShipModule::isSelectable).toList()
                : shipModulesList.stream().filter(ShipModule::isSelectable).toList();
        this.name = LabelBuilder.builder()
                .withStyleClass("ships-modules-title")
                .withText(firstModule.getName().getBlueprintGroup().getLocalizationKey())
                .build();
        HBox.setHgrow(this.name, Priority.ALWAYS);
        Stream<List<ShipModule>> stream = shipModules.stream().allMatch(shipModule -> shipModule.hasGrouping())
                ? shipModules.stream().collect(Collectors.groupingBy(shipModule -> String.valueOf(shipModule.getGrouping()))).values().stream()
                : shipModules.stream().collect(Collectors.groupingBy(shipModule -> shipModule.getModuleSize().toString() + shipModule.getGrouping() + shipModule.getOrigin().equals(Origin.POWERPLAY))).values().stream();
        stream = Stream.concat(stream, ApplicationState.getInstance().getPreferredCommander().map(commander -> {
            final ShipLegacyModules legacyModules = LegacyModuleService.loadModules(commander);
            return legacyModules.getLegacyModules().stream().map(this::mapToShipModule).filter(shipModule -> shipModule.getClass().equals(firstModule.getClass())).map(Collections::singletonList);
        }).orElseGet(Stream::empty));
        this.options =
                stream.sorted(Comparator.comparing(shipModuleSubList -> (shipModuleSubList.get(0).hasGrouping()) ? String.valueOf(shipModuleSubList.get(0).getGrouping()) : shipModuleSubList.get(0).getModuleSize().toString() + shipModuleSubList.get(0).getOrigin().equals(Origin.POWERPLAY))).map(shipModuleSubList ->
                        shipModuleSubList.stream().map(shipModule -> {
                                    final ShipModuleButton button = ShipModuleButtonBuilder.builder()
                                            .withShipModule(shipModule)
                                            .withNonLocalizedText(shipModule.getModuleSize() + "" + shipModule.getModuleClass() + (shipModule.isLegacy() ? " " + shipModule.getCustomName() : shipModule.getClarifier() + shipModule.getNonSortingClarifier()))
                                            .withOnAction(event -> {
                                                final ShipModule clone = shipModule.Clone();
                                                if (slotBox.getSlot().getShipModule() != null) {
                                                    if (isSimilar(slotBox.getSlot().getShipModule(), clone)) {
                                                        if (!clone.isPreEngineered() && !clone.isLegacy() && !slotBox.getSlot().getShipModule().isLegacy()) {//already pre-applied
                                                            clone.getModifications().addAll(slotBox.getSlot().getShipModule().getModifications());
                                                            clone.getExperimentalEffects().addAll(slotBox.getSlot().getShipModule().getExperimentalEffects());
                                                        } else if (!clone.isLegacy() && !slotBox.getSlot().getShipModule().isLegacy()) {//pre engineered always uses modification, legacy has pre-applied
                                                            clone.getExperimentalEffects().addAll(slotBox.getSlot().getShipModule().getExperimentalEffects());
                                                        }
                                                    }
                                                }
                                                slotBox.getSlot().setShipModule(clone);
                                                notifyChanged();
                                                slotBox.refresh();
                                                slotBox.close();
                                            })
                                            .build();

                                    button.registerEventHandler(MouseEvent.MOUSE_ENTERED, _ ->
                                            EventService.publish(new ModuleHighlightEvent(shipModule)));
                                    List<DestroyableResizableImageView> images = new ArrayList<>();
                                    if (shipModule instanceof ExternalModule externalModule && !externalModule.getMounting().equals(Mounting.NA)) {
                                        images.add(ResizableImageViewBuilder.builder()
                                                .withImage("/images/ships/icons/" + externalModule.getMounting().name().toLowerCase() + ".png")
                                                .withStyleClass("ships-button-image")
                                                .build());
                                    }
                                    if (Origin.GUARDIAN.equals(shipModule.getOrigin()) || Origin.POWERPLAY.equals(shipModule.getOrigin())) {
                                        images.add(ResizableImageViewBuilder.builder()
                                                .withImage("/images/ships/icons/" + shipModule.getOrigin().name().toLowerCase() + ".png")
                                                .withStyleClass("ships-button-image")
                                                .build());
                                    }
                                    if (shipModule.isLegacy()) {
                                        images.add(ResizableImageViewBuilder.builder()
                                                .withImage("/images/ships/icons/legacy.png")
                                                .withStyleClass("ships-button-image")
                                                .build());
                                    }
                                    if (shipModule.isPreEngineered()) {
                                        images.add(ResizableImageViewBuilder.builder()
                                                .withImage("/images/ships/icons/preengineered.png")
                                                .withStyleClass("ships-button-image")
                                                .build());
                                    }
                                    if (shipModule.isStoreExclusive()) {
                                        images.add(ResizableImageViewBuilder.builder()
                                                .withImage("/images/ships/icons/arx.png")
                                                .withStyleClass("ships-button-image")
                                                .build());
                                    }
                                    if (shipModule.isAdvanced()) {
                                        images.add(ResizableImageViewBuilder.builder()
                                                .withImage("/images/ships/icons/advanced2.png")
                                                .withStyleClass("ships-button-image")
                                                .build());
                                    }
                                    if (shipModule.isEnhanced()) {
                                        images.add(ResizableImageViewBuilder.builder()
                                                .withImage("/images/ships/icons/enhanced2.png")
                                                .withStyleClass("ships-button-image")
                                                .build());
                                    }
                                    if (shipModule.isDumbfire()) {
                                        images.add(ResizableImageViewBuilder.builder()
                                                .withImage("/images/ships/icons/dumb2.png")
                                                .withStyleClass("ships-button-image")
                                                .build());
                                    }
                                    if (shipModule.isSeeker()) {
                                        images.add(ResizableImageViewBuilder.builder()
                                                .withImage("/images/ships/icons/seeker2.png")
                                                .withStyleClass("ships-button-image")
                                                .build());
                                    }
                                    if (shipModule.isCGExclusive()) {
                                        images.add(ResizableImageViewBuilder.builder()
                                                .withImage("/images/ships/icons/cg.png")
                                                .withStyleClass("ships-button-image")
                                                .build());
                                    }
                                    if (!images.isEmpty()) {
                                        HBox imagesBox = BoxBuilder.builder()
                                                .withStyleClass("ships-button-image-box")
                                                .withNodes(images.toArray(DestroyableResizableImageView[]::new)).buildHBox();
                                        button.setGraphic(imagesBox);

                                    }
                                    button.setFocusTraversable(false);
                                    button.setDisable(isButtonDisabled(slotBox, shipModule));
                                    if (shipModule instanceof ShieldGenerator) {
                                        double maxMass = ApplicationState.getInstance().getShip().getMaximumMass();
                                        final double maxMassForModule = (double) shipModule.getAttributeValue(HorizonsModifier.SHIELDGEN_MAXIMUM_MASS);
                                        if (maxMass > maxMassForModule) {
                                            button.getStyleClass().add("module-button-overload");
                                        }
                                    }
                                    if (shipModule instanceof Thrusters) {
                                        double maxMass = ApplicationState.getInstance().getShip().getMaximumMass();
                                        final double maxMassForModule = (double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_MASS);
                                        if (maxMass > maxMassForModule) {
                                            button.getStyleClass().add("module-button-overload");
                                        }
                                    }
                                    if (shipModule instanceof PowerPlant) {
                                        final Map<Integer, Double> power = ApplicationState.getInstance().getShip().getRetractedPower();
                                        double usedPower = power.get(1) + power.get(2) + power.get(3) + power.get(4) + power.get(5);
                                        final double available = (double) shipModule.getAttributeValue(HorizonsModifier.POWER_CAPACITY) - usedPower;
                                        if (available < 0D) {
                                            button.getStyleClass().add("module-button-overload");
                                        }
                                    }
                                    if (shipModule instanceof PowerDistributor) {
                                        final double engineCapacity = (double) shipModule.getAttributeValue(HorizonsModifier.ENGINES_CAPACITY);
                                        final double boostCost = (double) ApplicationState.getInstance().getShip().getAttributes().getOrDefault(HorizonsModifier.BOOST_COST, 0D);
                                        final boolean engineCapacityEnough = engineCapacity > boostCost + BOOST_MARGIN;
                                        if (!engineCapacityEnough) {
                                            button.getStyleClass().add("module-button-overload");
                                        }
                                    }
                                    return button;
                                }
                        ).toArray(ShipModuleButton[]::new)
                ).map(buttons -> {
                    final DestroyableHBox hBox = BoxBuilder.builder()
                            .withStyleClass("ships-modules-buttons")
                            .withNodes(buttons).buildHBox();
                    Arrays.stream(buttons)
                            .forEach(button -> button.addBinding(button.prefWidthProperty(), this.widthProperty().subtract((buttons.length - 1) * hBox.getSpacing()).divide(buttons.length)));
                    return hBox;
                }).toList();
        final DestroyableVBox vBox = BoxBuilder.builder()
                .withStyleClass("ships-modules-item")
                .withNodes(BoxBuilder.builder()
                        .withNodes(new GrowingRegion(), this.name, new GrowingRegion()).buildHBox()).buildVBox();
        vBox.getNodes().addAll(this.options);
        this.getNodes().add(vBox);

    }

    private static boolean isButtonDisabled(SlotBox slotBox, ShipModule shipModule) {
        return switch (shipModule.getName()) {
            case FRAME_SHIFT_DRIVE_OVERCHARGE, FRAME_SHIFT_DRIVE_OVERCHARGE_PRE, SENSORS, LIFE_SUPPORT ->
                    slotBox.getSlot().getSlotSize() != shipModule.getModuleSize().intValue();
            default -> slotBox.getSlot().getSlotSize() < shipModule.getModuleSize().intValue();
        };

    }

    private ShipModule mapToShipModule(ShipLegacyModule shipLegacyModule) {
        final ShipModule shipModule = ShipModule.getModule(shipLegacyModule.getId()).Clone();

        shipLegacyModule.getModification().forEach(shipConfigurationModification ->
                shipModule.applyModification(shipConfigurationModification.getType(), shipConfigurationModification.getGrade(), null)
        );
        shipLegacyModule.getExperimentalEffect().forEach(shipConfigurationExperimentalEffect ->
                shipModule.applyExperimentalEffect(shipConfigurationExperimentalEffect.getType())
        );
        shipModule.getModifiers().putAll(shipLegacyModule.getModifiers());
        shipModule.setLegacy(true);
        shipModule.setCustomName(shipLegacyModule.getName());
        return shipModule;
    }

    private boolean isSimilar(ShipModule shipModule, ShipModule clone) {
        final boolean b = shipModule.isSame(clone);
        log.debug("similar:" + b);
        return b;
    }

    private void notifyChanged() {
        EventService.publish(new ShipBuilderEvent());
    }
}
