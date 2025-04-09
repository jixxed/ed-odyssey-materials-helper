package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.CheckBoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.FlipRemainingAvailableEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;

public class HorizonsWishlist extends DestroyableVBox implements DestroyableTemplate {

    public HorizonsWishlist() {
        this.initComponents();
    }

    @Override
    public void initComponents() {
        final DestroyableLabel wishlistLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText("tab.settings.title.wishlist.horizons")
                .build();
        final DestroyableHBox horizonsRemainingAvailableSetting = createHorizonsRemainingAvailableSetting();

        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getNodes().addAll(wishlistLabel, horizonsRemainingAvailableSetting);
    }

    private DestroyableHBox createHorizonsRemainingAvailableSetting() {
        DestroyableLabel flipHorizonsRemainingAvailableLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.flip.remaining.available.horizons")
                .build();
        DestroyableLabel flipHorizonsRemainingAvailableExplainLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.flip.remaining.available.horizons.explain")
                .build();
        //available is default(false)
        DestroyableCheckBox flipHorizonsRemainingAvailableCheckBox = CheckBoxBuilder.builder()
                .withSelected(PreferencesService.getPreference(PreferenceConstants.FLIP_WISHLIST_REMAINING_AVAILABLE_HORIZONS, Boolean.FALSE))//available is default(false)
                .withSelectedProperty((_, _, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.FLIP_WISHLIST_REMAINING_AVAILABLE_HORIZONS, newValue);
                    EventService.publish(new FlipRemainingAvailableEvent(Expansion.HORIZONS, newValue));
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(flipHorizonsRemainingAvailableLabel, flipHorizonsRemainingAvailableCheckBox, flipHorizonsRemainingAvailableExplainLabel)
                .buildHBox();
    }


}
