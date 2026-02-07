package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

public record ModuleProfile(Double minimumMass, Double optimalMass, Double maximumMass, Double minimumMultiplier,
                            Double optimalMultiplier, Double maximumMultiplier) {

    public double getMassCurveMultiplier(final double mass) {
        return (
                minimumMultiplier + Math.pow(
                        Math.clamp((maximumMass - mass) / (maximumMass - minimumMass), 0.0, 1.0),
                        Math.log(
                                (optimalMultiplier - minimumMultiplier) / (maximumMultiplier - minimumMultiplier)
                        ) / Math.log(
                                (maximumMass - optimalMass) / (maximumMass - minimumMass)
                        )
                ) * (maximumMultiplier - minimumMultiplier)
        );
    }
}
