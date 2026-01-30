package nl.jixxed.eliteodysseymaterials.templates.dialog.importdialog;

import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.Loadout;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

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
                .withImage(this.loadout.getEquipment().getImage())
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
                .withImage((this.loadout.getModifications()[position] != null) ? this.loadout.getModifications()[position].getImage() : "/images/modification/empty.png")
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
}
