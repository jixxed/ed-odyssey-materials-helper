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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BandPredictionServiceTest {

    private static final Instant BASE_TIME = Instant.parse("2026-01-15T12:00:00Z");
    private static final Instant CG_END = BASE_TIME.plusSeconds(72 * 3600); // 72 hours from start

    @Test
    void whenInsufficientData_thenNoOptimisticPrediction() {
        List<HourlyData> hourlyData = createHourlyDataWithDeltas(BASE_TIME, List.of(100L, 90L, 85L, 80L, 75L, 70L, 65L, 60L, 55L, 50L));

        Optional<BandPrediction> result = BandPredictionService.calculateOptimisticPrediction(hourlyData, CG_END);

        assertFalse(result.isPresent());
    }

    @Test
    void whenInsufficientData_thenNoPessimisticPrediction() {
        List<HourlyData> hourlyData = createHourlyDataWithDeltas(BASE_TIME, List.of(100L, 90L, 85L, 80L, 75L, 70L, 65L, 60L, 55L, 50L));

        Optional<BandPrediction> result = BandPredictionService.calculatePessimisticPrediction(hourlyData, CG_END);

        assertFalse(result.isPresent());
    }

    @Test
    void whenOptimisticPrediction_thenDecayApplied() {
        // Simulate decaying deltas: 1000, 900, 800, 700, ... (delta of -100 each step)
        List<Long> deltas = List.of(
                1000L, 900L, 800L, 700L, 600L, 500L, 450L, 400L,
                380L, 350L, 330L, 310L, 300L, 300L, 290L, 280L,
                275L, 270L, 265L, 260L, 258L, 255L, 253L, 250L);
        List<HourlyData> hourlyData = createHourlyDataWithDeltas(BASE_TIME, deltas);

        Optional<BandPrediction> result = BandPredictionService.calculateOptimisticPrediction(hourlyData, CG_END);

        assertTrue(result.isPresent());
        long optimistic = result.get().optimistic();
        // With decaying rate, predicted delta should grow but at decreasing rate
        assertTrue(optimistic >= 0, "Optimistic prediction should not be negative");
    }

    @Test
    void whenPessimisticPrediction_thenLinearProjection() {
        // Simulate constant deltas: +100 per hour
        List<Long> deltas = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            deltas.add(100L);
        }
        List<HourlyData> hourlyData = createHourlyDataWithDeltas(BASE_TIME, deltas);

        Optional<BandPrediction> result = BandPredictionService.calculatePessimisticPrediction(hourlyData, CG_END);

        assertTrue(result.isPresent());
        long pessimistic = result.get().pessimistic();
        // 24 data points, CG ends at 72h, remaining = 72-24 = 48h
        // Rate ~ 100/h, so pessimistic = 100 * 48 = 4800
        assertTrue(pessimistic > 0, "Pessimistic prediction should be positive");
        assertTrue(pessimistic <= 6000, "Pessimistic prediction should be bounded");
    }

    @Test
    void whenDecayHitsZero_thenNoNegativePrediction() {
        // Simulate deltas that approach zero
        List<Long> deltas = List.of(
                1000L, 900L, 800L, 700L, 600L, 500L, 400L, 300L,
                250L, 200L, 150L, 100L, 80L, 50L, 30L, 20L,
                10L, 5L, 3L, 2L, 1L, 1L, 0L, 0L);
        List<HourlyData> hourlyData = createHourlyDataWithDeltas(BASE_TIME, deltas);

        Optional<BandPrediction> result = BandPredictionService.calculateOptimisticPrediction(hourlyData, CG_END);

        assertTrue(result.isPresent());
        long optimistic = result.get().optimistic();
        assertTrue(optimistic >= 0, "Optimistic prediction should not be negative");
    }

    @Test
    void whenExpiredCG_thenNoPrediction() {
        List<Long> deltas = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            deltas.add(100L);
        }
        List<HourlyData> hourlyData = createHourlyDataWithDeltas(BASE_TIME, deltas);
        Instant cgEndPast = BASE_TIME.minusSeconds(3600);

        Optional<BandPrediction> result = BandPredictionService.calculateOptimisticPrediction(hourlyData, cgEndPast);

        assertFalse(result.isPresent());
    }

    @Test
    void whenRealResponse849Data_thenThresholdsAndPredictionsMatchExpected() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());
        ReportModels.ReportResponse response;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("cg/response849.json")) {
            response = mapper.readValue(is, ReportModels.ReportResponse.class);
        }

        ReportModels.CommunityGoalReport report = response.goals().get(0);
        List<HourlyData> hourlyData = report.hourlyData();

        // Verify we have enough data points (108 hours of data)
        assertTrue(hourlyData.size() >= 14, "Should have more than 14 hours of data");

        // Derive thresholds from latest bandMax
        List<BandMax> latestBandMax = hourlyData.getLast().bandMax();
        Map<String, Long> thresholds = BandPredictionService.deriveBandThresholds(latestBandMax);

        // Expected current thresholds for bands in reversed comparator order:
        // 100%, 75%, 50%, 25%, top10 -> 1, 1041, 2609, 8884, 97406
        assertEquals(1L, thresholds.get("100%"));
        assertEquals(1041L, thresholds.get("75%"));
        assertEquals(2609L, thresholds.get("50%"));
        assertEquals(8884L, thresholds.get("25%"));
        assertEquals(97406L, thresholds.get("top10"));

       // Calculate per-band deltas for 75% band (use estimated completion, not CG end)
        Instant cgEndUtc = Instant.parse("2026-06-17T22:00:00Z"); // estimated completion
        Optional<PerBandDeltas> perBandDeltas = BandPredictionService.calculatePerBandDeltas(hourlyData, cgEndUtc);

        assertTrue(perBandDeltas.isPresent(), "Should have per-band prediction with >14h data");

        long optimisticDelta75 = perBandDeltas.get().optimisticFor("75%");
        long pessimisticDelta75 = perBandDeltas.get().pessimisticFor("75%");

        long predicted75Optimistic = thresholds.get("75%") + optimisticDelta75;
        long predicted75Pessimistic = thresholds.get("75%") + pessimisticDelta75;

        // 75% band predicted values should match expected ranges
        assertTrue(predicted75Optimistic >= 1050,
                "Optimistic 75%% predicted should be >= 1050, got " + predicted75Optimistic);
        assertTrue(predicted75Optimistic <= 1100,
                "Optimistic 75%% predicted should be <= 1100, got " + predicted75Optimistic);
        assertTrue(predicted75Pessimistic >= 1650,
                "Pessimistic 75%% predicted should be >= 1650, got " + predicted75Pessimistic);
        assertTrue(predicted75Pessimistic <= 1850,
                "Pessimistic 75%% predicted should be <= 1850, got " + predicted75Pessimistic);

        // Pessimistic >= optimistic for all bands
        for (String bandName : perBandDeltas.get().optimisticDeltas().keySet()) {
            long opt = perBandDeltas.get().optimisticFor(bandName);
            long pess = perBandDeltas.get().pessimisticFor(bandName);
            assertTrue(pess >= opt,
                    "Pessimistic >= optimistic for band " + bandName + ": pessimistic=" + pess + ", optimistic=" + opt);
        }
    }

    @Test
    void whenRealResponse849Data_thenAllBandPredictionsHaveExactValues() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());
        ReportModels.ReportResponse response;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("cg/response849.json")) {
            response = mapper.readValue(is, ReportModels.ReportResponse.class);
        }

        ReportModels.CommunityGoalReport report = response.goals().get(0);
        List<HourlyData> hourlyData = report.hourlyData();

        List<BandMax> latestBandMax = hourlyData.getLast().bandMax();
        Map<String, Long> thresholds = BandPredictionService.deriveBandThresholds(latestBandMax);

        Instant cgEndUtc = Instant.parse("2026-06-17T22:00:00Z");
        Optional<PerBandDeltas> perBandDeltas = BandPredictionService.calculatePerBandDeltas(hourlyData, cgEndUtc);

        assertTrue(perBandDeltas.isPresent(), "Should have per-band prediction with >14h data");

        // Thresholds
        assertEquals(1L, thresholds.get("100%"));
        assertEquals(1041L, thresholds.get("75%"));
        assertEquals(2609L, thresholds.get("50%"));
        assertEquals(8884L, thresholds.get("25%"));
        assertEquals(97406L, thresholds.get("top10"));

        // Optimistic predictions (predicted = threshold + delta)
        assertEquals(1L, thresholds.get("100%") + perBandDeltas.get().optimisticFor("100%"), "100% optimistic predicted");
        assertEquals(1076L, thresholds.get("75%") + perBandDeltas.get().optimisticFor("75%"), "75% optimistic predicted");
        assertEquals(2903L, thresholds.get("50%") + perBandDeltas.get().optimisticFor("50%"), "50% optimistic predicted");
        assertEquals(12156L, thresholds.get("25%") + perBandDeltas.get().optimisticFor("25%"), "25% optimistic predicted");
        assertEquals(143377L, thresholds.get("top10") + perBandDeltas.get().optimisticFor("top10"), "top10 optimistic predicted");

        // Pessimistic predictions (predicted = threshold + delta)
        assertEquals(1L, thresholds.get("100%") + perBandDeltas.get().pessimisticFor("100%"), "100% pessimistic predicted");
        assertEquals(1688L, thresholds.get("75%") + perBandDeltas.get().pessimisticFor("75%"), "75% pessimistic predicted");
        assertEquals(3921L, thresholds.get("50%") + perBandDeltas.get().pessimisticFor("50%"), "50% pessimistic predicted");
        assertEquals(13971L, thresholds.get("25%") + perBandDeltas.get().pessimisticFor("25%"), "25% pessimistic predicted");
        assertEquals(171243L, thresholds.get("top10") + perBandDeltas.get().pessimisticFor("top10"), "top10 pessimistic predicted");
    }

    @Test
    void whenFirst14HoursOfResponse849Data_thenPredictionAvailable() throws Exception {
        // 12h skip + 2h minimum = 14h total required
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());
        ReportModels.ReportResponse response;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("cg/response849.json")) {
            response = mapper.readValue(is, ReportModels.ReportResponse.class);
        }

        List<HourlyData> hourlyData = response.goals().get(0).hourlyData().subList(0, 14);

        assertTrue(hourlyData.size() == 14, "Should have exactly 14 hours of data");

        Instant cgEndUtc = Instant.parse("2026-06-17T22:00:00Z");

        Optional<PerBandDeltas> perBandDeltas = BandPredictionService.calculatePerBandDeltas(hourlyData, cgEndUtc);

        assertTrue(perBandDeltas.isPresent(), "Should have prediction with 14h data (12h skip + 2h min)");

        long predicted75Optimistic = 1041 + perBandDeltas.get().optimisticFor("75%");
        long predicted75Pessimistic = 1041 + perBandDeltas.get().pessimisticFor("75%");

        assertTrue(predicted75Optimistic >= 1041,
                "Optimistic 75%% predicted should be >= current threshold, got " + predicted75Optimistic);
        assertTrue(predicted75Pessimistic >= 1041,
                "Pessimistic 75%% predicted should be >= current threshold, got " + predicted75Pessimistic);
    }

    @Test
    void whenFirst11HoursOfResponse849Data_thenNoPrediction() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());
        ReportModels.ReportResponse response;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("cg/response849.json")) {
            response = mapper.readValue(is, ReportModels.ReportResponse.class);
        }

        List<HourlyData> hourlyData = response.goals().get(0).hourlyData().subList(0, 11);

        // 11 data points is below the MINIMUM_DATA_POINTS threshold
        assertTrue(hourlyData.size() == 11, "Should have exactly 11 hours of data");

        Instant cgEndUtc = Instant.parse("2026-06-17T22:00:00Z");

        Optional<PerBandDeltas> optimisticDeltas = BandPredictionService.calculatePerBandDeltas(hourlyData, cgEndUtc);

        assertFalse(optimisticDeltas.isPresent(),
                "Should NOT have prediction with only 11h of data (below 14h minimum)");
    }

    @Test
    void whenNoHourlyData_thenNoPrediction() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());
        ReportModels.ReportResponse response;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("cg/response849.json")) {
            response = mapper.readValue(is, ReportModels.ReportResponse.class);
        }

        List<HourlyData> hourlyData = response.goals().get(0).hourlyData();
        hourlyData.clear();

        Instant cgEndUtc = Instant.parse("2026-06-17T22:00:00Z");

        Optional<PerBandDeltas> optimisticDeltas = BandPredictionService.calculatePerBandDeltas(hourlyData, cgEndUtc);

        assertFalse(optimisticDeltas.isPresent(),
                "Should NOT have prediction with no data");
    }

    @Test
    void whenConstantGrowth_thenScalarPredictionsReturnSameValue() {
        // 48 data points to ensure both optimistic (72h cap) and pessimistic work
        List<Long> deltas = new ArrayList<>();
        for (int i = 0; i < 48; i++) {
            deltas.add(100L);
        }
        List<HourlyData> hourlyData = createHourlyDataWithDeltas(BASE_TIME, deltas);

        Optional<BandPrediction> optimistic = BandPredictionService.calculateOptimisticPrediction(hourlyData, CG_END);
        Optional<BandPrediction> pessimistic = BandPredictionService.calculatePessimisticPrediction(hourlyData, CG_END);

        assertTrue(optimistic.isPresent());
        assertTrue(pessimistic.isPresent());

        // With constant growth, both should produce the same delta
        // (lookback is capped to available data for both)
        assertEquals(optimistic.get().optimistic(), pessimistic.get().pessimistic(),
                "Optimistic and pessimistic should be equal for constant growth");
    }

    @Test
    void whenDeriveBandThresholds_thenCorrectMapping() {
        List<BandMax> bandMax = List.of(
                new BandMax("top1", 100),
                new BandMax("top5", 500),
                new BandMax("top10", 1000),
                new BandMax("10%", 2500),
                new BandMax("25%", 5000),
                new BandMax("50%", 10000));
        Map<String, Long> thresholds = BandPredictionService.deriveBandThresholds(bandMax);

        // Sorted by BandComparator.reversed(): 50%, 25%, 10%, top10, top5, top1
        assertEquals(1L, thresholds.get("50%")); // first band always 1
        assertEquals(10001L, thresholds.get("25%")); // 50% max + 1
        assertEquals(5001L, thresholds.get("10%")); // 25% max + 1
        assertEquals(2501L, thresholds.get("top10")); // 10% max + 1
        assertEquals(1001L, thresholds.get("top5")); // top10 max + 1
        assertEquals(501L, thresholds.get("top1")); // top5 max + 1
        assertEquals(6, thresholds.size());
    }

    private List<HourlyData> createHourlyDataWithDeltas(Instant startTime, List<Long> deltas) {
        List<HourlyData> hourlyData = new ArrayList<>();
        for (int i = 0; i < deltas.size(); i++) {
            Instant hour = startTime.plusSeconds(i * 3600);
            BandMax bandMax = new BandMax("50%", 10000L + i * 100);
            hourlyData.add(new HourlyData(hour, 100L, 0L, deltas.get(i), null, List.of(bandMax)));
        }
        return hourlyData;
    }
}
