package nl.jixxed.eliteodysseymaterials;

import io.github.secretx33.resourceresolver.PathMatchingResourcePatternResolver;
import io.github.secretx33.resourceresolver.Resource;
import io.github.secretx33.resourceresolver.ResourcePatternResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LocalizationCheckTest {
    @Test
    @Tag("manual")
    public void noApostropheExistsInCSV() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:locale/**/*.csv");
        List<Executable> resourceChecks = new ArrayList<>();
        for (Resource resource : resources) {
            // Process the resource
            System.out.println(resource.getFilename());
            String[] split = resource.getContentAsString(StandardCharsets.UTF_8).split("\n");
            for (int i = 0; i < split.length; i++) {
                String line = split[i];
                int lineNumber = i + 1;
                resourceChecks.add(() -> {
                    Assertions.assertFalse(line.contains("'"), () -> {
                        try {
                            final String key = line.split(",")[0];
                            return resource.getFile().toString() + " contains APOSTROPHE(UTF+0027) " + line.codePoints().filter(ch -> ch == '\'').count() + " time(s) on line " + lineNumber + ". These need to be converted to RIGHT SINGLE QUOTATION MARK(UTF+2019). key: " + key.substring(1, key.length() - 1);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                });
            }
        }
        Assertions.assertAll(resourceChecks);
    }
}
