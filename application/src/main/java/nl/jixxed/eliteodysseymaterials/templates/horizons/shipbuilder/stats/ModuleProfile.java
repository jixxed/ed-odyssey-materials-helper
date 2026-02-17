package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import org.apache.commons.math3.util.Precision;

public record ModuleProfile(Double minimumMass, Double optimalMass, Double maximumMass, Double minimumMultiplier,
                            Double optimalMultiplier, Double maximumMultiplier) {

    public double getMassCurveMultiplier(final double mass) {
        double log = Math.log(
                (maximumMass - optimalMass) / (maximumMass - minimumMass)
        );
        log = log == 0.0 ? -Precision.EPSILON : log;
        return Math.clamp((
                minimumMultiplier + Math.pow(
                        Math.clamp((maximumMass - mass) / (maximumMass - minimumMass), 0.0, 1.0),
                        Math.log(
                                (optimalMultiplier - minimumMultiplier) / (maximumMultiplier - minimumMultiplier)
                        ) / log
                ) * (maximumMultiplier - minimumMultiplier)
        ), minimumMultiplier, maximumMultiplier);
    }
}
