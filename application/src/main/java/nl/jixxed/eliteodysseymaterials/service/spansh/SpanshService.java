/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.spansh;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.FXApplication;
import nl.jixxed.eliteodysseymaterials.helper.DnsHelper;
import nl.jixxed.eliteodysseymaterials.schemas.slef.Slef;
import nl.jixxed.eliteodysseymaterials.schemas.spansh.ShipSaveResponse;
import nl.jixxed.eliteodysseymaterials.service.HttpClientService;
import nl.jixxed.eliteodysseymaterials.service.Secrets;
import nl.jixxed.eliteodysseymaterials.service.VersionService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.TerminateApplicationEvent;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SpanshService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();
    static SimpleBooleanProperty active = new SimpleBooleanProperty(false);

    static {
        //ignore unknown properties to avoid crashes on api changes
        OBJECT_MAPPER.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));

        EVENT_LISTENERS.add(EventService.addStaticListener(TerminateApplicationEvent.class, _ -> {
            EXECUTOR_SERVICE.shutdown();
        }));
    }

    public static void openPlotter(Slef slef) {
        active.set(true);
        final Runnable run = () -> {
            try {
                HttpClient httpClient = HttpClientService.getHttpClient();
                final String data = OBJECT_MAPPER.writeValueAsString(slef);
                log.info(data);
                final String domainName = DnsHelper.resolveCname(Secrets.getOrDefault("plotter.host", "localhost"));
                final HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://" + domainName + "/api/ship/save"))
                        .header("User-Agent", VersionService.getUserAgent())
                        .POST(HttpRequest.BodyPublishers.ofString(data))
                        .build();
                final HttpResponse<String> send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (send.statusCode() == 200) {
                    ShipSaveResponse shipSaveResponse = OBJECT_MAPPER.readValue(send.body(), ShipSaveResponse.class);
                    FXApplication.getInstance().getHostServices().showDocument("https://" + domainName + "/exact-plotter/ship/" + shipSaveResponse.getShip());
                } else {
                    log.error("Failed to save ship: {}", send.body());
                }
            } catch (Exception ex) {
                log.error(ex.getMessage());
            } finally {
                Platform.runLater(() -> active.set(false));
            }
        };
        EXECUTOR_SERVICE.submit(run);
    }

    public static ObservableBooleanValue isWorking() {
        return active;
    }
}
