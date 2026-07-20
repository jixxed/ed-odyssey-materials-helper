/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.parser.messageprocessor.batch;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.persistence.commander.helper.CommunityGoalIdGenerator;
import nl.jixxed.eliteodysseymaterials.persistence.commander.model.CommunityGoalModel;
import nl.jixxed.eliteodysseymaterials.schemas.journal.CommunityGoal.CommunityGoal;
import nl.jixxed.eliteodysseymaterials.service.DatabaseException;
import nl.jixxed.eliteodysseymaterials.service.DatabaseService;
import nl.jixxed.eliteodysseymaterials.service.EliteMQService;

import java.math.BigInteger;
import java.util.List;

@Slf4j
public class CommunityGoalBatchMessageProcessor implements BatchMessageProcessor<CommunityGoal> {

    @Override
    public void process(final List<CommunityGoal> communityGoals) {
        communityGoals.forEach(communityGoal -> {
            communityGoal.getCurrentGoals().forEach(goal -> {
                String id = CommunityGoalIdGenerator.generate(communityGoal.getTimestamp(), goal.getCgid(), goal.getPlayerPercentileBand(), goal.getBonus().orElse(BigInteger.ZERO));
                try {
                        CommunityGoalModel cgGoal = CommunityGoalModel.builder()
                                .id(id)
                                .timestamp(communityGoal.getTimestamp())
                                .cgid(goal.getCgid())
                                .title(goal.getTitle())
                                .systemName(goal.getSystemName())
                                .marketName(goal.getMarketName())
                                .expiry(goal.getExpiry())
                                .isComplete(goal.getIsComplete())
                                .currentTotal(goal.getCurrentTotal())
                                .playerContribution(goal.getPlayerContribution())
                                .numContributors(goal.getNumContributors())
                                .topTierName(goal.getTopTier().getName())
                                .topTierBonus(goal.getTopTier().getBonus())
                                .topRankSize(goal.getTopRankSize().orElse(BigInteger.ZERO))
                                .playerInTopRank(goal.getPlayerInTopRank().orElse(false))
                                .tierReached(goal.getTierReached().orElse(null))
                                .playerPercentileBand(goal.getPlayerPercentileBand())
                                .bonus(goal.getBonus().orElse(BigInteger.ZERO))
                                .build();
                        DatabaseService.getCommanderDatabase().insert(cgGoal, DatabaseService.onConflictUpdate("id"));

                } catch (DatabaseException ex) {
                    log.error("Failed to add CommunityGoal to database", ex);
                }
            });
        });
        EliteMQService.sendCommunityGoal(communityGoals);
    }

    @Override
    public Class<CommunityGoal> getMessageClass() {
        return CommunityGoal.class;
    }
}
