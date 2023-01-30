package nl.jixxed.eliteodysseymaterials.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class MaterialTrackingServiceTest {


    @Test
    void modifiedBeforeMonday() {
        final File file = Mockito.mock(File.class);
        Mockito.when(file.lastModified()).thenReturn(ZonedDateTime.now().minusWeeks(1).toInstant().toEpochMilli());
        final boolean modifiedBeforeMonday = MaterialTrackingService.modifiedBeforeMonday(file);
        assertThat(modifiedBeforeMonday).isTrue();
    }

    @Test
    void modifiedAfterMonday() {
        final File file = Mockito.mock(File.class);
        Mockito.when(file.lastModified()).thenReturn(ZonedDateTime.now().toInstant().toEpochMilli());
        final boolean modifiedBeforeMonday = MaterialTrackingService.modifiedBeforeMonday(file);
        assertThat(modifiedBeforeMonday).isFalse();
    }
}