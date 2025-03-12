package nl.jixxed.eliteodysseymaterials.templates.settings;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;
import nl.jixxed.eliteodysseymaterials.helper.OsCheck;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.RegistryService;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyTab;
import nl.jixxed.eliteodysseymaterials.templates.settings.sections.*;

@Slf4j
public class SettingsTab extends OdysseyTab {
    public static final String SETTINGS_LABEL_CLASS = "settings-label";
    public static final String SETTINGS_LINK_CLASS = "settings-link";
    public static final String SETTINGS_DROPDOWN_CLASS = "settings-dropdown";
    public static final String SETTINGS_SPACING_10_CLASS = "settings-spacing-10";
    public static final String SETTINGS_JOURNAL_LINE_STYLE_CLASS = "settings-journal-line";
    public static final String SETTINGS_BUTTON_STYLE_CLASS = "settings-button";

    public static final BooleanProperty REGISTERED = new SimpleBooleanProperty(RegistryService.isRegistered());



    public SettingsTab() {
        initComponents();
        initEventHandling();
    }


    private void initEventHandling() {

    }

    private void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.settings"));
        final Label settingsLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tabs.settings"))
                .build();
        final VBox settings = BoxBuilder.builder()
                .withStyleClasses("settings-vbox", SETTINGS_SPACING_10_CLASS)
                .withNodes(settingsLabel)
                .buildVBox();
        settings.getChildren().addAll(
                new General(),
                new OdysseyMaterials(),
                new HorizonsMaterials(),
                new OdysseyWishlist(),
                new HorizonsWishlist(),
                new HorizonsShips(),
                new Notifications(),
                new FrontierAPI()
        );
        //AR
        if (OsCheck.isWindows()) {
            settings.getChildren().add(new AugmentedReality());
        }
        //Tracking
        settings.getChildren().add(new Tracking());

        ScrollPane scrollPane = ScrollPaneBuilder.builder()
                .withContent(settings)
                .build();
        this.setContent(scrollPane);
    }


    @Override
    public OdysseyTabs getTabType() {
        return OdysseyTabs.SETTINGS;
    }

}
