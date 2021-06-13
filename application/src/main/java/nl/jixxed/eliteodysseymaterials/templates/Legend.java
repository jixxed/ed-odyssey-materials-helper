package nl.jixxed.eliteodysseymaterials.templates;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Good;

import java.io.IOException;
import java.util.List;

public class Legend extends VBox {
    public Legend() {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Legend.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
        addItems();
    }

    private void addItems() {
        final List<MaterialCard> legendItems = List.of(
                new MaterialCard(Good.AGRICULTURALPROCESSSAMPLE, true, "Unused item", null, false),
                new MaterialCard(Good.PUSH, true, "Item used only for unlocking engineers", null, true),
                new MaterialCard(Good.IONISEDGAS, true, "Item used in blueprints", null, false),
                new MaterialCard(Good.SURVEILLANCEEQUIPMENT, true, "Item used in blueprints and for unlocking engineers", null, true),
                new MaterialCard(Asset.AEROGEL, true, "Chemical component", null),
                new MaterialCard(Asset.CIRCUITBOARD, true, "Circuit component", null),
                new MaterialCard(Asset.CARBONFIBREPLATING, true, "Tech component", null),
                new MaterialCard(Data.ESPIONAGEMATERIAL, true, "Unused data", null, false),
                new MaterialCard(Data.CATMEDIA, true, "Data used only for unlocking engineers", null, true),
                new MaterialCard(Data.CHEMICALINVENTORY, true, "Data used in blueprints", null, false),
                new MaterialCard(Data.GENETICRESEARCH, true, "Data used in blueprints and for unlocking engineers", null, true)
        );
        legendItems.forEach(materialCard -> materialCard.getStyleClass().add("legend"));
        this.getChildren().addAll(legendItems);
    }
}
