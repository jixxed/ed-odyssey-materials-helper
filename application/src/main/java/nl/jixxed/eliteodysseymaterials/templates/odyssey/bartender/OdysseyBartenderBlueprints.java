/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
