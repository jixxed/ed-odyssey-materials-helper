package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import static java.lang.Math.pow;

public record ModuleProfile(Double minimumMass, Double optimalMass, Double maximumMass, Double minimumMultiplier,
                            Double optimalMultiplier, Double maximumMultiplier) {
    public double getMassCurveMultiplier(final double mass) {
        double exponent = Math.log(
                (optimalMultiplier - minimumMultiplier) / (maximumMultiplier - minimumMultiplier)
        ) / Math.log(
                (maximumMass - (minimumMass.equals(optimalMass) ? maximumMass - minimumMass : optimalMass)) / (maximumMass - minimumMass)
        );
        return Math.clamp((
                minimumMultiplier + pow(
                        Math.clamp((maximumMass - mass) / (maximumMass - minimumMass), 0.0, 1.0),
                        exponent
                ) * (maximumMultiplier - minimumMultiplier)
        ), minimumMultiplier, maximumMultiplier);
    }
}
