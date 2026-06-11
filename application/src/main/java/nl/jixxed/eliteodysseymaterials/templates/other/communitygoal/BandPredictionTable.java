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
import nl.jixxed.eliteodysseymaterials.domain.Band;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.cg.BandPredictionService;
import nl.jixxed.eliteodysseymaterials.service.cg.PerBandDeltas;
import nl.jixxed.eliteodysseymaterials.service.cg.ReportModels;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BandPredictionTable extends DestroyableVBox implements DestroyableTemplate {

    private DestroyableHBox table;

    public BandPredictionTable() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("band-prediction-table");
        table = BoxBuilder.builder()
                .withStyleClass("threshold-table")
                .buildHBox();
        this.getNodes().add(table);
    }

    /**
     * Updates the table with the latest report data and player contribution.
     *
     * @param report             the community goal report
     * @param playerContribution the player's current contribution value
     */
    public void update(ReportModels.CommunityGoalReport report, long playerContribution) {
        table.getNodes().clear();
        Set<Band> bands = extractBandsFromReport(report);
        if (bands.isEmpty()) {
            this.setVisible(false);
            this.setManaged(false);
            return;
        }

        List<Band> bandOrder = bands.stream().sorted((a, b) -> b.compareTo(a)).toList();
        List<ReportModels.BandMax> latestBandMax = report.hourlyData().getLast().bandMax();
        Map<String, Long> thresholds = BandPredictionService.deriveBandThresholds(latestBandMax);

        // Determine the player's highest reached band (for highlighting)
        Band playerCurrentBand = determinePlayerBand(playerContribution, thresholds);

        // Resolve CG end time early (null for expired/insufficient-data CGs)
        Instant cgEndUtc = resolveCgEndUtc(report);

        // Build per-band columns (like RewardsTable)
        Map<Band, DestroyableVBox> bandColumns = new java.util.LinkedHashMap<>();

        for (Band band : bandOrder) {
            DestroyableVBox column = BoxBuilder.builder().withStyleClass("threshold-values").buildVBox();
            bandColumns.put(band, column);
        }

        // Header row: band names in each column (localized like RewardsTable)
        for (Band band : bandOrder) {
            DestroyableVBox column = bandColumns.get(band);
            String key = band.getName().contains("top") ? "community.goal.reward.table.top" : "community.goal.reward.table.percent";
            // Strip "top" or "%" from the name so the i18n key can add its own formatting
            String placeholder = band.getName().replace("top", "").replace("%", "");
            column.getNodes().add(LabelBuilder.builder()
                    .withStyleClass("cg-threshold-title")
                    .withText(key, placeholder)
                    .build());
        }

        // Current row
        for (Band band : bandOrder) {
            Long threshold = thresholds.get(band.getName());
            if (threshold == null) {
                continue;
            }
            DestroyableLabel valueLabel = LabelBuilder.builder()
                    .withStyleClass("cg-threshold-value")
                    .withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(threshold))
                    .build();
            if (playerCurrentBand != null && playerCurrentBand.getName().equals(band.getName())) {
                valueLabel.getStyleClass().add("band-prediction-highlight");
            }
            bandColumns.get(band).getNodes().add(valueLabel);
        }

        // Predicted row (only when CG has not yet ended and enough data)
        if (report.hourlyData().size() >= 14 && cgEndUtc != null && cgEndUtc.isAfter(Instant.now())) {
            Optional<PerBandDeltas> perBandDeltas =
                    BandPredictionService.calculatePerBandDeltas(report.hourlyData(), cgEndUtc);
            // Determine highest predicted band
            Band predictedBand = null;
            for (Band band : bandOrder) {
                Long threshold = thresholds.get(band.getName());
                if (threshold == null) {
                    continue;
                }
                long delta = perBandDeltas
                        .map(p -> p.pessimisticFor(band.getName()))
                        .orElse(0L);
                long predValue = threshold + delta;
                if (playerContribution >= predValue) {
                    predictedBand = band;
                }
            }

            for (Band band : bandOrder) {
                Long threshold = thresholds.get(band.getName());
                if (threshold == null) {
                    continue;
                }
                long predictedMin = calculatePredictedMin(band.getName(), thresholds, perBandDeltas);
                long predictedMax = calculatePredictedMax(band.getName(), thresholds, perBandDeltas);
                String rangeText = predictedMin == predictedMax
                        ? Formatters.NUMBER_FORMAT_0.format(predictedMin)
                        : Formatters.NUMBER_FORMAT_0.format(predictedMin) + "-" + Formatters.NUMBER_FORMAT_0.format(predictedMax);

                DestroyableLabel valueLabel = LabelBuilder.builder()
                        .withStyleClass("cg-threshold-value")
                        .withNonLocalizedText(rangeText)
                        .build();
                if (predictedBand != null && predictedBand.getName().equals(band.getName())) {
                    valueLabel.getStyleClass().add("band-prediction-highlight");
                } else if (playerContribution >= predictedMin && playerContribution < predictedMax) {
                    valueLabel.getStyleClass().add("band-prediction-between");
                }
                bandColumns.get(band).getNodes().add(valueLabel);
            }
        }

        // Build table: label column + spacers + band columns
        DestroyableVBox labelColumn = BoxBuilder.builder()
                .withStyleClass("threshold-names")
                .buildVBox();
        labelColumn.getNodes().add(LabelBuilder.builder()
                .withStyleClass("cg-threshold-title")
                .withText("community.goal.band.prediction.header")
                .build());
        labelColumn.getNodes().add(LabelBuilder.builder()
                .withStyleClass("cg-threshold-title")
                .withText(cgEndUtc != null && cgEndUtc.isBefore(Instant.now())
                        ? "community.goal.band.prediction.final"
                        : "community.goal.band.prediction.current")
                .build());
        if (report.hourlyData().size() >= 14 && cgEndUtc != null && cgEndUtc.isAfter(Instant.now())) {
            labelColumn.getNodes().add(LabelBuilder.builder()
                    .withStyleClass("cg-threshold-title")
                    .withText("community.goal.band.prediction.predicted")
                    .build());
        }
        table.getNodes().add(labelColumn);

        for (Band band : bandOrder) {
            table.getNodes().add(bandColumns.get(band));
            table.getNodes().add(new GrowingRegion("cg-spacer"));
        }

        boolean visible = !report.hourlyData().isEmpty();
        this.setVisible(visible);
        this.setManaged(visible);
    }

    /**
     * Determines the highest band the player currently qualifies for.
     *
     * @param playerContribution the player's current contribution value
     * @param thresholds         map of band name to minimum threshold
     * @return the highest band the player qualifies for, or null
     */
    private Band determinePlayerBand(long playerContribution, Map<String, Long> thresholds) {
        Band highest = null;
        for (Map.Entry<String, Long> entry : thresholds.entrySet()) {
            if (playerContribution >= entry.getValue()) {
                highest = new Band(entry.getKey());
            }
        }
        return highest;
    }

    /**
     * Extracts unique bands from the latest hourly data point.
     *
     * @param report the community goal report
     * @return set of unique bands
     */
    private Set<Band> extractBandsFromReport(ReportModels.CommunityGoalReport report) {
        List<ReportModels.BandMax> latestBandMax = report.hourlyData().getLast().bandMax();
        return latestBandMax.stream()
                .map(b -> new Band(b.band()))
                .collect(Collectors.toCollection(java.util.LinkedHashSet::new));
    }

    /**
     * Calculates the minimum predicted threshold for a band.
     *
     * @param bandName      the band name
     * @param thresholds    map of band name to current threshold
     * @param perBandDeltas optional per-band deltas
     * @return predicted minimum value (threshold + pessimistic delta)
     */
    private long calculatePredictedMin(String bandName, Map<String, Long> thresholds, Optional<PerBandDeltas> perBandDeltas) {
        long delta = perBandDeltas
                .map(p -> p.optimisticFor(bandName))
                .orElse(0L);
        return thresholds.getOrDefault(bandName, 0L) + delta;
    }

    /**
     * Calculates the maximum predicted threshold for a band.
     *
     * @param bandName      the band name
     * @param thresholds    map of band name to current threshold
     * @param perBandDeltas optional per-band deltas
     * @return predicted maximum value (threshold + optimistic delta)
     */
    private long calculatePredictedMax(String bandName, Map<String, Long> thresholds, Optional<PerBandDeltas> perBandDeltas) {
        long delta = perBandDeltas
                .map(p -> p.pessimisticFor(bandName))
                .orElse(0L);
        return thresholds.getOrDefault(bandName, 0L) + delta;
    }

    /**
     * Resolves the CG end time from the report's progress or metadata.
     * Tries estimatedCompletion first, then metadata expiry, then progress.endDate().
     *
     * @param report the community goal report
     * @return resolved CG end time, or progress.endDate() as fallback
     */
    private Instant resolveCgEndUtc(ReportModels.CommunityGoalReport report) {
        String estimatedCompletion = report.progress().estimatedCompletion();
        if (estimatedCompletion != null
                && !estimatedCompletion.equals("insufficient-data")
                && !estimatedCompletion.equals("expired")
                && !estimatedCompletion.equals("current-value")) {
            try {
                return Instant.parse(estimatedCompletion);
            } catch (Exception e) {
                // safe: falls through to metadata expiry parsing below
            }
        }
        Object expiryObj = report.metadata().get("expiry");
        if (expiryObj instanceof String expiry) {
            try {
                return LocalDateTime.parse(expiry, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        .atZone(java.time.ZoneOffset.UTC)
                        .toInstant();
            } catch (Exception e) {
                // safe: falls through to progress.endDate() fallback
            }
        }
        return report.progress().endDate();
    }

}
