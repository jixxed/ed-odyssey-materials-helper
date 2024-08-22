package nl.jixxed.eliteodysseymaterials.service;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class BackupService {

    public static final int MINIMUM_BACKUP_FILES = 5;
    public static final int DAYS_OF_BACKUPS_TO_KEEP = 14;

    public static void createConfigBackup() {
        Path configFolder = Path.of(OsConstants.CONFIG_DIRECTORY);
        Path backupFolder = Path.of(OsConstants.CONFIG_DIRECTORY + "/backup");
        try {
            log.info("Creating config backup.");
            zipFolder(configFolder, backupFolder);
            if (countFiles(backupFolder) >= MINIMUM_BACKUP_FILES) {
                log.info("Enough config backups available. Deleting old backups.");
                deleteOldFiles(backupFolder, DAYS_OF_BACKUPS_TO_KEEP);
            } else {
                log.info("Not enough config backups available. Not deleting old backups.");
            }
        } catch (Exception e) {
            log.error("Failed to backup current config.", e);
        }
    }

    private static void zipFolder(Path sourceFolderPath, Path zipPath) throws Exception {
        File backupFile = new File(zipPath.toFile().getAbsoluteFile() + "/backup." + DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss.SSS").format(LocalDateTime.now()) + ".zip");
        zipPath.toFile().mkdirs();
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(backupFile))) {
            Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<>() {
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (!file.startsWith(zipPath) && !isExcluded(file.getFileName().toString())) {
                        zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
                        log.debug("backup: " + file.getFileName());
                        Files.copy(file, zos);
                        zos.closeEntry();
                    }
                    return FileVisitResult.CONTINUE;
                }

                private boolean isExcluded(String fileName) {
                    return fileName.equals("lock") || fileName.equals("material-report.json") || fileName.endsWith(".tmp") || fileName.startsWith("support.");
                }
            });
        }
    }

    public static int countFiles(Path folderPath) throws IOException {
        return (int) Files.walk(folderPath)
                .filter(Files::isRegularFile)
                .count();
    }

    public static void deleteOldFiles(Path folderPath, int thresholdDays) {
        File folder = folderPath.toFile();
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : Arrays.stream(files).sorted(Comparator.comparingLong(BackupService::getFileAgeInMillis)).skip(5).toList()) {
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
