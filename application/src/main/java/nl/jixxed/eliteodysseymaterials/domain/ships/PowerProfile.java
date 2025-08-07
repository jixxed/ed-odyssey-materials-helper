package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PowerProfile {
    double powerCapacity = 0D;
    double powerGroupPassive = 0D;
    double powerGroup1 = 0D;
    double powerGroup2 = 0D;
    double powerGroup3 = 0D;
    double powerGroup4 = 0D;
    double powerGroup5 = 0D;

    public void increasePowerGroup(final int group, final double value) {
        switch (group) {
            case -1 -> this.powerGroupPassive += value;
            case 1 -> this.powerGroup1 += value;
            case 2 -> this.powerGroup2 += value;
            case 3 -> this.powerGroup3 += value;
            case 4 -> this.powerGroup4 += value;
            case 5 -> this.powerGroup5 += value;
            default -> throw new IllegalArgumentException("Invalid power group: " + group);
        }
    }
    public double usedPower(){
        return getPowerGroupPassive()
        + getPowerGroup1()
        + getPowerGroup2()
        + getPowerGroup3()
        + getPowerGroup4()
        + getPowerGroup5();
    }
}
