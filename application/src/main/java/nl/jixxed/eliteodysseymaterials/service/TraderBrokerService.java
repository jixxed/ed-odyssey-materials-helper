package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.BrokerTraderJournalEvent;
import nl.jixxed.eliteodysseymaterials.helper.DnsHelper;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.TerminateApplicationEvent;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TraderBrokerService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();
    private static final ThreadPoolExecutor EXECUTOR_SERVICE = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));

        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        OBJECT_MAPPER.registerModule(module);

    }

    public static void init() {
        EVENT_LISTENERS.add(EventService.addStaticListener(TerminateApplicationEvent.class, terminateApplicationEvent -> {
            EXECUTOR_SERVICE.shutdown();
        }));
    }

    public static void sendBrokerTraderEvent(BrokerTraderJournalEvent brokerTraderJournalEvent) {
        final Runnable run = () -> {
            try {
                final String data = OBJECT_MAPPER.writeValueAsString(brokerTraderJournalEvent);
                log.info(data);
                final HttpClient httpClient = HttpClient.newHttpClient();
                final String domainName = DnsHelper.resolveCname(Secrets.getOrDefault("api.services.host", "localhost"));
                final HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://" + domainName + "/Prod/v2/submit-broker-trader"))
                        .POST(HttpRequest.BodyPublishers.ofString(data))
                        .build();
                final HttpResponse<String> send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                log.info(send.body());
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (final Exception e) {
                log.error("publish materialtrade/techbroker error", e);
            }
        };
        EXECUTOR_SERVICE.submit(run);

    }
} 