package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;

public class ContentArea extends AnchorPane {
    private final SearchBar searchBar;
    private final RecipeBar recipeBar;
    final OverviewTab overview = new OverviewTab();
    final WishlistTab wishlistTab = new WishlistTab();
    final SettingsTab settingsTab = new SettingsTab();

    public ContentArea(final Application application) {
        super();


        this.recipeBar = new RecipeBar(application);

        setAnchor(this.recipeBar, 0.0, 0.0, 0.0, null);

        this.recipeBar.setVisible(PreferencesService.getPreference("recipes.visible", Boolean.TRUE));
        this.searchBar = new SearchBar();

        this.overview.setClosable(false);
        this.wishlistTab.setClosable(false);
        this.settingsTab.setClosable(false);
        final TabPane tabs = new TabPane(this.overview, this.wishlistTab, this.settingsTab);

        setAnchor(tabs, 0.0, 0.0, 0.0, null);
        tabs.setSide(Side.LEFT);
        final VBox body = new VBox(this.searchBar, tabs);
        HBox.setHgrow(body, Priority.ALWAYS);
        this.getChildren().addAll(this.recipeBar, body);
        VBox.setVgrow(tabs, Priority.ALWAYS);

        this.searchBar.getButton().setOnAction(event -> {
            final boolean visibility = !this.recipeBar.isVisible();
            this.recipeBar.setVisible(visibility);
            this.searchBar.getButton().setText(visibility ? "<" : ">");
            PreferencesService.setPreference("recipes.visible", visibility);
            setAnchor(body, 0.0, 0.0, this.recipeBar.isVisible() ? 373.0 : 0.0, 0.0);
        });

        setAnchor(body, 0.0, 0.0, this.recipeBar.isVisible() ? 373.0 : 0.0, 0.0);

    }

    private void setAnchor(final Node child, final Double topValue, final Double bottomValue, final Double leftValue, final Double rightValue) {
        AnchorPane.setTopAnchor(child, topValue);
        AnchorPane.setBottomAnchor(child, bottomValue);
        AnchorPane.setLeftAnchor(child, leftValue);
        AnchorPane.setRightAnchor(child, rightValue);
    }

}
