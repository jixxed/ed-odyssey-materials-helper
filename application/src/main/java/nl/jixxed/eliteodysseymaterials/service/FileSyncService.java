package nl.jixxed.eliteodysseymaterials.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;

import java.io.File;
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

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileSyncService {

    private static final List<SyncItem> SYNC_ITEMS = new ArrayList<>();

    public static final String BROKERS_URL = "https://raw.githubusercontent.com/jixxed/ed-odyssey-materials-helper/refs/heads/master/application/src/main/resources/technologybroker/brokers.jsonl";
    public static final String TRADERS_URL = "https://raw.githubusercontent.com/jixxed/ed-odyssey-materials-helper/refs/heads/master/application/src/main/resources/materialtrader/traders.jsonl";

    static {
        SYNC_ITEMS.add(new SyncItem(BROKERS_URL, Duration.ofDays(1), OsConstants.CONFIG_DIRECTORY + OsConstants.OS_SLASH + "brokers.jsonl", TechnologyBrokerService::update));
        SYNC_ITEMS.add(new SyncItem(TRADERS_URL, Duration.ofDays(1), OsConstants.CONFIG_DIRECTORY + OsConstants.OS_SLASH + "traders.jsonl", MaterialTraderService::update));
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
