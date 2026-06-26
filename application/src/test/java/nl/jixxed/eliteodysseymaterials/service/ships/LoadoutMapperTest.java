/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.CargoRack;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.DetailedSurfaceScanner;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.ShieldGenerator;
import nl.jixxed.eliteodysseymaterials.domain.ships.utility.KillWarrantScanner;
import nl.jixxed.eliteodysseymaterials.domain.ships.utility.PointDefence;
import nl.jixxed.eliteodysseymaterials.domain.ships.utility.SinkLauncher;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.Engineering;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.Loadout;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.Module;
import nl.jixxed.eliteodysseymaterials.service.ShipModuleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
class LoadoutMapperTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    {
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));
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
                Arguments.of("SEEKER_MISSILE_RACK_2_B_F_PRE.json", SeekerMissileRack.SEEKER_MISSILE_RACK_2_B_F_PRE, SlotType.HARDPOINT),
                Arguments.of("SEEKER_MISSILE_RACK_2_B_F_PRE_2.json", SeekerMissileRack.SEEKER_MISSILE_RACK_2_B_F_PRE_2, SlotType.HARDPOINT),
                Arguments.of("SEEKER_MISSILE_RACK_3_A_F_PRE.json", SeekerMissileRack.SEEKER_MISSILE_RACK_3_A_F_PRE, SlotType.HARDPOINT),
                Arguments.of("FRAGMENT_CANNON_1_E_G_PRE.json", FragmentCannon.FRAGMENT_CANNON_1_E_G_PRE, SlotType.HARDPOINT),
                Arguments.of("FRAGMENT_CANNON_3_C_G_PRE.json", FragmentCannon.FRAGMENT_CANNON_3_C_G_PRE, SlotType.HARDPOINT),
                Arguments.of("MULTI_CANNON_2_E_F_PRE.json", MultiCannon.MULTI_CANNON_2_E_F_PRE, SlotType.HARDPOINT),
                Arguments.of("IMPERIAL_HAMMER_PRE.json", RailGun.IMPERIAL_HAMMER_RAIL_GUN_2_B_F_PRE, SlotType.HARDPOINT),
                Arguments.of("RAIL_GUN_2_B_F_PRE.json", RailGun.RAIL_GUN_2_B_F_PRE, SlotType.HARDPOINT),
                Arguments.of("REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_PINK.json", RemoteReleaseFlakLauncher.REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_PINK, SlotType.HARDPOINT),
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
                Arguments.of("CARGO_RACK_6_E_PRE.json", CargoRack.CARGO_RACK_6_E_PRE, SlotType.OPTIONAL),
                Arguments.of("CARGO_RACK_5_E_PRE.json", CargoRack.CARGO_RACK_5_E_PRE, SlotType.OPTIONAL),
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

    private static Stream<Arguments> roundtripFixtures() {
        return Stream.of(
                Arguments.of(new LoadoutFixture("asp", "/ships/asp.json")),
                Arguments.of(new LoadoutFixture("mandalay", "/ships/mandalay.json")),
                Arguments.of(new LoadoutFixture("medium_transport", "/ships/medium_transport.json"))
        );
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

    @ParameterizedTest
    @MethodSource("preEngineeredModules")
    public void testIsPreEngineered(String filename, ShipModule shipModule, SlotType slotType) {
        String line = "";
        try (InputStream resourceAsStream = LoadoutMapperTest.class.getResourceAsStream("/ships/preengineered/" + filename)) {
            Assertions.assertTrue(resourceAsStream.available() > 0, "File is empty");
            ShipModuleService.getAllModules().stream().filter(ShipModule::isPreEngineered).map(ShipModule::getId).forEach(log::info);
            ShipModule.getBasicModules();
            final Module module = objectMapper.readValue(resourceAsStream, Module.class);
            Ship sa = Ship.KRAIT_MK_II;
            final List<ShipModule> potentialShipModules = LoadoutMapper.getPotentialShipModules(module.getItem(), slotType);
            log.debug(potentialShipModules.stream().map(ShipModule::getId).collect(Collectors.joining(", ")));
            final boolean preEngineered = LoadoutMapper.isPreEngineered(potentialShipModules, module.getEngineering().get(), null);
            Assertions.assertTrue(preEngineered);
            final Optional<ShipModule> first = potentialShipModules.stream().filter(ShipModule::isPreEngineered).filter(shipModule2 -> LoadoutMapper.matchingEngineering(shipModule2, module.getEngineering().get(), null)).findFirst();
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
        final boolean preEngineered = LoadoutMapper.isPreEngineered(List.of(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_A_V1_PRE, FrameShiftDrive.FRAME_SHIFT_DRIVE_5_A), engineering, null);
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
        final boolean preEngineered = LoadoutMapper.isPreEngineered(List.of(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_A_V1_PRE, FrameShiftDrive.FRAME_SHIFT_DRIVE_5_A), engineering, null);
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
        final boolean preEngineered = LoadoutMapper.isPreEngineered(List.of(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_A_V1_PRE, FrameShiftDrive.FRAME_SHIFT_DRIVE_5_A), engineering, null);
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
        final boolean preEngineered = LoadoutMapper.isPreEngineered(List.of(DetailedSurfaceScanner.DETAILED_SURFACE_SCANNER_1_I, DetailedSurfaceScanner.DETAILED_SURFACE_SCANNER_1_I_V1_PRE), engineering, null);
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
        final boolean preEngineered = LoadoutMapper.isPreEngineered(MiningLaser.MINING_LASERS, engineering, null);
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
        final boolean preEngineered = LoadoutMapper.isPreEngineered(SinkLauncher.SINK_LAUNCHERS, engineering, null);
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
        final boolean preEngineered = LoadoutMapper.isPreEngineered(List.of(DetailedSurfaceScanner.DETAILED_SURFACE_SCANNER_1_I, DetailedSurfaceScanner.DETAILED_SURFACE_SCANNER_1_I_V1_PRE), engineering, null);
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
        final boolean preEngineered = LoadoutMapper.isPreEngineered(List.of(GuardianGaussCannon.GUARDIAN_GAUSS_CANNON_1_D_F, GuardianGaussCannon.GUARDIAN_GAUSS_CANNON_1_D_F_PRE), engineering, null);
        Assertions.assertFalse(preEngineered);
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
    @Tag("manual")
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

    @ParameterizedTest
    @MethodSource("roundtripFixtures")
    void roundtripLoadoutShipLoadout(LoadoutFixture fixture) throws Exception {
        try (InputStream resourceAsStream = LoadoutMapperTest.class.getResourceAsStream(fixture.resourcePath)) {
            Assertions.assertTrue(resourceAsStream.available() > 0, fixture.name() + " file is empty");
            final Loadout original = objectMapper.readValue(resourceAsStream, Loadout.class);

            log.info("=== ORIGINAL LOADOUT: {} ===", fixture.name());
            log.info("Ship: {}", original.getShip());
            log.info("ShipName: {}", original.getShipName());
            original.getModules().forEach(mod -> {
                log.info("Module: slot={}, item={}", mod.getSlot(), mod.getItem());
                mod.getEngineering().ifPresent(eng -> {
                    log.info("  Engineering: BlueprintName={}, Level={}, Quality={}, ExperimentalEffect={}",
                            eng.getBlueprintName(), eng.getLevel(), eng.getQuality(), eng.getExperimentalEffect().orElse(null));
                    eng.getModifiers().forEach(m -> log.info("    Modifier: {}={}", m.getLabel(), m.getValue().orElse(null)));
                });
            });

            final nl.jixxed.eliteodysseymaterials.domain.ships.Ship ship = LoadoutMapper.toShip(original);
            final Loadout result = LoadoutMapper.toLoadout(
                    nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper.toShipConfiguration(ship, new nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration(), original.getShipName()));

            log.info("=== ROUNDTRIPPED LOADOUT: {} ===", fixture.name());
            log.info("Ship: {}", result.getShip());
            log.info("ShipName: {}", result.getShipName());
            result.getModules().forEach(mod -> {
                log.info("Module: slot={}, item={}", mod.getSlot(), mod.getItem());
                mod.getEngineering().ifPresent(eng -> {
                    log.info("  Engineering: BlueprintName={}, Level={}, Quality={}, ExperimentalEffect={}",
                            eng.getBlueprintName(), eng.getLevel(), eng.getQuality(), eng.getExperimentalEffect().orElse(null));
                    eng.getModifiers().forEach(m -> log.info("    Modifier: {}={}", m.getLabel(), m.getValue().orElse(null)));
                });
            });

            Assertions.assertEquals(original.getShip(), result.getShip().toLowerCase(), "Ship must match (case-insensitive)");
            Assertions.assertFalse(result.getModules().isEmpty(), fixture.name() + ": Modules must be present");

            // Every module item in the roundtripped loadout must match the original by slot name
            // (cosmetic/untemplateable slots may be dropped during roundtrip)
            final Map<String, Module> origBySlot = new HashMap<>();
            original.getModules().forEach(m -> origBySlot.put(m.getSlot(), m));
            final Map<String, nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.Module> resBySlot = new HashMap<>();
            result.getModules().forEach(m -> resBySlot.put(m.getSlot(), m));

            for (final var entry : resBySlot.entrySet()) {
                final String slotName = entry.getKey();
                final nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.Module resMod = entry.getValue();

                Assertions.assertNotNull(resMod.getSlot(), fixture.name() + ": Roundtripped module slot must be present");
                Assertions.assertNotNull(resMod.getItem(), fixture.name() + ": Roundtripped module item must be present");

                // Find matching original slot by stripping _SizeN suffix for comparison
                final String slotBase = slotName.replaceAll("_Size\\d+", "");
                final Module origMod = origBySlot.entrySet().stream()
                        .filter(e -> e.getKey().replaceAll("_Size\\d+", "").equals(slotBase))
                        .map(Map.Entry::getValue)
                        .findFirst()
                        .orElse(null);

                Assertions.assertNotNull(origMod, fixture.name() + ": slot '" + slotName + "' must exist in original");

                // Assert every module.item in the mapped loadout matches the original (case-insensitive)
                Assertions.assertEquals(origMod.getItem().toLowerCase(), resMod.getItem().toLowerCase(),
                        fixture.name() + ": item for slot '" + slotName + "' must match original");

                Assertions.assertEquals(origMod.getEngineering().isPresent(), resMod.getEngineering().isPresent(),
                        fixture.name() + ": engineering presence for slot '" + slotName + "' must match");

                if (origMod.getEngineering().isPresent() && resMod.getEngineering().isPresent()) {
                    final var origEng = origMod.getEngineering().get();
                    final var resEng = resMod.getEngineering().get();

                    Assertions.assertNotNull(resEng.getBlueprintName(), fixture.name() + ": Roundtripped BlueprintName for slot '" + slotName + "' must be present");
                    Assertions.assertNotNull(resEng.getLevel(), fixture.name() + ": Roundtripped Level for slot '" + slotName + "' must be present");
                    Assertions.assertNotNull(resEng.getQuality(), fixture.name() + ": Roundtripped Quality for slot '" + slotName + "' must be present");

                    Assertions.assertEquals(origEng.getLevel().intValue(), resEng.getLevel().intValue(), fixture.name() + ": Level for slot '" + slotName + "' must match");
                    Assertions.assertEquals(origEng.getQuality().intValue(), resEng.getQuality().intValue(), fixture.name() + ": Quality for slot '" + slotName + "' must match");

                    // Blueprint names match case-insensitively
                    final String origBlueprint = origEng.getBlueprintName();
                    final String resBlueprint = resEng.getBlueprintName();
                    Assertions.assertTrue(
                            resBlueprint.equalsIgnoreCase(origBlueprint),
                            fixture.name() + ": BlueprintName for slot '" + slotName + "' must match (" + origBlueprint + " vs " + resBlueprint + ")");

                    // Experimental effects match case-insensitively
                    Assertions.assertEquals(
                            origEng.getExperimentalEffect().map(String::toLowerCase).orElse(null),
                            resEng.getExperimentalEffect().map(String::toLowerCase).orElse(null),
                            fixture.name() + ": ExperimentalEffect for slot '" + slotName + "' must match");

                    // Verify modifier names and values match by label (not by position)
                    Assertions.assertEquals(origEng.getModifiers().size(), resEng.getModifiers().size(),
                            fixture.name() + ": modifier count for slot '" + slotName + "' must match");
                    for (int m = 0; m < origEng.getModifiers().size(); m++) {
                        final var origModEntry = origEng.getModifiers().get(m);
                        final var matchingRes = resEng.getModifiers().stream()
                                .filter(r -> r.getLabel().equals(origModEntry.getLabel()))
                                .findFirst();
                        Assertions.assertTrue(matchingRes.isPresent(),
                                fixture.name() + ": modifier '" + origModEntry.getLabel() + "' for slot '" + slotName + "' must exist in result");
                        // Compare as doubles to handle floating point precision
                        double origVal = origModEntry.getValue().map(v -> v.doubleValue()).orElse(Double.NaN);
                        double resVal = matchingRes.get().getValue().map(v -> v.doubleValue()).orElse(Double.NaN);
                        Assertions.assertEquals(origVal, resVal, 0.001,
                                fixture.name() + ": modifier '" + origModEntry.getLabel() + "' for slot '" + slotName + "' must match");
//                                fixture.name() + ": modifier '" + origModEntry.getLabel() + "' for slot '" + slotName + "' must match");
                    }
                }
            }
        }
    }

    @Test
    @Tag("manual")
    void roundtripAllLoadouts() throws Exception {
        String line = "";
        for (final var file : List.of("/ships/loadout.json")) {
            try (InputStream resourceAsStream = LoadoutMapperTest.class.getResourceAsStream(file)) {
                if (resourceAsStream == null || resourceAsStream.available() == 0) continue;
                final BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
                while (reader.ready()) {
                    line = reader.readLine();
                    log.info("===================================================");
                    log.info(line);
                    log.info("===================================================");
                    if (line == null || line.isBlank()) continue;
                    final Loadout original = objectMapper.readValue(line, Loadout.class);
                    final nl.jixxed.eliteodysseymaterials.domain.ships.Ship ship = LoadoutMapper.toShip(original);
                    final Loadout result = LoadoutMapper.toLoadout(
                            nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper.toShipConfiguration(ship, new nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration(), original.getShipName()));

                    log.info("=== ORIGINAL LOADOUT: {} ===", original.getShip());
                    log.info("Ship: {}", original.getShip());
                    log.info("ShipName: {}", original.getShipName());
                    original.getModules().forEach(mod -> {
                        log.info("Module: slot={}, item={}", mod.getSlot(), mod.getItem());
                        mod.getEngineering().ifPresent(eng -> {
                            log.info("  Engineering: BlueprintName={}, Level={}, Quality={}, ExperimentalEffect={}",
                                    eng.getBlueprintName(), eng.getLevel(), eng.getQuality(), eng.getExperimentalEffect().orElse(null));
                            eng.getModifiers().forEach(m -> log.info("    Modifier: {}={}", m.getLabel(), m.getValue().orElse(null)));
                        });
                    });


                    log.info("=== ROUNDTRIPPED LOADOUT: {} ===", result.getShip());
                    log.info("Ship: {}", result.getShip());
                    log.info("ShipName: {}", result.getShipName());
                    result.getModules().forEach(mod -> {
                        log.info("Module: slot={}, item={}", mod.getSlot(), mod.getItem());
                        mod.getEngineering().ifPresent(eng -> {
                            log.info("  Engineering: BlueprintName={}, Level={}, Quality={}, ExperimentalEffect={}",
                                    eng.getBlueprintName(), eng.getLevel(), eng.getQuality(), eng.getExperimentalEffect().orElse(null));
                            eng.getModifiers().forEach(m -> log.info("    Modifier: {}={}", m.getLabel(), m.getValue().orElse(null)));
                        });
                    });


                    Assertions.assertNotNull(result, file + ": toLoadout must not return null for " + original.getShip());
                    // Loadout doesn't expose ShipType, only getShip() string
                    Assertions.assertFalse(result.getModules().isEmpty(), file + ": modules must not be empty");

                    final Map<String, Module> origBySlot = new HashMap<>();
                    original.getModules().forEach(m -> origBySlot.put(m.getSlot(), m));
                    final Map<String, nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.Module> resBySlot = new HashMap<>();
                    result.getModules().forEach(m -> resBySlot.put(m.getSlot(), m));

                    for (final var entry : resBySlot.entrySet()) {


                        final String slotName = entry.getKey();
                        final nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.Module resMod = entry.getValue();
                        final Module origMod = origBySlot.get(slotName);

                        Assertions.assertNotNull(resMod.getSlot());
                        Assertions.assertNotNull(resMod.getItem());
                        Assertions.assertNotNull(origMod, file + ": slot '" + slotName + "' must exist in original");
                        Assertions.assertEquals(origMod.getItem(), resMod.getItem(),
                                file + ": item for slot '" + slotName + "' must match original");

                        if (origMod.getEngineering().isPresent()) {
                            final var origEng = origMod.getEngineering().get();
                            Assertions.assertTrue(resMod.getEngineering().isPresent(), file + ": engineering for slot '" + slotName + "' must be present");
                            final var resEng = resMod.getEngineering().get();

                            if (origEng.getQuality().doubleValue() == 0.0 || resEng.getQuality().doubleValue() == 0.0) {//legacy
                                continue;
                            }
                            Assertions.assertNotNull(resEng.getBlueprintName());
                            Assertions.assertTrue(resEng.getBlueprintName().equalsIgnoreCase(origEng.getBlueprintName()),
                                    file + ": blueprintName for slot '" + slotName + "' must match." + origEng.getBlueprintName() + "<>" + resEng.getBlueprintName());

                            if (!(resEng.getLevel().equals(BigInteger.valueOf(5)) && origEng.getLevel().equals(BigInteger.ONE))) {//some pre-eng wont match
                                Assertions.assertEquals(origEng.getLevel().intValue(), resEng.getLevel().intValue(), file + ": level for slot '" + slotName + "' must match");
                            }
                            Assertions.assertEquals(
                                    origEng.getExperimentalEffect().map(String::toLowerCase).orElse(null),
                                    resEng.getExperimentalEffect().map(String::toLowerCase).orElse(null),
                                    file + ": experimentalEffect for slot '" + slotName + "' must match");

                            // Verify modifier names and values match by label (not by position)
                            Assertions.assertEquals(origEng.getModifiers().stream().filter(modifier -> !modifier.getLabel().equalsIgnoreCase("DamageType")
                                            && !modifier.getLabel().equalsIgnoreCase("CabinClass")
                                            && !modifier.getLabel().equalsIgnoreCase("WeaponMode")).count(), resEng.getModifiers().size(),
                                    file + ": modifier count for slot '" + slotName + "' must match");
                            for (int m = 0; m < origEng.getModifiers().size(); m++) {
                                final var origModEntry = origEng.getModifiers().get(m);
                                final var matchingRes = resEng.getModifiers().stream()
                                        .filter(r -> r.getLabel().equals(origModEntry.getLabel()))
                                        .findFirst();
                                if (!origModEntry.getLabel().equalsIgnoreCase("DamageType")
                                        && !origModEntry.getLabel().equalsIgnoreCase("CabinClass")
                                        && !origModEntry.getLabel().equalsIgnoreCase("WeaponMode")) {
                                    Assertions.assertTrue(matchingRes.isPresent(),
                                            file + ": modifier '" + origModEntry.getLabel() + "' for slot '" + slotName + "' must exist in result");
                                    double origVal = origModEntry.getValue().map(v -> v.doubleValue()).orElse(Double.NaN);
                                    double resVal = matchingRes.get().getValue().map(v -> v.doubleValue()).orElse(Double.NaN);
                                    Assertions.assertEquals(origVal, resVal, 0.001,
                                            file + ": modifier '" + origModEntry.getLabel() + "' for slot '" + slotName + "' must match");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private record LoadoutFixture(String name, String resourcePath) {
    }
}