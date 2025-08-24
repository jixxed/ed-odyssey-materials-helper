package nl.jixxed.eliteodysseymaterials.service.capi;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import javafx.application.Platform;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.GameVersion;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.service.CAPIService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.CapiSquadronEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.TerminateApplicationEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class SquadronEndpointHandler implements EndpointHandler {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final CAPIService capiService;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    @Getter
    private final AtomicBoolean endpointEnabled = new AtomicBoolean(true);

    public SquadronEndpointHandler(CAPIService capiService) {
        this.capiService = capiService;
        EVENT_LISTENERS.add(EventService.addListener(this, TerminateApplicationEvent.class, _ -> {
            this.executor.shutdownNow();
        }));
    }

    @Override
    public void enable() {
        endpointEnabled.set(true);
    }

    @Override
    public void disable() {
        endpointEnabled.set(false);
    }

    @Override
    public void cleanup() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            try {
                final String pathname = GameVersion.LEGACY.equals(commander.getGameVersion()) ? commander.getCommanderFolder() : commander.getLiveCommanderFolder();
                Files.delete(Path.of(pathname, AppConstants.SQUADRON_FILE));
            } catch (final IOException e) {
                log.error("Failed to delete CAPI endpoint file", e);
            }
        });
    }

    @Override
    public void requestData() {
        log.info("Starting request of squadron endpoint handler");
        if (CAPIService.getInstance().getActive().get() && endpointEnabled.get()) {
            APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                if(!GameVersion.LIVE.equals(commander.getGameVersion()))//squadron endpoint is live only
                {
                    return;
                }
                final String pathname = commander.getCommanderFolder();
                final File endpointFileDir = new File(pathname);
                endpointFileDir.mkdirs();
                final File endpointFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.SQUADRON_FILE);
                if (isOutdated(endpointFile)) {
                    this.executor.submit(() -> {
                        final String url =  "https://companion.orerve.net/squadron";
                        final OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, url);
                        try {
                            Response response = capiService.request(oAuthRequest);
                            // handle response
                            if (response.getCode() == 400) {
                                log.warn("Frontier API returned a " + response.getCode() + "(No game entitlement). Disabling Squadron endpoint.");
                                // no FC -> disable endpoint
                                endpointEnabled.set(false);
                                Platform.runLater(() -> {
                                    NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.capi.title"), LocaleService.LocaleString.of("notification.capi.message.400"));
                                });
                            } else if (response.getCode() == 200) {
                                if(response.getBody().equals("{}")){
                                    log.warn("Frontier API returned a " + response.getCode() + "(No squadron). Disabling Squadron endpoint.");
                                    // no FC -> disable endpoint
                                    endpointEnabled.set(false);
                                    final boolean delete = endpointFile.delete();
                                    if (delete) log.info("Deleted stale squadron file");
                                }else {
                                    log.info("Frontier API returned a " + response.getCode() + ". Storing response.");
                                    // write response to file
                                    try (final FileOutputStream fileOutputStream = new FileOutputStream(endpointFile)) {
                                        fileOutputStream.write(response.getBody().getBytes(StandardCharsets.UTF_8));
                                    }
                                }
                            } else {
                                log.warn("Frontier API returned a " + response.getCode() + ". Disabling service.");
                                endpointEnabled.set(false);
                            }
                            Platform.runLater(() -> EventService.publish(new CapiSquadronEvent()));
                        } catch (final InterruptedException e) {
                            log.error("InterruptedException", e);
                        } catch (final ExecutionException e) {
                            log.error("ExecutionException", e);
                        } catch (final IOException e) {
                            log.error("IOException", e);
                        } catch (final Exception e) {
                            log.error("Exception", e);
                            log.warn("Frontier API returned an error. Disabling service.");
                            endpointEnabled.set(false);
                            final boolean delete = endpointFile.delete();
                            if (delete) log.info("Deleted squadron file");
                        }
                    });
                }
            });
        }
    }
}
