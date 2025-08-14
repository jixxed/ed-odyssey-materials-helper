package nl.jixxed.eliteodysseymaterials.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Properties;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Secrets {
    private static Properties props;

    static {
        loadSecrets();
    }

    public static Properties loadSecrets() {
        props = new Properties();

        try (InputStream in = Secrets.class
                .getClassLoader()
                .getResourceAsStream("secret.properties")) {

            if (in == null) {
                return props;
            }

            props.load(in);
        } catch (Exception e) {
            log.error("Failed to load secret.properties", e);
            return props;
        }
        return props;
    }

    public static String getOrDefault(final String key, final String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
