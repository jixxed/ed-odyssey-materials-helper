package nl.jixxed.eliteodysseymaterials.templates.odyssey.bartender;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import nl.jixxed.eliteodysseymaterials.service.event.CommanderSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
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
    private OdysseyBartenderRecipes odysseyBartenderRecipes;
    private VBox top;
    private DestroyableLabel title;
    private VBox right;
    private final BooleanProperty detailView = new SimpleBooleanProperty(false);
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

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
        final Button addAll = ButtonBuilder.builder().withOnAction(event -> addAllAssets()).withText(LocaleService.getStringBinding("tab.bartender.total.add.all")).build();
        reset.visibleProperty().bind(this.detailView);
        addAll.visibleProperty().bind(this.detailView);
        final HBox title = BoxBuilder.builder().withStyleClass("bartender-header-title-box").withNodes(this.title, new GrowingRegion(), addAll, reset).buildHBox();
        this.top = BoxBuilder.builder().withStyleClass("bartender-top").withNodes(title).buildVBox();
        //materials
        this.bottom = BoxBuilder.builder().withStyleClass("bartender-bottom").buildVBox();
        for (final AssetType assetType : Arrays.stream(AssetType.values()).sorted().toArray(AssetType[]::new)) {
            for (final Asset asset : Asset.values()) {
                if (assetType.equals(asset.getType()) && !asset.isUnknown()) {
                    this.bartenderMaterials.get(assetType).add(new OdysseyBartenderMaterial(asset));
                }
            }
            final FlowPane flowPane = FlowPaneBuilder.builder().withStyleClass("bartender-flow").withOrientation(Orientation.HORIZONTAL).withNodes(this.bartenderMaterials.get(assetType)).build();
            this.categories.put(assetType, flowPane);
        }
        this.bottom.getChildren().addAll(this.categories.values());
        final VBox left = BoxBuilder.builder().withStyleClass("bartender-left").withNodes(this.top, this.bottom).buildVBox();
        this.odysseyBartenderResult = new OdysseyBartenderResult();
        this.odysseyBartenderRecipes = new OdysseyBartenderRecipes();
        this.right = BoxBuilder.builder().withStyleClass("settings-spacing-10").withNodes(this.odysseyBartenderRecipes).buildVBox();
        final HBox layout = BoxBuilder.builder().withStyleClasses("bartender-layout").withNodes(left, this.right).buildHBox();
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(layout)
                .build();
        this.setContent(this.scrollPane);

    }

    private void addAllAssets() {
        this.bartenderMaterials.values().stream().flatMap(Collection::stream).forEach(odysseyBartenderMaterial -> {
            if (LayoutMode.TRADE.equals(odysseyBartenderMaterial.getLayoutMode().get())) {
                odysseyBartenderMaterial.addAllAssets();
            }
        });
    }

    private void resetView() {
        this.detailView.set(false);
        this.title.textProperty().bind(LocaleService.getStringBinding("tabs.bartender.select.material"));
        this.odysseyBartenderResult.reset();
        this.right.getChildren().remove(this.odysseyBartenderResult);
        this.bottom.getChildren().clear();
        this.categories.values().forEach(flowPane -> {
            flowPane.getChildren().clear();
        });
        for (final AssetType assetType : Arrays.stream(AssetType.values()).sorted().toArray(AssetType[]::new)) {
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
        this.eventListeners.add(EventService.addListener(this, OdysseyBartenderMaterialSelectedEvent.class, odysseyBartenderMaterialSelectedEvent -> {
            this.detailView.set(true);
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
            if (!this.right.getChildren().contains(this.odysseyBartenderResult)) {
                this.right.getChildren().add(0, this.odysseyBartenderResult);
            }

        }));
        this.eventListeners.add(EventService.addListener(this, 9, CommanderSelectedEvent.class, event -> {
            resetView();
        }));
    }

    @Override
    public OdysseyTabs getTabType() {
        return BARTENDER;
    }
}
