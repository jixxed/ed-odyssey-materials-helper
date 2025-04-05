package nl.jixxed.eliteodysseymaterials.templates.odyssey.bartender;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Orientation;
import javafx.scene.control.skin.ScrollPaneSkin;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.AssetType;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.CommanderSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyBartenderMaterialSelectedEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyTab;

import java.util.*;

import static nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs.BARTENDER;

public class OdysseyBartenderTab extends OdysseyTab implements DestroyableEventTemplate {
    private OdysseyBartenderMaterial selectedMaterial;
    private final Map<AssetType, List<OdysseyBartenderMaterial>> bartenderMaterials = Map.of(
            AssetType.CHEMICAL, new ArrayList<>(),
            AssetType.CIRCUIT, new ArrayList<>(),
            AssetType.TECH, new ArrayList<>()
    );
    private final Map<AssetType, DestroyableFlowPane> categories = new HashMap<>();
    private DestroyableScrollPane scrollPane;
    private DestroyableVBox bottom;
    private DestroyableVBox top;
    private OdysseyBartenderResult odysseyBartenderResult;
    private OdysseyBartenderBlueprints odysseyBartenderBlueprints;
    private DestroyableLabel title;
    private DestroyableVBox right;
    private DestroyableRegion filler;
    private final BooleanProperty detailView = new SimpleBooleanProperty(false);


