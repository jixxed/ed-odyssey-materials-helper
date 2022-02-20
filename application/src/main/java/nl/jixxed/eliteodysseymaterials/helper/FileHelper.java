package nl.jixxed.eliteodysseymaterials.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileHelper {

    public static void copyFileContents(final File from, final File to) {
        try (final FileInputStream in = new FileInputStream(from); final FileOutputStream out = new FileOutputStream(to)) {
            int byteIn;
            while ((byteIn = in.read()) != -1) {
                out.write(byteIn);
            }
        } catch (final IOException e) {
            log.error("Failed to copy contents", e);
        }
    }
}
