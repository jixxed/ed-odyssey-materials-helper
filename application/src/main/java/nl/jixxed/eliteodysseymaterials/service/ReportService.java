package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.helper.DnsHelper;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Fileheader.Fileheader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public class ReportService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));
    }

    public static void reportMaterial(final Object material) {
        final String buildVersion = VersionService.getBuildVersion();
        if (buildVersion != null) {
            final Runnable run = () -> {
                try {
                    final String data = OBJECT_MAPPER.writeValueAsString(new Report(buildVersion, ApplicationState.getInstance().getFileheader(), material));
                    log.info(data);
                    final HttpClient httpClient = HttpClient.newHttpClient();
                    final String domainName = DnsHelper.resolveCname("edmattracking.jixxed.nl");
                    final HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("https://" + domainName + "/Prod/submit-new"))
                            .POST(HttpRequest.BodyPublishers.ofString(data))
                            .build();
                    final HttpResponse<String> send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    log.info(send.body());
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (final Exception e) {
                    log.error("publish material tracking error", e);
                }
            };
            new Thread(run).start();
        }
    }

    public static void reportJournal(String journalLine, String error) {
        final String buildVersion = VersionService.getBuildVersion();
        if (buildVersion != null) {
            final Runnable run = () -> {
                try {
                    final String data = OBJECT_MAPPER.writeValueAsString(new ReportUnknownJournal(buildVersion, ApplicationState.getInstance().getFileheader(), journalLine, error));
                    log.info(data);
                    final HttpClient httpClient = HttpClient.newHttpClient();
                    final String domainName = DnsHelper.resolveCname("edmattracking.jixxed.nl");
                    final HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("https://" + domainName + "/Prod/submit-unknown-journal"))
                            .POST(HttpRequest.BodyPublishers.ofString(data))
                            .build();
                    final HttpResponse<String> send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    log.info(send.body());
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (final Exception e) {
                    log.error("publish unknown journal error", e);
                }
            };
            new Thread(run).start();
        }
    }

    @Data
    @AllArgsConstructor
    public static class Report {
        String version;
        Fileheader fileheader;
        Object data;
    }
    @Data
    @AllArgsConstructor
    public static class ReportUnknownJournal {
        String version;
        Fileheader fileheader;
        String message;
        String error;
    }
}
