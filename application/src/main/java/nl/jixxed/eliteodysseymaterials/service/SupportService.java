package nl.jixxed.eliteodysseymaterials.service;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import org.slf4j.LoggerFactory;

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
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class SupportService {

    private static final int DAYS_OF_SUPPORT_PACKAGES_TO_KEEP = 2;

    public static String createSupportPackage() {
        return createSupportPackage(null);
    }

    public static String createSupportPackage(String name) {
        final Path zipPath = Path.of(OsConstants.getConfigDirectory() + "/support");
        List<Path> excludedPaths = List.of(
                Path.of(OsConstants.getConfigDirectory() + "/tesseract"),
                Path.of(OsConstants.getConfigDirectory() + "/backup"),
                zipPath
        );
        Path configFolder = Path.of(OsConstants.getConfigDirectory());
        Path journalFolder = Path.of(PreferencesService.getPreference(PreferenceConstants.JOURNAL_FOLDER, OsConstants.getDefaultWatchedFolder()));
        try {
            log.info("Creating support package.");
            return zipFolder(configFolder, journalFolder, zipPath, excludedPaths, name);
        } catch (Exception e) {
            log.error("Failed to create support package.", e);
        }
        FileService.deleteOldFiles(zipPath, DAYS_OF_SUPPORT_PACKAGES_TO_KEEP);
        return "";
    }

    private static String zipFolder(Path configFolderPath, Path journalFolderPath, Path zipPath, List<Path> excludedPaths, String name) throws Exception {
        final String filename = name != null ? name : "support." + DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss.SSS").format(LocalDateTime.now());
        File backupFile = new File(zipPath.toFile().getAbsoluteFile() + "/" + filename + ".zip");
        Files.deleteIfExists(backupFile.toPath());
        zipPath.toFile().mkdirs();
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(backupFile))) {
            Files.walkFileTree(configFolderPath, new SimpleFileVisitor<>() {
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (excludedPaths.stream().noneMatch(file::startsWith) && !isExcluded(file.getFileName().toString())) {
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
            File logFile = findLogFile();
            if (logFile != null && logFile.exists()) {
                zos.putNextEntry(new ZipEntry("log/edomh.log"));
                log.debug("support: " + logFile.getName());
                Files.copy(Path.of(logFile.getAbsolutePath()), zos);
                zos.closeEntry();
            }
        }
        return backupFile.getAbsolutePath();
    }

    private static File findLogFile() {
        File clientLogFile;
        RollingFileAppender<?> fileAppender = null;
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        for (Logger logger : context.getLoggerList()) {
            for (Iterator<Appender<ILoggingEvent>> index = logger.iteratorForAppenders();
                 index.hasNext(); ) {
                Object enumElement = index.next();
                if (enumElement instanceof RollingFileAppender<?>) {
                    fileAppender = (RollingFileAppender<?>) enumElement;
                }
            }
        }
        if (fileAppender != null) {
            clientLogFile = new File(fileAppender.getFile());
        } else {
            clientLogFile = null;
        }
        return clientLogFile;
    }
}
