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
import io.sentry.Sentry;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.FXApplication;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.enums.GameVersion;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.service.capi.EndpointHandler;
import nl.jixxed.eliteodysseymaterials.service.capi.FleetCarrierEndpointHandler;
import nl.jixxed.eliteodysseymaterials.service.capi.SquadronEndpointHandler;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class CAPIService {
    private static CAPIService capiService;
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    @Getter
    private final OAuth20Service oAuth20Service;
    @Getter
    private OAuth2AccessToken oAuth2AccessToken;
    @Getter
    private final SimpleBooleanProperty active = new SimpleBooleanProperty(false);
    private AuthorizationUrlBuilder authorizationUrlBuilder;
    private TimerTask timerTask;
    private Timer timer;
    private final List<EndpointHandler> endpointHandlers = new ArrayList<>();

    private CAPIService() {
        endpointHandlers.add(new FleetCarrierEndpointHandler(this));
        endpointHandlers.add(new SquadronEndpointHandler(this));
        this.oAuth20Service = new ServiceBuilder(CapiOAuth20Service.CLIENT_ID)
                .defaultScope("auth capi")
                .callback("edomh://capi/")
//                .debug()
                .build(FrontierApi.instance());

        EVENT_LISTENERS.add(EventService.addListener(this, CapiOAuthCallbackEvent.class, event -> {
            this.executor.submit(() -> {
                try {
                    log.info("get access token");

                    final AccessTokenRequestParams accessTokenRequestParams = AccessTokenRequestParams.create(event.getCode())
                            .pkceCodeVerifier(this.authorizationUrlBuilder.getPkce().getCodeVerifier())
                            .scope("capi")
                            .addExtraParameter("client_id", CapiOAuth20Service.CLIENT_ID);
                    this.oAuth2AccessToken = this.oAuth20Service.getAccessToken(accessTokenRequestParams);
//                    https://companion.orerve.net/profile
                    CommanderCheck commanderCheck = validCommander();
                    if (commanderCheck.isValid()) {
                        log.info("save access token");
                        saveToken(this.oAuth2AccessToken);
                        Platform.runLater(() -> {
                            NotificationService.showInformation(NotificationType.SUCCESS, LocaleService.LocaleString.of("notification.capi.title"), LocaleService.LocaleString.of("notification.capi.message.auth.success"));
                            this.active.set(true);
                            endpointHandlers.forEach(EndpointHandler::enable);
                            endpointHandlers.forEach(EndpointHandler::requestData);
                        });
                    } else {
                        Platform.runLater(() -> {
                            if(commanderCheck.expected != null) {
                                NotificationService.showInformation(NotificationType.ERROR, LocaleService.LocaleString.of("notification.capi.title"), LocaleService.LocaleString.of("notification.capi.message.auth.bad.account", commanderCheck.expected, commanderCheck.actual));
                            }
//                            else{
//                                NotificationService.showInformation(NotificationType.ERROR, LocaleService.LocaleString.of("notification.capi.title"), LocaleService.LocaleString.of("notification.capi.error"));
//                            }
                            this.active.set(false);
                        });
                    }
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
        }));
        EVENT_LISTENERS.add(EventService.addListener(this, TerminateApplicationEvent.class, event -> {
            if (this.timer != null) {
                this.timer.cancel();
            }
            this.executor.shutdownNow();
        }));
        EVENT_LISTENERS.add(EventService.addListener(this, JournalInitEvent.class, event -> {
            if (event.isInitialised()) {
                Platform.runLater(() -> {
                            this.active.set(this.loadToken(APPLICATION_STATE.getPreferredCommander().orElse(null)));
                            if(this.active.get()){
                                endpointHandlers.forEach(EndpointHandler::enable);
                            }else{
                                endpointHandlers.forEach(EndpointHandler::disable);
                            }
                            APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                                if (this.timer != null) {
                                    this.timer.cancel();
                                }
                                final String pathname = commander.getCommanderFolder();
                                final File commanderFolder = new File(pathname);
                                commanderFolder.mkdirs();
                                this.timer = new Timer("capi-task", true);
                                this.timerTask = new TimerTask() {
                                    @Override
                                    public void run() {
                                        if (isCapiRunning()) {
                                            endpointHandlers.forEach(EndpointHandler::requestData);
                                        }
                                    }
                                };
                                //endpoints are called every 5 seconds if updates are needed
                                this.timer.schedule(this.timerTask, 0L, 5L * 1000L);
                            });
                        }
                );

            }
        }));
    }

    private static boolean isCapiRunning() {
        return Boolean.FALSE.equals(PreferencesService.getPreference("colonisation.horizons.pause.capi", false));
    }

    private CommanderCheck validCommander() {
        return APPLICATION_STATE.getPreferredCommander().map(commander -> {
            final String url = GameVersion.LEGACY.equals(commander.getGameVersion()) ? "https://legacy-companion.orerve.net/profile" : "https://companion.orerve.net/profile";
            final OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, url);
            try {
                Response response = this.request(oAuthRequest, true);
                if (response.getCode() == 200) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    final JsonNode jsonNode = objectMapper.readTree(response.getBody());
                    final String name = jsonNode.get("commander").get("name").asText();

                    log.info("Test equals: " + commander.getName() + " <> " + name);
                    var check = new CommanderCheck(commander.getName(), name);
                    if (!check.isValid()) {
                        Sentry.captureMessage("Commander name mismatch: expected '" + commander.getName() + "', got '" + name + "'");
                    }
                    return check;
                }
                if (response.getCode() == 400) {
                    Platform.runLater(() -> {
                        endpointHandlers.forEach(EndpointHandler::disable);
//                        this.fcEndpointEnabled.set(false);
                        NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.capi.title"), LocaleService.LocaleString.of("notification.capi.message.400"));
                    });
                }
            } catch (final InterruptedException e) {
                log.error("InterruptedException", e);
            } catch (final ExecutionException e) {
                log.error("ExecutionException", e);
            } catch (final IOException e) {
                log.error("IOException", e);
            } catch (final Exception e) {
                log.error("Exception", e);
            }
            return new CommanderCheck(null, null);
        }).orElse(new CommanderCheck(null, null));
    }

    public static CAPIService getInstance() {
        if (capiService == null) {
            capiService = new CAPIService();
        }
        return capiService;
    }

    public void authenticate() {
        this.authorizationUrlBuilder = ((CapiOAuth20Service) this.oAuth20Service).getAuthorizationUrlBuilder();
        final String build = this.authorizationUrlBuilder.build();
        Platform.runLater(() -> FXApplication.getInstance().getHostServices().showDocument(build));
    }

    public void refreshToken() {
        try {
            this.oAuth2AccessToken = this.oAuth20Service.refreshAccessToken(this.oAuth2AccessToken.getRefreshToken());
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
                } catch (final IOException | NullPointerException e) {
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
                Files.delete(Path.of(commander.getLiveCommanderFolder(), AppConstants.CAPI_FILE));
                endpointHandlers.forEach(EndpointHandler::cleanup);
            } catch (final IOException e) {
                log.error("Failed to delete CAPI token file", e);
            }
        });
    }
    private record CommanderCheck(String expected, String actual){
        boolean isValid(){
            return expected != null && expected.equalsIgnoreCase(actual);
        }
    }
    public synchronized Response request(OAuthRequest oAuthRequest) throws IOException, ExecutionException, InterruptedException {
        return request(oAuthRequest, false);
    }
    private synchronized Response request(OAuthRequest oAuthRequest, boolean skipActiveCheck) throws IOException, ExecutionException, InterruptedException {
        if(skipActiveCheck || active.get()) {
            oAuth20Service.signRequest(oAuth2AccessToken, oAuthRequest);
            log.info("requesting data from CAPI: " + oAuthRequest.getUrl());
            Response response = oAuth20Service.execute(oAuthRequest);
            if (response.getCode() == 401) {
                log.warn("Frontier API returned unauthorized. Attempting to refresh token.");
                capiService.refreshToken();
                final OAuthRequest oAuthRequestRetry = new OAuthRequest(oAuthRequest.getVerb(), oAuthRequest.getUrl());
                oAuth20Service.signRequest(oAuth2AccessToken, oAuthRequestRetry);
                log.info("requesting data from CAPI again: " + oAuthRequest.getUrl());
                response = oAuth20Service.execute(oAuthRequestRetry);
            }
            if (response.getCode() == 401) {
                Platform.runLater(() -> active.set(false));
                Platform.runLater(() -> {
                    NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.capi.title"), LocaleService.LocaleString.of("notification.capi.message.auth.fail"));
                });
            }else{
                return response;
            }
        }
        return new Response(501,"CAPI service disabled", Map.of(), "");
    }
}
