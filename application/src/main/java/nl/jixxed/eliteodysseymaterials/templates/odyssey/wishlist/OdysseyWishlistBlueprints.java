package nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist;

import javafx.scene.input.MouseEvent;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.PopOverBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintCategory;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import org.controlsfx.control.PopOver;

public class OdysseyWishlistBlueprints extends DestroyableVBox implements DestroyableTemplate {

    private DestroyableResizableImageView blueprintsHelp;

    public OdysseyWishlistBlueprints() {
        initComponents();
    }

    @Override
    public void initComponents() {

        this.getStyleClass().add("blueprints");

        DestroyableVBox blueprints = BoxBuilder.builder()
                .withStyleClass("wishlist-blueprints")
                .withNodes(
                        new OdysseyWishlistBlueprintsCategory(BlueprintCategory.ENGINEER_UNLOCKS),
                        new OdysseyWishlistBlueprintsCategory(BlueprintCategory.SUIT_GRADES),
                        new OdysseyWishlistBlueprintsCategory(BlueprintCategory.SUIT_MODULES),
                        new OdysseyWishlistBlueprintsCategory(BlueprintCategory.WEAPON_GRADES),
                        new OdysseyWishlistBlueprintsCategory(BlueprintCategory.WEAPON_MODULES)
                )
                .buildVBox();

        DestroyableLabel title = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("tab.wishlist.selected.blueprints")
                .build();

        this.blueprintsHelp = ResizableImageViewBuilder.builder()
                .withStyleClass("help-image")
                .withImage("/images/other/help.png")
                .withOnMouseClicked(this::showHelp)
                .build();
        final DestroyableHBox titleBar = BoxBuilder.builder()
                .withStyleClass("title-bar")
                .withNodes(title, this.blueprintsHelp)
                .buildHBox();
        this.getNodes().addAll(titleBar, blueprints);
    }

    private void showHelp(MouseEvent event) {
        DestroyableHBox selectedBlueprintsHintWhite = createHintLine("white");
        DestroyableHBox selectedBlueprintsHintYellow = createHintLine("yellow");
        DestroyableHBox selectedBlueprintsHintGreen = createHintLine("green");
        final DestroyableVBox contentNode = BoxBuilder.builder()
                .withStyleClass("help-popover")
                .withNodes(selectedBlueprintsHintWhite, selectedBlueprintsHintYellow, selectedBlueprintsHintGreen)
                .buildVBox();
        final DestroyablePopOver popOver = PopOverBuilder.builder()
                .withStyleClass("odyssey-wishlist-blueprints-help-popover")
                .withContent(contentNode)
                .withDetachable(false)
                .withHeaderAlwaysVisible(false)
                .withCornerRadius(0)
                .withArrowLocation(PopOver.ArrowLocation.TOP_LEFT)
                .build();
        popOver.show(this.blueprintsHelp, event.getScreenX(), event.getScreenY());
    }

    private static DestroyableHBox createHintLine(String color) {
        final DestroyableLabel hintColor = LabelBuilder.builder()
                .withStyleClasses("wishlist-hint-" + color)
                .withText("tab.wishlist.selected.blueprints.hint." + color)
                .build();
        final DestroyableLabel hintExplain = LabelBuilder.builder()
                .withStyleClass("wishlist-hint-explain")
                .withText("tab.wishlist.selected.blueprints.hint." + color + ".odyssey.explain")
                .build();
        return BoxBuilder.builder()
                .withStyleClass("hint-line")
                .withNodes(hintColor, hintExplain)
                .buildHBox();
    }
}
