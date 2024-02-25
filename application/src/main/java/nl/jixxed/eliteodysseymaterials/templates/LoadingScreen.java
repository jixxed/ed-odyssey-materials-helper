package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventProcessedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

import java.util.ArrayList;
import java.util.List;

public class LoadingScreen extends AnchorPane implements Template {
    private static ProgressBar progressBar;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    public LoadingScreen() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("loading-screen");
        final DestroyableLabel loading = LabelBuilder.builder().withStyleClass("loading-screen-text").withNonLocalizedText("LOADING").build();
        final HBox hBox = BoxBuilder.builder().withNodes(new GrowingRegion(), BoxBuilder.builder().withNodes(new GrowingRegion(), loading, getProgressBar(), new GrowingRegion()).buildVBox(), new GrowingRegion()).buildHBox();
        AnchorPane.setRightAnchor(hBox,0D);
        AnchorPane.setBottomAnchor(hBox,0D);
        AnchorPane.setTopAnchor(hBox,0D);
        AnchorPane.setLeftAnchor(hBox,0D);
        this.getChildren().add(hBox);
    }

    @Override
    public void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, EventProcessedEvent.class, event -> {
            progressBar.setProgress((double) event.getCurrent() / (double) event.getTotal());
        }));
    }

    private static ProgressBar getProgressBar() {
        progressBar = new ProgressBar();
        progressBar.getStyleClass().add("loading-screen-progressbar");
        return progressBar;
    }
}
