/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModuleProfileTest {

    @Test
    void getMassCurveMultiplier() {
        var minimumMass = 100D;
        var maximumMass = 800D;
        var optimalMass = 200D;
        var minimumMultiplier = 0.5;
        var maximumMultiplier = 1.2;
        var optimalMultiplier = 1.0;

        var moduleProfile = new ModuleProfile(minimumMass, optimalMass, maximumMass, minimumMultiplier, optimalMultiplier, maximumMultiplier);

        // Test at minimum mass
        assertEquals(maximumMultiplier, moduleProfile.getMassCurveMultiplier(minimumMass));

        // Test at optimal mass
        assertEquals(optimalMultiplier, moduleProfile.getMassCurveMultiplier(optimalMass));

        // Test at maximum mass
        assertEquals(minimumMultiplier, moduleProfile.getMassCurveMultiplier(maximumMass));

        // Test at a mass between optimal and maximum
        assertEquals(0.706, moduleProfile.getMassCurveMultiplier(400), 0.0005);
        // Test at a mass between optimal and minimum
        assertEquals(1.095, moduleProfile.getMassCurveMultiplier(150), 0.0005);

        // Test below minimum mass
        assertEquals(maximumMultiplier, moduleProfile.getMassCurveMultiplier(minimumMass - 50));

        // Test over maximum mass
        assertEquals(minimumMultiplier, moduleProfile.getMassCurveMultiplier(maximumMass + 500));
    }
}