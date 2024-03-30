package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.CheckBoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.FlipRemainingAvailableEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

import java.util.ArrayList;
import java.util.List;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;

public class OdysseyWishlist extends VBox implements Template {
    private DestroyableLabel flipOdysseyRemainingAvailableLabel;
    private DestroyableLabel flipOdysseyRemainingAvailableExplainLabel;
    private CheckBox flipOdysseyRemainingAvailableCheckBox;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    public OdysseyWishlist() {
        this.initComponents();
        this.initEventHandling();
    }
    @Override
    public void initComponents() {
        final Label wishlistOdysseyLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.settings.title.wishlist.odyssey"))
                .build();
        final HBox odysseyRemainingAvailableSetting = createOdysseyRemainingAvailableSetting();

        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getChildren().addAll(wishlistOdysseyLabel, odysseyRemainingAvailableSetting);
    }

    @Override
    public void initEventHandling() {

    }
    private HBox createOdysseyRemainingAvailableSetting() {
        this.flipOdysseyRemainingAvailableLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.flip.remaining.available.odyssey")).build();
        this.flipOdysseyRemainingAvailableExplainLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.flip.remaining.available.odyssey.explain")).build();
        this.flipOdysseyRemainingAvailableCheckBox  = CheckBoxBuilder.builder()
                .withValue(PreferencesService.getPreference(PreferenceConstants.FLIP_WISHLIST_REMAINING_AVAILABLE_ODYSSEY, Boolean.FALSE))//available is default(false)
                .withChangeListener((observable, oldValue, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.FLIP_WISHLIST_REMAINING_AVAILABLE_ODYSSEY, newValue);
                    EventService.publish(new FlipRemainingAvailableEvent(Expansion.ODYSSEY, newValue));
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.flipOdysseyRemainingAvailableLabel, this.flipOdysseyRemainingAvailableCheckBox, this.flipOdysseyRemainingAvailableExplainLabel)
                .buildHBox();
    }

}
