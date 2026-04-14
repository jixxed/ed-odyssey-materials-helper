/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.watchdog;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.time.Year;
import java.util.stream.Stream;

class JournalWatcherTest {

    private static final String TWO_YEARS_AGO = String.format("%ty", Year.now().minusYears(2));
    private static final String FIVE_YEARS_AGO = String.format("%ty", Year.now().minusYears(5));
    private final JournalWatcher journalWatcher = new JournalWatcher();
    private static final File fileNewDate = new File("Journal.20" + TWO_YEARS_AGO + "-03-15T151250.01.log");
    private static final File fileNewTimestamp = new File("Journal." + TWO_YEARS_AGO + "0314230050.02.log");
    private static final File fileOldDate = new File("Journal.20" + FIVE_YEARS_AGO + "-03-15T151250.03.log");
    private static final File fileOldTimestamp = new File("Journal." + FIVE_YEARS_AGO + "0314230050.04.log");


    private static Stream<Arguments> provideFilesForGetFileTimestamp() {
        return Stream.of(
                Arguments.of(fileNewDate, Long.valueOf(TWO_YEARS_AGO + "031515125001")),
                Arguments.of(fileNewTimestamp, Long.valueOf(TWO_YEARS_AGO + "031423005002")),
                Arguments.of(fileOldDate, Long.valueOf(FIVE_YEARS_AGO + "031515125003")),
                Arguments.of(fileOldTimestamp, Long.valueOf(FIVE_YEARS_AGO + "031423005004"))
        );
    }

    private static Stream<Arguments> provideFilesForIsNewerThan2020() {
        return Stream.of(
                Arguments.of(fileNewDate, true),
                Arguments.of(fileNewTimestamp, true),
                Arguments.of(fileOldDate, false),
                Arguments.of(fileOldTimestamp, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideFilesForGetFileTimestamp")
    void getFileTimestamp(final File file, final long expected) {
        final Long fileTimestamp = this.journalWatcher.getFileTimestamp(file);
        Assertions.assertEquals(expected, fileTimestamp);

    }

    @ParameterizedTest
    @MethodSource("provideFilesForIsNewerThan2020")
    void isNewerThan2020(final File file, final boolean expected) {
        final boolean newerThan2020 = this.journalWatcher.isNewerThanTwoYears(file);
        Assertions.assertEquals(expected, newerThan2020);
    }
}