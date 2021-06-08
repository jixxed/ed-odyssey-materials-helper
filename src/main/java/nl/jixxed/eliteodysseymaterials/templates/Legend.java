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
                new MaterialCard(Good.AGRICULTURALPROCESSSAMPLE, "Unused item", "", false),
                new MaterialCard(Good.PUSH, "Item used only for unlocking engineers", "", true),
                new MaterialCard(Good.IONISEDGAS, "Item used in blueprints", "", false),
                new MaterialCard(Good.SURVEILLANCEEQUIPMENT, "Item used in blueprints and for unlocking engineers", "", true),
                new MaterialCard(Asset.AEROGEL, "Chemical component", ""),
                new MaterialCard(Asset.CIRCUITBOARD, "Circuit component", ""),
                new MaterialCard(Asset.CARBONFIBREPLATING, "Tech component", ""),
                new MaterialCard(Data.ESPIONAGEMATERIAL, "Unused data", "", false),
                new MaterialCard(Data.CATMEDIA, "Data used only for unlocking engineers", "", true),
                new MaterialCard(Data.CHEMICALINVENTORY, "Data used in blueprints", "", false),
                new MaterialCard(Data.GENETICRESEARCH, "Data used in blueprints and for unlocking engineers", "", true)

        );
        legendItems.forEach(materialCard -> materialCard.getStyleClass().add("legend"));
        this.getChildren().addAll(legendItems);
    }
}
