package nl.jixxed.eliteodysseymaterials.service.ships;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipType;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.FrameShiftDrive;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.PowerDistributor;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.PowerPlant;
import nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.DetailedSurfaceScanner;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.ShieldGenerator;
import nl.jixxed.eliteodysseymaterials.domain.ships.utility.KillWarrantScanner;
import nl.jixxed.eliteodysseymaterials.domain.ships.utility.PointDefence;
import nl.jixxed.eliteodysseymaterials.domain.ships.utility.SinkLauncher;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.Engineering;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.Loadout;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.Module;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
class LoadoutMapperTest {
    @BeforeEach
    public void setUp() {
        List modules = ShipModule.getBasicModules();
    }

    @Test
    public void getShipSlotIndexes() {
        final Ship ship = ShipService.createBlankShip(ShipType.FEDERATION_CORVETTE);
        Assertions.assertAll(
                () -> Assertions.assertEquals(0, LoadoutMapper.getShipSlot(ship, "HugeHardpoint1").getIndex()),
                () -> Assertions.assertEquals(1, LoadoutMapper.getShipSlot(ship, "HugeHardpoint2").getIndex()),
                () -> Assertions.assertEquals(2, LoadoutMapper.getShipSlot(ship, "LargeHardpoint1").getIndex()),
                () -> Assertions.assertEquals(3, LoadoutMapper.getShipSlot(ship, "MediumHardpoint1").getIndex()),
                () -> Assertions.assertEquals(4, LoadoutMapper.getShipSlot(ship, "MediumHardpoint2").getIndex()),
                () -> Assertions.assertEquals(5, LoadoutMapper.getShipSlot(ship, "SmallHardpoint1").getIndex()),
                () -> Assertions.assertEquals(6, LoadoutMapper.getShipSlot(ship, "SmallHardpoint2").getIndex()),
                () -> Assertions.assertEquals(0, LoadoutMapper.getShipSlot(ship, "TinyHardpoint1").getIndex()),
                () -> Assertions.assertEquals(1, LoadoutMapper.getShipSlot(ship, "TinyHardpoint2").getIndex()),
                () -> Assertions.assertEquals(2, LoadoutMapper.getShipSlot(ship, "TinyHardpoint3").getIndex()),
                () -> Assertions.assertEquals(3, LoadoutMapper.getShipSlot(ship, "TinyHardpoint4").getIndex()),
                () -> Assertions.assertEquals(4, LoadoutMapper.getShipSlot(ship, "TinyHardpoint5").getIndex()),
                () -> Assertions.assertEquals(5, LoadoutMapper.getShipSlot(ship, "TinyHardpoint6").getIndex()),
                () -> Assertions.assertEquals(6, LoadoutMapper.getShipSlot(ship, "TinyHardpoint7").getIndex()),
                () -> Assertions.assertEquals(7, LoadoutMapper.getShipSlot(ship, "TinyHardpoint8").getIndex()),
                () -> Assertions.assertEquals(0, LoadoutMapper.getShipSlot(ship, "Armour").getIndex()),
                () -> Assertions.assertEquals(1, LoadoutMapper.getShipSlot(ship, "PowerPlant").getIndex()),
                () -> Assertions.assertEquals(2, LoadoutMapper.getShipSlot(ship, "MainEngines").getIndex()),
                () -> Assertions.assertEquals(3, LoadoutMapper.getShipSlot(ship, "FrameShiftDrive").getIndex()),
                () -> Assertions.assertEquals(4, LoadoutMapper.getShipSlot(ship, "LifeSupport").getIndex()),
                () -> Assertions.assertEquals(5, LoadoutMapper.getShipSlot(ship, "PowerDistributor").getIndex()),
                () -> Assertions.assertEquals(6, LoadoutMapper.getShipSlot(ship, "Radar").getIndex()),
                () -> Assertions.assertEquals(7, LoadoutMapper.getShipSlot(ship, "FuelTank").getIndex()),
                () -> Assertions.assertEquals(0, LoadoutMapper.getShipSlot(ship, "Slot01_Size7").getIndex()),
                () -> Assertions.assertEquals(1, LoadoutMapper.getShipSlot(ship, "Slot02_Size7").getIndex()),
                () -> Assertions.assertEquals(2, LoadoutMapper.getShipSlot(ship, "Slot03_Size7").getIndex()),
                () -> Assertions.assertEquals(3, LoadoutMapper.getShipSlot(ship, "Slot04_Size6").getIndex()),
                () -> Assertions.assertEquals(4, LoadoutMapper.getShipSlot(ship, "Slot05_Size6").getIndex()),
                () -> Assertions.assertEquals(5, LoadoutMapper.getShipSlot(ship, "Slot06_Size5").getIndex()),
                () -> Assertions.assertEquals(6, LoadoutMapper.getShipSlot(ship, "Slot07_Size5").getIndex()),
                () -> Assertions.assertEquals(7, LoadoutMapper.getShipSlot(ship, "Military01").getIndex()),
                () -> Assertions.assertEquals(8, LoadoutMapper.getShipSlot(ship, "Military02").getIndex()),
                () -> Assertions.assertEquals(9, LoadoutMapper.getShipSlot(ship, "Slot08_Size4").getIndex()),
                () -> Assertions.assertEquals(10, LoadoutMapper.getShipSlot(ship, "Slot09_Size4").getIndex()),
                () -> Assertions.assertEquals(11, LoadoutMapper.getShipSlot(ship, "Slot10_Size3").getIndex()),
                () -> Assertions.assertEquals(12, LoadoutMapper.getShipSlot(ship, "Slot11_Size1").getIndex())
        );
    }

