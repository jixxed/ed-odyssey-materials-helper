package nl.jixxed.eliteodysseymaterials.templates.odyssey.engineers;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyEngineerSearchEvent;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyTab;

import java.util.Arrays;
import java.util.List;

public class OdysseyEngineersTab extends OdysseyTab {

    private ScrollPane scrollPane;
    private FlowPane flowPane;

    private final String query = "";
    private OdysseyEngineerCard[] odysseyEngineerCards;

    public OdysseyEngineersTab() {
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.engineers"));
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
                .withContent(this.flowPane)
                .build();
        this.setContent(this.scrollPane);
    }


    private void initEventHandling() {
        EventService.addListener(this, OdysseyEngineerSearchEvent.class, odysseyEngineerSearchEvent -> {
            update(odysseyEngineerSearchEvent.getSearch());
        });
    }

    private void update(final String search) {
        this.flowPane.getChildren().clear();

        final List<OdysseyEngineerCard> engineerCards = Arrays.stream(this.odysseyEngineerCards)
                .filter(odysseyEngineerCard -> search.isBlank()
                        || odysseyEngineerCard.getEngineer().getSettlement().getSettlementName().toLowerCase().contains(search.toLowerCase())
                        || odysseyEngineerCard.getEngineer().getStarSystem().getName().toLowerCase().contains(search.toLowerCase())
                        || LocaleService.getLocalizedStringForCurrentLocale(odysseyEngineerCard.getEngineer().getSpecialisation().getLocalizationKey()).toLowerCase().contains(search.toLowerCase())
                        || LocaleService.getLocalizedStringForCurrentLocale(odysseyEngineerCard.getEngineer().getLocalizationKey()).toLowerCase().contains(search.toLowerCase())
                        || hasBlueprintLike(odysseyEngineerCard.getEngineer(), search)
                )
                .toList();
        this.flowPane.getChildren().addAll(engineerCards);
    }

    private boolean hasBlueprintLike(final Engineer engineer, final String search) {
        return OdysseyBlueprintConstants.getSuitModuleBlueprints().entrySet().stream()
                .filter(entry -> entry.getValue().getEngineers().contains(engineer))
                .anyMatch(entry -> entry.getKey().getLocalizationKey().toLowerCase().contains(search.toLowerCase()))
                || OdysseyBlueprintConstants.getWeaponModuleBlueprints().entrySet().stream()
                .filter(entry -> entry.getValue().getEngineers().contains(engineer))
                .anyMatch(entry -> entry.getKey().getLocalizationKey().toLowerCase().contains(search.toLowerCase()));
    }

    @Override
    public OdysseyTabs getTabType() {
        return OdysseyTabs.ENGINEERS;
    }
}
