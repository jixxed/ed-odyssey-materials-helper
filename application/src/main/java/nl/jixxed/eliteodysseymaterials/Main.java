package nl.jixxed.eliteodysseymaterials;

import io.sentry.Attachment;
import io.sentry.Sentry;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.logging.SLF4JLogDelegateFactory;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.service.SupportService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static io.vertx.core.logging.LoggerFactory.LOGGER_DELEGATE_FACTORY_CLASS_NAME;
import static java.lang.System.setProperty;

@Slf4j
public class Main {
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
        setProperty(LOGGER_DELEGATE_FACTORY_CLASS_NAME, SLF4JLogDelegateFactory.class.getName());
        LoggerFactory.getLogger(LoggerFactory.class);
        log.info("launching app with java version: " + getVersion());
        Sentry.init(options -> {
            final String buildVersion = getBuildVersion();
            options.setDsn("https://1aacf97280717f749dfc93a1713f9551@o4507814449774592.ingest.de.sentry.io/4507814504759376");
            options.setEnvironment(getEnvironment(buildVersion));
            options.setRelease("edomh-app@" + buildVersion);
            options.setEnabled(!buildVersion.equals("dev"));
            options.setBeforeSend((event, hint) -> {
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
