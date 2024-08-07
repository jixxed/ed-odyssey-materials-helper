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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public class Ship {
        static{
                final List<List<? extends ShipModule>> modules = ShipModule.ALL_MODULES;
        }
    public static final Ship SIDE_WINDER = new Ship(
            ShipType.SIDE_WINDER,
            5070,
            32000,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 220.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 320.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 5.0),
                    Map.entry(HorizonsModifier.SHIELDS, 40.0),
                    Map.entry(HorizonsModifier.ARMOUR, 60.0),
                    Map.entry(HorizonsModifier.MASS, 25.0),
//                    Map.entry(HorizonsModifier.FWDACC,44.39),
//                    Map.entry(HorizonsModifier.REVACC,29.96),
//                    Map.entry(HorizonsModifier.LATACC,29.96),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 45.454),
                    Map.entry(HorizonsModifier.BOOST_COST, 7.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 42.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 16.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 110.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,220.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,110.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,240.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 34.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 140.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.18),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 18.15),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.3),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 20.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 6.0),
                    Map.entry(HorizonsModifier.CREW, 1.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 240.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 350.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 7.0),
                    Map.entry(HorizonsModifier.SHIELDS, 60.0),
                    Map.entry(HorizonsModifier.ARMOUR, 40.0),
                    Map.entry(HorizonsModifier.MASS, 50.0),
//                    Map.entry(HorizonsModifier.FWDACC,43.97),
//                    Map.entry(HorizonsModifier.REVACC,29.97),
//                    Map.entry(HorizonsModifier.LATACC,29.86),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 75.0),
                    Map.entry(HorizonsModifier.BOOST_COST, 8.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 50.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 18.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 120.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,220.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,110.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,240.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 40.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 165.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.38),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 21.48),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.34),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 28.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 6.0),
                    Map.entry(HorizonsModifier.CREW, 1.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 200.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 300.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
                    Map.entry(HorizonsModifier.SHIELDS, 50.0),
                    Map.entry(HorizonsModifier.ARMOUR, 100.0),
                    Map.entry(HorizonsModifier.MASS, 14.0),
//                    Map.entry(HorizonsModifier.FWDACC,39.87),
//                    Map.entry(HorizonsModifier.REVACC,29.95),
//                    Map.entry(HorizonsModifier.LATACC,29.95),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 35.0),
                    Map.entry(HorizonsModifier.BOOST_COST, 7.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 36.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 14.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 100.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,220.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,110.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,240.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 123.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.06),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 16.2),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.25),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 20.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 6.0),
                    Map.entry(HorizonsModifier.CREW, 1.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 220.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 320.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
                    Map.entry(HorizonsModifier.SHIELDS, 60.0),
                    Map.entry(HorizonsModifier.ARMOUR, 90.0),
                    Map.entry(HorizonsModifier.MASS, 35.0),
//                    Map.entry(HorizonsModifier.FWDACC,39.41),
//                    Map.entry(HorizonsModifier.REVACC,27.73),
//                    Map.entry(HorizonsModifier.LATACC,27.86),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 45.454),
                    Map.entry(HorizonsModifier.BOOST_COST, 8.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 38.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 14.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 100.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 170.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.45),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 22.6),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.36),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 35.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 7.0),
                    Map.entry(HorizonsModifier.CREW, 2.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 300.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 400.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 5.0),
                    Map.entry(HorizonsModifier.SHIELDS, 80.0),
                    Map.entry(HorizonsModifier.ARMOUR, 60.0),
                    Map.entry(HorizonsModifier.MASS, 50.0),
//                    Map.entry(HorizonsModifier.FWDACC,34.54),
//                    Map.entry(HorizonsModifier.REVACC,27.84),
//                    Map.entry(HorizonsModifier.LATACC,27.84),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 70.0),
                    Map.entry(HorizonsModifier.BOOST_COST, 8.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 40.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 15.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 100.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,220.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,110.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,240.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 163.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.5),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 21.2),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.37),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 28.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 6.0),
                    Map.entry(HorizonsModifier.CREW, 1.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 320.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 400.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
                    Map.entry(HorizonsModifier.SHIELDS, 105.0),
                    Map.entry(HorizonsModifier.ARMOUR, 70.0),
                    Map.entry(HorizonsModifier.MASS, 50.0),
//                    Map.entry(HorizonsModifier.FWDACC,53.98),
//                    Map.entry(HorizonsModifier.REVACC,29.7),
//                    Map.entry(HorizonsModifier.LATACC,24.95),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 62.5),
                    Map.entry(HorizonsModifier.BOOST_COST, 10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 35.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 15.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 195.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.69),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 26.2),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.41),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 35.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 7.0),
                    Map.entry(HorizonsModifier.CREW, 1.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 280.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 400.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 5.0),
                    Map.entry(HorizonsModifier.SHIELDS, 80.0),
                    Map.entry(HorizonsModifier.ARMOUR, 120.0),
                    Map.entry(HorizonsModifier.MASS, 180.0),
