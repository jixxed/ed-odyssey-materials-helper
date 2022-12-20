package nl.jixxed.eliteodysseymaterials.templates.horizons.materials;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsMaterialSearchEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class HorizonsMaterialOverview extends VBox implements Template {

    private HorizonsMaterialCard[] rawCards;
    private HorizonsMaterialCard[] encodedCards;
    private HorizonsMaterialCard[] manufacturedCards;
    private HBox nearestTraders;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
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

        update("");
        this.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.5));
    }

    private void update(final String search) {
        this.getChildren().clear();
        this.getChildren().add(this.nearestTraders);
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        Arrays.stream(HorizonsMaterialType.getRawTypes()).forEach(type -> {
            if (Arrays.stream(Raw.materialsForType(type)).anyMatch(raw -> search.isBlank() || LocaleService.getLocalizedStringForCurrentLocale(raw.getLocalizationKey()).toLowerCase(LocaleService.getCurrentLocale()).contains(search.toLowerCase(LocaleService.getCurrentLocale())))) {
                final HorizonsMaterialCard[] array = Arrays.stream(this.rawCards).filter(horizonsMaterialCard -> horizonsMaterialCard.getMaterial().getMaterialType().equals(type)).sorted(Comparator.comparing(card -> card.getMaterial().getRarity())).toList().toArray(HorizonsMaterialCard[]::new);
                createMaterialCardRow(type, array);
            }
        });
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        Arrays.stream(HorizonsMaterialType.getEncodedTypes()).forEach(type -> {
            if (Arrays.stream(Encoded.materialsForType(type)).anyMatch(encoded -> search.isBlank() || LocaleService.getLocalizedStringForCurrentLocale(encoded.getLocalizationKey()).toLowerCase(LocaleService.getCurrentLocale()).contains(search.toLowerCase(LocaleService.getCurrentLocale())))) {
                final HorizonsMaterialCard[] array = Arrays.stream(this.encodedCards).filter(horizonsMaterialCard -> horizonsMaterialCard.getMaterial().getMaterialType().equals(type)).sorted(Comparator.comparing(card -> card.getMaterial().getRarity())).toList().toArray(HorizonsMaterialCard[]::new);
                createMaterialCardRow(type, array);

            }
        });
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        Arrays.stream(HorizonsMaterialType.getManufacturedTypes()).forEach(type -> {
            if (Arrays.stream(Manufactured.materialsForType(type)).anyMatch(manufactured -> search.isBlank() || LocaleService.getLocalizedStringForCurrentLocale(manufactured.getLocalizationKey()).toLowerCase(LocaleService.getCurrentLocale()).contains(search.toLowerCase(LocaleService.getCurrentLocale())))) {
                final HorizonsMaterialCard[] array = Arrays.stream(this.manufacturedCards).filter(horizonsMaterialCard -> horizonsMaterialCard.getMaterial().getMaterialType().equals(type)).sorted(Comparator.comparing(card -> card.getMaterial().getRarity())).toList().toArray(HorizonsMaterialCard[]::new);
                createMaterialCardRow(type, array);
            }
        });
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
        //NOOP
        this.eventListeners.add(EventService.addListener(this, HorizonsMaterialSearchEvent.class, horizonsMaterialSearchEvent -> {
            update(horizonsMaterialSearchEvent.getSearch());
        }));
    }
}