    @Test
    public void getShipSlotSizes() {
        final Ship ship = ShipService.createBlankShip(ShipType.FEDERATION_CORVETTE);
        Assertions.assertAll(
                () -> Assertions.assertEquals(4, LoadoutMapper.getShipSlot(ship, "HugeHardpoint1").getSlotSize()),
                () -> Assertions.assertEquals(4, LoadoutMapper.getShipSlot(ship, "HugeHardpoint2").getSlotSize()),
                () -> Assertions.assertEquals(3, LoadoutMapper.getShipSlot(ship, "LargeHardpoint1").getSlotSize()),
                () -> Assertions.assertEquals(2, LoadoutMapper.getShipSlot(ship, "MediumHardpoint1").getSlotSize()),
                () -> Assertions.assertEquals(2, LoadoutMapper.getShipSlot(ship, "MediumHardpoint2").getSlotSize()),
                () -> Assertions.assertEquals(1, LoadoutMapper.getShipSlot(ship, "SmallHardpoint1").getSlotSize()),
                () -> Assertions.assertEquals(1, LoadoutMapper.getShipSlot(ship, "SmallHardpoint2").getSlotSize()),
                () -> Assertions.assertEquals(0, LoadoutMapper.getShipSlot(ship, "TinyHardpoint1").getSlotSize()),
                () -> Assertions.assertEquals(0, LoadoutMapper.getShipSlot(ship, "TinyHardpoint2").getSlotSize()),
                () -> Assertions.assertEquals(0, LoadoutMapper.getShipSlot(ship, "TinyHardpoint3").getSlotSize()),
                () -> Assertions.assertEquals(0, LoadoutMapper.getShipSlot(ship, "TinyHardpoint4").getSlotSize()),
                () -> Assertions.assertEquals(0, LoadoutMapper.getShipSlot(ship, "TinyHardpoint5").getSlotSize()),
                () -> Assertions.assertEquals(0, LoadoutMapper.getShipSlot(ship, "TinyHardpoint6").getSlotSize()),
                () -> Assertions.assertEquals(0, LoadoutMapper.getShipSlot(ship, "TinyHardpoint7").getSlotSize()),
                () -> Assertions.assertEquals(0, LoadoutMapper.getShipSlot(ship, "TinyHardpoint8").getSlotSize()),
                () -> Assertions.assertEquals(1, LoadoutMapper.getShipSlot(ship, "Armour").getSlotSize()),
                () -> Assertions.assertEquals(8, LoadoutMapper.getShipSlot(ship, "PowerPlant").getSlotSize()),
                () -> Assertions.assertEquals(7, LoadoutMapper.getShipSlot(ship, "MainEngines").getSlotSize()),
                () -> Assertions.assertEquals(6, LoadoutMapper.getShipSlot(ship, "FrameShiftDrive").getSlotSize()),
                () -> Assertions.assertEquals(5, LoadoutMapper.getShipSlot(ship, "LifeSupport").getSlotSize()),
                () -> Assertions.assertEquals(8, LoadoutMapper.getShipSlot(ship, "PowerDistributor").getSlotSize()),
                () -> Assertions.assertEquals(8, LoadoutMapper.getShipSlot(ship, "Radar").getSlotSize()),
                () -> Assertions.assertEquals(5, LoadoutMapper.getShipSlot(ship, "FuelTank").getSlotSize()),
                () -> Assertions.assertEquals(7, LoadoutMapper.getShipSlot(ship, "Slot01_Size7").getSlotSize()),
                () -> Assertions.assertEquals(7, LoadoutMapper.getShipSlot(ship, "Slot02_Size7").getSlotSize()),
                () -> Assertions.assertEquals(7, LoadoutMapper.getShipSlot(ship, "Slot03_Size7").getSlotSize()),
                () -> Assertions.assertEquals(6, LoadoutMapper.getShipSlot(ship, "Slot04_Size6").getSlotSize()),
                () -> Assertions.assertEquals(6, LoadoutMapper.getShipSlot(ship, "Slot05_Size6").getSlotSize()),
                () -> Assertions.assertEquals(5, LoadoutMapper.getShipSlot(ship, "Slot06_Size5").getSlotSize()),
                () -> Assertions.assertEquals(5, LoadoutMapper.getShipSlot(ship, "Slot07_Size5").getSlotSize()),
                () -> Assertions.assertEquals(5, LoadoutMapper.getShipSlot(ship, "Military01").getSlotSize()),
                () -> Assertions.assertEquals(5, LoadoutMapper.getShipSlot(ship, "Military02").getSlotSize()),
                () -> Assertions.assertEquals(4, LoadoutMapper.getShipSlot(ship, "Slot08_Size4").getSlotSize()),
                () -> Assertions.assertEquals(4, LoadoutMapper.getShipSlot(ship, "Slot09_Size4").getSlotSize()),
                () -> Assertions.assertEquals(3, LoadoutMapper.getShipSlot(ship, "Slot10_Size3").getSlotSize()),
                () -> Assertions.assertEquals(1, LoadoutMapper.getShipSlot(ship, "Slot11_Size1").getSlotSize())
        );
    }

