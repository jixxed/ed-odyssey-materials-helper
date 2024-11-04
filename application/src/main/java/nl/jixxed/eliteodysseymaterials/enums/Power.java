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
                            new RankReward(52, 30)
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
                            new RankReward(1, 100)
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
