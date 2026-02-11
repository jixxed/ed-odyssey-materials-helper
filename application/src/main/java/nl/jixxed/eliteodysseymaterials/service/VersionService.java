package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nu.redpois0n.oslib.OperatingSystem;
import org.apache.maven.artifact.versioning.ComparableVersion;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class VersionService {

    private static final boolean BETA = false;
    public static final String USER_AGENT = "EDOMH/%s (%s; %s; %s)";

    public static boolean isBeta() {
        return BETA;
    }

    public static String getLatestVersion() throws IOException {
        if (isDev()) {
            return "dev";
        }
        final URL url = new URL("https://api.github.com/repos/jixxed/ed-odyssey-materials-helper/releases/latest");
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        final InputStream responseStream = connection.getInputStream();
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode response = objectMapper.readTree(responseStream);
        return response.get("tag_name").asText();
    }

    public static String getBuildVersion() {
        return Secrets.getOrDefault("app.version", "dev");
    }

    public static boolean isLatestVersion() {
        final String buildVersion = VersionService.getBuildVersion();
        String latestVersion = "";
        try {
            latestVersion = VersionService.getLatestVersion();
        } catch (final IOException e) {
            log.error("Error retrieving latest version", e);
        }

        return (VersionService.isDev() || buildVersion.equals(latestVersion) || latestVersion.isBlank());
    }

    public static boolean isDev() {
        return "dev".equals(getBuildVersion());
    }

    public static String getUserAgent() {
        OperatingSystem.getOperatingSystem().getDetailedString();
        return USER_AGENT.formatted(getBuildVersion(), OperatingSystem.getOperatingSystem().getDetailedString(), OperatingSystem.getOperatingSystem().getArch(), OperatingSystem.getOperatingSystem().getDesktopEnvironment());
    }

    public static boolean isOutdated() {
        if(isDev()){
            return false;
        }
        String minVersion = Secrets.getOrDefault("min.version", "1.0.0");
        return !isEqualOrNewer(getBuildVersion(), minVersion);
    }

    protected static boolean isEqualOrNewer(String current, String min) {
        ComparableVersion requiredVersion = new ComparableVersion(min);
        ComparableVersion currentVersion = new ComparableVersion(current);
        return currentVersion.compareTo(requiredVersion) >= 0;
    }
}
