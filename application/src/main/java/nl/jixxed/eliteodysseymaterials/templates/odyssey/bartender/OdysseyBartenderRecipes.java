package nl.jixxed.eliteodysseymaterials.templates.odyssey.bartender;

import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyBartenderMaterialHoverEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class OdysseyBartenderRecipes extends VBox implements Template {
    private DestroyableLabel title;
    private final List<DestroyableLabel> recipes = new ArrayList<>();

    OdysseyBartenderRecipes() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("bartender-recipes");
        this.title = LabelBuilder.builder().withStyleClass("bartender-recipes-header").withText(LocaleService.getStringBinding("tab.bartender.used.in")).build();
        this.getChildren().add(this.title);
    }

    @Override
    public void initEventHandling() {
        EventService.addListener(this, OdysseyBartenderMaterialHoverEvent.class, event -> update(event.getAsset()));
    }

    private void update(final Asset asset) {
        this.getChildren().removeAll(this.recipes);
        this.recipes.forEach(DestroyableComponent::destroy);
        this.recipes.clear();
        final Map<OdysseyBlueprintName, Integer> recipesContaining = OdysseyBlueprintConstants.findRecipesContaining(asset);
        recipesContaining.entrySet().stream()
                .sorted(Comparator.comparing(entry -> LocaleService.getLocalizedStringForCurrentLocale(entry.getKey().getLocalizationKey())))
                .forEach(entry -> this.recipes.add(LabelBuilder.builder().withStyleClass("bartender-recipes-line").withText(LocaleService.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale(entry.getKey().getLocalizationKey()) + " (" + entry.getValue() + ")")).build()));
        this.getChildren().addAll(this.recipes);
    }
}
