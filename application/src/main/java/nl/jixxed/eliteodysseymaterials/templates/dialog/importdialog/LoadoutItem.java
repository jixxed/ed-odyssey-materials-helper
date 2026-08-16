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

import nl.edomh.core.domain.SelectedModification;
import nl.edomh.core.enums.*;
import nl.edomh.ui.shared.builder.BoxBuilder;
import nl.edomh.ui.shared.builder.LabelBuilder;
import nl.edomh.ui.shared.builder.ResizableImageViewBuilder;
import nl.edomh.core.domain.Loadout;
import nl.edomh.ui.shared.templates.components.GrowingRegion;
import nl.edomh.ui.shared.templates.destroyables.*;

public class LoadoutItem extends DestroyableHBox implements DestroyableTemplate {

    private final Loadout loadout;

    public LoadoutItem(final Loadout loadout) {
        this.loadout = loadout;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("loadout-item");

        final DestroyableResizableImageView image = ResizableImageViewBuilder.builder()
                .withStyleClass("loadout-image")
                .withImage(getImage(this.loadout.getEquipment()))
                .build();

        var imageBox = BoxBuilder.builder()
                .withStyleClass("image-pane")
                .withNodes(new GrowingRegion(), image, new GrowingRegion())
                .buildVBox();
        //title
        final DestroyableLabel title = LabelBuilder.builder()
                .withStyleClass("loadout-name")
                .withText(this.loadout.getEquipment().getLocalizationKey())
                .build();

        this.getNodes().add(imageBox);

        final DestroyableLabel modificationsTitle = LabelBuilder
                .builder()
                .withStyleClass("title")
                .withText("loadout.equipment.modifications")
                .build();
        //modifications
        final DestroyableVBox loadoutModification1 = createModSlot(0);
        final DestroyableVBox loadoutModification2 = createModSlot(1);
        final DestroyableVBox loadoutModification3 = createModSlot(2);
        final DestroyableVBox loadoutModification4 = createModSlot(3);

        final DestroyableHBox modifications = BoxBuilder.builder()
                .withStyleClass("modifications")
                .withNodes(loadoutModification1, new GrowingRegion(), loadoutModification2, new GrowingRegion(), loadoutModification3, new GrowingRegion(), loadoutModification4)
                .buildHBox();

        DestroyableVBox content = BoxBuilder.builder().withStyleClass("content").withNodes(title, createCurrentGrade(), createTargetGrade(), new GrowingRegion(), modificationsTitle, modifications).buildVBox();
        this.getNodes().add(content);

    }

    private DestroyableVBox createModSlot(int position) {
        var imageView = ResizableImageViewBuilder.builder()
                .withStyleClasses("mod-image")
                .withImage((this.loadout.getModifications()[position] != null) ? getImage(this.loadout.getModifications()[position]) : "nl/edomh/ui/shared/images/modification/empty.png")
                .build();
        final boolean hasModification = this.loadout.getModifications()[position] != null && this.loadout.getModifications()[position].getModification() != null;
        var label = LabelBuilder.builder()
                .withStyleClass("mod-name")
                .withText(hasModification ? this.loadout.getModifications()[position].getModification().getLocalizationKey() : "loadout.modification.name.none")
                .build();

        return BoxBuilder.builder().withStyleClass("mod-slot").withNodes(imageView, label).buildVBox();
    }

    private DestroyableHBox createCurrentGrade() {
        var currentLevelLabel = LabelBuilder.builder()
                .withStyleClass("grade-name")
                .withText("loadout.equipment.level.current")
                .build();
        var currentLevel = LabelBuilder.builder()
                .withStyleClass("grade-value")
                .withNonLocalizedText(loadout.getCurrentLevel().toString())
                .build();

        return BoxBuilder.builder()
                .withStyleClass("grade-line")
                .withNodes(currentLevelLabel, new GrowingRegion(), currentLevel)
                .buildHBox();
    }

    private DestroyableHBox createTargetGrade() {
        var targetLevelLabel = LabelBuilder.builder()
                .withStyleClass("grade-name")
                .withText("loadout.equipment.level.target")
                .build();
        var targetLevel = LabelBuilder.builder()
                .withStyleClass("grade-value")
                .withNonLocalizedText(loadout.getTargetLevel().toString())
                .build();

        return BoxBuilder.builder()
                .withStyleClass("grade-line")
                .withNodes(targetLevelLabel, new GrowingRegion(), targetLevel)
                .buildHBox();
    }

    private String getImage(Equipment equipment){
        return switch (equipment){
            case Suit s -> "nl/edomh/ui/shared/images/suit/" + equipment.name().toLowerCase() + ".png";
            case Weapon w -> "nl/edomh/ui/shared/images/weapon/" + equipment.name().toLowerCase() + ".png";
            default -> throw new IllegalStateException("Unexpected value: " + equipment);
        };
    }

    private String getImage(Modification modification, boolean present) {
        return switch (modification) {
            case SuitModification _ -> "nl/edomh/ui/shared/images/modification/" + modification.name().toLowerCase() + (present ? "_active" : "") + ".png";
            case WeaponModification _ -> {
                String name = modification.name();
                name = name.endsWith("_KINETIC") ? name.substring(0, name.indexOf("_KINETIC")) : name;
                name = name.endsWith("_LASER") ? name.substring(0, name.indexOf("_LASER")) : name;
                name = name.endsWith("_PLASMA") ? name.substring(0, name.indexOf("_PLASMA")) : name;
                yield "nl/edomh/ui/shared/images/modification/" + name.toLowerCase() + (present ? "_active" : "") + ".png";
            }
            default -> throw new IllegalStateException("Unexpected value: " + modification);
        };
    }
    private String getImage(SelectedModification selectedModification) {
        if (selectedModification.getModification() == null) {
            return "nl/edomh/ui/shared/images/modification/empty.png";
        }
        return getImage(selectedModification.getModification(), selectedModification.isPresent());
    }
}
