package nl.jixxed.eliteodysseymaterials;

import io.github.secretx33.resourceresolver.PathMatchingResourcePatternResolver;
import io.github.secretx33.resourceresolver.Resource;
import io.github.secretx33.resourceresolver.ResourcePatternResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LocalizationCheckTest {
    @Test
    public void noApostropheExistsInCSV() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:locale/**/*.csv");
        List<Executable> resourceChecks = new ArrayList<>();
        for (Resource resource : resources) {
            // Process the resource
            resourceChecks.add(() -> {
                System.out.println(resource.getFilename());
                Assertions.assertFalse(resource.getContentAsString(StandardCharsets.UTF_8).contains("'"), () -> {
                    try {
                        return resource.getFilename() + " contains APOSTROPHE(UTF+0027) " + resource.getContentAsString(StandardCharsets.UTF_8).codePoints().filter(ch -> ch == '\'').count() + " time(s). These need to be converted to RIGHT SINGLE QUOTATION MARK(UTF+2019).";
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            });
        }
        Assertions.assertAll(resourceChecks);
    }
}
