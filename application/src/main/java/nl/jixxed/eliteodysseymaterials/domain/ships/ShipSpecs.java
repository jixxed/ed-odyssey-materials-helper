/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.domain.ships;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipSpecs {
    @JsonProperty("min_speed")
    private Double minSpeed;
    @JsonProperty("top_speed")
    private Double topSpeed;
    @JsonProperty("boost_speed")
    private Double boostSpeed;
    @JsonProperty("manoeuvrability")
    private Double manoeuvrability;
    @JsonProperty("shields")
    private Double shields;
    @JsonProperty("armour")
    private Double armour;
    @JsonProperty("mass")
    private Double mass;
    @JsonProperty("boost_interval")
    private Double boostInterval;
    @JsonProperty("boost_cost")
    private Double boostCost;
    @JsonProperty("forward_acceleration")
    private Double forwardAcceleration;
    @JsonProperty("reverse_acceleration")
    private Double reverseAcceleration;
    @JsonProperty("lateral_acceleration")
    private Double lateralAcceleration;
    @JsonProperty("supercruise_profile")
    private Double[][] supercruiseProfile;
    @JsonProperty("cruise_pitch_profile")
    private Double[][] cruisePitchProfile;
    @JsonProperty("cruise_roll_profile")
    private Double[][] cruiseRollProfile;
    @JsonProperty("cruise_yaw_profile")
    private Double[][] cruiseYawProfile;
    @JsonProperty("max_pitch_speed")
    private Double maxPitchSpeed;
    @JsonProperty("max_yaw_speed")
    private Double maxYawSpeed;
    @JsonProperty("max_roll_speed")
    private Double maxRollSpeed;
    @JsonProperty("min_pitch_speed")
    private Double minPitchSpeed;
    @JsonProperty("heat_capacity")
    private Double heatCapacity;
    @JsonProperty("heat_dissipation_min")
    private Double heatDissipationMin;
    @JsonProperty("heat_dissipation_max")
    private Double heatDissipationMax;
    @JsonProperty("fuel_reserve")
    private Double fuelReserve;
    @JsonProperty("armour_hardness")
    private Double armourHardness;
    @JsonProperty("mass_lock")
    private Double massLock;
    @JsonProperty("sensor_lock_min")
    private Double sensorLockMin;

}
