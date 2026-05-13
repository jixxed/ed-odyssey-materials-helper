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

import javafx.scene.control.ScrollPane;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.Goal;
import nl.jixxed.eliteodysseymaterials.enums.OtherTabType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.cg.CommunityGoalsService;
import nl.jixxed.eliteodysseymaterials.service.event.CommunityGoalEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.GoalSelectedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.other.OtherTab;

import java.util.List;

public class CommunityGoalTab extends OtherTab implements DestroyableEventTemplate {

    private DestroyableVBox communityGoalsBox;
    private Goal selectedGoal = Goal.ACTIVE;

    public CommunityGoalTab() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("community-goal-tab");
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("tabs.communitygoal"));
        final CommunityGoalMenu menu = new CommunityGoalMenu();
        this.communityGoalsBox = BoxBuilder.builder()
                .withStyleClass("community-goals")
                .buildVBox();
        final DestroyableVBox content = BoxBuilder.builder()
                .withStyleClass("contents")
                .withNodes(menu, this.communityGoalsBox).buildVBox();
        ScrollPane scrollPane = register(ScrollPaneBuilder.builder()
                .withStyleClass("community-goal-tab-content")
                .withContent(content)
                .build());
        this.setContent(scrollPane);
        refreshContent();

    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, CommunityGoalEvent.class, _ -> refreshContent()));
        register(EventService.addListener(true, this, GoalSelectedEvent.class, event -> {
            selectedGoal = event.getSelected();
            refreshContent();
        }));
    }

    private void refreshContent() {

        List<CommunityGoal> communityGoals = (selectedGoal.equals(Goal.ACTIVE))
                ? getActiveGoals().stream().map(CommunityGoal::new).toList()
                : List.of(new CommunityGoal(selectedGoal));
        this.communityGoalsBox.getNodes().clear();
        this.communityGoalsBox.getNodes().addAll(communityGoals);
    }

    private List<Goal> getActiveGoals() {
        return CommunityGoalsService.getAvailableCommunityGoals().stream().filter(Goal::getActive).toList();
    }

    @Override
    public OtherTabType getTabType() {
        return OtherTabType.COMMUNITYGOAL;
    }
}
