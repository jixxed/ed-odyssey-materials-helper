package nl.jixxed.eliteodysseymaterials.templates;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ApplicationLayout extends AnchorPane {
//    @FXML
//    VBox vbox;

    public ApplicationLayout() {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ApplicationLayout.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }

//    public VBox getVbox() {
//        return this.vbox;
//    }
}
