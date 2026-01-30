package nl.jixxed.eliteodysseymaterials.service;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class BackupService {

    public static final int MINIMUM_BACKUP_FILES = 5;
    public static final int DAYS_OF_BACKUPS_TO_KEEP = 14;

    public static void createConfigBackup() {
        Path configFolder = Path.of(OsConstants.getConfigDirectory());
        Path backupFolder = Path.of(OsConstants.getConfigDirectory() + "/backup");
        try {
            log.info("Creating config backup.");
            zipFolder(configFolder, backupFolder);
            if (FileService.countFiles(backupFolder) >= MINIMUM_BACKUP_FILES) {
                log.info("Enough config backups available. Deleting old backups.");
                FileService.deleteOldFiles(backupFolder, DAYS_OF_BACKUPS_TO_KEEP);
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

                public static final List<String> EXCLUSIONS = List.of(
                        "material-report.json",
                        "lock",
                        "brokers.jsonl",
                        "traders.jsonl",
                        "traders_brokers.zip",
                        "commodities.json"
                );

                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (!file.startsWith(zipPath) && !file.startsWith(Path.of(OsConstants.getConfigDirectory() + "/tesseract")) && !isExcluded(file.getFileName().toString())) {
                        zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
                        log.debug("backup: " + file.getFileName());
                        Files.copy(file, zos);
                        zos.closeEntry();
                    }
                    return FileVisitResult.CONTINUE;
                }

                private boolean isExcluded(String fileName) {
                    return EXCLUSIONS.contains(fileName) || fileName.endsWith(".tmp") || fileName.startsWith("support.");
                }
            });
        }
    }

}
