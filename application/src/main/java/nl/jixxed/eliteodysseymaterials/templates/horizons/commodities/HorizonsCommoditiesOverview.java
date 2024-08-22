package nl.jixxed.eliteodysseymaterials.templates.horizons.commodities;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.CommoditiesSearch;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsCommoditiesSearchEvent;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class HorizonsCommoditiesOverview extends VBox implements Template {
    private HorizonsCommodityCard[] commodityCards;
    private CommoditiesSearch currentSearch = new CommoditiesSearch("", HorizonsCommoditiesSort.ALPHABETICAL, HorizonsCommoditiesShow.ALL);
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    HorizonsCommoditiesOverview() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("horizons-material-overview");
        this.commodityCards = Arrays.stream(Commodity.values()).filter(Predicate.not(Commodity::isUnknown)).map(HorizonsCommodityCard::new).toList().toArray(HorizonsCommodityCard[]::new);
        update();
        this.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.5));
    }

    private void update() {
        this.getChildren().clear();
        final List<HBox> rows = Arrays.stream(CommodityType.values()).filter(Predicate.not(CommodityType::isUnknown)).map(commodityType -> {
            final HorizonsCommodityCard[] horizonsCommodityCardsForType = Arrays.stream(this.commodityCards)
                    .filter(card -> card.getCommodity().getCommodityType().equals(commodityType)).toList()
                    .toArray(HorizonsCommodityCard[]::new);
            if (hasCardsVisibleInGroup(horizonsCommodityCardsForType)) {
                final HorizonsCommodityCard[] array = Arrays.stream(horizonsCommodityCardsForType)
                        .filter(HorizonsCommoditiesShow.getFilter(this.currentSearch))
                        .filter(card -> this.currentSearch.getQuery().isBlank() || LocaleService.getLocalizedStringForCurrentLocale(card.getCommodity().getLocalizationKey()).toLowerCase(LocaleService.getCurrentLocale()).contains(this.currentSearch.getQuery().toLowerCase(LocaleService.getCurrentLocale())) || LocaleService.getLocalizedStringForCurrentLocale(card.getCommodity().getCommodityType().getLocalizationKey()).toLowerCase(LocaleService.getCurrentLocale()).contains(this.currentSearch.getQuery().toLowerCase(LocaleService.getCurrentLocale())))
                        .sorted(HorizonsCommoditiesSort.getSort(this.currentSearch))
                        .toList()
                        .toArray(HorizonsCommodityCard[]::new);
                return createCommodityCardRow(commodityType, array);
            }
            return null;
        }).filter(Objects::nonNull).toList();

        this.getChildren().addAll(rows);
    }

    private boolean hasCardsVisibleInGroup(final HorizonsCommodityCard[] horizonsCommodityCardsForType) {
        return Arrays.stream(horizonsCommodityCardsForType).anyMatch(card -> (this.currentSearch.getQuery().isBlank() || LocaleService.getLocalizedStringForCurrentLocale(card.getCommodity().getLocalizationKey()).toLowerCase(LocaleService.getCurrentLocale()).contains(this.currentSearch.getQuery().toLowerCase(LocaleService.getCurrentLocale())) || LocaleService.getLocalizedStringForCurrentLocale(card.getCommodity().getCommodityType().getLocalizationKey()).toLowerCase(LocaleService.getCurrentLocale()).contains(this.currentSearch.getQuery().toLowerCase(LocaleService.getCurrentLocale()))) && HorizonsCommoditiesShow.getFilter(this.currentSearch).test(card));
    }

    private HBox createCommodityCardRow(final CommodityType type, final HorizonsCommodityCard[] array) {
        final DestroyableLabel category = LabelBuilder.builder().withStyleClass("horizons-material-overview-row-name").withText(LocaleService.getStringBinding(type.getLocalizationKey())).build();
        final FlowPane commodities = FlowPaneBuilder.builder().withNodes(array).build();
        commodities.vgapProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        commodities.hgapProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        HBox.setHgrow(commodities, Priority.ALWAYS);
        final HBox row = BoxBuilder.builder().withNodes(category, commodities).buildHBox();
        row.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        return row;
    }

    @Override
    public void initEventHandling() {

        Observable
                .create(emitter -> this.eventListeners.add(EventService.addListener(true, this, StorageEvent.class, storageEvent -> {
                    if (StoragePool.SHIP.equals(storageEvent.getStoragePool()) || StoragePool.FLEETCARRIER.equals(storageEvent.getStoragePool())) {
                        emitter.onNext(storageEvent);
                    }
                })))
                .debounce(250, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(storageEvent -> Platform.runLater(this::update));

        this.eventListeners.add(EventService.addListener(true, this, HorizonsCommoditiesSearchEvent.class, horizonsCommoditiesSearchEvent -> {
            this.currentSearch = horizonsCommoditiesSearchEvent.getSearch();
            Platform.runLater(this::update);
        }));
    }
}
