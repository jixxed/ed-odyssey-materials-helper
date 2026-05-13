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

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class ReportModels {

    public record HourlyPoint(Instant hourUtc, long value) {}

    public record HourlyDeltaPoint(Instant hourUtc, Long delta) {}

    public record BandPoint(
            Instant hourUtc,
            String bandLabel,
            long minimumContribution,
            Long rewardBonus) {}

    public record ProgressData(
            Long currentAmount,
            Long requiredAmount,
            Instant startDate,
            Instant endDate,
            String estimatedCompletion) {}

    public record HourlyData(Instant hourUtc, long numContributors, long currentTotal, Long delta, Integer reachedTier, List<BandMax> bandMax) {}

    public record BandMax(String band, long max) {}

    public record TierReward(int tier, List<BandReward> bands) {}

    public record BandReward(String band, long reward) {}

    public record TierUnlock(int tier, Instant earliestOccurrence) {}

    public record CommunityGoalReport(
            long cgid,
            String title,
            List<HourlyData> hourlyData,
            List<TierReward> tierRewards,
            List<TierUnlock> tierUnlocks,
            ProgressData progress,
            Integer currentTopTier,
            Integer currentAchievedTier,
            List<String> integrityFlags,
            Map<String, Object> metadata) {}

    public record ReportResponse(Instant generatedAtUtc, List<CommunityGoalReport> goals) {}
}
