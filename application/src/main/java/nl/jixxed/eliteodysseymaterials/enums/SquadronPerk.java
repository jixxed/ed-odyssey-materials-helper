/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.enums;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum SquadronPerk {
    BONUS_MINING_FRAGMENT("bonusMiningFragment"),
    CARGO_INSURANCE("cargoInsurance"),
    COLONISATION_CLAIM_COST("colonisationClaimCost"),
    COLONISATION_TRADE_BOND("colonisationTradeBond"),
    CQC_EXP_GAIN("cqcExpGain"),
    DSS_EFFICIENCY("dssEfficiency"),
    EXP_GAIN("expGain"),
    FACTION_INFLUENCE("factionInfluence"),
    FSD_BOOST_SYNTHESIS("fsdBoostSynthesis"),
    LIMPET_SYNTHESIS("limpetSynthesis"),
    MISSION_REWARD_BOOST("missionRewardBoost"),
    NOTORIETY_DECAY("notorietyDecay"),
    NPC_CREW_EXP("npcCrewExp"),
    POWERPLAY_MERITS("powerplayMerits"),
    PREMIUM_AMMO_SYNTHESIS("premiumAmmoSynthesis"),
    SALE_WING_REWARDS("saleWingRewards"),
    VOUCHER_BOND_WING_REWARDS("voucherBondWingRewards"),
    UNKNOWN("");

    private final String id;

    public static SquadronPerk forId(final String searchId) {
        return Arrays.stream(SquadronPerk.values())
                .filter(perk -> perk.id.equalsIgnoreCase(searchId))
                .findFirst()
                .orElse(SquadronPerk.UNKNOWN);
    }

    public String getLocalizationKey() {
        return "squadron.perk.name." + this.name().toLowerCase();
    }
}
