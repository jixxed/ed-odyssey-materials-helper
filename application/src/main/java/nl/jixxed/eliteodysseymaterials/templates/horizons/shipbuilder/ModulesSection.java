package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsShipSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.util.List;

public class ModulesSection extends DestroyableFlowPane implements DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    private DestroyableVBox hardpointsColumn;
    private DestroyableVBox coreColumn;
    private DestroyableVBox optionalColumn;
    private DestroyableVBox utilityColumn;

    public ModulesSection() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("modules");
        initShipLayout();

    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, 0, HorizonsShipSelectedEvent.class, _ -> {
            APPLICATION_STATE.getPreferredCommander()
                    .flatMap(commander -> ShipService.getShipConfigurations(commander).getSelectedShipConfiguration())
                    .ifPresent(configuration -> APPLICATION_STATE.setShip(ShipMapper.toShip(configuration)));
            initShipSlots();
        }));
    }

    public void initShipLayout() {
        this.hardpointsColumn = BoxBuilder.builder()
                .withStyleClass("slots-column")
                .buildVBox();
        this.coreColumn = BoxBuilder.builder()
                .withStyleClass("slots-column")
                .buildVBox();
        this.optionalColumn = BoxBuilder.builder()
                .withStyleClass("slots-column")
                .buildVBox();
        this.utilityColumn = BoxBuilder.builder()
                .withStyleClass("slots-column")
                .buildVBox();
        initShipSlots();

        var hardpointsAndCore = BoxBuilder.builder().withStyleClass("column-set").withNodes(this.hardpointsColumn, this.coreColumn).buildHBox();
        var optionalAndUtilities = BoxBuilder.builder().withStyleClass("column-set").withNodes(this.optionalColumn, this.utilityColumn).buildHBox();

        this.prefWrapLengthProperty().bind(hardpointsAndCore.widthProperty().add(optionalAndUtilities.widthProperty()));

        this.getNodes().addAll(hardpointsAndCore, optionalAndUtilities);
    }

    public void initShipSlots() {
        this.hardpointsColumn.getNodes().clear();
        this.coreColumn.getNodes().clear();
        this.optionalColumn.getNodes().clear();
        this.utilityColumn.getNodes().clear();
        if (APPLICATION_STATE.getShip() != null) {
            populateColumn(this.hardpointsColumn, "blueprint.category.name.hardpoint", APPLICATION_STATE.getShip().getHardpointSlots());
            populateColumn(this.coreColumn, "blueprint.category.name.core_internal", APPLICATION_STATE.getShip().getCoreSlots());
            populateColumn(this.optionalColumn, "blueprint.category.name.optional_internal", APPLICATION_STATE.getShip().getOptionalSlots());
            populateColumn(this.utilityColumn, "blueprint.category.name.utility_mount", APPLICATION_STATE.getShip().getUtilitySlots());
        }
    }

    private void populateColumn(DestroyableVBox coreColumn, String columnNameKey, List<? extends Slot> slots) {
        coreColumn.getNodes().add(LabelBuilder.builder().withStyleClass("column-header").withText(columnNameKey).build());
        coreColumn.getNodes().addAll(slots.stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new));
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
    }
}