    public OdysseyBartenderTab() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("bartender-tab");
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("tabs.bartender"));
        //what material do you want to trade
        this.title = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("tabs.bartender.select.material")
                .build();
        final DestroyableButton reset = ButtonBuilder.builder()
                .withOnAction(event -> selectView())
                .withText("tab.bartender.total.reset")
                .build();
        final DestroyableButton addAll = ButtonBuilder.builder()
                .withOnAction(event -> addAllAssets())
                .withText("tab.bartender.total.add.all")
                .build();
        reset.addBinding(reset.visibleProperty(), this.detailView);
        addAll.addBinding(addAll.visibleProperty(), this.detailView);
        final DestroyableHBox titleSection = BoxBuilder.builder()
                .withStyleClass("title-section")
                .withNodes(this.title, new GrowingRegion(), addAll, reset).buildHBox();
        this.top = BoxBuilder.builder()
                .withStyleClass("top-section")
                .buildVBox();
        //materials
        for (final AssetType assetType : Arrays.stream(AssetType.values()).sorted().toArray(AssetType[]::new)) {
            for (final Asset asset : Asset.values()) {
                if (assetType.equals(asset.getType()) && !asset.isUnknown()) {
                    this.bartenderMaterials.get(assetType).add(register(new OdysseyBartenderMaterial(asset)));
                }
            }
            final DestroyableFlowPane flowPane = FlowPaneBuilder.builder()
                    .withStyleClass("bartender-flow")
                    .withOrientation(Orientation.HORIZONTAL)
                    .withNodes(this.bartenderMaterials.get(assetType))
                    .build();
            this.categories.put(assetType, flowPane);
        }
        this.bottom = BoxBuilder.builder()
                .withStyleClass("bottom-section")
                .withNodes(this.categories.values())
                .buildVBox();

        final DestroyableVBox left = BoxBuilder.builder()
                .withStyleClass("left-section")
                .withNodes(titleSection, this.top, this.bottom).buildVBox();
        this.odysseyBartenderResult = new OdysseyBartenderResult();
        this.odysseyBartenderBlueprints = new OdysseyBartenderBlueprints();
        this.odysseyBartenderResult.setVisible(false);
        this.odysseyBartenderResult.setManaged(false);
        this.filler = new DestroyableRegion();
        this.right = BoxBuilder.builder()
                .withStyleClass("right-section")
                .withNodes(this.filler, this.odysseyBartenderResult, this.odysseyBartenderBlueprints)
                .buildVBox();
        final DestroyableHBox layout = BoxBuilder.builder()
                .withStyleClasses("bartender-layout")
                .withNodes(left, new GrowingRegion(), this.right)
                .buildHBox();
        this.scrollPane = ScrollPaneBuilder.builder()
                .withStyleClass("bartender-tab-content")
                .withContent(layout)
                .build();

        this.scrollPane.addChangeListener(this.scrollPane.skinProperty(), (_, _, _) ->
                ((ScrollPaneSkin) this.scrollPane.getSkin()).getVerticalScrollBar().valueProperty()
                        .addListener((_, _, newValue2) ->
                                setFillerHeight((double) newValue2)));

        this.scrollPane.addChangeListener(this.scrollPane.heightProperty(), (_, _, _) ->
                setFillerHeight(((ScrollPaneSkin) this.scrollPane.getSkin()).getVerticalScrollBar().getValue()));
        selectView();
        this.setContent(this.scrollPane);
    }

    private void setFillerHeight(final double scrollbarPos) {
        this.filler.setMinHeight(Math.round(scrollbarPos * (this.scrollPane.getContent().getBoundsInParent().getHeight() - this.scrollPane.getViewportBounds().getHeight())));
        this.filler.setMaxHeight(Math.round(scrollbarPos * (this.scrollPane.getContent().getBoundsInParent().getHeight() - this.scrollPane.getViewportBounds().getHeight())));
    }

    private void addAllAssets() {
        this.bartenderMaterials.values().stream().flatMap(Collection::stream).forEach(odysseyBartenderMaterial -> {
            if (LayoutMode.TRADE.equals(odysseyBartenderMaterial.getLayoutMode().get())) {
                odysseyBartenderMaterial.addAllAssets();
            }
        });
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, OdysseyBartenderMaterialSelectedEvent.class, odysseyBartenderMaterialSelectedEvent -> {
            selectedMaterialToTrade(odysseyBartenderMaterialSelectedEvent.getOdysseyBartenderMaterial());
            Platform.runLater(() -> ((ScrollPaneSkin) this.scrollPane.getSkin()).getVerticalScrollBar().setValue(0D));
        }));
        register(EventService.addListener(true, this, 9, CommanderSelectedEvent.class, _ -> selectView()));
    }

    private void selectedMaterialToTrade(OdysseyBartenderMaterial odysseyBartenderMaterial) {
        this.selectedMaterial = odysseyBartenderMaterial;
        this.detailView.set(true);
        this.title.addBinding(this.title.textProperty(), LocaleService.getStringBinding("tabs.bartender.select.material.trade"));
        this.top.getChildren().add(this.selectedMaterial);
        this.top.setManaged(true);
        this.top.setVisible(true);
        this.categories.forEach((assetType, flowPane) -> {
            if (assetType.equals(odysseyBartenderMaterial.getAsset().getType())) {
                flowPane.getChildren().remove(odysseyBartenderMaterial);
                odysseyBartenderMaterial.setLayoutMode(LayoutMode.SELECTED);
                flowPane.getChildren().forEach(material -> ((OdysseyBartenderMaterial) material).setLayoutMode(LayoutMode.TRADE));

            } else {
                flowPane.setVisible(false);
                flowPane.setManaged(false);
            }
        });
        this.odysseyBartenderResult.setVisible(true);
        this.odysseyBartenderResult.setManaged(true);
    }

    private void selectView() {
        this.detailView.set(false);
        this.title.addBinding(this.title.textProperty(), LocaleService.getStringBinding("tabs.bartender.select.material"));
        this.odysseyBartenderResult.reset();
        this.odysseyBartenderResult.setVisible(false);
        this.odysseyBartenderResult.setManaged(false);
        this.bottom.getChildren().clear();
        this.categories.values().forEach(flowPane -> flowPane.getChildren().clear());
        for (final AssetType assetType : Arrays.stream(AssetType.values()).sorted().toArray(AssetType[]::new)) {
            final DestroyableFlowPane flowPane = this.categories.get(assetType);
            flowPane.setVisible(true);
            flowPane.setManaged(true);
            flowPane.getChildren().addAll(this.bartenderMaterials.get(assetType));
            this.bartenderMaterials.get(assetType).forEach(odysseyBartenderMaterial -> odysseyBartenderMaterial.setLayoutMode(LayoutMode.DEFAULT));
        }
        this.bottom.getChildren().addAll(this.categories.values());
        this.top.getChildren().clear();
        this.top.setManaged(false);
        this.top.setVisible(false);
        this.selectedMaterial = null;
    }

    @Override
    public OdysseyTabs getTabType() {
        return BARTENDER;
    }
}
