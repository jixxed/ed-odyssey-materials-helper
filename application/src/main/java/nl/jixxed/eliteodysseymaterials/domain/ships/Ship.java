package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint.PulseLaser;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military.GuardianHullReinforcementPackage;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military.HullReinforcementPackage;
import nl.jixxed.eliteodysseymaterials.domain.ships.special.FuelTank;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Ship {
    private static final List<List<? extends ShipModule>> ALL_MODULES = List.of(
            //hardpoint
            PulseLaser.PULSE_LASERS,
            //core
            Armour.ARMOURS,
            FrameShiftDrive.FRAME_SHIFT_DRIVES,
            LifeSupport.LIFE_SUPPORTS,
            PowerDistributor.POWER_DISTRIBUTORS,
            PowerPlant.POWER_PLANTS,
            Sensors.SENSORS,
            Thrusters.THRUSTERS,
            FuelTank.FUEL_TANKS,
            //military
            GuardianHullReinforcementPackage.GUARDIAN_HULL_REINFORCEMENT_PACKAGES,
            HullReinforcementPackage.HULL_REINFORCEMENT_PACKAGES,
            //optional
            CargoRack.CARGO_RACKS,
            FrameShiftDriveBooster.FRAME_SHIFT_DRIVE_BOOSTERS,
            Computer.COMPUTERS,
            ShieldGenerator.SHIELD_GENERATORS,
            PassengerCabin.PASSENGER_CABINS,
            DetailedSurfaceScanner.DETAILED_SURFACE_SCANNERS
            //utility

    );
    public static final Ship SIDE_WINDER = new Ship(
            ShipType.SIDE_WINDER,
            5070,
            32000,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,220.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,320.0),
//                    Map.entry(HorizonsModifier.MNV,5.0),
                    Map.entry(HorizonsModifier.SHIELDS,40.0),
                    Map.entry(HorizonsModifier.ARMOUR,60.0),
                    Map.entry(HorizonsModifier.MASS,25.0),
//                    Map.entry(HorizonsModifier.FWDACC,44.39),
//                    Map.entry(HorizonsModifier.REVACC,29.96),
//                    Map.entry(HorizonsModifier.LATACC,29.96),
//                    Map.entry(HorizonsModifier.MINTHRUST,45.454),
                    Map.entry(HorizonsModifier.BOOST_COST,7.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,42.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,16.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,110.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,220.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,110.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,240.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,34.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,140.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,1.18),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,18.15),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.3),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,20.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,6.0),
                    Map.entry(HorizonsModifier.CREW,1.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship EAGLE = new Ship(//Eagle MkII
            ShipType.EAGLE,
            7490,
            44800,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,240.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,350.0),
//                    Map.entry(HorizonsModifier.MNV,7.0),
                    Map.entry(HorizonsModifier.SHIELDS,60.0),
                    Map.entry(HorizonsModifier.ARMOUR,40.0),
                    Map.entry(HorizonsModifier.MASS,50.0),
//                    Map.entry(HorizonsModifier.FWDACC,43.97),
//                    Map.entry(HorizonsModifier.REVACC,29.97),
//                    Map.entry(HorizonsModifier.LATACC,29.86),
//                    Map.entry(HorizonsModifier.MINTHRUST,75.0),
                    Map.entry(HorizonsModifier.BOOST_COST,8.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,50.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,18.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,120.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,220.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,110.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,240.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,40.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,165.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,1.38),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,21.48),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.34),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,28.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,6.0),
                    Map.entry(HorizonsModifier.CREW,1.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(1).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.EAGLE_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(2).shipModule(PowerPlant.POWER_PLANT_2_E).build(),
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship HAULER = new Ship(
            ShipType.HAULER,
            8160,
            52720,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,200.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,300.0),
//                    Map.entry(HorizonsModifier.MNV,4.0),
                    Map.entry(HorizonsModifier.SHIELDS,50.0),
                    Map.entry(HorizonsModifier.ARMOUR,100.0),
                    Map.entry(HorizonsModifier.MASS,14.0),
//                    Map.entry(HorizonsModifier.FWDACC,39.87),
//                    Map.entry(HorizonsModifier.REVACC,29.95),
//                    Map.entry(HorizonsModifier.LATACC,29.95),
//                    Map.entry(HorizonsModifier.MINTHRUST,35.0),
                    Map.entry(HorizonsModifier.BOOST_COST,7.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,36.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,14.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,100.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,220.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,110.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,240.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,123.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,1.06),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,16.2),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.25),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,20.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,6.0),
                    Map.entry(HorizonsModifier.CREW,1.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship ADDER = new Ship(
            ShipType.ADDER,
            18710,
            87810,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,220.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,320.0),
//                    Map.entry(HorizonsModifier.MNV,4.0),
                    Map.entry(HorizonsModifier.SHIELDS,60.0),
                    Map.entry(HorizonsModifier.ARMOUR,90.0),
                    Map.entry(HorizonsModifier.MASS,35.0),
//                    Map.entry(HorizonsModifier.FWDACC,39.41),
//                    Map.entry(HorizonsModifier.REVACC,27.73),
//                    Map.entry(HorizonsModifier.LATACC,27.86),
//                    Map.entry(HorizonsModifier.MINTHRUST,45.454),
                    Map.entry(HorizonsModifier.BOOST_COST,8.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,38.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,14.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,100.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,170.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,1.45),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,22.6),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.36),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,35.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,7.0),
                    Map.entry(HorizonsModifier.CREW,2.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship EMPIRE_EAGLE = new Ship(//Imperial Eagle
            ShipType.EMPIRE_EAGLE,
            50890,
            110830,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,300.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,400.0),
//                    Map.entry(HorizonsModifier.MNV,5.0),
                    Map.entry(HorizonsModifier.SHIELDS,80.0),
                    Map.entry(HorizonsModifier.ARMOUR,60.0),
                    Map.entry(HorizonsModifier.MASS,50.0),
//                    Map.entry(HorizonsModifier.FWDACC,34.54),
//                    Map.entry(HorizonsModifier.REVACC,27.84),
//                    Map.entry(HorizonsModifier.LATACC,27.84),
//                    Map.entry(HorizonsModifier.MINTHRUST,70.0),
                    Map.entry(HorizonsModifier.BOOST_COST,8.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,40.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,15.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,100.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,220.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,110.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,240.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,163.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,1.5),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,21.2),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.37),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,28.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,6.0),
                    Map.entry(HorizonsModifier.CREW,1.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship VIPER = new Ship(//viper MK III
            ShipType.VIPER,
            74610,
            142930,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,320.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,400.0),
//                    Map.entry(HorizonsModifier.MNV,4.0),
                    Map.entry(HorizonsModifier.SHIELDS,105.0),
                    Map.entry(HorizonsModifier.ARMOUR,70.0),
                    Map.entry(HorizonsModifier.MASS,50.0),
//                    Map.entry(HorizonsModifier.FWDACC,53.98),
//                    Map.entry(HorizonsModifier.REVACC,29.7),
//                    Map.entry(HorizonsModifier.LATACC,24.95),
//                    Map.entry(HorizonsModifier.MINTHRUST,62.5),
                    Map.entry(HorizonsModifier.BOOST_COST,10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,35.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,15.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,195.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,1.69),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,26.2),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.41),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,35.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,7.0),
                    Map.entry(HorizonsModifier.CREW,1.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(1).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship COBRA_MK_III = new Ship(
            ShipType.COBRA_MK_III,
            186260,
            349720,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,280.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,400.0),
//                    Map.entry(HorizonsModifier.MNV,5.0),
                    Map.entry(HorizonsModifier.SHIELDS,80.0),
                    Map.entry(HorizonsModifier.ARMOUR,120.0),
                    Map.entry(HorizonsModifier.MASS,180.0),
//                    Map.entry(HorizonsModifier.FWDACC,35.03),
//                    Map.entry(HorizonsModifier.REVACC,25.16),
//                    Map.entry(HorizonsModifier.LATACC,20.02),
//                    Map.entry(HorizonsModifier.MINTHRUST,50.0),
                    Map.entry(HorizonsModifier.BOOST_COST,10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,40.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,10.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,100.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,225.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,1.92),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,30.63),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.49),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,35.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,8.0),
                    Map.entry(HorizonsModifier.CREW,2.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(1).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship VIPER_MK_IV = new Ship(
            ShipType.VIPER_MK_IV,
            290680,
            437930,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,270.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,340.0),
//                    Map.entry(HorizonsModifier.MNV,3.0),
                    Map.entry(HorizonsModifier.SHIELDS,150.0),
                    Map.entry(HorizonsModifier.ARMOUR,150.0),
                    Map.entry(HorizonsModifier.MASS,190.0),
//                    Map.entry(HorizonsModifier.FWDACC,53.84),
//                    Map.entry(HorizonsModifier.REVACC,30.14),
//                    Map.entry(HorizonsModifier.LATACC,24.97),
//                    Map.entry(HorizonsModifier.MINTHRUST,64.815),
                    Map.entry(HorizonsModifier.BOOST_COST,10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,30.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,12.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,25.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,209.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,1.82),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,28.98),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.46),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,35.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,7.0),
                    Map.entry(HorizonsModifier.CREW,1.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(1).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(2).shipModule(CargoRack.CARGO_RACK_1_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship DIAMOND_BACK = new Ship(//scout
            ShipType.DIAMOND_BACK,
            441800,
            564330,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,280.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,380.0),
//                    Map.entry(HorizonsModifier.MNV,5.0),
                    Map.entry(HorizonsModifier.SHIELDS,120.0),
                    Map.entry(HorizonsModifier.ARMOUR,120.0),
                    Map.entry(HorizonsModifier.MASS,170.0),
//                    Map.entry(HorizonsModifier.FWDACC,39.57),
//                    Map.entry(HorizonsModifier.REVACC,29.82),
//                    Map.entry(HorizonsModifier.LATACC,25.19),
//                    Map.entry(HorizonsModifier.MINTHRUST,60.714),
                    Map.entry(HorizonsModifier.BOOST_COST,10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,42.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,15.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,100.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,35.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,346.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,2.42),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,48.05),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.49),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,40.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,8.0),
                    Map.entry(HorizonsModifier.CREW,1.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(1).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(3).shipModule(ShieldGenerator.SHIELD_GENERATOR_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship COBRA_MK_IV = new Ship(
            ShipType.COBRA_MK_IV,
            584200,
            764720,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,200.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,300.0),
//                    Map.entry(HorizonsModifier.MNV,3.0),
                    Map.entry(HorizonsModifier.SHIELDS,120.0),
                    Map.entry(HorizonsModifier.ARMOUR,120.0),
                    Map.entry(HorizonsModifier.MASS,210.0),
//                    Map.entry(HorizonsModifier.FWDACC,27.84),
//                    Map.entry(HorizonsModifier.REVACC,19.91),
//                    Map.entry(HorizonsModifier.LATACC,15.03),
//                    Map.entry(HorizonsModifier.MINTHRUST,50.0),
                    Map.entry(HorizonsModifier.BOOST_COST,10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,30.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,10.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,25.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,228.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,1.99),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,31.68),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.51),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,35.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,8.0),
                    Map.entry(HorizonsModifier.CREW,2.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(4).slotSize(1).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build()
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
                    Map.entry(HorizonsModifier.TOP_SPEED,220.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,350.0),
//                    Map.entry(HorizonsModifier.MNV,3.0),
                    Map.entry(HorizonsModifier.SHIELDS,90.0),
                    Map.entry(HorizonsModifier.ARMOUR,180.0),
                    Map.entry(HorizonsModifier.MASS,155.0),
//                    Map.entry(HorizonsModifier.FWDACC,20.1),
//                    Map.entry(HorizonsModifier.REVACC,14.96),
//                    Map.entry(HorizonsModifier.LATACC,15.07),
//                    Map.entry(HorizonsModifier.MINTHRUST,40.909),
                    Map.entry(HorizonsModifier.BOOST_COST,10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,30.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,17.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,100.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,220.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,110.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,240.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,23.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,179.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,1.7),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,24.55),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.39),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,35.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,8.0),
                    Map.entry(HorizonsModifier.CREW,1.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(4).shipModule(CargoRack.CARGO_RACK_4_E).build(),
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
                    Map.entry(HorizonsModifier.TOP_SPEED,250.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,350.0),
//                    Map.entry(HorizonsModifier.MNV,3.0),
                    Map.entry(HorizonsModifier.SHIELDS,110.0),
                    Map.entry(HorizonsModifier.ARMOUR,110.0),
                    Map.entry(HorizonsModifier.MASS,140.0),
//                    Map.entry(HorizonsModifier.FWDACC,39.63),
//                    Map.entry(HorizonsModifier.REVACC,30.01),
//                    Map.entry(HorizonsModifier.LATACC,14.97),
//                    Map.entry(HorizonsModifier.MINTHRUST,48.0),
                    Map.entry(HorizonsModifier.BOOST_COST,10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,30.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,20.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,100.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,220.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,110.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,240.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,23.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,245.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,1.91),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,56.0),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.5),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,35.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,9.0),
                    Map.entry(HorizonsModifier.CREW,1.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship DIAMOND_BACK_XL = new Ship(//explorer
            ShipType.DIAMOND_BACK_XL,
            1616160,
            1894760,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,260.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,340.0),
//                    Map.entry(HorizonsModifier.MNV,4.0),
                    Map.entry(HorizonsModifier.SHIELDS,150.0),
                    Map.entry(HorizonsModifier.ARMOUR,150.0),
                    Map.entry(HorizonsModifier.MASS,260.0),
//                    Map.entry(HorizonsModifier.FWDACC,34.63),
//                    Map.entry(HorizonsModifier.REVACC,25.06),
//                    Map.entry(HorizonsModifier.LATACC,19.89),
//                    Map.entry(HorizonsModifier.MINTHRUST,61.538),
                    Map.entry(HorizonsModifier.BOOST_COST,13.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,35.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,13.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,28.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,351.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,2.46),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,50.55),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.52),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,42.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,10.0),
                    Map.entry(HorizonsModifier.CREW,1.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship EMPIRE_COURIER = new Ship(//imperial courier
            ShipType.EMPIRE_COURIER,
            2462010,
            2542930,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,280.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,380.0),
//                    Map.entry(HorizonsModifier.MNV,4.0),
                    Map.entry(HorizonsModifier.SHIELDS,200.0),
                    Map.entry(HorizonsModifier.ARMOUR,80.0),
                    Map.entry(HorizonsModifier.MASS,35.0),
//                    Map.entry(HorizonsModifier.FWDACC,57.53),
//                    Map.entry(HorizonsModifier.REVACC,30.02),
//                    Map.entry(HorizonsModifier.LATACC,24.88),
//                    Map.entry(HorizonsModifier.MINTHRUST,78.571),
                    Map.entry(HorizonsModifier.BOOST_COST,10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,38.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,16.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,32.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,230.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,1.62),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,25.05),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.41),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,30.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,7.0),
                    Map.entry(HorizonsModifier.CREW,1.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(2).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship INDEPENDANT_TRADER = new Ship(//keelback
            ShipType.INDEPENDANT_TRADER,
            2937840,
            3126150,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,200.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,300.0),
//                    Map.entry(HorizonsModifier.MNV,2.0),
                    Map.entry(HorizonsModifier.SHIELDS,135.0),
                    Map.entry(HorizonsModifier.ARMOUR,270.0),
                    Map.entry(HorizonsModifier.MASS,180.0),
//                    Map.entry(HorizonsModifier.FWDACC,20.22),
//                    Map.entry(HorizonsModifier.REVACC,15.07),
//                    Map.entry(HorizonsModifier.LATACC,15.03),
//                    Map.entry(HorizonsModifier.MINTHRUST,45.0),
                    Map.entry(HorizonsModifier.BOOST_COST,10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,27.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,15.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,100.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,220.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,110.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,240.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,20.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,215.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,1.87),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,29.78),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.39),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,45.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,8.0),
                    Map.entry(HorizonsModifier.CREW,2.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(4).shipModule(CargoRack.CARGO_RACK_2_E).build(),
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
                    Map.entry(HorizonsModifier.TOP_SPEED,220.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,300.0),
//                    Map.entry(HorizonsModifier.MNV,5.0),
                    Map.entry(HorizonsModifier.SHIELDS,120.0),
                    Map.entry(HorizonsModifier.ARMOUR,180.0),
                    Map.entry(HorizonsModifier.MASS,150.0),
//                    Map.entry(HorizonsModifier.FWDACC,35.02),
//                    Map.entry(HorizonsModifier.REVACC,20.1),
//                    Map.entry(HorizonsModifier.LATACC,20.03),
//                    Map.entry(HorizonsModifier.MINTHRUST,50.0),
                    Map.entry(HorizonsModifier.BOOST_COST,13.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,40.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,15.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,110.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,35.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,210.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,1.8),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,29.65),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.47),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,52.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,8.0),
                    Map.entry(HorizonsModifier.CREW,2.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build()
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
                    Map.entry(HorizonsModifier.TOP_SPEED,210.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,340.0),
//                    Map.entry(HorizonsModifier.MNV,5.0),
                    Map.entry(HorizonsModifier.SHIELDS,240.0),
                    Map.entry(HorizonsModifier.ARMOUR,160.0),
                    Map.entry(HorizonsModifier.MASS,230.0),
//                    Map.entry(HorizonsModifier.FWDACC,39.55),
//                    Map.entry(HorizonsModifier.REVACC,29.88),
//                    Map.entry(HorizonsModifier.LATACC,19.98),
//                    Map.entry(HorizonsModifier.MINTHRUST,90.476),
                    Map.entry(HorizonsModifier.BOOST_COST,16.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,42.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,17.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,110.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,180.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,90.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,200.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,35.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,237.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,1.87),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,35.63),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.57),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,55.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,10.0),
                    Map.entry(HorizonsModifier.CREW,2.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(3).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(3).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).shipModule(Computer.ADVANCED_DOCKING_COMPUTER).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship ASP = new Ship(//asp explorer
            ShipType.ASP,
            6137180,
            6661160,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,250.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,340.0),
