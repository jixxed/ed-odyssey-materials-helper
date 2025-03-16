package nl.jixxed.eliteodysseymaterials.templates.horizons.materials;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.SpawnConstants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsMaterialsSearch;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsMaterialSearchEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class HorizonsMaterialOverview extends DestroyableVBox implements DestroyableEventTemplate {

    private HorizonsMaterialCard[] rawCards;
    private HorizonsMaterialCard[] encodedCards;
    private HorizonsMaterialCard[] manufacturedCards;
    private DestroyableHBox nearestTraders;
    private HorizonsMaterialsSearch currentSearch = new HorizonsMaterialsSearch("", HorizonsMaterialsShow.ALL);

    HorizonsMaterialOverview() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("horizons-material-overview");
//        Arrays.stream(HorizonsMaterialType.getRawTypes()).forEach(type -> {
//
//        });
        final DestroyableFlowPane traders = FlowPaneBuilder.builder()
                .withOrientation(Orientation.HORIZONTAL)
                .withStyleClass("nearest-trader-flow")
                .withNodes(
                        new HorizonsNearestTrader(HorizonsStorageType.RAW),
                        new HorizonsNearestTrader(HorizonsStorageType.ENCODED),
                        new HorizonsNearestTrader(HorizonsStorageType.MANUFACTURED)
                )
                .build();
        final DestroyableLabel nearestTradersTitle = LabelBuilder.builder()
                .withStyleClass("horizons-material-overview-row-name")
                .withText("horizons.materials.nearest.trader")
                .build();
        this.nearestTraders = BoxBuilder.builder()
                .withNodes(nearestTradersTitle, traders)
                .buildHBox();
        HBox.setHgrow(traders, Priority.ALWAYS);
        this.nearestTraders.addBinding(this.nearestTraders.spacingProperty(), ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        this.rawCards = Arrays.stream(Raw.values()).map(HorizonsMaterialCard::new).toList().toArray(HorizonsMaterialCard[]::new);
        this.encodedCards = Arrays.stream(Encoded.values()).map(HorizonsMaterialCard::new).toList().toArray(HorizonsMaterialCard[]::new);
        this.manufacturedCards = Arrays.stream(Manufactured.values()).map(HorizonsMaterialCard::new).toList().toArray(HorizonsMaterialCard[]::new);

        update();
        this.addBinding(this.spacingProperty(), ScalingHelper.getPixelDoubleBindingFromEm(0.5));
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, HorizonsMaterialSearchEvent.class, horizonsCommoditiesSearchEvent -> {
            this.currentSearch = horizonsCommoditiesSearchEvent.getSearch();
            Platform.runLater(this::update);
        }));
    }

    private void update() {
        this.getNodes().clear();
        this.getNodes().add(this.nearestTraders);
        final DestroyableSeparator rawLine = new DestroyableSeparator(Orientation.HORIZONTAL);
        final DestroyableSeparator encodedLine = new DestroyableSeparator(Orientation.HORIZONTAL);
        final DestroyableSeparator manufacturedLine = new DestroyableSeparator(Orientation.HORIZONTAL);
        this.getNodes().add(rawLine);
        AtomicBoolean hasRaw = new AtomicBoolean(false);
        AtomicBoolean hasEncoded = new AtomicBoolean(false);
        AtomicBoolean hasManufactured = new AtomicBoolean(false);
        Arrays.stream(HorizonsMaterialType.getRawTypes()).forEach(type -> {
            if (Arrays.stream(Raw.materialsForType(type)).anyMatch(this::searchForMaterial)) {
                final HorizonsMaterialCard[] array = Arrays.stream(this.rawCards).filter(horizonsMaterialCard -> horizonsMaterialCard.getMaterial().getMaterialType().equals(type)).sorted(Comparator.comparing(card -> card.getMaterial().getRarity())).toList().toArray(HorizonsMaterialCard[]::new);
                createMaterialCardRow(type, array);
                hasRaw.set(true);
            }
        });
        if (!hasRaw.get()) {
            this.getNodes().remove(rawLine);
        }
        this.getNodes().add(encodedLine);
        Arrays.stream(HorizonsMaterialType.getEncodedTypes()).forEach(type -> {
            if (Arrays.stream(Encoded.materialsForType(type)).anyMatch(this::searchForMaterial)) {
                final HorizonsMaterialCard[] array = Arrays.stream(this.encodedCards).filter(horizonsMaterialCard -> horizonsMaterialCard.getMaterial().getMaterialType().equals(type)).sorted(Comparator.comparing(card -> card.getMaterial().getRarity())).toList().toArray(HorizonsMaterialCard[]::new);
                createMaterialCardRow(type, array);
                hasEncoded.set(true);

            }
        });
        if (!hasEncoded.get()) {
            this.getNodes().remove(encodedLine);
        }
        this.getNodes().add(manufacturedLine);
        Arrays.stream(HorizonsMaterialType.getManufacturedTypes()).forEach(type -> {
            if (Arrays.stream(Manufactured.materialsForType(type)).anyMatch(this::searchForMaterial)) {
                final HorizonsMaterialCard[] array = Arrays.stream(this.manufacturedCards).filter(horizonsMaterialCard -> horizonsMaterialCard.getMaterial().getMaterialType().equals(type)).sorted(Comparator.comparing(card -> card.getMaterial().getRarity())).toList().toArray(HorizonsMaterialCard[]::new);
                createMaterialCardRow(type, array);
                hasManufactured.set(true);
            }
        });
        if (!hasManufactured.get()) {
            this.getNodes().remove(manufacturedLine);
        }
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

    private void createMaterialCardRow(final HorizonsMaterialType type, final HorizonsMaterialCard[] array) {
        final DestroyableLabel category = LabelBuilder.builder()
                .withStyleClass("horizons-material-overview-row-name")
                .withText(type.getLocalizationKey())
                .build();
        final DestroyableFlowPane materials = FlowPaneBuilder.builder()
                .withNodes(array)
                .build();
        materials.addBinding(materials.vgapProperty(), ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        materials.addBinding(materials.hgapProperty(), ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        HBox.setHgrow(materials, Priority.ALWAYS);
        final DestroyableHBox row = BoxBuilder.builder()
                .withNodes(category, materials).buildHBox();
        row.addBinding(row.spacingProperty(), ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        this.getNodes().add(row);
    }
}
