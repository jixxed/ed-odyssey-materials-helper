package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.layout.AnchorPane;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.service.event.EventProcessedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

public class LoadingScreen extends DestroyableAnchorPane implements DestroyableEventTemplate {
    private static DestroyableProgressBar progressBar;

    public LoadingScreen() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("loading-screen");
        final DestroyableLabel loading = LabelBuilder.builder()
                .withStyleClass("loading-screen-text")
                .withNonLocalizedText("LOADING")
                .build();
        final DestroyableHBox hBox = BoxBuilder.builder()
                .withNodes(new GrowingRegion(), BoxBuilder.builder()
                        .withNodes(new GrowingRegion(), loading, getProgressBar(), new GrowingRegion()).buildVBox(), new GrowingRegion()).buildHBox();
        AnchorPane.setRightAnchor(hBox, 0D);
        AnchorPane.setBottomAnchor(hBox, 0D);
        AnchorPane.setTopAnchor(hBox, 0D);
        AnchorPane.setLeftAnchor(hBox, 0D);
        this.getNodes().add(hBox);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, EventProcessedEvent.class, event -> progressBar.setProgress((double) event.getCurrent() / (double) event.getTotal())));
    }

    private static DestroyableProgressBar getProgressBar() {
        progressBar = new DestroyableProgressBar();
        progressBar.getStyleClass().add("loading-screen-progressbar");
        return progressBar;
    }
}
