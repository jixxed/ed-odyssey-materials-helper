package nl.jixxed.eliteodysseymaterials.templates;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ApplicationLayout extends AnchorPane {
    private final ContentArea contentArea;
    private final BottomBar bottomBar;

    public ApplicationLayout(final Application application) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ApplicationLayout.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }

        this.bottomBar = new BottomBar();
        this.contentArea = new ContentArea(application);
        setAnchor(this.contentArea, 0.0, 25.0, 0.0, 0.0);
        setAnchor(this.bottomBar, null, 0.0, 0.0, 0.0);
        this.getChildren().addAll(this.contentArea, this.bottomBar);

        this.getStylesheets().add(getClass().getResource("/nl/jixxed/eliteodysseymaterials/style/style.css").toExternalForm());
    }


    private void setAnchor(final Node child, final Double topValue, final Double bottomValue, final Double leftValue, final Double rightValue) {
        AnchorPane.setTopAnchor(child, topValue);
        AnchorPane.setBottomAnchor(child, bottomValue);
        AnchorPane.setLeftAnchor(child, leftValue);
        AnchorPane.setRightAnchor(child, rightValue);
    }

    public void setWatchedFile(final String watchedFile) {
        Platform.runLater(() -> {
            this.bottomBar.setWatchedFileLabel("Watching: " + watchedFile);
        });
    }

    public void updateLastTimeStamp(final JsonNode journalMessage) {
        Platform.runLater(() -> {
            this.bottomBar.setLastTimeStampLabel("Latest observed relevant message: " + journalMessage.get("timestamp").asText() + " (" + journalMessage.get("event").asText() + ")");
        });
    }

    public void updateGui() {
        Platform.runLater(this.contentArea::updateGui);
    }
}
