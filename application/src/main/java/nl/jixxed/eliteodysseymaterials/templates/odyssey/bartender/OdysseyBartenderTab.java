package nl.jixxed.eliteodysseymaterials.templates.odyssey.bartender;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.AssetType;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyBartenderMaterialSelectedEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyTab;

import java.util.*;

import static nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs.BARTENDER;

public class OdysseyBartenderTab extends OdysseyTab implements Template {
    private OdysseyBartenderMaterial selectedMaterial;
    private final Map<AssetType, List<OdysseyBartenderMaterial>> bartenderMaterials = Map.of(
            AssetType.CHEMICAL, new ArrayList<>(),
            AssetType.CIRCUIT, new ArrayList<>(),
            AssetType.TECH, new ArrayList<>()
    );
    private final Map<AssetType, FlowPane> categories = new HashMap<>();
    private ScrollPane scrollPane;
    private VBox bottom;
    private OdysseyBartenderResult odysseyBartenderResult;
    private VBox top;
    private DestroyableLabel title;

    public OdysseyBartenderTab() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.bartender"));
        //what material do you want to trade
        this.title = LabelBuilder.builder().withStyleClass("bartender-header").withText(LocaleService.getStringBinding("tabs.bartender.select.material")).build();
        final Button reset = ButtonBuilder.builder().withOnAction(event -> resetView()).withText(LocaleService.getStringBinding("tab.bartender.total.reset")).build();
        final HBox title = BoxBuilder.builder().withNodes(this.title, new GrowingRegion(), reset).buildHBox();
        this.top = BoxBuilder.builder().withStyleClass("bartender-top").withNodes(title).buildVBox();
        //materials
        this.bottom = BoxBuilder.builder().withStyleClass("bartender-bottom").buildVBox();
        for (final AssetType assetType : Arrays.stream(AssetType.values()).sorted(Comparator.comparing(Enum::name)).toArray(AssetType[]::new)) {
            for (final Asset asset : Asset.values()) {
                if (assetType.equals(asset.getType()) && !asset.isUnknown()) {
                    this.bartenderMaterials.get(assetType).add(new OdysseyBartenderMaterial(asset));
                }
            }
            final FlowPane flowPane = FlowPaneBuilder.builder().withStyleClass("bartender-flow").withOrientation(Orientation.HORIZONTAL).withNodes(this.bartenderMaterials.get(assetType)).build();
            this.categories.put(assetType, flowPane);
            this.bottom.getChildren().add(flowPane);
        }
        final VBox left = BoxBuilder.builder().withStyleClass("bartender-left").withNodes(this.top, this.bottom).buildVBox();
        this.odysseyBartenderResult = new OdysseyBartenderResult();
        this.odysseyBartenderResult.setVisible(false);
        final HBox layout = BoxBuilder.builder().withStyleClass("bartender-layout").withNodes(left, this.odysseyBartenderResult).buildHBox();
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(layout)
                .build();
        this.setContent(this.scrollPane);

    }

    private void resetView() {
        this.title.textProperty().bind(LocaleService.getStringBinding("tabs.bartender.select.material"));
        this.odysseyBartenderResult.reset();
        this.odysseyBartenderResult.setVisible(false);
        this.bottom.getChildren().clear();
        this.categories.values().forEach(flowPane -> {
            flowPane.getChildren().clear();
        });
        for (final AssetType assetType : AssetType.values()) {
            final FlowPane flowPane = this.categories.get(assetType);
            flowPane.getChildren().addAll(this.bartenderMaterials.get(assetType));
            this.bartenderMaterials.get(assetType).forEach(odysseyBartenderMaterial -> odysseyBartenderMaterial.setLayoutMode(LayoutMode.DEFAULT));
        }
        this.bottom.getChildren().addAll(this.categories.values());
        this.top.getChildren().remove(this.selectedMaterial);
        this.selectedMaterial = null;
    }

    @Override
    public void initEventHandling() {
        EventService.addListener(this, OdysseyBartenderMaterialSelectedEvent.class, odysseyBartenderMaterialSelectedEvent -> {
            this.title.textProperty().bind(LocaleService.getStringBinding("tabs.bartender.select.material.trade"));
            final OdysseyBartenderMaterial odysseyBartenderMaterial = odysseyBartenderMaterialSelectedEvent.getOdysseyBartenderMaterial();
            this.selectedMaterial = odysseyBartenderMaterial;
            this.top.getChildren().add(this.selectedMaterial);
            this.categories.forEach((assetType, flowPane) -> {
                if (assetType.equals(odysseyBartenderMaterial.getAsset().getType())) {
                    flowPane.getChildren().remove(odysseyBartenderMaterial);
                    odysseyBartenderMaterial.setLayoutMode(LayoutMode.SELECTED);
                    flowPane.getChildren().forEach(material -> ((OdysseyBartenderMaterial) material).setLayoutMode(LayoutMode.TRADE));

                } else {
                    this.bottom.getChildren().remove(flowPane);
                }
            });
            this.odysseyBartenderResult.setVisible(true);

        });
    }

    @Override
    public OdysseyTabs getTabType() {
        return BARTENDER;
    }
}
