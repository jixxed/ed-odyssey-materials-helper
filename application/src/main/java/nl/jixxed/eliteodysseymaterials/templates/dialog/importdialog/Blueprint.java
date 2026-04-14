/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.dialog.importdialog;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.EdAwesomeIconViewPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.QuantitySelect;
import nl.jixxed.eliteodysseymaterials.templates.generic.QuantitySelectable;

import java.util.Comparator;
import java.util.stream.Collectors;

public class Blueprint extends DestroyableVBox implements DestroyableTemplate {

    private final WishlistBlueprint<?> wishlistBlueprint;

    private DestroyableLabel title;
    private DestroyableLabel blueprintName;
    private DestroyableLabel experimentalEffectName;

    private QuantitySelectable quantityLine;

    public Blueprint(final WishlistBlueprint<?> wishlistBlueprint) {
        this.wishlistBlueprint = wishlistBlueprint;
        initComponents();
    }

    public void initComponents() {
        this.getStyleClass().add("blueprint");


        this.title = LabelBuilder.builder()
                .withStyleClass("module")
                .withText(getBlueprintTitle())
                .build();

        this.blueprintName = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(getBlueprintName())
                .withGraphic(this.wishlistBlueprint instanceof HorizonsEngineerWishlistBlueprint bp && bp.getRecipeName().isInColonia() ? EdAwesomeIconViewPaneBuilder.builder().withStyleClass("colonia-icon").withIcons(EdAwesomeIcon.OTHER_COLONIA).build() : null)
                .build();

        experimentalEffectName = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(getBlueprintEffect())
                .build();


        experimentalEffectName.addBinding(experimentalEffectName.visibleProperty(), this.experimentalEffectName.textProperty().isNotEmpty());
        experimentalEffectName.addBinding(experimentalEffectName.managedProperty(), this.experimentalEffectName.textProperty().isNotEmpty());

        final DestroyableLabel quantityLabel = LabelBuilder.builder().withText("wishlist.blueprint.horizons.quantity", this.wishlistBlueprint.getQuantity())
                .withStyleClass("quantity-text")
                .withManaged(wishlistBlueprint.getQuantity() > 10)
                .withVisibility(wishlistBlueprint.getQuantity() > 10)
                .build();

        final DestroyableHBox titleLine = BoxBuilder.builder().withStyleClass("title-line").withNodes(title, new GrowingRegion(), quantityLabel).buildHBox();
        final DestroyableHBox nameLine = BoxBuilder.builder().withStyleClass("blueprint-line").withNodes(this.blueprintName).buildHBox();
        final DestroyableHBox effectLine = BoxBuilder.builder().withStyleClass("blueprint-line").withNodes(this.experimentalEffectName).buildHBox();
        quantityLine = new QuantitySelect(wishlistBlueprint.getQuantity(), new SimpleBooleanProperty(true), wishlistBlueprint);
        this.getNodes().addAll(titleLine, nameLine, effectLine, new GrowingRegion(), (Node & Destroyable) quantityLine);
    }


    private StringBinding getBlueprintTitle() {
        return switch (wishlistBlueprint) {
            case HorizonsEngineerWishlistBlueprint bp ->
                    LocaleService.getStringBinding("wishlist.blueprint.horizons.title.engineerunlock");
            case OdysseyWishlistBlueprint bp ->
                    LocaleService.getStringBinding(bp.getRecipeName().getTitleLocalizationKey());
            default -> LocaleService.getStringBinding(wishlistBlueprint.getRecipeName().getLocalizationKey());
        };
    }

    private StringBinding getBlueprintName() {
        return switch (wishlistBlueprint) {
            case HorizonsExperimentalWishlistBlueprint bp ->
                    LocaleService.getStringBinding("wishlist.blueprint.horizons.title.experimental", LocaleService.LocalizationKey.of(bp.getBlueprintType().getLocalizationKey()));
            case HorizonsModuleWishlistBlueprint bp -> {
                final String gradeList = bp.getPercentageToComplete().keySet().stream().sorted(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).map(HorizonsBlueprintGrade::getGrade).map(String::valueOf).collect(Collectors.joining(","));
                yield LocaleService.getStringBinding("wishlist.blueprint.horizons.title.module", LocaleService.LocalizationKey.of(bp.getBlueprintType().getLocalizationKey()), gradeList.isEmpty() ? "?" : gradeList);
            }
            case HorizonsSynthesisWishlistBlueprint bp ->
                    LocaleService.getStringBinding("wishlist.blueprint.horizons.title.synthesis", LocaleService.LocalizationKey.of("blueprint.synthesis.grade" + bp.getBlueprintGrade().getGrade()));
            case HorizonsTechBrokerWishlistBlueprint bp ->
                    LocaleService.getStringBinding("wishlist.blueprint.horizons.title.techbroker", LocaleService.LocalizationKey.of(bp.getBlueprintType().getLocalizationKey()));
            case HorizonsEngineerWishlistBlueprint bp ->
                    Bindings.createStringBinding(() -> LocaleService.getStringBinding("wishlist.blueprint.horizons.title.engineer", LocaleService.LocalizationKey.of(bp.getRecipeName().getLocalizationKey())).get().replaceAll("^[ ├└\\s]+", ""));
            case OdysseyWishlistBlueprint bp ->
                    LocaleService.getStringBinding(bp.getRecipeName().getShortLocalizationKey());
        };
    }

    private StringBinding getBlueprintEffect() {
        return switch (wishlistBlueprint) {
            case HorizonsModuleWishlistBlueprint bp ->
                    LocaleService.getStringBinding(bp.getExperimentalEffect() != null ? bp.getExperimentalEffect().getLocalizationKey() : "blank");
            default -> LocaleService.getStringBinding("blank");
        };
    }
}