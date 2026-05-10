/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.watchdog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.application.Platform;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;

import java.io.File;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
public class JournalWatcher {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Optional<File> currentlyWatchedFile = Optional.empty();
    @Getter
    private File watchedFolder;
    private FileWatcher fileWatcher;
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final PublishSubject<File> modifyEventSubject = PublishSubject.create();
    private Disposable subscription;

    public JournalWatcher() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public void watch(final File folder, final Consumer<File> fileModifiedProcessor, final Consumer<File> fileSwitchedProcessor) {
        Observable<File> debouncedModifyEvents = modifyEventSubject
                .debounce(100, TimeUnit.MILLISECONDS);
        subscription = debouncedModifyEvents.subscribe(fileModifiedProcessor::accept, throwable ->
                log.error("Error processing file", throwable));
        try {
            this.watchedFolder = folder;
            if (!folder.exists()) {
                EventService.publish(new JournalInitEvent(true));
                return;
            }
            findLatestFile(folder);
            if (this.currentlyWatchedFile.isEmpty()) {
                EventService.publish(new JournalInitEvent(true));
                return;
            }
            this.currentlyWatchedFile.ifPresent(fileSwitchedProcessor);
            this.fileWatcher = new FileWatcher(true).withListener(new FileAdapter() {
                @Override
                public void onModified(final FileEvent event) {
                    final File file = event.getFile();
                    final String currentFilePath = getCurrentFilePath();
                    final boolean isSameFile = currentFilePath.equals(file.getAbsolutePath());
                    if (isSameFile || isValidOdysseyJournal(file)) {
                        if (isSameFile) {
                            fileModifiedProcessor.accept(file);
                        } else if (isNewerJournal(file)) {
                            setCurrentlyWatchedFile(file);
                            fileSwitchedProcessor.accept(file);
                            log.info("Switched to journal: " + file.getAbsolutePath());
                        } else {
                            log.info("Rejected journal: " + file.getAbsolutePath());
                        }
                    }
                }

                private boolean isValidOdysseyJournal(final File file) {
                    return file.isFile()
                            && file.getName().startsWith(AppConstants.JOURNAL_FILE_PREFIX)
                            && file.getName().endsWith(AppConstants.JOURNAL_FILE_SUFFIX)
                            && JournalUtils.isNewerThanTwoYears(file)
                            && JournalUtils.hasFileHeader(file)
                            && JournalUtils.hasCommanderHeader(file)
                            && JournalUtils.isSelectedCommander(file);
                }
            }).watch(folder);
        } catch (Exception ex) {
            log.error("failed to initialize journal", ex);
            Platform.runLater(() -> {
                NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.journal.init.error.title"), LocaleService.LocaleString.of("notification.journal.init.error.text"));
            });
            EventService.publish(new JournalInitEvent(true));
        }
//        });
    }

    private synchronized String getCurrentFilePath() {
        return this.currentlyWatchedFile.map(File::getAbsolutePath).orElse("");
    }

    private synchronized void setCurrentlyWatchedFile(final File file) {
        this.currentlyWatchedFile = Optional.of(file);
    }

    private void findLatestFile(final File folder) {
        try {
            this.currentlyWatchedFile = FileService.listFiles(folder, true).stream()
                    .filter(file -> file.getName().startsWith(AppConstants.JOURNAL_FILE_PREFIX))
                    .filter(file -> file.getName().endsWith(AppConstants.JOURNAL_FILE_SUFFIX))
                    .filter(JournalUtils::isNewerThanTwoYears)
                    .filter(JournalUtils::hasFileHeader)
                    .filter(JournalUtils::hasCommanderHeader)
                    .filter(JournalUtils::isSelectedCommander)
                    .max(Comparator.comparingLong(JournalUtils::getFileTimestamp));
            log.info("Registered watched file: " + this.currentlyWatchedFile.map(File::getName).orElse("No file"));
        } catch (final NullPointerException ex) {
            log.error("Failed to Registered watched file at " + folder.getAbsolutePath(), ex);
        }
    }


    private synchronized boolean isNewerJournal(final File file) {
        final long fileTimestamp = JournalUtils.getFileTimestamp(file);
        final long currentFileTimestamp = this.currentlyWatchedFile.map(JournalUtils::getFileTimestamp).orElse(0L);
        return fileTimestamp > currentFileTimestamp;
    }




    public void stop() {
        if (this.fileWatcher != null) {
            this.fileWatcher.stop();
        }
        if (this.subscription != null) {
            subscription.dispose();
        }
    }

}
