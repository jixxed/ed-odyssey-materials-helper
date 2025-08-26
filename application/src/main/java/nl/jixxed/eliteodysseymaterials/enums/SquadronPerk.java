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
    LIMPET_SYNTHESIS(""),
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
