/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.other.communitygoal;

import javafx.collections.FXCollections;
import nl.jixxed.eliteodysseymaterials.builder.ComboBoxBuilder;
import nl.jixxed.eliteodysseymaterials.domain.Goal;
import nl.jixxed.eliteodysseymaterials.service.cg.CommunityGoalsService;
import nl.jixxed.eliteodysseymaterials.service.event.AvailableCommunityGoalsEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.GoalSelectedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComboBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CommunityGoalMenu extends DestroyableHBox implements DestroyableEventTemplate {

    private DestroyableComboBox<Goal> goalSelect;

    public CommunityGoalMenu() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initEventHandling() {
        //update the list of entries on these events
        register(EventService.addListener(true, this, AvailableCommunityGoalsEvent.class, event -> refresh(event.getAvailableGoals())));
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("community-goal-menu");
        List<Goal> goals = new ArrayList<>();
        goals.add(Goal.ACTIVE);
        goalSelect = ComboBoxBuilder.builder(Goal.class)
                .withStyleClass("community-goal-select")
                .withSelected(Goal.ACTIVE)
                .withItemsProperty(FXCollections.observableArrayList(goals.stream().sorted(Comparator.comparing(Goal::getId).reversed()).toList()))
                .withValueChangeListener((_, _, newValue) -> {
                    if (newValue != null) {
                        EventService.publish(new GoalSelectedEvent(newValue));
                    }
                })
                .build();
        this.getNodes().add(goalSelect);
        refresh(CommunityGoalsService.getAvailableCommunityGoals());
    }

    public void refresh(List<Goal> availableGoals) {
        List<Goal> goals = new ArrayList<>();
        goals.add(Goal.ACTIVE);
        goals.addAll(availableGoals);
        Goal selectedItem = goalSelect.getSelectionModel().getSelectedItem();
        goalSelect.getItems().clear();
        goalSelect.getItems().addAll(goals.stream().sorted(Comparator.comparing(Goal::getId).reversed()).toList());
        if(selectedItem != null && goals.contains(selectedItem)) {
            goalSelect.getSelectionModel().select(selectedItem);
        }
    }
}
