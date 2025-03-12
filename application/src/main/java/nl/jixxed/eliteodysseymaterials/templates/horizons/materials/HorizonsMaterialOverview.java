package nl.jixxed.eliteodysseymaterials.templates.horizons.materials;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class HorizonsMaterialOverview extends VBox implements DestroyableTemplate {

    private HorizonsMaterialCard[] rawCards;
    private HorizonsMaterialCard[] encodedCards;
    private HorizonsMaterialCard[] manufacturedCards;
    private HBox nearestTraders;
    private HorizonsMaterialsSearch currentSearch = new HorizonsMaterialsSearch("", HorizonsMaterialsShow.ALL);

    HorizonsMaterialOverview() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("horizons-material-overview");
        Arrays.stream(HorizonsMaterialType.getRawTypes()).forEach(type -> {

        });
        final FlowPane traders = FlowPaneBuilder.builder().withOrientation(Orientation.HORIZONTAL).withStyleClass("nearest-trader-flow").withNodes(new HorizonsNearestTrader[]{new HorizonsNearestTrader(HorizonsStorageType.RAW), new HorizonsNearestTrader(HorizonsStorageType.ENCODED), new HorizonsNearestTrader(HorizonsStorageType.MANUFACTURED)}).build();
        final DestroyableLabel nearestTradersTitle = LabelBuilder.builder().withStyleClass("horizons-material-overview-row-name").withText(LocaleService.getStringBinding("horizons.materials.nearest.trader")).build();
        this.nearestTraders = BoxBuilder.builder().withNodes(nearestTradersTitle, traders).buildHBox();
        HBox.setHgrow(traders, Priority.ALWAYS);
        this.nearestTraders.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        this.rawCards = Arrays.stream(Raw.values()).map(HorizonsMaterialCard::new).toList().toArray(HorizonsMaterialCard[]::new);
        this.encodedCards = Arrays.stream(Encoded.values()).map(HorizonsMaterialCard::new).toList().toArray(HorizonsMaterialCard[]::new);
        this.manufacturedCards = Arrays.stream(Manufactured.values()).map(HorizonsMaterialCard::new).toList().toArray(HorizonsMaterialCard[]::new);

        update();
        this.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.5));
    }

    private void update() {
        this.getChildren().clear();
        this.getChildren().add(this.nearestTraders);
        final Separator rawLine = new Separator(Orientation.HORIZONTAL);
        final Separator encodedLine = new Separator(Orientation.HORIZONTAL);
        final Separator manufacturedLine = new Separator(Orientation.HORIZONTAL);
        this.getChildren().add(rawLine);
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
        if(!hasRaw.get()){
            this.getChildren().remove(rawLine);
        }
        this.getChildren().add(encodedLine);
        Arrays.stream(HorizonsMaterialType.getEncodedTypes()).forEach(type -> {
            if (Arrays.stream(Encoded.materialsForType(type)).anyMatch(this::searchForMaterial)) {
                final HorizonsMaterialCard[] array = Arrays.stream(this.encodedCards).filter(horizonsMaterialCard -> horizonsMaterialCard.getMaterial().getMaterialType().equals(type)).sorted(Comparator.comparing(card -> card.getMaterial().getRarity())).toList().toArray(HorizonsMaterialCard[]::new);
                createMaterialCardRow(type, array);
                hasEncoded.set(true);

            }
        });
        if(!hasEncoded.get()){
            this.getChildren().remove(encodedLine);
        }
        this.getChildren().add(manufacturedLine);
        Arrays.stream(HorizonsMaterialType.getManufacturedTypes()).forEach(type -> {
            if (Arrays.stream(Manufactured.materialsForType(type)).anyMatch(this::searchForMaterial)) {
                final HorizonsMaterialCard[] array = Arrays.stream(this.manufacturedCards).filter(horizonsMaterialCard -> horizonsMaterialCard.getMaterial().getMaterialType().equals(type)).sorted(Comparator.comparing(card -> card.getMaterial().getRarity())).toList().toArray(HorizonsMaterialCard[]::new);
                createMaterialCardRow(type, array);
                hasManufactured.set(true);
            }
        });
        if(!hasManufactured.get()){
            this.getChildren().remove(manufacturedLine);
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
        final DestroyableLabel category = LabelBuilder.builder().withStyleClass("horizons-material-overview-row-name").withText(LocaleService.getStringBinding(type.getLocalizationKey())).build();
        final FlowPane materials = FlowPaneBuilder.builder().withNodes(array).build();
        materials.vgapProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        materials.hgapProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        HBox.setHgrow(materials, Priority.ALWAYS);
        final HBox row = BoxBuilder.builder().withNodes(category, materials).buildHBox();
        row.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        this.getChildren().add(row);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, HorizonsMaterialSearchEvent.class, horizonsCommoditiesSearchEvent -> {
            this.currentSearch = horizonsCommoditiesSearchEvent.getSearch();
            Platform.runLater(this::update);
        }));
    }
}