//                    Map.entry(HorizonsModifier.MNV,4.0),
                    Map.entry(HorizonsModifier.SHIELDS,140.0),
                    Map.entry(HorizonsModifier.ARMOUR,210.0),
                    Map.entry(HorizonsModifier.MASS,280.0),
//                    Map.entry(HorizonsModifier.FWDACC,23.64),
//                    Map.entry(HorizonsModifier.REVACC,15.04),
//                    Map.entry(HorizonsModifier.LATACC,14.97),
//                    Map.entry(HorizonsModifier.MINTHRUST,48.0),
                    Map.entry(HorizonsModifier.BOOST_COST,13.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,38.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,10.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,100.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,272.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,2.34),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,39.9),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.63),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,52.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,11.0),
                    Map.entry(HorizonsModifier.CREW,2.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(4).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(5).slotSize(1).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ));

    public static final Ship FEDERATION_DROPSHIP = new Ship(
            ShipType.FEDERATION_DROPSHIP,
            13501480,
            14314210,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,180.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,300.0),
//                    Map.entry(HorizonsModifier.MNV,3.0),
                    Map.entry(HorizonsModifier.SHIELDS,200.0),
                    Map.entry(HorizonsModifier.ARMOUR,300.0),
                    Map.entry(HorizonsModifier.MASS,580.0),
