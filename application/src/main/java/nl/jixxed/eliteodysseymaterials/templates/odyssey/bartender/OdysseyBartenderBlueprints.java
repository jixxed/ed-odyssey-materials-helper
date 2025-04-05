package nl.jixxed.eliteodysseymaterials.templates.odyssey.bartender;

import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyBartenderMaterialHoverEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class OdysseyBartenderBlueprints extends DestroyableVBox implements DestroyableEventTemplate {
    private final List<DestroyableLabel> recipes = new ArrayList<>();


    OdysseyBartenderBlueprints() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("bartender-blueprints");
        DestroyableLabel title = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("tab.bartender.used.in")
                .build();
        this.getNodes().add(title);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, OdysseyBartenderMaterialHoverEvent.class, event -> update(event.getAsset())));
    }

    private void update(final Asset asset) {
        this.getNodes().removeAll(this.recipes);
        this.recipes.forEach(DestroyableComponent::destroy);
        this.recipes.clear();
        final Map<OdysseyBlueprintName, Integer> recipesContaining = OdysseyBlueprintConstants.findRecipesContaining(asset);
        recipesContaining.entrySet().stream()
                .sorted(Comparator.comparing(entry -> LocaleService.getLocalizedStringForCurrentLocale(entry.getKey().getLocalizationKey())))
                .forEach(entry -> this.recipes.add(LabelBuilder.builder()
                        .withStyleClass("blueprint")
                        .withText("tab.bartender.entry", LocaleService.LocalizationKey.of(entry.getKey().getLocalizationKey()), entry.getValue())
                        .build()));
        this.getNodes().addAll(this.recipes);
    }
}