    //    private static Stream<Arguments> preEngineeredModules() {
//        return Stream.of(
//                Arguments.of("gauss_pre_1.json",SlotType.HARDPOINT),
//                Arguments.of("gauss_pre_2.json",SlotType.HARDPOINT),
//                Arguments.of("abrasion_blaster_pre.json",SlotType.HARDPOINT),
//                Arguments.of("festive_launcher_pre_green.json",SlotType.HARDPOINT),
//                Arguments.of("festive_launcher_pre_red.json",SlotType.HARDPOINT),
//                Arguments.of("festive_launcher_pre_yellow.json",SlotType.HARDPOINT),
//                Arguments.of("fsd_pre_3.json",SlotType.CORE_FRAME_SHIFT_DRIVE),
//                Arguments.of("fsd_pre_3_exp.json",SlotType.CORE_FRAME_SHIFT_DRIVE),
//                Arguments.of("fsd_pre_4.json",SlotType.CORE_FRAME_SHIFT_DRIVE),
//                Arguments.of("fsd_pre_4_exp.json",SlotType.CORE_FRAME_SHIFT_DRIVE),
//                Arguments.of("fsd_pre_4_exp_lw.json",SlotType.CORE_FRAME_SHIFT_DRIVE),
//                Arguments.of("fsd_pre_5.json",SlotType.CORE_FRAME_SHIFT_DRIVE),
//                Arguments.of("fsd_pre_5_exp.json",SlotType.CORE_FRAME_SHIFT_DRIVE),
//                Arguments.of("fsd_pre_6.json",SlotType.CORE_FRAME_SHIFT_DRIVE),
//                Arguments.of("fsd_pre_6_exp.json",SlotType.CORE_FRAME_SHIFT_DRIVE),
//                Arguments.of("powerplant_pre_3.json",SlotType.CORE_POWER_PLANT),
//                Arguments.of("powerplant_pre_4.json",SlotType.CORE_POWER_PLANT),
//                Arguments.of("powerplant_pre_5.json",SlotType.CORE_POWER_PLANT),
//                Arguments.of("ax_missile_rack_3_pre.json",SlotType.HARDPOINT),
//                Arguments.of("mining_laser.json",SlotType.HARDPOINT),
//                Arguments.of("heatsink_pre.json",SlotType.UTILITY)
//        );
//    }
    private static Stream<Arguments> preEngineeredModules() {
        List modules = ShipModule.getBasicModules();
        return Stream.of(

                Arguments.of("ABRASION_BLASTER_1_D_F_PRE.json", AbrasionBlaster.ABRASION_BLASTER_1_D_F_PRE, SlotType.HARDPOINT),
                Arguments.of("AX_MISSILE_RACK_2_E_F_PRE.json", AXMissileRack.AX_MISSILE_RACK_2_E_F_PRE, SlotType.HARDPOINT),
                Arguments.of("AX_MISSILE_RACK_3_C_F_PRE.json", AXMissileRack.AX_MISSILE_RACK_3_C_F_PRE, SlotType.HARDPOINT),
                Arguments.of("AX_MULTI_CANNON_2_E_F_PRE.json", AXMultiCannon.AX_MULTI_CANNON_2_E_F_PRE, SlotType.HARDPOINT),
                Arguments.of("AX_MULTI_CANNON_3_C_F_PRE.json", AXMultiCannon.AX_MULTI_CANNON_3_C_F_PRE, SlotType.HARDPOINT),
                Arguments.of("ENZYME_MISSILE_RACK_2_B_F_PRE.json", EnzymeMissileRack.ENZYME_MISSILE_RACK_2_B_F_PRE, SlotType.HARDPOINT),
                Arguments.of("GUARDIAN_GAUSS_CANNON_1_D_F_PRE.json", GuardianGaussCannon.GUARDIAN_GAUSS_CANNON_1_D_F_PRE, SlotType.HARDPOINT),
                Arguments.of("GUARDIAN_GAUSS_CANNON_2_B_F_PRE.json", GuardianGaussCannon.GUARDIAN_GAUSS_CANNON_2_B_F_PRE, SlotType.HARDPOINT),
                Arguments.of("GUARDIAN_PLASMA_CHARGER_1_D_F_PRE.json", GuardianPlasmaCharger.GUARDIAN_PLASMA_CHARGER_1_D_F_PRE, SlotType.HARDPOINT),
                Arguments.of("GUARDIAN_PLASMA_CHARGER_2_B_F_PRE.json", GuardianPlasmaCharger.GUARDIAN_PLASMA_CHARGER_2_B_F_PRE, SlotType.HARDPOINT),
                Arguments.of("GUARDIAN_SHARD_CANNON_1_D_F_PRE.json", GuardianShardCannon.GUARDIAN_SHARD_CANNON_1_D_F_PRE, SlotType.HARDPOINT),
                Arguments.of("GUARDIAN_SHARD_CANNON_2_A_F_PRE.json", GuardianShardCannon.GUARDIAN_SHARD_CANNON_2_A_F_PRE, SlotType.HARDPOINT),
                Arguments.of("GUARDIAN_SHARD_CANNON_2_A_F_PRE_GOD.json", GuardianShardCannon.GUARDIAN_SHARD_CANNON_2_A_F_PRE_GOD, SlotType.HARDPOINT),
                Arguments.of("MINING_LASER_1_D_F_PRE.json", MiningLaser.MINING_LASER_1_D_F_PRE, SlotType.HARDPOINT),
                Arguments.of("MINING_LASER_1_D_F_PRE_ARX.json", MiningLaser.MINING_LASER_1_D_F_PRE_ARX, SlotType.HARDPOINT),
                Arguments.of("SEEKER_MISSILE_RACK_2_B_F_PRE.json", MissileRack.SEEKER_MISSILE_RACK_2_B_F_PRE, SlotType.HARDPOINT),
                Arguments.of("SEEKER_MISSILE_RACK_2_B_F_PRE_2.json", MissileRack.SEEKER_MISSILE_RACK_2_B_F_PRE_2, SlotType.HARDPOINT),
                Arguments.of("SEEKER_MISSILE_RACK_3_A_F_PRE.json", MissileRack.SEEKER_MISSILE_RACK_3_A_F_PRE, SlotType.HARDPOINT),
                Arguments.of("FRAGMENT_CANNON_1_E_G_PRE.json", FragmentCannon.FRAGMENT_CANNON_1_E_G_PRE, SlotType.HARDPOINT),
                Arguments.of("FRAGMENT_CANNON_3_C_G_PRE.json", FragmentCannon.FRAGMENT_CANNON_3_C_G_PRE, SlotType.HARDPOINT),
                Arguments.of("MULTI_CANNON_2_E_F_PRE.json", MultiCannon.MULTI_CANNON_2_E_F_PRE, SlotType.HARDPOINT),
                Arguments.of("RAIL_GUN_2_B_F_PRE.json", RailGun.RAIL_GUN_2_B_F_PRE, SlotType.HARDPOINT),
                Arguments.of("REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_GREEN.json", RemoteReleaseFlakLauncher.REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_GREEN, SlotType.HARDPOINT),
                Arguments.of("REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_YELLOW.json", RemoteReleaseFlakLauncher.REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_YELLOW, SlotType.HARDPOINT),
                Arguments.of("REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_RED.json", RemoteReleaseFlakLauncher.REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_RED, SlotType.HARDPOINT),
                Arguments.of("FRAME_SHIFT_DRIVE_3_A_V1_PRE.json", FrameShiftDrive.FRAME_SHIFT_DRIVE_3_A_V1_PRE, SlotType.CORE_FRAME_SHIFT_DRIVE),
                Arguments.of("FRAME_SHIFT_DRIVE_4_A_V1_PRE.json", FrameShiftDrive.FRAME_SHIFT_DRIVE_4_A_V1_PRE, SlotType.CORE_FRAME_SHIFT_DRIVE),
                Arguments.of("FRAME_SHIFT_DRIVE_5_A_V1_PRE.json", FrameShiftDrive.FRAME_SHIFT_DRIVE_5_A_V1_PRE, SlotType.CORE_FRAME_SHIFT_DRIVE),
                Arguments.of("FRAME_SHIFT_DRIVE_6_A_V1_PRE.json", FrameShiftDrive.FRAME_SHIFT_DRIVE_6_A_V1_PRE, SlotType.CORE_FRAME_SHIFT_DRIVE),
                Arguments.of("FRAME_SHIFT_DRIVE_2_A_SCO_V1_PRE.json", FrameShiftDrive.FRAME_SHIFT_DRIVE_OVERCHARGE_2_A_PRE, SlotType.CORE_FRAME_SHIFT_DRIVE),
                Arguments.of("FRAME_SHIFT_DRIVE_3_A_SCO_V1_PRE.json", FrameShiftDrive.FRAME_SHIFT_DRIVE_OVERCHARGE_3_A_PRE, SlotType.CORE_FRAME_SHIFT_DRIVE),
                Arguments.of("FRAME_SHIFT_DRIVE_4_A_SCO_V1_PRE.json", FrameShiftDrive.FRAME_SHIFT_DRIVE_OVERCHARGE_4_A_PRE, SlotType.CORE_FRAME_SHIFT_DRIVE),
                Arguments.of("FRAME_SHIFT_DRIVE_5_A_SCO_V1_PRE.json", FrameShiftDrive.FRAME_SHIFT_DRIVE_OVERCHARGE_5_A_PRE, SlotType.CORE_FRAME_SHIFT_DRIVE),
                Arguments.of("FRAME_SHIFT_DRIVE_6_A_SCO_V1_PRE.json", FrameShiftDrive.FRAME_SHIFT_DRIVE_OVERCHARGE_6_A_PRE, SlotType.CORE_FRAME_SHIFT_DRIVE),
                Arguments.of("FRAME_SHIFT_DRIVE_7_A_SCO_V1_PRE.json", FrameShiftDrive.FRAME_SHIFT_DRIVE_OVERCHARGE_7_A_PRE, SlotType.CORE_FRAME_SHIFT_DRIVE),
                Arguments.of("POWER_DISTRIBUTOR_3_A_PRE.json", PowerDistributor.POWER_DISTRIBUTOR_3_A_SYSTEM_ENGINE_FOCUSED, SlotType.CORE_POWER_DISTRIBUTION),
                Arguments.of("POWER_DISTRIBUTOR_3_D_PRE.json", PowerDistributor.POWER_DISTRIBUTOR_3_D_SYSTEM_ENGINE_FOCUSED, SlotType.CORE_POWER_DISTRIBUTION),
                Arguments.of("POWER_DISTRIBUTOR_4_A_PRE.json", PowerDistributor.POWER_DISTRIBUTOR_4_A_SYSTEM_ENGINE_FOCUSED, SlotType.CORE_POWER_DISTRIBUTION),
                Arguments.of("POWER_DISTRIBUTOR_4_D_PRE.json", PowerDistributor.POWER_DISTRIBUTOR_4_D_SYSTEM_ENGINE_FOCUSED, SlotType.CORE_POWER_DISTRIBUTION),
                Arguments.of("POWER_DISTRIBUTOR_6_A_PRE.json", PowerDistributor.POWER_DISTRIBUTOR_6_A_SYSTEM_ENGINE_FOCUSED, SlotType.CORE_POWER_DISTRIBUTION),
                Arguments.of("POWER_PLANT_3_A_ARMOURED_OVERCHARGED_PRE.json", PowerPlant.POWER_PLANT_3_A_ARMOURED_OVERCHARGED, SlotType.CORE_POWER_PLANT),
                Arguments.of("POWER_PLANT_3_A_OVERCHARGED_OVERCHARGED_PRE.json", PowerPlant.POWER_PLANT_3_A_OVERCHARGED_OVERCHARGED, SlotType.CORE_POWER_PLANT),
                Arguments.of("POWER_PLANT_4_A_OVERCHARGED_OVERCHARGED_PRE.json", PowerPlant.POWER_PLANT_4_A_OVERCHARGED_OVERCHARGED, SlotType.CORE_POWER_PLANT),
                Arguments.of("POWER_PLANT_5_A_OVERCHARGED_OVERCHARGED_PRE.json", PowerPlant.POWER_PLANT_5_A_OVERCHARGED_OVERCHARGED, SlotType.CORE_POWER_PLANT),
                Arguments.of("DETAILED_SURFACE_SCANNER_1_I_V1_PRE.json", DetailedSurfaceScanner.DETAILED_SURFACE_SCANNER_1_I_V1_PRE, SlotType.OPTIONAL),
                Arguments.of("SHIELD_GENERATOR_3_A_PRE.json", ShieldGenerator.SHIELD_GENERATOR_3_A_PRE, SlotType.OPTIONAL),
                Arguments.of("KILL_WARRANT_SCANNER_0_A_PRE.json", KillWarrantScanner.KILL_WARRANT_SCANNER_0_A_PRE, SlotType.UTILITY),
                Arguments.of("KILL_WARRANT_SCANNER_0_C_PRE.json", KillWarrantScanner.KILL_WARRANT_SCANNER_0_C_PRE, SlotType.UTILITY),
                Arguments.of("POINT_DEFENCE_0_I_PRE.json", PointDefence.POINT_DEFENCE_0_I_PRE, SlotType.UTILITY),
                Arguments.of("HEAT_SINK_LAUNCHER_0_I_PRE.json", SinkLauncher.HEAT_SINK_LAUNCHER_0_I_PRE, SlotType.UTILITY)//,


        );
    }

