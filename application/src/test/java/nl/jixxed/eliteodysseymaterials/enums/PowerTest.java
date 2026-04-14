/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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