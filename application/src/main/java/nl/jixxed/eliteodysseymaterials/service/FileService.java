package nl.jixxed.eliteodysseymaterials.service;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
@Slf4j
public class FileService {
    public static int countFiles(Path folderPath) throws IOException {
        return (int) Files.walk(folderPath)
                .filter(Files::isRegularFile)
                .count();
    }

    public static void deleteOldFiles(Path folderPath, int thresholdDays) {
        File folder = folderPath.toFile();
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : Arrays.stream(files).sorted(Comparator.comparingLong(FileService::getFileAgeInMillis)).skip(5).toList()) {
                long fileAgeInDays = getFileAgeInDays(file);
                if (fileAgeInDays > thresholdDays) {
                    if (file.delete()) {
                        log.info("Deleted: " + file.getName());
                    } else {
                        log.error("Failed to delete: " + file.getName());
                    }
                }
            }
        }
    }

    public static long getFileAgeInDays(File file) {
        final long ageInMillis = getFileAgeInMillis(file);
        return ageInMillis / (1000 * 60 * 60 * 24); // Convert milliseconds to days

    }

    private static long getFileAgeInMillis(File file) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(Paths.get(file.getPath()), BasicFileAttributes.class);
            long creationTime = attrs.creationTime().toMillis();
            long currentTime = new Date().getTime();
            long ageInMillis = currentTime - creationTime;
            return ageInMillis;
        } catch (Exception e) {
            log.error("Failed to file age.", e);
            return -1;
        }
    }
}
