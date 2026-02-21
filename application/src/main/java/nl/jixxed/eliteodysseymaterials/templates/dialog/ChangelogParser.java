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