//                    Map.entry(HorizonsModifier.FWDACC,29.99),
//                    Map.entry(HorizonsModifier.REVACC,20.34),
//                    Map.entry(HorizonsModifier.LATACC,10.19),
//                    Map.entry(HorizonsModifier.MINTHRUST,55.556),
                    Map.entry(HorizonsModifier.BOOST_COST,19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,30.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,14.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,80.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,80.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,20.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,331.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,2.6),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,46.5),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.83),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,60.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,14.0),
                    Map.entry(HorizonsModifier.CREW,2.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(4).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship TYPE_7 = new Ship(
            ShipType.TYPE_7,
            16774470,
            17472250,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,180.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,300.0),
//                    Map.entry(HorizonsModifier.MNV,1.0),
                    Map.entry(HorizonsModifier.SHIELDS,156.0),
                    Map.entry(HorizonsModifier.ARMOUR,340.0),
                    Map.entry(HorizonsModifier.MASS,350.0),
//                    Map.entry(HorizonsModifier.FWDACC,20.11),
//                    Map.entry(HorizonsModifier.REVACC,15.02),
//                    Map.entry(HorizonsModifier.LATACC,15.13),
//                    Map.entry(HorizonsModifier.MINTHRUST,33.333),
                    Map.entry(HorizonsModifier.BOOST_COST,10.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,22.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,22.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,60.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,200.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,16.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,226.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,2.17),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,32.45),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.52),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,54.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,10.0),
                    Map.entry(HorizonsModifier.CREW,1.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(1).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.TYPE_7_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(5).shipModule(PowerPlant.POWER_PLANT_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(5).shipModule(Thrusters.THRUSTERS_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(5).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(4).shipModule(LifeSupport.LIFE_SUPPORT_4_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(4).shipModule(PowerDistributor.POWER_DISTRIBUTOR_4_E).build(),
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
                    Map.entry(HorizonsModifier.TOP_SPEED,230.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,330.0),
//                    Map.entry(HorizonsModifier.MNV,4.0),
                    Map.entry(HorizonsModifier.SHIELDS,200.0),
                    Map.entry(HorizonsModifier.ARMOUR,280.0),
                    Map.entry(HorizonsModifier.MASS,400.0),
//                    Map.entry(HorizonsModifier.FWDACC,37.84),
//                    Map.entry(HorizonsModifier.REVACC,25.84),
//                    Map.entry(HorizonsModifier.LATACC,20.01),
//                    Map.entry(HorizonsModifier.MINTHRUST,65.217),
                    Map.entry(HorizonsModifier.BOOST_COST,19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,38.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,16.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,170.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,60.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,150.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,32.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,289.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,2.6),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,46.5),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.77),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,65.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,13.0),
                    Map.entry(HorizonsModifier.CREW,2.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(3).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(4).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(5).slotSize(1).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship FEDERATION_DROPSHIP_MKII = new Ship(//Federal Assault Ship
            ShipType.FEDERATION_DROPSHIP_MK_II,
            19102490,
            19814210,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,210.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,350.0),
//                    Map.entry(HorizonsModifier.MNV,4.0),
                    Map.entry(HorizonsModifier.SHIELDS,200.0),
                    Map.entry(HorizonsModifier.ARMOUR,300.0),
                    Map.entry(HorizonsModifier.MASS,480.0),
//                    Map.entry(HorizonsModifier.FWDACC,39.81),
//                    Map.entry(HorizonsModifier.REVACC,20.04),
//                    Map.entry(HorizonsModifier.LATACC,15.07),
//                    Map.entry(HorizonsModifier.MINTHRUST,71.429),
                    Map.entry(HorizonsModifier.BOOST_COST,19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,38.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,19.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,170.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,80.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,200.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,286.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,2.53),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,45.23),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.72),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,60.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,14.0),
                    Map.entry(HorizonsModifier.CREW,2.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(3).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship EMPIRE_TRADER = new Ship(//imperial clipper
            ShipType.EMPIRE_TRADER,
            21108270,
            22295860,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,300.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,380.0),
//                    Map.entry(HorizonsModifier.MNV,5.0),
                    Map.entry(HorizonsModifier.SHIELDS,180.0),
                    Map.entry(HorizonsModifier.ARMOUR,270.0),
                    Map.entry(HorizonsModifier.MASS,400.0),
//                    Map.entry(HorizonsModifier.FWDACC,24.74),
//                    Map.entry(HorizonsModifier.REVACC,20.05),
//                    Map.entry(HorizonsModifier.LATACC,10.1),
//                    Map.entry(HorizonsModifier.MINTHRUST,60.0),
                    Map.entry(HorizonsModifier.BOOST_COST,19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,40.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,18.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,80.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,304.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,2.63),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,46.8),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.74),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,60.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,12.0),
                    Map.entry(HorizonsModifier.CREW,2.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build()
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
                    Map.entry(HorizonsModifier.TOP_SPEED,180.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,300.0),
//                    Map.entry(HorizonsModifier.MNV,3.0),
                    Map.entry(HorizonsModifier.SHIELDS,200.0),
                    Map.entry(HorizonsModifier.ARMOUR,300.0),
                    Map.entry(HorizonsModifier.MASS,500.0),
//                    Map.entry(HorizonsModifier.FWDACC,29.78),
//                    Map.entry(HorizonsModifier.REVACC,24.78),
//                    Map.entry(HorizonsModifier.LATACC,18.96),
//                    Map.entry(HorizonsModifier.MINTHRUST,61.11),
                    Map.entry(HorizonsModifier.BOOST_COST,19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,32.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,16.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,80.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,150.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,150.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,316.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,2.53),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,45.23),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.77),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,65.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,13.0),
                    Map.entry(HorizonsModifier.CREW,3.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(4).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(5).slotSize(1).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship TYPE_X_3 = new Ship(//challenger
            ShipType.TYPE_X_3,
            29561170,
            30472250,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,200.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,310.0),
//                    Map.entry(HorizonsModifier.MNV,4.0),
                    Map.entry(HorizonsModifier.SHIELDS,220.0),
                    Map.entry(HorizonsModifier.ARMOUR,300.0),
                    Map.entry(HorizonsModifier.MASS,450.0),
//                    Map.entry(HorizonsModifier.FWDACC,31.65),
//                    Map.entry(HorizonsModifier.REVACC,25.94),
//                    Map.entry(HorizonsModifier.LATACC,20.09),
//                    Map.entry(HorizonsModifier.MINTHRUST,65.0),
                    Map.entry(HorizonsModifier.BOOST_COST,19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,35.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,16.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,120.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,120.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,32.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,316.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,2.87),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,51.4),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.77),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,65.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,13.0),
                    Map.entry(HorizonsModifier.CREW,2.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(4).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(5).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(6).slotSize(1).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(6).shipModule(CargoRack.CARGO_RACK_5_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(6).shipModule(ShieldGenerator.SHIELD_GENERATOR_6_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship FEDERATION_GUNSHIP = new Ship(
            ShipType.FEDERATION_GUNSHIP,
            34806280,
            35814210,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,170.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,280.0),
//                    Map.entry(HorizonsModifier.MNV,1.0),
                    Map.entry(HorizonsModifier.SHIELDS,250.0),
                    Map.entry(HorizonsModifier.ARMOUR,350.0),
                    Map.entry(HorizonsModifier.MASS,580.0),
//                    Map.entry(HorizonsModifier.FWDACC,24.61),
//                    Map.entry(HorizonsModifier.REVACC,17.83),
//                    Map.entry(HorizonsModifier.LATACC,10.08),
//                    Map.entry(HorizonsModifier.MINTHRUST,58.824),
                    Map.entry(HorizonsModifier.BOOST_COST,23.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,25.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,18.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,80.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,80.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,20.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,325.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,2.87),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,51.4),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.82),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,60.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,14.0),
                    Map.entry(HorizonsModifier.CREW,2.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(4).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(5).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(6).slotSize(1).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship KRAIT_LIGHT = new Ship(
            ShipType.KRAIT_LIGHT,
            35732880,
            37472250,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,250.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,350.0),
//                    Map.entry(HorizonsModifier.MNV,3.0),
                    Map.entry(HorizonsModifier.SHIELDS,200.0),
                    Map.entry(HorizonsModifier.ARMOUR,180.0),
                    Map.entry(HorizonsModifier.MASS,270.0),
//                    Map.entry(HorizonsModifier.FWDACC,NaN),
//                    Map.entry(HorizonsModifier.REVACC,NaN),
//                    Map.entry(HorizonsModifier.LATACC,NaN),
//                    Map.entry(HorizonsModifier.MINTHRUST,64.0),
                    Map.entry(HorizonsModifier.BOOST_COST,13.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,31.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,10.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,200.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,26.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,300.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,2.68),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,52.05),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.63),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,60.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,14.0),
                    Map.entry(HorizonsModifier.CREW,2.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build()
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
                    Map.entry(HorizonsModifier.TOP_SPEED,240.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,330.0),
//                    Map.entry(HorizonsModifier.MNV,3.0),
                    Map.entry(HorizonsModifier.SHIELDS,220.0),
                    Map.entry(HorizonsModifier.ARMOUR,220.0),
                    Map.entry(HorizonsModifier.MASS,320.0),
//                    Map.entry(HorizonsModifier.FWDACC,28.01),
//                    Map.entry(HorizonsModifier.REVACC,18.04),
//                    Map.entry(HorizonsModifier.LATACC,15.12),
//                    Map.entry(HorizonsModifier.MINTHRUST,62.5),
                    Map.entry(HorizonsModifier.BOOST_COST,13.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,31.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,10.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,200.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,26.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,300.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,2.68),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,52.05),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.63),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,55.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,16.0),
                    Map.entry(HorizonsModifier.CREW,3.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(4).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(5).shipModule(CargoRack.CARGO_RACK_5_E).build(),
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
                    Map.entry(HorizonsModifier.TOP_SPEED,300.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,380.0),
//                    Map.entry(HorizonsModifier.MNV,1.0),
                    Map.entry(HorizonsModifier.SHIELDS,220.0),
                    Map.entry(HorizonsModifier.ARMOUR,220.0),
                    Map.entry(HorizonsModifier.MASS,290.0),
//                    Map.entry(HorizonsModifier.FWDACC,29.66),
//                    Map.entry(HorizonsModifier.REVACC,25.08),
//                    Map.entry(HorizonsModifier.LATACC,19.95),
//                    Map.entry(HorizonsModifier.MINTHRUST,66.667),
                    Map.entry(HorizonsModifier.BOOST_COST,16.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,25.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,18.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,55.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,220.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,110.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,240.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,20.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,262.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,2.3),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,42.68),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.79),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,55.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,15.0),
                    Map.entry(HorizonsModifier.CREW,2.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build()
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
                    Map.entry(HorizonsModifier.TOP_SPEED,260.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,350.0),
//                    Map.entry(HorizonsModifier.MNV,4.0),
                    Map.entry(HorizonsModifier.SHIELDS,300.0),
                    Map.entry(HorizonsModifier.ARMOUR,225.0),
                    Map.entry(HorizonsModifier.MASS,250.0),
//                    Map.entry(HorizonsModifier.FWDACC,29.31),
//                    Map.entry(HorizonsModifier.REVACC,24.34),
//                    Map.entry(HorizonsModifier.LATACC,20.04),
//                    Map.entry(HorizonsModifier.MINTHRUST,84.615),
                    Map.entry(HorizonsModifier.BOOST_COST,19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,38.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,12.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,30.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,224.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,2.05),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,41.63),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.67),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,70.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,12.0),
                    Map.entry(HorizonsModifier.CREW,2.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(4).slotSize(2).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(4).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(5).slotSize(0).build()
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
                    Map.entry(HorizonsModifier.TOP_SPEED,310.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,380.0),
//                    Map.entry(HorizonsModifier.MNV,3.0),
                    Map.entry(HorizonsModifier.SHIELDS,270.0),
                    Map.entry(HorizonsModifier.ARMOUR,230.0),
                    Map.entry(HorizonsModifier.MASS,250.0),
//                    Map.entry(HorizonsModifier.FWDACC,NaN),
//                    Map.entry(HorizonsModifier.REVACC,NaN),
//                    Map.entry(HorizonsModifier.LATACC,NaN),
//                    Map.entry(HorizonsModifier.MINTHRUST,77.42),
                    Map.entry(HorizonsModifier.BOOST_COST,16.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,30.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,10.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,75.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,180.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,90.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,200.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,27.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,165.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,2.05),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,41.63),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.5),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,70.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,12.0),
                    Map.entry(HorizonsModifier.CREW,2.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(4).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(4).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(5).slotSize(0).build()
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
                    Map.entry(HorizonsModifier.TOP_SPEED,230.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,300.0),
//                    Map.entry(HorizonsModifier.MNV,2.0),
                    Map.entry(HorizonsModifier.SHIELDS,260.0),
                    Map.entry(HorizonsModifier.ARMOUR,260.0),
                    Map.entry(HorizonsModifier.MASS,350.0),
//                    Map.entry(HorizonsModifier.FWDACC,29.59),
//                    Map.entry(HorizonsModifier.REVACC,18.02),
//                    Map.entry(HorizonsModifier.LATACC,15.92),
//                    Map.entry(HorizonsModifier.MINTHRUST,60.87),
                    Map.entry(HorizonsModifier.BOOST_COST,23.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,29.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,10.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,90.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,200.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,220.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,24.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,300.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,2.68),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,52.05),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.83),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,65.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,17.0),
                    Map.entry(HorizonsModifier.CREW,2.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(4).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build()
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
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,130.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,200.0),
//                    Map.entry(HorizonsModifier.MNV,0.0),
                    Map.entry(HorizonsModifier.SHIELDS,240.0),
                    Map.entry(HorizonsModifier.ARMOUR,480.0),
                    Map.entry(HorizonsModifier.MASS,850.0),
