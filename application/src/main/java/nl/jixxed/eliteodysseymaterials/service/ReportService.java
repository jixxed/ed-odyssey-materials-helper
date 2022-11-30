package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.helper.DnsHelper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Slf4j
public class ReportService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static void reportMaterial(final JsonNode journalMessage) {
        final Runnable run = () -> {
            try {
                final String data = OBJECT_MAPPER.writeValueAsString(journalMessage);
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
