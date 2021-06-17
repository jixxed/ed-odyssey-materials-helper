package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ApplicationLayout extends AnchorPane {

    public ApplicationLayout(final Application application) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ApplicationLayout.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }

        final BottomBar bottomBar = new BottomBar();
        final ContentArea contentArea = new ContentArea(application);
        setAnchor(contentArea, 0.0, 25.0, 0.0, 0.0);
        setAnchor(bottomBar, null, 0.0, 0.0, 0.0);
        this.getChildren().addAll(contentArea, bottomBar);

    }


    private void setAnchor(final Node child, final Double topValue, final Double bottomValue, final Double leftValue, final Double rightValue) {
        AnchorPane.setTopAnchor(child, topValue);
        AnchorPane.setBottomAnchor(child, bottomValue);
        AnchorPane.setLeftAnchor(child, leftValue);
        AnchorPane.setRightAnchor(child, rightValue);
    }

}
