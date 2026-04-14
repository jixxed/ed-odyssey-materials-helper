/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.dialog;

import org.apache.maven.artifact.versioning.ComparableVersion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangelogParser {
    private static final Pattern VERSION_BLOCK =
            Pattern.compile(
                    "Version\\s+([0-9A-Za-z.+\\-]+)\\R-+\\R([\\s\\S]*?)(?=\\RVersion\\s+[0-9A-Za-z.+\\-]+|$)"
            );

    public static String getWhatsNew(String whatsnew, String lastRunVersion) {

        ComparableVersion last = new ComparableVersion(lastRunVersion);
        Matcher matcher = VERSION_BLOCK.matcher(whatsnew);

        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            String versionString = matcher.group(1);
            String changes = matcher.group(2).trim();

            ComparableVersion current =
                    new ComparableVersion(versionString);

            if (current.compareTo(last) > 0) {
                result.append("Version ")
                        .append(versionString)
                        .append("\n-------------\n")
                        .append(changes)
                        .append("\n\n");
            }
        }

        return result.toString().trim();
    }
}
