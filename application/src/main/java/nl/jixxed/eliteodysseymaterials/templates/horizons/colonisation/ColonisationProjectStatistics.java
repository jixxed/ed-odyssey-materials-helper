package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItem;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItems;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.ColonisationService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.ColonisationSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipLoadoutEvent;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class ColonisationProjectStatistics extends DestroyableVBox implements DestroyableEventTemplate {
    ColonisationItem colonisationItem;
    private DestroyableLabel requiredValue;
    private DestroyableLabel deliveredValue;
    private DestroyableLabel toCollectValue;
    private DestroyableLabel toDeliverValue;

    public ColonisationProjectStatistics() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("project-stats");
        DestroyableLabel requiredTitle = LabelBuilder.builder()
                .withStyleClass("title-amount")
                .withText("tab.colonisation.material.total.required")
                .build();
        requiredValue = LabelBuilder.builder()
                .withStyleClass("amount")
                .withNonLocalizedText("0")
                .build();
        DestroyableLabel deliveredTitle = LabelBuilder.builder()
                .withStyleClass("title-amount")
                .withText("tab.colonisation.material.total.delivered")
                .build();
        deliveredValue = LabelBuilder.builder()
                .withStyleClass("amount")
                .withNonLocalizedText("0")
                .build();
        DestroyableLabel toCollectTitle = LabelBuilder.builder()
                .withStyleClass("title-amount")
                .withText("tab.colonisation.material.total.tocollect")
                .build();
        toCollectValue = LabelBuilder.builder()
                .withStyleClass("amount")
                .withNonLocalizedText("0")
                .build();
        DestroyableLabel toDeliverTitle = LabelBuilder.builder()
                .withStyleClass("title-amount")
                .withText("tab.colonisation.material.total.todeliver")
                .build();
        toDeliverValue = LabelBuilder.builder()
                .withStyleClass("amount")
                .withNonLocalizedText("0")
                .build();
        var required = BoxBuilder.builder()
                .withNodes(requiredTitle, new GrowingRegion(), requiredValue)
                .buildHBox();
        var delivered = BoxBuilder.builder()
                .withNodes(deliveredTitle, new GrowingRegion(), deliveredValue)
                .buildHBox();
        var toCollect = BoxBuilder.builder()
                .withNodes(toCollectTitle, new GrowingRegion(), toCollectValue)
                .buildHBox();
        var toDeliver = BoxBuilder.builder()
                .withNodes(toDeliverTitle, new GrowingRegion(), toDeliverValue)
                .buildHBox();
        DestroyableLabel statsTitle = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("tab.colonisation.material.stats")
                .build();
        this.getChildren().addAll(statsTitle, required, delivered, toCollect, toDeliver);
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
            setBuildable(colonisationItems.getSelectedColonisationItem());
        });
    }

    public void setBuildable(ColonisationItem colonisationItem) {
        this.colonisationItem = colonisationItem;
        update();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, StorageEvent.class, storageEvent -> {
            if (StoragePool.FLEETCARRIER.equals(storageEvent.getStoragePool()) || StoragePool.SHIP.equals(storageEvent.getStoragePool())) {
                update();
            }
        }));
//        register(EventService.addListener(true, this, MarketUpdatedEvent.class, event -> {
//            update();
//        }));
        register(EventService.addListener(true, this, ShipLoadoutEvent.class, event -> {
            update();
        }));

        register(EventService.addListener(true, this, ColonisationSelectedEvent.class, event -> {
            setBuildable(event.getColonisationItem());
        }));

    }

    private void update() {
        AtomicInteger required = new AtomicInteger(0);
        AtomicInteger delivered = new AtomicInteger(0);
        AtomicInteger toCollect = new AtomicInteger(0);
        AtomicInteger toDeliver = new AtomicInteger(0);
        colonisationItem.getConstructionRequirements().forEach((commodity, progress) -> {

            var availableShip = StorageService.getCommodityCount(commodity, StoragePool.SHIP);
            var availableFleetCarrier = StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER);

            var remaining = progress.required() - progress.provided();
            delivered.set(delivered.get() + Math.max(0, progress.provided()));
            required.set(required.get() + Math.max(0, progress.required()));

            toDeliver.set(toDeliver.get() + remaining);
            final int collectValue = Math.max(0, remaining - availableShip - availableFleetCarrier);
            toCollect.set(toCollect.get() + collectValue);

        });
        String cargoDeliver = getCargoString(toDeliver.get());
        if (!Objects.equals(cargoDeliver, "0")) {
            toDeliverValue.setText(MessageFormat.format("({0}) {1}", cargoDeliver, toDeliver.get()));
        } else {
            toDeliverValue.setText(MessageFormat.format("{0}", toDeliver.get()));
        }
        String cargoCollect = getCargoString(toCollect.get());
        if (!Objects.equals(cargoCollect, "0")) {
            toCollectValue.setText(MessageFormat.format("({0}) {1}", cargoCollect, toCollect.get()));
        } else {
            toCollectValue.setText(MessageFormat.format("{0}", toCollect.get()));
        }
        deliveredValue.setText(Formatters.NUMBER_FORMAT_0.format(delivered.get()));
        requiredValue.setText(Formatters.NUMBER_FORMAT_0.format(required.get()));
    }

    private static String getCargoString(int quantity) {
        final Ship ship = ShipMapper.toShip(ShipConfiguration.CURRENT);
        if (quantity == 0 || ship == null)
            return "0";
        final int currentCargo = (int) ship.getMaxCargo();
        if (currentCargo == 0D)
            return Formatters.NUMBER_FORMAT_0.format(Double.POSITIVE_INFINITY);

        return Formatters.NUMBER_FORMAT_0.format(calculateTrips(quantity, currentCargo)) + " x " + Formatters.NUMBER_FORMAT_0.format(currentCargo) + "T";
    }

    private static Double calculateTrips(int quantity, int currentCargoCapacity) {
        if (quantity <= 0) {
            return 0D;
        }
        if( currentCargoCapacity <= 0){
            return Double.POSITIVE_INFINITY;
        }
        return 1D + ((quantity-1) / currentCargoCapacity);
    }

    public void clear() {
        setBuildable(ColonisationItem.ALL);
    }
}
