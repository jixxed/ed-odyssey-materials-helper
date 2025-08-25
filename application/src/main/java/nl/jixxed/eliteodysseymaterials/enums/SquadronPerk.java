package nl.jixxed.eliteodysseymaterials.enums;

import lombok.RequiredArgsConstructor;

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
    FSD_SYNTHESIS(""),
    LIMPET_SYNTHESIS(""),
    MISSION_REWARD_BOOST("missionRewardBoost"),
    NOTORIETY_DECAY("notorietyDecay"),
    NPC_CREW_EXP("npcCrewExp"),
    POWERPLAY_MERITS("powerplayMerits"),
    PREMIUM_AMMO_SYNTHESIS("premiumAmmoSynthesis"),
    SALE_WING_REWARDS("saleWingRewards"),
    VOUCHER_BOND_WING_REWARDS("voucherBondWingRewards");

    private final String id;

//    primary perks
    // increased mining fragment yield - bonusMiningFragment
    // cargo insurance - cargoInsurance
    // reduced system colonisation claim cost (PRIMARY)
    // increased colonisation delivery profits (PRIMARY) - colonisationTradeBond
    // increased cqc rank gains (PRIMARY)
    // increased minimum map efficiency target - dssEfficiency
    // increased pilots' federation rank gains - expGain
    // increased faction influence gains - factionInfluence
    // reduced fsd synthesis costs (PRIMARY)
    // reduced limpet synthesis costs (PRIMARY)
    // increased mission rewards - missionRewardBoost
    // notoriety decay (PRIMARY) - notorietyDecay
    // increased npc crew rank gains
    // increased powerplay merity gains - powerplayMerits
    // reduced premium ammo synthesis costs (PRIMARY) - premiumAmmoSynthesis
    // increased wing trade rewards - saleWingRewards
    // increased wing combat rewards - voucherBondWingRewards
//    faction perks
    // increased mining fragment yield
    // cargo insurance
    // increased minimum map efficiency target
    // increased pilots' federation rank gains
    // increased faction influence gains
    // increased mission rewards
    // increased npc crew rank gains
    // increased powerplay merity gains
    // increased wing trade rewards
    // increased wing combat rewards


}
