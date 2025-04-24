package nl.jixxed.eliteodysseymaterials.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileSyncService {

    private static final List<SyncItem> SYNC_ITEMS = new ArrayList<>();

    public static final String BROKERS_TRADERS_URL = "https://d3a17kxijdwucd.cloudfront.net/traders_brokers.zip";

    static {
        SYNC_ITEMS.add(new SyncItem(BROKERS_TRADERS_URL, Duration.ofDays(1), OsConstants.getConfigDirectory() + OsConstants.getOsSlash() + "traders_brokers.zip", FileSyncService::extractZipFiles));
    }

    private static void extractZipFiles(String zipFilePath) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            byte[] buffer = new byte[1024];
            while ((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();
                if (entryName.equals("traders.jsonl") || entryName.equals("brokers.jsonl")) {
                    File outputFile = new File(OsConstants.getConfigDirectory() + OsConstants.getOsSlash() + entryName);
                    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }

                    // Update respective services based on file name
                    if (entryName.equals("traders.jsonl")) {
                        MaterialTraderService.update(outputFile.getAbsolutePath());
                    } else if (entryName.equals("brokers.jsonl")) {
                        TechnologyBrokerService.update(outputFile.getAbsolutePath());
                    }
                }
                zis.closeEntry();
            }
        } catch (IOException e) {
            log.error("Failed to extract zip file", e);
        }
    }

    public static void init() {
        SYNC_ITEMS.forEach(syncItem -> {
            File targetFile = new File(syncItem.getTargetFile());
            targetFile.getParentFile().mkdirs(); // Create parent directories
            boolean shouldDownload = !targetFile.exists() ||
                    Duration.between(Instant.ofEpochMilli(targetFile.lastModified()), Instant.now()).compareTo(syncItem.getInterval()) > 0;

            if (shouldDownload) {
                try {
                    final URL url = URI.create(syncItem.getEndpoint()).toURL();
                    try (final ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream()); final FileOutputStream fileOutputStream = new FileOutputStream(syncItem.getTargetFile())) {
                        fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
                        syncItem.getFileConsumer().accept(syncItem.getTargetFile());
                    } catch (IOException e) {
                        log.error("failed to download file", e);
                    }
                } catch (MalformedURLException e) {
                    log.error("failed to download file", e);
                }
            }
        });
    }

    @AllArgsConstructor
    @Getter
    private static class SyncItem {
        private String endpoint;
        private Duration interval;
        private String targetFile;
        private Consumer<String> fileConsumer;
    }
}
