package nl.jixxed.eliteodysseymaterials.service;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.util.Duration;
import javafx.util.Pair;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.*;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.helper.POIHelper;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.event.BlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsBlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.market.MarketAPIService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.CopyableLocation;
import org.controlsfx.control.PopOver;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static nl.jixxed.eliteodysseymaterials.enums.Rarity.UNKNOWN;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialService {

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
    private static final String STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE = "material-tooltip-subtitle";
    private static final String STYLECLASS_MATERIAL_TOOLTIP_LOCATION_LINE = "material-tooltip-location-line";
    private static final String STYLECLASS_MATERIAL_TOOLTIP_TITLE = "material-tooltip-title";
    private static final String STYLECLASS_MATERIAL_TOOLTIP_DESCRIPTION = "material-tooltip-description";
    private static final Map<Node, DestroyablePopOver> POPOVERS = new HashMap<>();

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

    private static <E extends Node & DestroyableComponent> DestroyableMaterialContent getMaterialPopOverContent(final HorizonsMaterial horizonsMaterial, final boolean wishlist, Supplier<Integer> requiredAmount, final E hoverableNode) {
        final DestroyableMaterialContent vBox = new DestroyableMaterialContent();
        vBox.getStyleClass().add("material-popover-content");
//                .withStyleClass("material-popover-content").buildVBox();
        if (horizonsMaterial.isUnknown()) {
            vBox.getNodes().add(LabelBuilder.builder()
                    .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_TITLE)
                    .withText(LocaleService.getStringBinding("material.tooltip.unknown"))
                    .build());
        } else {
            vBox.getNodes().add(LabelBuilder.builder()
                    .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_TITLE)
                    .withText(LocaleService.getStringBinding(horizonsMaterial.getLocalizationKey()))
                    .build());
            if (GameVersion.LIVE.equals(horizonsMaterial.getGameVersion())) {
                vBox.getNodes().add(LabelBuilder.builder()
                        .withText(LocaleService.getStringBinding((horizonsMaterial instanceof Commodity) ? "material.tooltip.is.live.commodity" : "material.tooltip.is.live"))
                        .build());
            }
            if (horizonsMaterial instanceof Commodity commodity) {
                vBox.getNodes().add(
                        LabelBuilder.builder()
                                .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_DESCRIPTION)
                                .withText(ObservableResourceFactory.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale(commodity.getDescriptionLocalizationKey())))
                                .build());
                vBox.getNodes().add(LabelBuilder.builder()
                        .build());
                if (horizonsMaterial instanceof RareCommodity rareCommodity) {
                    vBox.getNodes().add(new CopyableLocation(rareCommodity.getStarSystem(), rareCommodity.getStation()));
                }
                vBox.getNodes().add(
                        LabelBuilder.builder()
                                .withText(ObservableResourceFactory.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale("material.tooltip.type") + " " + LocaleService.getLocalizedStringForCurrentLocale(commodity.getCommodityType().getLocalizationKey())))
                                .build());
                vBox.getNodes().add(
                        LabelBuilder.builder()
                                .withText(ObservableResourceFactory.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale("material.tooltip.is.rare") + " " + LocaleService.getLocalizedStringForCurrentLocale(commodity instanceof RareCommodity ? "material.tooltip.text.yes" : "material.tooltip.text.no")))
                                .build());
                addMarketToTooltip(commodity, vBox);
                addMineableToTooltip(commodity, vBox);
                addRefinableToTooltip(commodity, vBox);
                addFleetCarrierOrdersToTooltip(commodity, vBox);
                if (requiredAmount.get() > 0 && commodity.isPurchasable()) {
                    addNearbyMarketsToTooltip(commodity, vBox, requiredAmount, hoverableNode);
                }
            } else if (horizonsMaterial instanceof Raw) {
                vBox.getNodes().add(LabelBuilder.builder()
                        .withText(LocaleService.getStringBinding("material.tooltip.type.raw"))
                        .build());
            } else if (horizonsMaterial instanceof Encoded) {
                vBox.getNodes().add(LabelBuilder.builder()
                        .withText(LocaleService.getStringBinding("material.tooltip.type.encoded"))
                        .build());
            } else if (horizonsMaterial instanceof Manufactured) {
                vBox.getNodes().add(LabelBuilder.builder()
                        .withText(LocaleService.getStringBinding("material.tooltip.type.manufactured"))
                        .build());
            }
            if (horizonsMaterial instanceof EngineeringMaterial engineeringMaterial && engineeringMaterial.isHuman()) {
                addClosestTraderToTooltip(horizonsMaterial, vBox);
            }
            addHorizonsSpawnLocationsToTooltip(SpawnConstants.HORIZONSMATERIAL_LOCATION.get(horizonsMaterial), vBox);
            if (!(horizonsMaterial instanceof Commodity)) {
                if (wishlist && WishlistService.getCurrentWishlistCount(horizonsMaterial, false).max() - StorageService.getMaterialCount(horizonsMaterial) > 0) {
                    addHorizonsTradeToTooltip(horizonsMaterial, vBox);
                } else {
                    addHorizonsTradeToTooltipClassic(horizonsMaterial, vBox);
                }
            }
            addHorizonsBlueprintsToTooltip(HorizonsBlueprintConstants.findRecipesContaining(horizonsMaterial), vBox);
        }
        return vBox;


    }

    private static <E extends Node & DestroyableComponent> void addNearbyMarketsToTooltip(Commodity commodity, DestroyableMaterialContent vBox, Supplier<Integer> requiredAmount, final E hoverableNode) {
        vBox.addSecondaryAction(() -> {
            Integer requiredAmountValue = requiredAmount.get();
            MarketAPIService.getNearbyStations(commodity, requiredAmountValue, LocationService.getCurrentStarSystem(), marketStations -> {
                Platform.runLater(() -> {
                    if (marketStations.isEmpty()) {
                        return;
                    }
                    vBox.getNodes().add(LabelBuilder.builder()
                            .build());
                    vBox.getNodes().add(LabelBuilder.builder()
                            .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE)
                            .withText("horizons.materials.nearest.markets")
                            .build());
                    DestroyableLabel supplyHeader = LabelBuilder.builder().withStyleClass("selling-stations-header").withText("horizons.materials.nearest.markets.supply.header").build();
                    DestroyableLabel ageHeader = LabelBuilder.builder().withStyleClass("selling-stations-header").withText("horizons.materials.nearest.markets.age.header").build();
                    DestroyableLabel locationsHeader = LabelBuilder.builder().withStyleClass("selling-stations-header").withText("horizons.materials.nearest.markets.location.header").build();
                    GridPane.setConstraints(supplyHeader, 0, 0);
                    GridPane.setConstraints(ageHeader, 1, 0);
                    GridPane.setConstraints(locationsHeader, 2, 0);


                    DestroyableGridPane gridpane = GridPaneBuilder.builder()
                            .withStyleClass("selling-stations")
                            .withNodes(supplyHeader, ageHeader, locationsHeader)
                            .build();

                    for (int i = 0; i < marketStations.size(); i++) {
                        var station = marketStations.get(i);
                        DestroyableLabel supply = LabelBuilder.builder().withStyleClass("supply").withText("horizons.materials.nearest.markets.supply", Formatters.NUMBER_FORMAT_0.format(station.getSupply()), Formatters.NUMBER_FORMAT_0.format(station.getSellPrice())).build();
                        DestroyableLabel age = LabelBuilder.builder().withStyleClass("age").withText(convertToAgeString(station.getLastMarketUpdate())).build();
                        CopyableLocation copyableLocation = new CopyableLocation(station.getStarSystem(), station.getName(), station.getDistanceFromStar(), 0);
                        GridPane.setConstraints(supply, 0, i + 1);
                        GridPane.setConstraints(age, 1, i + 1);
                        GridPane.setConstraints(copyableLocation, 2, i + 1);
                        gridpane.getNodes().addAll(supply, age, copyableLocation);
                    }
                    vBox.getNodes().add(gridpane);
                });
            }, () -> POPOVERS.containsKey(hoverableNode));
        });
    }

    private static StringBinding convertToAgeString(ZonedDateTime lastMarketUpdate) {
        if (lastMarketUpdate == null) {
            return LocaleService.getStringBinding("time.age.seconds", 0);
        }

        ZonedDateTime now = ZonedDateTime.now(lastMarketUpdate.getZone());
        java.time.Duration duration = java.time.Duration.between(lastMarketUpdate, now);

        long seconds = duration.getSeconds();
        String ageKey;
        long ageValue;

        if (seconds < 60) {
            ageValue = seconds;
            ageKey = ageValue == 1 ? "time.age.second" : "time.age.seconds";
        } else if (seconds < 3600) { // < 1 hour
            ageValue = seconds / 60;
            ageKey = ageValue == 1 ? "time.age.minute" : "time.age.minutes";
        } else if (seconds < 86400) { // < 1 day
            ageValue = seconds / 3600;
            ageKey = ageValue == 1 ? "time.age.hour" : "time.age.hours";
        } else if (seconds < 2592000) { // < ~30 days
            ageValue = seconds / 86400;
            ageKey = ageValue == 1 ? "time.age.day" : "time.age.days";
        } else if (seconds < 31536000) { // < ~12 months
            ageValue = seconds / 2592000;
            ageKey = ageValue == 1 ? "time.age.month" : "time.age.months";
        } else {
            ageValue = seconds / 31536000;
            ageKey = ageValue == 1 ? "time.age.year" : "time.age.years";
        }

        return LocaleService.getStringBinding(ageKey, ageValue);
    }

    private static void addClosestTraderToTooltip(HorizonsMaterial horizonsMaterial, DestroyableVBox vBox) {
        final MaterialTrader closest = MaterialTraderService.findClosest(LocationService.getCurrentStarSystem(), horizonsMaterial.getStorageType());
        vBox.getNodes().add(LabelBuilder.builder()
                .build());
        vBox.getNodes().add(LabelBuilder.builder()
                .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE)
                .withText("horizons.materials.nearest.trader")
                .build());
        vBox.getNodes().add(new CopyableLocation(closest.getStarSystem(), closest.getName(), closest.getDistanceFromStar(), closest.getDistanceFromStarVariance()));
    }

    private static void addMineableToTooltip(Commodity commodity, DestroyableVBox vBox) {
        Mineable.getMineable(commodity).ifPresent(mineable -> {
            vBox.getNodes().add(LabelBuilder.builder()
                    .build());
            vBox.getNodes().add(LabelBuilder.builder()
                    .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE)
                    .withText("material.tooltip.mining")
                    .build());
            vBox.getNodes().add(LabelBuilder.builder()
                    .withText("material.tooltip.mining.locations")
                    .build());
            vBox.getNodes().addAll(mineable.getLocations().stream().map(mineableLocation -> LabelBuilder.builder()
                    .withText(mineableLocation.getLocalizationKey())
                    .build()).toList());
        });
    }

    private static void addMarketToTooltip(Commodity commodity, DestroyableVBox vBox) {
        final Optional<MarketItem> marketItem = MarketService.getMarketItem(commodity);
        final boolean sells = MarketService.sells(commodity);
        final boolean buys = MarketService.buys(commodity);
        marketItem.ifPresent(item -> {
            if (buys || sells) {
                vBox.getNodes().add(LabelBuilder.builder()
                        .build());
                vBox.getNodes().add(LabelBuilder.builder()
                        .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE)
                        .withText(LocaleService.getStringBinding("material.tooltip.market"))
                        .build());
                if (buys) {
                    vBox.getNodes().add(LabelBuilder.builder()
                            .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_DESCRIPTION)
                            .withText(
                                    LocaleService.getStringBinding("material.tooltip.market.buys",
                                            item.sellPrice(),
                                            item.demand().equals(BigInteger.ONE) ? "∞" : item.demand()))
                            .build());
                }
                if (sells) {
                    vBox.getNodes().add(LabelBuilder.builder()
                            .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_DESCRIPTION)
                            .withText(
                                    LocaleService.getStringBinding("material.tooltip.market.sells",
                                            item.buyPrice(),
                                            item.stock()))
                            .build());
                }
            }
        });

    }

    private static void addRefinableToTooltip(Commodity commodity, DestroyableVBox vBox) {
        Refinable.getRefinable(commodity).ifPresent(refinable -> {
            vBox.getNodes().add(LabelBuilder.builder()
                    .build());
            vBox.getNodes().add(LabelBuilder.builder()
                    .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE)
                    .withText(LocaleService.getStringBinding("material.tooltip.refining"))
                    .build());
            vBox.getNodes().add(LabelBuilder.builder()
                    .withText(
                            LocaleService.getStringBinding("material.tooltip.refinable",
                                    LocaleService.LocalizationKey.of(refinable.getCommodityFrom().getLocalizationKey()),
                                    LocaleService.LocalizationKey.of(refinable.getCommodityTo().getLocalizationKey()),
                                    refinable.getCommodityIn(),
                                    refinable.getCommodityOut()))
                    .build());
        });
    }

    private static void addHorizonsTradeToTooltip(final HorizonsMaterial horizonsMaterial, final DestroyableVBox vBox) {
        if (horizonsMaterial.getRarity() != UNKNOWN && horizonsMaterial.getMaterialType().isTradable() && !(horizonsMaterial instanceof Commodity)) {
            vBox.getNodes().add(LabelBuilder.builder()
                    .build());
            vBox.getNodes().add(LabelBuilder.builder()
                    .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE)
                    .withText(LocaleService.getStringBinding("material.tooltip.trade"))
                    .build());
            final List<HorizonsTradeSuggestion> tradeSuggestions = MaterialTraderService.getTradeSuggestions(horizonsMaterial);
            if (!tradeSuggestions.isEmpty()) {
                tradeSuggestions.forEach(tradeSuggestion -> {
                    final NumberFormat percentFormat = NumberFormat.getPercentInstance();
                    percentFormat.setMaximumFractionDigits(1);
                    vBox.getNodes().add(

                            BoxBuilder.builder()
                                    .withNodes(LabelBuilder.builder()
                                                    .withStyleClasses("trade-suggestion-bullet", "horizons-tradetype" + tradeSuggestion.getHorizonsMaterialTo().getTradeType(tradeSuggestion.getHorizonsMaterialFrom()).getPreference())
                                                    .withNonLocalizedText(getArrow(tradeSuggestion.getHorizonsMaterialTo().getTradeType(tradeSuggestion.getHorizonsMaterialFrom())))
                                                    .build(),
                                            LabelBuilder.builder()
                                                    .withStyleClass("horizons-tradetype" + tradeSuggestion.getHorizonsMaterialTo().getTradeType(tradeSuggestion.getHorizonsMaterialFrom()).getPreference())
                                                    .withText(LocaleService.getStringBinding("material.tooltip.tradesuggestions",
                                                            tradeSuggestion.receivedOnTrade(),
                                                            (int) tradeSuggestion.amountRequiredToTrade(),
                                                            LocaleService.getLocalizedStringForCurrentLocale(tradeSuggestion.getHorizonsMaterialFrom().getLocalizationKey()),
                                                            tradeSuggestion.getOwned(),
                                                            tradeSuggestion.getReserved(),
                                                            percentFormat.format(tradeSuggestion.getPercentageUsedOnTrade())
                                                    ))
                                                    .build()).buildHBox()
                    );
                });
            } else {
                vBox.getNodes().add(LabelBuilder.builder()
                        .withText(LocaleService.getStringBinding("material.tooltip.tradesuggestions.none"))
                        .build());
            }
        }


    }

    private static String getArrow(final MaterialTradeType tradeSuggestionType) {
        return switch (tradeSuggestionType) {
            case DOWNTRADE -> "\u2193";
            case UPTRADE -> "\u2191";
            case CROSS_DOWNTRADE, CROSS_UPTRADE, IMPOSSIBLE -> "\u292E";
        };
    }

    private static void addHorizonsTradeToTooltipClassic(final HorizonsMaterial horizonsMaterial, final DestroyableVBox vBox) {
        if (horizonsMaterial.getRarity() != UNKNOWN && horizonsMaterial.getMaterialType().isTradable() && !(horizonsMaterial instanceof Commodity)) {
            vBox.getNodes().add(LabelBuilder.builder()
                    .build());
            vBox.getNodes().add(LabelBuilder.builder()
                    .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE)
                    .withText(LocaleService.getStringBinding("material.tooltip.downtrade"))
                    .build());
            final List<HorizonsMaterial> otherMaterialsInCategory = HorizonsMaterial.getAllMaterials().stream()
                    .filter(material -> material.getMaterialType() == horizonsMaterial.getMaterialType()
                            && material != horizonsMaterial
                            && material.getRarity().getLevel() > horizonsMaterial.getRarity().getLevel())
                    .sorted(Comparator.comparing(HorizonsMaterial::getRarity))
                    .toList();
            if (!otherMaterialsInCategory.isEmpty()) {
                otherMaterialsInCategory.forEach(material -> {
                    final int materialCount = StorageService.getMaterialCount(material);
                    final int factor = material.getRarity().getLevel() - horizonsMaterial.getRarity().getLevel();
                    final int potentialFromTrade = materialCount * (int) Math.pow(3, factor);
                    vBox.getNodes().add(LabelBuilder.builder()
                            .withText(LocaleService.getStringBinding("material.tooltip.downtrade.entry", potentialFromTrade, LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey())))
                            .build());
                });
            } else {
                vBox.getNodes().add(LabelBuilder.builder()
                        .withText(LocaleService.getStringBinding("material.tooltip.downtrade.none"))
                        .build());
            }
        }


    }

    private static void addHorizonsSpawnLocationsToTooltip(final List<HorizonsMaterialSpawnLocation> horizonsMaterialSpawnLocations, final DestroyableVBox vBox) {
        if (horizonsMaterialSpawnLocations != null && !horizonsMaterialSpawnLocations.isEmpty()) {
            vBox.getNodes().add(LabelBuilder.builder()
                    .build());
            vBox.getNodes().add(LabelBuilder.builder()
                    .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE)
                    .withText(LocaleService.getStringBinding("material.tooltip.spawnlocations"))
                    .build());
            horizonsMaterialSpawnLocations.stream().forEach(spawn -> {
                if (spawn.hasLocations()) {
                    Location closest = spawn.getClosest();
                    final CopyableLocation copyableLocation = new CopyableLocation(closest.getStarSystem(), LocaleService.getLocalizedStringForCurrentLocale(spawn.getLocalizationKey(), closest.getBody()));
                    vBox.getNodes().add(copyableLocation);
                } else {
                    final DestroyableLabel location = LabelBuilder.builder()
                            .withText(LocaleService.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale(spawn.getLocalizationKey())))
                            .build();
                    vBox.getNodes().add(location);
                }
            });
        }
    }

    private static DestroyableMaterialContent getMaterialPopOverContent(final OdysseyMaterial odysseyMaterial) {
        final DestroyableMaterialContent vBox = new DestroyableMaterialContent();
        vBox.getStyleClass().add("material-popover-content");
        if (odysseyMaterial.isUnknown()) {
            vBox.getNodes().add(LabelBuilder.builder()
                    .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_TITLE)
                    .withText(LocaleService.getStringBinding("material.tooltip.unknown"))
                    .build());
        } else {
            vBox.getNodes().add(LabelBuilder.builder()
                    .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_TITLE)
                    .withText(LocaleService.getStringBinding(odysseyMaterial.getLocalizationKey()))
                    .build());
            if (odysseyMaterial.isIllegal()) {
                vBox.getNodes().add(LabelBuilder.builder()
                        .withText(LocaleService.getStringBinding("material.tooltip.illegal"))
                        .build());
            }
            addBarterInfoToTooltip(odysseyMaterial, vBox);
            if (odysseyMaterial instanceof Data data) {
                addTransferTimeToTooltip(data, vBox);
            }
            addFleetCarrierOrdersToTooltip(odysseyMaterial, vBox);
            addRecipesToTooltip(OdysseyBlueprintConstants.findRecipesContaining(odysseyMaterial), vBox);
            if (odysseyMaterial instanceof Data data) {
                addSpawnRatesToTooltip(data, vBox);
            }
