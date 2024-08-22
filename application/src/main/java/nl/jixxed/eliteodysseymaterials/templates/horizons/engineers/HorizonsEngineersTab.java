package nl.jixxed.eliteodysseymaterials.templates.horizons.engineers;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsEngineerSearchEvent;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HorizonsEngineersTab extends HorizonsTab {

    private ScrollPane scrollPane;
    private FlowPane flowPane;
    private HorizonsEngineerCard[] horizonsEngineerCards;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    public HorizonsEngineersTab() {
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.engineers"));
        this.horizonsEngineerCards = Arrays.stream(Engineer.values())
                .filter(Engineer::isHorizons)
                .filter(engineer -> !Engineer.UNKNOWN.equals(engineer))
                .map(HorizonsEngineerCard::new)
                .toArray(HorizonsEngineerCard[]::new);
        this.flowPane = FlowPaneBuilder.builder()
                .withStyleClass("engineer-grid")
                .withNodes(this.horizonsEngineerCards)
                .build();
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(this.flowPane)
                .build();
        this.setContent(this.scrollPane);
    }

    private void initEventHandling() {
        this.eventListeners.add(EventService.addListener(true, this, HorizonsEngineerSearchEvent.class, horizonsEngineerSearchEvent -> {
            update(horizonsEngineerSearchEvent.getSearch());
        }));
    }

    private void update(final String search) {
        this.flowPane.getChildren().clear();

        final List<HorizonsEngineerCard> engineerCards = Arrays.stream(this.horizonsEngineerCards)
                .filter(horizonsEngineerCard -> search.isBlank()
                        || horizonsEngineerCard.getEngineer().getSettlement().getSettlementName().toLowerCase().contains(search.toLowerCase())
                        || horizonsEngineerCard.getEngineer().getStarSystem().getName().toLowerCase().contains(search.toLowerCase())
                        || LocaleService.getLocalizedStringForCurrentLocale(horizonsEngineerCard.getEngineer().getLocalizationKey()).toLowerCase().contains(search.toLowerCase())
                        || hasBlueprintLike(horizonsEngineerCard.getEngineer(), search)
                )
                .toList();
        this.flowPane.getChildren().addAll(engineerCards);
    }

    private boolean hasBlueprintLike(final Engineer engineer, final String search) {
        return HorizonsBlueprintConstants.getHardpointBlueprints().values().stream()
                .flatMap(horizonsBlueprintTypeMapMap -> horizonsBlueprintTypeMapMap.values().stream())
                .flatMap(horizonsBlueprintGradeHorizonsBlueprintMap -> horizonsBlueprintGradeHorizonsBlueprintMap.values().stream())
                .filter(horizonsBlueprint -> horizonsBlueprint.getEngineers().contains(engineer))
                .anyMatch(horizonsBlueprint -> LocaleService.getLocalizedStringForCurrentLocale(horizonsBlueprint.getBlueprintName().getLocalizationKey()).toLowerCase().contains(search.toLowerCase()))
                || HorizonsBlueprintConstants.getUtilityMountBlueprints().values().stream()
                .flatMap(horizonsBlueprintTypeMapMap -> horizonsBlueprintTypeMapMap.values().stream())
                .flatMap(horizonsBlueprintGradeHorizonsBlueprintMap -> horizonsBlueprintGradeHorizonsBlueprintMap.values().stream())
                .filter(horizonsBlueprint -> horizonsBlueprint.getEngineers().contains(engineer))
                .anyMatch(horizonsBlueprint -> LocaleService.getLocalizedStringForCurrentLocale(horizonsBlueprint.getBlueprintName().getLocalizationKey()).toLowerCase().contains(search.toLowerCase()))
                || HorizonsBlueprintConstants.getCoreInternalBlueprints().values().stream()
                .flatMap(horizonsBlueprintTypeMapMap -> horizonsBlueprintTypeMapMap.values().stream())
                .flatMap(horizonsBlueprintGradeHorizonsBlueprintMap -> horizonsBlueprintGradeHorizonsBlueprintMap.values().stream())
                .filter(horizonsBlueprint -> horizonsBlueprint.getEngineers().contains(engineer))
                .anyMatch(horizonsBlueprint -> LocaleService.getLocalizedStringForCurrentLocale(horizonsBlueprint.getBlueprintName().getLocalizationKey()).toLowerCase().contains(search.toLowerCase()))
                || HorizonsBlueprintConstants.getOptionalInternalBlueprints().values().stream()
                .flatMap(horizonsBlueprintTypeMapMap -> horizonsBlueprintTypeMapMap.values().stream())
                .flatMap(horizonsBlueprintGradeHorizonsBlueprintMap -> horizonsBlueprintGradeHorizonsBlueprintMap.values().stream())
                .filter(horizonsBlueprint -> horizonsBlueprint.getEngineers().contains(engineer))
                .anyMatch(horizonsBlueprint -> LocaleService.getLocalizedStringForCurrentLocale(horizonsBlueprint.getBlueprintName().getLocalizationKey()).toLowerCase().contains(search.toLowerCase()));
    }

    @Override
    public HorizonsTabs getTabType() {
        return HorizonsTabs.ENGINEERS;
    }
}
