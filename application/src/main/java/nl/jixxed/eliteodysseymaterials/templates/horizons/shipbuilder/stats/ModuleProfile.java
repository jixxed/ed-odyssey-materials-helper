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

import static java.lang.Math.pow;

public record ModuleProfile(Double minimumMass, Double optimalMass, Double maximumMass, Double minimumMultiplier,
                            Double optimalMultiplier, Double maximumMultiplier) {

    public double getMassCurveMultiplier(final double mass) {
        if(maximumMultiplier.equals(minimumMultiplier)){
            return maximumMultiplier;
        }
        double exponent = Math.log(
                (optimalMultiplier - minimumMultiplier) / (maximumMultiplier - minimumMultiplier)
        ) / Math.log(
                (maximumMass - optimalMass) / (maximumMass - minimumMass)
        );
        return Math.clamp((
                minimumMultiplier + pow(
                        Math.clamp((maximumMass - mass) / (maximumMass - minimumMass), 0.0, 1.0),
                        exponent
                ) * (maximumMultiplier - minimumMultiplier)
        ), minimumMultiplier, maximumMultiplier);
    }
}
