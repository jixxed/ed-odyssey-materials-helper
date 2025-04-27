package nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist;

import javafx.scene.input.MouseEvent;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.WishlistHideCompletedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import org.controlsfx.control.PopOver;

public class OdysseyWishlistMaterials extends DestroyableVBox implements DestroyableTemplate {

    private DestroyableResizableImageView materialsHelp;

    public OdysseyWishlistMaterials() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("materials");
        OdysseyWishlistMaterialsCategory allFlow = new OdysseyWishlistMaterialsCategory(WishlistMaterialsCategory.ALL);
        OdysseyWishlistMaterialsCategory goodsFlow = new OdysseyWishlistMaterialsCategory(WishlistMaterialsCategory.GOODS);
        OdysseyWishlistMaterialsCategory assetsFlow = new OdysseyWishlistMaterialsCategory(WishlistMaterialsCategory.ASSETS);
        OdysseyWishlistMaterialsCategory dataFlow = new OdysseyWishlistMaterialsCategory(WishlistMaterialsCategory.DATA);

        DestroyableCheckBox hideCompletedCheckBox = CheckBoxBuilder.builder()
                .withStyleClass("wishlist-checkbox")
                .withText("tab.wishlist.hide.completed")
                .withSelected(PreferencesService.getPreference("blueprint.odyssey.hide.completed", false))
                .withSelectedProperty((observable, oldValue, newValue) ->
                {
                    PreferencesService.setPreference("blueprint.odyssey.hide.completed", newValue);
                    EventService.publish(new WishlistHideCompletedEvent(Expansion.ODYSSEY, newValue));
                })
                .build();

        this.materialsHelp = ResizableImageViewBuilder.builder()
                .withOnMouseClicked(this::showHelp)
                .withStyleClass("help-image")
                .withImage("/images/other/help.png")
                .build();

        DestroyableLabel requiredMaterialsLabel = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("tab.wishlist.required.materials")
                .build();
        final DestroyableHBox titleBar = BoxBuilder.builder()
                .withStyleClass("title-bar")
                .withNodes(requiredMaterialsLabel, this.materialsHelp, hideCompletedCheckBox)
                .buildHBox();
        final DestroyableVBox flows = BoxBuilder.builder()
                .withStyleClass("flows")
                .withNodes(allFlow, goodsFlow, assetsFlow, dataFlow)
                .buildVBox();
        this.getNodes().addAll(titleBar, flows);
    }

    private void showHelp(MouseEvent event) {
        DestroyableHBox materialHintRed = createHintLine("red");
        DestroyableHBox materialHintYellow = createHintLine("yellow");
        DestroyableHBox materialHintGreen = createHintLine("green");
        DestroyableHBox materialHintRequired = BoxBuilder.builder()
                .withNodes(LabelBuilder.builder()
                        .withStyleClass("wishlist-hint-explain")
                        .withText("tab.wishlist.material.hint.required.explain")
                        .build())
                .buildHBox();
        final DestroyableVBox contentNodeMaterials = BoxBuilder.builder().withStyleClass("help-popover")
                .withNodes(materialHintRed, materialHintYellow, materialHintGreen, materialHintRequired).buildVBox();
        final DestroyablePopOver popOverMaterials = PopOverBuilder.builder()
                .withStyleClass("odyssey-wishlist-materials-help-popover")
                .withContent(contentNodeMaterials)
                .withDetachable(false)
                .withHeaderAlwaysVisible(false)
                .withCornerRadius(0)
                .withArrowLocation(PopOver.ArrowLocation.TOP_LEFT)
                .build();
        popOverMaterials.show(this.materialsHelp, event.getScreenX(), event.getScreenY());
    }


    private static DestroyableHBox createHintLine(String color) {
        final DestroyableLabel hintColor = LabelBuilder.builder()
                .withStyleClasses("wishlist-hint-" + color)
                .withText("tab.wishlist.material.hint." + color)
                .build();
        final DestroyableLabel hintExplain = LabelBuilder.builder()
                .withStyleClass("wishlist-hint-explain")
                .withText("tab.wishlist.material.hint." + color + ".explain")
                .build();
        return BoxBuilder.builder()
                .withStyleClass("hint-line")
                .withNodes(hintColor, hintExplain)
                .buildHBox();
    }
}