package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint.PulseLaser;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.special.CargoHatch;
import nl.jixxed.eliteodysseymaterials.domain.ships.special.FuelTank;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.enums.PassengerCabinType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public class Ship {
    public static final Ship SIDE_WINDER = new Ship(
            ShipType.SIDE_WINDER,
            5070,
            32000,
            ShipSpecs.SIDE_WINDER,
            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1107).y(597).index(0).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1043).y(663).index(1).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1035).y(675).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(795).y(430).index(1).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.SIDE_WINDER_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(2).shipModule(PowerPlant.POWER_PLANT_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(2).shipModule(Thrusters.THRUSTERS_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(2).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(1).shipModule(LifeSupport.LIFE_SUPPORT_1_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(1).shipModule(PowerDistributor.POWER_DISTRIBUTOR_1_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(1).shipModule(Sensors.SENSORS_1_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(1).shipModule(FuelTank.FUEL_TANK_1_C).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(2).shipModule(ShieldGenerator.SHIELD_GENERATOR_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(2).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build()
            ));

    public static final Ship EAGLE = new Ship(//Eagle MkII
            ShipType.EAGLE,
            7490,
            44800,
            ShipSpecs.EAGLE,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1028).y(569).index(0).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(970).y(561).index(1).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(946).y(534).index(2).slotSize(1).build()
            ),
            List.of(//checked
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(737).y(520).index(0).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.EAGLE_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(2).shipModule(PowerPlant.POWER_PLANT_2_D).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(3).shipModule(Thrusters.THRUSTERS_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(3).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(1).shipModule(LifeSupport.LIFE_SUPPORT_1_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(2).shipModule(PowerDistributor.POWER_DISTRIBUTOR_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(2).shipModule(Sensors.SENSORS_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(2).shipModule(FuelTank.FUEL_TANK_2_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(3).shipModule(ShieldGenerator.SHIELD_GENERATOR_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(2).shipModule(CargoRack.CARGO_RACK_1_E).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(2).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(1).namedIndex(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(1).namedIndex(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).namedIndex(5).shipModule(Computer.SUPERCRUISE_ASSIST).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).namedIndex(6).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build()
            ));

    public static final Ship HAULER = new Ship(
            ShipType.HAULER,
            8160,
            52720,
            ShipSpecs.HAULER,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(927).y(465).index(0).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(//checked
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1079).y(476).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(896).y(647).index(1).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.HAULER_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(2).shipModule(PowerPlant.POWER_PLANT_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(2).shipModule(Thrusters.THRUSTERS_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(2).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(1).shipModule(LifeSupport.LIFE_SUPPORT_1_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(1).shipModule(PowerDistributor.POWER_DISTRIBUTOR_1_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(1).shipModule(Sensors.SENSORS_1_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(2).shipModule(FuelTank.FUEL_TANK_2_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(2).shipModule(ShieldGenerator.SHIELD_GENERATOR_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build()
            ));

    public static final Ship ADDER = new Ship(
            ShipType.ADDER,
            18710,
            87810,
            ShipSpecs.ADDER,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1003).y(559).index(0).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1155).y(565).index(1).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1025).y(421).index(2).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(755).y(557).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(925).y(725).index(1).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.ADDER_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(3).shipModule(PowerPlant.POWER_PLANT_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(3).shipModule(Thrusters.THRUSTERS_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(3).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(1).shipModule(LifeSupport.LIFE_SUPPORT_1_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(2).shipModule(PowerDistributor.POWER_DISTRIBUTOR_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(3).shipModule(Sensors.SENSORS_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(3).shipModule(FuelTank.FUEL_TANK_3_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(3).shipModule(ShieldGenerator.SHIELD_GENERATOR_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(2).shipModule(CargoRack.CARGO_RACK_1_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build()
            ));

    public static final Ship EMPIRE_EAGLE = new Ship(//Imperial Eagle
            ShipType.EMPIRE_EAGLE,
            50890,
            110830,
            ShipSpecs.EMPIRE_EAGLE,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1032).y(573).index(0).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(972).y(562).index(1).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(942).y(535).index(2).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(736).y(521).index(0).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.EMPIRE_EAGLE_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(3).shipModule(PowerPlant.POWER_PLANT_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(3).shipModule(Thrusters.THRUSTERS_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(3).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(1).shipModule(LifeSupport.LIFE_SUPPORT_1_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(2).shipModule(PowerDistributor.POWER_DISTRIBUTOR_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(2).shipModule(Sensors.SENSORS_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(2).shipModule(FuelTank.FUEL_TANK_2_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(3).shipModule(ShieldGenerator.SHIELD_GENERATOR_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(2).shipModule(CargoRack.CARGO_RACK_1_E).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(2).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(1).namedIndex(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(1).namedIndex(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).namedIndex(5).shipModule(Computer.SUPERCRUISE_ASSIST).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).namedIndex(6).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build()
            ));

    public static final Ship VIPER_MK_III = new Ship(//viper MK III
            ShipType.VIPER_MK_III,
            74610,
            142930,
            ShipSpecs.VIPER_MK_III,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1064).y(579).index(0).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(939).y(448).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1239).y(712).index(2).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1174).y(782).index(3).slotSize(1).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(875).y(380).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(791).y(462).index(1).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.VIPER_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(3).shipModule(PowerPlant.POWER_PLANT_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(3).shipModule(Thrusters.THRUSTERS_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(3).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(2).shipModule(LifeSupport.LIFE_SUPPORT_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(3).shipModule(PowerDistributor.POWER_DISTRIBUTOR_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(3).shipModule(Sensors.SENSORS_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(2).shipModule(FuelTank.FUEL_TANK_2_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(3).shipModule(ShieldGenerator.SHIELD_GENERATOR_3_E).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(2).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(2).namedIndex(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(1).namedIndex(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).namedIndex(5).shipModule(Computer.SUPERCRUISE_ASSIST).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).namedIndex(6).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build()
            ));

    public static final Ship COBRA_MK_III = new Ship(
            ShipType.COBRA_MK_III,
            186260,
            349720,
            ShipSpecs.COBRA_MK_III,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1257).y(702).index(0).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1165).y(799).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1085).y(817).index(2).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(606).y(340).index(3).slotSize(1).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1094).y(754).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(718).y(373).index(1).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.COBRA_MK_III_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(4).shipModule(PowerPlant.POWER_PLANT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(4).shipModule(Thrusters.THRUSTERS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(4).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(3).shipModule(LifeSupport.LIFE_SUPPORT_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(3).shipModule(PowerDistributor.POWER_DISTRIBUTOR_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(3).shipModule(Sensors.SENSORS_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(4).shipModule(FuelTank.FUEL_TANK_4_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(4).shipModule(ShieldGenerator.SHIELD_GENERATOR_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(2).shipModule(CargoRack.CARGO_RACK_1_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build()
            ));

    public static final Ship VIPER_MK_IV = new Ship(
            ShipType.VIPER_MK_IV,
            290680,
            437930,
            ShipSpecs.VIPER_MK_IV,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1090).y(562).index(0).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(948).y(408).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1273).y(720).index(2).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1211).y(791).index(3).slotSize(1).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(932).y(395).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(839).y(486).index(1).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.VIPER_MK_IV_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(4).shipModule(PowerPlant.POWER_PLANT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(4).shipModule(Thrusters.THRUSTERS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(4).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(2).shipModule(LifeSupport.LIFE_SUPPORT_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(3).shipModule(PowerDistributor.POWER_DISTRIBUTOR_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(3).shipModule(Sensors.SENSORS_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(4).shipModule(FuelTank.FUEL_TANK_4_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(3).shipModule(ShieldGenerator.SHIELD_GENERATOR_3_E).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(3).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(2).namedIndex(4).shipModule(CargoRack.CARGO_RACK_1_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(2).namedIndex(5).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).namedIndex(6).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(1).namedIndex(7).shipModule(Computer.SUPERCRUISE_ASSIST).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(1).namedIndex(8).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build()
            ));

    public static final Ship DIAMOND_BACK = new Ship(//scout
            ShipType.DIAMOND_BACK,
            441800,
            564330,
            ShipSpecs.DIAMOND_BACK,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(3).x(779).y(836).index(0).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1163).y(327).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(3).x(629).y(889).index(2).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1334).y(290).index(3).slotSize(1).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1251).y(756).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(820).y(413).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1177).y(471).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(872).y(782).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.DIAMOND_BACK_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(4).shipModule(PowerPlant.POWER_PLANT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(4).shipModule(Thrusters.THRUSTERS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(4).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(2).shipModule(LifeSupport.LIFE_SUPPORT_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(3).shipModule(PowerDistributor.POWER_DISTRIBUTOR_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(2).shipModule(Sensors.SENSORS_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(4).shipModule(FuelTank.FUEL_TANK_4_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(3).shipModule(ShieldGenerator.SHIELD_GENERATOR_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build()
            ));

    public static final Ship COBRA_MK_IV = new Ship(
            ShipType.COBRA_MK_IV,
            584200,
            764720,
            ShipSpecs.COBRA_MK_IV,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1278).y(680).index(0).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1177).y(788).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(931).y(385).index(2).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(842).y(467).index(3).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1001).y(514).index(4).slotSize(1).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(687).y(330).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1092).y(744).index(1).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.COBRA_MK_IV_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(4).shipModule(PowerPlant.POWER_PLANT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(4).shipModule(Thrusters.THRUSTERS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(4).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(3).shipModule(LifeSupport.LIFE_SUPPORT_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(3).shipModule(PowerDistributor.POWER_DISTRIBUTOR_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(3).shipModule(Sensors.SENSORS_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(4).shipModule(FuelTank.FUEL_TANK_4_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(4).shipModule(ShieldGenerator.SHIELD_GENERATOR_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(2).shipModule(CargoRack.CARGO_RACK_1_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship TYPE_6 = new Ship(
            ShipType.TYPE_6,
            858010,
            1045950,
            ShipSpecs.TYPE_6,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1178).y(649).index(0).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1051).y(509).index(1).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1143).y(646).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1261).y(476).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(804).y(757).index(2).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.TYPE_6_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(3).shipModule(PowerPlant.POWER_PLANT_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(4).shipModule(Thrusters.THRUSTERS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(4).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(2).shipModule(LifeSupport.LIFE_SUPPORT_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(3).shipModule(PowerDistributor.POWER_DISTRIBUTOR_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(2).shipModule(Sensors.SENSORS_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(4).shipModule(FuelTank.FUEL_TANK_4_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(3).shipModule(ShieldGenerator.SHIELD_GENERATOR_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(2).shipModule(CargoRack.CARGO_RACK_1_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship DOLPHIN = new Ship(
            ShipType.DOLPHIN,
            1095780,
            1337320,
            ShipSpecs.DOLPHIN,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1165).y(412).index(0).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1204).y(457).index(1).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1409).y(303).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(868).y(403).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(828).y(443).index(2).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.DOLPHIN_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(4).shipModule(PowerPlant.POWER_PLANT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(5).shipModule(Thrusters.THRUSTERS_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(4).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(4).shipModule(LifeSupport.LIFE_SUPPORT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(3).shipModule(PowerDistributor.POWER_DISTRIBUTOR_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(3).shipModule(Sensors.SENSORS_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(4).shipModule(FuelTank.FUEL_TANK_4_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(5).shipModule(PassengerCabin.ECONOMY_CLASS_PASSENGER_CABIN_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(4).shipModule(ShieldGenerator.SHIELD_GENERATOR_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(2).shipModule(CargoRack.CARGO_RACK_1_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build()
            ));

    public static final Ship DIAMOND_BACK_XL = new Ship(//explorer
            ShipType.DIAMOND_BACK_XL,
            1616160,
            1894760,
            ShipSpecs.DIAMOND_BACK_XL,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1148).y(492).index(0).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(3).x(734).y(877).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1196).y(878).index(2).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1305).y(797).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(695).y(317).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1201).y(456).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(854).y(813).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.DIAMOND_BACK_XL_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(4).shipModule(PowerPlant.POWER_PLANT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(4).shipModule(Thrusters.THRUSTERS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(5).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(3).shipModule(LifeSupport.LIFE_SUPPORT_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(4).shipModule(PowerDistributor.POWER_DISTRIBUTOR_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(3).shipModule(Sensors.SENSORS_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(5).shipModule(FuelTank.FUEL_TANK_5_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(4).shipModule(ShieldGenerator.SHIELD_GENERATOR_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build()
            ));

    public static final Ship EMPIRE_COURIER = new Ship(//imperial courier
            ShipType.EMPIRE_COURIER,
            2462010,
            2542930,
            ShipSpecs.EMPIRE_COURIER,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(898).y(825).index(0).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1235).y(479).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1273).y(345).index(2).slotSize(2).build()
            ),
            List.of(//checked
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1039).y(580).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(896).y(471).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(798).y(740).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1138).y(402).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.EMPIRE_COURIER_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(4).shipModule(PowerPlant.POWER_PLANT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(3).shipModule(Thrusters.THRUSTERS_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(3).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(1).shipModule(LifeSupport.LIFE_SUPPORT_1_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(3).shipModule(PowerDistributor.POWER_DISTRIBUTOR_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(2).shipModule(Sensors.SENSORS_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(3).shipModule(FuelTank.FUEL_TANK_3_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(2).shipModule(ShieldGenerator.SHIELD_GENERATOR_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(2).shipModule(CargoRack.CARGO_RACK_1_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(2).shipModule(CargoRack.CARGO_RACK_1_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build()
            ));

    public static final Ship INDEPENDANT_TRADER = new Ship(//keelback
            ShipType.INDEPENDANT_TRADER,
            2937840,
            3126150,
            ShipSpecs.INDEPENDANT_TRADER,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1233).y(637).index(0).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1124).y(751).index(1).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1187).y(646).index(2).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1060).y(505).index(3).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1149).y(650).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1271).y(471).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(849).y(433).index(2).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.INDEPENDANT_TRADER_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(4).shipModule(PowerPlant.POWER_PLANT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(4).shipModule(Thrusters.THRUSTERS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(4).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(1).shipModule(LifeSupport.LIFE_SUPPORT_1_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(3).shipModule(PowerDistributor.POWER_DISTRIBUTOR_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(2).shipModule(Sensors.SENSORS_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(4).shipModule(FuelTank.FUEL_TANK_4_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(3).shipModule(ShieldGenerator.SHIELD_GENERATOR_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(2).shipModule(CargoRack.CARGO_RACK_1_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship ASP_SCOUT = new Ship(
            ShipType.ASP_SCOUT,
            3811220,
            3961160,
            ShipSpecs.ASP_SCOUT,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1232).y(700).index(0).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1166).y(772).index(1).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1297).y(510).index(2).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1280).y(491).index(3).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(713).y(430).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(833).y(318).index(1).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.ASP_SCOUT_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(4).shipModule(PowerPlant.POWER_PLANT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(4).shipModule(Thrusters.THRUSTERS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(4).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(3).shipModule(LifeSupport.LIFE_SUPPORT_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(4).shipModule(PowerDistributor.POWER_DISTRIBUTOR_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(4).shipModule(Sensors.SENSORS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(4).shipModule(FuelTank.FUEL_TANK_4_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(5).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(3).shipModule(ShieldGenerator.SHIELD_GENERATOR_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship VULTURE = new Ship(
            ShipType.VULTURE,
            4670100,
            4925620,
            ShipSpecs.VULTURE,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1205).y(538).index(0).slotSize(3).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1002).y(743).index(1).slotSize(3).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1066).y(410).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(865).y(605).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1356).y(735).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1207).y(899).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.VULTURE_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(4).shipModule(PowerPlant.POWER_PLANT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(5).shipModule(Thrusters.THRUSTERS_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(4).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(3).shipModule(LifeSupport.LIFE_SUPPORT_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(5).shipModule(PowerDistributor.POWER_DISTRIBUTOR_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(4).shipModule(Sensors.SENSORS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(3).shipModule(FuelTank.FUEL_TANK_3_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(5).shipModule(ShieldGenerator.SHIELD_GENERATOR_5_E).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(1).slotSize(5).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(4).namedIndex(2).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(2).namedIndex(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(1).namedIndex(5).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).namedIndex(6).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).namedIndex(7).shipModule(Computer.SUPERCRUISE_ASSIST).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(1).namedIndex(8).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build()
            ));

    public static final Ship ASP = new Ship(//asp explorer
            ShipType.ASP,
            6137180,
            6661160,
            ShipSpecs.ASP,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1163).y(773).index(0).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1229).y(702).index(1).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1344).y(520).index(2).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1261).y(429).index(3).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1297).y(515).index(4).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1280).y(496).index(5).slotSize(1).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1099).y(613).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(714).y(432).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(833).y(323).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(752).y(777).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.ASP_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(5).shipModule(PowerPlant.POWER_PLANT_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(5).shipModule(Thrusters.THRUSTERS_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(5).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(4).shipModule(LifeSupport.LIFE_SUPPORT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(4).shipModule(PowerDistributor.POWER_DISTRIBUTOR_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(5).shipModule(Sensors.SENSORS_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(5).shipModule(FuelTank.FUEL_TANK_5_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(6).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(5).shipModule(ShieldGenerator.SHIELD_GENERATOR_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(2).shipModule(CargoRack.CARGO_RACK_1_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship FEDERATION_DROPSHIP = new Ship(
            ShipType.FEDERATION_DROPSHIP,
            13501480,
            14314210,
            ShipSpecs.FEDERATION_DROPSHIP,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1318).y(510).index(0).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1088).y(548).index(1).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1207).y(681).index(2).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(3).x(991).y(766).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(944).y(766).index(4).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(919).y(805).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(787).y(683).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1003).y(594).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1132).y(724).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.FEDERATION_DROPSHIP_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(6).shipModule(PowerPlant.POWER_PLANT_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(6).shipModule(Thrusters.THRUSTERS_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(5).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(5).shipModule(LifeSupport.LIFE_SUPPORT_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(6).shipModule(PowerDistributor.POWER_DISTRIBUTOR_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(4).shipModule(Sensors.SENSORS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(4).shipModule(FuelTank.FUEL_TANK_4_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(6).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(5).shipModule(ShieldGenerator.SHIELD_GENERATOR_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(4).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(5).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(3).namedIndex(5).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(3).namedIndex(6).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(2).namedIndex(9).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(1).namedIndex(10).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship TYPE_7 = new Ship(
            ShipType.TYPE_7,
            16774470,
            17472250,
            ShipSpecs.TYPE_7,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1226).y(450).index(0).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1186).y(408).index(1).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1001).y(585).index(2).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1035).y(556).index(3).slotSize(1).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(926).y(465).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(903).y(488).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1018).y(459).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1112).y(565).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.TYPE_7_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(5).shipModule(PowerPlant.POWER_PLANT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(5).shipModule(Thrusters.THRUSTERS_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(5).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(4).shipModule(LifeSupport.LIFE_SUPPORT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(4).shipModule(PowerDistributor.POWER_DISTRIBUTOR_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(3).shipModule(Sensors.SENSORS_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(5).shipModule(FuelTank.FUEL_TANK_5_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(6).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(6).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(6).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(5).shipModule(ShieldGenerator.SHIELD_GENERATOR_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship TYPE_X = new Ship(//chieftan
            ShipType.TYPE_X,
            18603850,
            19382250,
            ShipSpecs.TYPE_X,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1030).y(534).index(0).slotSize(3).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1190).y(688).index(1).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1380).y(447).index(2).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1247).y(747).index(3).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1182).y(617).index(4).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1119).y(690).index(5).slotSize(1).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(920).y(602).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1069).y(457).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(902).y(639).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(979).y(714).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.TYPE_X_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(6).shipModule(PowerPlant.POWER_PLANT_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(6).shipModule(Thrusters.THRUSTERS_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(5).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(5).shipModule(LifeSupport.LIFE_SUPPORT_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(6).shipModule(PowerDistributor.POWER_DISTRIBUTOR_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(4).shipModule(Sensors.SENSORS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(4).shipModule(FuelTank.FUEL_TANK_4_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(6).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(5).shipModule(ShieldGenerator.SHIELD_GENERATOR_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(3).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(4).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(5).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(2).namedIndex(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(2).namedIndex(5).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(1).namedIndex(6).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship FEDERATION_DROPSHIP_MK_II = new Ship(//Federal Assault Ship
            ShipType.FEDERATION_DROPSHIP_MK_II,
            19102490,
            19814210,
            ShipSpecs.FEDERATION_DROPSHIP_MK_II,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1317).y(514).index(0).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1130).y(643).index(1).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1211).y(683).index(2).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1086).y(551).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(917).y(801).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(791).y(683).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1121).y(731).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(990).y(610).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.FEDERATION_DROPSHIP_MK_II_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(6).shipModule(PowerPlant.POWER_PLANT_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(6).shipModule(Thrusters.THRUSTERS_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(5).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(5).shipModule(LifeSupport.LIFE_SUPPORT_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(6).shipModule(PowerDistributor.POWER_DISTRIBUTOR_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(4).shipModule(Sensors.SENSORS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(4).shipModule(FuelTank.FUEL_TANK_4_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(5).shipModule(ShieldGenerator.SHIELD_GENERATOR_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(3).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(4).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(3).namedIndex(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(2).namedIndex(5).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(2).namedIndex(6).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(1).namedIndex(7).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship EMPIRE_TRADER = new Ship(//imperial clipper
            ShipType.EMPIRE_TRADER,
            21108270,
            22295860,
            ShipSpecs.EMPIRE_TRADER,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1030).y(156).index(0).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(481).y(652).index(1).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(969).y(717).index(2).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(775).y(526).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(//checked
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(841).y(518).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(979).y(658).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(3).x(1168).y(463).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(752).y(463).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.EMPIRE_TRADER_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(6).shipModule(PowerPlant.POWER_PLANT_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(6).shipModule(Thrusters.THRUSTERS_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(5).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(5).shipModule(LifeSupport.LIFE_SUPPORT_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(6).shipModule(PowerDistributor.POWER_DISTRIBUTOR_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(5).shipModule(Sensors.SENSORS_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(4).shipModule(FuelTank.FUEL_TANK_4_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(7).shipModule(CargoRack.CARGO_RACK_6_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(6).shipModule(ShieldGenerator.SHIELD_GENERATOR_6_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(2).shipModule(CargoRack.CARGO_RACK_1_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship TYPE_X_2 = new Ship(//crusader
            ShipType.TYPE_X_2,
            22087940,
            22866340,
            ShipSpecs.TYPE_X_2,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1190).y(688).index(0).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1049).y(549).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1384).y(442).index(2).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1254).y(754).index(3).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1183).y(612).index(4).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1112).y(687).index(5).slotSize(1).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(920).y(602).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1071).y(457).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(895).y(635).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(989).y(730).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.TYPE_X_2_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(6).shipModule(PowerPlant.POWER_PLANT_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(6).shipModule(Thrusters.THRUSTERS_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(5).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(5).shipModule(LifeSupport.LIFE_SUPPORT_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(6).shipModule(PowerDistributor.POWER_DISTRIBUTOR_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(4).shipModule(Sensors.SENSORS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(4).shipModule(FuelTank.FUEL_TANK_4_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(6).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(5).shipModule(ShieldGenerator.SHIELD_GENERATOR_5_E).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(2).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(3).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(4).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(3).namedIndex(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(3).namedIndex(4).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(2).namedIndex(5).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(2).namedIndex(6).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(1).namedIndex(7).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship TYPE_X_3 = new Ship(//challenger
            ShipType.TYPE_X_3,
            29561170,
            30472250,
            ShipSpecs.TYPE_X_3,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1194).y(687).index(0).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1177).y(495).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1382).y(444).index(2).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1264).y(595).index(3).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1252).y(753).index(4).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1183).y(614).index(5).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1112).y(688).index(6).slotSize(1).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(921).y(602).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1068).y(454).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(902).y(639).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(978).y(714).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.TYPE_X_3_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(6).shipModule(PowerPlant.POWER_PLANT_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(6).shipModule(Thrusters.THRUSTERS_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(5).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(5).shipModule(LifeSupport.LIFE_SUPPORT_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(6).shipModule(PowerDistributor.POWER_DISTRIBUTOR_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(4).shipModule(Sensors.SENSORS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(4).shipModule(FuelTank.FUEL_TANK_4_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).namedIndex(1).slotSize(6).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).namedIndex(2).slotSize(6).shipModule(ShieldGenerator.SHIELD_GENERATOR_6_E).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(2).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(3).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(4).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).namedIndex(3).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).namedIndex(4).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).namedIndex(5).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).namedIndex(6).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).namedIndex(7).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship FEDERATION_GUNSHIP = new Ship(
            ShipType.FEDERATION_GUNSHIP,
            34806280,
            35814210,
            ShipSpecs.FEDERATION_GUNSHIP,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1308).y(405).index(0).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1240).y(885).index(1).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(3).x(680).y(885).index(2).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1202).y(568).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1085).y(438).index(4).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1119).y(537).index(5).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1083).y(572).index(6).slotSize(1).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(998).y(498).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1123).y(617).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1032).y(381).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(876).y(531).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.FEDERATION_GUNSHIP_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(6).shipModule(PowerPlant.POWER_PLANT_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(6).shipModule(Thrusters.THRUSTERS_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(5).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(5).shipModule(LifeSupport.LIFE_SUPPORT_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(7).shipModule(PowerDistributor.POWER_DISTRIBUTOR_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(5).shipModule(Sensors.SENSORS_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(4).shipModule(FuelTank.FUEL_TANK_4_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(6).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(6).shipModule(ShieldGenerator.SHIELD_GENERATOR_6_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(3).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(4).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(5).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(2).namedIndex(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(2).namedIndex(5).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(1).namedIndex(6).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship KRAIT_LIGHT = new Ship(
            ShipType.KRAIT_LIGHT,
            35732880,
            37472250,
            ShipSpecs.KRAIT_LIGHT,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1003).y(659).index(0).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1114).y(546).index(1).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1395).y(269).index(2).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1490).y(392).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(768).y(234).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(4).x(1152).y(234).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(996).y(570).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1103).y(682).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.KRAIT_LIGHT_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(7).shipModule(PowerPlant.POWER_PLANT_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(6).shipModule(Thrusters.THRUSTERS_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(5).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(4).shipModule(LifeSupport.LIFE_SUPPORT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(7).shipModule(PowerDistributor.POWER_DISTRIBUTOR_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(6).shipModule(Sensors.SENSORS_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(5).shipModule(FuelTank.FUEL_TANK_5_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(6).shipModule(ShieldGenerator.SHIELD_GENERATOR_6_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(5).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(5).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(2).shipModule(CargoRack.CARGO_RACK_1_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship KRAIT_MK_II = new Ship(
            ShipType.KRAIT_MK_II,
            44152080,
            45814210,
            ShipSpecs.KRAIT_MK_II,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1170).y(681).index(0).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1004).y(658).index(1).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1113).y(547).index(2).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1395).y(270).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1489).y(391).index(4).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(707).y(577).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(989).y(309).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(994).y(568).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1103).y(683).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.KRAIT_MK_II_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(7).shipModule(PowerPlant.POWER_PLANT_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(6).shipModule(Thrusters.THRUSTERS_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(5).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(4).shipModule(LifeSupport.LIFE_SUPPORT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(7).shipModule(PowerDistributor.POWER_DISTRIBUTOR_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(6).shipModule(Sensors.SENSORS_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(5).shipModule(FuelTank.FUEL_TANK_5_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(6).shipModule(ShieldGenerator.SHIELD_GENERATOR_6_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(6).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(5).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(2).shipModule(CargoRack.CARGO_RACK_1_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship ORCA = new Ship(
            ShipType.ORCA,
            47792090,
            48539890,
            ShipSpecs.ORCA,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1261).y(407).index(0).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1064).y(463).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1132).y(537).index(2).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1210).y(436).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(677).y(739).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(871).y(449).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(883).y(436).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.ORCA_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(5).shipModule(PowerPlant.POWER_PLANT_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(6).shipModule(Thrusters.THRUSTERS_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(5).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(6).shipModule(LifeSupport.LIFE_SUPPORT_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(5).shipModule(PowerDistributor.POWER_DISTRIBUTOR_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(4).shipModule(Sensors.SENSORS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(5).shipModule(FuelTank.FUEL_TANK_5_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(6).shipModule(PassengerCabin.ECONOMY_CLASS_PASSENGER_CABIN_6_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(5).shipModule(PassengerCabin.ECONOMY_CLASS_PASSENGER_CABIN_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(5).shipModule(ShieldGenerator.SHIELD_GENERATOR_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship FER_DE_LANCE = new Ship(
            ShipType.FER_DE_LANCE,
            51126980,
            51567040,
            ShipSpecs.FER_DE_LANCE,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1080).y(572).index(0).slotSize(4).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1158).y(733).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1220).y(668).index(2).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1140).y(529).index(3).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(956).y(678).index(4).slotSize(2).build()
            ),
            List.of(//checked
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(909).y(539).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(856).y(606).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(961).y(712).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1058).y(691).index(3).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(892).y(365).index(4).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(795).y(457).index(5).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.FER_DE_LANCE_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(6).shipModule(PowerPlant.POWER_PLANT_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(5).shipModule(Thrusters.THRUSTERS_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(4).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(4).shipModule(LifeSupport.LIFE_SUPPORT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(6).shipModule(PowerDistributor.POWER_DISTRIBUTOR_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(4).shipModule(Sensors.SENSORS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(3).shipModule(FuelTank.FUEL_TANK_3_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(4).shipModule(ShieldGenerator.SHIELD_GENERATOR_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship MAMBA = new Ship(
            ShipType.MAMBA,
            55434290,
            55867040,
            ShipSpecs.MAMBA,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(951).y(507).index(0).slotSize(4).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1194).y(498).index(1).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(959).y(747).index(2).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1355).y(696).index(3).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1199).y(856).index(4).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1244).y(632).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1045).y(410).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(810).y(905).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(540).y(667).index(3).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1121).y(786).index(4).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1277).y(632).index(5).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.MAMBA_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(6).shipModule(PowerPlant.POWER_PLANT_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(5).shipModule(Thrusters.THRUSTERS_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(4).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(4).shipModule(LifeSupport.LIFE_SUPPORT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(6).shipModule(PowerDistributor.POWER_DISTRIBUTOR_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(4).shipModule(Sensors.SENSORS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(3).shipModule(FuelTank.FUEL_TANK_3_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(4).shipModule(ShieldGenerator.SHIELD_GENERATOR_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship PYTHON = new Ship(
            ShipType.PYTHON,
            55316050,
            56978180,
            ShipSpecs.PYTHON,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(996).y(714).index(0).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1154).y(552).index(1).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1595).y(324).index(2).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1209).y(846).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1314).y(729).index(4).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1211).y(750).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1240).y(718).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(646).y(500).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1076).y(910).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.PYTHON_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(7).shipModule(PowerPlant.POWER_PLANT_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(6).shipModule(Thrusters.THRUSTERS_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(5).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(4).shipModule(LifeSupport.LIFE_SUPPORT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(7).shipModule(PowerDistributor.POWER_DISTRIBUTOR_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(6).shipModule(Sensors.SENSORS_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(5).shipModule(FuelTank.FUEL_TANK_5_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(6).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(6).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(6).shipModule(ShieldGenerator.SHIELD_GENERATOR_6_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(5).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(2).shipModule(CargoRack.CARGO_RACK_1_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship TYPE_9 = new Ship(
            ShipType.TYPE_9,
            72108220,
            76555840,
            ShipSpecs.TYPE_9,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1152).y(496).index(0).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1227).y(435).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(904).y(765).index(2).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1323).y(425).index(3).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1247).y(341).index(4).slotSize(1).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1117).y(365).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(806).y(672).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(880).y(717).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(709).y(550).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.TYPE_9_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(6).shipModule(PowerPlant.POWER_PLANT_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(7).shipModule(Thrusters.THRUSTERS_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(6).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(5).shipModule(LifeSupport.LIFE_SUPPORT_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(6).shipModule(PowerDistributor.POWER_DISTRIBUTOR_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(4).shipModule(Sensors.SENSORS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(6).shipModule(FuelTank.FUEL_TANK_6_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(8).namedIndex(0).shipModule(CargoRack.CARGO_RACK_7_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(8).namedIndex(1).shipModule(CargoRack.CARGO_RACK_7_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(7).namedIndex(2).shipModule(CargoRack.CARGO_RACK_6_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(6).namedIndex(3).shipModule(ShieldGenerator.SHIELD_GENERATOR_6_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(5).namedIndex(4).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(4).namedIndex(5).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(4).namedIndex(6).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(3).namedIndex(7).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(3).namedIndex(8).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(2).namedIndex(11).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(10).slotSize(1).namedIndex(12).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship BELUGA_LINER = new Ship(
            ShipType.BELUGA_LINER,
            79686090,
            84532760,
            ShipSpecs.BELUGA_LINER,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(852).y(447).index(0).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(822).y(475).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1299).y(494).index(2).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1209).y(383).index(3).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1459).y(321).index(4).slotSize(2).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(899).y(512).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(652).y(329).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(526).y(867).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(464).y(816).index(3).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1299).y(522).index(4).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1171).y(369).index(5).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.BELUGA_LINER_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(6).shipModule(PowerPlant.POWER_PLANT_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(7).shipModule(Thrusters.THRUSTERS_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(7).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(8).shipModule(LifeSupport.LIFE_SUPPORT_8_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(6).shipModule(PowerDistributor.POWER_DISTRIBUTOR_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(5).shipModule(Sensors.SENSORS_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(7).shipModule(FuelTank.FUEL_TANK_7_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(6).shipModule(ShieldGenerator.SHIELD_GENERATOR_6_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(6).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(6).shipModule(PassengerCabin.BUSINESS_CLASS_PASSENGER_CABIN_6_D).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(6).shipModule(PassengerCabin.BUSINESS_CLASS_PASSENGER_CABIN_6_D).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(5).shipModule(PassengerCabin.BUSINESS_CLASS_PASSENGER_CABIN_4_D).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(5).shipModule(PassengerCabin.BUSINESS_CLASS_PASSENGER_CABIN_4_D).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(10).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(11).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship TYPE_9_MILITARY = new Ship(
            ShipType.TYPE_9_MILITARY,
            121486140,
            124755340,
            ShipSpecs.TYPE_9_MILITARY,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1179).y(319).index(0).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(733).y(766).index(1).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1185).y(717).index(2).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(859).y(366).index(3).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1128).y(522).index(4).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1205).y(433).index(5).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(884).y(759).index(6).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1318).y(408).index(7).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1276).y(360).index(8).slotSize(1).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1098).y(362).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(789).y(667).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(687).y(578).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(862).y(742).index(3).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(773).y(241).index(4).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(648).y(346).index(5).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1073).y(333).index(6).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1282).y(573).index(7).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.TYPE_9_MILITARY_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(8).shipModule(PowerPlant.POWER_PLANT_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(7).shipModule(Thrusters.THRUSTERS_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(7).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(5).shipModule(LifeSupport.LIFE_SUPPORT_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(7).shipModule(PowerDistributor.POWER_DISTRIBUTOR_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(4).shipModule(Sensors.SENSORS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(6).shipModule(FuelTank.FUEL_TANK_6_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(8).shipModule(CargoRack.CARGO_RACK_7_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(7).shipModule(CargoRack.CARGO_RACK_6_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(6).shipModule(ShieldGenerator.SHIELD_GENERATOR_6_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(4).slotSize(5).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(5).slotSize(5).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(4).namedIndex(5).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(4).namedIndex(6).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(3).namedIndex(7).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(3).namedIndex(8).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(10).slotSize(2).namedIndex(11).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(11).slotSize(1).namedIndex(12).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship ANACONDA = new Ship(
            ShipType.ANACONDA,
            142447820,
            146969450,
            ShipSpecs.ANACONDA,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1150).y(466).index(0).slotSize(4).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1346).y(329).index(1).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1196).y(648).index(2).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1132).y(710).index(3).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1378).y(781).index(4).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1297).y(876).index(5).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(734).y(721).index(6).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(750).y(736).index(7).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(762).y(762).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(681).y(686).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(797).y(211).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(556).y(418).index(3).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1308).y(746).index(4).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1252).y(809).index(5).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1347).y(288).index(6).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1384).y(331).index(7).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.ANACONDA_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(8).shipModule(PowerPlant.POWER_PLANT_8_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(7).shipModule(Thrusters.THRUSTERS_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(6).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(5).shipModule(LifeSupport.LIFE_SUPPORT_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(8).shipModule(PowerDistributor.POWER_DISTRIBUTOR_8_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(8).shipModule(Sensors.SENSORS_8_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(5).shipModule(FuelTank.FUEL_TANK_5_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(7).shipModule(CargoRack.CARGO_RACK_6_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(6).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(6).shipModule(ShieldGenerator.SHIELD_GENERATOR_6_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(6).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(5).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(5).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(7).slotSize(5).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(4).namedIndex(8).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(4).namedIndex(9).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(10).slotSize(4).namedIndex(10).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(11).slotSize(2).namedIndex(13).shipModule(CargoRack.CARGO_RACK_1_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(12).slotSize(1).namedIndex(14).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship FEDERATION_CORVETTE = new Ship(
            ShipType.FEDERATION_CORVETTE,
            183147460,
            187969450,
            ShipSpecs.FEDERATION_CORVETTE,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(707).y(254).index(0).slotSize(4).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(644).y(316).index(1).slotSize(4).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1220).y(424).index(2).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(3).x(1031).y(689).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(957).y(717).index(4).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1302).y(752).index(5).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1259).y(797).index(6).slotSize(1).build()
            ),
            List.of(//checked
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(797).y(212).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(571).y(414).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1140).y(605).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1074).y(674).index(3).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1125).y(589).index(4).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1059).y(659).index(5).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1073).y(559).index(6).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1021).y(505).index(7).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.FEDERATION_CORVETTE_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(8).shipModule(PowerPlant.POWER_PLANT_8_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(7).shipModule(Thrusters.THRUSTERS_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(6).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(5).shipModule(LifeSupport.LIFE_SUPPORT_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(8).shipModule(PowerDistributor.POWER_DISTRIBUTOR_8_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(8).shipModule(Sensors.SENSORS_8_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(5).shipModule(FuelTank.FUEL_TANK_5_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(7).shipModule(ShieldGenerator.SHIELD_GENERATOR_7_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(7).shipModule(CargoRack.CARGO_RACK_6_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(7).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(6).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(6).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(5).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(5).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(7).slotSize(5).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(8).slotSize(5).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(4).namedIndex(8).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(10).slotSize(4).namedIndex(9).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(11).slotSize(3).namedIndex(10).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(12).slotSize(1).namedIndex(11).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship CUTTER = new Ship(
            ShipType.CUTTER,
            200484780,
            208969450,
            ShipSpecs.CUTTER,

            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1503).y(258).index(0).slotSize(4).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1123).y(603).index(1).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1091).y(638).index(2).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1399).y(375).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1347).y(304).index(4).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(967).y(119).index(5).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(470).y(570).index(6).slotSize(2).build()
            ),
            List.of(//checked
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(666).y(422).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(820).y(288).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(652).y(596).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(856).y(784).index(3).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(443).y(501).index(4).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(914).y(931).index(5).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(929).y(570).index(6).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1026).y(479).index(7).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.CUTTER_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(8).shipModule(PowerPlant.POWER_PLANT_8_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(8).shipModule(Thrusters.THRUSTERS_8_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(7).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(7).shipModule(LifeSupport.LIFE_SUPPORT_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(7).shipModule(PowerDistributor.POWER_DISTRIBUTOR_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(7).shipModule(Sensors.SENSORS_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(6).shipModule(FuelTank.FUEL_TANK_6_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(8).shipModule(ShieldGenerator.SHIELD_GENERATOR_8_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(8).shipModule(CargoRack.CARGO_RACK_7_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(6).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(6).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(6).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(5).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(5).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(7).slotSize(5).build(),
                    Slot.builder().slotType(SlotType.MILITARY).index(8).slotSize(5).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(4).namedIndex(8).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(10).slotSize(3).namedIndex(9).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(11).slotSize(1).namedIndex(10).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship PYTHON_NX = new Ship(
            ShipType.PYTHON_NX,
            66161981,
            67527359,
            ShipSpecs.PYTHON_NX,
            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(933).y(624).index(0).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1100).y(460).index(1).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1609).y(227).index(2).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1015).y(525).index(3).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1157).y(768).index(4).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1277).y(640).index(5).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1232).y(725).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1256).y(699).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(569).y(428).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1000).y(868).index(3).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(574).y(532).index(4).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(967).y(215).index(5).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.PYTHON_NX_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(6).shipModule(PowerPlant.POWER_PLANT_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(6).shipModule(Thrusters.THRUSTERS_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(5).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_OVERCHARGE_5_C).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(4).shipModule(LifeSupport.LIFE_SUPPORT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(6).shipModule(PowerDistributor.POWER_DISTRIBUTOR_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(5).shipModule(Sensors.SENSORS_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(4).shipModule(FuelTank.FUEL_TANK_4_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(6).shipModule(ShieldGenerator.SHIELD_GENERATOR_6_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(1).shipModule(CargoRack.CARGO_RACK_1_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));
    public static final Ship TYPE_8 = new Ship(
            ShipType.TYPE_8,
            36238840,
            38453970,
            ShipSpecs.TYPE_8,
            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(803).y(749).index(0).namedIndex(1).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1088).y(582).index(1).namedIndex(1).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1029).y(539).index(2).namedIndex(2).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(935).y(429).index(3).namedIndex(4).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1262).y(528).index(4).namedIndex(5).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1105).y(359).index(5).namedIndex(6).slotSize(1).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(945).y(336).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(832).y(437).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(802).y(647).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(924).y(750).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.TYPE_8_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(5).shipModule(PowerPlant.POWER_PLANT_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(5).shipModule(Thrusters.THRUSTERS_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(5).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_OVERCHARGE_5_C).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(3).shipModule(LifeSupport.LIFE_SUPPORT_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(4).shipModule(PowerDistributor.POWER_DISTRIBUTOR_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(3).shipModule(Sensors.SENSORS_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(5).shipModule(FuelTank.FUEL_TANK_5_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(7).shipModule(CargoRack.CARGO_RACK_7_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(6).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(6).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(6).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(5).shipModule(ShieldGenerator.SHIELD_GENERATOR_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));
    public static final Ship MANDALAY = new Ship(
            ShipType.MANDALAY,
            16527690,
            17639221,
            ShipSpecs.MANDALAY,
            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1051).y(444).index(0).namedIndex(1).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(914).y(570).index(1).namedIndex(2).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1161).y(702).index(2).namedIndex(3).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(855).y(517).index(3).namedIndex(4).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1331).y(702).index(4).namedIndex(1).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1230).y(818).index(5).namedIndex(2).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1331).y(469).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1091).y(355).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1164).y(348).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(785).y(625).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.MANDALAY_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(5).shipModule(PowerPlant.POWER_PLANT_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(5).shipModule(Thrusters.THRUSTERS_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(5).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_OVERCHARGE_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(4).shipModule(LifeSupport.LIFE_SUPPORT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(5).shipModule(PowerDistributor.POWER_DISTRIBUTOR_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(5).shipModule(Sensors.SENSORS_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(5).shipModule(FuelTank.FUEL_TANK_5_C).build()

            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(6).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(5).shipModule(ShieldGenerator.SHIELD_GENERATOR_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship COBRA_MK_V = new Ship(
            ShipType.COBRA_MK_V,
            1473191,
            1989461,
            ShipSpecs.COBRA_MK_V,
            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1490).y(504).index(0).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1313).y(283).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(875).y(378).index(2).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1329).y(502).index(3).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1070).y(784).index(4).slotSize(1).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(1193).y(792).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(691).y(395).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1096).y(225).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(661).y(635).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.COBRA_MK_V_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(4).shipModule(PowerPlant.POWER_PLANT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(4).shipModule(Thrusters.THRUSTERS_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(4).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_OVERCHARGE_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(3).shipModule(LifeSupport.LIFE_SUPPORT_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(4).shipModule(PowerDistributor.POWER_DISTRIBUTOR_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(3).shipModule(Sensors.SENSORS_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(4).shipModule(FuelTank.FUEL_TANK_4_C).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(5).shipModule(ShieldGenerator.SHIELD_GENERATOR_4_C).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(2).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship CORSAIR = new Ship(
            ShipType.CORSAIR,
            76884160,
            79304748,
            ShipSpecs.CORSAIR,
            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1055).y(504).index(0).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(4).x(786).y(429).index(1).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1144).y(431).index(2).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1005).y(553).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(3).x(879).y(549).index(4).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(800).y(785).index(5).slotSize(2).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1110).y(198).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(550).y(745).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(722).y(758).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(4).x(1209).y(758).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.CORSAIR_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(7).shipModule(PowerPlant.POWER_PLANT_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(7).shipModule(Thrusters.THRUSTERS_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(5).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_OVERCHARGE_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(4).shipModule(LifeSupport.LIFE_SUPPORT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(7).shipModule(PowerDistributor.POWER_DISTRIBUTOR_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(6).shipModule(Sensors.SENSORS_6_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(5).shipModule(FuelTank.FUEL_TANK_5_C).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(6).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(6).shipModule(ShieldGenerator.SHIELD_GENERATOR_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(6).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(5).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(5).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(2).shipModule(Computer.SUPERCRUISE_ASSIST).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build()
            ));

    public static final Ship PANTHER_CLIPPER_MK_II = new Ship(
            ShipType.PANTHER_CLIPPER_MK_II,
            0,
            0,
            ShipSpecs.PANTHER_CLIPPER_MK_II,
            List.of(
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(960).y(540).index(0).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(960).y(540).index(1).slotSize(3).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(960).y(540).index(2).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(960).y(540).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(960).y(540).index(4).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(960).y(540).index(5).slotSize(2).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(960).y(540).index(6).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(960).y(540).index(7).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(960).y(540).index(8).slotSize(1).build(),
                    ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(960).y(540).index(9).slotSize(1).build()
            ),
            List.of(
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(960).y(540).index(0).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(960).y(540).index(1).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(960).y(540).index(2).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(960).y(540).index(3).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(960).y(540).index(4).slotSize(0).build(),
                    ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(960).y(540).index(5).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.PANTHER_CLIPPER_MK_II_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(8).shipModule(PowerPlant.POWER_PLANT_8_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(8).shipModule(Thrusters.THRUSTERS_8_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(7).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_OVERCHARGE_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(5).shipModule(LifeSupport.LIFE_SUPPORT_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(7).shipModule(PowerDistributor.POWER_DISTRIBUTOR_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(5).shipModule(Sensors.SENSORS_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(7).shipModule(FuelTank.FUEL_TANK_7_C).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CARGO).index(0).slotSize(8).shipModule(CargoRack.CARGO_RACK_7_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).namedIndex(1).slotSize(8).shipModule(ShieldGenerator.SHIELD_GENERATOR_8_E).build(),
                    Slot.builder().slotType(SlotType.CARGO).index(2).slotSize(7).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(7).namedIndex(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(6).namedIndex(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(6).namedIndex(4).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(6).namedIndex(5).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(5).namedIndex(6).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(5).namedIndex(7).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(4).namedIndex(8).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(10).slotSize(2).namedIndex(9).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(11).slotSize(1).namedIndex(10).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));
    public static final List<Ship> ALL = List.of(
            SIDE_WINDER,
            EAGLE,
            HAULER,
            ADDER,
            VIPER_MK_III,
            COBRA_MK_III,
            TYPE_6,
            DOLPHIN,
            TYPE_7,
            ASP,
            VULTURE,
            EMPIRE_TRADER,
            FEDERATION_DROPSHIP,
            ORCA,
            TYPE_8,
            TYPE_9,
            PYTHON,
            BELUGA_LINER,
            FER_DE_LANCE,
            ANACONDA,
            FEDERATION_CORVETTE,
            CUTTER,
            DIAMOND_BACK,
            EMPIRE_COURIER,
            DIAMOND_BACK_XL,
            EMPIRE_EAGLE,
            FEDERATION_DROPSHIP_MK_II,
            FEDERATION_GUNSHIP,
            VIPER_MK_IV,
            COBRA_MK_IV,
            INDEPENDANT_TRADER,
            ASP_SCOUT,
            TYPE_9_MILITARY,
            KRAIT_MK_II,
            TYPE_X,
            TYPE_X_2,
            TYPE_X_3,
            KRAIT_LIGHT,
            MAMBA,
            PYTHON_NX,
            MANDALAY,
            COBRA_MK_V,
            CORSAIR,
            PANTHER_CLIPPER_MK_II
    );


    @Getter
    private final ShipType shipType;
    @Getter
    private final List<ImageSlot> hardpointSlots;
    @Getter
    private final List<ImageSlot> utilitySlots;
    @Getter
    private final List<Slot> coreSlots;
    @Getter
    private final List<Slot> optionalSlots;
    @Getter
    private final Slot cargoHatch;

    @Getter
    private final Map<HorizonsModifier, Object> attributes = new HashMap<>();
    private double currentFuelReserve = 0D;
    private double currentFuel = 0D;
    private double currentCargo = 0D;
    @Getter
    @Setter
    private long price;
    @Getter
    @Setter
    private long retailPrice;

    private Ship(final ShipType shipType, final long price, final long retailPrice, final Map<HorizonsModifier, Object> attributes, final List<ImageSlot> hardpointSlots, final List<ImageSlot> utilitySlots, final List<Slot> coreSlots, final List<Slot> optionalSlots) {
        this.price = price;
        this.retailPrice = retailPrice;
        this.shipType = shipType;
        this.attributes.putAll(attributes);
        this.hardpointSlots = new ArrayList<>(hardpointSlots);
        this.utilitySlots = new ArrayList<>(utilitySlots);
        this.coreSlots = new ArrayList<>(coreSlots);
        this.optionalSlots = new ArrayList<>(optionalSlots);
        this.cargoHatch = new Slot(Slot.builder().slotType(SlotType.CARGO_HATCH).index(0).slotSize(0).shipModule(CargoHatch.CARGO_HATCH).build());
        this.currentFuel = getMaxFuel();
        this.currentFuelReserve = getMaxFuelReserve();
    }

    public Ship(final Ship ship) {
        this.price = ship.price;
        this.retailPrice = ship.retailPrice;
        this.shipType = ship.shipType;
        this.hardpointSlots = new ArrayList<>(ship.hardpointSlots.stream().map(ImageSlot::new).toList());
        this.utilitySlots = new ArrayList<>(ship.utilitySlots.stream().map(ImageSlot::new).toList());
        this.coreSlots = new ArrayList<>(ship.coreSlots.stream().map(Slot::new).toList());
        this.optionalSlots = new ArrayList<>(ship.optionalSlots.stream().map(Slot::new).toList());
        this.cargoHatch = new Slot(ship.cargoHatch);
        this.attributes.putAll(ship.getAttributes());
        this.currentCargo = ship.currentCargo;
        this.currentFuel = ship.currentFuel;
        this.currentFuelReserve = ship.currentFuelReserve;
    }

    public double getMaxFuel() {
        final Double coreFuel = this.getCoreSlots().stream()
                .filter(slot -> slot.getSlotType() == SlotType.CORE_FUEL_TANK)
                .findFirst()
                .map(Slot::getShipModule)
                .map(shipModule -> (double) shipModule.getAttributeValue(HorizonsModifier.FUEL_CAPACITY))
                .orElse(0D);
        final Double optionalFuel = this.getOptionalSlots().stream()
                .filter(slot -> slot.getShipModule() instanceof FuelTank)
                .map(Slot::getShipModule)
                .map(shipModule -> (double) shipModule.getAttributeValue(HorizonsModifier.FUEL_CAPACITY))
                .mapToDouble(Double::doubleValue)
                .sum();
        return coreFuel + optionalFuel;
    }

    public double getMaxCargo() {
        return this.getOptionalSlots().stream()
                .filter(slot -> slot.getShipModule() instanceof CargoRack || slot.getShipModule() instanceof AntiCorrosionCargoRack || slot.getShipModule() instanceof LargeCargoRack)
                .map(Slot::getShipModule)
                .map(shipModule -> (double) shipModule.getAttributeValue(HorizonsModifier.CARGO_CAPACITY))
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public double getMaxPotentialCargo() {
        return this.getOptionalSlots().stream()
                .filter(slot -> slot.getSlotType().equals(SlotType.OPTIONAL) || slot.getSlotType().equals(SlotType.CARGO))
                .mapToDouble(slot -> Math.pow(2, slot.getSlotSize()) * (slot.getSlotType().equals(SlotType.CARGO) ? 1.5 : 1.0))
                .sum();
    }

    public int getMaxPotentialPassenger(PassengerCabinType cabinType) {
        if (cabinType.equals(PassengerCabinType.LUXURY) && !(this == BELUGA_LINER || this == DOLPHIN || this == ORCA)) {
            return 0;
        }
        return this.getOptionalSlots().stream()
                .filter(slot -> slot.getSlotSize() >= cabinType.getMinSize())
                .mapToInt(slot -> cabinType.getPassengerCount(slot.getSlotSize()))
                .sum();
    }

    public double getMaxPassenger() {
        return this.getOptionalSlots().stream()
                .filter(slot -> slot.getShipModule() instanceof PassengerCabin)
                .map(Slot::getShipModule)
                .map(shipModule -> (double) shipModule.getAttributeValue(HorizonsModifier.CABIN_CAPACITY))
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public double getMaxFuelReserve() {
        return (double) this.attributes.getOrDefault(HorizonsModifier.FUEL_RESERVE, 0.0);
    }

    /**
     * Mass of the ship and modules without fuel, cargo and fuel reserve
     *
     * @return
     */
    public double getEmptyMass() {
        return (double) this.attributes.getOrDefault(HorizonsModifier.MASS, 0.0)
                + Stream.concat(this.getCoreSlots().stream(), Stream.concat(this.getHardpointSlots().stream(), Stream.concat(this.getUtilitySlots().stream(), this.getOptionalSlots().stream())))
                .map(slot -> (slot.isOccupied()) ? (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MASS, 0.0) : 0.0)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public double getCurrentFuelReserve() {
        final boolean liveStats = ApplicationState.getInstance().isLiveStats();
        if (liveStats) {
            return ApplicationState.getInstance().getShipConfig()
                    .map(shipConfig -> shipConfig.getFuelReserve().doubleValue())
                    .orElseGet(this::getMaxFuelReserve);
        } else {
            return currentFuelReserve;
        }

    }

    public double getCurrentFuel() {
        final boolean liveStats = ApplicationState.getInstance().isLiveStats();
        if (liveStats) {
            return ApplicationState.getInstance().getShipConfig()
                    .map(shipConfig -> shipConfig.getFuelCapacity().doubleValue())
                    .orElse(getMaxFuel());
        } else {
            return currentFuel;
        }
    }

    public double getCurrentCargo() {
        final boolean liveStats = ApplicationState.getInstance().isLiveStats();
        if (liveStats) {
            return ApplicationState.getInstance().getShipConfig()
                    .map(shipConfig -> shipConfig.getCargoCapacity().doubleValue())
                    .orElse(0D);
        } else {
            return currentCargo;
        }
    }

    public void setCurrentFuelReserve(double currentFuelReserve) {
        this.currentFuelReserve = Math.min(currentFuelReserve, getMaxFuelReserve());
    }

    public void setCurrentFuel(double currentFuel) {
        this.currentFuel = Math.min(currentFuel, getMaxFuel());
    }

    public void setCurrentCargo(double currentCargo) {
        this.currentCargo = Math.min(currentCargo, getMaxCargo());
    }

    public Map<Integer, Double> getRetractedPower() {
        Map<Integer, Double> powerValues = new HashMap<>(Map.of(
                -1, 0D,
                0, 0D,
                1, 0D,
                2, 0D,
                3, 0D,
                4, 0D,
                5, 0D
        ));


        powerValues.put(0, (Double) getCoreSlots().stream().filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType())).findFirst().filter(Slot::isOccupied).map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_CAPACITY)).orElse(0.0D));
        if (getCargoHatch().isOccupied() && getCargoHatch().getShipModule().isPowered()) {
            powerValues.compute(getCargoHatch().getShipModule().isPassivePowerWithoutToggle() ? -1 : getCargoHatch().getShipModule().getPowerGroup(), (key, value) -> value + (double) getCargoHatch().getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW));
        }
        getUtilitySlots().stream()
                .filter(Slot::isOccupied)
                .filter(slot -> slot.getShipModule().isPassivePower())
                .filter(slot -> slot.getShipModule().isPowered())
                .forEach(slot -> powerValues.compute(slot.getShipModule().isPassivePowerWithoutToggle() ? -1 : slot.getShipModule().getPowerGroup(), (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)));
        getOptionalSlots().stream()
                .filter(Slot::isOccupied)
                .filter(slot -> slot.getShipModule().isPowered())
                .forEach(slot -> powerValues.compute(slot.getShipModule().isPassivePowerWithoutToggle() ? -1 : slot.getShipModule().getPowerGroup(), (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)));
        getCoreSlots().stream()
                .filter(Slot::isOccupied)
                .filter(slot -> slot.getShipModule().isPowered())
                .forEach(slot -> powerValues.compute(slot.getShipModule().isPassivePowerWithoutToggle() ? -1 : slot.getShipModule().getPowerGroup(), (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)));

        return powerValues;
    }

    public Map<Integer, Double> getDeployedPower() {
        Map<Integer, Double> powerValues = getRetractedPower();

        getHardpointSlots().stream()
                .filter(Slot::isOccupied)
                .filter(slot -> slot.getShipModule().isPowered())
                .forEach(slot -> powerValues.compute(
                        slot.getShipModule().isPassivePowerWithoutToggle() ? -1 : slot.getShipModule().getPowerGroup(),
                        (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)
                ));
        getUtilitySlots().stream()
                .filter(Slot::isOccupied)
                .filter(slot -> !slot.getShipModule().isPassivePower())
                .filter(slot -> slot.getShipModule().isPowered())
                .forEach(slot -> powerValues.compute(
                        slot.getShipModule().isPassivePowerWithoutToggle() ? -1 : slot.getShipModule().getPowerGroup(),
                        (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)
                ));


        return powerValues;
    }

    public double getMaximumMass() {
        return getEmptyMass() + getMaxCargo() + getMaxFuel();
    }

    public Long getRebuy() {
        return (long) (getTotalPrice() * 0.05);
    }

    public Long getTotalPrice() {
        return (long) getModulesValue() + getHullValue();
    }

    public Long getModulesValue() {
        final long sumUtility = getUtilitySlots().stream()
                .filter(Slot::isOccupied)
                .map(slot -> slot.getShipModule().getBuyPrice() != null ? slot.getShipModule().getBuyPrice() : slot.getShipModule().getBasePrice())
                .mapToLong(Long::longValue)
                .sum();
        final long sumOptional = getOptionalSlots().stream()
                .filter(Slot::isOccupied)
                .map(slot -> slot.getShipModule().getBuyPrice() != null ? slot.getShipModule().getBuyPrice() : slot.getShipModule().getBasePrice())
                .mapToLong(Long::longValue)
                .sum();
        final long sumCore = getCoreSlots().stream()
                .filter(Slot::isOccupied)
                .map(slot -> slot.getShipModule().getBuyPrice() != null ? slot.getShipModule().getBuyPrice() : slot.getShipModule().getBasePrice())
                .mapToLong(Long::longValue)
                .sum();
        final long sumHardpoint = getHardpointSlots().stream()
                .filter(Slot::isOccupied)
                .map(slot -> slot.getShipModule().getBuyPrice() != null ? slot.getShipModule().getBuyPrice() : slot.getShipModule().getBasePrice())
                .mapToLong(Long::longValue)
                .sum();
        return sumUtility + sumOptional + sumCore + sumHardpoint;
    }

    public Long getHullValue() {
        return this.price;
    }

    public boolean supportsGraphs() {
        return this.shipType == ShipType.PYTHON_NX
                || this.shipType == ShipType.MANDALAY
                || this.shipType == ShipType.COBRA_MK_V
                || this.shipType == ShipType.CORSAIR
//                || this.shipType == ShipType.PANTHER_CLIPPER_MK_II
                || this.shipType == ShipType.TYPE_8;

    }
}
