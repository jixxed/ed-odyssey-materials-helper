package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PowerTest {

    @ParameterizedTest
    @CsvSource({
            "1, 0",
            "2, 2000",
            "3, 5000",
            "4, 9000",
            "5, 15000",
            "6, 23000",
            "7, 31000",
            "8, 39000",
            "9, 47000",
            "10, 55000",
            "11, 63000",
            "12, 71000",
            "13, 79000",
            "14, 87000",
            "15, 95000"
    })
    void getNextRankMerits(long rank, int expectedMerits) {
        // Assuming getNextRankMerits is a static method in the Power class
        int actualMerits = Power.getMeritsRequiredForRank(rank);
        assertEquals(expectedMerits, actualMerits);
    }
}