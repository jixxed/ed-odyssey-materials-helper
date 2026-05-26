/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.capi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.helper.DnsHelper;
import nl.jixxed.eliteodysseymaterials.service.CAPIService;
import nl.jixxed.eliteodysseymaterials.service.HttpClientService;
import nl.jixxed.eliteodysseymaterials.service.Secrets;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.GameStatusEvent;
import nl.jixxed.eliteodysseymaterials.service.event.TerminateApplicationEvent;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class CapiServerHealthChecker {
    private static final long INITIAL_DELAY_MS = 2L * 60 * 1000; // 2 minutes
    private static final long INTERVAL_MS = 2L * 60 * 1000; // 2 minutes

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    private final Timer timer = new Timer("capi-health-checker", true);
    private TimerTask timerTask;
    private volatile boolean running = false;

    private static volatile CapiServerHealthChecker instance;

    private CapiServerHealthChecker() {
        EVENT_LISTENERS.add(EventService.addStaticListener(TerminateApplicationEvent.class, event -> {
            stop();
        }));
    }

    public static CapiServerHealthChecker getInstance() {
        if (instance == null) {
            synchronized (CapiServerHealthChecker.class) {
                if (instance == null) {
                    instance = new CapiServerHealthChecker();
                }
            }
        }
        return instance;
    }

    public synchronized void startIfNotRunning() {
        if (running) {
            return;
        }
        running = true;
        log.info("CAPI server health checker started");
        timerTask = new TimerTask() {
            @Override
            public void run() {
                checkServerStatus();
            }
        };
        timer.schedule(timerTask, INITIAL_DELAY_MS, INTERVAL_MS);
        EventService.publish(new GameStatusEvent(false));
    }

    private void checkServerStatus() {
        try {
            final String domainName = DnsHelper.resolveCname(Secrets.getOrDefault("ed.status.host", "localhost"));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(java.net.URI.create("https://" + domainName + "/status"))
                    .GET()
                    .build();
            var response = HttpClientService.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JsonNode jsonNode = OBJECT_MAPPER.readTree(response.body());
                String status = jsonNode.get("status").asText();
                if ("up".equals(status)) {
                    log.info("CAPI server is back up. Unpausing all handlers.");
                    stop();
                    CAPIService.getInstance().unpauseAll();
                    EventService.publish(new GameStatusEvent(true));

                }
            }
        } catch (Exception e) {
            log.debug("Health check request failed (will retry): {}", e.getMessage());
        }
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        log.info("CAPI server health checker stopped");
        if (timerTask != null) {
            timerTask.cancel();
        }
        timer.cancel();
    }

    public boolean isRunning() {
        return running;
    }
}
