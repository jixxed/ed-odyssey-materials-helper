package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ShipModuleButtonBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipLegacyModule;
import nl.jixxed.eliteodysseymaterials.domain.ShipLegacyModules;
import nl.jixxed.eliteodysseymaterials.domain.ships.Origin;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.Armour;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipBuilderEvent;
import nl.jixxed.eliteodysseymaterials.service.ships.LegacyModuleService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class SlotBoxEntry extends DestroyableVBox {
    DestroyableLabel name;
    List<DestroyableHBox> options;

    public SlotBoxEntry(final SlotBox slotBox, final List<ShipModule> shipModulesList) {
        this.getStyleClass().add("ships-modules");
        //add ship modules
        final ShipModule firstModule = shipModulesList.getFirst();
        final List<ShipModule> shipModules = (firstModule instanceof Armour)
                ? shipModulesList.stream().filter(shipModule -> ((Armour) shipModule).getShipType().equals(ApplicationState.getInstance().getShip().getShipType())).filter(ShipModule::isSelectable).toList()
                : shipModulesList.stream().filter(ShipModule::isSelectable).toList();
        this.name = LabelBuilder.builder()
                .withStyleClass("ships-modules-title")
                .withText(firstModule.getName().getBlueprintGroup().getLocalizationKey())
                .build();
        HBox.setHgrow(this.name, Priority.ALWAYS);
        Stream<List<ShipModule>> stream = shipModules.stream().allMatch(ShipModule::hasGrouping)
                ? shipModules.stream().collect(Collectors.groupingBy(shipModule -> String.valueOf(shipModule.getGrouping()))).values().stream()
                : shipModules.stream().collect(Collectors.groupingBy(shipModule -> shipModule.getModuleSize().toString() + shipModule.getGrouping() + shipModule.getOrigin().equals(Origin.POWERPLAY))).values().stream();
        stream = Stream.concat(stream, ApplicationState.getInstance().getPreferredCommander().map(commander -> {
            final ShipLegacyModules legacyModules = LegacyModuleService.loadModules(commander);
            return legacyModules.getLegacyModules().stream().map(this::mapToShipModule).filter(shipModule -> shipModule.getClass().equals(firstModule.getClass())).map(Collections::singletonList);
        }).orElseGet(Stream::empty));
        this.options = stream
                .sorted(Comparator.comparing(shipModuleSubList -> (shipModuleSubList.getFirst().hasGrouping()) ? String.valueOf(shipModuleSubList.getFirst().getGrouping()) : shipModuleSubList.getFirst().getModuleSize().toString() + shipModuleSubList.getFirst().getOrigin().equals(Origin.POWERPLAY)))
                .map(shipModuleSubList ->
                        shipModuleSubList.stream()
                                .map(shipModule -> createShipModuleButton(slotBox, shipModule))
                                .toArray(ShipModuleButton[]::new))
                .map(this::toButtonRow)
                .collect(Collectors.toList());
        final DestroyableVBox vBox = BoxBuilder.builder()
                .withStyleClass("ships-modules-item")
                .withNodes(BoxBuilder.builder()
                        .withNodes(new GrowingRegion(), this.name, new GrowingRegion())
                        .buildHBox())
                .buildVBox();
        vBox.getNodes().addAll(this.options);
        this.getNodes().add(vBox);

    }

    private DestroyableHBox toButtonRow(ShipModuleButton[] buttons) {
        final DestroyableHBox buttonRow = BoxBuilder.builder()
                .withStyleClass("ships-modules-buttons")
                .withNodes(buttons)
                .buildHBox();
        Arrays.stream(buttons)
                .forEach(button -> button.addBinding(button.prefWidthProperty(), this.widthProperty().subtract((buttons.length - 1) * buttonRow.getSpacing()).divide(buttons.length)));
        return buttonRow;
    }

    private ShipModuleButton createShipModuleButton(SlotBox slotBox, ShipModule shipModule) {
        //already pre-applied
        //pre engineered always uses modification, legacy has pre-applied
        final String buttonText = shipModule.getModuleSize()
                + ""
                + shipModule.getModuleClass()
                + (shipModule.isLegacy() ? " " + shipModule.getCustomName() : shipModule.getClarifier() + shipModule.getNonSortingClarifier());
        return ShipModuleButtonBuilder.builder()
                .withShipModule(shipModule)
                .withNonLocalizedText(buttonText)
                .withOnAction(getSelectModuleHandler(slotBox, shipModule))
                .withDisable(isButtonDisabled(slotBox, shipModule))
                .withFocusTraversable(false)
                .build();
    }

    private EventHandler<ActionEvent> getSelectModuleHandler(SlotBox slotBox, ShipModule shipModule) {
        return event -> {
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
        };
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

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        options.clear();
    }

    public boolean matches(String search) {
        boolean isCG = "community goal".contains(search) || "cg".contains(search);
        boolean isPreEngineered = "pre engineered".contains(search) || "pre-engineered".contains(search);
        boolean isLegacy = "legacy".contains(search);
        boolean isPowerplay = "powerplay".contains(search);
        return name.getText().toLowerCase().contains(search.toLowerCase())
                || options.stream()
                .anyMatch(box -> box.getChildren().stream()
                        .map(button -> ((ShipModuleButton) button).getShipModule())
                        .anyMatch(shipModule ->
                                LocaleService.getLocalizedStringForCurrentLocale(shipModule.getName().getLocalizationKey()).toLowerCase().contains(search.toLowerCase())
                                        || (isCG && shipModule.isCGExclusive())
                                        || (isPreEngineered && shipModule.isPreEngineered())
                                        || (isLegacy && shipModule.isLegacy())
                                        || (isPowerplay && shipModule.getOrigin().equals(Origin.POWERPLAY))));
    }
}
