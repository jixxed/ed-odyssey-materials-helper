package nl.jixxed.eliteodysseymaterials.templates.horizons.materials;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.constants.SpawnConstants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsMaterialsSearch;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsMaterialSearchEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class HorizonsMaterialOverview extends DestroyableVBox implements DestroyableEventTemplate {


    private static final String FLOW_PANE_STYLE_CLASS = "material-card-flow-pane";
    private DestroyableHBox nearestTraders;
    private HorizonsMaterialsSearch currentSearch = new HorizonsMaterialsSearch("", HorizonsMaterialsShow.ALL);
    private final List<DestroyableFlowPane> rawFlowPanes = new ArrayList<>();
    private final List<DestroyableFlowPane> encodedFlowPanes = new ArrayList<>();
    private final List<DestroyableFlowPane> manufacturedFlowPanes = new ArrayList<>();
    private DestroyableSeparator rawLine;
    private DestroyableSeparator encodedLine;
    private DestroyableSeparator manufacturedLine;

    HorizonsMaterialOverview() {
        final HorizonsMaterialsShow filter = HorizonsMaterialsShow.valueOf(PreferencesService.getPreference("search.horizons.materials.filter", "ALL"));
        currentSearch.setMaterialsShow(filter);
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("horizons-material-overview");
        final DestroyableFlowPane traders = FlowPaneBuilder.builder()
                .withOrientation(Orientation.HORIZONTAL)
                .withStyleClass("nearest-trader-flow")
                .withNodes(
                        new HorizonsNearestTrader(HorizonsStorageType.RAW),
                        new HorizonsNearestTrader(HorizonsStorageType.ENCODED),
                        new HorizonsNearestTrader(HorizonsStorageType.MANUFACTURED)
                )
                .build();
        this.nearestTraders = BoxBuilder.builder()
                .withNodes(traders)
                .buildHBox();
        HBox.setHgrow(traders, Priority.ALWAYS);

        rawLine = new DestroyableSeparator(Orientation.HORIZONTAL);
        encodedLine = new DestroyableSeparator(Orientation.HORIZONTAL);
        manufacturedLine = new DestroyableSeparator(Orientation.HORIZONTAL);

        initCards();
        update();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, HorizonsMaterialSearchEvent.class, horizonsCommoditiesSearchEvent -> {
            this.currentSearch = horizonsCommoditiesSearchEvent.getSearch();
            Platform.runLater(this::update);
        }));
    }

    private void update() {
        updateCategory(this.rawFlowPanes, rawLine);
        updateCategory(this.encodedFlowPanes, encodedLine);
        updateCategory(this.manufacturedFlowPanes, manufacturedLine);
        this.layoutChildren();
    }

    private void updateCategory(List<DestroyableFlowPane> rawFlowPanes, DestroyableSeparator rawLine) {
        rawFlowPanes.forEach(flowPane -> {
            final boolean match = flowPane.getChildren().stream()
                    .map(HorizonsMaterialCard.class::cast)
                    .anyMatch(materialCard -> searchForMaterial(materialCard.getMaterial()));
            flowPane.setVisible(match);
            flowPane.setManaged(match);
        });
        rawLine.setVisible(rawFlowPanes.stream().anyMatch(DestroyableFlowPane::isVisible));
        rawLine.setManaged(rawFlowPanes.stream().anyMatch(DestroyableFlowPane::isVisible));
    }

    private void initCards() {
        this.getNodes().add(this.nearestTraders);
        this.getNodes().add(rawLine);
        Arrays.stream(HorizonsMaterialType.getRawTypes()).forEach(type -> {
            final HorizonsMaterialCard[] array = Arrays.stream(Raw.values())
                    .filter(material -> material.getMaterialType().equals(type))
                    .map(HorizonsMaterialCard::new)
                    .sorted(Comparator.comparing(card -> card.getMaterial().getRarity()))
                    .toArray(HorizonsMaterialCard[]::new);
            rawFlowPanes.add(createMaterialCardRow(type, array));
        });

        this.getNodes().add(encodedLine);
        Arrays.stream(HorizonsMaterialType.getEncodedTypes()).forEach(type -> {
            final HorizonsMaterialCard[] array = Arrays.stream(Encoded.values())
                    .filter(material -> material.getMaterialType().equals(type))
                    .map(HorizonsMaterialCard::new)
                    .sorted(Comparator.comparing(card -> card.getMaterial().getRarity()))
                    .toArray(HorizonsMaterialCard[]::new);
            encodedFlowPanes.add(createMaterialCardRow(type, array));
        });

        this.getNodes().add(manufacturedLine);
        Arrays.stream(HorizonsMaterialType.getManufacturedTypes()).forEach(type -> {
            final HorizonsMaterialCard[] array = Arrays.stream(Manufactured.values())
                    .filter(material -> material.getMaterialType().equals(type))
                    .map(HorizonsMaterialCard::new)
                    .sorted(Comparator.comparing(card -> card.getMaterial().getRarity()))
                    .toArray(HorizonsMaterialCard[]::new);
            manufacturedFlowPanes.add(createMaterialCardRow(type, array));
        });
    }

    private boolean searchForMaterial(HorizonsMaterial material) {
        return (currentSearch.getQuery().isBlank()
                || LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey()).toLowerCase(LocaleService.getCurrentLocale()).contains(currentSearch.getQuery().toLowerCase(LocaleService.getCurrentLocale()))
                || searchForSpawnLocation(material)
        )
                && HorizonsMaterialsShow.getFilter(currentSearch).test(material);
    }

    private boolean searchForSpawnLocation(HorizonsMaterial material) {
        final List<HorizonsMaterialSpawnLocation> horizonsMaterialSpawnLocations = SpawnConstants.HORIZONSMATERIAL_LOCATION.get(material);
        if (horizonsMaterialSpawnLocations != null && !horizonsMaterialSpawnLocations.isEmpty()) {
            return horizonsMaterialSpawnLocations.stream().map(horizonsMaterialSpawnLocation -> LocaleService.getLocalizedStringForCurrentLocale(horizonsMaterialSpawnLocation.getLocalizationKey())).anyMatch(spawn -> spawn.toLowerCase().contains(currentSearch.getQuery().toLowerCase(LocaleService.getCurrentLocale())));
        }
        return false;
    }

    private DestroyableFlowPane createMaterialCardRow(final HorizonsMaterialType type, final HorizonsMaterialCard[] array) {
//        final DestroyableLabel category = LabelBuilder.builder()
//                .withStyleClass("row-name")
//                .withText(type.getLocalizationKey())
//                .build();
        final DestroyableFlowPane materials = FlowPaneBuilder.builder()
                .withStyleClass(FLOW_PANE_STYLE_CLASS)
                .withNodes(array)
                .build();
        HBox.setHgrow(materials, Priority.ALWAYS);
        final DestroyableHBox row = BoxBuilder.builder()
                .withStyleClass("category-row")
                .withNodes(/*category,*/ materials)
                .buildHBox();
        row.addBinding(row.visibleProperty(), materials.visibleProperty());
        row.addBinding(row.managedProperty(), materials.managedProperty());
        this.getNodes().add(row);
        return materials;
    }
}
