package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.helper.AnchorPaneHelper;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

public class AROverlay extends AnchorPane implements Template {
    private final Stage stage;
    @Getter
    private DestroyableResizableImageView resizableImageView;

    public AROverlay(final Stage stage) {
        super();
        this.stage = stage;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.resizableImageView = ResizableImageViewBuilder.builder().build();
//        final AnchorPane anchorPane = new AnchorPane(this.resizableImageView);
        AnchorPaneHelper.setAnchor(this.resizableImageView, 0D, 0D, 0D, 0D);
        this.getChildren().add(this.resizableImageView);
    }

    @Override
    public void initEventHandling() {
    }
}
