package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ShipModuleButtonBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.ExternalModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Mounting;
import nl.jixxed.eliteodysseymaterials.domain.ships.Origin;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.Armour;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ModuleHighlightEvent;
import nl.jixxed.eliteodysseymaterials.service.event.ShipBuilderEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class SlotBoxEntry extends VBox {
    Label name;
    List<HBox> options;
    private HorizonsShipBuilderTab tab;
    public SlotBoxEntry(final HorizonsShipBuilderTab tab, final SlotBox slotBox, final List<ShipModule> shipModulesList) {
        this.tab = tab;
        this.getStyleClass().add("ships-modules");
        //add ship modules
        final ShipModule firstModule = shipModulesList.get(0);
        final List<ShipModule> shipModules = (firstModule instanceof Armour)
                ? shipModulesList.stream().filter(shipModule -> ((Armour) shipModule).getShipType().equals(ApplicationState.getInstance().getShip().getShipType())).toList()
                : shipModulesList;
        this.name = LabelBuilder.builder().withStyleClass("ships-modules-title").withText(LocaleService.getStringBinding(firstModule.getName().getBlueprintGroup().getLocalizationKey())).build();
        HBox.setHgrow(this.name, Priority.ALWAYS);
        final Stream<List<ShipModule>> stream = shipModules.stream().allMatch(shipModule -> shipModule.getModuleClass().equals(firstModule.getModuleClass()) && shipModule.getClarifier().isEmpty())
                ? Stream.of(shipModules)
                : shipModules.stream().collect(Collectors.groupingBy(shipModule -> shipModule.getModuleSize().toString() + (shipModule.groupOnName() ? shipModule.getName().toString() : "") + shipModule.getClarifier())).values().stream();
//        final Stream<List<ShipModule>> stream =shipModules.stream().collect(Collectors.groupingBy(shipModule -> shipModule.getModuleSize() + shipModule.getClarifier())).values().stream();
        this.options =
                stream.sorted(Comparator.comparing(shipModuleSubList -> shipModuleSubList.get(0).getModuleSize().toString() + (shipModuleSubList.get(0).groupOnName() ? shipModuleSubList.get(0).getName().toString() : "") + shipModuleSubList.get(0).getClarifier())).map(shipModuleSubList ->
                                shipModuleSubList.stream().map(shipModule -> {
                                            final ShipModuleButton button = ShipModuleButtonBuilder.builder().withShipModule(shipModule).withNonLocalizedText(shipModule.getModuleSize() + "" + shipModule.getModuleClass() + shipModule.getClarifier() + shipModule.getNonSortingClarifier()).withOnAction(event -> {
                                                final ShipModule clone = shipModule.Clone();
                                                if(slotBox.getSlot().getShipModule() != null) {
                                                    if(isSimilar(slotBox.getSlot().getShipModule(), clone) ) {
                                                        if(!clone.isPreEngineered()){//already pre-applied
                                                            clone.getModifications().addAll(slotBox.getSlot().getShipModule().getModifications());
                                                        }
                                                        clone.getExperimentalEffects().addAll(slotBox.getSlot().getShipModule().getExperimentalEffects());
                                                    }
                                                }
                                                slotBox.getSlot().setShipModule(clone);
                                                notifyChanged();
                                                slotBox.refresh();
                                                slotBox.close();
                                            }).build();

                                            button.setOnMouseEntered(event -> {
                                                    EventService.publish(new ModuleHighlightEvent(shipModule));
                                            });
                                            List<DestroyableResizableImageView> images = new ArrayList<>();
                                            if(shipModule instanceof ExternalModule externalModule && !externalModule.getMounting().equals(Mounting.NA)) {
                                                images.add(ResizableImageViewBuilder.builder().withImage("/images/ships/icons/" + externalModule.getMounting().name().toLowerCase() + ".png").withStyleClass("ships-button-image").build());
                                            }
                                            if(Origin.GUARDIAN.equals(shipModule.getOrigin()) || Origin.POWERPLAY.equals(shipModule.getOrigin())) {
                                                images.add(ResizableImageViewBuilder.builder().withImage("/images/ships/icons/" + shipModule.getOrigin().name().toLowerCase() + ".png").withStyleClass("ships-button-image").build());
                                            }
                                            if(shipModule.isLegacy()) {
                                                images.add(ResizableImageViewBuilder.builder().withImage("/images/ships/icons/legacy.png").withStyleClass("ships-button-image").build());
                                            }
                                            if(shipModule.isPreEngineered()) {
                                                images.add(ResizableImageViewBuilder.builder().withImage("/images/ships/icons/preengineered.png").withStyleClass("ships-button-image").build());
                                            }
                                            if(shipModule.isCGExclusive()) {
                                                images.add(ResizableImageViewBuilder.builder().withImage("/images/ships/icons/cg.png").withStyleClass("ships-button-image").build());
                                            }
                                            if(!images.isEmpty()){
                                                HBox imagesBox = BoxBuilder.builder().withStyleClass("ships-button-image-box").withNodes(images.toArray(DestroyableResizableImageView[]::new)).buildHBox();
                                                button.setGraphic(imagesBox);

                                            }
//                                    HBox.setHgrow(button, Priority.ALWAYS);
                                            button.setFocusTraversable(false);
                                            button.setDisable(slotBox.getSlot().getSlotSize() < shipModule.getModuleSize().intValue());
                                            return button;
                                        }
                                ).toArray(ShipModuleButton[]::new)
                ).map(buttons -> {
                    final HBox hBox = BoxBuilder.builder().withStyleClass("ships-modules-buttons").withNodes(buttons).buildHBox();
                    Arrays.stream(buttons).forEach(button -> button.prefWidthProperty().bind(this.widthProperty().subtract((buttons.length - 1) * hBox.getSpacing()).divide(buttons.length)));
                    return hBox;
                }).toList();
        final VBox vBox = BoxBuilder.builder().withStyleClass("ships-modules-item").withNodes(BoxBuilder.builder().withNodes(new GrowingRegion(), this.name, new GrowingRegion()).buildHBox()).buildVBox();
        vBox.getChildren().addAll(this.options);
        this.getChildren().add(vBox);

    }

    private boolean isSimilar(ShipModule shipModule, ShipModule clone) {
        final boolean b = shipModule.getName().getBlueprintGroup().equals(clone.getName().getBlueprintGroup()) &&
                shipModule.getAllowedBlueprints().size() == clone.getAllowedBlueprints().size() &&
                shipModule.getAllowedExperimentalEffects().size() == clone.getAllowedExperimentalEffects().size() &&
                shipModule.getAllowedBlueprints().containsAll(clone.getAllowedBlueprints()) &&
                shipModule.getAllowedExperimentalEffects().containsAll(clone.getAllowedExperimentalEffects());
        log.debug("similar:" + b);
        return b;
    }

    //    private void addEngineering(final SlotBox slotBox) {
//        final ShipModule shipModule = slotBox.getSlot().getShipModule();
//        if(shipModule != null && !shipModule.getAllowedBlueprints().isEmpty()){
//            final ToggleGroup toggleGroup = new ToggleGroup();
//            final List<ToggleButton> toggleButtons = shipModule.getAllowedBlueprints().stream().sorted(Comparator.comparing(horizonsBlueprintType -> LocaleService.getLocalizedStringForCurrentLocale(horizonsBlueprintType.getLocalizationKey()))).map(horizonsBlueprintType -> {
//
//                        final ToggleButton button = ToggleButtonBuilder.builder().withStyleClass("toggle-button-blueprints").withText(LocaleService.getStringBinding(horizonsBlueprintType.getLocalizationKey())).withOnAction(event -> {
//                            slotBox.getSlot().setEngineering(((ToggleButton)event.getTarget()).isSelected() ? horizonsBlueprintType : null);
//                            slotBox.refresh();
//                        }).build();
//                        button.selectedProperty().set(horizonsBlueprintType.equals(slotBox.getSlot().getEngineering()));
//                        button.selectedProperty().addListener((observable, oldValue, newValue) ->
//                        {
//                            if(Boolean.TRUE.equals(oldValue)){
//                                slotBox.getSlot().setEngineering(null);
//                                slotBox.refresh();
//                            }
//                            if (Boolean.TRUE.equals(newValue)) {
//                                slotBox.getSlot().setEngineering(horizonsBlueprintType);
//                                slotBox.refresh();
//                            }
//                        });
//                        button.setFocusTraversable(false);
//                        button.setToggleGroup(toggleGroup);
//                        return button;
//                    }
//
//            ).toList();
//
//            this.getChildren().add(LabelBuilder.builder().withText(LocaleService.getStringBinding("tabs.ships.blueprints")).build());
//            this.getChildren().addAll(toggleButtons);
//        }
//
//    }

    private void notifyChanged() {
        EventService.publish(new ShipBuilderEvent());
    }
}
