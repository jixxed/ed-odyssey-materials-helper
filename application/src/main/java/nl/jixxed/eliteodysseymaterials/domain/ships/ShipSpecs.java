package nl.jixxed.eliteodysseymaterials.domain.ships;

import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Map;

public class ShipSpecs {
    public static final Map<HorizonsModifier, Object> SIDE_WINDER = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 220.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 320.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 5.0),
            Map.entry(HorizonsModifier.SHIELDS, 40.0),
            Map.entry(HorizonsModifier.ARMOUR, 60.0),
            Map.entry(HorizonsModifier.MASS, 25.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 45.454),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 4.0),
            Map.entry(HorizonsModifier.BOOST_COST, 7.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 44.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 42.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 16.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 110.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 34.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 140.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.18),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 18.15),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.3),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 20.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 6.0)
    );
    public static final Map<HorizonsModifier, Object> EAGLE = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 240.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 350.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 7.0),
            Map.entry(HorizonsModifier.SHIELDS, 60.0),
            Map.entry(HorizonsModifier.ARMOUR, 40.0),
            Map.entry(HorizonsModifier.MASS, 50.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 75.0),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 4.5),
            Map.entry(HorizonsModifier.BOOST_COST, 8.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 44.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 50.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 18.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 120.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 40.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 165.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.38),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 21.48),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.34),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 28.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 6.0)


            //checked


    );
    public static final Map<HorizonsModifier, Object> HAULER = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 200.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 300.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
            Map.entry(HorizonsModifier.SHIELDS, 50.0),
            Map.entry(HorizonsModifier.ARMOUR, 100.0),
            Map.entry(HorizonsModifier.MASS, 14.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 35.0),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 4.0),
            Map.entry(HorizonsModifier.BOOST_COST, 7.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 40.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 36.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 14.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 100.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 123.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.06),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 16.2),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.25),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 20.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 6.0)


            //checked


    );
    public static final Map<HorizonsModifier, Object> ADDER = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 220.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 320.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
            Map.entry(HorizonsModifier.SHIELDS, 60.0),
            Map.entry(HorizonsModifier.ARMOUR, 90.0),
            Map.entry(HorizonsModifier.MASS, 35.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 45.454),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 4.0),
            Map.entry(HorizonsModifier.BOOST_COST, 8.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 39.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 28.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 28.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 38.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 14.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 100.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 170.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.45),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 22.6),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.36),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 35.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 7.0)


    );
    public static final Map<HorizonsModifier, Object> EMPIRE_EAGLE = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 300.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 400.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 5.0),
            Map.entry(HorizonsModifier.SHIELDS, 80.0),
            Map.entry(HorizonsModifier.ARMOUR, 60.0),
            Map.entry(HorizonsModifier.MASS, 50.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 70.0),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 4.5),
            Map.entry(HorizonsModifier.BOOST_COST, 8.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 35.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 28.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 28.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 40.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 15.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 100.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 163.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.5),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 21.2),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.37),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 28.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 6.0)


    );
    public static final Map<HorizonsModifier, Object> VIPER_MK_III = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 320.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 400.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
            Map.entry(HorizonsModifier.SHIELDS, 105.0),
            Map.entry(HorizonsModifier.ARMOUR, 70.0),
            Map.entry(HorizonsModifier.MASS, 50.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 62.5),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 5.0),
            Map.entry(HorizonsModifier.BOOST_COST, 10.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 54.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 25.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 35.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 15.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 90.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 195.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.69),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 26.2),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.41),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 35.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 7.0)


    );
    public static final Map<HorizonsModifier, Object> COBRA_MK_III = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 280.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 400.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 5.0),
            Map.entry(HorizonsModifier.SHIELDS, 80.0),
            Map.entry(HorizonsModifier.ARMOUR, 120.0),
            Map.entry(HorizonsModifier.MASS, 180.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 50.0),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 5.0),
            Map.entry(HorizonsModifier.BOOST_COST, 10.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 35.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 25.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 40.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 10.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 100.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 225.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.92),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 30.63),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.49),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 35.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 8.0)


    );
    public static final Map<HorizonsModifier, Object> VIPER_MK_IV = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 270.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 340.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 3.0),
            Map.entry(HorizonsModifier.SHIELDS, 150.0),
            Map.entry(HorizonsModifier.ARMOUR, 150.0),
            Map.entry(HorizonsModifier.MASS, 190.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 64.815),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 5.0),
            Map.entry(HorizonsModifier.BOOST_COST, 10.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 54.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 25.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 30.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 12.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 90.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 25.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 209.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.82),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 28.98),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.46),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 35.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 7.0)


    );
    public static final Map<HorizonsModifier, Object> DIAMOND_BACK = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 280.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 380.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 5.0),
            Map.entry(HorizonsModifier.SHIELDS, 120.0),
            Map.entry(HorizonsModifier.ARMOUR, 120.0),
            Map.entry(HorizonsModifier.MASS, 170.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 60.714),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 4.0),
            Map.entry(HorizonsModifier.BOOST_COST, 10.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 40.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 25.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 42.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 15.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 100.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 35.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 346.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.42),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 48.05),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.49),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 40.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 8.0)


    );
    public static final Map<HorizonsModifier, Object> COBRA_MK_IV = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 200.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 300.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 3.0),
            Map.entry(HorizonsModifier.SHIELDS, 120.0),
            Map.entry(HorizonsModifier.ARMOUR, 120.0),
            Map.entry(HorizonsModifier.MASS, 210.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 50.0),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 5.0),
            Map.entry(HorizonsModifier.BOOST_COST, 10.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 28.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 15.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 30.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 10.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 90.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 25.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 228.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.99),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 31.68),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.51),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 35.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 8.0)


    );
    public static final Map<HorizonsModifier, Object> TYPE_6 = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 220.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 350.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 3.0),
            Map.entry(HorizonsModifier.SHIELDS, 90.0),
            Map.entry(HorizonsModifier.ARMOUR, 180.0),
            Map.entry(HorizonsModifier.MASS, 155.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 40.909),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 4.0),
            Map.entry(HorizonsModifier.BOOST_COST, 10.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 15.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 15.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 30.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 17.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 100.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 23.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 179.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.7),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 24.55),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.39),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 35.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 8.0)


    );
    public static final Map<HorizonsModifier, Object> DOLPHIN = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 250.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 350.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 3.0),
            Map.entry(HorizonsModifier.SHIELDS, 110.0),
            Map.entry(HorizonsModifier.ARMOUR, 110.0),
            Map.entry(HorizonsModifier.MASS, 140.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 48.0),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 4.0),
            Map.entry(HorizonsModifier.BOOST_COST, 10.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 40.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 15.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 30.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 20.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 100.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 23.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 245.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.91),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 56.0),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.5),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 35.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 9.0)


    );
    public static final Map<HorizonsModifier, Object> DIAMOND_BACK_XL = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 260.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 340.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
            Map.entry(HorizonsModifier.SHIELDS, 150.0),
            Map.entry(HorizonsModifier.ARMOUR, 150.0),
            Map.entry(HorizonsModifier.MASS, 260.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 61.538),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 4.0),
            Map.entry(HorizonsModifier.BOOST_COST, 13.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 35.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 25.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 35.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 13.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 90.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 28.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 351.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.46),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 50.55),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.52),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 42.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 10.0)


    );
    public static final Map<HorizonsModifier, Object> EMPIRE_COURIER = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 280.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 380.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
            Map.entry(HorizonsModifier.SHIELDS, 200.0),
            Map.entry(HorizonsModifier.ARMOUR, 80.0),
            Map.entry(HorizonsModifier.MASS, 35.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 78.571),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 4.0),
            Map.entry(HorizonsModifier.BOOST_COST, 10.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 58.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 25.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 38.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 16.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 90.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 32.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 230.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.62),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 25.05),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.41),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 30.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 7.0)


            //checked


    );
    public static final Map<HorizonsModifier, Object> INDEPENDANT_TRADER = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 200.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 300.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 2.0),
            Map.entry(HorizonsModifier.SHIELDS, 135.0),
            Map.entry(HorizonsModifier.ARMOUR, 270.0),
            Map.entry(HorizonsModifier.MASS, 180.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 45.0),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 4.0),
            Map.entry(HorizonsModifier.BOOST_COST, 10.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 15.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 15.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 27.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 15.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 100.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 20.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 215.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.87),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 29.78),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.39),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 45.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 8.0)


    );
    public static final Map<HorizonsModifier, Object> ASP_SCOUT = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 220.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 300.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 5.0),
            Map.entry(HorizonsModifier.SHIELDS, 120.0),
            Map.entry(HorizonsModifier.ARMOUR, 180.0),
            Map.entry(HorizonsModifier.MASS, 150.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 50.0),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 4.5),
            Map.entry(HorizonsModifier.BOOST_COST, 13.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 35.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 40.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 15.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 110.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 35.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 210.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.8),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 29.65),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.47),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 52.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 8.0)


    );
    public static final Map<HorizonsModifier, Object> VULTURE = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 210.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 340.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 5.0),
            Map.entry(HorizonsModifier.SHIELDS, 240.0),
            Map.entry(HorizonsModifier.ARMOUR, 160.0),
            Map.entry(HorizonsModifier.MASS, 230.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 90.476),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 4.5),
            Map.entry(HorizonsModifier.BOOST_COST, 16.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 40.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 42.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 17.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 110.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 35.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 237.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.87),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 35.63),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.57),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 55.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 10.0)


    );
    public static final Map<HorizonsModifier, Object> ASP = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 250.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 340.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
            Map.entry(HorizonsModifier.SHIELDS, 140.0),
            Map.entry(HorizonsModifier.ARMOUR, 210.0),
            Map.entry(HorizonsModifier.MASS, 280.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 48.0),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 4.5),
            Map.entry(HorizonsModifier.BOOST_COST, 13.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 24.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 15.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 15.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 38.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 10.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 100.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 272.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.34),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 39.9),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.63),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 52.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 11.0)


    );
    public static final Map<HorizonsModifier, Object> FEDERATION_DROPSHIP = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 180.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 300.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 3.0),
            Map.entry(HorizonsModifier.SHIELDS, 200.0),
            Map.entry(HorizonsModifier.ARMOUR, 300.0),
            Map.entry(HorizonsModifier.MASS, 580.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 55.556),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 6.0),
            Map.entry(HorizonsModifier.BOOST_COST, 19.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 10.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 30.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 14.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 80.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 20.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 331.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.6),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 46.5),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.83),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 60.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 14.0)


    );
    public static final Map<HorizonsModifier, Object> TYPE_7 = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 180.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 300.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 1.0),
            Map.entry(HorizonsModifier.SHIELDS, 156.0),
            Map.entry(HorizonsModifier.ARMOUR, 340.0),
            Map.entry(HorizonsModifier.MASS, 350.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 33.333),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 6.0),
            Map.entry(HorizonsModifier.BOOST_COST, 10.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 15.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 15.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 22.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 22.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 60.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 16.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 226.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.17),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 32.45),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.52),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 54.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 10.0)


    );
    public static final Map<HorizonsModifier, Object> TYPE_X = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 230.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 330.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
            Map.entry(HorizonsModifier.SHIELDS, 200.0),
            Map.entry(HorizonsModifier.ARMOUR, 280.0),
            Map.entry(HorizonsModifier.MASS, 400.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 65.217),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 6.0),
            Map.entry(HorizonsModifier.BOOST_COST, 19.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 38.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 26.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 38.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 16.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 90.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 32.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 289.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.6),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 46.5),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.77),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 65.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 13.0)


    );
    public static final Map<HorizonsModifier, Object> FEDERATION_DROPSHIP_MK_II = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 210.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 350.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
            Map.entry(HorizonsModifier.SHIELDS, 200.0),
            Map.entry(HorizonsModifier.ARMOUR, 300.0),
            Map.entry(HorizonsModifier.MASS, 480.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 71.429),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 6.0),
            Map.entry(HorizonsModifier.BOOST_COST, 19.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 40.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 15.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 38.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 19.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 90.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 286.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.53),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 45.23),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.72),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 60.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 14.0)


    );
    public static final Map<HorizonsModifier, Object> EMPIRE_TRADER = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 300.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 380.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 5.0),
            Map.entry(HorizonsModifier.SHIELDS, 180.0),
            Map.entry(HorizonsModifier.ARMOUR, 270.0),
            Map.entry(HorizonsModifier.MASS, 400.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 60.0),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 4.5),
            Map.entry(HorizonsModifier.BOOST_COST, 19.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 25.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 10.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 40.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 18.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 80.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 304.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.63),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 46.8),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.74),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 60.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 12.0)


            //checked


    );
    public static final Map<HorizonsModifier, Object> TYPE_X_2 = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 180.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 300.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 3.0),
            Map.entry(HorizonsModifier.SHIELDS, 200.0),
            Map.entry(HorizonsModifier.ARMOUR, 300.0),
            Map.entry(HorizonsModifier.MASS, 500.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 61.11),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 6.0),
            Map.entry(HorizonsModifier.BOOST_COST, 19.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 25.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 19.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 32.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 16.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 80.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 316.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.53),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 45.23),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.77),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 65.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 13.0)


    );
    public static final Map<HorizonsModifier, Object> TYPE_X_3 = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 200.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 310.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
            Map.entry(HorizonsModifier.SHIELDS, 220.0),
            Map.entry(HorizonsModifier.ARMOUR, 300.0),
            Map.entry(HorizonsModifier.MASS, 450.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 65.0),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 6.0),
            Map.entry(HorizonsModifier.BOOST_COST, 19.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 32.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 26.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 35.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 16.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 90.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 32.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 316.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.87),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 51.4),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.77),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 65.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 13.0)


    );
    public static final Map<HorizonsModifier, Object> FEDERATION_GUNSHIP = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 170.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 280.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 1.0),
            Map.entry(HorizonsModifier.SHIELDS, 250.0),
            Map.entry(HorizonsModifier.ARMOUR, 350.0),
            Map.entry(HorizonsModifier.MASS, 580.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 58.824),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 6.0),
            Map.entry(HorizonsModifier.BOOST_COST, 23.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 25.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 18.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 10.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 25.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 18.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 80.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 20.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 325.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.87),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 51.4),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.82),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 60.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 14.0)


    );
    public static final Map<HorizonsModifier, Object> KRAIT_LIGHT = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 250.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 350.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 3.0),
            Map.entry(HorizonsModifier.SHIELDS, 200.0),
            Map.entry(HorizonsModifier.ARMOUR, 180.0),
            Map.entry(HorizonsModifier.MASS, 270.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 64.0),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 4.5),
            Map.entry(HorizonsModifier.BOOST_COST, 13.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 28.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 18.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 15.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 31.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 10.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 90.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 26.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 300.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.68),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 52.05),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.63),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 60.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 14.0)


    );
    public static final Map<HorizonsModifier, Object> KRAIT_MK_II = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 240.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 330.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 3.0),
            Map.entry(HorizonsModifier.SHIELDS, 220.0),
            Map.entry(HorizonsModifier.ARMOUR, 220.0),
            Map.entry(HorizonsModifier.MASS, 320.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 62.5),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 4.5),
            Map.entry(HorizonsModifier.BOOST_COST, 13.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 28.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 18.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 15.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 31.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 10.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 90.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 26.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 300.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.68),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 52.05),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.63),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 55.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 16.0)


    );
    public static final Map<HorizonsModifier, Object> ORCA = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 300.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 380.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 1.0),
            Map.entry(HorizonsModifier.SHIELDS, 220.0),
            Map.entry(HorizonsModifier.ARMOUR, 220.0),
            Map.entry(HorizonsModifier.MASS, 290.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 66.667),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 4.0),
            Map.entry(HorizonsModifier.BOOST_COST, 16.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 25.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 25.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 18.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 55.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 20.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 262.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.3),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 42.68),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.79),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 55.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 15.0)


    );
    public static final Map<HorizonsModifier, Object> FER_DE_LANCE = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 260.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 350.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),
            Map.entry(HorizonsModifier.SHIELDS, 300.0),
            Map.entry(HorizonsModifier.ARMOUR, 225.0),
            Map.entry(HorizonsModifier.MASS, 250.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 84.615),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 5.0),
            Map.entry(HorizonsModifier.BOOST_COST, 19.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 29.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 24.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 38.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 12.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 90.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 224.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.05),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 41.63),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.67),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 70.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 12.0)


            //checked


    );
    public static final Map<HorizonsModifier, Object> MAMBA = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 310.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 380.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 3.0),
            Map.entry(HorizonsModifier.SHIELDS, 270.0),
            Map.entry(HorizonsModifier.ARMOUR, 230.0),
            Map.entry(HorizonsModifier.MASS, 250.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 77.42),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 5.0),
            Map.entry(HorizonsModifier.BOOST_COST, 16.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 35.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 15.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 30.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 10.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 75.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 27.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 165.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.05),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 41.63),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.5),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 70.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 12.0)


    );
    public static final Map<HorizonsModifier, Object> PYTHON = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 230.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 300.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 2.0),
            Map.entry(HorizonsModifier.SHIELDS, 260.0),
            Map.entry(HorizonsModifier.ARMOUR, 260.0),
            Map.entry(HorizonsModifier.MASS, 350.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 60.87),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 4.5),
            Map.entry(HorizonsModifier.BOOST_COST, 23.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 18.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 16.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 29.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 10.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 90.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 24.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 300.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.68),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 52.05),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.83),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 65.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 17.0)


    );
    public static final Map<HorizonsModifier, Object> TYPE_9 = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 130.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 200.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 0.0),
            Map.entry(HorizonsModifier.SHIELDS, 240.0),
            Map.entry(HorizonsModifier.ARMOUR, 480.0),
            Map.entry(HorizonsModifier.MASS, 850.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 30.769),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 6.0),
            Map.entry(HorizonsModifier.BOOST_COST, 19.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 10.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 10.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 20.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 8.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 20.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 15.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 289.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 3.1),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 48.35),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.77),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 65.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 16.0)


    );
    public static final Map<HorizonsModifier, Object> BELUGA_LINER = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 200.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 280.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 2.0),
            Map.entry(HorizonsModifier.SHIELDS, 280.0),
            Map.entry(HorizonsModifier.ARMOUR, 280.0),
            Map.entry(HorizonsModifier.MASS, 950.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 55.0),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 6.0),
            Map.entry(HorizonsModifier.BOOST_COST, 19.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 17.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 15.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 25.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 17.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 60.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 20.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 283.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.6),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 50.85),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.81),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 60.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 18.0)


    );
    public static final Map<HorizonsModifier, Object> TYPE_9_MILITARY = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 180.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 220.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 0.0),
            Map.entry(HorizonsModifier.SHIELDS, 320.0),
            Map.entry(HorizonsModifier.ARMOUR, 580.0),
            Map.entry(HorizonsModifier.MASS, 1200.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 83.333),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 6.0),
            Map.entry(HorizonsModifier.BOOST_COST, 19.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 18.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 10.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 10.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 22.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 8.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 40.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 18.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 335.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 3.16),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 67.15),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.77),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 75.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 26.0)


    );
    public static final Map<HorizonsModifier, Object> ANACONDA = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 180.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 240.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 1.0),
            Map.entry(HorizonsModifier.SHIELDS, 350.0),
            Map.entry(HorizonsModifier.ARMOUR, 525.0),
            Map.entry(HorizonsModifier.MASS, 400.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 44.444),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 6.0),
            Map.entry(HorizonsModifier.BOOST_COST, 27.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 10.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 10.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 25.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 10.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 60.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 20.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 334.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 3.16),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 67.15),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 1.07),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 65.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 23.0)


    );
    public static final Map<HorizonsModifier, Object> FEDERATION_CORVETTE = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 200.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 260.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 2.0),
            Map.entry(HorizonsModifier.SHIELDS, 555.0),
            Map.entry(HorizonsModifier.ARMOUR, 370.0),
            Map.entry(HorizonsModifier.MASS, 900.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 50.0),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 6.0),
            Map.entry(HorizonsModifier.BOOST_COST, 27.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 10.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 10.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 28.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 8.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 75.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 22.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 333.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 3.28),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 70.33),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 1.13),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 70.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 24.0)


            //checked


    );
    public static final Map<HorizonsModifier, Object> CUTTER = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 200.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 320.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 0.0),
            Map.entry(HorizonsModifier.SHIELDS, 600.0),
            Map.entry(HorizonsModifier.ARMOUR, 400.0),
            Map.entry(HorizonsModifier.MASS, 1100.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 80.0),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 6.0),
            Map.entry(HorizonsModifier.BOOST_COST, 23.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 29.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 10.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 6.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN},
                    {Double.NaN, Double.NaN}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 18.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 8.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 45.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 14.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 327.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 3.27),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 72.58),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 1.16),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 70.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 26.0)


            //checked


    );
    public static final Map<HorizonsModifier, Object> PYTHON_NX = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 256.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 345.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 5.0),
            Map.entry(HorizonsModifier.SHIELDS, 335.0),
            Map.entry(HorizonsModifier.ARMOUR, 280.0),
            Map.entry(HorizonsModifier.MASS, 450.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 85.85),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 5.0),
            Map.entry(HorizonsModifier.BOOST_COST, 20.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 26.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {50.0, 22.0, 60.0},
                    {80.0, 90.0, 80.0},
                    {50.0, 10.0, 60.0}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {40.0, 40.0},
                    {29.0, 37.0},
                    {30.0, 40.0}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {80.0, 80.0},
                    {91.0, 91.0},
                    {60.0, 60.0}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {40.0, 40.0},
                    {12.5, 12.5},
                    {30.0, 40.0}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 37.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 12.5),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 93.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 30.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 260.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 2.68),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 52.05),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.83),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 70.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 17.0)


    );
    public static final Map<HorizonsModifier, Object> TYPE_8 = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 200.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 340.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 1.0),
            Map.entry(HorizonsModifier.SHIELDS, 228.0),
            Map.entry(HorizonsModifier.ARMOUR, 440.0),
            Map.entry(HorizonsModifier.MASS, 400.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 45.0),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 6.0),
            Map.entry(HorizonsModifier.BOOST_COST, 9.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 25.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 18.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {50.0, 18.0, 60.0},
                    {80.0, 60.0, 80.0},
                    {50.0, 18.0, 60.0}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {50.0, 50.0},
                    {18.0, 28.0},
                    {40.0, 60.0}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {80.0, 80.0},
                    {60.0, 60.0},
                    {80.0, 90.0}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {50.0, 50.0},
                    {16.0, 18.0},
                    {40.0, 60.0}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 28.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 18.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 60.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 18.0),//ingame testing
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 235.75),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 3.1),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 36.25),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.52),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 58.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 18.0)
            // 2 116 55   // 1 -18 -21


    );
    public static final Map<HorizonsModifier, Object> MANDALAY = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 280.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 350.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 5.0),
            Map.entry(HorizonsModifier.SHIELDS, 220.0),
            Map.entry(HorizonsModifier.ARMOUR, 230.0),
            Map.entry(HorizonsModifier.MASS, 230.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 71.5),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 5.0),
            Map.entry(HorizonsModifier.BOOST_COST, 14.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 35.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {50.0, 40.0, 60.0},
                    {80.0, 120.0, 80.0},
                    {50.0, 25.0, 60.0}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {40.0, 40.0},
                    {27.0, 35.0},
                    {30.0, 40.0}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {80.0, 80.0},
                    {90.0, 90.0},
                    {80.0, 80.0}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {40.0, 40.0},
                    {28.0, 28.0},
                    {30.0, 40.0}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 35.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 28.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 96.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 27.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 250.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, Double.NaN),//TODO - unknown
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 51.0),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.5),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 55.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 11.0)


    );
    public static final Map<HorizonsModifier, Object> COBRA_MK_V = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 290.0),
            Map.entry(HorizonsModifier.BOOST_SPEED, 410.0),
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 5.0),
            Map.entry(HorizonsModifier.SHIELDS, 160.0),
            Map.entry(HorizonsModifier.ARMOUR, 180.0),
            Map.entry(HorizonsModifier.MASS, 150.0),
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 79.0),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 5.0),
            Map.entry(HorizonsModifier.BOOST_COST, 10.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {50.0, 37.0, 60.0},
                    {80.0, 110.0, 80.0},
                    {50.0, 22.0, 60.0}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {40.0, 40.0},
                    {41.0, 45.0},
                    {30.0, 30.0}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {80.0, 80.0},
                    {111.0, 120.0},
                    {80.0, 80.0}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {40.0, 40.0},
                    {28.5, 33.0},
                    {30.0, 30.0}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 45.0),
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 33.0),
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 120.0),
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 40.0),
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 245.0),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, Double.NaN),
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 39.5),
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.49),
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 40.0),
            Map.entry(HorizonsModifier.MASS_LOCK, 8.0)


    );
    public static final Map<HorizonsModifier, Object> CORSAIR = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 280.0),//unittest
            Map.entry(HorizonsModifier.BOOST_SPEED, 356.0),//unittest
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 4.0),//from the buy screen
            Map.entry(HorizonsModifier.SHIELDS, 235.0),//unittest
            Map.entry(HorizonsModifier.ARMOUR, 270.0),//unittest
            Map.entry(HorizonsModifier.MASS, 265.0),//from the buy screen
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 64.0),//ingame testing
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 5.0),//ingame testing
            Map.entry(HorizonsModifier.BOOST_COST, 19.0),//ingame testing
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 20.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {50.0, 21.0, 60.0},
                    {80.0, 80.0, 80.0},
                    {50.0, 10.0, 60.0}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {60.0, 40.0},
                    {23.0, 26.0},
                    {70.0, 40.0}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {40.0, 40.0},
                    {80.0, 80.0},
                    {40.0, 40.0}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {60.0, 40.0},
                    {9.0, 10.0},
                    {70.0, 40.0}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 26.0),//unittest
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 10.0),//unittest
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 80.0),//unittest
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 23.0),//ingame testing
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 230.0),//ingame testing
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, 1.62),//ingame testing
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 52.05),//ingame testing
            Map.entry(HorizonsModifier.FUEL_RESERVE, 0.41),//from journal
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 65.0),//side panel (Armour Rating)
            Map.entry(HorizonsModifier.MASS_LOCK, 7.0)//ingame testing


    );
    public static final Map<HorizonsModifier, Object> PANTHER_CLIPPER_MK_II = Map.ofEntries(
            Map.entry(HorizonsModifier.TOP_SPEED, 180.0),//unittest
            Map.entry(HorizonsModifier.BOOST_SPEED, 249.0),//unittest
            Map.entry(HorizonsModifier.MANOEUVRABILITY, 0.0),//from the buy screen
            Map.entry(HorizonsModifier.SHIELDS, 350.0),//unittest
            Map.entry(HorizonsModifier.ARMOUR, 620.0),//unittest
            Map.entry(HorizonsModifier.MASS, 1200.0),//from the buy screen
            Map.entry(HorizonsModifier.MINIMUM_THRUST, 34.0),
            Map.entry(HorizonsModifier.BOOST_INTERVAL, 6.5),
            Map.entry(HorizonsModifier.BOOST_COST, 19.0),
            Map.entry(HorizonsModifier.FORWARD_ACCELERATION, 30.0),
            Map.entry(HorizonsModifier.REVERSE_ACCELERATION, 10.0),
            Map.entry(HorizonsModifier.LATERAL_ACCELERATION, 10.0),
            Map.entry(HorizonsModifier.SUPERCRUISE_PROFILE, new Double[][]{
                    {50.0, 16.0, 60.0},
                    {80.0, 25.0, 80.0},
                    {50.0, 10.0, 60.0}}),
            Map.entry(HorizonsModifier.CRUISE_PITCH_PROFILE, new Double[][]{
                    {80.0, 80.0},
                    {14.0, 18.0},
                    {80.0, 60.0}}),
            Map.entry(HorizonsModifier.CRUISE_ROLL_PROFILE, new Double[][]{
                    {80.0, 80.0},
                    {20.0, 20.0},
                    {80.0, 90.0}}),
            Map.entry(HorizonsModifier.CRUISE_YAW_PROFILE, new Double[][]{
                    {80.0, 80.0},
                    {8.0, 10.0},
                    {80.0, 60.0}}),
            Map.entry(HorizonsModifier.MAX_PITCH_SPEED, 18.0),//unittest
            Map.entry(HorizonsModifier.MAX_YAW_SPEED, 10.0),//unittest
            Map.entry(HorizonsModifier.MAX_ROLL_SPEED, 20.0),//unittest
            Map.entry(HorizonsModifier.MIN_PITCH_SPEED, 14.0),//ingame testing
            Map.entry(HorizonsModifier.HEAT_CAPACITY, 329.12),//ingame testing
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN, Double.NaN),//ingame testing
            Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX, 62.23),//ingame testing
            Map.entry(HorizonsModifier.FUEL_RESERVE, 1.11),//from journal
            Map.entry(HorizonsModifier.ARMOUR_HARDNESS, 70.0),//side panel (Armour Rating)
            Map.entry(HorizonsModifier.MASS_LOCK, 25.0)//ingame testing


    );
}
