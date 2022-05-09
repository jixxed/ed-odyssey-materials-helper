package nl.jixxed.eliteodysseymaterials.templates;

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
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;
import nl.jixxed.eliteodysseymaterials.helper.AnchorPaneHelper;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

@SuppressWarnings("java:S110")
@Slf4j
class OdysseyContentArea extends AnchorPane {

    private SearchBar searchBar;
    private OdysseyBlueprintBar odysseyBlueprintBar;
    private OverviewTab overview;
    private WishlistTab wishlistTab;
    private OdysseyEngineersTab odysseyEngineersTab;
    private LoadoutEditorTab loadoutEditorTab;
    private TradeTab tradeTab;
    private TabPane tabs;
    private VBox body;

    OdysseyContentArea(final Application application) {
        initComponents(application);
        initEventHandling();
    }

    private void initComponents(final Application application) {
        this.overview = new OverviewTab();
        this.wishlistTab = new WishlistTab();
        this.loadoutEditorTab = new LoadoutEditorTab();
        this.odysseyEngineersTab = new OdysseyEngineersTab();
        this.tradeTab = new TradeTab();
        this.overview.setClosable(false);
        this.wishlistTab.setClosable(false);
        this.loadoutEditorTab.setClosable(false);
        this.odysseyEngineersTab.setClosable(false);
        this.tradeTab.setClosable(false);

        this.searchBar = new SearchBar();
        this.tabs = new TabPane(this.overview, this.wishlistTab, this.loadoutEditorTab, this.tradeTab, this.odysseyEngineersTab);
        this.tabs.getStyleClass().add("odyssey-tab-pane");
        this.tabs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                final OdysseyTabs tabType = ((EDOTab) newValue).getTabType();
                EventService.publish(new OdysseyTabSelectedEvent(tabType));
            }
        });
        this.tabs.setSide(Side.LEFT);
        VBox.setVgrow(this.tabs, Priority.ALWAYS);

        this.body = new VBox(this.searchBar, this.tabs);
        HBox.setHgrow(this.body, Priority.ALWAYS);

        this.odysseyBlueprintBar = new OdysseyBlueprintBar(application);
        this.odysseyBlueprintBar.visibleProperty().addListener((observable, oldValue, newValue) -> setBodyAnchor(newValue, this.odysseyBlueprintBar.getWidth()));
        this.odysseyBlueprintBar.widthProperty().addListener((observable, oldValue, newValue) -> setBodyAnchor(isRecipeBarVisible(), newValue.doubleValue()));
        this.odysseyBlueprintBar.visibleProperty().set(isRecipeBarVisible());

        AnchorPaneHelper.setAnchor(this.odysseyBlueprintBar, 0.0, 0.0, 0.0, null);
        setBodyAnchor(isRecipeBarVisible(), this.odysseyBlueprintBar.getWidth());
        AnchorPaneHelper.setAnchor(this.tabs, 0.0, 0.0, 0.0, null);

        this.getChildren().addAll(this.odysseyBlueprintBar, this.body);
    }

    private void initEventHandling() {
        EventService.addListener(this, WishlistBlueprintEvent.class, wishlistEvent -> {
            if (Action.ADDED.equals(wishlistEvent.getAction())) {
                this.tabs.getSelectionModel().select(this.wishlistTab);
            }
        });
        EventService.addListener(this, BlueprintClickEvent.class, blueprintClickEvent -> {
            this.odysseyBlueprintBar.setVisible(true);
            PreferencesService.setPreference(PreferenceConstants.RECIPES_VISIBLE, true);
        });
        EventService.addListener(this, ApplicationLifeCycleEvent.class, applicationLifeCycleEvent -> setBodyAnchor(isRecipeBarVisible(), this.odysseyBlueprintBar.getWidth()));
        EventService.addListener(this, AfterFontSizeSetEvent.class, fontSizeEvent -> setBodyAnchor(isRecipeBarVisible(), this.odysseyBlueprintBar.getWidth()));
        EventService.addListener(this, MenuButtonClickedEvent.class, event -> {
            if (Expansion.ODYSSEY.equals(event.getExpansion())) {
                final boolean visibility = !this.odysseyBlueprintBar.isVisible();
                this.odysseyBlueprintBar.setVisible(visibility);
                PreferencesService.setPreference(PreferenceConstants.RECIPES_VISIBLE, visibility);
            }
        });

        EventService.addListener(this, ImportResultEvent.class, importResultEvent -> {
            if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_WISHLIST)) {
                this.tabs.getSelectionModel().select(this.wishlistTab);
            } else if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_LOADOUT)) {
                this.tabs.getSelectionModel().select(this.loadoutEditorTab);
            }
        });
    }

    private boolean isRecipeBarVisible() {
        return PreferencesService.getPreference(PreferenceConstants.RECIPES_VISIBLE, Boolean.TRUE);
    }

    private void setBodyAnchor(final boolean isRecipeBarVisible, final double width) {
        AnchorPaneHelper.setAnchor(this.body, 0.0, 0.0, isRecipeBarVisible ? width : 0.0, 0.0);
    }


}
