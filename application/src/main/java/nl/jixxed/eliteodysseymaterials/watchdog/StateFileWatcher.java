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

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
public class StateFileWatcher {
    private Optional<File> watchedFile = Optional.empty();
    private final FileWatcher fileWatcher;

    public StateFileWatcher(final File folder, final Consumer<File> fileProcessor, final String filename) {
        findFile(folder, filename);
        this.watchedFile.ifPresent(fileProcessor);
        this.fileWatcher = new FileWatcher(true)
                .withListener(new FileListener() {
                    @Override
                    public void onCreated(final FileEvent event) {
                        handleFile(event, fileProcessor);
                    }

                    @Override
                    public void onModified(final FileEvent event) {
                        handleFile(event, fileProcessor);
                    }

                    private void handleFile(final FileEvent event, final Consumer<File> fileProcessor) {
                        final File file = event.getFile();
                        if (file.isFile() && file.getName().equals(filename)) {
                            StateFileWatcher.this.watchedFile = Optional.of(file);
                            fileProcessor.accept(file);
                        }

                    }

                    @Override
                    public void onDeleted(final FileEvent event) {

                    }
                }).watch(folder);
    }

    private void findFile(final File folder, final String filename) {
        try {
            this.watchedFile = FileService.listFiles(folder, true).stream()
                    .filter(file -> file.getName().equals(filename))
                    .findFirst();
            log.info("Registered watched file: " + this.watchedFile.map(File::getName).orElse(filename + " not found"));
        } catch (final NullPointerException ex) {
            log.error("Failed to Registered watched file at " + folder.getAbsolutePath());
        }
    }

    public void stop() {
        this.fileWatcher.stop();
    }
}
