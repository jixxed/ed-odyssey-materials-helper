package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CountingInputStream;
import javafx.application.Platform;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileProcessor {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static long position = 0L;

    public static synchronized void resetAndProcessJournal(final File file) {
        position = 0L;
        processJournal(file);
    }

    public static synchronized void processJournal(final File file) {
        try (final CountingInputStream is = new CountingInputStream(Files.newInputStream(Paths.get(file.toURI()), StandardOpenOption.READ))) {
            if (file.length() >= position) {
                // Skip over already processed bytes.
                is.skip(position);
            }
            final InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            final BufferedReader lineReader = new BufferedReader(reader);

            // Process all lines.
            String line;
            while ((line = lineReader.readLine()) != null) {
                final String finalLine = line;
                //try to read line as json, if exception occurs we get JsonProcessingException and can try to read again later
                OBJECT_MAPPER.readTree(finalLine);
                position = is.getCount();
                Platform.runLater(() -> MessageHandler.handleMessage(finalLine, file));
            }
        } catch (final JsonProcessingException e) {
            log.error("Read error", e);
        } catch (final IOException e) {
            log.error("Error processing journal", e);
        }
    }

    public static synchronized void processShipLockerBackpack(final File file) {
        try {
            final String message = Files.readString(file.toPath());
            Platform.runLater(() -> MessageHandler.handleMessage(message, file));
        } catch (final IOException e) {
            log.error("Error processing ShipLocker or Backpack", e);
        }
    }


}
