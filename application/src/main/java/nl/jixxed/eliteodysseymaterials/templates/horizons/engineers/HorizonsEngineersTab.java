package nl.jixxed.eliteodysseymaterials.templates.horizons.engineers;

import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsEngineerSearchEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableScrollPane;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

import java.util.Arrays;
import java.util.List;

public class HorizonsEngineersTab extends HorizonsTab implements DestroyableEventTemplate {

    private DestroyableFlowPane flowPane;
    private HorizonsEngineerCard[] horizonsEngineerCards;


    public HorizonsEngineersTab() {
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.addBinding(this.textProperty(),LocaleService.getStringBinding("tabs.engineers"));
        this.horizonsEngineerCards = Arrays.stream(Engineer.values())
                .filter(Engineer::isHorizons)
                .filter(engineer -> !Engineer.UNKNOWN.equals(engineer))
                .map(HorizonsEngineerCard::new)
                .toArray(HorizonsEngineerCard[]::new);
        this.flowPane = FlowPaneBuilder.builder()
                .withStyleClass("engineer-grid")
                .withNodes(this.horizonsEngineerCards)
                .build();
        DestroyableScrollPane scrollPane = register(ScrollPaneBuilder.builder()
                .withContent(this.flowPane)
                .build());
        this.setContent(scrollPane);
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, HorizonsEngineerSearchEvent.class, horizonsEngineerSearchEvent -> {
            update(horizonsEngineerSearchEvent.getSearch());
        }));
    }

    private void update(final String search) {
        this.flowPane.getNodes().clear();

        final List<HorizonsEngineerCard> engineerCards = Arrays.stream(this.horizonsEngineerCards)
                .filter(horizonsEngineerCard -> search.isBlank()
                        || horizonsEngineerCard.getEngineer().getSettlement().getSettlementName().toLowerCase().contains(search.toLowerCase())
                        || horizonsEngineerCard.getEngineer().getStarSystem().getName().toLowerCase().contains(search.toLowerCase())
                        || LocaleService.getLocalizedStringForCurrentLocale(horizonsEngineerCard.getEngineer().getLocalizationKey()).toLowerCase().contains(search.toLowerCase())
                        || hasBlueprintLike(horizonsEngineerCard.getEngineer(), search)
                )
                .toList();
        this.flowPane.getNodes().addAll(engineerCards);
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
