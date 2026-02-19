package nl.jixxed.eliteodysseymaterials.templates.horizons.permits;

import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.PermitsSearch;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsPermitsShow;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabType;
import nl.jixxed.eliteodysseymaterials.enums.Permit;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.PermitSearchEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableScrollPane;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class HorizonsPermitsTab extends HorizonsTab implements DestroyableEventTemplate {
    private DestroyableFlowPane flowPane;
    private PermitCard[] permitCards;

    private PermitsSearch currentSearch;

    public HorizonsPermitsTab() {
        final HorizonsPermitsShow filter = HorizonsPermitsShow.valueOf(PreferencesService.getPreference("search.horizons.permits.filter", "ALL"));
        currentSearch = new PermitsSearch("", filter);
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("permit-tab");
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("tabs.permit"));
        this.permitCards = Arrays.stream(Permit.values())
                .filter(permit -> !permit.isUnknown())
                .map(PermitCard::new)
                .sorted(Comparator.comparing(card -> LocaleService.getLocalizedStringForCurrentLocale(card.getPermit().getLocalizationKey())))
                .toArray(PermitCard[]::new);
        this.flowPane = FlowPaneBuilder.builder()
                .withStyleClass("permit-flow")
                .withNodes(this.permitCards)
                .build();
        DestroyableScrollPane scrollPane = register(ScrollPaneBuilder.builder()
                .withStyleClass("permit-tab-content")
                .withContent(this.flowPane)
                .build());
        this.setContent(scrollPane);
        update(currentSearch);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, PermitSearchEvent.class, permitSearchEvent -> {
            currentSearch = permitSearchEvent.getPermitsSearch();
            update(permitSearchEvent.getPermitsSearch());
        }));
    }

    private void update(final PermitsSearch search) {
        Arrays.stream(this.permitCards).forEach(permitCard -> {
            boolean visible = HorizonsPermitsShow.getFilter(search).test(permitCard) && (search.getQuery().isBlank()
                    || permitCard.getLocations().stream().anyMatch(location -> location.getStarSystem().getName().toLowerCase().contains(search.getQuery().toLowerCase()))
                    || LocaleService.getLocalizedStringForCurrentLocale(permitCard.getPermit().getLocalizationKey()).toLowerCase().contains(search.getQuery().toLowerCase())
                    || LocaleService.getLocalizedStringForCurrentLocale(permitCard.getPermit().getDescriptionLocalizationKey()).toLowerCase().contains(search.getQuery().toLowerCase()));
            permitCard.setVisible(visible);
            permitCard.setManaged(visible);
        });
        this.flowPane.getChildren().clear();
        final List<PermitCard> cards = Arrays.stream(this.permitCards)
                .sorted(Comparator.comparing(card -> LocaleService.getLocalizedStringForCurrentLocale(card.getPermit().getLocalizationKey())))
                .toList();
        this.flowPane.getChildren().addAll(cards);
    }

    @Override
    public HorizonsTabType getTabType() {
        return HorizonsTabType.PERMIT;
    }
}
