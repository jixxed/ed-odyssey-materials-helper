package nl.jixxed.eliteodysseymaterials.templates;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.BlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EngineerCard extends VBox {
    private final ImageView image = new ImageView();
    private final Engineer engineer;
    private final List<RecipeName> suitRecipes;
    private final List<RecipeName> weaponRecipes;

    public EngineerCard(final Engineer engineer) {
        this.engineer = engineer;
        this.image.setImage(new Image(getClass().getResourceAsStream("/images/engineer/" + engineer.name().toLowerCase() + ".jpg")));
        this.suitRecipes = RecipeConstants.getSuitModuleBlueprints().entrySet().stream()
                .filter(recipeNameModuleRecipeEntry -> recipeNameModuleRecipeEntry.getValue().getEngineers().contains(engineer))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        this.weaponRecipes = RecipeConstants.getWeaponModuleBlueprints().entrySet().stream()
                .filter(recipeNameModuleRecipeEntry -> recipeNameModuleRecipeEntry.getValue().getEngineers().contains(engineer))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        this.getStyleClass().add("engineer-card");
        this.image.getStyleClass().add("engineer-image");
        final Label engineerName = new Label(engineer.friendlyName());
        engineerName.getStyleClass().add("engineer-name");
        final Label engineerLocation = new Label(engineer.getSettlement() + " | " + engineer.getSystem());
        engineerLocation.getStyleClass().add("engineer-location");
        final Label suitModules = new Label();
        final Label weaponModules = new Label();
        suitModules.getStyleClass().add("engineer-category");
        weaponModules.getStyleClass().add("engineer-category");
        suitModules.textProperty().bind(LocaleService.getStringBinding("tab.engineer.suit.modules"));
        weaponModules.textProperty().bind(LocaleService.getStringBinding("tab.engineer.weapon.modules"));
        final List<Label> suitBlueprints = this.suitRecipes.stream().sorted(Comparator.comparing(RecipeName::name)).map(recipeName -> {
            final Label label = new Label();
            label.getStyleClass().add("engineer-blueprint");
            label.textProperty().bind(LocaleService.getStringBinding(recipeName.getLocalizationKey()));
            label.setOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(recipeName)));
            return label;
        }).collect(Collectors.toList());
        final List<Label> weaponBlueprints = this.weaponRecipes.stream().sorted(Comparator.comparing(RecipeName::name)).map(recipeName -> {
            final Label label = new Label();
            label.getStyleClass().add("engineer-blueprint");
            label.textProperty().bind(LocaleService.getStringBinding(recipeName.getLocalizationKey()));
            label.setOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(recipeName)));
            return label;
        }).collect(Collectors.toList());

//

        this.getChildren().add(this.image);
        this.getChildren().add(engineerName);
        this.getChildren().add(engineerLocation);
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.getChildren().add(suitModules);
        this.getChildren().addAll(suitBlueprints);
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.getChildren().add(weaponModules);
        this.getChildren().addAll(weaponBlueprints);
    }
}
