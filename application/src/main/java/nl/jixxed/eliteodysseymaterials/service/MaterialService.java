package nl.jixxed.eliteodysseymaterials.service;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.constants.BarterConstants;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.EconomyStatistic;
import nl.jixxed.eliteodysseymaterials.domain.MaterialStatistic;
import nl.jixxed.eliteodysseymaterials.domain.SettlementStatistic;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.POIHelper;
import org.controlsfx.control.PopOver;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialService {

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
    private static final String STYLECLASS_MATERIAL_TOOLTIP_SUBTITLE = "material-tooltip-subtitle";
    private static final String STYLECLASS_MATERIAL_TOOLTIP_LOCATION_LINE = "material-tooltip-location-line";

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

    private static VBox getMaterialPopOverContent(final HorizonsMaterial horizonsMaterial) {
        final VBox vBox = BoxBuilder.builder().buildVBox();
        LabelBuilder.builder().withText(LocaleService.getStringBinding(horizonsMaterial.getLocalizationKey())).build();
        return vBox;


    }

    private static VBox getMaterialPopOverContent(final OdysseyMaterial odysseyMaterial) {
        final VBox vBox = BoxBuilder.builder().buildVBox();
        if (odysseyMaterial.isUnknown()) {
            vBox.getChildren().add(LabelBuilder.builder().withStyleClass("material-tooltip-title").withText(LocaleService.getStringBinding("material.tooltip.unknown")).build());
        } else {
            vBox.getChildren().add(LabelBuilder.builder().withStyleClass("material-tooltip-title").withText(LocaleService.getStringBinding(odysseyMaterial.getLocalizationKey())).build());
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

    public static void addMaterialInfoPopOver(final Node hoverableNode, final OdysseyMaterial odysseyMaterial) {
        hoverableNode.setOnMouseEntered(mouseEvent -> {
            final Node contentNode = getMaterialPopOverContent(odysseyMaterial);
            contentNode.getStyleClass().add("material-popover");
            final PopOver popOver = new PopOver(contentNode);
            popOver.setDetachable(false);
            popOver.setHeaderAlwaysVisible(false);
            popOver.show(hoverableNode);
            final Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100)));
            timeline.setOnFinished(finishEvent -> {
                if (hoverableNode.isHover() || contentNode.isHover()) {
                    timeline.play();
                } else {
                    popOver.hide(Duration.ZERO);
                    popOver.setContentNode(null);
                    timeline.stop();
                }
            });
            hoverableNode.setOnMouseExited(mouseEvent2 -> timeline.play());
        });
    }

    public static void addMaterialInfoPopOver(final Node hoverableNode, final HorizonsMaterial horizonsMaterial) {
        hoverableNode.setOnMouseEntered(mouseEvent -> {
            final Node contentNode = getMaterialPopOverContent(horizonsMaterial);
            contentNode.getStyleClass().add("material-popover");
            final PopOver popOver = new PopOver(contentNode);
            popOver.setDetachable(false);
            popOver.setHeaderAlwaysVisible(false);
            popOver.show(hoverableNode);
            final Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100)));
            timeline.setOnFinished(finishEvent -> {
                if (hoverableNode.isHover() || contentNode.isHover()) {
                    timeline.play();
                } else {
                    popOver.hide(Duration.ZERO);
                    popOver.setContentNode(null);
                    timeline.stop();
                }
            });
            hoverableNode.setOnMouseExited(mouseEvent2 -> timeline.play());
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
                    NotificationService.showInformation("Clipboard", "System name copied.");
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


}
