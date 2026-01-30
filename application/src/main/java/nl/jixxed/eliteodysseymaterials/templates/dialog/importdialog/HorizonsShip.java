package nl.jixxed.eliteodysseymaterials.templates.dialog.importdialog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ClipboardShip;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

@Slf4j
public class HorizonsShip extends DestroyableVBox implements DestroyableTemplate {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    private String json;

    public HorizonsShip(String json) {
        this.json = json;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("ship");
        try {
            final ClipboardShip clipboardShip = OBJECT_MAPPER.readValue(json, ClipboardShip.class);
            final Ship ship = ShipMapper.toShip(clipboardShip.getShipConfiguration());
            var hardpointSlots = ship.getHardpointSlots().stream()
                    .map(ShipSlot::new)
                    .toList();
            var coreSlots = ship.getCoreSlots().stream()
                    .map(ShipSlot::new)
                    .toList();
            var optionalSlots = ship.getOptionalSlots().stream()
                    .map(ShipSlot::new)
                    .toList();
            var utilitySlots = ship.getUtilitySlots().stream()
                    .map(ShipSlot::new)
                    .toList();

            var hardpointsTitle = LabelBuilder.builder()
                    .withStyleClass("slot-title")
                    .withText("blueprint.category.name.hardpoint")
                    .build();
            var coreModulesTitle = LabelBuilder.builder()
                    .withStyleClass("slot-title")
                    .withText("blueprint.category.name.core_internal")
                    .build();
            var optionalModulesTitle = LabelBuilder.builder()
                    .withStyleClass("slot-title")
                    .withText("blueprint.category.name.optional_internal")
                    .build();
            var utilitiesTitle = LabelBuilder.builder()
                    .withStyleClass("slot-title")
                    .withText("blueprint.category.name.utility_mount")
                    .build();

            DestroyableVBox hardpoints = BoxBuilder.builder()
                    .withStyleClass("slots")
                    .withNode(hardpointsTitle)
                    .withNodes(hardpointSlots)
                    .buildVBox();
            DestroyableVBox coreModules = BoxBuilder.builder()
                    .withStyleClass("slots")
                    .withNode(coreModulesTitle)
                    .withNodes(coreSlots)
                    .buildVBox();
            DestroyableVBox optionalModules = BoxBuilder.builder()
                    .withStyleClass("slots")
                    .withNode(optionalModulesTitle)
                    .withNodes(optionalSlots)
                    .buildVBox();
            DestroyableVBox utilities = BoxBuilder.builder()
                    .withStyleClass("slots")
                    .withNode(utilitiesTitle)
                    .withNodes(utilitySlots)
                    .buildVBox();
            DestroyableLabel shipType = LabelBuilder.builder().withStyleClass("type").withText(ship.getShipType().getLocalizationKey()).build();
            DestroyableLabel shipName = LabelBuilder.builder().withStyleClass("name").withNonLocalizedText(clipboardShip.getShipConfiguration().getName()).build();
            DestroyableVBox detailsData = BoxBuilder.builder()
                    .withStyleClass("details")
                    .withNodes(shipType, shipName)
                    .buildVBox();
            var shipImage = ResizableImageViewBuilder.builder()
                    .withImage("/images/ships/ship/" + ship.getShipType().name().toLowerCase() + ".1.png")
                    .withStyleClass("ship-image")
                    .withPreserveRatio(true)
                    .build();
            DestroyableHBox shipDetails = BoxBuilder.builder()
                    .withStyleClass("ship-details")
                    .withNodes(shipImage, detailsData)
                    .buildHBox();
            DestroyableFlowPane flowPane = FlowPaneBuilder.builder()
                    .withStyleClass("list")
                    .withNodes(shipDetails, hardpoints, coreModules, optionalModules, utilities)
                    .build();

            this.getNodes().add(flowPane);
        } catch (JsonProcessingException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }
}
