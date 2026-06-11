/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.cg;

import nl.jixxed.eliteodysseymaterials.service.cg.ReportModels.BandMax;
import nl.jixxed.eliteodysseymaterials.service.cg.ReportModels.HourlyData;
import nl.jixxed.eliteodysseymaterials.templates.other.communitygoal.BandComparator;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Computes per-band growth deltas and their projected values at CG end.
 * Uses only hourUtc and bandMax fields from HourlyData.
 *
 * <p>For each band, derives its bandMin at each hour from that hour's bandMax data,
 * then tracks the bandMin growth over the lookback window and projects it forward.</p>
 */
public class BandPredictionService {

    private static final int MINIMUM_DATA_POINTS = 14;

    private BandPredictionService() {
    }

    /**
     * Calculates optimistic band minimum prediction.
     * Optimistic prediction uses a 72-hour lookback window, typically yielding lower growth estimates.
     * Uses the 50% bandMax growth rate for scalar projection.
     *
     * @param hourlyData the hourly contribution data
     * @param cgEndUtc   the community goal end time
     * @return prediction with optimistic bound, or empty if insufficient data
     */
    public static Optional<BandPrediction> calculateOptimisticPrediction(List<HourlyData> hourlyData,
            Instant cgEndUtc) {
        return calculateScalarPrediction(hourlyData, cgEndUtc, true);
    }

    /**
     * Calculates pessimistic band minimum prediction.
     * Pessimistic prediction uses the full available data range, typically yielding higher growth estimates.
     * Uses the 50% bandMax growth rate for scalar projection.
     *
     * @param hourlyData the hourly contribution data
     * @param cgEndUtc   the community goal end time
     * @return prediction with pessimistic bound, or empty if insufficient data
     */
    public static Optional<BandPrediction> calculatePessimisticPrediction(List<HourlyData> hourlyData,
            Instant cgEndUtc) {
        return calculateScalarPrediction(hourlyData, cgEndUtc, false);
    }

    /**
     * Calculates per-band growth deltas for display in the prediction table.
     * For each band, tracks its own bandMin growth over the lookback window
     * and projects it forward.
     * Both optimistic and pessimistic skip the first 12 hours of data, then:
     * optimistic uses the last 72 hours, pessimistic uses the full remaining range.
     * Requires at least 14 hours of data total, with at least 2 usable points after the skip.
     *
     * @param hourlyData the hourly contribution data
     * @param cgEndUtc   the community goal end time
     * @return per-band deltas for optimistic and pessimistic projections, or empty if insufficient data
     */
    public static Optional<PerBandDeltas> calculatePerBandDeltas(List<HourlyData> hourlyData,
            Instant cgEndUtc) {
        int dataPointCount = hourlyData.size();
        if (dataPointCount < MINIMUM_DATA_POINTS) {
            return Optional.empty();
        }

        int remainingHours = calculateRemainingHours(hourlyData, cgEndUtc);
        if (remainingHours <= 0) {
            return Optional.empty();
        }

        // Skip first 12 hours (dataPointCount >= 14 guaranteed, so skip is always 12)
        int skip = 12;
        int usableDataCount = dataPointCount - skip;

        List<HourlyData> usableData = hourlyData.subList(skip, dataPointCount);

        // Get sorted band list from the latest data point (has all bands)
        List<BandMax> sortedBands = hourlyData.getLast().bandMax().stream()
                .sorted(new BandComparator().reversed())
                .toList();

        if (sortedBands.isEmpty()) {
            return Optional.empty();
        }

        // Optimistic: last 72h of usable data
        int optLookback = Math.min(72, usableDataCount);
        List<HourlyData> optWindow = hourlyData.subList(dataPointCount - optLookback, dataPointCount);
        Map<String, Long> optimisticDeltas =
                calculateDeltasForWindow(sortedBands, optWindow, optLookback, remainingHours);

        // Pessimistic: full usable range (after 12h skip)
        Map<String, Long> pessimisticDeltas =
                calculateDeltasForWindow(sortedBands, usableData, usableDataCount, remainingHours);

        // Ensure pessimistic >= optimistic for each band
        for (String bandName : optimisticDeltas.keySet()) {
            long pess = pessimisticDeltas.getOrDefault(bandName, 0L);
            if (optimisticDeltas.get(bandName) > pess) {
                optimisticDeltas.put(bandName, pess);
            }
        }

        return Optional.of(new PerBandDeltas(optimisticDeltas, pessimisticDeltas));
    }

