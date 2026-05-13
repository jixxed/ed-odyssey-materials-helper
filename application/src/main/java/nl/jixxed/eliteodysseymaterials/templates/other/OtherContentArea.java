/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.other;

import javafx.application.Platform;
import javafx.geometry.Side;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TabPaneBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.OtherTabType;
import nl.jixxed.eliteodysseymaterials.enums.TabType;
import nl.jixxed.eliteodysseymaterials.helper.AnchorPaneHelper;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.OtherTabSelectedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableAnchorPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTabPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.other.colonisation.ColonisationTab;
import nl.jixxed.eliteodysseymaterials.templates.other.permits.PermitsTab;
import nl.jixxed.eliteodysseymaterials.templates.other.powerplay.PowerplayTab;
import nl.jixxed.eliteodysseymaterials.templates.other.communitygoal.CommunityGoalTab;

public class OtherContentArea extends DestroyableAnchorPane implements DestroyableEventTemplate {
    private CommunityGoalTab communityGoalTab;
    private DestroyableTabPane tabs;
    private DestroyableVBox body;
    private PermitsTab permitsTab;

     public OtherContentArea() {
        initComponents();
        initEventHandling();
    }
    @Override
    public void initEventHandling() {

    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("other-tab-content");
        PowerplayTab powerplayTab = new PowerplayTab();
        powerplayTab.setClosable(false);
        ColonisationTab colonisationTab = new ColonisationTab();
        colonisationTab.setClosable(false);
        this.communityGoalTab = new CommunityGoalTab();
        this.communityGoalTab.setClosable(false);
        permitsTab = new PermitsTab();
        permitsTab.setClosable(false);

        OtherSearchBar searchBar = new OtherSearchBar();
        this.tabs = TabPaneBuilder.builder()
                .withTabs(this.communityGoalTab, powerplayTab, colonisationTab, permitsTab)
                .withStyleClass("other-tab-pane")
                .withSide(Side.LEFT)
                .withSelectedItemListener((_, _, newValue) -> {
                    if (newValue != null) {
                        final OtherTabType tabType = (OtherTabType) ((OtherTab) newValue).getTabType();
                        EventService.publish(new OtherTabSelectedEvent(tabType));
                    }
                    PreferencesService.setPreference(PreferenceConstants.SELECTED_TAB_OTHER, this.tabs.getTabs().indexOf(newValue));
                })
                .build();
        this.tabs.getSelectionModel().select(Math.min(PreferencesService.getPreference(PreferenceConstants.SELECTED_TAB_OTHER, 0), this.tabs.getTabs().size() - 1));
        VBox.setVgrow(this.tabs, Priority.ALWAYS);

        this.body = BoxBuilder.builder().withNodes(searchBar, this.tabs).buildVBox();
        HBox.setHgrow(this.body, Priority.ALWAYS);

        AnchorPaneHelper.setAnchor(this.tabs, 0.0, 0.0, 0.0, 0.0);

        this.getNodes().addAll( this.body);
        setBodyAnchor();
    }

    public TabType getSelectedTab() {
        final OtherTab selected = (OtherTab) this.tabs.getSelectionModel().getSelectedItem();
        return selected.getTabType();
    }

    private void setBodyAnchor() {
        Platform.runLater(() -> {
            AnchorPaneHelper.setAnchor(this.body, 0.0, 0.0,  0.0, 0.0);
            this.requestLayout();
        });
    }
}
