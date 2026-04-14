/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.ed.confidential.api.PropertiesSecrecyLoader;
import nl.jixxed.ed.confidential.api.SecrecyLoader;

import java.io.InputStream;
import java.util.Properties;
import java.util.ServiceLoader;

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
