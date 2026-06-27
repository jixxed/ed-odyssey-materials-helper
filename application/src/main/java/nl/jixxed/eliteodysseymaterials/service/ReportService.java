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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Fileheader.Fileheader;

@Slf4j
public class ReportService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));
    }
    public static void reportMaterial(final Object material) {
        try {
            reportMaterial(OBJECT_MAPPER.writeValueAsString(material));
        } catch (JsonProcessingException e) {
            //noop
        }
    }

    public static void reportMaterial(final String material) {
        final String buildVersion = VersionService.getBuildVersion();
        EliteMQService.sendMaterialReport(new Report(buildVersion, ApplicationState.getInstance().getFileheader(), material));
    }

    public static void reportJournal(String channel, String journalLine, String error) {
        final String buildVersion = VersionService.getBuildVersion();
        EliteMQService.sendJournalReport(new ReportUnknownJournal(channel, buildVersion, ApplicationState.getInstance().getFileheader(), journalLine, error));
    }

    @Data
    @AllArgsConstructor
    public static class Report {
        String version;
        Fileheader fileheader;
        String data;
    }

    @Data
    @AllArgsConstructor
    public static class ReportUnknownJournal {
        String channel;
        String version;
        Fileheader fileheader;
        String message;
        String error;
    }
}
