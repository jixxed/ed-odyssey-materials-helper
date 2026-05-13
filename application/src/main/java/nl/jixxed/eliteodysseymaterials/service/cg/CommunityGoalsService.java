/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.cg;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.Goal;
import nl.jixxed.eliteodysseymaterials.helper.DnsHelper;
import nl.jixxed.eliteodysseymaterials.service.HttpClientService;
import nl.jixxed.eliteodysseymaterials.service.Secrets;
import nl.jixxed.eliteodysseymaterials.service.VersionService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class CommunityGoalsService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    private static final ExecutorService EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();

    private static final ScheduledExecutorService SCHEDULED_EXECUTOR = Executors.newSingleThreadScheduledExecutor();

    @Getter
    private static List<Goal> availableCommunityGoals = List.of();
    private static Map<Integer, ReportModels.ReportResponse> reports = new ConcurrentHashMap<>();
    private static Disposable subscribe;
    private static AtomicInteger activeCGID = new AtomicInteger(Goal.ACTIVE.getId());

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module());
        EVENT_LISTENERS.add(EventService.addStaticListener(TerminateApplicationEvent.class, _ -> {
            EXECUTOR.shutdownNow();
            SCHEDULED_EXECUTOR.shutdownNow();
            if (subscribe != null) {
                subscribe.dispose();
            }
        }));

        SCHEDULED_EXECUTOR.scheduleWithFixedDelay(update(), 0, 15, TimeUnit.MINUTES);
    }

    private static Runnable update() {
        AtomicReference<Runnable> runnable = new AtomicReference<>();
        subscribe = Observable.<AvailableGoals>create(emitter -> {
                    runnable.set(() -> {
                        getReport(activeCGID.get());
                        AvailableGoals availableGoals = webRequestAvailable();
                        if (availableGoals != null) {
                            emitter.onNext(availableGoals);
                        }
                    });
                })
                .observeOn(Schedulers.io())
                .subscribe(goals -> {
                            availableCommunityGoals = goals.goals();
                            EventService.publish(new AvailableCommunityGoalsEvent(availableCommunityGoals));
                        },
                        t -> log.error(t.getMessage(), t));
        return runnable.get();
    }

    public static Optional<ReportModels.ReportResponse> getActiveReport() {
        return getReport(Goal.ACTIVE.getId());
    }

    public static Optional<ReportModels.ReportResponse> getReport(Integer cgid) {
        activeCGID.set(cgid);
        ReportModels.ReportResponse reportModels = reports.get(cgid);
        if (reportModels != null) {
            if (reportModels.generatedAtUtc().isBefore(Instant.now().minus(15, ChronoUnit.MINUTES))) {
                fetchNewReport(cgid);
            }
            return Optional.of(reportModels);
        } else {
            fetchNewReport(cgid);
        }
        return Optional.empty();
    }

    private static void fetchNewReport(Integer cgid) {
        EXECUTOR.submit(() -> {
            ReportModels.ReportResponse report = webRequestReports(cgid);
            reports.put(cgid, report);
            EventService.publish(new CommunityGoalReportEvent(report));
        });
    }

    private synchronized static ReportModels.ReportResponse webRequestReports(Integer cgid) {
        ReportModels.ReportResponse reportModels = reports.get(cgid);
        if (reportModels != null && reportModels.generatedAtUtc().isAfter(Instant.now().minus(15, ChronoUnit.MINUTES))) {
            return null;
        }
        try {
            final HttpResponse<String> send;
            log.info("Fetching CG report for cgid {}", cgid);
            HttpClient httpClient = HttpClientService.getHttpClient();
            final String domainName = DnsHelper.resolveCname(Secrets.getOrDefault("cg.services.host", "localhost"));
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://" + domainName + "/api/report/" + ((cgid != null && !Goal.ACTIVE.getId().equals(cgid)) ? cgid : "active")))
                    .header("User-Agent", VersionService.getUserAgent())
                    .header("X-Api-Key", Secrets.getOrDefault("cg.services.api.key", "none"))
                    .GET()
                    .build();
            send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return OBJECT_MAPPER.readValue(send.body(), ReportModels.ReportResponse.class);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (final Exception e) {
            log.error("Failed to fetch CG report", e);
        }
        return null;
    }

    private static AvailableGoals webRequestAvailable() {
        try {
            final HttpResponse<String> send;
            log.info("Fetching available CG's");

            HttpClient httpClient = HttpClientService.getHttpClient();
            final String domainName = DnsHelper.resolveCname(Secrets.getOrDefault("cg.services.host", "localhost"));
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://" + domainName + "/api/available"))
                    .header("User-Agent", VersionService.getUserAgent())
                    .header("X-Api-Key", Secrets.getOrDefault("cg.services.api.key", "none"))
                    .GET()
                    .build();
            send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return OBJECT_MAPPER.readValue(send.body(), AvailableGoals.class);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (final Exception e) {
            log.error("Failed to fetch available CG's", e);
        }
        return null;
    }
}
