/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

-- apply changes
create table community_goal (
                                id                            varchar(255) not null,
                                timestamp                     timestamp,
                                cgid                          integer,
                                title                         varchar(255),
                                system_name                   varchar(255),
                                market_name                   varchar(255),
                                expiry                        timestamp,
                                is_complete                   int,
                                current_total                 integer,
                                player_contribution           integer,
                                num_contributors              integer,
                                top_tier_name                 varchar(255),
                                top_tier_bonus                varchar(255),
                                top_rank_size                 integer,
                                player_in_top_rank            int,
                                tier_reached                  varchar(255),
                                player_percentile_band        integer,
                                bonus                         integer,
                                constraint pk_community_goal primary key (id)
);
CREATE INDEX IF NOT EXISTS idx_community_goal_timestamp ON community_goal(timestamp);
CREATE INDEX IF NOT EXISTS idx_community_goal_cgid ON community_goal(cgid);