//                    Map.entry(HorizonsModifier.FWDACC,35.03),
//                    Map.entry(HorizonsModifier.REVACC,25.16),
//                    Map.entry(HorizonsModifier.LATACC,20.02),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 50.0),
                    Map.entry(HorizonsModifier.BOOST_COST, 10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 40.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 10.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 100.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 225.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.92),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 30.63),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.49),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 35.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 8.0),
                    Map.entry(HorizonsModifier.CREW, 2.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 270.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 340.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 3.0),
                    Map.entry(HorizonsModifier.SHIELDS, 150.0),
                    Map.entry(HorizonsModifier.ARMOUR, 150.0),
                    Map.entry(HorizonsModifier.MASS, 190.0),
//                    Map.entry(HorizonsModifier.FWDACC,53.84),
//                    Map.entry(HorizonsModifier.REVACC,30.14),
//                    Map.entry(HorizonsModifier.LATACC,24.97),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 64.815),
                    Map.entry(HorizonsModifier.BOOST_COST, 10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 30.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 12.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 25.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 209.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.82),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 28.98),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.46),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 35.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 7.0),
                    Map.entry(HorizonsModifier.CREW, 1.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 280.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 380.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 5.0),
                    Map.entry(HorizonsModifier.SHIELDS, 120.0),
                    Map.entry(HorizonsModifier.ARMOUR, 120.0),
                    Map.entry(HorizonsModifier.MASS, 170.0),
//                    Map.entry(HorizonsModifier.FWDACC,39.57),
//                    Map.entry(HorizonsModifier.REVACC,29.82),
//                    Map.entry(HorizonsModifier.LATACC,25.19),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 60.714),
                    Map.entry(HorizonsModifier.BOOST_COST, 10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 42.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 15.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 100.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 35.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 346.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.42),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 48.05),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.49),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 40.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 8.0),
                    Map.entry(HorizonsModifier.CREW, 1.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 200.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 300.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 3.0),
                    Map.entry(HorizonsModifier.SHIELDS, 120.0),
                    Map.entry(HorizonsModifier.ARMOUR, 120.0),
                    Map.entry(HorizonsModifier.MASS, 210.0),
//                    Map.entry(HorizonsModifier.FWDACC,27.84),
//                    Map.entry(HorizonsModifier.REVACC,19.91),
//                    Map.entry(HorizonsModifier.LATACC,15.03),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 50.0),
                    Map.entry(HorizonsModifier.BOOST_COST, 10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 30.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 10.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 25.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 228.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.99),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 31.68),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.51),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 35.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 8.0),
                    Map.entry(HorizonsModifier.CREW, 2.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 220.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 350.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 3.0),
                    Map.entry(HorizonsModifier.SHIELDS, 90.0),
                    Map.entry(HorizonsModifier.ARMOUR, 180.0),
                    Map.entry(HorizonsModifier.MASS, 155.0),
//                    Map.entry(HorizonsModifier.FWDACC,20.1),
//                    Map.entry(HorizonsModifier.REVACC,14.96),
//                    Map.entry(HorizonsModifier.LATACC,15.07),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 40.909),
                    Map.entry(HorizonsModifier.BOOST_COST, 10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 30.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 17.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 100.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,220.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,110.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,240.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 23.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 179.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.7),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 24.55),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.39),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 35.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 8.0),
                    Map.entry(HorizonsModifier.CREW, 1.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 250.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 350.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 3.0),
                    Map.entry(HorizonsModifier.SHIELDS, 110.0),
                    Map.entry(HorizonsModifier.ARMOUR, 110.0),
                    Map.entry(HorizonsModifier.MASS, 140.0),
