/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
