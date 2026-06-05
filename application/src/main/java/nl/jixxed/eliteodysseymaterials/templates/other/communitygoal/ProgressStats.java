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

import javafx.beans.binding.StringBinding;
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

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        String currentPercentage = Formatters.NUMBER_FORMAT_1.format(Math.clamp(completion * 100D, 0D, 100D));
        progressPercent.addBinding(progressPercent.textProperty(), LocaleService.getStringBinding("community.goal.progress.percent", currentPercentage));
        progress.setProgress(completion);
        String estimatedCompletion = report.progress().estimatedCompletion();
        this.estimate.setVisible(false);
        this.estimate.setManaged(false);
        if (estimatedCompletion.equals("current-value")) {
            this.estimate.addBinding(this.estimate.textProperty(), LocaleService.getStringBinding("community.goal.progress.percent", currentPercentage));
            this.estimate.setVisible(true);
            this.estimate.setManaged(true);
        } else if (!estimatedCompletion.equals("insufficient-data") && !estimatedCompletion.equals("expired")) {//date or percentage
            try {
                ZonedDateTime zoned = ZonedDateTime.parse(estimatedCompletion).withZoneSameInstant(ZoneId.systemDefault());
                String formatKey = "community.goal.date.format.currentyear";
                StringBinding formattedDate = LocaleService.getStringBinding(locale -> {
                    String pattern = LocaleService.getLocalizedStringForLocale(locale, formatKey);
                    return LocaleService.getLocalizedStringForCurrentLocale("community.goal.progress.estimate", DateTimeFormatter.ofPattern(pattern).withLocale(locale).format(zoned));
                });
                this.estimate.addBinding(this.estimate.textProperty(), formattedDate);

            } catch (DateTimeParseException ex) {
                this.estimate.addBinding(this.estimate.textProperty(), LocaleService.getStringBinding("community.goal.progress.estimate", estimatedCompletion));
            }
            this.estimate.setVisible(true);
            this.estimate.setManaged(true);
        }
    }
}
