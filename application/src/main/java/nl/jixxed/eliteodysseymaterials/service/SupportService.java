package nl.jixxed.eliteodysseymaterials.service;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;

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
public class SupportService {


    public static String createSupportPackage() {
        Path backupFolder = Path.of(OsConstants.CONFIG_DIRECTORY + "/backup");
        Path configFolder = Path.of(OsConstants.CONFIG_DIRECTORY);
        Path supportFolder = Path.of(OsConstants.CONFIG_DIRECTORY + "/support");
        Path journalFolder = Path.of(PreferencesService.getPreference(PreferenceConstants.JOURNAL_FOLDER, OsConstants.DEFAULT_WATCHED_FOLDER));
        try {
            log.info("Creating support package.");
            return zipFolder(configFolder, journalFolder, supportFolder,backupFolder);
        } catch (Exception e) {
            log.error("Failed to create support package.", e);
        }
        return "";
    }

    private static String zipFolder(Path configFolderPath,Path journalFolderPath, Path zipPath, Path backupFolderPath) throws Exception {
        File backupFile = new File(zipPath.toFile().getAbsoluteFile() + "/support." + DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss.SSS").format(LocalDateTime.now()) + ".zip");
        zipPath.toFile().mkdirs();
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(backupFile))) {
            Files.walkFileTree(configFolderPath, new SimpleFileVisitor<>() {
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (!file.startsWith(zipPath) &&!file.startsWith(backupFolderPath) && !isExcluded(file.getFileName().toString())) {
                        zos.putNextEntry(new ZipEntry(configFolderPath.relativize(file).toString()));
                        log.debug("support: " + file.getFileName());
                        Files.copy(file, zos);
                        zos.closeEntry();
                    }
                    return FileVisitResult.CONTINUE;
                }

                private boolean isExcluded(String fileName) {
                    return fileName.equals("lock") || fileName.equals("material-report.json") || fileName.endsWith(".tmp") || fileName.equals("capi.json");
                }
            });

            Files.walkFileTree(journalFolderPath, new SimpleFileVisitor<>() {
                List<String> files = List.of(
                    AppConstants.CARGO_FILE,
                    AppConstants.BACKPACK_FILE,
                    AppConstants.FCMATERIALS_FILE,
                    AppConstants.STATUS_FILE,
                    AppConstants.SHIPLOCKER_FILE,
                    AppConstants.SHIPYARD_FILE,
                    AppConstants.OUTFITTING_FILE,
                    AppConstants.MARKET_FILE,
                    AppConstants.NAVROUTE_FILE,
                    AppConstants.MODULESINFO_FILE,
                    ApplicationState.getInstance().getWatchedFile()
                );
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (files.contains(file.getFileName().toString())) {
                        zos.putNextEntry(new ZipEntry("journal/" + journalFolderPath.relativize(file).toString()));
                        log.debug("support: " + file.getFileName());
                        Files.copy(file, zos);
                        zos.closeEntry();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        return backupFile.getAbsolutePath();
    }
}
