package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.AccessTokenRequestParams;
import com.github.scribejava.core.oauth.AuthorizationUrlBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.enums.GameVersion;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CAPIService {
    private static CAPIService capiService;
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final Application application;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final OAuth20Service service;
    private OAuth2AccessToken oAuth2AccessToken;
    @Getter
    private final BooleanProperty active = new SimpleBooleanProperty(false);
    @Getter
    private final BooleanProperty fcEnabled = new SimpleBooleanProperty(true);
    private AuthorizationUrlBuilder authorizationUrlBuilder;
    private TimerTask timerTask;
    private Timer timer;

    private CAPIService(final Application application) {
        this.application = application;
        this.service = new ServiceBuilder(CapiOAuth20Service.CLIENT_ID)
                .defaultScope("auth capi")
                .callback("edomh://capi/")
//                .debug()
                .build(FrontierApi.instance());

        EVENT_LISTENERS.add(EventService.addListener(this, CapiOAuthCallbackEvent.class, event -> {
            log.info(event.getCode());
            log.info(event.getState());
            this.executor.submit(() -> {
                try {
                    log.info("get access token");

                    final AccessTokenRequestParams accessTokenRequestParams = AccessTokenRequestParams.create(event.getCode())
                            .pkceCodeVerifier(this.authorizationUrlBuilder.getPkce().getCodeVerifier())
                            .scope("capi")
                            .addExtraParameter("client_id", CapiOAuth20Service.CLIENT_ID);
                    this.oAuth2AccessToken = this.service.getAccessToken(accessTokenRequestParams);
                    log.info("save access token");
                    saveToken(this.oAuth2AccessToken);
                    Platform.runLater(() -> {
                        NotificationService.showInformation(NotificationType.SUCCESS, LocaleService.LocaleString.of("notification.capi.title"), LocaleService.LocaleString.of("notification.capi.message.auth.success"));
                        this.active.set(true);
                    });
                } catch (final InterruptedException e) {
                    log.error("InterruptedException", e);
                } catch (final ExecutionException e) {
                    log.error("ExecutionException", e);
                } catch (final IOException e) {
                    log.error("IOException", e);
                } catch (final Exception e) {
                    log.error("Exception", e);
                }
            });
            requestFleetCarrierData();
        }));
        EVENT_LISTENERS.add(EventService.addListener(this, TerminateApplicationEvent.class, terminateApplicationEvent -> this.executor.shutdownNow()));
        EVENT_LISTENERS.add(EventService.addListener(this, JournalInitEvent.class, event -> {
            if (event.isInitialised()) {
                Platform.runLater(() -> {
                            this.active.set(this.loadToken(APPLICATION_STATE.getPreferredCommander().orElse(null)));
                            this.fcEnabled.set(true);
                            APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                                if (this.timer != null) {
                                    this.timer.cancel();
                                }
                                final String pathname = commander.getCommanderFolder();
                                final File fleetCarrierFileDir = new File(pathname);
                                fleetCarrierFileDir.mkdirs();
                                final File fleetCarrierFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.FLEETCARRIER_FILE);
                                this.timer = new Timer("Fleetcarrier-api-task", true);
                                this.timerTask = new TimerTask() {
                                    @Override
                                    public void run() {
                                        requestFleetCarrierData();
                                    }
                                };
                                this.timer.scheduleAtFixedRate(this.timerTask, calculateDelay(fleetCarrierFile), 300L * 1000L);
                            });
                        }
                );

            }
        }));
        EVENT_LISTENERS.add(EventService.addListener(this, TerminateApplicationEvent.class, event -> {
            if (this.timer != null) {
                this.timer.cancel();
            }
            this.executor.shutdownNow();
        }));
    }

    private static long calculateDelay(final File fleetCarrierFile) {
        if (!fleetCarrierFile.exists()) {
            return 0;
        }
        final ZonedDateTime now = ZonedDateTime.now();
        final ZonedDateTime fileModified = ZonedDateTime.ofInstant(Instant.ofEpochMilli(fleetCarrierFile.lastModified()), ZoneId.systemDefault());
        final long delay = 300L * 1000L;
        final long delayed = (now.toEpochSecond() - fileModified.toEpochSecond()) * 1000;
        if (delayed > delay) {
            return 0;
        } else {
            return delay - delayed;
        }
    }

    public static CAPIService getInstance() {
        if (capiService == null) {
            throw new UnsupportedOperationException("CapiService not initialized!");
        }
        return capiService;
    }

    public static CAPIService getInstance(final Application application) {
        if (capiService == null) {
            capiService = new CAPIService(application);
        }
        return capiService;
    }

    public void authenticate() {
        this.authorizationUrlBuilder = ((CapiOAuth20Service) this.service).getAuthorizationUrlBuilder();
        final String build = this.authorizationUrlBuilder.build();
        log.debug(build);
        Platform.runLater(() -> this.application.getHostServices().showDocument(build));
    }

    private void requestFleetCarrierData() {
        if (this.active.get() && this.fcEnabled.get()) {
            APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {

                this.executor.submit(() -> {
                    final String url = GameVersion.LEGACY.equals(commander.getGameVersion()) ? "https://legacy-companion.orerve.net/fleetcarrier" : "https://companion.orerve.net/fleetcarrier";
                    final OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, url);
                    this.service.signRequest(this.oAuth2AccessToken, oAuthRequest);
                    try {
                        Response response = this.service.execute(oAuthRequest);
                        // retry if token expired
                        if (response.getCode() == 401) {
                            log.warn("Frontier API returned unauthorized. Attempting to refresh token.");
                            refreshToken();
                            final OAuthRequest oAuthRequest2 = new OAuthRequest(Verb.GET, url);
                            this.service.signRequest(this.oAuth2AccessToken, oAuthRequest2);
                            response = this.service.execute(oAuthRequest2);
                        }
                        // handle response
                        if (response.getCode() == 204) {
                            log.warn("Frontier API returned a " + response.getCode() + "(No fleetcarrier). Disabling FC endpoint.");
                            // no FC -> disable endpoint
                            Platform.runLater(() -> {
                                this.fcEnabled.set(false);
                            });
                        } else if (response.getCode() == 400) {
                            log.warn("Frontier API returned a " + response.getCode() + "(No game entitlement). Disabling FC endpoint.");
                            // no FC -> disable endpoint
                            Platform.runLater(() -> {
                                this.fcEnabled.set(false);
                                NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.capi.title"), LocaleService.LocaleString.of("notification.capi.message.400"));
                            });
                        } else if (response.getCode() == 200) {
                            log.info("Frontier API returned a " + response.getCode() + ". Storing response.");
                            // write response to file
                            final String pathname = commander.getCommanderFolder();
                            final File fleetCarrierFileDir = new File(pathname);
                            fleetCarrierFileDir.mkdirs();
                            final File fleetCarrierFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.FLEETCARRIER_FILE);
                            try (final FileOutputStream fileOutputStream = new FileOutputStream(fleetCarrierFile)) {
                                fileOutputStream.write(response.getBody().getBytes(StandardCharsets.UTF_8));
                            }
                        } else {
                            log.warn("Frontier API returned a " + response.getCode() + ". Disabling service.");
                            Platform.runLater(() -> this.active.set(false));
                        }
                        Platform.runLater(() -> EventService.publish(new CapiFleetCarrierEvent()));
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
                            this.active.set(false);
                            final String pathname = commander.getCommanderFolder();
                            final boolean delete = new File(pathname + OsConstants.getOsSlash() + AppConstants.FLEETCARRIER_FILE).delete();
                            if (delete) log.info("Deleted stale fleetcarrier file");
                            NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.capi.title"), LocaleService.LocaleString.of("notification.capi.message.auth.fail"));
                        });
                    }
                });
            });
        }
    }

    private void refreshToken() {
        try {
            this.oAuth2AccessToken = this.service.refreshAccessToken(this.oAuth2AccessToken.getRefreshToken());
            saveToken(this.oAuth2AccessToken);
            Platform.runLater(() -> this.active.set(true));
        } catch (final InterruptedException e) {
            log.error("InterruptedException", e);
            Platform.runLater(() -> this.active.set(false));
        } catch (final ExecutionException e) {
            log.error("ExecutionException", e);
            Platform.runLater(() -> this.active.set(false));
        } catch (final IOException e) {
            log.error("IOException", e);
            Platform.runLater(() -> this.active.set(false));
        }
    }

    private boolean loadToken(final Commander commander) {
        this.oAuth2AccessToken = null;
        if (commander != null) {
            final String pathname = commander.getLiveCommanderFolder();
            final File capiTokenDir = new File(pathname);
            capiTokenDir.mkdirs();
            final File capiTokenFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.CAPI_FILE);
            if (capiTokenFile.exists()) {
                try {
                    final JsonNode tokenJson = OBJECT_MAPPER.readTree(Files.readString(capiTokenFile.toPath()));
                    final String accessToken = tokenJson.get("accessToken").asText();
                    final String tokenType = tokenJson.get("tokenType").asText();
                    final Integer expiresIn = tokenJson.get("expiresIn").asInt();
                    final String refreshToken = tokenJson.get("refreshToken").asText();
                    final String scope = tokenJson.get("scope").asText();
                    final String rawResponse = tokenJson.get("rawResponse").asText();
                    this.oAuth2AccessToken = new OAuth2AccessToken(accessToken, tokenType, expiresIn, refreshToken, scope, rawResponse);
                } catch (final IOException e) {
                    log.error("Failed to process CAPI token file", e);
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    private void saveToken(final OAuth2AccessToken accessToken) {

        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            try {
                final String tokenJson = OBJECT_MAPPER.writeValueAsString(accessToken);
                final String pathname = commander.getLiveCommanderFolder();
                final File capiTokenDir = new File(pathname);
                capiTokenDir.mkdirs();
                final File capiTokenFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.CAPI_FILE);
                try (final FileOutputStream fileOutputStream = new FileOutputStream(capiTokenFile)) {
                    fileOutputStream.write(tokenJson.getBytes(StandardCharsets.UTF_8));
                }
            } catch (final IOException e) {
                log.error("Failed to save token to CAPI token file", e);
            }
        });
    }

    public void deauthenticate() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            try {
                Platform.runLater(() -> this.active.set(false));
                final String pathname = commander.getLiveCommanderFolder();
                Files.delete(Path.of(pathname, AppConstants.CAPI_FILE));
            } catch (final IOException e) {
                log.error("Failed to delete CAPI token file", e);
            }
        });
    }
}