//                    Map.entry(HorizonsModifier.FWDACC,39.63),
//                    Map.entry(HorizonsModifier.REVACC,30.01),
//                    Map.entry(HorizonsModifier.LATACC,14.97),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 48.0),
                    Map.entry(HorizonsModifier.BOOST_COST, 10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 30.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 20.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 100.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,220.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,110.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,240.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 23.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 245.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.91),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 56.0),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.5),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 35.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 9.0),
                    Map.entry(HorizonsModifier.CREW, 1.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 260.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 340.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
                    Map.entry(HorizonsModifier.SHIELDS, 150.0),
                    Map.entry(HorizonsModifier.ARMOUR, 150.0),
                    Map.entry(HorizonsModifier.MASS, 260.0),
//                    Map.entry(HorizonsModifier.FWDACC,34.63),
//                    Map.entry(HorizonsModifier.REVACC,25.06),
//                    Map.entry(HorizonsModifier.LATACC,19.89),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 61.538),
                    Map.entry(HorizonsModifier.BOOST_COST, 13.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 35.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 13.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 28.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 351.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.46),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 50.55),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.52),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 42.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 10.0),
                    Map.entry(HorizonsModifier.CREW, 1.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 280.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 380.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
                    Map.entry(HorizonsModifier.SHIELDS, 200.0),
                    Map.entry(HorizonsModifier.ARMOUR, 80.0),
                    Map.entry(HorizonsModifier.MASS, 35.0),
//                    Map.entry(HorizonsModifier.FWDACC,57.53),
//                    Map.entry(HorizonsModifier.REVACC,30.02),
//                    Map.entry(HorizonsModifier.LATACC,24.88),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 78.571),
                    Map.entry(HorizonsModifier.BOOST_COST, 10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 38.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 16.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 32.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 230.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.62),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 25.05),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.41),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 30.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 7.0),
                    Map.entry(HorizonsModifier.CREW, 1.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 200.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 300.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 2.0),
                    Map.entry(HorizonsModifier.SHIELDS, 135.0),
                    Map.entry(HorizonsModifier.ARMOUR, 270.0),
                    Map.entry(HorizonsModifier.MASS, 180.0),
//                    Map.entry(HorizonsModifier.FWDACC,20.22),
//                    Map.entry(HorizonsModifier.REVACC,15.07),
//                    Map.entry(HorizonsModifier.LATACC,15.03),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 45.0),
                    Map.entry(HorizonsModifier.BOOST_COST, 10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 27.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 15.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 100.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,220.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,110.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,240.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 20.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 215.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.87),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 29.78),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.39),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 45.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 8.0),
                    Map.entry(HorizonsModifier.CREW, 2.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 220.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 300.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 5.0),
                    Map.entry(HorizonsModifier.SHIELDS, 120.0),
                    Map.entry(HorizonsModifier.ARMOUR, 180.0),
                    Map.entry(HorizonsModifier.MASS, 150.0),
//                    Map.entry(HorizonsModifier.FWDACC,35.02),
//                    Map.entry(HorizonsModifier.REVACC,20.1),
//                    Map.entry(HorizonsModifier.LATACC,20.03),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 50.0),
                    Map.entry(HorizonsModifier.BOOST_COST, 13.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 40.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 15.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 110.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 35.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 210.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.8),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 29.65),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.47),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 52.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 8.0),
                    Map.entry(HorizonsModifier.CREW, 2.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 210.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 340.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 5.0),
                    Map.entry(HorizonsModifier.SHIELDS, 240.0),
                    Map.entry(HorizonsModifier.ARMOUR, 160.0),
                    Map.entry(HorizonsModifier.MASS, 230.0),
