package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class VersionService {

    private static final boolean BETA = false;

    public static boolean isBeta() {
        return BETA;
    }

    public static String getLatestVersion() throws IOException {
        if (getBuildVersion() == null) {
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
        return System.getProperty("app.version");
    }

    public static boolean isLatestVersion() {
        final String buildVersion = VersionService.getBuildVersion();
        String latestVersion = "";
        try {
            latestVersion = VersionService.getLatestVersion();
        } catch (final IOException e) {
            log.error("Error retrieving latest version", e);
        }

        return (VersionService.getBuildVersion() == null || buildVersion.equals(latestVersion) || latestVersion.isBlank());
    }

    public static boolean isDev() {
        return getBuildVersion() == null;
    }
}
