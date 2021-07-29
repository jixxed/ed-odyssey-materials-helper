package nl.jixxed.eliteodysseymaterials.templates;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.constants.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.BlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EngineerEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationEvent;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class EngineerCard extends VBox {
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

    EngineerCard(final Engineer engineer) {
        this.getChildren().add(getEngineerImageView(engineer));
        this.getChildren().add(getEngineerName(engineer));
        this.getChildren().add(getEngineerSpecialisation(engineer));
        this.getChildren().add(getEngineerLocation(engineer));
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.getChildren().add(getSuitModulesTitle());
        this.getChildren().addAll(getSuitBlueprints(engineer));
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.getChildren().add(getWeaponModulesTitle());
        this.getChildren().addAll(getWeaponBlueprints(engineer));
    }

    private List<Label> getWeaponBlueprints(final Engineer engineer) {
        final List<RecipeName> weaponRecipes = RecipeConstants.getWeaponModuleBlueprints().entrySet().stream()
                .filter(recipeNameModuleRecipeEntry -> recipeNameModuleRecipeEntry.getValue().getEngineers().contains(engineer))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        this.getStyleClass().add("engineer-card");

        return weaponRecipes.stream().sorted(Comparator.comparing(RecipeName::name)).map(recipeName -> {
            final Label label = new Label();
            label.getStyleClass().add("engineer-blueprint");
            label.textProperty().bind(LocaleService.getStringBinding(recipeName.getLocalizationKey()));
            label.setOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(recipeName)));
            return label;
        }).collect(Collectors.toList());
    }

    private List<Label> getSuitBlueprints(final Engineer engineer) {

        final List<RecipeName> suitRecipes = RecipeConstants.getSuitModuleBlueprints().entrySet().stream()
                .filter(recipeNameModuleRecipeEntry -> recipeNameModuleRecipeEntry.getValue().getEngineers().contains(engineer))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return suitRecipes.stream().sorted(Comparator.comparing(RecipeName::name)).map(recipeName -> {
            final Label label = new Label();
            label.getStyleClass().add("engineer-blueprint");
            label.textProperty().bind(LocaleService.getStringBinding(recipeName.getLocalizationKey()));
            label.setOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(recipeName)));
            return label;
        }).collect(Collectors.toList());
    }

    private Label getWeaponModulesTitle() {
        final Label weaponModules = new Label();
        weaponModules.getStyleClass().add("engineer-category");
        weaponModules.textProperty().bind(LocaleService.getStringBinding("tab.engineer.weapon.modules"));
        return weaponModules;
    }

    private Label getSuitModulesTitle() {
        final Label suitModules = new Label();
        suitModules.getStyleClass().add("engineer-category");
        suitModules.textProperty().bind(LocaleService.getStringBinding("tab.engineer.suit.modules"));
        return suitModules;
    }

    private FlowPane getEngineerLocation(final Engineer engineer) {
        final Label engineerLocation = new Label(engineer.getSettlement() + " | " + engineer.getSystem());
        engineerLocation.getStyleClass().add("engineer-location");
        final Label engineerDistance = new Label("(0Ly)");
        engineerDistance.getStyleClass().add("engineer-distance");

        final ImageView copyIcon = new ImageView(new Image(EngineerCard.class.getResourceAsStream("/images/other/copy.png")));
        copyIcon.getStyleClass().add("engineer-copy-icon");
        copyIcon.setFitHeight(16);
        copyIcon.setFitWidth(16);
        final FlowPane location = new FlowPane(engineerLocation, new StackPane(copyIcon), engineerDistance);
        location.setMaxWidth(285);
        location.setOnMouseClicked(event -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(engineer.getSystem());
            clipboard.setContent(content);
        });
        EventService.addListener(LocationEvent.class, locationEvent -> engineerDistance.setText("(" + NUMBER_FORMAT.format(engineer.getDistance(locationEvent.getX(), locationEvent.getY(), locationEvent.getZ())) + "Ly)"));
        location.getStyleClass().add("engineer-location-line");
        return location;
    }

    private HBox getEngineerSpecialisation(final Engineer engineer) {
        final Label engineerSpecialisation = new Label();
        engineerSpecialisation.textProperty().bind(LocaleService.getStringBinding(engineer.getSpecialisation().getLocalizationKey()));

        switch (engineer.getSpecialisation()) {
            case FORCE -> engineerSpecialisation.getStyleClass().add("specialisation-force");
            case DYNAMIC -> engineerSpecialisation.getStyleClass().add("specialisation-dynamic");
            case STRATEGIC -> engineerSpecialisation.getStyleClass().add("specialisation-strategic");
            case UNKNOWN -> {
            }
        }
        final ImageView specialisationIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/engineer/specialisation/" + engineer.getSpecialisation().name().toLowerCase() + ".png")));
        specialisationIcon.setFitHeight(32);
        specialisationIcon.setFitWidth(32);

        final HBox specialisationBox = new HBox(new StackPane(specialisationIcon), engineerSpecialisation);
        specialisationBox.setPadding(new Insets(0, 0, 0, 5));
        return specialisationBox;
    }

    private Label getEngineerName(final Engineer engineer) {
        final Label engineerName = new Label();
        engineerName.textProperty().bind(LocaleService.getStringBinding(engineer.getLocalizationKey()));
        engineerName.getStyleClass().add("engineer-name");
        engineerName.setOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(RecipeName.forEngineer(engineer))));
        return engineerName;
    }

    private ImageView getEngineerImageView(final Engineer engineer) {
        final ImageView image = new ImageView();
        image.setImage(new Image(getClass().getResourceAsStream("/images/engineer/locked.png")));
        image.getStyleClass().add("engineer-image");
        EventService.addListener(EngineerEvent.class, engineerEvent -> {
            if (APPLICATION_STATE.isEngineerUnlocked(engineer)) {
                image.setImage(new Image(getClass().getResourceAsStream("/images/engineer/" + engineer.name().toLowerCase() + ".jpg")));
            } else {
                image.setImage(new Image(getClass().getResourceAsStream("/images/engineer/locked.png")));
            }
        });
        return image;
    }
}
