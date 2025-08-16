package nl.jixxed.eliteodysseymaterials.templates.horizons.engineers;

import javafx.beans.binding.StringBinding;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsBlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

public class Blueprint extends DestroyableHBox implements DestroyableTemplate {
    private HorizonsBlueprint blueprint;
    private Integer maxGrade;
    private boolean withType;
    private DestroyableLabel name;
    private DestroyableLabel bullet;

    public Blueprint(HorizonsBlueprint blueprint, Integer maxGrade, boolean withType) {
        this.blueprint = blueprint;
        this.maxGrade = maxGrade;
        this.withType = withType;
        initComponents();
    }

    public Blueprint(HorizonsBlueprint blueprint, Integer maxGrade) {
        this(blueprint, maxGrade, false);
    }

    @Override
    public void initComponents() {
        bullet = LabelBuilder.builder()
                .withStyleClass("bullet")
                .withNonLocalizedText((blueprint != null) ? maxGrade.toString() : "0")
                .withOnMouseClicked(event -> EventService.publish(new HorizonsBlueprintClickEvent(blueprint)))
                .build();
        name = LabelBuilder.builder()
                .withStyleClass("blueprint")
                .withText(getBlueprintStringBinding(withType, blueprint))
                .withOnMouseClicked(event -> EventService.publish(new HorizonsBlueprintClickEvent(blueprint)))
                .build();
        this.getNodes().addAll(bullet, name);
        this.setVisible(blueprint != null);
        this.setManaged(blueprint != null);
    }

    public void update(HorizonsBlueprint blueprint, Integer maxGrade, boolean withType) {
        this.blueprint = blueprint;
        this.maxGrade = maxGrade;
        this.withType = withType;
        bullet.setText(maxGrade.toString());
        name.textProperty().bind(getBlueprintStringBinding(withType, blueprint));
        this.setVisible(blueprint != null);
        this.setManaged(blueprint != null);
    }

    StringBinding getBlueprintStringBinding(boolean withType, HorizonsBlueprint blueprint) {
        if ((blueprint != null)) {
            return !withType
                    ? LocaleService.getStringBinding(blueprint.getBlueprintName().getLocalizationKey())
                    : LocaleService.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale(blueprint.getBlueprintName().getLocalizationKey()) + " - " +
                    LocaleService.getLocalizedStringForCurrentLocale(blueprint.getHorizonsBlueprintType().getLocalizationKey()));
        } else {
            return LocaleService.getStringBinding("blank");
        }
    }

}
