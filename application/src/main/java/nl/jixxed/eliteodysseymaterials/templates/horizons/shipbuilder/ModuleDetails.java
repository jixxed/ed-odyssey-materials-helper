package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.HardpointModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LegacyModuleSavedEvent;
import nl.jixxed.eliteodysseymaterials.service.ships.LegacyModuleService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class ModuleDetails extends DestroyableVBox implements DestroyableEventTemplate {
    private DestroyableFlowPane properties;
    private DestroyableFlowPane attributes;
    private DestroyableLabel legacySaveButton;
    private DestroyableLabel moduleName;
    private ShipModule shipModule;

    public ModuleDetails(ShipModule shipModule) {
        this.shipModule = shipModule;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("module-details");
        legacySaveButton = LabelBuilder.builder()
                .withStyleClass("legacy-save")
                .withText("module.details.save.legacy")
                .withVisibility(false)
                .withManaged(false)
                .withOnMouseClicked(_ -> {
                    LegacyModuleService.saveLegacyModule(shipModule);
                    EventService.publish(new LegacyModuleSavedEvent());
                })
                .build();
        this.getNodes().add(legacySaveButton);
        moduleName = LabelBuilder.builder()
                .withStyleClass("title")
                .withNonLocalizedText("")
                .build();
        final DestroyableLabel moduleSpecs = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("module.details.module.specs")
                .build();
        properties = FlowPaneBuilder.builder()
                .withStyleClass("properties")
                .build();
        attributes = FlowPaneBuilder.builder()
                .withStyleClass("attributes")
                .build();
        this.getNodes().addAll(BoxBuilder.builder().withNodes(moduleName, new GrowingRegion(), legacySaveButton).buildHBox(), properties, moduleSpecs, attributes);
        update();
    }

    @Override
    public void initEventHandling() {

    }

    private void addEngineering(String text, int index, int size) {
        properties.getNodes().add(new ModuleDetailsProperty("module.details.engineering", "notification.value.text", text, index, size));//TODO add locale value (not notification)
    }

    private void addPrice(String text, int index, int size) {
        properties.getNodes().add(new ModuleDetailsProperty("module.details.price", "module.details.price.value", text, index, size));

    }

    private void addBuyPrice(String text, int index, int size) {
        properties.getNodes().add(new ModuleDetailsProperty("module.details.buyprice", "module.details.buyprice.value", text, index, size));
    }

    private void addRebuyPrice(String text, int index, int size) {
        properties.getNodes().add(new ModuleDetailsProperty("module.details.rebuyprice", "module.details.rebuyprice.value", text, index, size));
    }

    private void update() {
        legacySaveButton.setVisible(false);
        legacySaveButton.setManaged(false);
        final boolean modulePresent = shipModule != null;
        attributes.getNodes().clear();
        properties.getNodes().clear();
        if (modulePresent) {
            int propertyIndex = 0;
            int size = 2 + (shipModule.getModifications().isEmpty() ? 0 : 1) + (shipModule.getBuyPrice() != null ? 1 : 0);
            addPrice(Formatters.NUMBER_FORMAT_0.format(shipModule.getBasePrice()), propertyIndex++, size);
            if (shipModule.getBuyPrice() != null) {
                addBuyPrice(Formatters.NUMBER_FORMAT_0.format(shipModule.getBuyPrice()), propertyIndex++, size);
            }
            addRebuyPrice(Formatters.NUMBER_FORMAT_0.format((shipModule.getBuyPrice() != null ? shipModule.getBuyPrice() : shipModule.getBasePrice()) * 0.05), propertyIndex++, size);
            if (!shipModule.getModifications().isEmpty()) {
                addEngineering(Stream.concat(shipModule.getModifications().stream()
                                        .map(modification -> LocaleService.getLocalizedStringForCurrentLocale(modification.getModification().getLocalizationKey())),
                                shipModule.getExperimentalEffects().stream()
                                        .map(modification -> LocaleService.getLocalizedStringForCurrentLocale(modification.getLocalizationKey())))
                        .collect(Collectors.joining(", ")), propertyIndex++, size);
            }
            moduleName.setText(LocaleService.getLocalizedStringForCurrentLocale(shipModule.getLocalizationKey()) + " " + shipModule.getModuleSize().intValue() + shipModule.getModuleClass() + ((shipModule instanceof HardpointModule hardpointModule ? "-" + hardpointModule.getMounting().getShortName() : "")));

            List<HorizonsModifier> attributesList = shipModule.getAttibutes().stream().filter(Predicate.not(shipModule::isHiddenStat)).sorted(Comparator.comparing(HorizonsModifier::getOrder)).toList();

            for (int i = 0; i < attributesList.size(); i++) {
                HorizonsModifier horizonsModifier = attributesList.get(i);
                addAttribute(horizonsModifier, shipModule, i, attributesList.size());
            }
            if (shipModule.isLegacy()) {
                legacySaveButton.setVisible(true);
                legacySaveButton.setManaged(true);
            }
        }
    }

    private void addAttribute(HorizonsModifier horizonsModifier, ShipModule shipModule, int index, int size) {
        ModuleDetailsAttribute moduleDetailsAttribute = new ModuleDetailsAttribute(horizonsModifier, shipModule, index, size);
        attributes.getNodes().add(moduleDetailsAttribute);

    }

}