//                    Map.entry(HorizonsModifier.FWDACC,39.55),
//                    Map.entry(HorizonsModifier.REVACC,29.88),
//                    Map.entry(HorizonsModifier.LATACC,19.98),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 90.476),
                    Map.entry(HorizonsModifier.BOOST_COST, 16.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 42.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 17.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 110.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,180.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,90.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,200.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 35.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 237.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.87),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 35.63),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.57),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 55.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 10.0),
                    Map.entry(HorizonsModifier.CREW, 2.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 250.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 340.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
                    Map.entry(HorizonsModifier.SHIELDS, 140.0),
                    Map.entry(HorizonsModifier.ARMOUR, 210.0),
                    Map.entry(HorizonsModifier.MASS, 280.0),
//                    Map.entry(HorizonsModifier.FWDACC,23.64),
//                    Map.entry(HorizonsModifier.REVACC,15.04),
//                    Map.entry(HorizonsModifier.LATACC,14.97),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 48.0),
                    Map.entry(HorizonsModifier.BOOST_COST, 13.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 38.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 10.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 100.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 272.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.34),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 39.9),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.63),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 52.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 11.0),
                    Map.entry(HorizonsModifier.CREW, 2.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 180.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 300.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 3.0),
                    Map.entry(HorizonsModifier.SHIELDS, 200.0),
                    Map.entry(HorizonsModifier.ARMOUR, 300.0),
                    Map.entry(HorizonsModifier.MASS, 580.0),
//                    Map.entry(HorizonsModifier.FWDACC,29.99),
//                    Map.entry(HorizonsModifier.REVACC,20.34),
//                    Map.entry(HorizonsModifier.LATACC,10.19),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 55.556),
                    Map.entry(HorizonsModifier.BOOST_COST, 19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 30.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 14.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 80.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,80.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 20.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 331.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.6),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 46.5),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.83),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 60.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 14.0),
                    Map.entry(HorizonsModifier.CREW, 2.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 180.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 300.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 1.0),
                    Map.entry(HorizonsModifier.SHIELDS, 156.0),
                    Map.entry(HorizonsModifier.ARMOUR, 340.0),
                    Map.entry(HorizonsModifier.MASS, 350.0),
//                    Map.entry(HorizonsModifier.FWDACC,20.11),
//                    Map.entry(HorizonsModifier.REVACC,15.02),
//                    Map.entry(HorizonsModifier.LATACC,15.13),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 33.333),
                    Map.entry(HorizonsModifier.BOOST_COST, 10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 22.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 22.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 60.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,200.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 16.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 226.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.17),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 32.45),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.52),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 54.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 10.0),
                    Map.entry(HorizonsModifier.CREW, 1.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 230.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 330.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
                    Map.entry(HorizonsModifier.SHIELDS, 200.0),
                    Map.entry(HorizonsModifier.ARMOUR, 280.0),
                    Map.entry(HorizonsModifier.MASS, 400.0),
//                    Map.entry(HorizonsModifier.FWDACC,37.84),
//                    Map.entry(HorizonsModifier.REVACC,25.84),
//                    Map.entry(HorizonsModifier.LATACC,20.01),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 65.217),
                    Map.entry(HorizonsModifier.BOOST_COST, 19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 38.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 16.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,170.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,60.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,150.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 32.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 289.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.6),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 46.5),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.77),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 65.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 13.0),
                    Map.entry(HorizonsModifier.CREW, 2.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 210.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 350.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
                    Map.entry(HorizonsModifier.SHIELDS, 200.0),
                    Map.entry(HorizonsModifier.ARMOUR, 300.0),
                    Map.entry(HorizonsModifier.MASS, 480.0),
//                    Map.entry(HorizonsModifier.FWDACC,39.81),
//                    Map.entry(HorizonsModifier.REVACC,20.04),
//                    Map.entry(HorizonsModifier.LATACC,15.07),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 71.429),
                    Map.entry(HorizonsModifier.BOOST_COST, 19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 38.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 19.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,170.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,80.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,200.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 286.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.53),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 45.23),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.72),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 60.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 14.0),
                    Map.entry(HorizonsModifier.CREW, 2.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 300.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 380.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 5.0),
                    Map.entry(HorizonsModifier.SHIELDS, 180.0),
                    Map.entry(HorizonsModifier.ARMOUR, 270.0),
                    Map.entry(HorizonsModifier.MASS, 400.0),
//                    Map.entry(HorizonsModifier.FWDACC,24.74),
//                    Map.entry(HorizonsModifier.REVACC,20.05),
//                    Map.entry(HorizonsModifier.LATACC,10.1),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 60.0),
                    Map.entry(HorizonsModifier.BOOST_COST, 19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 40.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 18.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 80.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 304.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.63),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 46.8),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.74),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 60.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 12.0),
                    Map.entry(HorizonsModifier.CREW, 2.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 180.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 300.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 3.0),
                    Map.entry(HorizonsModifier.SHIELDS, 200.0),
                    Map.entry(HorizonsModifier.ARMOUR, 300.0),
                    Map.entry(HorizonsModifier.MASS, 500.0),
