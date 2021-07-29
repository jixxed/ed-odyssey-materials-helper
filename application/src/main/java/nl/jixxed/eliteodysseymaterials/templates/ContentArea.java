package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.Action;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

@SuppressWarnings("java:S110")
class ContentArea extends AnchorPane {

    private final SearchBar searchBar;
    private final RecipeBar recipeBar;
    private final OverviewTab overview = new OverviewTab();
    private final WishlistTab wishlistTab = new WishlistTab();
    private final EngineersTab engineersTab = new EngineersTab();
    private final SettingsTab settingsTab;

    ContentArea(final Application application) {
        super();
        this.settingsTab = new SettingsTab(application);

        this.recipeBar = new RecipeBar(application);

        setAnchor(this.recipeBar, 0.0, 0.0, 0.0, null);

        this.searchBar = new SearchBar();

        this.overview.setClosable(false);
        this.wishlistTab.setClosable(false);
        this.engineersTab.setClosable(false);
        this.settingsTab.setClosable(false);
        final TabPane tabs = new TabPane(this.overview, this.wishlistTab, this.engineersTab, this.settingsTab); //, this.settingsTab
        tabs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                final Tabs tabType = ((EDOTab) newValue).getTabType();
                EventService.publish(new TabSelecetedEvent(tabType));
            }
        });
        EventService.addListener(WishlistRecipeEvent.class, wishlistEvent -> {
            if (Action.ADDED.equals(wishlistEvent.getAction())) {
                tabs.getSelectionModel().select(this.wishlistTab);
            }
        });
        setAnchor(tabs, 0.0, 0.0, 0.0, null);
        tabs.setSide(Side.LEFT);
        final VBox body = new VBox(this.searchBar, tabs);
        HBox.setHgrow(body, Priority.ALWAYS);
        this.getChildren().addAll(this.recipeBar, body);
        VBox.setVgrow(tabs, Priority.ALWAYS);
        EventService.addListener(BlueprintClickEvent.class, blueprintClickEvent -> {
            this.recipeBar.setVisible(true);
            this.searchBar.getButton().setText("<");
            PreferencesService.setPreference(PreferenceConstants.RECIPES_VISIBLE, true);
        });
        this.searchBar.getButton().setOnAction(event -> {
            final boolean visibility = !this.recipeBar.isVisible();
            this.recipeBar.setVisible(visibility);
            this.searchBar.getButton().setText(visibility ? "<" : ">");
            PreferencesService.setPreference(PreferenceConstants.RECIPES_VISIBLE, visibility);
        });
        EventService.addListener(ApplicationLifeCycleEvent.class, applicationLifeCycleEvent -> setAnchor(body, 0.0, 0.0, PreferencesService.getPreference(PreferenceConstants.RECIPES_VISIBLE, Boolean.TRUE) ? this.recipeBar.getWidth() : 0.0, 0.0));
        EventService.addListener(AfterFontSizeSetEvent.class, fontSizeEvent -> setAnchor(body, 0.0, 0.0, PreferencesService.getPreference(PreferenceConstants.RECIPES_VISIBLE, Boolean.TRUE) ? this.recipeBar.getWidth() : 0.0, 0.0));

        this.recipeBar.visibleProperty().addListener((observable, oldValue, newValue) ->
                setAnchor(body, 0.0, 0.0, newValue ? this.recipeBar.getWidth() : 0.0, 0.0));
        this.recipeBar.widthProperty().addListener((observable, oldValue, newValue) ->
                setAnchor(body, 0.0, 0.0, PreferencesService.getPreference(PreferenceConstants.RECIPES_VISIBLE, Boolean.TRUE) ? newValue.doubleValue() : 0.0, 0.0));

        final boolean isRecipeBarVisible = PreferencesService.getPreference(PreferenceConstants.RECIPES_VISIBLE, Boolean.TRUE);
        this.recipeBar.visibleProperty().set(isRecipeBarVisible);
        setAnchor(body, 0.0, 0.0, isRecipeBarVisible ? this.recipeBar.getWidth() : 0.0, 0.0);
    }

    private void setAnchor(final Node child, final Double topValue, final Double bottomValue, final Double leftValue, final Double rightValue) {
        AnchorPane.setTopAnchor(child, topValue);
        AnchorPane.setBottomAnchor(child, bottomValue);
        AnchorPane.setLeftAnchor(child, leftValue);
        AnchorPane.setRightAnchor(child, rightValue);
    }

}
