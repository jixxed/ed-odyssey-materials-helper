package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import nl.jixxed.eliteodysseymaterials.service.event.ApplicationLifeCycleEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

public class ApplicationLayout extends AnchorPane {
    private final BottomBar bottomBar;
    private final ContentArea contentArea;

    public ApplicationLayout(final Application application) {
        this.getStyleClass().add("app");
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
