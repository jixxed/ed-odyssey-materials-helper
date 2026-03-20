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
import nl.jixxed.eliteodysseymaterials.enums.EndpointState;
import nl.jixxed.eliteodysseymaterials.enums.GameVersion;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.service.CAPIService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

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
public class CommanderEndpointHandler implements EndpointHandler {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();
    private final CAPIService capiService;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public int getFrequency() {
        return 300;//refresh every 5 minutes
    }

    @Getter
    private static final AtomicBoolean endpointEnabled = new AtomicBoolean(true);
    @Getter
    private static final AtomicBoolean endpointPaused = new AtomicBoolean(false);

    public CommanderEndpointHandler(CAPIService capiService) {
        this.capiService = capiService;
        EVENT_LISTENERS.add(EventService.addListener(this, TerminateApplicationEvent.class, _ -> {
            this.executor.shutdownNow();
        }));
    }

    @Override
    public void enable() {
        endpointEnabled.set(true);
        APPLICATION_STATE.setCommanderEndpoint(EndpointState.ENABLED);
    }

    @Override
    public void disable() {
        endpointEnabled.set(false);
        APPLICATION_STATE.setCommanderEndpoint(EndpointState.DISABLED);
    }

    @Override
    public void pause() {
        endpointPaused.set(true);
        APPLICATION_STATE.setCommanderEndpoint(EndpointState.PAUSED);
    }

    @Override
    public void unpause() {
        endpointPaused.set(false);
        APPLICATION_STATE.setCommanderEndpoint(endpointEnabled.get() ? EndpointState.ENABLED : EndpointState.DISABLED);
    }

    @Override
    public void cleanup() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            try {
                final String pathname = GameVersion.LEGACY.equals(commander.getGameVersion()) ? commander.getCommanderFolder() : commander.getLiveCommanderFolder();
                Files.delete(Path.of(pathname, AppConstants.ARX_FILE));
            } catch (final IOException e) {
                log.error("Failed to delete commander file", e);
            }
        });
    }

    @Override
    public void requestData() {
        if (CAPIService.getInstance().getActive().get() && endpointEnabled.get() && !endpointPaused.get()) {
            APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                if (!GameVersion.LIVE.equals(commander.getGameVersion()))//balance endpoint is live only
                {
                    return;
                }
                final String pathname = commander.getCommanderFolder();
                final File endpointFileDir = new File(pathname);
                endpointFileDir.mkdirs();
                final File endpointFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.ARX_FILE);
                this.executor.submit(() -> {
                    if (CAPIService.getInstance().getActive().get() && endpointEnabled.get()) {
                        if (isOutdated(endpointFile)) {
                            final String url = "https://api.zaonce.net/3.0/payment/balance";
                            final OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, url);
                            try {
                                Response response = capiService.request(oAuthRequest);
                                // handle response
                                if (response.getCode() == 400) {
                                    log.warn("Frontier API returned a " + response.getCode() + "(No game entitlement). Disabling Commander endpoint.");
                                    // no FC -> disable endpoint
                                    disable();
                                    Platform.runLater(() -> {
                                        NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.capi.title"), LocaleService.LocaleString.of("notification.capi.message.400"));
                                    });
                                } else if (response.getCode() == 200) {
                                    log.info("Frontier API returned a " + response.getCode() + ". Storing response.");
                                    // write response to file
                                    try (final FileOutputStream fileOutputStream = new FileOutputStream(endpointFile)) {
                                        fileOutputStream.write(response.getBody().getBytes(StandardCharsets.UTF_8));
                                    }
                                } else if (response.getCode() == 418) {
                                    log.warn("Frontier API returned a " + response.getCode() + ". Pausing endpoint.");
                                    pause();
                                } else {
                                    log.warn("Frontier API returned a " + response.getCode() + ". Disabling service.");
                                    disable();
                                }
                                Platform.runLater(() -> EventService.publish(new CapiArxEvent()));
                            } catch (final InterruptedException e) {
                                log.error("InterruptedException", e);
                            } catch (final ExecutionException e) {
                                log.error("ExecutionException", e);
                            } catch (final IOException e) {
                                log.error("IOException", e);
                            } catch (final Exception e) {
                                log.error("Exception", e);
                                log.warn("Frontier API returned an error. Disabling service.");
                                Platform.runLater(() -> {
                                    NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.capi.title"), LocaleService.LocaleString.of("notification.capi.message.auth.fail"));
                                });
                                disable();
                                CAPIService.getInstance().getActive().set(false);
                                final boolean delete = endpointFile.delete();
                                if (delete) log.info("Deleted commander file");
                            }
                        }
                    }
                });
            });
        }
    }
}
