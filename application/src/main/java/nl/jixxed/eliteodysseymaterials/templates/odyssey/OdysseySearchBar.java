package nl.jixxed.eliteodysseymaterials.templates.odyssey;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.engineers.OdysseyEngineerSearchBar;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.materials.OdysseyMaterialSearchBar;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.trade.OdysseyTradeSearchBar;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist.OdysseyWishlistSearchBar;

@Slf4j
class OdysseySearchBar extends HBox {

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";
    private Button button;
    private OdysseyMaterialSearchBar materialSearchBar;
    private OdysseyTradeSearchBar tradeSearchBar;
    private OdysseyWishlistSearchBar wishlistSearchBar;
    private OdysseyEngineerSearchBar engineerSearchBar;

    OdysseySearchBar() {
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        this.getStyleClass().add("root");
        initMenuButton();
        this.materialSearchBar = new OdysseyMaterialSearchBar();
        this.tradeSearchBar = new OdysseyTradeSearchBar();
        this.wishlistSearchBar = new OdysseyWishlistSearchBar();
        this.engineerSearchBar = new OdysseyEngineerSearchBar();

        applyFontSizingHack();

        HBox.setHgrow(this.materialSearchBar, Priority.ALWAYS);
        HBox.setHgrow(this.tradeSearchBar, Priority.ALWAYS);
        HBox.setHgrow(this.wishlistSearchBar, Priority.ALWAYS);
        HBox.setHgrow(this.engineerSearchBar, Priority.ALWAYS);
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
            EventService.publish(new MenuButtonClickedEvent(Expansion.ODYSSEY));
        });
    }

    private void initEventHandling() {
        EventService.addListener(this, BlueprintClickEvent.class, blueprintClickEvent -> this.button.setText("<"));
        //hack for component resizing on other fontsizes
        EventService.addListener(this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSizeEvent.getFontSize());
            this.styleProperty().set(fontStyle);
            this.button.styleProperty().set(fontStyle);
        });
        EventService.addListener(this, OdysseyTabSelectedEvent.class, event -> {
            if (OdysseyTabs.TRADE.equals(event.getSelectedTab())) {
                if (this.getChildren().contains(this.materialSearchBar) || this.getChildren().contains(this.wishlistSearchBar) || this.getChildren().contains(this.engineerSearchBar)) {
                    this.getChildren().remove(this.materialSearchBar);
                    this.getChildren().remove(this.wishlistSearchBar);
                    this.getChildren().remove(this.engineerSearchBar);
                    this.getChildren().add(this.tradeSearchBar);
                }
            } else if (OdysseyTabs.WISHLIST.equals(event.getSelectedTab())) {
                if (this.getChildren().contains(this.materialSearchBar) || this.getChildren().contains(this.tradeSearchBar) || this.getChildren().contains(this.engineerSearchBar)) {
                    this.getChildren().remove(this.materialSearchBar);
                    this.getChildren().remove(this.tradeSearchBar);
                    this.getChildren().remove(this.engineerSearchBar);
                    this.getChildren().add(this.wishlistSearchBar);
                }
            } else if (OdysseyTabs.ENGINEERS.equals(event.getSelectedTab())) {
                if (this.getChildren().contains(this.materialSearchBar) || this.getChildren().contains(this.tradeSearchBar) || this.getChildren().contains(this.wishlistSearchBar)) {
                    this.getChildren().remove(this.materialSearchBar);
                    this.getChildren().remove(this.tradeSearchBar);
                    this.getChildren().remove(this.wishlistSearchBar);
                    this.getChildren().add(this.engineerSearchBar);
                }
            } else {
                if (this.getChildren().contains(this.tradeSearchBar) || this.getChildren().contains(this.wishlistSearchBar) || this.getChildren().contains(this.engineerSearchBar)) {
                    this.getChildren().remove(this.tradeSearchBar);
                    this.getChildren().remove(this.wishlistSearchBar);
                    this.getChildren().remove(this.engineerSearchBar);
                    this.getChildren().add(this.materialSearchBar);
                }
            }
        });
    }

    private boolean isRecipeBarVisible() {
        return PreferencesService.getPreference(PreferenceConstants.RECIPES_VISIBLE, Boolean.TRUE);
    }
}
