package nl.jixxed.eliteodysseymaterials;

import io.sentry.Attachment;
import io.sentry.Sentry;
import io.sentry.protocol.OperatingSystem;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.service.SupportService;
import nl.jixxed.eliteodysseymaterials.service.VersionService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Slf4j
public class Main {
    static {
        final List<List<? extends ShipModule>> modules = ShipModule.ALL_MODULES;
    }

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();


    public static void main(final String[] args) {
        //handle deeplinks
        if (args.length > 0 && args[0].startsWith("edomh://")) {
            handleDeeplink(args[0]);
        }

        //exit if app is already running
        if (APPLICATION_STATE.isLocked()) {
            System.exit(0);
        }
        log.info("launching app with java version: " + getVersion());
        Sentry.init(options -> {

            final String buildVersion = getBuildVersion();
            options.setDsn("https://1aacf97280717f749dfc93a1713f9551@o4507814449774592.ingest.de.sentry.io/4507814504759376");
            options.setEnvironment(getEnvironment(buildVersion));
            options.setRelease("edomh-app@" + buildVersion);
            options.setEnabled(!buildVersion.equals("dev"));
            options.setBeforeSend((event, hint) -> {
                if (!VersionService.isLatestVersion()) {
                    return null;  // Returning null prevents the event from being sent to Sentry
                }
                String supportFile = "";
                try {
                    supportFile = SupportService.createSupportPackage();
                } catch (Exception e) {
                    log.error("Failed to create support package", e);
                }
                if(!supportFile.isBlank()) {
                    Attachment attachment = new Attachment(supportFile);
                    hint.addAttachment(attachment);
                }
                OperatingSystem os = new OperatingSystem();
                os.setName(System.getProperty("os.name"));
                os.setVersion(System.getProperty("os.version"));
                os.setBuild(System.getProperty("os.arch"));
                event.getContexts().setOperatingSystem(os);
                if(APPLICATION_STATE.getFileheader() != null) {
                    event.setTag("game.version", APPLICATION_STATE.getFileheader().getGameversion());
                    event.setTag("game.build", APPLICATION_STATE.getFileheader().getBuild());
                    event.setTag("game.language", APPLICATION_STATE.getFileheader().getLanguage());
                    event.setTag("game.odyssey", String.valueOf(APPLICATION_STATE.getFileheader().getOdyssey()));
                }
                return event;
            });
        });
        FXApplication.launchFx(args);
    }

    private static String getEnvironment(String buildVersion) {
        if (buildVersion.equals("dev")) {
            return "Development";
        }else if(buildVersion.contains("beta")){
            return "Beta";
        }else{
            return "Production";
        }
    }

    public static String getBuildVersion() {
        var version = System.getProperty("app.version");
        if(version == null){
            return "dev";
        }
        return version;
    }

    private static void handleDeeplink(final String arg) {
        try (final OutputStream output = new FileOutputStream(OsConstants.DEEPLINK + ".tmp")) {
            output.write(arg.getBytes(StandardCharsets.UTF_8));
        } catch (final IOException e) {
            log.error("Error creating create deeplink tmp file", e);
        }
        try {
            Files.move(Path.of(OsConstants.DEEPLINK + ".tmp"), Path.of(OsConstants.DEEPLINK), StandardCopyOption.REPLACE_EXISTING);
        } catch (final IOException e) {
            log.error("Error moving deeplink tmp file", e);
        }
    }
    private static int getVersion() {
        String version = System.getProperty("java.version");
        if(version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            final int dot = version.indexOf(".");
            if(dot != -1) { version = version.substring(0, dot); }
        } return Integer.parseInt(version);
    }
}