//                    Map.entry(HorizonsModifier.FWDACC,29.78),
//                    Map.entry(HorizonsModifier.REVACC,24.78),
//                    Map.entry(HorizonsModifier.LATACC,18.96),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 61.11),
                    Map.entry(HorizonsModifier.BOOST_COST, 19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 32.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 16.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 80.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,150.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,150.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 316.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.53),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 45.23),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.77),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 65.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 13.0),
                    Map.entry(HorizonsModifier.CREW, 3.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 200.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 310.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
                    Map.entry(HorizonsModifier.SHIELDS, 220.0),
                    Map.entry(HorizonsModifier.ARMOUR, 300.0),
                    Map.entry(HorizonsModifier.MASS, 450.0),
//                    Map.entry(HorizonsModifier.FWDACC,31.65),
//                    Map.entry(HorizonsModifier.REVACC,25.94),
//                    Map.entry(HorizonsModifier.LATACC,20.09),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 65.0),
                    Map.entry(HorizonsModifier.BOOST_COST, 19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 35.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 16.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,120.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,120.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 32.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 316.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.87),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 51.4),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.77),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 65.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 13.0),
                    Map.entry(HorizonsModifier.CREW, 2.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 170.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 280.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 1.0),
                    Map.entry(HorizonsModifier.SHIELDS, 250.0),
                    Map.entry(HorizonsModifier.ARMOUR, 350.0),
                    Map.entry(HorizonsModifier.MASS, 580.0),
//                    Map.entry(HorizonsModifier.FWDACC,24.61),
//                    Map.entry(HorizonsModifier.REVACC,17.83),
//                    Map.entry(HorizonsModifier.LATACC,10.08),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 58.824),
                    Map.entry(HorizonsModifier.BOOST_COST, 23.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 25.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 18.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 80.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,80.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 20.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 325.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.87),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 51.4),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.82),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 60.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 14.0),
                    Map.entry(HorizonsModifier.CREW, 2.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 250.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 350.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 3.0),
                    Map.entry(HorizonsModifier.SHIELDS, 200.0),
                    Map.entry(HorizonsModifier.ARMOUR, 180.0),
                    Map.entry(HorizonsModifier.MASS, 270.0),
//                    Map.entry(HorizonsModifier.FWDACC,NaN),
//                    Map.entry(HorizonsModifier.REVACC,NaN),
//                    Map.entry(HorizonsModifier.LATACC,NaN),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 64.0),
                    Map.entry(HorizonsModifier.BOOST_COST, 13.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 31.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 10.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,200.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 26.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 300.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.68),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 52.05),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.63),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 60.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 14.0),
                    Map.entry(HorizonsModifier.CREW, 2.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 240.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 330.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 3.0),
                    Map.entry(HorizonsModifier.SHIELDS, 220.0),
                    Map.entry(HorizonsModifier.ARMOUR, 220.0),
                    Map.entry(HorizonsModifier.MASS, 320.0),
//                    Map.entry(HorizonsModifier.FWDACC,28.01),
//                    Map.entry(HorizonsModifier.REVACC,18.04),
//                    Map.entry(HorizonsModifier.LATACC,15.12),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 62.5),
                    Map.entry(HorizonsModifier.BOOST_COST, 13.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 31.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 10.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,200.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 26.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 300.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.68),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 52.05),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.63),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 55.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 16.0),
                    Map.entry(HorizonsModifier.CREW, 3.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 300.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 380.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 1.0),
                    Map.entry(HorizonsModifier.SHIELDS, 220.0),
                    Map.entry(HorizonsModifier.ARMOUR, 220.0),
                    Map.entry(HorizonsModifier.MASS, 290.0),
