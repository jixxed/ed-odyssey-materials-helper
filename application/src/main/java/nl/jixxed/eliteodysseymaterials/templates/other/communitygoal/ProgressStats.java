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

import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ProgressBarBuilder;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.cg.ReportModels;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableProgressBar;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

public class ProgressStats extends DestroyableVBox implements DestroyableTemplate {

    private DestroyableLabel progressNumber;
    private DestroyableLabel progressPercent;
    private DestroyableProgressBar progress;
    private DestroyableLabel estimate;

    public ProgressStats() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("progress-stats");
        progressNumber = LabelBuilder.builder().withStyleClass("progress-number").build();
        progressPercent = LabelBuilder.builder().withStyleClass("progress-percent").build();
        this.getNodes().add(BoxBuilder.builder().withStyleClass("progress-values").withNodes(progressNumber, new GrowingRegion(), progressPercent).buildHBox());
        progress = ProgressBarBuilder.builder().withStyleClass("progress").build();
        this.getNodes().add(progress);
        estimate = LabelBuilder.builder().withStyleClass("estimate").build();
        this.getNodes().add(estimate);

    }

    public void update(ReportModels.CommunityGoalReport report) {
        progressNumber.addBinding(progressNumber.textProperty(), LocaleService.getStringBinding("community.goal.progress.value", Formatters.NUMBER_FORMAT_0.format(report.progress().currentAmount()), Formatters.NUMBER_FORMAT_0.format(report.progress().requiredAmount())));
        double completion = (double) report.progress().currentAmount() / report.progress().requiredAmount();
        progressPercent.addBinding(progressPercent.textProperty(), LocaleService.getStringBinding("community.goal.progress.percent", Formatters.NUMBER_FORMAT_1.format(Math.clamp(completion * 100D, 0D, 100D))));
        progress.setProgress(completion);
        this.estimate.addBinding(this.estimate.textProperty(), LocaleService.getStringBinding("community.goal.progress.estimate", report.progress().estimatedCompletion()));
    }
}
