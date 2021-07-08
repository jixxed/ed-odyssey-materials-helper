package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import nl.jixxed.eliteodysseymaterials.service.event.ApplicationLifeCycleEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.io.IOException;

public class ApplicationLayout extends AnchorPane {
    final BottomBar bottomBar;
    final ContentArea contentArea;

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

        EventService.addListener(ApplicationLifeCycleEvent.class, applicationLifeCycleEvent -> setAnchor(this.contentArea, 0.0, this.bottomBar.getHeight(), 0.0, 0.0));
        this.bottomBar.heightProperty().addListener(observable -> setAnchor(this.contentArea, 0.0, this.bottomBar.getHeight(), 0.0, 0.0));
        setAnchor(this.bottomBar, null, 0.0, 0.0, 0.0);
        this.getChildren().addAll(this.contentArea, this.bottomBar);

    }


    private void setAnchor(final Node child, final Double topValue, final Double bottomValue, final Double leftValue, final Double rightValue) {
        AnchorPane.setTopAnchor(child, topValue);
        AnchorPane.setBottomAnchor(child, bottomValue);
        AnchorPane.setLeftAnchor(child, leftValue);
        AnchorPane.setRightAnchor(child, rightValue);
    }

}