    private static Stream<Arguments> preEngineeredModules2() {
        List modules = ShipModule.getBasicModules();
        return Stream.of(

                Arguments.of("FRAGMENT_CANNON_1_E_G_PRE.json", FragmentCannon.FRAGMENT_CANNON_1_E_G_PRE, SlotType.HARDPOINT),
                Arguments.of("FRAGMENT_CANNON_3_C_G_PRE.json", FragmentCannon.FRAGMENT_CANNON_3_C_G_PRE, SlotType.HARDPOINT)

        );
    }

    @ParameterizedTest
    @MethodSource("preEngineeredModules")
    public void testIsPreEngineered(String filename, ShipModule shipModule, SlotType slotType) {
        String line = "";
        try (InputStream resourceAsStream = LoadoutMapperTest.class.getResourceAsStream("/ships/preengineered/" + filename)) {
            Assertions.assertTrue(resourceAsStream.available() > 0, "File is empty");
            ShipModule.ALL_MODULES.stream().flatMap(Collection::stream).filter(ShipModule::isPreEngineered).map(ShipModule::getId).forEach(log::info);
            ShipModule.getBasicModules();
            final Module module = objectMapper.readValue(resourceAsStream, Module.class);
            Ship sa = Ship.KRAIT_MK_II;
            final List<ShipModule> potentialShipModules = LoadoutMapper.getPotentialShipModules(module.getItem(), slotType);
            log.debug(potentialShipModules.stream().map(ShipModule::getId).collect(Collectors.joining(", ")));
            final boolean preEngineered = LoadoutMapper.isPreEngineered(potentialShipModules, module.getEngineering().get());
            Assertions.assertTrue(preEngineered);
            final Optional<ShipModule> first = potentialShipModules.stream().filter(ShipModule::isPreEngineered).filter(shipModule2 -> LoadoutMapper.matchingEngineering(shipModule2, module.getEngineering().get())).findFirst();
            Assertions.assertEquals(shipModule.getId(), first.get().getId());


        } catch (Exception e) {
            log.error("error", e);
            log.info(line);
            Assertions.assertFalse(true);
        }
    }

