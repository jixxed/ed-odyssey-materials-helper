package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class BillOfMaterialsEntryTest {

    @ParameterizedTest
    @CsvSource({
            "0, 0, 0",
            "0, 100, 0",
            "1, 100, 1",
            "99, 100, 1",
            "100, 100, 1",
            "101, 100, 2",
            "200, 100, 2",
            "200, 0, Infinity"
    })
    void calculateTrips(int quantity, int cargoCapacity, Double expectedTrips) {
        Double actualTrips = BillOfMaterialsEntry.calculateTrips(quantity, cargoCapacity);
        assertEquals(expectedTrips, actualTrips);
    }
}