//                    Map.entry(HorizonsModifier.FWDACC,29.66),
//                    Map.entry(HorizonsModifier.REVACC,25.08),
//                    Map.entry(HorizonsModifier.LATACC,19.95),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 66.667),
                    Map.entry(HorizonsModifier.BOOST_COST, 16.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 25.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 18.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 55.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,220.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,110.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,240.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 20.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 262.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.3),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 42.68),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.79),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 55.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 15.0),
                    Map.entry(HorizonsModifier.CREW, 2.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 260.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 350.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
                    Map.entry(HorizonsModifier.SHIELDS, 300.0),
                    Map.entry(HorizonsModifier.ARMOUR, 225.0),
                    Map.entry(HorizonsModifier.MASS, 250.0),
//                    Map.entry(HorizonsModifier.FWDACC,29.31),
//                    Map.entry(HorizonsModifier.REVACC,24.34),
//                    Map.entry(HorizonsModifier.LATACC,20.04),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 84.615),
                    Map.entry(HorizonsModifier.BOOST_COST, 19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 38.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 12.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 224.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.05),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 41.63),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.67),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 70.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 12.0),
                    Map.entry(HorizonsModifier.CREW, 2.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 310.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 380.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 3.0),
                    Map.entry(HorizonsModifier.SHIELDS, 270.0),
                    Map.entry(HorizonsModifier.ARMOUR, 230.0),
                    Map.entry(HorizonsModifier.MASS, 250.0),