    /**
     * Calculate per-band deltas for a given lookback window.
     * Each band's projected threshold growth = its own bandMin growth projected forward.
     *
     * @param sortedBands    sorted list of band max values
     * @param window         hourly data window for lookback
     * @param lookback       number of hours in the lookback window
     * @param remainingHours remaining hours until CG end
     * @return map of band name to projected delta
     */
    private static Map<String, Long> calculateDeltasForWindow(List<BandMax> sortedBands,
            List<HourlyData> window, int lookback, int remainingHours) {
        // Pre-build per-hour lookup to avoid O(n*m^2) nested streams
        List<LinkedHashMap<String, BandMax>> hourLookups = window.stream()
                .map(data -> data.bandMax().stream().collect(java.util.stream.Collectors.toMap(
                        BandMax::band, b -> b, (a, bb) -> a, LinkedHashMap::new)))
                .toList();

        Map<String, Long> deltas = new LinkedHashMap<>();
        for (BandMax bandMax : sortedBands) {
            int targetIndex = findBandIndex(sortedBands, bandMax.band());
            if (targetIndex == -1) {
                deltas.put(bandMax.band(), 0L);
                continue;
            }
            List<Long> bandMins = deriveBandMinsForIndex(hourLookups, targetIndex, sortedBands);
            if (bandMins.size() >= 3) {
                long growth = bandMins.getLast() - bandMins.getFirst();
                deltas.put(bandMax.band(),
                        Math.round((double) growth / lookback * remainingHours));
            } else {
                deltas.put(bandMax.band(), 0L);
            }
        }
        return deltas;
    }

    /**
     * Scalar prediction for backward compatibility.
     * Uses the 50% band growth rate, projected linearly.
     *
     * @param hourlyData the hourly contribution data
     * @param cgEndUtc   the community goal end time
     * @param optimistic whether to use optimistic (72h) or full lookback
     * @return prediction with both optimistic and pessimistic set to the same delta
     */
    private static Optional<BandPrediction> calculateScalarPrediction(List<HourlyData> hourlyData,
            Instant cgEndUtc, boolean optimistic) {
        int dataPointCount = hourlyData.size();
        if (dataPointCount < MINIMUM_DATA_POINTS) {
            return Optional.empty();
        }

        int remainingHours = calculateRemainingHours(hourlyData, cgEndUtc);
        if (remainingHours <= 0) {
            return Optional.empty();
        }

        int lookback = Math.min(determineLookback(remainingHours, optimistic), dataPointCount);
        if (lookback < MINIMUM_DATA_POINTS) {
            return Optional.empty();
        }

        int startIdx = dataPointCount - lookback;
        List<Long> cumulativeValues = extractReferenceBandCumulative(hourlyData.subList(startIdx, dataPointCount));
        if (cumulativeValues.size() < 3) {
            return Optional.empty();
        }

        long growth = cumulativeValues.getLast() - cumulativeValues.getFirst();
        long delta = Math.round((double) growth / lookback * remainingHours);

        return Optional.of(new BandPrediction(delta, delta));
    }

    /**
     * Derives band minimum thresholds from the latest hourly data's bandMax values.
     * The bands are cumulative -- the minimum for a band is the previous band's max + 1.
     * The first band's minimum is 1.
     *
     * @param latestBandMax the band max values from the latest hourly data point
     * @return map of band name to its minimum threshold value
     */
    public static Map<String, Long> deriveBandThresholds(List<BandMax> latestBandMax) {
        Map<String, Long> thresholds = new LinkedHashMap<>();
        long previousMax = 0;

        List<BandMax> sorted = latestBandMax.stream()
                .sorted(new BandComparator().reversed())
                .toList();

        for (int i = 0; i < sorted.size(); i++) {
            BandMax bandMax = sorted.get(i);
            long min = (i == 0) ? 1 : previousMax + 1;
            thresholds.put(bandMax.band(), min);
            previousMax = bandMax.max();
        }
        return thresholds;
    }

