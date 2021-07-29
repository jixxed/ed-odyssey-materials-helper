package nl.jixxed.eliteodysseymaterials.parser;

import javafx.application.Platform;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileProcessor {

    private static int lineNumber = 0;

    public static synchronized void resetAndProcessJournal(final File file) {
        Platform.runLater(() -> {
            lineNumber = 0;
            processJournal(file);
        });
    }

    public static synchronized void processJournal(final File file) {
        Platform.runLater(() -> {
            try (final Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
                int cursor = 0;
                while (scanner.hasNextLine()) {
                    final String line = scanner.nextLine();
                    cursor++;
                    if (line.isBlank()) {
                        break;
                    }
                    if (cursor > lineNumber) {
                        lineNumber++;
                        MessageHandler.handleMessage(line, file);
                    }
                }
            } catch (final IOException e) {
                log.error("Error processing Journal", e);
            }
        });
    }

    public static synchronized void processShipLockerBackpack(final File file) {
        Platform.runLater(() -> {
            try {
                final String message = Files.readString(file.toPath());
                MessageHandler.handleMessage(message, file);
            } catch (final IOException e) {
                log.error("Error processing ShipLocker or Backpack", e);
            }
        });
    }


}
