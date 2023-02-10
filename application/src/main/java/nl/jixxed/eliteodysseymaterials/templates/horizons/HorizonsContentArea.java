package nl.jixxed.eliteodysseymaterials.templates.horizons;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.Action;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.helper.AnchorPaneHelper;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.horizons.commodities.HorizonsCommoditiesOverviewTab;
import nl.jixxed.eliteodysseymaterials.templates.horizons.engineers.HorizonsEngineersTab;
import nl.jixxed.eliteodysseymaterials.templates.horizons.materials.HorizonsMaterialTab;
import nl.jixxed.eliteodysseymaterials.templates.horizons.menu.HorizonsBlueprintBar;
import nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.HorizonsShipBuilderTab;
import nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist.HorizonsWishlistTab;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("java:S110")
@Slf4j
public
class HorizonsContentArea extends AnchorPane {

    private HorizonsSearchBar searchBar;
    private HorizonsMaterialTab horizonsMaterialOverview;
    private HorizonsBlueprintBar recipeBar;
    private TabPane tabs;
    private VBox body;
    private HorizonsEngineersTab horizonsEngineersTab;
    private HorizonsWishlistTab horizonsWishlistTab;
    private HorizonsShipBuilderTab horizonsShipBuilderTab;
    private HorizonsCommoditiesOverviewTab horizonsCommoditiesOverview;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    public HorizonsContentArea(final Application application) {
        initComponents(application);
        initEventHandling();
    }

    private void initComponents(final Application application) {
        this.horizonsMaterialOverview = new HorizonsMaterialTab();
        this.horizonsMaterialOverview.setClosable(false);
        this.horizonsCommoditiesOverview = new HorizonsCommoditiesOverviewTab();
        this.horizonsCommoditiesOverview.setClosable(false);

        this.horizonsEngineersTab = new HorizonsEngineersTab();
        this.horizonsEngineersTab.setClosable(false);
        this.horizonsWishlistTab = new HorizonsWishlistTab(application);
        this.horizonsWishlistTab.setClosable(false);
        this.horizonsShipBuilderTab = new HorizonsShipBuilderTab();
        this.horizonsShipBuilderTab.setClosable(false);

        this.searchBar = new HorizonsSearchBar();
        this.tabs = new TabPane(this.horizonsMaterialOverview, this.horizonsCommoditiesOverview, this.horizonsWishlistTab, this.horizonsShipBuilderTab, this.horizonsEngineersTab);
        this.tabs.getStyleClass().add("odyssey-tab-pane");
        this.tabs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                final HorizonsTabs tabType = ((HorizonsTab) newValue).getTabType();
                EventService.publish(new HorizonsTabSelectedEvent(tabType));
            }
        });
        this.tabs.setSide(Side.LEFT);
        VBox.setVgrow(this.tabs, Priority.ALWAYS);

        this.body = new VBox(this.searchBar, this.tabs);
        HBox.setHgrow(this.body, Priority.ALWAYS);

        this.recipeBar = new HorizonsBlueprintBar(application);
        this.recipeBar.visibleProperty().addListener((observable, oldValue, newValue) -> setBodyAnchor(newValue, this.recipeBar.getWidth()));
        this.recipeBar.widthProperty().addListener((observable, oldValue, newValue) -> setBodyAnchor(isRecipeBarVisible(), newValue.doubleValue()));
        this.recipeBar.visibleProperty().set(isRecipeBarVisible());


        AnchorPaneHelper.setAnchor(this.recipeBar, 0.0, 0.0, 0.0, null);
        setBodyAnchor(isRecipeBarVisible(), this.recipeBar.getWidth());
        AnchorPaneHelper.setAnchor(this.tabs, 0.0, 0.0, 0.0, null);

        this.getChildren().addAll(this.recipeBar, this.body);
    }

    private boolean isRecipeBarVisible() {
        return PreferencesService.getPreference(PreferenceConstants.HORIZONS_RECIPES_VISIBLE, Boolean.TRUE);
    }

    private void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, HorizonsWishlistBlueprintEvent.class, wishlistEvent -> {
            if (Action.ADDED.equals(wishlistEvent.getAction())) {
                this.tabs.getSelectionModel().select(this.horizonsWishlistTab);
            }
        }));
        this.eventListeners.add(EventService.addListener(this, HorizonsBlueprintClickEvent.class, blueprintClickEvent -> {
            this.recipeBar.setVisible(true);
            PreferencesService.setPreference(PreferenceConstants.HORIZONS_RECIPES_VISIBLE, true);
        }));
        this.eventListeners.add(EventService.addListener(this, ApplicationLifeCycleEvent.class, applicationLifeCycleEvent -> setBodyAnchor(isRecipeBarVisible(), this.recipeBar.getWidth())));
        this.eventListeners.add(EventService.addListener(this, AfterFontSizeSetEvent.class, fontSizeEvent -> setBodyAnchor(isRecipeBarVisible(), this.recipeBar.getWidth())));
        this.eventListeners.add(EventService.addListener(this, MenuButtonClickedEvent.class, event -> {
            if (Expansion.HORIZONS.equals(event.getExpansion())) {
                final boolean visibility = !this.recipeBar.isVisible();
                this.recipeBar.setVisible(visibility);
                PreferencesService.setPreference(PreferenceConstants.HORIZONS_RECIPES_VISIBLE, visibility);
            }
        }));
        this.eventListeners.add(EventService.addListener(this, ImportResultEvent.class, importResultEvent -> {
            if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_HORIZONS_WISHLIST) || importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_EDSY_WISHLIST) || importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_CORIOLIS_WISHLIST)) {
                this.tabs.getSelectionModel().select(this.horizonsWishlistTab);
            }
        }));
    }

    private void setBodyAnchor(final boolean isRecipeBarVisible, final double width) {
        AnchorPaneHelper.setAnchor(this.body, 0.0, 0.0, isRecipeBarVisible ? width : 0.0, 0.0);
    }

}
