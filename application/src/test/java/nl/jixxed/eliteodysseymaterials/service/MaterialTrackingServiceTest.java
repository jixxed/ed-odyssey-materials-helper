package nl.jixxed.eliteodysseymaterials.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.time.ZonedDateTime;

class MaterialTrackingServiceTest {


    @Test
    void modifiedBeforeMonday() {
        final File file = Mockito.mock(File.class);
        Mockito.when(file.lastModified()).thenReturn(ZonedDateTime.now().minusWeeks(1).toInstant().toEpochMilli());
        final boolean modifiedBeforeMonday = MaterialTrackingService.modifiedBeforeMonday(file);
        Assertions.assertThat(modifiedBeforeMonday).isTrue();
    }

    @Test
    void modifiedAfterMonday() {
        final File file = Mockito.mock(File.class);
        Mockito.when(file.lastModified()).thenReturn(ZonedDateTime.now().toInstant().toEpochMilli());
        final boolean modifiedBeforeMonday = MaterialTrackingService.modifiedBeforeMonday(file);
        Assertions.assertThat(modifiedBeforeMonday).isFalse();
    }
}