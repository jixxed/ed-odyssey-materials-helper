package nl.jixxed.eliteodysseymaterials;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Settings extends VBox {

    @FXML
    CheckBox checkBoxIrrelevant;
    @FXML
    CheckBox checkBoxUnlock;

    public Settings() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public CheckBox getCheckBoxIrrelevant() {
        return checkBoxIrrelevant;
    }

    public CheckBox getCheckBoxUnlock() {
        return checkBoxUnlock;
    }
}
