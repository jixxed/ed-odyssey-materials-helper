package nl.jixxed.eliteodysseymaterials;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import nl.jixxed.eliteodysseymaterials.parser.FileProcessor;
import nl.jixxed.eliteodysseymaterials.templates.ApplicationLayout;
import nl.jixxed.eliteodysseymaterials.watchdog.GameStateWatcher;
import nl.jixxed.eliteodysseymaterials.watchdog.JournalWatcher;

import java.io.File;

public class Main extends Application {
    private final ApplicationLayout applicationLayout = new ApplicationLayout(this);
    private final GameStateWatcher gameStateWatcher = new GameStateWatcher();
    private final JournalWatcher journalWatcher = new JournalWatcher();

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("ED Odyssey Materials Helper");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/rocket.png")));

        final String userprofile = System.getenv("USERPROFILE");
        final File watchedFolder = new File(userprofile + "\\Saved Games\\Frontier Developments\\Elite Dangerous");

        this.applicationLayout.setWatchedFile("Watching: None - No Odyssey journals found at " + watchedFolder.getAbsolutePath());

        this.gameStateWatcher.watch(watchedFolder, this::processShipLockerBackPack, "ShipLocker.json");
        this.gameStateWatcher.watch(watchedFolder, this::processShipLockerBackPack, "Backpack.json");

        this.journalWatcher.watch(watchedFolder, this::processJournal, FileProcessor::resetAndProcessJournal);

        primaryStage.setScene(new Scene(this.applicationLayout));
        primaryStage.show();
    }

    protected void processJournal(final File file) {
        this.applicationLayout.setWatchedFile("Watching: " + file.getAbsoluteFile());
        final JsonNode message = FileProcessor.processJournal(file);
        this.applicationLayout.updateLastTimeStamp(message);
        this.applicationLayout.updateGui();
    }

    protected void processShipLockerBackPack(final File file) {
        this.applicationLayout.setWatchedFile("Watching: " + file.getAbsoluteFile());
        final JsonNode message = FileProcessor.processShipLockerBackPack(file);
        this.applicationLayout.updateLastTimeStamp(message);
        this.applicationLayout.updateGui();
    }


    public static void main(final String[] args) {
        launch(args);
    }
}
