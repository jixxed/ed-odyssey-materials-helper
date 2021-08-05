package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.Action;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;
import nl.jixxed.eliteodysseymaterials.helper.AnchorPaneHelper;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

@SuppressWarnings("java:S110")
class ContentArea extends AnchorPane {

    private SearchBar searchBar;
    private RecipeBar recipeBar;
    private OverviewTab overview;
    private WishlistTab wishlistTab;
    private EngineersTab engineersTab;
    private SettingsTab settingsTab;
    private TabPane tabs;
    private VBox body;

    ContentArea(final Application application) {
        initComponents(application);
        initEventHandling();
    }

    private void initComponents(final Application application) {
        this.overview = new OverviewTab();
        this.wishlistTab = new WishlistTab();
        this.engineersTab = new EngineersTab();
        this.settingsTab = new SettingsTab(application);
        this.overview.setClosable(false);
        this.wishlistTab.setClosable(false);
        this.engineersTab.setClosable(false);
        this.settingsTab.setClosable(false);

        this.searchBar = new SearchBar();
        this.tabs = new TabPane(this.overview, this.wishlistTab, this.engineersTab, this.settingsTab);
        this.tabs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                final Tabs tabType = ((EDOTab) newValue).getTabType();
                EventService.publish(new TabSelectedEvent(tabType));
            }
        });
        this.tabs.setSide(Side.LEFT);
        VBox.setVgrow(this.tabs, Priority.ALWAYS);

        this.body = new VBox(this.searchBar, this.tabs);
        HBox.setHgrow(this.body, Priority.ALWAYS);

        this.recipeBar = new RecipeBar(application);
        this.recipeBar.visibleProperty().addListener((observable, oldValue, newValue) -> setBodyAnchor(newValue, this.recipeBar.getWidth()));
        this.recipeBar.widthProperty().addListener((observable, oldValue, newValue) -> setBodyAnchor(isRecipeBarVisible(), newValue.doubleValue()));
        this.recipeBar.visibleProperty().set(isRecipeBarVisible());


        AnchorPaneHelper.setAnchor(this.recipeBar, 0.0, 0.0, 0.0, null);
        setBodyAnchor(isRecipeBarVisible(), this.recipeBar.getWidth());
        AnchorPaneHelper.setAnchor(this.tabs, 0.0, 0.0, 0.0, null);

        this.getChildren().addAll(this.recipeBar, this.body);
    }

    private void initEventHandling() {
        EventService.addListener(WishlistRecipeEvent.class, wishlistEvent -> {
            if (Action.ADDED.equals(wishlistEvent.getAction())) {
                this.tabs.getSelectionModel().select(this.wishlistTab);
            }
        });
        EventService.addListener(BlueprintClickEvent.class, blueprintClickEvent -> {
            this.recipeBar.setVisible(true);
            PreferencesService.setPreference(PreferenceConstants.RECIPES_VISIBLE, true);
        });
        EventService.addListener(ApplicationLifeCycleEvent.class, applicationLifeCycleEvent -> setBodyAnchor(isRecipeBarVisible(), this.recipeBar.getWidth()));
        EventService.addListener(AfterFontSizeSetEvent.class, fontSizeEvent -> setBodyAnchor(isRecipeBarVisible(), this.recipeBar.getWidth()));
        EventService.addListener(MenuButtonClickedEvent.class, event -> {
            final boolean visibility = !this.recipeBar.isVisible();
            this.recipeBar.setVisible(visibility);
            PreferencesService.setPreference(PreferenceConstants.RECIPES_VISIBLE, visibility);
        });

    }

    private boolean isRecipeBarVisible() {
        return PreferencesService.getPreference(PreferenceConstants.RECIPES_VISIBLE, Boolean.TRUE);
    }

    private void setBodyAnchor(final boolean isRecipeBarVisible, final double width) {
        AnchorPaneHelper.setAnchor(this.body, 0.0, 0.0, isRecipeBarVisible ? width : 0.0, 0.0);
    }


}
