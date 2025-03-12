package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.CheckBoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.FlipRemainingAvailableEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;

public class HorizonsWishlist extends DestroyableVBox implements DestroyableTemplate {

    private Label wishlistHorizonsGradeRollsLabel;
    private HBox wishlistHorizonsGradeRolls;
    private DestroyableLabel flipHorizonsRemainingAvailableLabel;
    private DestroyableLabel flipHorizonsRemainingAvailableExplainLabel;
    private CheckBox flipHorizonsRemainingAvailableCheckBox;
    public HorizonsWishlist() {
        this.initComponents();
        this.initEventHandling();
    }
    @Override
    public void initComponents() {
        final Label wishlistLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.settings.title.wishlist.horizons"))
                .build();
        final HBox horizonsRemainingAvailableSetting = createHorizonsRemainingAvailableSetting();
//        final HBox wishlistHorizonsGradeRollsSetting = createWishlistHorizonsGradeRollsSetting();

        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getChildren().addAll(wishlistLabel, horizonsRemainingAvailableSetting/*,wishlistHorizonsGradeRollsSetting*/);
    }

    @Override
    public void initEventHandling() {

    }
//    private HBox createWishlistHorizonsGradeRollsSetting() {
//        this.wishlistHorizonsGradeRollsLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.wishlist.grade.rolls")).build();
//        final VBox[] gradeControls = Arrays.stream(HorizonsBlueprintGrade.values()).filter(Predicate.not(HorizonsBlueprintGrade.NONE::equals)).sorted(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).map(grade ->
//                {
//                    final ButtonIntField buttonIntField = new ButtonIntField(0, 15, PreferencesService.getPreference(PreferenceConstants.WISHLIST_GRADE_ROLLS_PREFIX + grade.name(), grade.getDefaultNumberOfRolls()));
//                    buttonIntField.addHandlerOnValidChange(rolls -> PreferencesService.setPreference(PreferenceConstants.WISHLIST_GRADE_ROLLS_PREFIX + grade.name(), rolls));
//                    buttonIntField.getStyleClass().add("wishlist-rolls-select");
//                    final DestroyableLabel label = LabelBuilder.builder().withStyleClass("wishlist-rolls-label").withNonLocalizedText(String.valueOf(grade.getGrade())).build();
//                    final AnchorPane anchorPane2 = new AnchorPane(label);
//                    AnchorPaneHelper.setAnchor(label, 0D, 0D, 0D, 0D);
//                    return BoxBuilder.builder().withNodes(
//                            anchorPane2,
//                            buttonIntField
//                    ).buildVBox();
//                })
//                .toArray(VBox[]::new);
//
//        this.wishlistHorizonsGradeRolls = BoxBuilder.builder().withStyleClasses("grade-selects").withNodes(gradeControls).buildHBox();
//        return BoxBuilder.builder()
//                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
//                .withNodes(this.wishlistHorizonsGradeRollsLabel, this.wishlistHorizonsGradeRolls)
//                .buildHBox();
//    }
    private HBox createHorizonsRemainingAvailableSetting() {
        this.flipHorizonsRemainingAvailableLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.flip.remaining.available.horizons")).build();
        this.flipHorizonsRemainingAvailableExplainLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.flip.remaining.available.horizons.explain")).build();
        this.flipHorizonsRemainingAvailableCheckBox  = CheckBoxBuilder.builder()
                .withSelected(PreferencesService.getPreference(PreferenceConstants.FLIP_WISHLIST_REMAINING_AVAILABLE_HORIZONS, Boolean.FALSE))//available is default(false)
                .withSelectedProperty((observable, oldValue, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.FLIP_WISHLIST_REMAINING_AVAILABLE_HORIZONS, newValue);
                    EventService.publish(new FlipRemainingAvailableEvent(Expansion.HORIZONS, newValue));
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.flipHorizonsRemainingAvailableLabel, this.flipHorizonsRemainingAvailableCheckBox, this.flipHorizonsRemainingAvailableExplainLabel)
                .buildHBox();
    }


}
