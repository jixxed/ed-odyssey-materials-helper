package nl.jixxed.eliteodysseymaterials.templates.dialog.importdialog;

import javafx.geometry.Orientation;
import javafx.util.Duration;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.templates.components.EdAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIconView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.BlueprintsTextFlow;

import java.math.BigDecimal;

public class ShipSlot extends DestroyableHBox implements DestroyableTemplate {

    private final Slot slot;

    public ShipSlot(Slot slot) {
        this.slot = slot;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("ship-slot");
        DestroyableLabel slotSize = LabelBuilder.builder().withStyleClass("slot-size").withNonLocalizedText(slot.getSlotSizeName()).build();


        DestroyableVBox slotSizeBox = BoxBuilder.builder().withStyleClass("slot-size-box").withNodes(slotSize).buildVBox();
        DestroyableVBox labelBox = BoxBuilder.builder().withStyleClass("label-box").buildVBox();
        if(slot.getShipModule() != null){
            labelBox.getNodes().add(iconBox(slot.getShipModule()));
            DestroyableLabel moduleSize = LabelBuilder.builder().withStyleClass("module-size").withNonLocalizedText(slot.getShipModule().getModuleSize().toString()).build();
            DestroyableLabel moduleRating;
            if(slot.getShipModule() instanceof HardpointModule hardpointModule) {
                var graphic = createIconWithoutTooltip(hardpointModule.getMounting().getIcon(), "icon");
                moduleRating = LabelBuilder.builder().withStyleClass("module-rating").withGraphic(graphic).build();
            }else{
                moduleRating = LabelBuilder.builder().withStyleClass("module-rating").withNonLocalizedText(slot.getShipModule().getModuleClass().toString()).build();
            }

            DestroyableLabel moduleName = LabelBuilder.builder().withStyleClass("module-name").withText(slot.getShipModule().getLocalizationKey()).build();
            DestroyableHBox name = BoxBuilder.builder().withStyleClass("name-box").withNodes(moduleSize, moduleRating, moduleName).buildHBox();
            labelBox.getNodes().add(name);

            if(!slot.getShipModule().getModifications().isEmpty()) {
//                DestroyableLabel moduleBlueprint = LabelBuilder.builder().withStyleClass("blueprint").withText(slot.getShipModule().getModifications().getFirst().getModification().getLocalizationKey(true)).build();
//                DestroyableLabel moduleBlueprintCompleteness = LabelBuilder.builder().withStyleClass("completion").withNonLocalizedText(slot.getShipModule().getModifications().getFirst().getModificationCompleteness().orElse(BigDecimal.ZERO).toString()).build();
//                DestroyableLabel moduleGrade = LabelBuilder.builder().withStyleClass("grade").withNonLocalizedText(slot.getShipModule().getModifications().getFirst().getGrade().toString()).build();
//                DestroyableLabel moduleEffect = LabelBuilder.builder().withStyleClass("effect").withText(slot.getShipModule().getExperimentalEffects().getFirst().getLocalizationKey(true)).build();
                BlueprintsTextFlow blueprintsTextFlow = new BlueprintsTextFlow();
                blueprintsTextFlow.update(slot.getShipModule(), slot.getOldShipModule());
                DestroyableHBox blueprint = BoxBuilder.builder().withStyleClass("blueprint-box").withNodes(blueprintsTextFlow).buildHBox();
                labelBox.getNodes().add(blueprint);
            }

//            if(!slot.getShipModule().getExperimentalEffects().isEmpty()) {
//                labelBox.getNodes().add(moduleEffect);
//            }
        }

        DestroyableSeparator destroyableSeparator = new DestroyableSeparator(Orientation.VERTICAL);
        destroyableSeparator.getStyleClass().add("splitter");

        this.getNodes().addAll(slotSizeBox, destroyableSeparator, labelBox);
    }

