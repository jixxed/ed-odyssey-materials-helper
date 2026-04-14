/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
