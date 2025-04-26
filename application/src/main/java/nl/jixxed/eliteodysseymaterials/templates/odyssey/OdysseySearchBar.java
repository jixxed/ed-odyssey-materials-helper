package nl.jixxed.eliteodysseymaterials.templates.odyssey;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableButton;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.engineers.OdysseyEngineerSearchBar;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.materials.OdysseyMaterialSearchBar;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist.OdysseyWishlistSearchBar;

@Slf4j
class OdysseySearchBar extends DestroyableHBox implements DestroyableEventTemplate {

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";
    private DestroyableButton button;
    private OdysseyMaterialSearchBar materialSearchBar;
    private OdysseyWishlistSearchBar wishlistSearchBar;
    private OdysseyEngineerSearchBar engineerSearchBar;


    OdysseySearchBar() {
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("search");
        initMenuButton();
        this.materialSearchBar = new OdysseyMaterialSearchBar();
        this.wishlistSearchBar = register(new OdysseyWishlistSearchBar());
        this.engineerSearchBar = register(new OdysseyEngineerSearchBar());

        applyFontSizingHack();

        HBox.setHgrow(this.materialSearchBar, Priority.ALWAYS);
        HBox.setHgrow(this.wishlistSearchBar, Priority.ALWAYS);
        HBox.setHgrow(this.engineerSearchBar, Priority.ALWAYS);
        this.getNodes().addAll(this.button, this.materialSearchBar);
    }

    private void applyFontSizingHack() {
        //hack for component resizing on other fontsizes
        final Integer fontSize = FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize();
        final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSize);
        this.styleProperty().set(fontStyle);
        this.button.styleProperty().set(fontStyle);
    }


    private void initMenuButton() {
        this.button = ButtonBuilder.builder()
                .withNonLocalizedText(isRecipeBarVisible() ? "<" : ">")
                .withStyleClasses("root", "menubutton")
                .withOnMouseClicked(_ -> {
                    this.button.setText(isRecipeBarVisible() ? ">" : "<");
                    EventService.publish(new MenuButtonClickedEvent(Expansion.ODYSSEY));
                })
                .build();
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, BlueprintClickEvent.class, _ -> this.button.setText("<")));
        //hack for component resizing on other fontsizes
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSizeEvent.getFontSize());
            this.styleProperty().set(fontStyle);
            this.button.styleProperty().set(fontStyle);
        }));
        register(EventService.addListener(true, this, OdysseyTabSelectedEvent.class, event -> {
            switchTab(event.getSelectedTab());
        }));
    }

    private boolean isRecipeBarVisible() {
        return PreferencesService.getPreference(PreferenceConstants.RECIPES_VISIBLE, Boolean.TRUE);
    }

    private void switchTab(OdysseyTabs selectedTab) {
        this.getChildren().removeAll(this.materialSearchBar, this.wishlistSearchBar, this.engineerSearchBar);
        switch (selectedTab) {
            case WISHLIST -> this.getChildren().add(this.wishlistSearchBar);
            case ENGINEERS -> this.getChildren().add(this.engineerSearchBar);
            case null, default -> this.getChildren().add(this.materialSearchBar);
        }
    }
}
