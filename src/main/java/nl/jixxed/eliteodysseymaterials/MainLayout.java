package nl.jixxed.eliteodysseymaterials;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainLayout  extends VBox {
    @FXML
    Label label;
    @FXML
    CheckBox checkBoxIrrelevant;
    @FXML
    CheckBox checkBoxUnlock;
    public MainLayout() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainLayout.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    public void setLabel(String text){
        this.label.setText(text);
    }

    public CheckBox getCheckBoxIrrelevant() {
        return checkBoxIrrelevant;
    }

    public CheckBox getCheckBoxUnlock() {
        return checkBoxUnlock;
    }
}
