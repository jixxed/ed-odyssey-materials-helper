package nl.jixxed.eliteodysseymaterials.service;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Duration;
import javafx.util.Pair;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.constants.BarterConstants;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.POIHelper;
import org.controlsfx.control.PopOver;

import java.text.NumberFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialService {

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
    private static final String STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE = "material-tooltip-subtitle";
    private static final String STYLECLASS_MATERIAL_TOOLTIP_LOCATION_LINE = "material-tooltip-location-line";
    private static final String STYLECLASS_MATERIAL_TOOLTIP_TITLE = "material-tooltip-title";

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

    private static VBox getMaterialPopOverContent(final HorizonsMaterial horizonsMaterial) {
        final VBox vBox = BoxBuilder.builder().buildVBox();
        LabelBuilder.builder().withText(LocaleService.getStringBinding(horizonsMaterial.getLocalizationKey())).build();
        if (horizonsMaterial.isUnknown()) {
            vBox.getChildren().add(LabelBuilder.builder().withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_TITLE).withText(LocaleService.getStringBinding("material.tooltip.unknown")).build());
        } else {
            vBox.getChildren().add(LabelBuilder.builder().withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_TITLE).withText(LocaleService.getStringBinding(horizonsMaterial.getLocalizationKey())).build());
            if (horizonsMaterial instanceof Commodity commodity) {
                vBox.getChildren().add(LabelBuilder.builder().withText(ObservableResourceFactory.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale("material.tooltip.type") + LocaleService.getLocalizedStringForCurrentLocale(commodity.getCommodityType().getLocalizationKey()))).build());
                vBox.getChildren().add(LabelBuilder.builder().withText(ObservableResourceFactory.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale("material.tooltip.is.rare") + LocaleService.getLocalizedStringForCurrentLocale(commodity.isRareCommodity() ? "material.tooltip.text.yes" : "material.tooltip.text.no"))).build());
            } else if (horizonsMaterial instanceof Raw) {
                vBox.getChildren().add(LabelBuilder.builder().withText(LocaleService.getStringBinding("material.tooltip.type.raw")).build());
            } else if (horizonsMaterial instanceof Encoded) {
                vBox.getChildren().add(LabelBuilder.builder().withText(LocaleService.getStringBinding("material.tooltip.type.encoded")).build());
            } else if (horizonsMaterial instanceof Manufactured) {
                vBox.getChildren().add(LabelBuilder.builder().withText(LocaleService.getStringBinding("material.tooltip.type.manufactured")).build());
            }

            addHorizonsBlueprintsToTooltip(HorizonsBlueprintConstants.findRecipesContaining(horizonsMaterial), vBox);
        }
        return vBox;


    }

    private static VBox getMaterialPopOverContent(final OdysseyMaterial odysseyMaterial) {
        final VBox vBox = BoxBuilder.builder().buildVBox();
        if (odysseyMaterial.isUnknown()) {
            vBox.getChildren().add(LabelBuilder.builder().withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_TITLE).withText(LocaleService.getStringBinding("material.tooltip.unknown")).build());
        } else {
            vBox.getChildren().add(LabelBuilder.builder().withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_TITLE).withText(LocaleService.getStringBinding(odysseyMaterial.getLocalizationKey())).build());
            if (odysseyMaterial.isIllegal()) {
                vBox.getChildren().add(LabelBuilder.builder().withText(LocaleService.getStringBinding("material.tooltip.illegal")).build());
            }
            addBarterInfoToTooltip(odysseyMaterial, vBox);
            if (odysseyMaterial instanceof Data data) {
                addTransferTimeToTooltip(data, vBox);
            }
            addRecipesToTooltip(OdysseyBlueprintConstants.findRecipesContaining(odysseyMaterial), vBox);
            addStatisticsToTooltip(odysseyMaterial, vBox);
        }
        return vBox;


    }

    public static void addMaterialInfoPopOver(final Node hoverableNode, final Material material) {
        hoverableNode.setOnMouseEntered(mouseEvent -> {
            final Node contentNode = (material instanceof HorizonsMaterial) ? getMaterialPopOverContent((HorizonsMaterial) material) : getMaterialPopOverContent((OdysseyMaterial) material);
            contentNode.getStyleClass().add("material-popover");
            final PopOver popOver = new PopOver(contentNode);
            popOver.setDetachable(false);
            popOver.setHeaderAlwaysVisible(false);
            final Rectangle2D currentScreen = Screen.getScreensForRectangle(mouseEvent.getScreenX(), mouseEvent.getScreenY(), 1, 1).get(0).getBounds();
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
            final Timeline timelineShow = new Timeline();
            timelineShow.getKeyFrames().add(new KeyFrame(Duration.millis(500)));
            timelineShow.setOnFinished(finishEvent -> {
                if (hoverableNode.isHover() || (contentNode.isHover())) {
                    if (popOver.getContentNode() != null) {
                        popOver.show(hoverableNode);
                    }
                }
            });
            timelineShow.play();
            final Timeline timelineHide = new Timeline();
            timelineHide.getKeyFrames().add(new KeyFrame(Duration.millis(100)));
            timelineHide.setOnFinished(finishEvent -> {
                if (hoverableNode.isHover() || contentNode.isHover()) {
                    timelineHide.play();
                } else {
                    popOver.hide(Duration.ZERO);
                    if (popOver.getContentNode() != null) {
                        popOver.setContentNode(null);
                    }
                    timelineHide.stop();
                }
            });
            hoverableNode.setOnMouseExited(mouseEvent2 -> timelineHide.play());
        });
    }

    private static void addStatisticsToTooltip(final OdysseyMaterial odysseyMaterial, final VBox vBox) {
        final MaterialStatistic statistic = MaterialTrackingService.getMaterialStatistic(odysseyMaterial);
        //economies
        vBox.getChildren().add(LabelBuilder.builder().build());
        vBox.getChildren().add(LabelBuilder.builder().withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE).withText(LocaleService.getStringBinding("material.tooltip.statistics.economies")).build());
        vBox.getChildren().add(LabelBuilder.builder()
                .withText(ObservableResourceFactory.getStringBinding(() -> statistic.getEconomies().stream().sorted(Comparator.comparing(EconomyStatistic::getAmount).reversed()).map(economyStatistic -> economyStatistic.getEconomy() + "(" + economyStatistic.getAmount() + ")").collect(Collectors.joining(", ")))).build());
        //most collected
        vBox.getChildren().add(LabelBuilder.builder().build());
        vBox.getChildren().add(LabelBuilder.builder().withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE).withText(LocaleService.getStringBinding("material.tooltip.statistics.settlements")).build());
        statistic.getMostcollected().forEach(settlementStatistic -> addSettlementStatistic(vBox, settlementStatistic));
        //best runs
        vBox.getChildren().add(LabelBuilder.builder().build());
        vBox.getChildren().add(LabelBuilder.builder().withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE).withText(LocaleService.getStringBinding("material.tooltip.statistics.runs")).build());
        statistic.getBestrun().forEach(settlementStatistic -> addSettlementStatistic(vBox, settlementStatistic));

    }

    private static void addSettlementStatistic(final VBox vBox, final SettlementStatistic settlementStatistic) {
        final Double distance = LocationService.calculateDistance(LocationService.getCurrentStarSystem(), new StarSystem(settlementStatistic.getSystem(), settlementStatistic.getX(), settlementStatistic.getY(), settlementStatistic.getZ()));
        final String settlement = POIHelper.map(settlementStatistic.getSettlement());
        final Label label = LabelBuilder.builder().withNonLocalizedText(settlementStatistic.getAmount() + " - " + settlement + " | " + settlementStatistic.getBody() + " | " + settlementStatistic.getSystem() + " (" + NUMBER_FORMAT.format(distance) + "Ly) ").build();
        vBox.getChildren().add(BoxBuilder.builder().withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_LOCATION_LINE)
                .withOnMouseClicked(event -> {
                    copyLocationToClipboard(settlementStatistic.getSystem());
                    NotificationService.showInformation(NotificationType.COPY, "Clipboard", "System name copied.");
                }).withNodes(label, new StackPane(ResizableImageViewBuilder.builder()
                        .withStyleClass("material-tooltip-copy-icon")
                        .withImage("/images/other/copy.png")
                        .build())).buildHBox());
    }

    private static void copyLocationToClipboard(final String text) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    private static void addTransferTimeToTooltip(final Data data, final VBox vBox) {
        vBox.getChildren().add(LabelBuilder.builder().build());
        vBox.getChildren().add(LabelBuilder.builder().withText(LocaleService.getStringBinding((data.isUpload()) ? "material.tooltip.data.upload" : "material.tooltip.data.download", data.getTransferTime())).build());
    }

    private static void addBarterInfoToTooltip(final OdysseyMaterial odysseyMaterial, final VBox vBox) {
        final Integer barterSellPrice = BarterConstants.getBarterSellPrice(odysseyMaterial);
        vBox.getChildren().add(LabelBuilder.builder().build());
        vBox.getChildren().add(LabelBuilder.builder().withText(LocaleService.getStringBinding("material.tooltip.barter.sell.price", barterSellPrice == -1 ? "?" : NUMBER_FORMAT.format(barterSellPrice))).build());
        if (odysseyMaterial instanceof Asset) {
            vBox.getChildren().add(LabelBuilder.builder().withText(LocaleService.getStringBinding("material.tooltip.barter.trade", BarterConstants.getBarterValues(odysseyMaterial))).build());
        }
    }

    private static void addRecipesToTooltip(final Map<OdysseyBlueprintName, Integer> recipesContainingMaterial, final VBox vBox) {
        if (!recipesContainingMaterial.isEmpty()) {
            vBox.getChildren().add(LabelBuilder.builder().build());
            vBox.getChildren().add(LabelBuilder.builder().withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE).withText(LocaleService.getStringBinding("material.tooltip.used.in.recipes")).build());
            recipesContainingMaterial.entrySet().stream()
                    .sorted(Comparator.comparing(entry -> ObservableResourceFactory.getResources().getString(entry.getKey().getLocalizationKey())))
                    .forEach(entry -> vBox.getChildren().add(LabelBuilder.builder().withText(ObservableResourceFactory.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale(entry.getKey().getLocalizationKey()) + " (" + entry.getValue() + ")")).build()));
        }
    }

    private static void addHorizonsBlueprintsToTooltip(final Map<HorizonsBlueprint, Integer> horizonsBlueprintMap, final VBox vBox) {
        if (!horizonsBlueprintMap.isEmpty()) {
            vBox.getChildren().add(LabelBuilder.builder().build());
            vBox.getChildren().add(LabelBuilder.builder().withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE).withText(LocaleService.getStringBinding("material.tooltip.used.in.recipes")).build());
            final Map<Pair<HorizonsBlueprintName, HorizonsBlueprintType>, List<Pair<HorizonsBlueprintGrade, Integer>>> groupedBlueprints = new HashMap<>();
            horizonsBlueprintMap.forEach((key1, value) -> {
                final Pair<HorizonsBlueprintName, HorizonsBlueprintType> keyPair = new Pair<>(key1.getHorizonsBlueprintName(), key1.getHorizonsBlueprintType());
                final List<Pair<HorizonsBlueprintGrade, Integer>> gradeValues = groupedBlueprints.computeIfAbsent(keyPair, key -> new ArrayList<>());
                gradeValues.add(new Pair<>(key1.getHorizonsBlueprintGrade(), value));
            });


            groupedBlueprints.entrySet().stream().
                    mapMulti((Map.Entry<Pair<HorizonsBlueprintName, HorizonsBlueprintType>, List<Pair<HorizonsBlueprintGrade, Integer>>> entry, Consumer<StringBinding> consumer) -> {
                        final Map<Integer, List<Pair<HorizonsBlueprintGrade, Integer>>> gradeGroups = entry.getValue().stream().collect(Collectors.groupingBy(Pair::getValue));
                        gradeGroups.forEach((materialAmount, pairs) ->
                                consumer.accept(ObservableResourceFactory.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale(entry.getKey().getKey().getBlueprintCategory().getLocalizationKey()) +
                                                " - " +
                                                LocaleService.getLocalizedStringForCurrentLocale(entry.getKey().getKey().getLocalizationKey()) +
                                                " - " +
                                                LocaleService.getLocalizedStringForCurrentLocale(entry.getKey().getValue().getLocalizationKey()) +
                                                ((!pairs.get(0).getKey().equals(HorizonsBlueprintGrade.NONE)) ? " - " : "") +
                                                (pairs.stream()
                                                        .filter(pair -> pair.getKey().getGrade() > 0)
                                                        .sorted(Comparator.comparingInt(value -> value.getKey().getGrade()))
                                                        .map(pair -> String.valueOf(pair.getKey().getGrade()))
                                                        .collect(Collectors.joining(", "))) +
                                                " (" + materialAmount + ")"
                                        )
                                )
                        );
                    })
                    .sorted(Comparator.comparing(StringBinding::get))
                    .forEach(text -> vBox.getChildren().add(LabelBuilder.builder().withText(text).build()));
        }
    }

    public static boolean isMaterialOnWishlist(final OdysseyMaterial odysseyMaterial) {
        return APPLICATION_STATE.getPreferredCommander()
                .map(commander -> APPLICATION_STATE.getWishlists(commander.getFid()).getWishlists().stream()
                        .anyMatch(wishlist -> wishlist.getItems().stream()
                                .anyMatch(wishlistBlueprint -> OdysseyBlueprintConstants.getRecipe(wishlistBlueprint.getRecipeName()).hasIngredient(odysseyMaterial))))
                .orElse(false);

    }
}
