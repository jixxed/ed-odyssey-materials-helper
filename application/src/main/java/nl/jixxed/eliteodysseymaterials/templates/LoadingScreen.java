package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.enums.LoadingStage;
import nl.jixxed.eliteodysseymaterials.service.event.EventProcessedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LoadingEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

@Slf4j
public class LoadingScreen extends DestroyableAnchorPane implements DestroyableEventTemplate {
    private DestroyableProgressBar progressBar;
    private DestroyableLabel text;

    public LoadingScreen() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("loading-screen");
        final DestroyableLabel loading = LabelBuilder.builder()
                .withStyleClass("text-title")
                .withText("loading.screen.text")
                .build();
        initProgressBar();
        text = LabelBuilder.builder()
                .withStyleClass("text-status")
                .withNonLocalizedText("Loading journal files...")
                .build();
        final DestroyableHBox hBox = BoxBuilder.builder()
                .withNodes(new GrowingRegion(), BoxBuilder.builder()
                        .withNodes(new GrowingRegion(), loading, progressBar, text, new GrowingRegion()).buildVBox(), new GrowingRegion()).buildHBox();
        AnchorPane.setRightAnchor(hBox, 0D);
        AnchorPane.setBottomAnchor(hBox, 0D);
        AnchorPane.setTopAnchor(hBox, 0D);
        AnchorPane.setLeftAnchor(hBox, 0D);
        this.getNodes().add(hBox);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, EventProcessedEvent.class, event -> getSetProgress(event.getCurrent(), event.getTotal())));
        register(EventService.addListener(true, this, LoadingEvent.class, event -> setState(event.getLoadingStage())));

    }

    private void setState(LoadingStage loadingStage) {
        switch (loadingStage) {
            case EVENTS -> {
                progressBar.setProgress(0);
                text.setText("Loading journal files...");
            }
            case UI -> {
                text.setText("Loading UI...");
            }
        }
    }

    private void getSetProgress(double current, double total) {
//        log.debug("updating loading bar " + current + "/" + total);
        progressBar.setProgress(current / total);
    }

    private void initProgressBar() {
        progressBar = new DestroyableProgressBar();
        progressBar.getStyleClass().add("loadingbar");
    }
}