//            addStatisticsToTooltip(odysseyMaterial, vBox);
        }
        return vBox;


    }

    private static void addSpawnRatesToTooltip(final Data data, final DestroyableVBox vBox) {
        final Map<DataPortType, Double> dataPortSpawnRates = SpawnConstants.DATA_SPAWN_CHANCE.get(data);
        if (dataPortSpawnRates != null) {
            vBox.getNodes().add(LabelBuilder.builder()
                    .build());
            vBox.getNodes().add(LabelBuilder.builder()
                    .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE)
                    .withText(LocaleService.getStringBinding("material.tooltip.spawnrates"))
                    .build());
            final String rates = dataPortSpawnRates.entrySet().stream()
                    .filter(entry -> entry.getValue() > 0.01)
                    .sorted((Comparator<Map.Entry<DataPortType, Double>>) (Comparator<?>) Map.Entry.comparingByValue().reversed())
                    .map((entry) -> entry.getKey() + "(" + NUMBER_FORMAT.format(entry.getValue()) + "%)")
                    .collect(Collectors.joining(", "));
            vBox.getNodes().add(LabelBuilder.builder()
                    .withNonLocalizedText(rates)
                    .build());
        }
    }

    public static <E extends Node & DestroyableComponent> void addMaterialInfoPopOver(final E hoverableNode, final Material material, final boolean wishlist) {
        addMaterialInfoPopOver(hoverableNode, material, wishlist, () -> 0);
    }

    public static <E extends Node & DestroyableComponent> void addMaterialInfoPopOver(final E hoverableNode, final Material material, final boolean wishlist, Supplier<Integer> requiredAmount) {
        hoverableNode.addEventBinding(hoverableNode.onMouseEnteredProperty(), mouseEvent -> {
            if (POPOVERS.containsKey(hoverableNode)) {
                return;
            }
            try {

                final Timeline timelineShow = new Timeline();
                final Timeline timelineHide = new Timeline();
                final AtomicReference<DestroyableVBox> contentNode = new AtomicReference<>();
                timelineShow.getKeyFrames().add(new KeyFrame(Duration.millis(500)));
                timelineShow.setOnFinished(_ -> {
                    DestroyableMaterialContent content = switch (material) {
                        case HorizonsMaterial horizonsMaterial ->
                                getMaterialPopOverContent(horizonsMaterial, wishlist, requiredAmount, hoverableNode);
                        case OdysseyMaterial odysseyMaterial -> getMaterialPopOverContent(odysseyMaterial);
                    };
                    contentNode.set(content);
                    contentNode.get().getStyleClass().add("material-popover");
                    if (!POPOVERS.containsKey(hoverableNode) && (hoverableNode.isHover() || contentNode.get().isHover())) {
                        try {
                            final Screen screen = Screen.getScreensForRectangle(mouseEvent.getScreenX(), mouseEvent.getScreenY(), 1, 1).getFirst();
                            final DestroyablePopOver popOver = PopOverBuilder.builder()
                                    .withStyleClass("popover-menubutton-layout")
                                    .withContent((Node & Destroyable) contentNode.get())
                                    .withArrowIndent(0)
                                    .withArrowSize(0)
                                    .withCornerRadius(0)
                                    .withDetachable(false)
                                    .withHeaderAlwaysVisible(false)
                                    .withDestroyOnHide(true)
                                    .build();
                            POPOVERS.put(hoverableNode, popOver);
                            content.triggerSecondaryActions();
                            final Rectangle2D currentScreen = screen.getBounds();
                            final double mouseXOnScreen = mouseEvent.getScreenX() - currentScreen.getMinX();
                            final double mouseYOnScreen = mouseEvent.getScreenY() - currentScreen.getMinY();
                            if (mouseXOnScreen < currentScreen.getWidth() / 2 && mouseYOnScreen < currentScreen.getHeight() / 2) {
                                popOver.setArrowLocation(PopOver.ArrowLocation.LEFT_TOP);
                            } else if (mouseXOnScreen < currentScreen.getWidth() / 2 && mouseYOnScreen > currentScreen.getHeight() / 2) {
                                popOver.setArrowLocation(PopOver.ArrowLocation.LEFT_BOTTOM);
                            } else if (mouseXOnScreen > currentScreen.getWidth() / 2 && mouseYOnScreen < currentScreen.getHeight() / 2) {
                                popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_TOP);
                            } else {
                                popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_BOTTOM);
                            }
                            if (popOver.getContentNode() != null) {
                                popOver.show(hoverableNode);
                            }
                            timelineHide.play();
                        } catch (NoSuchElementException ex) {
                            log.error(ex.getMessage(), ex);
                            contentNode.get().destroy();
                        }
                    } else {
                        contentNode.get().destroy();
                    }
                });
                timelineHide.getKeyFrames().add(new KeyFrame(Duration.millis(100)));
                timelineHide.setOnFinished(_ -> {
                    if (hoverableNode.isHover() || (contentNode.get() != null && contentNode.get().isHover())) {
                        timelineHide.play();
                    } else {
                        if (POPOVERS.containsKey(hoverableNode)) {
                            POPOVERS.get(hoverableNode).hide(Duration.ZERO);
                            POPOVERS.get(hoverableNode).destroy();
                            POPOVERS.remove(hoverableNode);
                        }
                        timelineHide.stop();
                    }
                });
//                hoverableNode.addEventBinding(hoverableNode.onMouseExitedProperty(), _ -> Platform.runLater(timelineHide::play));
                timelineShow.play();
            } catch (IndexOutOfBoundsException ex) {
                log.warn("Unable to determine screen to show material info on.");
            }


        });
    }


    private static void addSettlementStatistic(final DestroyableVBox vBox, final SettlementStatistic settlementStatistic) {
        final Double distance = LocationService.calculateDistance(LocationService.getCurrentStarSystem(), new StarSystem(settlementStatistic.getSystem(), settlementStatistic.getX(), settlementStatistic.getY(), settlementStatistic.getZ()));
        final String settlement = POIHelper.map(settlementStatistic.getSettlement());
        final DestroyableLabel label = LabelBuilder.builder()
                .withNonLocalizedText(settlementStatistic.getAmount() + " - " + settlement + " | " + settlementStatistic.getBody() + " | " + settlementStatistic.getSystem() + " (" + NUMBER_FORMAT.format(distance) + "Ly) ")
                .build();
        final DestroyableResizableImageView copyIcon = ResizableImageViewBuilder.builder()
                .withStyleClass("material-tooltip-copy-icon")
                .withImage("/images/other/copy.png")
                .build();
        final DestroyableStackPane copyIconStackPane = StackPaneBuilder.builder()
                .withNodes(copyIcon)
                .build();
        vBox.getNodes().add(BoxBuilder.builder()
                .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_LOCATION_LINE)
                .withOnMouseClicked(event -> {
                    copyLocationToClipboard(settlementStatistic.getSystem());
                    NotificationService.showInformation(NotificationType.COPY, LocaleService.LocaleString.of("notification.clipboard.title"), LocaleService.LocaleString.of("notification.clipboard.system.copied.text"));
                })
                .withNodes(label, copyIconStackPane).buildHBox());
    }

    private static void copyLocationToClipboard(final String text) {
        ClipboardHelper.copyToClipboard(text);
    }

    private static void addTransferTimeToTooltip(final Data data, final DestroyableVBox vBox) {
        vBox.getNodes().add(LabelBuilder.builder()
                .build());
        vBox.getNodes().add(LabelBuilder.builder()
                .withText(LocaleService.getStringBinding((data.isUpload()) ? "material.tooltip.data.upload" : "material.tooltip.data.download", data.getTransferTime()))
                .build());
    }

    private static void addBarterInfoToTooltip(final OdysseyMaterial odysseyMaterial, final DestroyableVBox vBox) {
        final Integer barterSellPrice = BarterConstants.getBarterSellPrice(odysseyMaterial);
        vBox.getNodes().add(LabelBuilder.builder()
                .build());
        vBox.getNodes().add(LabelBuilder.builder()
                .withText(LocaleService.getStringBinding("material.tooltip.barter.sell.price", barterSellPrice == -1 ? "?" : NUMBER_FORMAT.format(barterSellPrice)))
                .build());
        if (odysseyMaterial instanceof Asset asset) {
            vBox.getNodes().add(LabelBuilder.builder()
                    .withText(LocaleService.getStringBinding("material.tooltip.barter.trade", asset.getBuyValue() + "/" + asset.getSellValue()))
                    .build());
        }
    }

    private static void addFleetCarrierOrdersToTooltip(final OdysseyMaterial odysseyMaterial, final DestroyableVBox vBox) {
        final BuyOrder buyOrder = OrderService.getBuyOrder(odysseyMaterial);
        final SellOrder sellOrder = OrderService.getSellOrder(odysseyMaterial);
        final Integer fleetCarrierStorageAmount = StorageService.getMaterialStorage(odysseyMaterial).getFleetCarrierValue();
        if (buyOrder != null || sellOrder != null || CAPIService.getInstance().getActive().not().get() || fleetCarrierStorageAmount > 0) {
            vBox.getNodes().add(LabelBuilder.builder()
                    .build());
            vBox.getNodes().add(LabelBuilder.builder()
                    .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE)
                    .withText(LocaleService.getStringBinding("material.tooltip.fleetcarrier"))
                    .build());
        }
        if (CAPIService.getInstance().getActive().not().get()) {
            vBox.getNodes().add(LabelBuilder.builder()
                    .withText(LocaleService.getStringBinding("material.tooltip.fleetcarrier.not.linked"))
                    .build());
        }
        if (buyOrder != null) {
            vBox.getNodes().add(LabelBuilder.builder()
                    .withText(LocaleService.getStringBinding("material.tooltip.fleetcarrier.buy", buyOrder.getTotal(), buyOrder.getOutstanding(), NUMBER_FORMAT.format(buyOrder.getPrice())))
                    .build());
        }
        if (sellOrder != null) {
            vBox.getNodes().add(LabelBuilder.builder()
                    .withText(LocaleService.getStringBinding("material.tooltip.fleetcarrier.sell", sellOrder.getStock(), NUMBER_FORMAT.format(sellOrder.getPrice())))
                    .build());
        }
        if (fleetCarrierStorageAmount > 0) {
            vBox.getNodes().add(LabelBuilder.builder()
                    .withText(LocaleService.getStringBinding("material.tooltip.fleetcarrier.storage", fleetCarrierStorageAmount))
                    .build());
        }

    }

    private static void addFleetCarrierOrdersToTooltip(final Commodity commodity, final DestroyableVBox vBox) {
        final BuyOrder buyOrder = OrderService.getBuyOrder(commodity);
        final SellOrder sellOrder = OrderService.getSellOrder(commodity);
        final Integer fleetCarrierStorageAmount = StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER);
        if (buyOrder != null || sellOrder != null || CAPIService.getInstance().getActive().not().get() || fleetCarrierStorageAmount > 0) {
            vBox.getNodes().add(LabelBuilder.builder()
                    .build());
            vBox.getNodes().add(LabelBuilder.builder()
                    .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE)
                    .withText(LocaleService.getStringBinding("material.tooltip.fleetcarrier"))
                    .build());
        }
        if (CAPIService.getInstance().getActive().not().get()) {
            vBox.getNodes().add(LabelBuilder.builder()
                    .withText(LocaleService.getStringBinding("material.tooltip.fleetcarrier.not.linked"))
                    .build());
        }
        if (buyOrder != null) {
            vBox.getNodes().add(LabelBuilder.builder()
                    .withText(LocaleService.getStringBinding("material.tooltip.fleetcarrier.buy", buyOrder.getTotal(), buyOrder.getOutstanding(), NUMBER_FORMAT.format(buyOrder.getPrice())))
                    .build());
        }
        if (sellOrder != null) {
            vBox.getNodes().add(LabelBuilder.builder()
                    .withText(LocaleService.getStringBinding("material.tooltip.fleetcarrier.sell", sellOrder.getStock(), NUMBER_FORMAT.format(sellOrder.getPrice())))
                    .build());
        }
        if (fleetCarrierStorageAmount > 0) {
            vBox.getNodes().add(LabelBuilder.builder()
                    .withText(LocaleService.getStringBinding("material.tooltip.fleetcarrier.storage", fleetCarrierStorageAmount))
                    .build());
        }

    }

    private static void addRecipesToTooltip(final Map<OdysseyBlueprintName, Integer> recipesContainingMaterial, final DestroyableVBox vBox) {
        if (!recipesContainingMaterial.isEmpty()) {
            vBox.getNodes().add(LabelBuilder.builder()
                    .build());
            vBox.getNodes().add(LabelBuilder.builder()
                    .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE)
                    .withText(LocaleService.getStringBinding("material.tooltip.used.in.recipes"))
                    .build());
            final List<OdysseyBlueprintListing> odysseyBlueprintListings = recipesContainingMaterial.entrySet().stream().
                    map(entry -> new OdysseyBlueprintListing(OdysseyBlueprintConstants.getRecipeCategory(entry.getKey()), entry.getKey(), entry.getValue()))
                    .sorted()
                    .collect(Collectors.toList());
            DestroyableFlowPane catBox = null;
            BlueprintCategory prevCat = null;
            for (final OdysseyBlueprintListing odysseyBlueprintListing : odysseyBlueprintListings) {
                if (!odysseyBlueprintListing.category().equals(prevCat)) {
                    prevCat = odysseyBlueprintListing.category();
                    catBox = FlowPaneBuilder.builder()
                            .withStyleClass("blueprint-listing-flowpane")
                            .build();
                    final DestroyableTitledPane titledPane = TitledPaneBuilder.builder().build();
                    titledPane.addBinding(titledPane.textProperty(), LocaleService.getStringBinding(odysseyBlueprintListing.category().getLocalizationKey()));
                    titledPane.setContentNode(catBox);
                    titledPane.setExpanded(PreferencesService.getPreference(PreferenceConstants.TOOLTIP_BLUEPRINT_EXPANDED, Boolean.FALSE));
                    vBox.getNodes().add(titledPane);
                    final String[] classes = new String[]{"blueprint-listing-label"};
                    final DestroyableLabel build = LabelBuilder.builder()
                            .withStyleClasses(classes)
                            .withText(odysseyBlueprintListing.toStringBinding())
                            .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(odysseyBlueprintListing.name())))
                            .build();
                    catBox.getNodes().add(build);
                } else {
                    //append
                    final String[] classes = new String[]{"blueprint-listing-label"};
                    final DestroyableLabel build = LabelBuilder.builder()
                            .withStyleClasses(classes)
                            .withText(odysseyBlueprintListing.toStringBinding())
                            .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(odysseyBlueprintListing.name())))
                            .build();
                    catBox.getNodes().add(build);
                    catBox.addBinding(catBox.prefWrapLengthProperty(), ScalingHelper.getPixelDoubleBindingFromEm(23.33 * 2));
                }
            }


        }
    }

    private static void addHorizonsBlueprintsToTooltip(final Map<HorizonsBlueprint, Integer> horizonsBlueprintMap, final DestroyableVBox vBox) {
        if (!horizonsBlueprintMap.isEmpty()) {
            vBox.getNodes().add(LabelBuilder.builder()
                    .build());
            vBox.getNodes().add(LabelBuilder.builder()
                    .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE)
                    .withText(LocaleService.getStringBinding("material.tooltip.used.in.recipes"))
                    .build());
            final Map<Pair<HorizonsBlueprintName, HorizonsBlueprintType>, List<Pair<HorizonsBlueprintGrade, Integer>>> groupedBlueprints = new HashMap<>();
            horizonsBlueprintMap.forEach((key1, value) -> {
                final Pair<HorizonsBlueprintName, HorizonsBlueprintType> keyPair = new Pair<>(key1.getHorizonsBlueprintName(), key1.getHorizonsBlueprintType());
                final List<Pair<HorizonsBlueprintGrade, Integer>> gradeValues = groupedBlueprints.computeIfAbsent(keyPair, key -> new ArrayList<>());
                gradeValues.add(new Pair<>(key1.getHorizonsBlueprintGrade(), value));
            });


            final List<HorizonsBlueprintListing> horizonsBlueprintListings = groupedBlueprints.entrySet().stream().
                    mapMulti((final Map.Entry<Pair<HorizonsBlueprintName, HorizonsBlueprintType>, List<Pair<HorizonsBlueprintGrade, Integer>>> entry, final Consumer<HorizonsBlueprintListing> consumer) -> {
                        final Map<Integer, List<Pair<HorizonsBlueprintGrade, Integer>>> gradeGroups = entry.getValue().stream().collect(Collectors.groupingBy(Pair::getValue));
                        gradeGroups.forEach((materialAmount, pairs) ->
                                consumer.accept(
                                        new HorizonsBlueprintListing(entry.getKey().getKey().getBlueprintCategory(), entry.getKey().getKey(), entry.getKey().getValue(), pairs, materialAmount)
                                )
                        );
                    })
                    .sorted()
                    .collect(Collectors.toList());
            DestroyableFlowPane catBox = null;
            BlueprintCategory prevCat = null;
            for (final HorizonsBlueprintListing horizonsBlueprintListing : horizonsBlueprintListings) {
                if (!horizonsBlueprintListing.category().equals(prevCat)) {
                    prevCat = horizonsBlueprintListing.category();
                    catBox = FlowPaneBuilder.builder()
                            .withStyleClass("blueprint-listing-flowpane")
                            .build();
                    final DestroyableTitledPane titledPane = TitledPaneBuilder.builder().build();
                    titledPane.addBinding(titledPane.textProperty(), LocaleService.getStringBinding(horizonsBlueprintListing.category().getLocalizationKey()));
                    titledPane.setContentNode(catBox);
                    titledPane.setExpanded(PreferencesService.getPreference(PreferenceConstants.TOOLTIP_BLUEPRINT_EXPANDED, Boolean.FALSE));
                    vBox.getNodes().add(titledPane);
                    final String[] classes = (horizonsBlueprintListing.type().isExperimental()) ? new String[]{"blueprint-listing-label", "blueprint-listing-label-experimental"} : new String[]{"blueprint-listing-label"};
                    final DestroyableLabel build = LabelBuilder.builder()
                            .withStyleClasses(classes)
                            .withText(horizonsBlueprintListing.toStringBinding())
                            .withOnMouseClicked(event -> EventService.publish(new HorizonsBlueprintClickEvent(HorizonsBlueprintConstants.getRecipe(horizonsBlueprintListing.name(), horizonsBlueprintListing.type(), horizonsBlueprintListing.gradeGroups().get(0).getKey()))))
                            .build();
                    catBox.getNodes().add(build);
                    catBox.addBinding(catBox.prefWrapLengthProperty(), ScalingHelper.getPixelDoubleBindingFromEm(23.33 * 2));
                } else {
                    //append
                    final String[] classes = (horizonsBlueprintListing.type().isExperimental()) ? new String[]{"blueprint-listing-label", "blueprint-listing-label-experimental"} : new String[]{"blueprint-listing-label"};
                    final DestroyableLabel build = LabelBuilder.builder()
                            .withStyleClasses(classes)
                            .withText(horizonsBlueprintListing.toStringBinding())
                            .withOnMouseClicked(event -> EventService.publish(new HorizonsBlueprintClickEvent(HorizonsBlueprintConstants.getRecipe(horizonsBlueprintListing.name(), horizonsBlueprintListing.type(), horizonsBlueprintListing.gradeGroups().get(0).getKey()))))
                            .build();
                    catBox.getNodes().add(build);
                }
            }
        }
    }

}
