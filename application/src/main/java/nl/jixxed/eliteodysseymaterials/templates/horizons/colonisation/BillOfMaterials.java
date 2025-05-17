package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import nl.jixxed.eliteodysseymaterials.domain.ColonisationItem;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationSearch;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsColonisationSort;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.ColonisationSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsColonisationSearchEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;

import java.util.List;

public class BillOfMaterials extends DestroyableFlowPane implements DestroyableEventTemplate, DestroyableComponent {

    ColonisationItem colonisationItem;
    private ColonisationSearch currentSearch = new ColonisationSearch("", HorizonsColonisationSort.ALPHABETICAL);


    public BillOfMaterials() {
        final HorizonsColonisationSort materialSort = HorizonsColonisationSort.valueOf(PreferencesService.getPreference("search.colonisation.sort", "ALPHABETICAL"));
        currentSearch.setColonisationSort(materialSort);
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("bill-of-materials");
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ColonisationSelectedEvent.class, event -> {
            setBuildable(event.getColonisationItem());
        }));
        register(EventService.addListener(true, this, HorizonsColonisationSearchEvent.class, event -> {
            this.currentSearch = event.getSearch();
            update();
        }));
    }

    public void setBuildable(ColonisationItem colonisationItem) {
        this.colonisationItem = colonisationItem;
        createCards();

    }


    private void createCards() {
        clear();
        final List<BillOfMaterialsEntry> entries = colonisationItem.getConstructionRequirements().entrySet().stream()
                .map(entry -> new BillOfMaterialsEntry(colonisationItem, entry.getKey(), entry.getValue()))
                .sorted(HorizonsColonisationSort.getSort(currentSearch))
                .toList();
        this.getNodes().addAll(entries);
    }

    private void update() {
        final List<BillOfMaterialsEntry> cards = this.getChildren().stream()
                .map(BillOfMaterialsEntry.class::cast)
                .sorted(HorizonsColonisationSort.getSort(this.currentSearch))
                .toList();
        this.getChildren().clear();
        this.getChildren().addAll(cards);
    }

    public void clear() {
        this.getNodes().clear();
    }
}
