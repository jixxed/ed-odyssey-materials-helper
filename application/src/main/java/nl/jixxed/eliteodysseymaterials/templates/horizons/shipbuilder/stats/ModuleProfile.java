package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import static java.lang.Math.pow;

public record ModuleProfile(Double minimumMass, Double optimalMass, Double maximumMass, Double minimumMultiplier,
                            Double optimalMultiplier, Double maximumMultiplier) {
    public ModuleProfile(Double minimumMass, Double optimalMass, Double maximumMass, Double minimumMultiplier, Double optimalMultiplier, Double maximumMultiplier) {
        this.minimumMass = minimumMass;
        this.optimalMass = minimumMass.equals(optimalMass) ? maximumMass - minimumMass : optimalMass;
        this.maximumMass = maximumMass;
        this.minimumMultiplier = minimumMultiplier;
        this.optimalMultiplier = optimalMultiplier;
        this.maximumMultiplier = maximumMultiplier;
    }

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
