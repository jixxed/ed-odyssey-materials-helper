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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.helper.DnsHelper;
import nl.jixxed.eliteodysseymaterials.schemas.journal.CommunityGoal.CommunityGoal;
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
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class EliteMQService {
    private static final LocalDateTime MIN_DATETIME = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
    private static final Semaphore SEMAPHORE = new Semaphore(1, true);
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


    public static void sendCommunityGoal(CommunityGoal communityGoal) {
        if (!VersionService.isDev()) {
            try {
                final String data = OBJECT_MAPPER.writeValueAsString(communityGoal);
                final Runnable run = () -> {
                    try (final HttpClient httpClient = HttpClient.newHttpClient()) {
                        final String domainName = DnsHelper.resolveCname(Secrets.getOrDefault("elite.mq.host", "localhost"));
                        final HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("https://" + domainName + "/communitygoal"))
                                .header("User-Agent", VersionService.getUserAgent())
                                .header("X-API-Key", Secrets.getOrDefault("elite.mq.api.key", "none"))
                                .header("Content-Type", "application/json")
                                .POST(HttpRequest.BodyPublishers.ofString(data))
                                .build();
                        SEMAPHORE.acquire();
                        try {
                            final LocalDateTime lastTimestamp = UserPreferencesService.getPreference(PreferenceConstants.MQ_LATEST_CG, MIN_DATETIME);
                            var timestamp = communityGoal.getTimestamp();
                            if (timestamp.isAfter(lastTimestamp)) {
                                UserPreferencesService.setPreference(PreferenceConstants.MQ_LATEST_CG, timestamp);
                                final HttpResponse<String> send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                            }
                        } finally {
                            SEMAPHORE.release(); // Release the permit after the request is completed
                        }
                    } catch (final InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (final Exception e) {
                        log.error("publish cg error", e);
                    }
                };
                EXECUTOR_SERVICE.submit(run);
            } catch (Exception e) {
                log.error("publish cg error", e);
            }

        }
    }
} 