package nl.jixxed.eliteodysseymaterials.templates.horizons;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableButton;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.horizons.commodities.HorizonsCommoditiesSearchBar;
import nl.jixxed.eliteodysseymaterials.templates.horizons.engineers.HorizonsEngineerSearchBar;
import nl.jixxed.eliteodysseymaterials.templates.horizons.materials.HorizonsMaterialSearchBar;
import nl.jixxed.eliteodysseymaterials.templates.horizons.powerplay.PowerplaySearchBar;
import nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist.HorizonsWishlistSearchBar;

@Slf4j
class HorizonsSearchBar extends DestroyableHBox implements DestroyableEventTemplate {

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";
    private DestroyableButton button;
    private HorizonsMaterialSearchBar materialSearchBar;
    private HorizonsCommoditiesSearchBar commoditiesSearchBar;
    private HorizonsWishlistSearchBar horizonsWishlistSearchBar;
    private HorizonsEngineerSearchBar horizonsEngineerSearchBar;
    private PowerplaySearchBar powerplaySearchBar;


    HorizonsSearchBar() {
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("root");
        initMenuButton();
        this.materialSearchBar = new HorizonsMaterialSearchBar();
        //register bars that are not yet added
        this.commoditiesSearchBar = register(new HorizonsCommoditiesSearchBar());
        this.horizonsWishlistSearchBar = register(new HorizonsWishlistSearchBar());
        this.horizonsEngineerSearchBar = register(new HorizonsEngineerSearchBar());
        this.powerplaySearchBar = register(new PowerplaySearchBar());

        applyFontSizingHack();

        HBox.setHgrow(this.materialSearchBar, Priority.ALWAYS);
        HBox.setHgrow(this.commoditiesSearchBar, Priority.ALWAYS);
        HBox.setHgrow(this.horizonsWishlistSearchBar, Priority.ALWAYS);
        HBox.setHgrow(this.horizonsEngineerSearchBar, Priority.ALWAYS);
        HBox.setHgrow(this.powerplaySearchBar, Priority.ALWAYS);
        this.getNodes().addAll(this.button, this.materialSearchBar);
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, BlueprintClickEvent.class, _ -> this.button.setText("<")));
        //hack for component resizing on other fontsizes
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSizeEvent.getFontSize());
            this.styleProperty().set(fontStyle);
            this.button.styleProperty().set(fontStyle);
        }));
        register(EventService.addListener(true, this, HorizonsTabSelectedEvent.class, event -> switchTab(event.getSelectedTab())));
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
                    EventService.publish(new MenuButtonClickedEvent(Expansion.HORIZONS));
                })
                .build();
    }

    private void switchTab(HorizonsTabs selectedTab) {
        this.getChildren().removeAll(this.materialSearchBar, this.commoditiesSearchBar, this.horizonsWishlistSearchBar, this.horizonsEngineerSearchBar, this.powerplaySearchBar);
        if (HorizonsTabs.COMMODITIES.equals(selectedTab)) {
            this.getChildren().add(this.commoditiesSearchBar);
        } else if (HorizonsTabs.WISHLIST.equals(selectedTab)) {
            this.getChildren().add(this.horizonsWishlistSearchBar);
        } else if (HorizonsTabs.ENGINEERS.equals(selectedTab)) {
            this.getChildren().add(this.horizonsEngineerSearchBar);
        } else if (HorizonsTabs.POWERPLAY.equals(selectedTab)) {
            this.getChildren().add(this.powerplaySearchBar);
        } else {
            this.getChildren().add(this.materialSearchBar);
        }
    }

    private boolean isRecipeBarVisible() {
        return PreferencesService.getPreference(PreferenceConstants.HORIZONS_RECIPES_VISIBLE, Boolean.TRUE);
    }
}
