package nl.jixxed.eliteodysseymaterials.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.ed.confidential.api.PropertiesSecrecyLoader;
import nl.jixxed.ed.confidential.api.SecrecyLoader;

import java.io.InputStream;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.function.Supplier;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Secrets {
    private static Properties props;

    static {
        loadSecrets();
    }

    public static Properties loadSecrets() {
        ServiceLoader<SecrecyLoader> loader = ServiceLoader.load(SecrecyLoader.class);
        SecrecyLoader secrecyLoader = loader.stream().map(ServiceLoader.Provider::get)
                .filter(f -> !isJUnitTest() && !(f instanceof PropertiesSecrecyLoader))
                .findFirst()
                .orElseGet(PropertiesSecrecyLoader::new);

        props = secrecyLoader.load(() -> {
            Properties properties = new Properties();
            try (InputStream in = Secrets.class
                    .getClassLoader()
                    .getResourceAsStream("secret.properties")) {
                if (in == null) {
                    return properties;
                }
                properties.load(in);
            } catch (Exception e) {
                log.error("Failed to load secret.properties", e);
            }
            return properties;
        });
        return props;
    }

    public static String getOrDefault(final String key, final String defaultValue) {
        if (isJUnitTest()) {
            return defaultValue;
        }
        return props.getProperty(key, defaultValue);
    }

    public static boolean isJUnitTest() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }
}
