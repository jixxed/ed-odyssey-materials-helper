package nl.jixxed.eliteodysseymaterials.templates.horizons.powerplay;

import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.enums.Power;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.PowerSearchEvent;
import nl.jixxed.eliteodysseymaterials.service.event.PowerplayEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableScrollPane;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PowerplayTab extends HorizonsTab implements DestroyableEventTemplate {
    private DestroyableFlowPane flowPane;
    private PowerplayCard[] powerplayCards;

    private String currentSearch = "";

    public PowerplayTab() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("powerplay-tab");
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("tabs.powerplay"));
        this.powerplayCards = Arrays.stream(Power.values())
                .filter(power -> power != Power.NONE)
                .map(PowerplayCard::new)
                .sorted(Comparator.comparing((PowerplayCard powerPlayCard) -> !powerPlayCard.getPower().equals(Power.ALL))
                        .thenComparing(powerplayCard -> !powerplayCard.getPower().equals(ApplicationState.getInstance().getPower()))
                        .thenComparing(powerplayCard -> LocaleService.getLocalizedStringForCurrentLocale(powerplayCard.getPower().getLocalizationKey())))
                .toArray(PowerplayCard[]::new);
        this.flowPane = FlowPaneBuilder.builder()
                .withStyleClass("power-grid")
                .withNodes(this.powerplayCards)
                .build();
        DestroyableScrollPane scrollPane = register(ScrollPaneBuilder.builder()
                .withStyleClass("powerplay-tab-content")
                .withContent(this.flowPane)
                .build());
        this.setContent(scrollPane);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, PowerSearchEvent.class, powerSearchEvent -> {
            currentSearch = powerSearchEvent.getSearch();
            update(powerSearchEvent.getSearch());
        }));

        register(EventService.addListener(true, this, PowerplayEvent.class, _ -> update(currentSearch)));
    }

    //TODO test with visible/managed state instead of recreate
    private void update(final String search) {
        this.flowPane.getNodes().clear();

        final List<PowerplayCard> cards = Arrays.stream(this.powerplayCards)
                .filter(powerplayCard -> search.isBlank()
                        || powerplayCard.getPower().getPerks().keySet().stream().anyMatch(powerPerk -> LocaleService.getLocalizedStringForCurrentLocale(powerPerk.getLocalizationKey()).toLowerCase().contains(search.toLowerCase()))
                        || powerplayCard.getPower().getPerks().keySet().stream().anyMatch(powerPerk -> LocaleService.getLocalizedStringForCurrentLocale(powerPerk.getLocalizationTitleKey()).toLowerCase().contains(search.toLowerCase()))
                        || powerplayCard.getPower().getUnlockables().keySet().stream().anyMatch(module -> LocaleService.getLocalizedStringForCurrentLocale(module.getLocalizationKey()).toLowerCase().contains(search.toLowerCase()))
                        || powerplayCard.getPower().getStarSystem().getName().toLowerCase().contains(search.toLowerCase())
                        || LocaleService.getLocalizedStringForCurrentLocale(powerplayCard.getPower().getLocalizationKey()).toLowerCase().contains(search.toLowerCase())
                )
                .sorted(Comparator.comparing((PowerplayCard powerPlayCard) -> !powerPlayCard.getPower().equals(Power.ALL))
                        .thenComparing(powerplayCard -> !powerplayCard.getPower().equals(ApplicationState.getInstance().getPower()))
                        .thenComparing(powerplayCard -> LocaleService.getLocalizedStringForCurrentLocale(powerplayCard.getPower().getLocalizationKey())))
                .toList();
        this.flowPane.getNodes().addAll(cards);
    }

    @Override
    public HorizonsTabs getTabType() {
        return HorizonsTabs.POWERPLAY;
    }
}