//                    Map.entry(HorizonsModifier.FWDACC,NaN),
//                    Map.entry(HorizonsModifier.REVACC,NaN),
//                    Map.entry(HorizonsModifier.LATACC,NaN),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 77.42),
                    Map.entry(HorizonsModifier.BOOST_COST, 16.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 30.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 10.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 75.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,180.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,90.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,200.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 27.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 165.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.05),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 41.63),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.5),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 70.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 12.0),
                    Map.entry(HorizonsModifier.CREW, 2.0)

            ),
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
                Map.ofEntries(
                        Map.entry(HorizonsModifier.TOP_SPEED, 230.0),
                        Map.entry(HorizonsModifier.BOOST_SPEED, 300.0),
                        Map.entry(HorizonsModifier.MANOEUVRABILITY, 2.0),
                        Map.entry(HorizonsModifier.SHIELDS, 260.0),
                        Map.entry(HorizonsModifier.ARMOUR, 260.0),
                        Map.entry(HorizonsModifier.MASS, 350.0),
//                    Map.entry(HorizonsModifier.FWDACC,29.59),
//                    Map.entry(HorizonsModifier.REVACC,18.02),
//                    Map.entry(HorizonsModifier.LATACC,15.92),
                        Map.entry(HorizonsModifier.MINIMUM_THRUST, 60.87),
                        Map.entry(HorizonsModifier.BOOST_COST, 23.0),
                        Map.entry(HorizonsModifier.PITCH_SPEED, 29.0),
                        Map.entry(HorizonsModifier.YAW_SPEED, 10.0),
                        Map.entry(HorizonsModifier.ROLL_SPEED, 90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                        Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 24.0),
                        Map.entry(HorizonsModifier.HEAT_CAPACITY, 300.0),
                        Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.68),
                        Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 52.05),
                        Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                        Map.entry(HorizonsModifier.FUEL_RESERVE, 0.83),
                        Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 65.0),
                        Map.entry(HorizonsModifier.MASS_LOCK, 17.0),
                        Map.entry(HorizonsModifier.CREW, 2.0)

                ),
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

        public static final Ship PYTHON_NX = new Ship(
                ShipType.PYTHON_NX,
                59255259,
                59255259,
                Map.ofEntries(
                        Map.entry(HorizonsModifier.TOP_SPEED, 256.0),
                        Map.entry(HorizonsModifier.BOOST_SPEED, 345.0),
                        Map.entry(HorizonsModifier.MANOEUVRABILITY, 5.0),
                        Map.entry(HorizonsModifier.SHIELDS, 335.0),
                        Map.entry(HorizonsModifier.ARMOUR, 280.0),
                        Map.entry(HorizonsModifier.MASS, 450.0),
//                    Map.entry(HorizonsModifier.FWDACC,31.65),
//                    Map.entry(HorizonsModifier.REVACC,25.94),
//                    Map.entry(HorizonsModifier.LATACC,20.09),
                        Map.entry(HorizonsModifier.MINIMUM_THRUST, 85.85),
                        Map.entry(HorizonsModifier.BOOST_COST, 20.0),
                        Map.entry(HorizonsModifier.PITCH_SPEED, 37.01),
                        Map.entry(HorizonsModifier.YAW_SPEED, 12.51),
                        Map.entry(HorizonsModifier.ROLL_SPEED, 91.03),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,120.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,120.0),
                        Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
                        Map.entry(HorizonsModifier.HEAT_CAPACITY, 260.0),
                        Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.68),
                        Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 52.05),
                        Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                        Map.entry(HorizonsModifier.FUEL_RESERVE, 0.83),
                        Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 70.0),
                        Map.entry(HorizonsModifier.MASS_LOCK, 17.0),
                        Map.entry(HorizonsModifier.CREW, 2.0)

                ),
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
                0,//TODO
                0,//TODO
                Map.ofEntries(
                        Map.entry(HorizonsModifier.TOP_SPEED, 130.0),//TODO
                        Map.entry(HorizonsModifier.BOOST_SPEED, 200.0),//TODO
                        Map.entry(HorizonsModifier.MANOEUVRABILITY, 0.0),//TODO
                        Map.entry(HorizonsModifier.SHIELDS, 240.0),//TODO
                        Map.entry(HorizonsModifier.ARMOUR, 480.0),//TODO
                        Map.entry(HorizonsModifier.MASS, 850.0),//TODO
//                    Map.entry(HorizonsModifier.FWDACC,20.03),
//                    Map.entry(HorizonsModifier.REVACC,10.11),
//                    Map.entry(HorizonsModifier.LATACC,10.03),
                        Map.entry(HorizonsModifier.MINIMUM_THRUST, 30.769),//TODO
                        Map.entry(HorizonsModifier.BOOST_COST, 19.0),//TODO
                        Map.entry(HorizonsModifier.PITCH_SPEED, 20.0),//TODO
                        Map.entry(HorizonsModifier.YAW_SPEED, 8.0),//TODO
                        Map.entry(HorizonsModifier.ROLL_SPEED, 20.0),//TODO
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,80.0),
                        Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 15.0),//TODO
                        Map.entry(HorizonsModifier.HEAT_CAPACITY, 289.0),//TODO
                        Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 3.1),//TODO
                        Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 48.35),//TODO
                        Map.entry(HorizonsModifier.FUEL_COST, 50.0),//TODO
                        Map.entry(HorizonsModifier.FUEL_RESERVE, 0.77),//TODO
                        Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 65.0),//TODO
                        Map.entry(HorizonsModifier.MASS_LOCK, 16.0),//TODO
                        Map.entry(HorizonsModifier.CREW, 3.0)//TODO

                ),
                List.of(
                        ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1152).y(496).index(0).slotSize(2).build(),
                        ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(1227).y(435).index(1).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                        ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(1).x(904).y(765).index(2).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                        ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1323).y(425).index(3).slotSize(1).build(),
                        ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1247).y(341).index(4).slotSize(1).build(),
                        ImageSlot.builder().slotType(SlotType.HARDPOINT).imageIndex(2).x(1247).y(341).index(5).slotSize(1).build()
                ),
                List.of(//TODO not mentioned on livestream
                        ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(1117).y(365).index(0).slotSize(0).build(),
                        ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(1).x(806).y(672).index(1).slotSize(0).build(),
                        ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(880).y(717).index(2).slotSize(0).build(),
                        ImageSlot.builder().slotType(SlotType.UTILITY).imageIndex(2).x(709).y(550).index(3).slotSize(0).build()
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
    public static final Ship TYPE_9 = new Ship(
            ShipType.TYPE_9,
            72108220,
            76555840,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 130.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 200.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 0.0),
                    Map.entry(HorizonsModifier.SHIELDS, 240.0),
                    Map.entry(HorizonsModifier.ARMOUR, 480.0),
                    Map.entry(HorizonsModifier.MASS, 850.0),
//                    Map.entry(HorizonsModifier.FWDACC,20.03),
//                    Map.entry(HorizonsModifier.REVACC,10.11),
//                    Map.entry(HorizonsModifier.LATACC,10.03),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 30.769),
                    Map.entry(HorizonsModifier.BOOST_COST, 19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 20.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 8.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 20.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,80.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 15.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 289.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 3.1),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 48.35),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.77),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 65.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 16.0),
                    Map.entry(HorizonsModifier.CREW, 3.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 200.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 280.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 2.0),
                    Map.entry(HorizonsModifier.SHIELDS, 280.0),
                    Map.entry(HorizonsModifier.ARMOUR, 280.0),
                    Map.entry(HorizonsModifier.MASS, 950.0),
