package nl.jixxed.eliteodysseymaterials.watchdog;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.stream.Stream;

class JournalWatcherTest {
    private final JournalWatcher journalWatcher = new JournalWatcher();
    private static final File fileNewDate = new File("Journal.2022-03-15T151250.01.log");
    private static final File fileNewTimestamp = new File("Journal.220314230050.02.log");
    private static final File fileOldDate = new File("Journal.2019-03-15T151250.03.log");
    private static final File fileOldTimestamp = new File("Journal.190314230050.04.log");


    private static Stream<Arguments> provideFilesForGetFileTimestamp() {
        return Stream.of(
                Arguments.of(fileNewDate, 22031515125001L),
                Arguments.of(fileNewTimestamp, 22031423005002L),
                Arguments.of(fileOldDate, 19031515125003L),
                Arguments.of(fileOldTimestamp, 19031423005004L)
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
        final boolean newerThan2020 = this.journalWatcher.isNewerThan2020(file);
        Assertions.assertEquals(expected, newerThan2020);
    }
}