    @Test
    void testIsPreEngineeredWithEffect_FSD_V1() throws JsonProcessingException {
        Engineering engineering = new ObjectMapper().readValue("""
                {
                	"Engineer": "Elvira Martuuk",
                	"EngineerID": 300160,
                	"BlueprintID": 128673694,
                	"BlueprintName": "FSD_LongRange",
                	"Level": 5,
                	"Quality": 1.0,
                	"ExperimentalEffect": "special_fsd_heavy",
                	"ExperimentalEffect_Localised": "Mass Manager",
                	"Modifiers": [
                		{
                			"Label": "Mass",
                			"Value": 26.0,
                			"OriginalValue": 20.0,
                			"LessIsGood": 1
                		},
                		{
                			"Label": "Integrity",
                			"Value": 77.279999,
                			"OriginalValue": 120.0,
                			"LessIsGood": 0
                		},
                		{
                			"Label": "PowerDraw",
                			"Value": 0.69,
                			"OriginalValue": 0.6,
                			"LessIsGood": 1
                		},
                		{
                			"Label": "BootTime",
                			"Value": 2.0,
                			"OriginalValue": 10.0,
                			"LessIsGood": 1
                		},
                		{
                			"Label": "FSDOptimalMass",
                			"Value": 1856.399902,
                			"OriginalValue": 1050.0,
                			"LessIsGood": 0
                		},
                		{
                			"Label": "FSDHeatRate",
                			"Value": 32.400002,
                			"OriginalValue": 27.0,
                			"LessIsGood": 1
                		}
                	]
                }
                """, Engineering.class);
        final boolean preEngineered = LoadoutMapper.isPreEngineered(List.of(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_A_V1_PRE, FrameShiftDrive.FRAME_SHIFT_DRIVE_5_A), engineering);
        Assertions.assertTrue(preEngineered);
    }

