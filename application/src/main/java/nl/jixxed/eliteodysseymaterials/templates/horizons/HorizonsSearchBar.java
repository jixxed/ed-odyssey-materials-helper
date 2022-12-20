package nl.jixxed.eliteodysseymaterials.templates.horizons;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.horizons.commodities.HorizonsCommoditiesSearchBar;
import nl.jixxed.eliteodysseymaterials.templates.horizons.engineers.HorizonsEngineerSearchBar;
import nl.jixxed.eliteodysseymaterials.templates.horizons.materials.HorizonsMaterialSearchBar;
import nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist.HorizonsWishlistSearchBar;

import java.util.ArrayList;
import java.util.List;

@Slf4j
class HorizonsSearchBar extends HBox {

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";
    private Button button;
    private HorizonsMaterialSearchBar materialSearchBar;
    private HorizonsCommoditiesSearchBar commoditiesSearchBar;
    private HorizonsWishlistSearchBar horizonsWishlistSearchBar;
    private HorizonsEngineerSearchBar horizonsEngineerSearchBar;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    HorizonsSearchBar() {
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        this.getStyleClass().add("root");
        initMenuButton();
        this.materialSearchBar = new HorizonsMaterialSearchBar();
        this.commoditiesSearchBar = new HorizonsCommoditiesSearchBar();
        this.horizonsWishlistSearchBar = new HorizonsWishlistSearchBar();
        this.horizonsEngineerSearchBar = new HorizonsEngineerSearchBar();

        applyFontSizingHack();

        HBox.setHgrow(this.materialSearchBar, Priority.ALWAYS);
        HBox.setHgrow(this.commoditiesSearchBar, Priority.ALWAYS);
        HBox.setHgrow(this.horizonsWishlistSearchBar, Priority.ALWAYS);
        HBox.setHgrow(this.horizonsEngineerSearchBar, Priority.ALWAYS);
        this.getChildren().addAll(this.button, this.materialSearchBar);
    }

    private void applyFontSizingHack() {
        //hack for component resizing on other fontsizes
        final Integer fontSize = FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize();
        final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSize);
        this.styleProperty().set(fontStyle);
        this.button.styleProperty().set(fontStyle);
    }


    private void initMenuButton() {
        this.button = new Button();
        this.button.setText(isRecipeBarVisible() ? "<" : ">");
        this.button.getStyleClass().addAll("root", "menubutton");
        this.button.setOnAction(event -> {
            this.button.setText(isRecipeBarVisible() ? ">" : "<");
            EventService.publish(new MenuButtonClickedEvent(Expansion.HORIZONS));
        });
    }

    private void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, BlueprintClickEvent.class, blueprintClickEvent -> this.button.setText("<")));
        //hack for component resizing on other fontsizes
        this.eventListeners.add(EventService.addListener(this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSizeEvent.getFontSize());
            this.styleProperty().set(fontStyle);
            this.button.styleProperty().set(fontStyle);
        }));
        this.eventListeners.add(EventService.addListener(this, HorizonsTabSelectedEvent.class, event -> {
            if (HorizonsTabs.COMMODITIES.equals(event.getSelectedTab())) {
                if (this.getChildren().contains(this.materialSearchBar) || this.getChildren().contains(this.horizonsWishlistSearchBar) || this.getChildren().contains(this.horizonsEngineerSearchBar)) {
                    this.getChildren().remove(this.materialSearchBar);
                    this.getChildren().remove(this.horizonsWishlistSearchBar);
                    this.getChildren().remove(this.horizonsEngineerSearchBar);
                    this.getChildren().add(this.commoditiesSearchBar);
                }
            } else if (HorizonsTabs.WISHLIST.equals(event.getSelectedTab())) {
                if (this.getChildren().contains(this.materialSearchBar) || this.getChildren().contains(this.commoditiesSearchBar) || this.getChildren().contains(this.horizonsEngineerSearchBar)) {
                    this.getChildren().remove(this.materialSearchBar);
                    this.getChildren().remove(this.commoditiesSearchBar);
                    this.getChildren().remove(this.horizonsEngineerSearchBar);
                    this.getChildren().add(this.horizonsWishlistSearchBar);
                }
            } else if (HorizonsTabs.ENGINEERS.equals(event.getSelectedTab())) {
                if (this.getChildren().contains(this.materialSearchBar) || this.getChildren().contains(this.commoditiesSearchBar) || this.getChildren().contains(this.horizonsWishlistSearchBar)) {
                    this.getChildren().remove(this.materialSearchBar);
                    this.getChildren().remove(this.commoditiesSearchBar);
                    this.getChildren().remove(this.horizonsWishlistSearchBar);
                    this.getChildren().add(this.horizonsEngineerSearchBar);
                }
            } else {
                if (this.getChildren().contains(this.horizonsWishlistSearchBar) || this.getChildren().contains(this.commoditiesSearchBar) || this.getChildren().contains(this.horizonsEngineerSearchBar)) {
                    this.getChildren().remove(this.horizonsWishlistSearchBar);
                    this.getChildren().remove(this.commoditiesSearchBar);
                    this.getChildren().remove(this.horizonsEngineerSearchBar);
                    this.getChildren().add(this.materialSearchBar);
                }
            }
        }));
    }

    private boolean isRecipeBarVisible() {
        return PreferencesService.getPreference(PreferenceConstants.HORIZONS_RECIPES_VISIBLE, Boolean.TRUE);
    }
}
