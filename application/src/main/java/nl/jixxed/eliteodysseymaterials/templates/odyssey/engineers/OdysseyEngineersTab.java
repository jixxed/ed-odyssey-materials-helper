package nl.jixxed.eliteodysseymaterials.templates.odyssey.engineers;

import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyEngineerSearchEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableScrollPane;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyTab;

import java.util.Arrays;
import java.util.List;

public class OdysseyEngineersTab extends OdysseyTab implements DestroyableEventTemplate {

    private DestroyableScrollPane scrollPane;
    private DestroyableFlowPane flowPane;

    private final String query = "";
    private OdysseyEngineerCard[] odysseyEngineerCards;


    public OdysseyEngineersTab() {
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("engineers-tab");
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("tabs.engineers"));
        this.odysseyEngineerCards = Arrays.stream(Engineer.values())
                .filter(Engineer::isOdyssey)
                .filter(engineer -> !Engineer.UNKNOWN.equals(engineer))
                .map(OdysseyEngineerCard::new)
                .toArray(OdysseyEngineerCard[]::new);
        this.flowPane = FlowPaneBuilder.builder()
                .withStyleClass("engineer-grid")
                .withNodes(this.odysseyEngineerCards)
                .build();
        this.scrollPane = ScrollPaneBuilder.builder()
                .withStyleClass("engineers-tab-content")
                .withContent(this.flowPane)
                .build();
        this.setContent(this.scrollPane);
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, OdysseyEngineerSearchEvent.class, odysseyEngineerSearchEvent -> {
            update(odysseyEngineerSearchEvent.getSearch());
        }));
    }

    private void update(final String search) {
        this.flowPane.getNodes().clear();

        final List<OdysseyEngineerCard> engineerCards = Arrays.stream(this.odysseyEngineerCards)
                .filter(odysseyEngineerCard -> search.isBlank()
                        || odysseyEngineerCard.getEngineer().getSettlement().getSettlementName().toLowerCase().contains(search.toLowerCase())
                        || odysseyEngineerCard.getEngineer().getStarSystem().getName().toLowerCase().contains(search.toLowerCase())
                        || LocaleService.getLocalizedStringForCurrentLocale(odysseyEngineerCard.getEngineer().getSpecialisation().getLocalizationKey()).toLowerCase().contains(search.toLowerCase())
                        || LocaleService.getLocalizedStringForCurrentLocale(odysseyEngineerCard.getEngineer().getLocalizationKey()).toLowerCase().contains(search.toLowerCase())
                        || hasBlueprintLike(odysseyEngineerCard.getEngineer(), search)
                )
                .toList();
        this.flowPane.getNodes().addAll(engineerCards);
    }

    private boolean hasBlueprintLike(final Engineer engineer, final String search) {
        return OdysseyBlueprintConstants.getSuitModuleBlueprints().entrySet().stream()
                .filter(entry -> entry.getValue().getEngineers().contains(engineer))
                .anyMatch(entry -> LocaleService.getLocalizedStringForCurrentLocale(entry.getKey().getLocalizationKey()).toLowerCase().contains(search.toLowerCase()))
                || OdysseyBlueprintConstants.getWeaponModuleBlueprints().entrySet().stream()
                .filter(entry -> entry.getValue().getEngineers().contains(engineer))
                .anyMatch(entry -> LocaleService.getLocalizedStringForCurrentLocale(entry.getKey().getLocalizationKey()).toLowerCase().contains(search.toLowerCase()));
    }

    @Override
    public OdysseyTabs getTabType() {
        return OdysseyTabs.ENGINEERS;
    }
}