//                    Map.entry(HorizonsModifier.FWDACC,20.03),
//                    Map.entry(HorizonsModifier.REVACC,10.11),
//                    Map.entry(HorizonsModifier.LATACC,10.03),
//                    Map.entry(HorizonsModifier.MINTHRUST,30.769),
                    Map.entry(HorizonsModifier.BOOST_COST,19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,20.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,8.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,20.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,80.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,15.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,289.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,3.1),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,48.35),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.77),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,65.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,16.0),
                    Map.entry(HorizonsModifier.CREW,3.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(4).slotSize(1).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(8).shipModule(CargoRack.CARGO_RACK_7_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(8).shipModule(CargoRack.CARGO_RACK_7_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(7).shipModule(CargoRack.CARGO_RACK_6_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(6).shipModule(ShieldGenerator.SHIELD_GENERATOR_6_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(5).shipModule(CargoRack.CARGO_RACK_4_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(10).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship BELUGA_LINER = new Ship(
            ShipType.BELUGA_LINER,
            79686090,
            84532760,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,200.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,280.0),
//                    Map.entry(HorizonsModifier.MNV,2.0),
                    Map.entry(HorizonsModifier.SHIELDS,280.0),
                    Map.entry(HorizonsModifier.ARMOUR,280.0),
                    Map.entry(HorizonsModifier.MASS,950.0),
//                    Map.entry(HorizonsModifier.FWDACC,20.01),
//                    Map.entry(HorizonsModifier.REVACC,17.12),
//                    Map.entry(HorizonsModifier.LATACC,15.03),
//                    Map.entry(HorizonsModifier.MINTHRUST,55.0),
                    Map.entry(HorizonsModifier.BOOST_COST,19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,25.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,17.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,60.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,80.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,20.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,283.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,2.6),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,50.85),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.81),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,60.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,18.0),
                    Map.entry(HorizonsModifier.CREW,3.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(4).slotSize(2).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(4).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(5).slotSize(0).build()
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
                    Map.entry(HorizonsModifier.TOP_SPEED,180.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,220.0),
//                    Map.entry(HorizonsModifier.MNV,0.0),
                    Map.entry(HorizonsModifier.SHIELDS,320.0),
                    Map.entry(HorizonsModifier.ARMOUR,580.0),
                    Map.entry(HorizonsModifier.MASS,1200.0),
//                    Map.entry(HorizonsModifier.FWDACC,17.96),
//                    Map.entry(HorizonsModifier.REVACC,10.04),
//                    Map.entry(HorizonsModifier.LATACC,10.09),
//                    Map.entry(HorizonsModifier.MINTHRUST,83.333),
                    Map.entry(HorizonsModifier.BOOST_COST,19.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,22.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,8.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,40.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,35.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,80.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,18.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,335.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,3.16),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,67.15),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,0.77),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,75.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,26.0),
                    Map.entry(HorizonsModifier.CREW,3.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(4).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(5).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(6).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(7).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(8).slotSize(1).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(4).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(5).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(6).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(7).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.TYPE_9_MILITARY_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(8).shipModule(PowerPlant.POWER_PLANT_8_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(7).shipModule(Thrusters.THRUSTERS_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(7).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_7_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(5).shipModule(LifeSupport.LIFE_SUPPORT_5_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(7).shipModule(PowerDistributor.POWER_DISTRIBUTOR_7_E).build(),
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(7).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(10).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(11).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship ANACONDA = new Ship(
            ShipType.ANACONDA,
            142447820,
            146969450,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,180.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,240.0),
//                    Map.entry(HorizonsModifier.MNV,1.0),
                    Map.entry(HorizonsModifier.SHIELDS,350.0),
                    Map.entry(HorizonsModifier.ARMOUR,525.0),
                    Map.entry(HorizonsModifier.MASS,400.0),
//                    Map.entry(HorizonsModifier.FWDACC,19.85),
//                    Map.entry(HorizonsModifier.REVACC,10.03),
//                    Map.entry(HorizonsModifier.LATACC,10.05),
//                    Map.entry(HorizonsModifier.MINTHRUST,44.444),
                    Map.entry(HorizonsModifier.BOOST_COST,27.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,25.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,10.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,60.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,80.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,20.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,334.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,3.16),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,67.15),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,1.07),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,65.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,23.0),
                    Map.entry(HorizonsModifier.CREW,3.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(4).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(5).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(6).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(7).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(4).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(5).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(6).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(7).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(8).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(10).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(11).slotSize(2).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(12).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship FEDERATION_CORVETTE = new Ship(
            ShipType.FEDERATION_CORVETTE,
            183147460,
            187969450,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,200.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,260.0),
//                    Map.entry(HorizonsModifier.MNV,2.0),
                    Map.entry(HorizonsModifier.SHIELDS,555.0),
                    Map.entry(HorizonsModifier.ARMOUR,370.0),
                    Map.entry(HorizonsModifier.MASS,900.0),
//                    Map.entry(HorizonsModifier.FWDACC,19.87),
//                    Map.entry(HorizonsModifier.REVACC,10.08),
//                    Map.entry(HorizonsModifier.LATACC,9.98),
//                    Map.entry(HorizonsModifier.MINTHRUST,50.0),
                    Map.entry(HorizonsModifier.BOOST_COST,27.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,28.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,8.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,75.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,80.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,22.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,333.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,3.28),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,70.33),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,1.13),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,70.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,24.0),
                    Map.entry(HorizonsModifier.CREW,3.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(4).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(5).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(6).slotSize(1).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(4).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(5).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(6).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(7).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(4).shipModule(CargoRack.CARGO_RACK_3_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(10).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(11).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(12).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));

    public static final Ship CUTTER = new Ship(
            ShipType.CUTTER,
            200484780,
            208969450,
            Map.ofEntries(
                    Map.entry(HorizonsModifier.TOP_SPEED,200.0),
                    Map.entry(HorizonsModifier.BOOST_SPEED,320.0),
//                    Map.entry(HorizonsModifier.MNV,0.0),
                    Map.entry(HorizonsModifier.SHIELDS,600.0),
                    Map.entry(HorizonsModifier.ARMOUR,400.0),
                    Map.entry(HorizonsModifier.MASS,1100.0),
//                    Map.entry(HorizonsModifier.FWDACC,29.37),
//                    Map.entry(HorizonsModifier.REVACC,10.04),
//                    Map.entry(HorizonsModifier.LATACC,6.06),
//                    Map.entry(HorizonsModifier.MINTHRUST,80.0),
                    Map.entry(HorizonsModifier.BOOST_COST,23.0),
                    Map.entry(HorizonsModifier.PITCH_SPEED,18.0),
                    Map.entry(HorizonsModifier.YAW_SPEED,8.0),
                    Map.entry(HorizonsModifier.ROLL_SPEED,45.0),
//                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,100.0),
//                    Map.entry(HorizonsModifier.YAW_ACCELERATION,50.0),
//                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,80.0),
                    Map.entry(HorizonsModifier.MIN_PITCH_SPEED,14.0),
                    Map.entry(HorizonsModifier.HEAT_CAPACITY,327.0),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,3.27),
                    Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,72.58),
                    Map.entry(HorizonsModifier.FUEL_COST,50.0),
                    Map.entry(HorizonsModifier.FUEL_RESERVE,1.16),
                    Map.entry(HorizonsModifier.ARMOUR_HARDNESS,70.0),
                    Map.entry(HorizonsModifier.MASS_LOCK,26.0),
                    Map.entry(HorizonsModifier.CREW,3.0)

            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(3).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(3).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(4).slotSize(2).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(5).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(6).slotSize(2).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(2).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(3).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(4).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(5).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(6).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(7).slotSize(0).build()
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
                    Slot.builder().slotType(SlotType.OPTIONAL).index(9).slotSize(4).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(10).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(11).slotSize(1).shipModule(Computer.SUPERCRUISE_ASSIST).build()
            ));




    @Getter
    private final ShipType shipType;
    @Getter
    private final List<Slot> hardpointSlots;
    @Getter
    private final List<Slot> utilitySlots;
    @Getter
    private final List<Slot> coreSlots;
    @Getter
    private final List<Slot> optionalSlots;

    @Getter
    private final Map<HorizonsModifier, Object> attributes = new HashMap<>();
    @Getter
    @Setter
    private double currentFuel;
    @Getter
    @Setter
    private double currentCargo = 0D;
    @Getter
    @Setter
    private long price;
    @Getter
    @Setter
    private long retailPrice;

    private Ship(final ShipType shipType, final long price, final long retailPrice, final Map<HorizonsModifier, Object> attributes, final List<Slot> hardpointSlots, final List<Slot> utilitySlots, final List<Slot> coreSlots, final List<Slot> optionalSlots) {
        this.price = price;
        this.retailPrice = retailPrice;
        this.shipType = shipType;
        this.attributes.putAll(attributes);
        this.hardpointSlots = new ArrayList<>(hardpointSlots);
        this.utilitySlots = new ArrayList<>(utilitySlots);
        this.coreSlots = new ArrayList<>(coreSlots);
        this.optionalSlots = new ArrayList<>(optionalSlots);

        this.currentFuel = getMaxFuel();
    }

    public Ship(final Ship ship) {
        this.price = ship.price;
        this.retailPrice = ship.retailPrice;
        this.shipType = ship.shipType;
        this.hardpointSlots = new ArrayList<>(ship.hardpointSlots.stream().map(Slot::new).toList());
        this.utilitySlots = new ArrayList<>(ship.utilitySlots.stream().map(Slot::new).toList());
        this.coreSlots = new ArrayList<>(ship.coreSlots.stream().map(Slot::new).toList());
        this.optionalSlots = new ArrayList<>(ship.optionalSlots.stream().map(Slot::new).toList());
        this.attributes.putAll(ship.getAttributes());
        this.currentFuel = ship.getCurrentFuel();
    }

    public double getMaxFuel() {
        final Double coreFuel = this.getCoreSlots().stream()
                .filter(slot -> slot.getSlotType() == SlotType.CORE_FUEL_TANK)
                .findFirst().map(slot -> (double) slot.getShipModule().getAttributeValue(HorizonsModifier.FUEL_CAPACITY))
                .orElse(0D);
        final Double optionalFuel = this.getOptionalSlots().stream()
                .filter(slot -> slot.getShipModule() instanceof FuelTank)
                .map(slot -> (double) slot.getShipModule().getAttributeValue(HorizonsModifier.FUEL_CAPACITY))
                .mapToDouble(Double::doubleValue)
                .sum();
        return coreFuel + optionalFuel;
    }
    public double getMaxCargo() {
        return this.getOptionalSlots().stream()
                .filter(slot -> slot.getShipModule() instanceof CargoRack)
                .map(slot -> (double) slot.getShipModule().getAttributeValue(HorizonsModifier.CARGO_CAPACITY))
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    /**
     * Mass of the ship and modules without fuel, cargo and fuel reserve
     * @return
     */
    public double getEmptyMass() {
        return (double)this.attributes.getOrDefault(HorizonsModifier.MASS, 0.0)
                + Stream.concat(this.getCoreSlots().stream(),Stream.concat(this.getHardpointSlots().stream(),Stream.concat(this.getUtilitySlots().stream(),this.getOptionalSlots().stream())))
                        .map(slot -> (slot.getShipModule() == null) ? 0.0 : (double)slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MASS, 0.0))
                        .mapToDouble(Double::doubleValue)
                        .sum();
    }
    public double getFuelReserve() {
        return (double)this.attributes.getOrDefault(HorizonsModifier.FUEL_RESERVE, 0.0);
    }

}
