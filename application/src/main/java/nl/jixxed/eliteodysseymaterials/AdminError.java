package nl.jixxed.eliteodysseymaterials;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class AdminError extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Elevated privileges");
        alert.setHeaderText("Elevated privileges have been detected.");
        alert.setContentText("This app should not be started with elevated privileges.\n" +
                "Please restart the app without elevated privileges.");
        alert.showAndWait();
        System.exit(1);
    }

    static void launchFx(final String[] args) {
        launch(args);
    }
}
