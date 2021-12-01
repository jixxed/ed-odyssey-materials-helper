package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import nl.jixxed.eliteodysseymaterials.helper.AnchorPaneHelper;
import nl.jixxed.eliteodysseymaterials.service.event.ApplicationLifeCycleEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

public class ApplicationLayout extends AnchorPane {
    private BottomBar bottomBar;
    private ContentArea contentArea;

    public ApplicationLayout(final Application application) {
        initComponents(application);
        initEventHandling();
    }

    private void initEventHandling() {
        EventService.addListener(this, ApplicationLifeCycleEvent.class, applicationLifeCycleEvent -> AnchorPaneHelper.setAnchor(this.contentArea, 0.0, this.bottomBar.getHeight(), 0.0, 0.0));
        this.bottomBar.heightProperty().addListener(observable -> AnchorPaneHelper.setAnchor(this.contentArea, 0.0, this.bottomBar.getHeight(), 0.0, 0.0));
    }

    private void initComponents(final Application application) {
        this.getStyleClass().add("app");
        this.bottomBar = new BottomBar();
        AnchorPaneHelper.setAnchor(this.bottomBar, null, 0.0, 0.0, 0.0);
        this.contentArea = new ContentArea(application);
        this.getChildren().addAll(this.contentArea, this.bottomBar);
    }
}
