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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
