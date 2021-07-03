package nl.jixxed.eliteodysseymaterials.templates;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.AssetType;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Good;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

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
        final List<LegendCard> legendItems = List.of(
                new LegendCard(Good.class, LocaleService.getStringBinding("menu.legend.good.unused"), false, false),
                new LegendCard(Good.class, LocaleService.getStringBinding("menu.legend.good.engineer.only"), true, false),
                new LegendCard(Good.class, LocaleService.getStringBinding("menu.legend.good.blueprint"), false, true),
                new LegendCard(Good.class, LocaleService.getStringBinding("menu.legend.good.blueprint.and.engineer"), true, true),
                new LegendCard(Asset.class, AssetType.CHEMICAL, LocaleService.getStringBinding("menu.legend.asset.chemical"), false, true),
                new LegendCard(Asset.class, AssetType.CIRCUIT, LocaleService.getStringBinding("menu.legend.asset.circuit"), false, true),
                new LegendCard(Asset.class, AssetType.TECH, LocaleService.getStringBinding("menu.legend.asset.tech"), false, true),
                new LegendCard(Data.class, LocaleService.getStringBinding("menu.legend.data.unused"), false, false),
                new LegendCard(Data.class, LocaleService.getStringBinding("menu.legend.data.engineer.only"), true, false),
                new LegendCard(Data.class, LocaleService.getStringBinding("menu.legend.data.blueprint"), false, true),
                new LegendCard(Data.class, LocaleService.getStringBinding("menu.legend.data.blueprint.and.engineer"), true, true)
        );
        legendItems.forEach(legendCard -> legendCard.getStyleClass().add("legend"));
        this.getChildren().addAll(legendItems);
    }
}