    @Test
    void testIsPreEngineeredNoEffect_FSD_V1() throws JsonProcessingException {
        Engineering engineering = new ObjectMapper().readValue("""
                {
                	"Engineer": "Elvira Martuuk",
                	"EngineerID": 300160,
                	"BlueprintID": 128673694,
                	"BlueprintName": "FSD_LongRange",
                	"Level": 5,
                	"Quality": 1.0,
                	"Modifiers": [
                		{
                			"Label": "Mass",
                			"Value": 26.0,
                			"OriginalValue": 20.0,
                			"LessIsGood": 1
                		},
                		{
                			"Label": "Integrity",
                			"Value": 84.0,
                			"OriginalValue": 120.0,
                			"LessIsGood": 0
                		},
                		{
                			"Label": "PowerDraw",
                			"Value": 0.69,
                			"OriginalValue": 0.6,
                			"LessIsGood": 1
                		},
                		{
                			"Label": "BootTime",
                			"Value": 2.0,
                			"OriginalValue": 10.0,
                			"LessIsGood": 1
                		},
                		{
                			"Label": "FSDOptimalMass",
                			"Value": 1785.0,
                			"OriginalValue": 1050.0,
                			"LessIsGood": 0
                		},
                		{
                			"Label": "FSDHeatRate",
                			"Value": 32.400002,
                			"OriginalValue": 27.0,
                			"LessIsGood": 1
                		}
                	]
                }
                """, Engineering.class);
        final boolean preEngineered = LoadoutMapper.isPreEngineered(List.of(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_A_V1_PRE, FrameShiftDrive.FRAME_SHIFT_DRIVE_5_A), engineering);
        Assertions.assertTrue(preEngineered);
    }