//                    Map.entry(HorizonsModifier.FWDACC,20.01),
//                    Map.entry(HorizonsModifier.REVACC,17.12),
//                    Map.entry(HorizonsModifier.LATACC,15.03),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 55.0),
                    Map.entry(HorizonsModifier.BOOST_COST, 19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 25.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 17.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 60.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,80.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 20.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 283.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.6),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 50.85),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.81),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 60.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 18.0),
                    Map.entry(HorizonsModifier.CREW, 3.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 180.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 220.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 0.0),
                    Map.entry(HorizonsModifier.SHIELDS, 320.0),
                    Map.entry(HorizonsModifier.ARMOUR, 580.0),
                    Map.entry(HorizonsModifier.MASS, 1200.0),
//                    Map.entry(HorizonsModifier.FWDACC,17.96),
//                    Map.entry(HorizonsModifier.REVACC,10.04),
//                    Map.entry(HorizonsModifier.LATACC,10.09),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 83.333),
                    Map.entry(HorizonsModifier.BOOST_COST, 19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 22.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 8.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 40.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,35.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,80.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 18.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 335.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 3.16),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 67.15),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 0.77),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 75.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 26.0),
                    Map.entry(HorizonsModifier.CREW, 3.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 180.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 240.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 1.0),
                    Map.entry(HorizonsModifier.SHIELDS, 350.0),
                    Map.entry(HorizonsModifier.ARMOUR, 525.0),
                    Map.entry(HorizonsModifier.MASS, 400.0),
//                    Map.entry(HorizonsModifier.FWDACC,19.85),
//                    Map.entry(HorizonsModifier.REVACC,10.03),
//                    Map.entry(HorizonsModifier.LATACC,10.05),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 44.444),
                    Map.entry(HorizonsModifier.BOOST_COST, 27.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 25.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 10.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 60.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,80.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 20.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 334.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 3.16),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 67.15),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 1.07),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 65.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 23.0),
                    Map.entry(HorizonsModifier.CREW, 3.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 200.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 260.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 2.0),
                    Map.entry(HorizonsModifier.SHIELDS, 555.0),
                    Map.entry(HorizonsModifier.ARMOUR, 370.0),
                    Map.entry(HorizonsModifier.MASS, 900.0),
//                    Map.entry(HorizonsModifier.FWDACC,19.87),
//                    Map.entry(HorizonsModifier.REVACC,10.08),
//                    Map.entry(HorizonsModifier.LATACC,9.98),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 50.0),
                    Map.entry(HorizonsModifier.BOOST_COST, 27.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 28.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 8.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 75.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,80.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 22.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 333.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 3.28),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 70.33),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 1.13),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 70.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 24.0),
                    Map.entry(HorizonsModifier.CREW, 3.0)

            ),
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED, 200.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED, 320.0),
                    Map.entry(HorizonsModifier.MANOEUVRABILITY, 0.0),
                    Map.entry(HorizonsModifier.SHIELDS, 600.0),
                    Map.entry(HorizonsModifier.ARMOUR, 400.0),
                    Map.entry(HorizonsModifier.MASS, 1100.0),
//                    Map.entry(HorizonsModifier.FWDACC,29.37),
//                    Map.entry(HorizonsModifier.REVACC,10.04),
//                    Map.entry(HorizonsModifier.LATACC,6.06),
                    Map.entry(HorizonsModifier.MINIMUM_THRUST, 80.0),
                    Map.entry(HorizonsModifier.BOOST_COST, 23.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED, 18.0),
                    Map.entry(HorizonsModifier.YAW_SPEED, 8.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED, 45.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,80.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 14.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY, 327.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 3.27),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 72.58),
                    Map.entry(HorizonsModifier.FUEL_COST, 50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE, 1.16),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 70.0),
                    Map.entry(HorizonsModifier.MASS_LOCK, 26.0),
                    Map.entry(HorizonsModifier.CREW, 3.0)

            ),
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
            PYTHON_NX
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
                .filter(slot -> slot.getShipModule() instanceof CargoRack || slot.getShipModule() instanceof AntiCorrosionCargoRack)
                .map(Slot::getShipModule)
                .map(shipModule -> (double) shipModule.getAttributeValue(HorizonsModifier.CARGO_CAPACITY))
                .mapToDouble(Double::doubleValue)
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
        this.currentCargo = Math.min(currentCargo, getMaxCargo() + getMaxPassenger());
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
}
