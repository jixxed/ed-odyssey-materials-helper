package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.Armour;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SlotBoxEntry extends VBox {
    Label name;
    List<HBox> options;

    public SlotBoxEntry(final SlotBox slotBox, final List<ShipModule> shipModulesList) {
        final ShipModule firstModule = shipModulesList.get(0);
        final List<ShipModule> shipModules = (firstModule instanceof Armour)
                ? shipModulesList.stream().filter(shipModule -> ((Armour) shipModule).getShipType().equals(slotBox.getTab().getShip().getShipType())).toList()
                : shipModulesList;
        this.name = LabelBuilder.builder().withNonLocalizedText(firstModule.getName().lcName()).build();
        final Stream<List<ShipModule>> stream = shipModules.stream().allMatch(shipModule -> shipModule.getModuleClass().equals(firstModule.getModuleClass()))
                ? Stream.of(shipModules)
                : shipModules.stream().collect(Collectors.groupingBy(ShipModule::getModuleSize)).values().stream();

        this.options =
                stream.sorted(Comparator.comparing(shipModuleSubList -> shipModuleSubList.get(0).getModuleSize())).map(shipModuleSubList ->
                        shipModuleSubList.stream().map(shipModule ->
                                ButtonBuilder.builder().withNonLocalizedText(shipModule.getModuleSize() + "" + shipModule.getModuleClass()).withOnAction(event -> {
                                    slotBox.getSlot().setShipModule(shipModule.Clone());
                                    slotBox.refresh();
                                    slotBox.close();
                                }).build()
                        ).toArray(Button[]::new)
                ).map(buttons -> BoxBuilder.builder().withNodes(buttons).buildHBox()).toList();
        this.getChildren().add(this.name);
        this.getChildren().addAll(this.options);
    }
}