    private DestroyableHBox iconBox(final ShipModule shipModule) {
        var iconBox = BoxBuilder.builder().withStyleClass("icon-box").buildHBox();
        if (shipModule != null) {
            addIcon(iconBox, (shipModule instanceof UtilityModule um && Mounting.GIMBALLED.equals(um.getMounting())), EdAwesomeIcon.SHIPS_GIMBALLED, "ship.module.icon.tooltip.gimballed", "module-icon");
            addIcon(iconBox, (shipModule instanceof UtilityModule um && Mounting.TURRETED.equals(um.getMounting())), EdAwesomeIcon.SHIPS_TURRETED, "ship.module.icon.tooltip.turreted", "module-icon");
            addIcon(iconBox, (shipModule instanceof UtilityModule um && Mounting.FIXED.equals(um.getMounting())), EdAwesomeIcon.SHIPS_FIXED, "ship.module.icon.tooltip.fixed", "module-icon");

            addIcon(iconBox, shipModule.isPreEngineered(), EdAwesomeIcon.SHIPS_PREENGINEERED, "ship.module.icon.tooltip.pre.engineered", "module-icon");
            addIcon(iconBox, shipModule.isStoreExclusive(), EdAwesomeIcon.OTHER_ARX, "ship.module.icon.tooltip.arx", "module-icon");
            addIcon(iconBox, shipModule.isAdvanced(), EdAwesomeIcon.SHIPS_ADVANCED, "ship.module.icon.tooltip.advanced", "module-icon-wide");
            addIcon(iconBox, shipModule.isEnhanced(), EdAwesomeIcon.SHIPS_ENHANCED, "ship.module.icon.tooltip.enhanced", "module-icon-wide");
            addIcon(iconBox, shipModule.isSeeker(), EdAwesomeIcon.SHIPS_SEEKER, "ship.module.icon.tooltip.seeker", "module-icon-wide");
            addIcon(iconBox, shipModule.isDumbfire(), EdAwesomeIcon.SHIPS_DUMBFIRE, "ship.module.icon.tooltip.dumbfire", "module-icon-wide");
            addIcon(iconBox, shipModule.isLegacy(), EdAwesomeIcon.SHIPS_LEGACY, "ship.module.icon.tooltip.legacy", "module-icon");
            addIcon(iconBox, Origin.POWERPLAY.equals(shipModule.getOrigin()), EdAwesomeIcon.OTHER_POWERPLAY_OPEN, "ship.module.icon.tooltip.powerplay", "module-icon");
            addIcon(iconBox, Origin.GUARDIAN.equals(shipModule.getOrigin()), EdAwesomeIcon.SHIPS_GUARDIAN_1, EdAwesomeIcon.SHIPS_GUARDIAN_2, "ship.module.icon.tooltip.guardian", "module-icon");
            addIcon(iconBox, shipModule.isMultiCrew(), EdAwesomeIcon.SHIPS_MULTICREW_RADAR, "ship.module.icon.tooltip.multicrew", "module-icon");
            addIcon(iconBox, shipModule.isCGExclusive(), EdAwesomeIcon.OTHER_COMMUNITYGOAL, "ship.module.icon.tooltip.community.goal.module", "module-icon");
            iconBox.getNodes().add(new GrowingRegion());
            addIcon(iconBox, shipModule.isSynthesisBasic(), EdAwesomeIcon.SHIPS_SYNTHESIS_BASIC, "ship.module.icon.tooltip.synthesis.basic", "module-icon", "synthesis");
            addIcon(iconBox, shipModule.isSynthesisStandard(), EdAwesomeIcon.SHIPS_SYNTHESIS_STANDARD, "ship.module.icon.tooltip.synthesis.standard", "module-icon", "synthesis");
            addIcon(iconBox, shipModule.isSynthesisPremium(), EdAwesomeIcon.SHIPS_SYNTHESIS_PREMIUM, "ship.module.icon.tooltip.synthesis.premium", "module-icon", "synthesis");
            iconBox.getNodes().add(new GrowingRegion());
            if (!shipModule.getModifications().isEmpty()) {
                if (!shipModule.isLegacy()) {
                    iconBox.getNodes().add(
                            LabelBuilder.builder()
                                    .withStyleClass("module-percent-modified")
                                    .withNonLocalizedText(shipModule.getModifications().getFirst().getModificationCompleteness().map(completeness -> Formatters.NUMBER_FORMAT_2.format(completeness.multiply(BigDecimal.valueOf(100))) + "%").orElse(""))
                                    .build()
                    );
                }
                iconBox.getNodes().addAll(
                        createIconWithTooltip(EdAwesomeIcon.SHIPS_ENGINEER, "ship.module.icon.tooltip.engineered", "module-icon"),
                        LabelBuilder.builder()
                                .withStyleClass("module-grade")
                                .withNonLocalizedText(String.valueOf(shipModule.getModifications().getFirst().getGrade().getGrade()))
                                .build()
                );

            }
        }
        return iconBox;
    }

    private void addIcon(DestroyableHBox iconBox, boolean condition, EdAwesomeIcon icon, String tooltipKey, String... styleClasses) {
        if (condition) {
            iconBox.getNodes().add(createIconWithTooltip(icon, tooltipKey, styleClasses));
        }
    }
    private void addIcon(DestroyableHBox iconBox, boolean condition, EdAwesomeIcon icon, EdAwesomeIcon icon2, String tooltipKey, String... styleClasses) {
        if (condition) {
            iconBox.getNodes().add(createIconWithTooltip(icon, icon2, tooltipKey, styleClasses));
        }
    }

    private EdAwesomeIconViewPane createIconWithTooltip(EdAwesomeIcon icon, String tooltipKey, String ... styleClasses) {
        EdAwesomeIconViewPane iconView = EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClasses(styleClasses)
                .withIcons(new EdAwesomeIconView(icon))
                .build();
        final DestroyableTooltip tooltip = TooltipBuilder.builder()
                .withShowDelay(Duration.seconds(0.1))
                .withText(tooltipKey)
                .build();
        tooltip.install(iconView);
        tooltip.setHideDelay(Duration.seconds(0));
        return iconView;
    }
    private EdAwesomeIconViewPane createIconWithTooltip(EdAwesomeIcon icon,EdAwesomeIcon icon2, String tooltipKey, String ... styleClasses) {
        EdAwesomeIconViewPane iconView = EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClasses(styleClasses)
                .withIcons(new EdAwesomeIconView(icon), new EdAwesomeIconView(icon2))
                .build();
        final DestroyableTooltip tooltip = TooltipBuilder.builder()
                .withShowDelay(Duration.seconds(0.1))
                .withText(tooltipKey)
                .build();
        tooltip.install(iconView);
        tooltip.setHideDelay(Duration.seconds(0));
        return iconView;
    }

    private static EdAwesomeIconViewPane createIconWithoutTooltip(EdAwesomeIcon icon, String... styleClasses) {
        return EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClasses(styleClasses)
                .withIcons(new EdAwesomeIconView(icon))
                .build();
    }
}
