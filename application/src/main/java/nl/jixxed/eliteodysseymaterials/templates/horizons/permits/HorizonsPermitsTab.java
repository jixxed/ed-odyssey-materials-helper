package nl.jixxed.eliteodysseymaterials.templates.horizons.permits;

import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabType;
import nl.jixxed.eliteodysseymaterials.enums.Permit;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableScrollPane;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

import java.util.Arrays;
import java.util.List;

public class HorizonsPermitsTab extends HorizonsTab implements DestroyableEventTemplate {
    private DestroyableFlowPane flowPane;
    private PermitCard[] permitCards;

    private String currentSearch = "";

    public HorizonsPermitsTab() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("permit-tab");
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("tabs.permit"));
        this.permitCards = Arrays.stream(Permit.values())
                .filter(permit -> permit != Permit.UNKNOWN)
                .map(PermitCard::new)
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
    }

    @Override
    public void initEventHandling() {
//        register(EventService.addListener(true, this, PermitSearchEvent.class, permitSearchEvent -> {
//            currentSearch = permitSearchEvent.getSearch();
//            update(permitSearchEvent.getSearch());
//        }));
    }

    private void update(final String search) {
        Arrays.stream(this.permitCards).forEach(permitCard -> {
//            boolean visible = search.isBlank()
//                    || permitCard.getPower().getPerks().keySet().stream().anyMatch(powerPerk -> LocaleService.getLocalizedStringForCurrentLocale(powerPerk.getLocalizationKey()).toLowerCase().contains(search.toLowerCase()))
//                    || powerplayCard.getPower().getPerks().keySet().stream().anyMatch(powerPerk -> LocaleService.getLocalizedStringForCurrentLocale(powerPerk.getLocalizationTitleKey()).toLowerCase().contains(search.toLowerCase()))
//                    || powerplayCard.getPower().getUnlockables().keySet().stream().anyMatch(module -> LocaleService.getLocalizedStringForCurrentLocale(module.getLocalizationKey()).toLowerCase().contains(search.toLowerCase()))
//                    || powerplayCard.getPower().getStarSystem().getName().toLowerCase().contains(search.toLowerCase())
//                    || LocaleService.getLocalizedStringForCurrentLocale(powerplayCard.getPower().getLocalizationKey()).toLowerCase().contains(search.toLowerCase());
//            powerplayCard.setVisible(visible);
//            powerplayCard.setManaged(visible);
        });
        this.flowPane.getChildren().clear();
        final List<PermitCard> cards = Arrays.stream(this.permitCards)
//                .sorted(Comparator.comparing((PowerplayCard powerPlayCard) -> !powerPlayCard.getPower().equals(Power.ALL))
//                        .thenComparing(powerplayCard -> !powerplayCard.getPower().equals(ApplicationState.getInstance().getPower()))
//                        .thenComparing(powerplayCard -> LocaleService.getLocalizedStringForCurrentLocale(powerplayCard.getPower().getLocalizationKey())))
                .toList();
        this.flowPane.getChildren().addAll(cards);
    }

    @Override
    public HorizonsTabType getTabType() {
        return HorizonsTabType.PERMIT;
    }
}
