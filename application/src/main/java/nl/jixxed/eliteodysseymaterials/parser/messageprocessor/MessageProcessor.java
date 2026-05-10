/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.persistence.commander.model.CommunityGoalModel;
import nl.jixxed.eliteodysseymaterials.persistence.common.model.StarSystemModel;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Event;
import nl.jixxed.eliteodysseymaterials.service.DatabaseService;

public interface MessageProcessor<T extends Event> {
    void process(final Object event);

    Class<T> getMessageClass();

    default void upsert(StarSystemModel starSystemModel) {
        String sql = """
                INSERT INTO star_system(address, name, x, y, z)
                VALUES (:address, :name, :x, :y, :z)
                ON CONFLICT(address) DO UPDATE SET
                  name = excluded.name,
                  x = excluded.x,
                  y = excluded.y,
                  z = excluded.z
                """;

        DatabaseService.getCommonDatabase().sqlUpdate(sql)
                .setParameter("address", starSystemModel.getAddress())
                .setParameter("name", starSystemModel.getName())
                .setParameter("x", starSystemModel.getX())
                .setParameter("y", starSystemModel.getY())
                .setParameter("z", starSystemModel.getZ())
                .execute();
    }

    default void upsert(CommunityGoalModel communityGoalModel) {
        String sql = """
                INSERT INTO community_goal (
                    id,
                    timestamp,
                    cgid,
                    title,
                    system_name,
                    market_name,
                    expiry,
                    is_complete,
                    current_total,
                    player_contribution,
                    num_contributors,
                    top_tier_name,
                    top_tier_bonus,
                    top_rank_size,
                    player_in_top_rank,
                    tier_reached,
                    player_percentile_band,
                    bonus
                )
                VALUES (
                    :id,
                    :timestamp,
                    :cgid,
                    :title,
                    :systemName,
                    :marketName,
                    :expiry,
                    :isComplete,
                    :currentTotal,
                    :playerContribution,
                    :numContributors,
                    :topTierName,
                    :topTierBonus,
                    :topRankSize,
                    :playerInTopRank,
                    :tierReached,
                    :playerPercentileBand,
                    :bonus
                )
                ON CONFLICT(id) DO UPDATE SET
                    timestamp = excluded.timestamp,
                    cgid = excluded.cgid,
                    title = excluded.title,
                    system_name = excluded.system_name,
                    market_name = excluded.market_name,
                    expiry = excluded.expiry,
                    is_complete = excluded.is_complete,
                    current_total = excluded.current_total,
                    player_contribution = excluded.player_contribution,
                    num_contributors = excluded.num_contributors,
                    top_tier_name = excluded.top_tier_name,
                    top_tier_bonus = excluded.top_tier_bonus,
                    top_rank_size = excluded.top_rank_size,
                    player_in_top_rank = excluded.player_in_top_rank,
                    tier_reached = excluded.tier_reached,
                    player_percentile_band = excluded.player_percentile_band,
                    bonus = excluded.bonus
                """;

        DatabaseService.getCommanderDatabase().sqlUpdate(sql)
                .setParameter("id", communityGoalModel.getId())
                .setParameter("timestamp", communityGoalModel.getTimestamp())
                .setParameter("cgid", communityGoalModel.getCgid())
                .setParameter("title", communityGoalModel.getTitle())
                .setParameter("systemName", communityGoalModel.getSystemName())
                .setParameter("marketName", communityGoalModel.getMarketName())
                .setParameter("expiry", communityGoalModel.getExpiry())
                .setParameter("isComplete", communityGoalModel.getIsComplete())
                .setParameter("currentTotal", communityGoalModel.getCurrentTotal())
                .setParameter("playerContribution", communityGoalModel.getPlayerContribution())
                .setParameter("numContributors", communityGoalModel.getNumContributors())
                .setParameter("topTierName", communityGoalModel.getTopTierName())
                .setParameter("topTierBonus", communityGoalModel.getTopTierBonus())
                .setParameter("topRankSize", communityGoalModel.getTopRankSize())
                .setParameter("playerInTopRank", communityGoalModel.getPlayerInTopRank())
                .setParameter("tierReached", communityGoalModel.getTierReached())
                .setParameter("playerPercentileBand", communityGoalModel.getPlayerPercentileBand())
                .setParameter("bonus", communityGoalModel.getBonus())
                .execute();
    }
}
