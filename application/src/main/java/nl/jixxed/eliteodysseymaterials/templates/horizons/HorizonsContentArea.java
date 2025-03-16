package nl.jixxed.eliteodysseymaterials.templates.horizons;

import javafx.geometry.Side;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TabPaneBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.Action;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.helper.AnchorPaneHelper;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableAnchorPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTabPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation.HorizonsColonisationTab;
import nl.jixxed.eliteodysseymaterials.templates.horizons.commodities.HorizonsCommoditiesOverviewTab;
import nl.jixxed.eliteodysseymaterials.templates.horizons.engineers.HorizonsEngineersTab;
import nl.jixxed.eliteodysseymaterials.templates.horizons.materials.HorizonsMaterialTab;
import nl.jixxed.eliteodysseymaterials.templates.horizons.menu.HorizonsBlueprintBar;
import nl.jixxed.eliteodysseymaterials.templates.horizons.powerplay.PowerplayTab;
import nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.HorizonsShipBuilderTab;
import nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist.HorizonsWishlistTab;

@SuppressWarnings("java:S110")
@Slf4j
public class HorizonsContentArea extends DestroyableAnchorPane implements DestroyableEventTemplate {

    private HorizonsBlueprintBar recipeBar;
    private DestroyableTabPane tabs;
    private DestroyableVBox body;
    private HorizonsWishlistTab horizonsWishlistTab;
    private HorizonsShipBuilderTab horizonsShipBuilderTab;

    public HorizonsContentArea() {
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        HorizonsMaterialTab horizonsMaterialOverview = new HorizonsMaterialTab();
        horizonsMaterialOverview.setClosable(false);
        HorizonsCommoditiesOverviewTab horizonsCommoditiesOverview = new HorizonsCommoditiesOverviewTab();
        horizonsCommoditiesOverview.setClosable(false);

        HorizonsEngineersTab horizonsEngineersTab = new HorizonsEngineersTab();
        horizonsEngineersTab.setClosable(false);
        PowerplayTab powerplayTab = new PowerplayTab();
        powerplayTab.setClosable(false);
        this.horizonsWishlistTab = new HorizonsWishlistTab();
        this.horizonsWishlistTab.setClosable(false);
        this.horizonsShipBuilderTab = new HorizonsShipBuilderTab();
        this.horizonsShipBuilderTab.setClosable(false);
        HorizonsColonisationTab horizonsColonisationTab = new HorizonsColonisationTab();
        horizonsColonisationTab.setClosable(false);

        HorizonsSearchBar searchBar = new HorizonsSearchBar();
        this.tabs = TabPaneBuilder.builder()
                .withTabs(horizonsMaterialOverview, horizonsCommoditiesOverview, this.horizonsWishlistTab, this.horizonsShipBuilderTab, horizonsEngineersTab, powerplayTab, horizonsColonisationTab)
                .withStyleClass("odyssey-tab-pane")
                .withSelectedItemListener((_, _, newValue) -> {
                    if (newValue != null) {
                        final HorizonsTabs tabType = ((HorizonsTab) newValue).getTabType();
                        EventService.publish(new HorizonsTabSelectedEvent(tabType));
                    }
                    PreferencesService.setPreference(PreferenceConstants.SELECTED_TAB_HORIZONS, this.tabs.getTabs().indexOf(newValue));
                })
                .withSide(Side.LEFT)
                .build();
        this.tabs.getSelectionModel().select(Math.min(PreferencesService.getPreference(PreferenceConstants.SELECTED_TAB_HORIZONS, 0), this.tabs.getTabs().size() - 1));
        VBox.setVgrow(this.tabs, Priority.ALWAYS);

        this.body = BoxBuilder.builder()
                .withNodes(searchBar, this.tabs)
                .buildVBox();
        HBox.setHgrow(this.body, Priority.ALWAYS);

        this.recipeBar = new HorizonsBlueprintBar();
        addChangeListener(this.recipeBar.visibleProperty(), (_, _, newValue) -> setBodyAnchor(newValue, this.recipeBar.getWidth()));
        addChangeListener(this.recipeBar.widthProperty(), (_, _, newValue) -> setBodyAnchor(isRecipeBarVisible(), newValue.doubleValue()));
        this.recipeBar.setVisible(isRecipeBarVisible());


        AnchorPaneHelper.setAnchor(this.recipeBar, 0.0, 0.0, 0.0, null);
        setBodyAnchor(isRecipeBarVisible(), this.recipeBar.getWidth());
        AnchorPaneHelper.setAnchor(this.tabs, 0.0, 0.0, 0.0, null);

        this.getNodes().addAll(this.recipeBar, this.body);
    }

    private boolean isRecipeBarVisible() {
        return PreferencesService.getPreference(PreferenceConstants.HORIZONS_RECIPES_VISIBLE, Boolean.TRUE);
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, HorizonsWishlistBlueprintEvent.class, wishlistEvent -> {
            if (Action.ADDED.equals(wishlistEvent.getAction())) {
                this.tabs.getSelectionModel().select(this.horizonsWishlistTab);
            }
        }));
        register(EventService.addListener(true, this, HorizonsWishlistOpenShipBuilderEvent.class, _ ->
                this.tabs.getSelectionModel().select(this.horizonsShipBuilderTab)));
        register(EventService.addListener(true, this, HorizonsBlueprintClickEvent.class, _ -> {
            this.recipeBar.setVisible(true);
            PreferencesService.setPreference(PreferenceConstants.HORIZONS_RECIPES_VISIBLE, true);
        }));
        register(EventService.addListener(true, this, ApplicationLifeCycleEvent.class, _ ->
                setBodyAnchor(isRecipeBarVisible(), this.recipeBar.getWidth())));
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, _ ->
                setBodyAnchor(isRecipeBarVisible(), this.recipeBar.getWidth())));
        register(EventService.addListener(true, this, MenuButtonClickedEvent.class, event -> {
            if (Expansion.HORIZONS.equals(event.getExpansion())) {
                final boolean visibility = !this.recipeBar.isVisible();
                this.recipeBar.setVisible(visibility);
                PreferencesService.setPreference(PreferenceConstants.HORIZONS_RECIPES_VISIBLE, visibility);
            }
        }));
        register(EventService.addListener(true, this, ImportResultEvent.class, importResultEvent -> {
            final ImportResult.ResultType resultType = importResultEvent.getResult().getResultType();
            if (isWishlistResult(resultType)) {
                this.tabs.getSelectionModel().select(this.horizonsWishlistTab);
            } else if (isShipBuilderResult(resultType)) {
                this.tabs.getSelectionModel().select(this.horizonsShipBuilderTab);
            }
        }));
    }

    private static boolean isShipBuilderResult(ImportResult.ResultType resultType) {
        return resultType.equals(ImportResult.ResultType.SUCCESS_HORIZONS_SHIP);
    }

    private static boolean isWishlistResult(ImportResult.ResultType resultType) {
        return resultType.equals(ImportResult.ResultType.SUCCESS_HORIZONS_WISHLIST)
                || resultType.equals(ImportResult.ResultType.SUCCESS_EDSY_WISHLIST)
                || resultType.equals(ImportResult.ResultType.SUCCESS_CORIOLIS_WISHLIST);
    }

    private void setBodyAnchor(final boolean isRecipeBarVisible, final double width) {
        AnchorPaneHelper.setAnchor(this.body, 0.0, 0.0, isRecipeBarVisible ? width : 0.0, 0.0);
    }

}
