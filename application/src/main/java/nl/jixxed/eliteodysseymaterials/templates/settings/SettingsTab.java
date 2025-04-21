package nl.jixxed.eliteodysseymaterials.templates.settings;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.ScrollPane;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;
import nl.jixxed.eliteodysseymaterials.helper.OsCheck;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.RegistryService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
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
    }

    private void initComponents() {
        this.getStyleClass().add("settings-tab");
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("tabs.settings"));
//        final DestroyableLabel settingsLabel = LabelBuilder.builder()
//                .withStyleClass("settings-header")
//                .withText(LocaleService.getStringBinding("tabs.settings"))
//                .build();
        final DestroyableVBox settings = BoxBuilder.builder()
                .withStyleClasses("settings-vbox", SETTINGS_SPACING_10_CLASS)
                .withNodes(new General(),
                        new OdysseyMaterials(),
                        new HorizonsMaterials(),
                        new OdysseyWishlist(),
                        new HorizonsWishlist(),
                        new HorizonsShips(),
                        new Notifications(),
                        new FrontierAPI())
                .buildVBox();
//        settings.getNodes().addAll(
//                new General(),
//                new OdysseyMaterials(),
//                new HorizonsMaterials(),
//                new OdysseyWishlist(),
//                new HorizonsWishlist(),
//                new HorizonsShips(),
//                new Notifications(),
//                new FrontierAPI()
//        );
        //AR
        if (OsCheck.isWindows()) {
            settings.getNodes().add(new AugmentedReality());
        }
        //Tracking
        settings.getNodes().add(new Tracking());

        ScrollPane scrollPane = register(ScrollPaneBuilder.builder()
                .withStyleClass("settings-tab-content")
                .withContent(settings)
                .build());
        this.setContent(scrollPane);
    }


    @Override
    public OdysseyTabs getTabType() {
        return OdysseyTabs.SETTINGS;
    }

}
