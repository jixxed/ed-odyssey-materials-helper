package nl.jixxed.eliteodysseymaterials.templates.generic;

import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.UTF8Constants;
import nl.jixxed.eliteodysseymaterials.enums.EngineerPrerequisite;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.BlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EngineerEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

public class UnlockRequirement extends DestroyableHBox implements DestroyableEventTemplate {

    private final EngineerPrerequisite prerequisite;
    private DestroyableLabel bullet;
    private DestroyableLabel name;

    public UnlockRequirement(EngineerPrerequisite prerequisite) {
        this.prerequisite = prerequisite;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        bullet = LabelBuilder.builder()
                .withStyleClass("bullet")
                .withNonLocalizedText(Boolean.TRUE.equals((prerequisite.isCompleted())) ? UTF8Constants.CHECK_TRUE : UTF8Constants.BULLET)
                .withOnMouseClicked(_ -> EventService.publish(new BlueprintClickEvent(prerequisite.getBlueprintName())))
                .build();
        name = LabelBuilder.builder()
                .withStyleClass("prerequisite")
                .withText(LocaleService.getStringBinding(prerequisite.getLocalisationKey()))
                .withOnMouseClicked(_ -> EventService.publish(new BlueprintClickEvent(prerequisite.getBlueprintName())))
                .build();
        this.getNodes().addAll(bullet, name);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, EngineerEvent.class, _ ->
                bullet.setText(Boolean.TRUE.equals((prerequisite.isCompleted())) ? UTF8Constants.CHECK_TRUE : UTF8Constants.BULLET)));
    }
}
