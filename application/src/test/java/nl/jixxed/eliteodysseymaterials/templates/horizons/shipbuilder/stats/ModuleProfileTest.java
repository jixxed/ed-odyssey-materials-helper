package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import org.junit.jupiter.api.Assertions;
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


    @Test
    void getMkIIMassCurveMultiplier() {
        var minimumMass = 100D;
        var maximumMass = 800D;
        var optimalMass = 100D;
        var minimumMultiplier = 0.5;
        var maximumMultiplier = 1.2;
        var optimalMultiplier = 1.0;

        var moduleProfile = new ModuleProfile(minimumMass, optimalMass, maximumMass, minimumMultiplier, optimalMultiplier, maximumMultiplier);

        // Test at minimum mass
        assertEquals(maximumMultiplier, moduleProfile.getMassCurveMultiplier(minimumMass));

        // Test at optimal mass
        assertEquals(maximumMultiplier, moduleProfile.getMassCurveMultiplier(optimalMass));

        // Test at maximum mass
        assertEquals(minimumMultiplier, moduleProfile.getMassCurveMultiplier(maximumMass));

        // Test at a mass between optimal and maximum
        Assertions.assertAll(
                () -> assertEquals(0.5, moduleProfile.getMassCurveMultiplier(150), 0.0005),
                () -> assertEquals(0.5, moduleProfile.getMassCurveMultiplier(200), 0.0005),
                () -> assertEquals(0.5, moduleProfile.getMassCurveMultiplier(250), 0.0005),
                () -> assertEquals(0.5, moduleProfile.getMassCurveMultiplier(300), 0.0005),
                () -> assertEquals(0.5, moduleProfile.getMassCurveMultiplier(350), 0.0005),
                () -> assertEquals(0.5, moduleProfile.getMassCurveMultiplier(400), 0.0005),
                () -> assertEquals(0.5, moduleProfile.getMassCurveMultiplier(450), 0.0005),
                () -> assertEquals(0.5, moduleProfile.getMassCurveMultiplier(500), 0.0005),
                () -> assertEquals(0.5, moduleProfile.getMassCurveMultiplier(550), 0.0005),
                () -> assertEquals(0.5, moduleProfile.getMassCurveMultiplier(600), 0.0005),
                () -> assertEquals(0.5, moduleProfile.getMassCurveMultiplier(650), 0.0005),
                () -> assertEquals(0.5, moduleProfile.getMassCurveMultiplier(700), 0.0005),
                () -> assertEquals(0.5, moduleProfile.getMassCurveMultiplier(750), 0.0005)
        );
        // Test at a mass between optimal and minimum

        // Test below minimum mass
        assertEquals(maximumMultiplier, moduleProfile.getMassCurveMultiplier(minimumMass - 50));

        // Test over maximum mass
        assertEquals(minimumMultiplier, moduleProfile.getMassCurveMultiplier(maximumMass + 500));
    }
}