    /**
     * Determines the lookback window size based on whether the prediction is optimistic or pessimistic.
     *
     * @param remainingHours hours remaining until CG end
     * @param optimistic     whether to use optimistic (72h cap) lookback
     * @return number of hours to look back
     */
    private static int determineLookback(int remainingHours, boolean optimistic) {
        if (optimistic) {
            return Math.min(72, remainingHours);
        }
        return remainingHours;
    }

    /**
     * Calculates the number of whole hours remaining until the CG ends.
     *
     * @param hourlyData the hourly contribution data
     * @param cgEndUtc   the community goal end time
     * @return remaining hours (0 if data is empty or CG has ended)
     */
    private static int calculateRemainingHours(List<HourlyData> hourlyData, Instant cgEndUtc) {
        if (hourlyData.isEmpty()) {
            return 0;
        }
        Instant lastDataPoint = hourlyData.getLast().hourUtc();
        long remainingMs = cgEndUtc.toEpochMilli() - lastDataPoint.toEpochMilli();
        return (int) Math.max(0, remainingMs / (1000L * 60 * 60));
    }

    /**
     * Extracts cumulative values for the 50% band from each hour in the window.
     *
     * @param hourlyData the hourly contribution data
     * @return list of 50% band max values in order
     */
    private static List<Long> extractReferenceBandCumulative(List<HourlyData> hourlyData) {
        return hourlyData.stream()
                .map(data -> data.bandMax().stream()
                        .filter(b -> "50%".equals(b.band()))
                        .findFirst()
                        .map(BandMax::max))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

   /**
     * Finds the index of a band in the sorted band list.
     *
     * @param sortedBands sorted list of band max values
     * @param bandName    the band name to find
     * @return index of the band, or -1 if not found
     */
    private static int findBandIndex(List<BandMax> sortedBands, String bandName) {
        for (int i = 0; i < sortedBands.size(); i++) {
            if (sortedBands.get(i).band().equals(bandName)) return i;
        }
        return -1;
    }

   /**
     * Derives bandMin threshold for a target index band at each hour using pre-built lookups.
     *
     * @param hourLookups   per-hour band name to BandMax lookup maps
     * @param targetIndex   index of the target band in sortedBands
     * @param sortedBands   sorted list of band max values
     * @return list of bandMin values, one per hour
     */
    private static List<Long> deriveBandMinsForIndex(List<LinkedHashMap<String, BandMax>> hourLookups,
            int targetIndex, List<BandMax> sortedBands) {
        return hourLookups.stream()
                .map(lookup -> deriveBandMinForIndex(lookup, targetIndex, sortedBands))
                .filter(java.util.Objects::nonNull)
                .toList();
    }

    /**
     * Derives bandMin threshold for a target index band at a single hour.
     *
     * @param hourLookup    band name to BandMax map for a single hour
     * @param targetIndex   index of the target band in sortedBands
     * @param sortedBands   sorted list of band max values
     * @return bandMin value for the target band, or null if the band is missing
     */
    private static Long deriveBandMinForIndex(LinkedHashMap<String, BandMax> hourLookup,
            int targetIndex, List<BandMax> sortedBands) {
        long previousMax = 0;
        for (int i = 0; i < sortedBands.size(); i++) {
            String bandName = sortedBands.get(i).band();
            BandMax bandMax = hourLookup.get(bandName);
            if (bandMax == null) return null;
            if (i == targetIndex) {
                return (i == 0) ? 1L : previousMax + 1;
            }
            previousMax = bandMax.max();
        }
        return null;
    }

}