    @Test
    void testIsNotPreEngineered_FSD() throws JsonProcessingException {
        Engineering engineering = new ObjectMapper().readValue("""
                {
                	"Engineer": "Elvira Martuuk",
                	"EngineerID": 300160,
                	"BlueprintID": 128673694,
                	"BlueprintName": "FSD_LongRange",
                	"Level": 5,
                	"Quality": 1.0,
                	"ExperimentalEffect": "special_fsd_heavy",
                	"ExperimentalEffect_Localised": "Mass Manager",
                	"Modifiers": [
                		{
                			"Label": "Mass",
                			"Value": 26.0,
                			"OriginalValue": 20.0,
                			"LessIsGood": 1
                		},
                		{
                			"Label": "Integrity",
                			"Value": 93.84,
                			"OriginalValue": 120.0,
                			"LessIsGood": 0
                		},
                		{
                			"Label": "PowerDraw",
                			"Value": 0.69,
                			"OriginalValue": 0.6,
                			"LessIsGood": 1
                		},
                		{
                			"Label": "FSDOptimalMass",
                			"Value": 1692.6,
                			"OriginalValue": 1050.0,
                			"LessIsGood": 0
                		}
                	]
                }
                """, Engineering.class);
        final boolean preEngineered = LoadoutMapper.isPreEngineered(List.of(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_A_V1_PRE, FrameShiftDrive.FRAME_SHIFT_DRIVE_5_A), engineering);
        Assertions.assertFalse(preEngineered);
    }

    @Test
    void testIsPreEngineeredNoEffect_DSS_V1() throws JsonProcessingException {
        Engineering engineering = new ObjectMapper().readValue("""
                {
                	"Engineer": "Felicity Farseer",
                	"EngineerID": 300100,
                	"BlueprintID": 128740149,
                	"BlueprintName": "Sensor_Expanded",
                	"Level": 5,
                	"Quality": 1.0,
                	"Modifiers": [
                		{
                			"Label": "PowerDraw",
                			"Value": 0.0,
                			"OriginalValue": 0.0,
                			"LessIsGood": 1
                		},
                		{
                			"Label": "DSS_PatchRadius",
                			"Value": 40.0,
                			"OriginalValue": 20.0,
                			"LessIsGood": 0
                		}
                	]
                }
                """, Engineering.class);
        final boolean preEngineered = LoadoutMapper.isPreEngineered(List.of(DetailedSurfaceScanner.DETAILED_SURFACE_SCANNER_1_I, DetailedSurfaceScanner.DETAILED_SURFACE_SCANNER_1_I_V1_PRE), engineering);
        Assertions.assertTrue(preEngineered);
    }

    @Test
    void testIsPreEngineeredModifiedMiningLaser() throws JsonProcessingException {
        Engineering engineering = new ObjectMapper().readValue("""
                {
                    "Engineer": "Selene Jean",
                    "EngineerID": 300210,
                    "BlueprintID": 128673614,
                    "BlueprintName": "Weapon_LongRange",
                    "Level": 5,
                    "Quality": 1.0,
                    "ExperimentalEffect": "special_incendiary_rounds",
                    "ExperimentalEffect_Localised": "Incendiary Rounds",
                    "Modifiers": [
                        {
                            "Label": "Integrity",
                                "Value": 20.0,
                                "OriginalValue": 40.0,
                                "LessIsGood": 0
                        },
                        {
                            "Label": "PowerDraw",
                                "Value": 0.25,
                                "OriginalValue": 0.5,
                                "LessIsGood": 1
                        },
                        {
                            "Label": "DamagePerSecond",
                                "Value": 1.9,
                                "OriginalValue": 2.0,
                                "LessIsGood": 0
                        },
                        {
                            "Label": "DistributorDraw",
                                "Value": 0.75,
                                "OriginalValue": 1.5,
                                "LessIsGood": 1
                        },
                        {
                            "Label": "ThermalLoad",
                                "Value": 1.02,
                                "OriginalValue": 2.0,
                                "LessIsGood": 1
                        },
                        {
                            "Label": "MaximumRange",
                                "Value": 2500.0,
                                "OriginalValue": 500.0,
                                "LessIsGood": 0
                        },
                        {
                            "Label": "DamageType",
                                "ValueStr": "$Thermic;",
                                "ValueStr_Localised": "Thermal"
                        },
                        {
                            "Label": "DamageFalloffRange",
                                "Value": 2500.0,
                                "OriginalValue": 300.0,
                                "LessIsGood": 0
                        }
                    ]
                }
                """, Engineering.class);
        final boolean preEngineered = LoadoutMapper.isPreEngineered(MiningLaser.MINING_LASERS, engineering);
        Assertions.assertTrue(preEngineered);
    }

    @Test
    void testIsPreEngineeredHeatSinkLauncher() throws JsonProcessingException {
        Engineering engineering = new ObjectMapper().readValue("""
                {
                     "EngineerID": 399999,
                     "BlueprintID": 129012673,
                     "BlueprintName": "Misc_HeatSinkCapacity",
                     "Level": 1,
                     "Quality": 1.000000,
                     "Modifiers": [
                       {
                         "Label": "AmmoMaximum",
                         "Value": 4.000000,
                         "OriginalValue": 2.000000,
                         "LessIsGood": 0
                       },
                       {
                         "Label": "ReloadTime",
                         "Value": 17.500000,
                         "OriginalValue": 10.000000,
                         "LessIsGood": 1
                       }
                     ]
                   }
                """, Engineering.class);
        final boolean preEngineered = LoadoutMapper.isPreEngineered(SinkLauncher.SINK_LAUNCHERS, engineering);
        Assertions.assertTrue(preEngineered);
    }

