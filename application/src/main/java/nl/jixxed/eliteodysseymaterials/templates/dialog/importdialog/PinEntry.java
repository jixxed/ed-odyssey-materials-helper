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

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.css.PseudoClass;
import nl.edomh.ui.shared.builder.BoxBuilder;
import nl.edomh.ui.shared.builder.FontAwesomeIconViewBuilder;
import nl.edomh.ui.shared.builder.LabelBuilder;
import nl.edomh.core.constants.HorizonsBlueprintConstants;
import nl.edomh.core.domain.ApplicationState;
import nl.edomh.core.domain.HorizonsBlueprint;
import nl.edomh.core.domain.HorizonsBlueprintJson;
import nl.edomh.core.enums.Engineer;
import nl.edomh.core.enums.HorizonsBlueprintGrade;
import nl.edomh.core.enums.HorizonsBlueprintName;
import nl.edomh.core.enums.HorizonsBlueprintType;
import nl.edomh.core.service.PinnedBlueprintService;
import nl.edomh.ui.shared.templates.components.FontAwesomeIconViewPane;
import nl.edomh.ui.shared.templates.destroyables.DestroyableHBox;
import nl.edomh.ui.shared.templates.destroyables.DestroyableLabel;
import nl.edomh.ui.shared.templates.destroyables.DestroyableTemplate;
import nl.edomh.ui.shared.templates.destroyables.DestroyableVBox;

import java.util.Comparator;
import java.util.Objects;

public class PinEntry extends DestroyableVBox implements DestroyableTemplate {

    private final Engineer engineer;
    private final HorizonsBlueprintJson blueprintJson;

    public PinEntry(final Engineer engineer, final HorizonsBlueprintJson blueprintJson) {
        this.engineer = engineer;
        this.blueprintJson = blueprintJson;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("pin-item");
        // --------------------------------------
        // | Engineer name (colored if changed) |
        // | BP current -> BP new               |
        // --------------------------------------
        HorizonsBlueprint currentPinnedBlueprint = PinnedBlueprintService.getPinnedBlueprint(engineer);
        HorizonsBlueprint newPinnedBlueprint = getNewPinnedBlueprint();
        DestroyableLabel engineerName = LabelBuilder.builder().withStyleClass("engineer").withText(engineer.getLocalizationKey()).build();
        DestroyableLabel currentPinnedBlueprintName = LabelBuilder.builder().withStyleClass("current-name").withText(currentPinnedBlueprint != null ? currentPinnedBlueprint.getBlueprintName().getLocalizationKey() : "blueprint.name.none").build();
        DestroyableLabel currentPinnedBlueprintType = LabelBuilder.builder().withStyleClass("current-type").withText(currentPinnedBlueprint != null ? currentPinnedBlueprint.getHorizonsBlueprintType().getLocalizationKey() : "blank").build();
        DestroyableLabel newPinnedBlueprintName = LabelBuilder.builder().withStyleClass("new-name").withText(newPinnedBlueprint != null ? newPinnedBlueprint.getBlueprintName().getLocalizationKey() : "blueprint.name.none").build();
        DestroyableLabel newPinnedBlueprintType = LabelBuilder.builder().withStyleClass("new-type").withText(newPinnedBlueprint != null ? newPinnedBlueprint.getHorizonsBlueprintType().getLocalizationKey() : "blank").build();
        newPinnedBlueprintName.pseudoClassStateChanged(PseudoClass.getPseudoClass("changed"), !Objects.equals(currentPinnedBlueprintName.getText(), newPinnedBlueprintName.getText()));
        newPinnedBlueprintType.pseudoClassStateChanged(PseudoClass.getPseudoClass("changed"), !Objects.equals(currentPinnedBlueprintType.getText(), newPinnedBlueprintType.getText()));
        var arrow = new FontAwesomeIconViewPane(FontAwesomeIconViewBuilder.builder()
                        .withStyleClasses("arrow")
                        .withIcon(FontAwesomeIcon.CHEVRON_RIGHT)
                        .build());
        DestroyableHBox change = BoxBuilder.builder().withStyleClass("change").withNodes(BoxBuilder.builder().withNodes(currentPinnedBlueprintName, currentPinnedBlueprintType).buildVBox(), arrow, BoxBuilder.builder().withNodes(newPinnedBlueprintName, newPinnedBlueprintType).buildVBox()).buildHBox();
        this.getNodes().addAll(engineerName, change);
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("changed"), !Objects.equals(currentPinnedBlueprint, newPinnedBlueprint));
    }

    private HorizonsBlueprint getNewPinnedBlueprint() {
        if(blueprintJson != null) {
            final Integer engineerRank = ApplicationState.getInstance().getEngineerRank(engineer);
            final HorizonsBlueprintName name = HorizonsBlueprintName.forName(blueprintJson.getName().equals("MISSILE_RACK") ? "DUMBFIRE_MISSILE_RACK" : blueprintJson.getName());
            final HorizonsBlueprintType horizonsBlueprintType = HorizonsBlueprintType.forName(blueprintJson.getType());
            final int maxBlueprintGrade = HorizonsBlueprintConstants.getEngineerableBlueprintGrades(name, horizonsBlueprintType).stream().max(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).orElse(HorizonsBlueprintGrade.GRADE_1).getGrade();
            return  (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(
                    name,
                    horizonsBlueprintType,
                    HorizonsBlueprintGrade.forDigit(Math.max(Math.min(engineerRank, maxBlueprintGrade), 1))
            );
        }
        return null;
    }
}