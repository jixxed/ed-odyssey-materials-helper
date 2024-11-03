package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.ShieldGenerator;

import java.util.List;
import java.util.Map;
@Getter
public enum Power {
    ZEMINA_TORVAL(
            Map.of(
//    Zemina Torval: Perk: reduced rebuy when killed in your Power's Territory -> rank 2: -20%
//    Zemina Torval: Perk: mining commodity profits in Power Territory -> rank 5: +10%
//    Zemina Torval: Perk: reduced rebuy when killed in your Power's Territory -> rank 8: -40%
//    Zemina Torval: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 11: -33%
//    Zemina Torval: Perk: mining commodity profits in Power Territory -> rank 14: +15%
//    Zemina Torval: Perk: reduced rebuy when killed in your Power's Territory -> rank 17: -60%
//    Zemina Torval: Perk: mining commodity profits in Power Territory -> rank 22: +20%
//    Zemina Torval: Perk: Imperial slaves commodity profits in Power Territory -> rank 24: +10%
//    Zemina Torval: Perk: reduced rebuy when killed in your Power's Territory -> rank 27: -80%
//    Zemina Torval: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 29: -66%
//    Zemina Torval: Perk: mining commodity profits in Power Territory -> rank 32: +25%
//    Zemina Torval: Perk: reduced rebuy when killed in your Power's Territory -> rank 36: -100%
//    Zemina Torval: Perk: Imperial slaves commodity profits in Power Territory -> rank 42: +20%
//    Zemina Torval: Perk: mining commodity profits in Power Territory -> rank 48: +30%
//    Zemina Torval: Perk: Imperial slaves commodity profits in Power Territory -> rank 52: +30%
//    Zemina Torval: Perk: mining commodity profits in Power Territory -> rank 55: +35%
//    Zemina Torval: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 60: -100%
//    Zemina Torval: Perk: mining commodity profits in Power Territory -> rank 67: +40%
//    Zemina Torval: Perk: mining commodity profits in Power Territory -> rank 73: +43%
//    Zemina Torval: Perk: Imperial slaves commodity profits in Power Territory -> rank 78: +40%
//    Zemina Torval: Perk: Trade Bond on sales in Power territory -> rank 86: +10%
//    Zemina Torval: Perk: Imperial slaves commodity profits in Power Territory -> rank 94: +50%
//    Zemina Torval: Perk: Trade Bond on sales in Power territory -> rank 100: +20%

                    PowerPerk.INCREASE_MINING_COMMODITY_PAYOUT, List.of(
                            new RankReward(5, 10),
                            new RankReward(14, 15),
                            new RankReward(22, 20),
                            new RankReward(32, 25),
                            new RankReward(48, 30),
                            new RankReward(55, 35),
                            new RankReward(67, 40),
                            new RankReward(73, 43)
                    ),
                    PowerPerk.INCREASE_IMPERIAL_SLAVES_COMMODITY_PAYOUT, List.of(
                            new RankReward(24, 10),
                            new RankReward(42, 20),
                            new RankReward(52, 30),
                            new RankReward(78, 40),
                            new RankReward(94, 50)
                    ),
                    PowerPerk.INCREASE_TRADE_BOND_SALES_AWARD, List.of(
                            new RankReward(86, 10),
                            new RankReward(100, 20)
                    )
            ),
            Map.ofEntries(
                    Map.entry(MiningLaser.MINING_LANCE_BEAM_LASER_1_D_F, 34),
                    Map.entry(PlasmaAccelerator.ADVANCED_PLASMA_ACCELERATOR_3_B_F, 39),
                    Map.entry(RailGun.IMPERIAL_HAMMER_RAIL_GUN_2_B_F, 44),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_1_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_2_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_3_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_4_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_5_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_6_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_7_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_8_A, 50),
                    Map.entry(MissileRack.ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F, 57),
                    Map.entry(MissileRack.PACK_HOUND_MISSILE_RACK_2_B_F, 63),
                    Map.entry(MultiCannon.ENFORCER_CANNON_1_F_F, 70),
                    Map.entry(FragmentCannon.PACIFIER_FRAG_CANNON_3_C_F, 76),
                    Map.entry(BeamLaser.RETRIBUTOR_BEAM_LASER_1_E_F, 83),
                    Map.entry(BurstLaser.CYTOSCRAMBLER_BURST_LASER_1_F_F, 88),
                    Map.entry(PulseLaser.PULSE_DISRUPTOR_LASER_2_E_F, 91),
                    Map.entry(Cannon.CONCORD_CANNON_2_D_G, 97)
            ),
            new StarSystem("Synteini", 51.78, -76.41, 28.72)
    ),

    FELICIA_WINTERS(
            Map.of(
                    //Felicia Winters: Perk: reduced rebuy when killed in your Power's Territory -> rank 2: -20%
//Felicia Winters: Perk: Increase on salvage profits in Power Territory -> rank 5: +10%
//Felicia Winters: Perk: reduced rebuy when killed in your Power's Territory -> rank 8: -40%
//Felicia Winters: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 11: -33%
//Felicia Winters: Perk: Increase on salvage profits in Power Territory -> rank 14: +20%
//Felicia Winters: Perk: reduced rebuy when killed in your Power's Territory -> rank 17: -60%
//Felicia Winters: Perk: Increase on salvage profits in Power Territory -> rank 22: +33%
//Felicia Winters: Perk: Increase in minor faction reputation gain in Power Territory -> rank 24: +20%
//Felicia Winters: Perk: reduced rebuy when killed in your Power's Territory -> rank 27: -80%
//Felicia Winters: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 29: -66%
//Felicia Winters: Perk: Increase on salvage profits in Power Territory -> rank 32: +40%
//Felicia Winters: Perk: reduced rebuy when killed in your Power's Territory -> rank 36: -100%
//Felicia Winters: Perk: Increase in minor faction reputation gain in Power Territory -> rank 42: +40%
//Felicia Winters: Perk: Increase on salvage profits in Power Territory -> rank 48: +50%
//Felicia Winters: Perk: Increase in minor faction reputation gain in Power Territory -> rank 52: +60%
//Felicia Winters: Perk: Increase on salvage profits in Power Territory -> rank 55: +60%
//Felicia Winters: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 60: -100%
//Felicia Winters: Perk: Increase on salvage profits in Power Territory -> rank 67: +70%
//Felicia Winters: Perk: Increase on salvage profits in Power Territory -> rank 73: +80%
//Felicia Winters: Perk: increase on food  and medicine profits in Power Territory -> rank 73: +20%
//Felicia Winters: Perk: Increase in minor faction reputation gain in Power Territory -> rank 78: +80%
//Felicia Winters: Perk: Increase on salvage profits in Power Territory -> rank 86: +90%
//Felicia Winters: Perk: increase on food  and medicine profits in Power Territory -> rank 86: +40%
//Felicia Winters: Perk: Increase in minor faction reputation gain in Power Territory -> rank 94: +100%
//Felicia Winters: Perk: increase on food  and medicine profits in Power Territory -> rank 100: +60%

                    PowerPerk.INCREASE_SALVAGE_PROFIT, List.of(
                            new RankReward(5, 10),
                            new RankReward(14, 20),
                            new RankReward(22, 33),
                            new RankReward(32, 40),
                            new RankReward(48, 50),
                            new RankReward(55, 60),
                            new RankReward(67, 70),
                            new RankReward(73, 80),
                            new RankReward(86, 90)
                    ),
                    PowerPerk.INCREASE_MINOR_FACTION_REPUTATION, List.of(
                            new RankReward(24, 20),
                            new RankReward(42, 40),
                            new RankReward(52, 60),
                            new RankReward(78, 80),
                            new RankReward(94, 100)
                    ),
                    PowerPerk.INCREASE_FOOD_AND_MEDICINE_PROFIT, List.of(
                            new RankReward(73, 20),
                            new RankReward(86, 40),
                            new RankReward(100, 60)
                    )
            ),
            Map.ofEntries(
                    Map.entry(PulseLaser.PULSE_DISRUPTOR_LASER_2_E_F, 34),
                    Map.entry(FragmentCannon.PACIFIER_FRAG_CANNON_3_C_F, 39),
                    Map.entry(BeamLaser.RETRIBUTOR_BEAM_LASER_1_E_F, 44),
                    Map.entry(Cannon.CONCORD_CANNON_2_D_G, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_1_A, 57),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_2_A, 57),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_3_A, 57),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_4_A, 57),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_5_A, 57),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_6_A, 57),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_7_A, 57),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_8_A, 57),
                    Map.entry(MissileRack.PACK_HOUND_MISSILE_RACK_2_B_F, 63),
                    Map.entry(MissileRack.ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F, 70),
                    Map.entry(MultiCannon.ENFORCER_CANNON_1_F_F, 76),
                    Map.entry(PlasmaAccelerator.ADVANCED_PLASMA_ACCELERATOR_3_B_F, 83),
                    Map.entry(RailGun.IMPERIAL_HAMMER_RAIL_GUN_2_B_F, 88),
                    Map.entry(MiningLaser.MINING_LANCE_BEAM_LASER_1_D_F, 91),
                    Map.entry(BurstLaser.CYTOSCRAMBLER_BURST_LASER_1_F_F, 97)
            ),
            new StarSystem("Rhea", 58.13, 22.59, -28.59)
    ),

    LI_YONG_RUI(
            Map.of(
                    //Li Yong-Rui: Perk: reduced rebuy when killed in your Power's Territory -> rank 2: -20%
//Li Yong-Rui: Perk: Exploration Data Sales -> rank 5: +75%
//Li Yong-Rui: Perk: reduced rebuy when killed in your Power's Territory -> rank 8: -40%
//Li Yong-Rui: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 11: -33%
//Li Yong-Rui: Perk: Exploration Data Sales -> rank 14: +90%
//Li Yong-Rui: Perk: reduced rebuy when killed in your Power's Territory -> rank 17: -60%
//Li Yong-Rui: Perk: Exploration Data Sales -> rank 22: +105%
//Li Yong-Rui: Perk: Trade Profits on sales -> rank 24: +5%
//Li Yong-Rui: Perk: reduced rebuy when killed in your Power's Territory -> rank 27: -80%
//Li Yong-Rui: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 29: -66%
//Li Yong-Rui: Perk: Exploration Data Sales -> rank 32: +120%
//Li Yong-Rui: Perk: reduced rebuy when killed in your Power's Territory -> rank 36: -100%
//Li Yong-Rui: Perk: Trade Profits on sales -> rank 42: +10%
//Li Yong-Rui: Perk: Exploration Data Sales -> rank 48: +135%
//Li Yong-Rui: Perk: Trade Profits on sales -> rank 52: +15%
//Li Yong-Rui: Perk: Exploration Data Sales -> rank 55: +150%
//Li Yong-Rui: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 60: -100%
//Li Yong-Rui: Perk: Exploration Data Sales -> rank 67: +165%
//Li Yong-Rui: Perk: Exploration Data Sales -> rank 73: +180%
//Li Yong-Rui: Perk: Trade Profits on sales -> rank 78: +20%
//Li Yong-Rui: Perk: Exploration Data Sales -> rank 86: +195%
//Li Yong-Rui: Perk: Rearm Refuel Repair prices -> rank 86: -50%
//Li Yong-Rui: Perk: Trade Profits on sales -> rank 94: +25%
//Li Yong-Rui: Perk: Exploration Data Sales -> rank 100: +210%
//Li Yong-Rui: Perk: Rearm Refuel Repair prices -> rank 100: -100%

                    PowerPerk.INCREASE_TRADE_BOND_SALES_AWARD, List.of(
                            new RankReward(24, 5),
                            new RankReward(42, 10),
                            new RankReward(52, 15),
                            new RankReward(78, 20),
                            new RankReward(94, 25)
                    ),
                    PowerPerk.INCREASE_EXPLORATION_DATA_SALES, List.of(
                            new RankReward(5, 20),
                            new RankReward(14, 30),
                            new RankReward(22, 40),
                            new RankReward(32, 50),
                            new RankReward(48, 60),
                            new RankReward(55, 70),
                            new RankReward(67, 80),
                            new RankReward(73, 90),
                            new RankReward(86, 100)

                    ),
                    PowerPerk.REDUCED_REARM_PRICES_IN_OWN_TERRITORY, List.of(
                            new RankReward(86, -50),
                            new RankReward(100, -100)
                    )

            ),
            Map.ofEntries(
                    Map.entry(MissileRack.PACK_HOUND_MISSILE_RACK_2_B_F, 34),
                    Map.entry(MultiCannon.ENFORCER_CANNON_1_F_F, 39),
                    Map.entry(BeamLaser.RETRIBUTOR_BEAM_LASER_1_E_F, 44),
                    Map.entry(Cannon.CONCORD_CANNON_2_D_G, 50),
                    Map.entry(PulseLaser.PULSE_DISRUPTOR_LASER_2_E_F, 57),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_1_A, 63),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_2_A, 63),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_3_A, 63),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_4_A, 63),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_5_A, 63),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_6_A, 63),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_7_A, 63),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_8_A, 63),
                    Map.entry(MissileRack.ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F, 70),
                    Map.entry(RailGun.IMPERIAL_HAMMER_RAIL_GUN_2_B_F, 76),
                    Map.entry(MiningLaser.MINING_LANCE_BEAM_LASER_1_D_F, 83),
                    Map.entry(BurstLaser.CYTOSCRAMBLER_BURST_LASER_1_F_F, 88),
                    Map.entry(FragmentCannon.PACIFIER_FRAG_CANNON_3_C_F, 91),
                    Map.entry(PlasmaAccelerator.ADVANCED_PLASMA_ACCELERATOR_3_B_F, 97)
            ),
            new StarSystem("Lembava", -43.25, -64.34, -77.69)
    ),
    ARCHON_DELAINE(
            Map.of(
//Archon Delaine: Perk: reduced rebuy when killed in your Power's Territory -> rank 2: -20%
//Archon Delaine: Perk: on Bounties placed on you -> rank 5: -10%
//Archon Delaine: Perk: reduced rebuy when killed in your Power's Territory -> rank 8: -40%
//Archon Delaine: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 11: -33%
//Archon Delaine: Perk: on Bounties placed on you -> rank 14: -20%
//Archon Delaine: Perk: reduced rebuy when killed in your Power's Territory -> rank 17: -60%
//Archon Delaine: Perk: on Bounties placed on you -> rank 22: -33%
//Archon Delaine: Perk: Black Market Profits on sales -> rank 24: +10%
//Archon Delaine: Perk: reduced rebuy when killed in your Power's Territory -> rank 27: -80%
//Archon Delaine: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 29: -66%
//Archon Delaine: Perk: on Bounties placed on you -> rank 32: -40%
//Archon Delaine: Perk: reduced rebuy when killed in your Power's Territory -> rank 36: -100%
//Archon Delaine: Perk: Black Market Profits on sales -> rank 42: +15%
//Archon Delaine: Perk: on Bounties placed on you -> rank 48: -50%
//Archon Delaine: Perk: Black Market Profits on sales -> rank 52: +20%
//Archon Delaine: Perk: on Bounties placed on you -> rank 55: -60%
//Archon Delaine: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 60: -100%
//Archon Delaine: Perk: on Bounties placed on you -> rank 67: -70%
//Archon Delaine: Perk: on Bounties placed on you -> rank 73: -80%
//Archon Delaine: Perk: Black Market Profits on sales -> rank 78: +25%
//Archon Delaine: Perk: on Bounties placed on you -> rank 86: +90%
//Archon Delaine: Perk: on Bounties placed on you -> rank 100: -100%

                    PowerPerk.REDUCED_BOUNTY_VALUE_IN_OWN_TERRITORY, List.of(
                            new RankReward(5, -10),
                            new RankReward(14, -20),
                            new RankReward(22, -33),
                            new RankReward(32, -40),
                            new RankReward(48, -50),
                            new RankReward(55, -60),
                            new RankReward(67, -70),
                            new RankReward(73, -80),
                            new RankReward(86, -80),
                            new RankReward(100, -100)
                    ),
                    PowerPerk.INCREASE_BLACK_MARKET_PROFITS, List.of(
                            new RankReward(24, 10),
                            new RankReward(42, 15),
                            new RankReward(52, 20),
                            new RankReward(78, 25)
                    )
            ),
            Map.ofEntries(
//                    34	Cytoscrambler Burst Laser
//                    39	Rocket Propelled Containment Missile
//                    44	Pacifier Frag-Cannon
//                    50	Pulse Disruptor Laser
//                    57	Advanced Plasma Accelerator
//                    63	Pack-Hound Missile Rack
//                    70	Mining Lance Beam Laser
//                    76	Enforcer Cannon
//                    83	Prismatic Shield Generator
//                    88	Concord Cannon
//                    91	Imperial Hammer Rail Gun
//                    97	Retributor Beam Laser
                    Map.entry(BurstLaser.CYTOSCRAMBLER_BURST_LASER_1_F_F, 34),
                    Map.entry(MissileRack.ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F, 39),
                    Map.entry(FragmentCannon.PACIFIER_FRAG_CANNON_3_C_F, 44),
                    Map.entry(PulseLaser.PULSE_DISRUPTOR_LASER_2_E_F, 50),
                    Map.entry(PlasmaAccelerator.ADVANCED_PLASMA_ACCELERATOR_3_B_F, 57),
                    Map.entry(MissileRack.PACK_HOUND_MISSILE_RACK_2_B_F, 63),
                    Map.entry(MiningLaser.MINING_LANCE_BEAM_LASER_1_D_F, 70),
                    Map.entry(MultiCannon.ENFORCER_CANNON_1_F_F, 76),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_1_A, 83),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_2_A, 83),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_3_A, 83),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_4_A, 83),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_5_A, 83),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_6_A, 83),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_7_A, 83),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_8_A, 83),
                    Map.entry(Cannon.CONCORD_CANNON_2_D_G, 88),
                    Map.entry(RailGun.IMPERIAL_HAMMER_RAIL_GUN_2_B_F, 91),
                    Map.entry(BeamLaser.RETRIBUTOR_BEAM_LASER_1_E_F, 97)
            ),
            new StarSystem("Harma", -99.25, -100.97, 20.41)
    ),
    NAKATO_KAINE(
            Map.of(
                    //Nakato Kaine: Perk: reduced rebuy when killed in your Power's Territory -> rank 2: -20%
//Nakato Kaine: Perk: Increase in Minor Faction Reputation Gain -> rank 5: +75%
//Nakato Kaine: Perk: reduced rebuy when killed in your Power's Territory -> rank 8: -40%
//Nakato Kaine: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 11: -33%
//Nakato Kaine: Perk: Increase in Minor Faction Reputation Gain -> rank 14: +90%
//Nakato Kaine: Perk: reduced rebuy when killed in your Power's Territory -> rank 17: -60%
//Nakato Kaine: Perk: Increase in Minor Faction Reputation Gain -> rank 22: +105%
//Nakato Kaine: Perk: Search and Rescue Payouts -> rank 24: +40%
//Nakato Kaine: Perk: reduced rebuy when killed in your Power's Territory -> rank 27: -80%
//Nakato Kaine: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 29: -66%
//Nakato Kaine: Perk: Increase in Minor Faction Reputation Gain -> rank 32: +120%
//Nakato Kaine: Perk: reduced rebuy when killed in your Power's Territory -> rank 36: -100%
//Nakato Kaine: Perk: Search and Rescue Payouts -> rank 42: +60%
//Nakato Kaine: Perk: Increase in Minor Faction Reputation Gain -> rank 48: 135%
//Nakato Kaine: Perk: Search and Rescue Payouts -> rank 52: +80%
//Nakato Kaine: Perk: Increase in Minor Faction Reputation Gain -> rank 55: +150%
//Nakato Kaine: Perk: Mining Commodity Profits -> rank 55: +10%
//Nakato Kaine: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 60: -100%
//Nakato Kaine: Perk: Mining Commodity Profits -> rank 67: +20%
//Nakato Kaine: Perk: Mining Commodity Profits -> rank 73: +30%
//Nakato Kaine: Perk: Search and Rescue Payouts -> rank 78: +100%
//Nakato Kaine: Perk: Mining Commodity Profits -> rank 86: +40%
//Nakato Kaine: Perk: Search and Rescue Payouts -> rank 94: +120%
//Nakato Kaine: Perk: Mining Commodity Profits -> rank 100: +50%

                    PowerPerk.INCREASE_MINOR_FACTION_REPUTATION, List.of(
                            new RankReward(5, 75),
                            new RankReward(14, 90),
                            new RankReward(22, 105),
                            new RankReward(32, 120),
                            new RankReward(48, 135),
                            new RankReward(55, 150)
                    ),
                    PowerPerk.INCREASE_SEARCH_AND_RESCUE_PAYOUT, List.of(
                            new RankReward(24, 40),
                            new RankReward(42, 60),
                            new RankReward(52, 80),
                            new RankReward(78, 100),
                            new RankReward(94, 120)
                    ),
                    PowerPerk.INCREASE_MINING_COMMODITY_PAYOUT, List.of(
                            new RankReward(55, 10),
                            new RankReward(67, 20),
                            new RankReward(73, 30),
                            new RankReward(86, 40),
                            new RankReward(100, 50)
                    )
            ),
            Map.ofEntries(
//                    34	Concord Cannon
//                    39	Retributor Beam Laser
//                    44	Pack-Hound Missile Rack
//                    50	Pulse Disruptor Laser
//                    57	Enforcer Cannon
//                    63	Rocket Propelled Containment Missile
//                    70	Prismatic Shield Generator
//                    76	Imperial Hammer Rail Gun
//                    83	Advanced Plasma Accelerator
//                    88	Pacifier Frag-Cannon
//                    91	Cytoscrambler Burst Laser
//                    97	Mining Lance Beam Laser
                    Map.entry(Cannon.CONCORD_CANNON_2_D_G, 34),
                    Map.entry(BeamLaser.RETRIBUTOR_BEAM_LASER_1_E_F, 39),
                    Map.entry(MissileRack.PACK_HOUND_MISSILE_RACK_2_B_F, 44),
                    Map.entry(PulseLaser.PULSE_DISRUPTOR_LASER_2_E_F, 50),
                    Map.entry(MultiCannon.ENFORCER_CANNON_1_F_F, 57),
                    Map.entry(MissileRack.ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F, 63),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_1_A, 70),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_2_A, 70),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_3_A, 70),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_4_A, 70),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_5_A, 70),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_6_A, 70),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_7_A, 70),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_8_A, 70),
                    Map.entry(RailGun.IMPERIAL_HAMMER_RAIL_GUN_2_B_F, 76),
                    Map.entry(PlasmaAccelerator.ADVANCED_PLASMA_ACCELERATOR_3_B_F, 83),
                    Map.entry(FragmentCannon.PACIFIER_FRAG_CANNON_3_C_F, 88),
                    Map.entry(BurstLaser.CYTOSCRAMBLER_BURST_LASER_1_F_F, 91),
                    Map.entry(MiningLaser.MINING_LANCE_BEAM_LASER_1_D_F, 97)

            ),
            new StarSystem("Tionisla", 82.25, 48.75, 68.16)
    ),
    JEROME_ARCHER(
            Map.of(
                    //Jerome Archer: Perk: reduced rebuy when killed in your Power's Territory -> rank 2: -20%
//Jerome Archer: Perk: Bounty Payouts -> rank 5: +10%
//Jerome Archer: Perk: reduced rebuy when killed in your Power's Territory -> rank 8: -40%
//Jerome Archer: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 11: -33%
//Jerome Archer: Perk: Bounty Payouts -> rank 14: +20%
//Jerome Archer: Perk: reduced rebuy when killed in your Power's Territory -> rank 17: -60%
//Jerome Archer: Perk: Bounty Payouts -> rank 22: +30%
//Jerome Archer: Perk: Weapon Module Cost -> rank 24: +10%
//Jerome Archer: Perk: reduced rebuy when killed in your Power's Territory -> rank 27: -80%
//Jerome Archer: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 29: -66%
//Jerome Archer: Perk: Bounty Payouts -> rank 32: +40%
//Jerome Archer: Perk: reduced rebuy when killed in your Power's Territory -> rank 36: -100%
//Jerome Archer: Perk: Weapon Module Cost -> rank 42: -15%
//Jerome Archer: Perk: Bounty Payouts -> rank 48: +50%
//Jerome Archer: Perk: Weapon Module Cost -> rank 52: -20%
//Jerome Archer: Perk: Bounty Payouts -> rank 55: +60%
//Jerome Archer: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 60: -100%
//Jerome Archer: Perk: Bounty Payouts -> rank 67: +70%
//Jerome Archer: Perk: Bounty Payouts -> rank 73: +80%
//Jerome Archer: Perk: Weapon Module Cost -> rank 78: -25%
//Jerome Archer: Perk: Bounty Payouts -> rank 86: +90%
//Jerome Archer: Perk: Weapon Module Cost -> rank 94: -30%
//Jerome Archer: Perk: Bounty Payouts -> rank 100: +100%

                    PowerPerk.INCREASE_BOUNTY_PAYOUT, List.of(
                            new RankReward(5, 10),
                            new RankReward(14, 20),
                            new RankReward(22, 30),
                            new RankReward(32, 40),
                            new RankReward(48, 50),
                            new RankReward(55, 60),
                            new RankReward(67, 70),
                            new RankReward(73, 80),
                            new RankReward(86, 90),
                            new RankReward(100, 100)
                    ),
                    PowerPerk.REDUCED_WEAPON_MODULE_COST_IN_OWN_TERRITORY, List.of(
                            new RankReward(24, -10),
                            new RankReward(42, -15),
                            new RankReward(52, -20),
                            new RankReward(78, -25),
                            new RankReward(94, -30)
                    )
            ),
            Map.ofEntries(
//                    34	Pacifier Frag-Cannon
//                    39	Pulse Disruptor Laser
//                    44	Rocket Propelled Containment Missile
//                    50	Enforcer Cannon
//                    57	Pack-Hound Missile Rack
//                    63	Concord Cannon
//                    70	Advanced Plasma Accelerator
//                    76	Cytoscrambler Burst Laser
//                    83	Prismatic Shield Generator
//                    88	Imperial Hammer Rail Gun
//                    91	Retributor Beam Laser
//                    97	Mining Lance Beam Laser
                    Map.entry(FragmentCannon.PACIFIER_FRAG_CANNON_3_C_F, 34),
                    Map.entry(PulseLaser.PULSE_DISRUPTOR_LASER_2_E_F, 39),
                    Map.entry(MissileRack.ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F, 44),
                    Map.entry(MultiCannon.ENFORCER_CANNON_1_F_F, 50),
                    Map.entry(MissileRack.PACK_HOUND_MISSILE_RACK_2_B_F, 57),
                    Map.entry(Cannon.CONCORD_CANNON_2_D_G, 63),
                    Map.entry(PlasmaAccelerator.ADVANCED_PLASMA_ACCELERATOR_3_B_F, 70),
                    Map.entry(BurstLaser.CYTOSCRAMBLER_BURST_LASER_1_F_F, 76),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_1_A, 83),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_2_A, 83),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_3_A, 83),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_4_A, 83),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_5_A, 83),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_6_A, 83),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_7_A, 83),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_8_A, 83),
                    Map.entry(RailGun.IMPERIAL_HAMMER_RAIL_GUN_2_B_F, 88),
                    Map.entry(BeamLaser.RETRIBUTOR_BEAM_LASER_1_E_F, 91),
                    Map.entry(MiningLaser.MINING_LANCE_BEAM_LASER_1_D_F, 97)
            ),
            new StarSystem("Nanomam", -14.78, 19.66, -15.25)
    ),
    DENTON_PATREUS(
            Map.of(
                    //Patreus: Perk: reduced rebuy when killed in your Power's Territory -> rank 2: -20%
//Patreus: Perk: Weapon Module Cost -> rank 5: -5%
//Patreus: Perk: reduced rebuy when killed in your Power's Territory -> rank 8: -40%
//Patreus: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 11: -33%
//Patreus: Perk: Weapon Module Cost -> rank 14: -10%
//Patreus: Perk: reduced rebuy when killed in your Power's Territory -> rank 17: -60%
//Patreus: Perk: Weapon Module Cost -> rank 22: -15%
//Patreus: Perk: Bounty Payouts -> rank 24: +20%
//Patreus: Perk: reduced rebuy when killed in your Power's Territory -> rank 27: -80%
//Patreus: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 29: -66%
//Patreus: Perk: Weapon Module Cost -> rank 32: -20%
//Patreus: Perk: reduced rebuy when killed in your Power's Territory -> rank 36: -100%
//Patreus: Perk: Bounty Payouts -> rank 42: +35%
//Patreus: Perk: Weapon Module Cost -> rank 48: -25%
//Patreus: Perk: Bounty Payouts -> rank 52: +50%
//Patreus: Perk: Weapon Module Cost -> rank 55: -30%
//Patreus: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 60: -100%
//Patreus: Perk: Weapon Module Cost -> rank 67: -35%
//Patreus: Perk: Weapon Module Cost -> rank 73: -40%
//Patreus: Perk: Bounty Payouts -> rank 78: +65%
//Patreus: Perk: Rearm Prices -> rank 86: -40%
//Patreus: Perk: Bounty Payouts -> rank 94: +80%
//Patreus: Perk: Rearm Prices -> rank 100: -90%

                    PowerPerk.REDUCED_WEAPON_MODULE_COST_IN_OWN_TERRITORY, List.of(
                            new RankReward(5, -5),
                            new RankReward(14, -10),
                            new RankReward(22, -15),
                            new RankReward(32, -20),
                            new RankReward(48, -25),
                            new RankReward(55, -30),
                            new RankReward(67, -35),
                            new RankReward(73, -40)
                    ),
                    PowerPerk.INCREASE_BOUNTY_PAYOUT, List.of(
                            new RankReward(24, 20),
                            new RankReward(42, 35),
                            new RankReward(52, 50),
                            new RankReward(78, 65),
                            new RankReward(94, 80)
                    ),
                    PowerPerk.REDUCED_REARM_PRICES_IN_OWN_TERRITORY, List.of(
                            new RankReward(86, -40),
                            new RankReward(100, -90)
                    )
            ),
            Map.ofEntries(
//                    34	Advanced Plasma Accelerator
//                    39	Mining Lance Beam Laser
//                    44	Imperial Hammer Rail Gun
//                    50	Prismatic Shield Generator
//                    57	Pack-Hound Missile Rack
//                    63	Rocket Propelled Containment Missile
//                    70	Retributor Beam Laser
//                    76	Enforcer Cannon
//                    83	Concord Cannon
//                    88	Pacifier Frag-Cannon
//                    91	Pulse Disruptor Laser
//                    97	Cytoscrambler Burst Laser
                    Map.entry(PlasmaAccelerator.ADVANCED_PLASMA_ACCELERATOR_3_B_F, 34),
                    Map.entry(MiningLaser.MINING_LANCE_BEAM_LASER_1_D_F, 39),
                    Map.entry(RailGun.IMPERIAL_HAMMER_RAIL_GUN_2_B_F, 44),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_1_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_2_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_3_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_4_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_5_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_6_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_7_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_8_A, 50),
                    Map.entry(MissileRack.PACK_HOUND_MISSILE_RACK_2_B_F, 57),
                    Map.entry(MissileRack.ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F, 63),
                    Map.entry(BeamLaser.RETRIBUTOR_BEAM_LASER_1_E_F, 70),
                    Map.entry(MultiCannon.ENFORCER_CANNON_1_F_F, 76),
                    Map.entry(Cannon.CONCORD_CANNON_2_D_G, 83),
                    Map.entry(FragmentCannon.PACIFIER_FRAG_CANNON_3_C_F, 88),
                    Map.entry(PulseLaser.PULSE_DISRUPTOR_LASER_2_E_F, 91),
                    Map.entry(BurstLaser.CYTOSCRAMBLER_BURST_LASER_1_F_F, 97)
            ),
            new StarSystem("Eotienses", 49.5, -104.03, 6.31)
    ),
    PRANAV_ANTAL(
            Map.of(
                    //Pranav Antal: Perk: reduced rebuy when killed in your Power's Territory -> rank 2: -20%
//Pranav Antal: Perk: Minor Faction Reputation Gain -> rank 5: +75%
//Pranav Antal: Perk: reduced rebuy when killed in your Power's Territory -> rank 8: -40%
//Pranav Antal: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 11: -33%
//Pranav Antal: Perk: Minor Faction Reputation Gain -> rank 14: +90%
//Pranav Antal: Perk: reduced rebuy when killed in your Power's Territory -> rank 17: -60%
//Pranav Antal: Perk: Minor Faction Reputation Gain -> rank 22: +105%
//Pranav Antal: Perk: Organics Data Sales -> rank 24: +10%
//Pranav Antal: Perk: reduced rebuy when killed in your Power's Territory -> rank 27: -80%
//Pranav Antal: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 29: -66%
//Pranav Antal: Perk: Minor Faction Reputation Gain -> rank 32: +120%
//Pranav Antal: Perk: reduced rebuy when killed in your Power's Territory -> rank 36: -100%
//Pranav Antal: Perk: Organics Data Sales -> rank 42: +20
//Pranav Antal: Perk: Minor Faction Reputation Gain -> rank 48: +135%
//Pranav Antal: Perk: Organics Data Sales -> rank 52: +30%
//Pranav Antal: Perk: Minor Faction Reputation Gain -> rank 55: 150%
//Pranav Antal: Perk: Technology Commodity Profits -> rank 55: +10%
//Pranav Antal: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 60: -100%
//Pranav Antal: Perk: Technology Commodity Profits -> rank 67: +20%
//Pranav Antal: Perk: Technology Commodity Profits -> rank 73: +30%
//Pranav Antal: Perk: Organics Data Sales -> rank 78: +40%
//Pranav Antal: Perk: Technology Commodity Profits -> rank 86: +40%
//Pranav Antal: Perk: Organics Data Sales -> rank 94: +50%
//Pranav Antal: Perk: Technology Commodity Profits -> rank 100: +50%

                    PowerPerk.INCREASE_MINOR_FACTION_REPUTATION, List.of(
                            new RankReward(5, 75),
                            new RankReward(14, 90),
                            new RankReward(22, 105),
                            new RankReward(32, 120),
                            new RankReward(48, 135),
                            new RankReward(55, 150)
                    ),
                    PowerPerk.INCREASE_ORGANICS_DATA_SALES, List.of(
                            new RankReward(24, 10),
                            new RankReward(42, 20),
                            new RankReward(52, 30),
                            new RankReward(78, 40),
                            new RankReward(94, 50)
                    ),
                    PowerPerk.INCREASE_TECHNOLOGY_COMMODITY_PROFIT, List.of(
                            new RankReward(55, 10),
                            new RankReward(67, 20),
                            new RankReward(73, 30),
                            new RankReward(86, 40),
                            new RankReward(100, 50)
                    )

            ),
            Map.ofEntries(
//                    34	Enforcer Cannon
//                    39	Pack-Hound Missile Rack
//                    44	Rocket Propelled Containment Missile
//                    50	Pulse Disruptor Laser
//                    57	Mining Lance Beam Laser
//                    63	Retributor Beam Laser
//                    70	Concord Cannon
//                    76	Cytoscrambler Burst Laser
//                    83	Pacifier Frag-Cannon
//                    88	Prismatic Shield Generator
//                    91	Imperial Hammer Rail Gun
//                    97	Advanced Plasma Accelerator
                    Map.entry(MultiCannon.ENFORCER_CANNON_1_F_F, 34),
                    Map.entry(MissileRack.PACK_HOUND_MISSILE_RACK_2_B_F, 39),
                    Map.entry(MissileRack.ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F, 44),
                    Map.entry(PulseLaser.PULSE_DISRUPTOR_LASER_2_E_F, 50),
                    Map.entry(MiningLaser.MINING_LANCE_BEAM_LASER_1_D_F, 57),
                    Map.entry(BeamLaser.RETRIBUTOR_BEAM_LASER_1_E_F, 63),
                    Map.entry(Cannon.CONCORD_CANNON_2_D_G, 70),
                    Map.entry(BurstLaser.CYTOSCRAMBLER_BURST_LASER_1_F_F, 76),
                    Map.entry(FragmentCannon.PACIFIER_FRAG_CANNON_3_C_F, 83),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_1_A, 88),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_2_A, 88),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_3_A, 88),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_4_A, 88),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_5_A, 88),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_6_A, 88),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_7_A, 88),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_8_A, 88),
                    Map.entry(RailGun.IMPERIAL_HAMMER_RAIL_GUN_2_B_F, 91),
                    Map.entry(PlasmaAccelerator.ADVANCED_PLASMA_ACCELERATOR_3_B_F, 97)

            ),
            new StarSystem("Polevnic", -79.91, -87.47, -33.53)
    ),
    YURI_GROM(
            Map.of(
                    //Yuri Grom: Perk: reduced rebuy when killed in your Power's Territory -> rank 2: -20%
//Yuri Grom: Perk: Bounty Payout -> rank 5: +2%
//Yuri Grom: Perk: Exploration Data Sales -> rank 5: +2%
//Yuri Grom: Perk: Trade Bond of +X% of Trade Profit on  Sales -> rank 5: +2%
//Yuri Grom: Perk: reduced rebuy when killed in your Power's Territory -> rank 8: -40%
//Yuri Grom: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 11: -33%
//Yuri Grom: Perk: Bounty Payout -> rank 14: +5%
//Yuri Grom: Perk: Exploration Data Sales -> rank 14: +5%
//Yuri Grom: Perk: Trade Bond of +X% of Trade Profit on  Sales -> rank 14: "+5%
//Yuri Grom: Perk: reduced rebuy when killed in your Power's Territory -> rank 17: -60%
//Yuri Grom: Perk: Bounty Payout -> rank 22: +7%
//Yuri Grom: Perk: Exploration Data Sales -> rank 22: +7%
//Yuri Grom: Perk: Trade Bond of +X% of Trade Profit on  Sales -> rank 22: +7%
//Yuri Grom: Perk: Weapon Module Cost -> rank 24: -10%
//Yuri Grom: Perk: reduced rebuy when killed in your Power's Territory -> rank 27: -80%
//Yuri Grom: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 29: -66%
//Yuri Grom: Perk: Bounty Payout -> rank 32: +10%
//Yuri Grom: Perk: Exploration Data Sales -> rank 32: +10%
//Yuri Grom: Perk: Trade Bond of +X% of Trade Profit on  Sales -> rank 32: +10%
//Yuri Grom: Perk: reduced rebuy when killed in your Power's Territory -> rank 36: -100%
//Yuri Grom: Perk: Weapon Module Cost -> rank 42: -15%
//Yuri Grom: Perk: Bounty Payout -> rank 48: +13%
//Yuri Grom: Perk: Exploration Data Sales -> rank 48: +13%
//Yuri Grom: Perk: Trade Bond of +X% of Trade Profit on  Sales -> rank 48: +13%
//Yuri Grom: Perk: Weapon Module Cost -> rank 52: -20%
//Yuri Grom: Perk: Bounty Payout -> rank 55: +15%
//Yuri Grom: Perk: Exploration Data Sales -> rank 55: +15%
//Yuri Grom: Perk: Trade Bond of +X% of Trade Profit on  Sales -> rank 55: +15%
//Yuri Grom: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 60: -100%
//Yuri Grom: Perk: Bounty Payout -> rank 67: +20%
//Yuri Grom: Perk: Bounty Payout -> rank 73: +30%
//Yuri Grom: Perk: Weapon Module Cost -> rank 78: -25%
//Yuri Grom: Perk: Bounty Payout -> rank 86: +40%
//Yuri Grom: Perk: Weapon Module Cost -> rank 94: -30%
//Yuri Grom: Perk: Bounty Payout -> rank 100: +60%

                    PowerPerk.INCREASE_BOUNTY_PAYOUT, List.of(
                            new RankReward(5, 2),
                            new RankReward(14, 5),
                            new RankReward(22, 7),
                            new RankReward(32, 10),
                            new RankReward(48, 13),
                            new RankReward(55, 15),
                            new RankReward(67, 20),
                            new RankReward(73, 30),
                            new RankReward(86, 40),
                            new RankReward(100, 60)
                    ),
                    PowerPerk.INCREASE_EXPLORATION_DATA_SALES, List.of(
                            new RankReward(5, 2),
                            new RankReward(14, 5),
                            new RankReward(22, 7),
                            new RankReward(32, 10),
                            new RankReward(48, 13),
                            new RankReward(55, 15)
                    ),
                    PowerPerk.INCREASE_TRADE_BOND_SALES_AWARD, List.of(
                            new RankReward(5, 2),
                            new RankReward(14, 5),
                            new RankReward(22, 7),
                            new RankReward(32, 10),
                            new RankReward(48, 13),
                            new RankReward(55, 15)
                    ),
                    PowerPerk.REDUCED_WEAPON_MODULE_COST_IN_OWN_TERRITORY, List.of(
                            new RankReward(24, -10),
                            new RankReward(42, -15),
                            new RankReward(52, -20),
                            new RankReward(78, -25),
                            new RankReward(94, -30)
                    )
            ),
            Map.ofEntries(
//                    34	Rocket Propelled Containment Missile
//                    39	Pack-Hound Missile Rack
//                    44	Advanced Plasma Accelerator
//                    50	Enforcer Cannon
//                    57	Pacifier Frag-Cannon
//                    63	Pulse Disruptor Laser
//                    70	Prismatic Shield Generator
//                    76	Imperial Hammer Rail Gun
//                    83	Retributor Beam Laser
//                    88	Concord Cannon
//                    91	Mining Lance Beam Laser
//                    97	Cytoscrambler Burst Laser
                    Map.entry(MissileRack.ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F, 34),
                    Map.entry(MissileRack.PACK_HOUND_MISSILE_RACK_2_B_F, 39),
                    Map.entry(PlasmaAccelerator.ADVANCED_PLASMA_ACCELERATOR_3_B_F, 44),
                    Map.entry(MultiCannon.ENFORCER_CANNON_1_F_F, 50),
                    Map.entry(FragmentCannon.PACIFIER_FRAG_CANNON_3_C_F, 57),
                    Map.entry(PulseLaser.PULSE_DISRUPTOR_LASER_2_E_F, 63),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_1_A, 70),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_2_A, 70),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_3_A, 70),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_4_A, 70),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_5_A, 70),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_6_A, 70),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_7_A, 70),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_8_A, 70),
                    Map.entry(RailGun.IMPERIAL_HAMMER_RAIL_GUN_2_B_F, 76),
                    Map.entry(BeamLaser.RETRIBUTOR_BEAM_LASER_1_E_F, 83),
                    Map.entry(Cannon.CONCORD_CANNON_2_D_G, 88),
                    Map.entry(MiningLaser.MINING_LANCE_BEAM_LASER_1_D_F, 91),
                    Map.entry(BurstLaser.CYTOSCRAMBLER_BURST_LASER_1_F_F, 97)

            ),
            new StarSystem("Euryale", 35.38, -68.97, 24.81)
    ),
    A_LAVIGNY_DUVAL(
            Map.of(
                    //A. Lavingy-Duval: Perk: reduced rebuy when killed in your Power's Territory -> rank 2: -20%
//A. Lavingy-Duval: Perk: Bounty Payout -> rank 5: +10%
//A. Lavingy-Duval: Perk: reduced rebuy when killed in your Power's Territory -> rank 8: -40%
//A. Lavingy-Duval: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 11: -33%
//A. Lavingy-Duval: Perk: Bounty Payout -> rank 14: +20%
//A. Lavingy-Duval: Perk: reduced rebuy when killed in your Power's Territory -> rank 17: -60%
//A. Lavingy-Duval: Perk: Bounty Payout -> rank 22: +30%
//A. Lavingy-Duval: Perk: Weapon Module Cost -> rank 24: -10%
//A. Lavingy-Duval: Perk: reduced rebuy when killed in your Power's Territory -> rank 27: -80%
//A. Lavingy-Duval: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 29: -66%
//A. Lavingy-Duval: Perk: Bounty Payout -> rank 32: +40%
//A. Lavingy-Duval: Perk: reduced rebuy when killed in your Power's Territory -> rank 36: -100%
//A. Lavingy-Duval: Perk: Weapon Module Cost -> rank 42: -15%
//A. Lavingy-Duval: Perk: Bounty Payout -> rank 48: +50%
//A. Lavingy-Duval: Perk: Weapon Module Cost -> rank 52: -20%
//A. Lavingy-Duval: Perk: Bounty Payout -> rank 55: +60%
//A. Lavingy-Duval: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 60: -100%
//A. Lavingy-Duval: Perk: Bounty Payout -> rank 67: +70%
//A. Lavingy-Duval: Perk: Bounty Payout -> rank 73: +80%
//A. Lavingy-Duval: Perk: Weapon Module Cost -> rank 78: -25%
//A. Lavingy-Duval: Perk: Bounty Payout -> rank 86: +90%
//A. Lavingy-Duval: Perk: Weapon Module Cost -> rank 94: -30%
//A. Lavingy-Duval: Perk: Bounty Payout -> rank 100: +100%

                    PowerPerk.INCREASE_BOUNTY_PAYOUT, List.of(
                            new RankReward(5, 10),
                            new RankReward(14, 20),
                            new RankReward(22, 30),
                            new RankReward(32, 40),
                            new RankReward(48, 50),
                            new RankReward(55, 60),
                            new RankReward(67, 70),
                            new RankReward(73, 80),
                            new RankReward(86, 90),
                            new RankReward(100, 100)
                    ),
                    PowerPerk.REDUCED_WEAPON_MODULE_COST_IN_OWN_TERRITORY, List.of(
                            new RankReward(24, -10),
                            new RankReward(42, -15),
                            new RankReward(52, -20),
                            new RankReward(78, -25),
                            new RankReward(94, -30)
                    )
            ),
            Map.ofEntries(
//                    34	Imperial Hammer Rail Gun
//                    39	Advanced Plasma Accelerator
//                    44	Mining Lance Beam Laser
//                    50	Prismatic Shield Generator
//                    57	Enforcer Cannon
//                    63	Pack-Hound Missile Rack
//                    70	Retributor Beam Laser
//                    76	Concord Cannon
//                    83	Rocket Propelled Containment Missile
//                    88	Cytoscrambler Burst Laser
//                    91	Pulse Disruptor Laser
//                    97	Pacifier Frag-Cannon
                    Map.entry(RailGun.IMPERIAL_HAMMER_RAIL_GUN_2_B_F, 34),
                    Map.entry(PlasmaAccelerator.ADVANCED_PLASMA_ACCELERATOR_3_B_F, 39),
                    Map.entry(MiningLaser.MINING_LANCE_BEAM_LASER_1_D_F, 44),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_1_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_2_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_3_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_4_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_5_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_6_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_7_A, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_8_A, 50),
                    Map.entry(MultiCannon.ENFORCER_CANNON_1_F_F, 57),
                    Map.entry(MissileRack.PACK_HOUND_MISSILE_RACK_2_B_F, 63),
                    Map.entry(BeamLaser.RETRIBUTOR_BEAM_LASER_1_E_F, 70),
                    Map.entry(Cannon.CONCORD_CANNON_2_D_G, 76),
                    Map.entry(MissileRack.ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F, 83),
                    Map.entry(BurstLaser.CYTOSCRAMBLER_BURST_LASER_1_F_F, 88),
                    Map.entry(PulseLaser.PULSE_DISRUPTOR_LASER_2_E_F, 91),
                    Map.entry(FragmentCannon.PACIFIER_FRAG_CANNON_3_C_F, 97)

            ),
            new StarSystem("Kamadhenu", 110.0, -99.97, -13.38)
    ),
    AISLING_DUVAL(
            Map.of(

//Aisling Duval: Perk: reduced rebuy when killed in your Power's Territory -> rank 2: -20%
//Aisling Duval: Perk: Search and Rescue Payout -> rank 5: +20%
//Aisling Duval: Perk: reduced rebuy when killed in your Power's Territory -> rank 8: -40%
//Aisling Duval: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 11: -33%
//Aisling Duval: Perk: Search and Rescue Payout -> rank 14: +40%
//Aisling Duval: Perk: reduced rebuy when killed in your Power's Territory -> rank 17: -60%
//Aisling Duval: Perk: Search and Rescue Payout -> rank 22: +60%
//Aisling Duval: Perk: Minor Faction Reputation Gain -> rank 24: +20%
//Aisling Duval: Perk: reduced rebuy when killed in your Power's Territory -> rank 27: -80%
//Aisling Duval: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 29: -66%
//Aisling Duval: Perk: Search and Rescue Payout -> rank 32: +80%
//Aisling Duval: Perk: reduced rebuy when killed in your Power's Territory -> rank 36: -100%
//Aisling Duval: Perk: Minor Faction Reputation Gain -> rank 42: +40%
//Aisling Duval: Perk: Search and Rescue Payout -> rank 48: +100%
//Aisling Duval: Perk: Minor Faction Reputation Gain -> rank 52: +60%
//Aisling Duval: Perk: Search and Rescue Payout -> rank 55: +120%
//Aisling Duval: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 60: -100%
//Aisling Duval: Perk: Search and Rescue Payout -> rank 67: +140%
//Aisling Duval: Perk: Search and Rescue Payout -> rank 73: +160%
//Aisling Duval: Perk: Minor Faction Reputation Gain -> rank 78: +80%
//Aisling Duval: Perk: Search and Rescue Payout -> rank 86: +180%
//Aisling Duval: Perk: Minor Faction Reputation Gain -> rank 94: +100%
//Aisling Duval: Perk: Search and Rescue Payout -> rank 100: +200%

                    PowerPerk.INCREASE_SEARCH_AND_RESCUE_PAYOUT, List.of(
                            new RankReward(5, 20),
                            new RankReward(14, 40),
                            new RankReward(22, 60),
                            new RankReward(32, 80),
                            new RankReward(48, 100),
                            new RankReward(55, 120),
                            new RankReward(67, 140),
                            new RankReward(73, 160),
                            new RankReward(86, 180),
                            new RankReward(100, 200)
                    ),
                    PowerPerk.INCREASE_MINOR_FACTION_REPUTATION, List.of(
                            new RankReward(24, 20),
                            new RankReward(42, 40),
                            new RankReward(52, 60),
                            new RankReward(78, 80),
                            new RankReward(94, 100)
                    )
            ),
            Map.ofEntries(
//                    34	Prismatic Shield Generator x8
//                    39	Imperial Hammer Rail Gun
//                    44	Advanced Plasma Accelerator
//                    50	Mining Lance Beam Laser
//                    57	Retributor Beam Laser
//                    63	Concord Cannon
//                    70	Pack-Hound Missile Rack
//                    76	Rocket Propelled Containment Missile
//                    83	Enforcer Cannon
//                    88	Pulse Disruptor Laser
//                    91	Pacifier Frag-Cannon
//                    97	Cytoscrambler Burst Laser
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_1_A, 34),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_2_A, 34),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_3_A, 34),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_4_A, 34),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_5_A, 34),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_6_A, 34),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_7_A, 34),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_8_A, 34),
                    Map.entry(RailGun.IMPERIAL_HAMMER_RAIL_GUN_2_B_F, 39),
                    Map.entry(PlasmaAccelerator.ADVANCED_PLASMA_ACCELERATOR_3_B_F, 44),
                    Map.entry(MiningLaser.MINING_LANCE_BEAM_LASER_1_D_F, 50),
                    Map.entry(BeamLaser.RETRIBUTOR_BEAM_LASER_1_E_F, 57),
                    Map.entry(Cannon.CONCORD_CANNON_2_D_G, 63),
                    Map.entry(MissileRack.PACK_HOUND_MISSILE_RACK_2_B_F, 70),
                    Map.entry(MissileRack.ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F, 76),
                    Map.entry(MultiCannon.ENFORCER_CANNON_1_F_F, 83),
                    Map.entry(PulseLaser.PULSE_DISRUPTOR_LASER_2_E_F, 88),
                    Map.entry(FragmentCannon.PACIFIER_FRAG_CANNON_3_C_F, 91),
                    Map.entry(BurstLaser.CYTOSCRAMBLER_BURST_LASER_1_F_F, 97)

            ),
            new StarSystem("Cubeo", 128.28, -155.63, 84.22)
    ),
    EDMUND_MAHON(
            Map.of(
                    //Edmund Mahon: Perk: reduced rebuy when killed in your Power's Territory -> rank 2: -20%
//Edmund Mahon: Perk: Trade Profits on Sales -> rank 5: +05%
//Edmund Mahon: Perk: reduced rebuy when killed in your Power's Territory -> rank 8: -40%
//Edmund Mahon: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 11: -33%
//Edmund Mahon: Perk: Trade Profits on Sales -> rank 14: +10%
//Edmund Mahon: Perk: reduced rebuy when killed in your Power's Territory -> rank 17: -60%
//Edmund Mahon: Perk: Trade Profits on Sales -> rank 22: +15%
//Edmund Mahon: Perk: Minor Faction Reputation Gain -> rank 24: +20%
//Edmund Mahon: Perk: reduced rebuy when killed in your Power's Territory -> rank 27: -80%
//Edmund Mahon: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 29: -66%
//Edmund Mahon: Perk: Trade Profits on Sales -> rank 32: +20%
//Edmund Mahon: Perk: reduced rebuy when killed in your Power's Territory -> rank 36: -100%
//Edmund Mahon: Perk: Minor Faction Reputation Gain -> rank 42: +40%
//Edmund Mahon: Perk: Trade Profits on Sales -> rank 48: +25%
//Edmund Mahon: Perk: Minor Faction Reputation Gain -> rank 52: -60%
//Edmund Mahon: Perk: Trade Profits  on RARE GOODS Sales -> rank 55: +10%
//Edmund Mahon: Perk: reduced rebuy when killed by  ship pledged to another power in territory your Power does not control -> rank 60: -100%
//Edmund Mahon: Perk: Trade Profits  on RARE GOODS Sales -> rank 67: +20%
//Edmund Mahon: Perk: Minor Faction Reputation Gain -> rank 73: +30%
//Edmund Mahon: Perk: Trade Profits  on RARE GOODS Sales -> rank 78: +30%
//Edmund Mahon: Perk: Minor Faction Reputation Gain -> rank 86: +40%
//Edmund Mahon: Perk: Trade Profits  on RARE GOODS Sales -> rank 94: +40%
//Edmund Mahon: Perk: Minor Faction Reputation Gain -> rank 100: +50%
                    PowerPerk.INCREASE_TRADE_BOND_SALES_AWARD, List.of(
                            new RankReward(5, 5),
                            new RankReward(14, 10),
                            new RankReward(22, 15),
                            new RankReward(32, 20),
                            new RankReward(48, 25)
                    ),
                    PowerPerk.INCREASE_MINOR_FACTION_REPUTATION, List.of(
                            new RankReward(24, 20),
                            new RankReward(42, 40),
                            new RankReward(52, 60),
                            new RankReward(78, 80),
                            new RankReward(94, 100)
                    ),
                    PowerPerk.INCREASE_TRADE_BOND_RARE_GOODS_AWARD, List.of(
                            new RankReward(55, 10),
                            new RankReward(67, 20),
                            new RankReward(73, 30),
                            new RankReward(86, 40),
                            new RankReward(100, 50)
                    )
            ),
            Map.ofEntries(
//                    34	Retributor Beam Laser
//                    39	Concord Cannon
//                    44	Pack-Hound Missile Rack
//                    50	Enforcer Cannon
//                    57	Prismatic Shield Generator x8
//                    63	Rocket Propelled Containment Missile
//                    70	Pulse Disruptor Laser
//                    76	Advanced Plasma Accelerator
//                    83	Mining Lance Beam Laser
//                    88	Imperial Hammer Rail Gun
//                    91	Pacifier Frag-Cannon
//                    97	Cytoscrambler Burst Laser
                    Map.entry(BeamLaser.RETRIBUTOR_BEAM_LASER_1_E_F, 34),
                    Map.entry(Cannon.CONCORD_CANNON_2_D_G, 39),
                    Map.entry(MissileRack.PACK_HOUND_MISSILE_RACK_2_B_F, 44),
                    Map.entry(MultiCannon.ENFORCER_CANNON_1_F_F, 50),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_1_A, 57),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_2_A, 57),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_3_A, 57),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_4_A, 57),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_5_A, 57),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_6_A, 57),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_7_A, 57),
                    Map.entry(ShieldGenerator.PRISMATIC_SHIELD_GENERATOR_8_A, 57),
                    Map.entry(MissileRack.ROCKET_PROPELLED_FSD_DISRUPTER_2_B_F, 63),
                    Map.entry(PulseLaser.PULSE_DISRUPTOR_LASER_2_E_F, 70),
                    Map.entry(PlasmaAccelerator.ADVANCED_PLASMA_ACCELERATOR_3_B_F, 76),
                    Map.entry(MiningLaser.MINING_LANCE_BEAM_LASER_1_D_F, 83),
                    Map.entry(RailGun.IMPERIAL_HAMMER_RAIL_GUN_2_B_F, 88),
                    Map.entry(FragmentCannon.PACIFIER_FRAG_CANNON_3_C_F, 91),
                    Map.entry(BurstLaser.CYTOSCRAMBLER_BURST_LASER_1_F_F, 97)

            ),
            new StarSystem("Gateway", -11.0, 77.84, -0.88)
    ),
    NONE(
            Map.of(),
            Map.of(),
            new StarSystem("Sol", 0.0, 0.0, 0.0)
    ),
    ALL(
            Map.of(
                    PowerPerk.RANK_DECAL, List.of(
                            new RankReward(1, 1)
                    ),
                    PowerPerk.REDUCED_REBUY_BY_OPPOSING_POWER, List.of(
                            new RankReward(11, -33),
                            new RankReward(29, -66),
                            new RankReward(60, -100)
                    ),
                    PowerPerk.REDUCED_REBUY_IN_OWN_TERRITORY, List.of(
                            new RankReward(2, -20),
                            new RankReward(8, -40),
                            new RankReward(17, -60),
                            new RankReward(27, -80),
                            new RankReward(36, -100)
                    )
            ),
            Map.of(),
            new StarSystem("Sol", 0.0, 0.0, 0.0)
    );

    private final Map<PowerPerk, List<RankReward>> perks;
    private final Map<ShipModule, Integer> unlockables;
    private final StarSystem starSystem;

    Power(Map<PowerPerk, List<RankReward>> perks, Map<ShipModule, Integer> unlockables, StarSystem starSystem) {
        this.perks = perks;
        this.unlockables = unlockables;
        this.starSystem = starSystem;
    }

    public static Power forName(String power) {
        switch (power) {
            case "Arissa Lavigny-Duval", "A. Lavigny-Duval":
                return A_LAVIGNY_DUVAL;
            case "Aisling Duval":
                return AISLING_DUVAL;
            case "Archon Delaine":
                return ARCHON_DELAINE;
            case "Denton Patreus":
                return DENTON_PATREUS;
            case "Edmund Mahon":
                return EDMUND_MAHON;
            case "Felicia Winters":
                return FELICIA_WINTERS;
            case "Li Yong-Rui":
                return LI_YONG_RUI;
            case "Pranav Antal":
                return PRANAV_ANTAL;
            case "Yuri Grom":
                return YURI_GROM;
            case "Jerome Archer":
                return JEROME_ARCHER;
            case "Zachary Hudson":
                return NONE;
            case "Zemina Torval":
                return ZEMINA_TORVAL;
            case "Nakato Kaine":
                return NAKATO_KAINE;
            default:
                throw new IllegalArgumentException("Unknown power: " + power);
        }
    }

    //localization key
    public String getLocalizationKey() {
        return "power." + this.name().toLowerCase();
    }

    public Double getDistance(final double x, final double y, final double z) {
        return Math.sqrt(Math.pow(this.getStarSystem().getX() - x, 2) + Math.pow(this.getStarSystem().getY() - y, 2) + Math.pow(this.getStarSystem().getZ() - z, 2));
    }

    public Double getDistance(final StarSystem starSystem) {
        return getDistance(starSystem.getX(), starSystem.getY(), starSystem.getZ());
    }

    public Object getNextRankMerits(Long rank) {
        int requiredMerits = 0;
        if(rank >= 2L){
            requiredMerits += 2000;
        }
        if(rank >= 3L){
            requiredMerits += 3000;
        }
        if(rank >= 4L){
            requiredMerits += 4000;
        }
        if(rank >= 5L){
            requiredMerits += 6000;
        }
        if(rank >= 6L){
            requiredMerits = requiredMerits + ( 6000 * (rank.intValue() - 5));
        }
        return requiredMerits;
    }
}