    @Test
    void testIsNotPreEngineered_DSS() throws JsonProcessingException {
        Engineering engineering = new ObjectMapper().readValue("""
                {
                	"Engineer": "Felicity Farseer",
                	"EngineerID": 300100,
                	"BlueprintID": 128740149,
                	"BlueprintName": "Sensor_Expanded",
                	"Level": 3,
                	"Quality": 1.0,
                	"Modifiers": [
                		{
                			"Label": "PowerDraw",
                			"Value": 0.0,
                			"OriginalValue": 0.0,
                			"LessIsGood": 1
                		},
                		{
                			"Label": "DSS_PatchRadius",
                			"Value": 26.0,
                			"OriginalValue": 20.0,
                			"LessIsGood": 0
                		}
                	]
                }
                """, Engineering.class);
        final boolean preEngineered = LoadoutMapper.isPreEngineered(List.of(DetailedSurfaceScanner.DETAILED_SURFACE_SCANNER_1_I, DetailedSurfaceScanner.DETAILED_SURFACE_SCANNER_1_I_V1_PRE), engineering);
        Assertions.assertFalse(preEngineered);
    }

    @Test
    void testIsGaussZoneReistanceEngineered() throws JsonProcessingException {
        Engineering engineering = new ObjectMapper().readValue("""
                {
                    "Engineer": "Ram Tah",
                    "EngineerID": 300110,
                    "BlueprintID": 129030458,
                    "BlueprintName": "GuardianWeapon_Sturdy",
                    "Level": 1,
                    "Quality": 0.0,
                    "Modifiers": [
                        {
                            "Label": "DamagePerSecond",
                            "Value": 21.204821,
                            "OriginalValue": 26.506025,
                            "LessIsGood": 0
                        },
                        {
                            "Label": "Damage",
                            "Value": 17.6,
                            "OriginalValue": 22.0,
                            "LessIsGood": 0
                        },
                        {
                            "Label": "GuardianModuleResistance",
                            "ValueStr": "$INT_PANEL_module_active;",
                            "ValueStr_Localised": "Active"
                        }
                    ]
                }""", Engineering.class);
        final boolean preEngineered = LoadoutMapper.isPreEngineered(List.of(GuardianGaussCannon.GUARDIAN_GAUSS_CANNON_1_D_F, GuardianGaussCannon.GUARDIAN_GAUSS_CANNON_1_D_F_PRE), engineering);
        Assertions.assertFalse(preEngineered);
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    {
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));
    }

    @Test
    public void testLoadoutMapping() {
//        final InputStream resourceAsStream = LoadoutMapperTest.class.getResourceAsStream("ships/loadout.json");
        String line = "";
        try (InputStream resourceAsStream = LoadoutMapperTest.class.getResourceAsStream("/ships/loadout.json")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
            while (reader.ready()) {
                line = reader.readLine();
                final Loadout loadout = objectMapper.readValue(line, Loadout.class);
                LoadoutMapper.toShip(loadout);
                Assertions.assertTrue(true);
            }

        } catch (Exception e) {
            log.error("error", e);
            log.info(line);
            Assertions.assertFalse(true);
        }
    }

    @Test
    public void listBlueprintIDs() {
//        final InputStream resourceAsStream = LoadoutMapperTest.class.getResourceAsStream("ships/loadout.json");
        String line = "";
        Set<String> modules = new HashSet<>();
        try (InputStream resourceAsStream = LoadoutMapperTest.class.getResourceAsStream("/ships/loadout.json")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
            while (reader.ready()) {
                line = reader.readLine();
                final Loadout loadout = objectMapper.readValue(line, Loadout.class);
                loadout.getModules().stream().forEach(module -> module.getEngineering().ifPresent(engineering -> {
                    modules.add(module.getItem() + " - " + engineering.getBlueprintID() + " - " + engineering.getBlueprintName() + " - " + engineering.getEngineer().orElse("?"));
                }));

            }
            modules.stream().sorted().forEach(System.out::println);
        } catch (Exception e) {
            log.error("error", e);
            log.info(line);
            Assertions.assertFalse(true);
        }
    }

    @Test
    @Disabled
    public void rebuy() {
//        "HullValue": 6136672,
//        "ModulesValue": 38003650,
//        "Rebuy": 2207018,
        String line = "";
        try (InputStream resourceAsStream = LoadoutMapperTest.class.getResourceAsStream("/ships/loadout/rebuy.json")) {
            ShipModule.getBasicModules();
            final Loadout loadout = objectMapper.readValue(resourceAsStream, Loadout.class);
            final Ship ship = LoadoutMapper.toShip(loadout);
            Assertions.assertAll(
                    () -> Assertions.assertEquals(2207018, ship.getRebuy()),
                    () -> Assertions.assertEquals(38003650, ship.getModulesValue()),
                    () -> Assertions.assertEquals(6136672, ship.getHullValue())
            );
        } catch (Exception e) {
            log.error("error", e);
            log.info(line);
            Assertions.assertFalse(true);
        }
    }
}