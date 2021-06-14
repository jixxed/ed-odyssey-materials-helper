package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;

public class ContentArea extends AnchorPane {
    private final MaterialOverview materialOverview = new MaterialOverview();
    private final SearchBar searchBar;
    private final RecipeBar recipeBar;

    public ContentArea(final Application application) {
        super();


        this.materialOverview.getStyleClass().add("card-grid");
        this.materialOverview.setSpacing(10);

        this.recipeBar = new RecipeBar(application);
        final Image img = new Image(getClass().getResourceAsStream("/images/menu.png"));
        final ImageView view = new ImageView(img);
        view.setFitHeight(40);
        view.setFitWidth(40);
        view.setPreserveRatio(true);
        //Creating a Button
        final Button button = new Button();
        //Setting the size of the button
        button.setPrefSize(40, 40);
        button.setMaxSize(40, 40);
        //Setting a graphic to the button
        button.setGraphic(view);
        button.getStyleClass().add("menubutton");

        final VBox menuBar = new VBox(button);
        menuBar.getStyleClass().add("menubar");
        menuBar.setMaxWidth(40);
        setAnchor(menuBar, 0.0, 0.0, 0.0, null);
        setAnchor(this.recipeBar, 0.0, 0.0, 42.0, null);

        this.recipeBar.setVisible(PreferencesService.getPreference("recipes.visible", Boolean.TRUE));
        this.searchBar = new SearchBar(this.materialOverview::updateContent);
        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.pannableProperty().set(true);
        scrollPane.setContent(this.materialOverview);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        final VBox body = new VBox(this.searchBar, scrollPane);
        HBox.setHgrow(body, Priority.ALWAYS);
        this.getChildren().addAll(menuBar, this.recipeBar, body);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);//Added this line
        button.setOnAction(event -> {
            final boolean visibility = !this.recipeBar.isVisible();
            this.recipeBar.setVisible(visibility);
            PreferencesService.setPreference("recipes.visible", visibility);
            setAnchor(body, 0.0, 0.0, this.recipeBar.isVisible() ? 414.0 : 42, 0.0);
        });

        setAnchor(body, 0.0, 0.0, this.recipeBar.isVisible() ? 414.0 : 42, 0.0);

    }

    private void setAnchor(final Node child, final Double topValue, final Double bottomValue, final Double leftValue, final Double rightValue) {
        AnchorPane.setTopAnchor(child, topValue);
        AnchorPane.setBottomAnchor(child, bottomValue);
        AnchorPane.setLeftAnchor(child, leftValue);
        AnchorPane.setRightAnchor(child, rightValue);
    }


    public void updateGui() {
        this.recipeBar.updateIngredientsValues();
        this.materialOverview.updateContent(this.searchBar.getSearch());
        this.recipeBar.updateIngredients();
        this.recipeBar.updateEngineerStyles();
    }
}
