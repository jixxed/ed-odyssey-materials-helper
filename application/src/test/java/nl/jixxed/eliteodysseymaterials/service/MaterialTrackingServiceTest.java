/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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