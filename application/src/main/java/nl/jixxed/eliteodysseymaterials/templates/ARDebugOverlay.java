package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.helper.AnchorPaneHelper;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

public class ARDebugOverlay extends VBox implements Template {
    private final Stage stage;
    @Getter
    private DestroyableResizableImageView resizableImageView1;
    @Getter
    private DestroyableResizableImageView resizableImageView2;
    @Getter
    private DestroyableResizableImageView resizableImageView3;
    @Getter
    private DestroyableResizableImageView resizableImageView4;
    @Getter
    private DestroyableResizableImageView resizableImageView5;
    @Getter
    private DestroyableResizableImageView resizableImageView6;

    public ARDebugOverlay(final Stage stage) {
        super();
        this.stage = stage;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.resizableImageView1 = ResizableImageViewBuilder.builder().build();
        this.resizableImageView2 = ResizableImageViewBuilder.builder().build();
        this.resizableImageView3 = ResizableImageViewBuilder.builder().build();
        this.resizableImageView4 = ResizableImageViewBuilder.builder().build();
        this.resizableImageView5 = ResizableImageViewBuilder.builder().build();
        this.resizableImageView6 = ResizableImageViewBuilder.builder().build();
//        final AnchorPane anchorPane = new AnchorPane(this.resizableImageView);
        AnchorPaneHelper.setAnchor(this.resizableImageView1, 0D, 0D, 0D, 0D);
        AnchorPaneHelper.setAnchor(this.resizableImageView2, 0D, 0D, 0D, 0D);
        AnchorPaneHelper.setAnchor(this.resizableImageView3, 0D, 0D, 0D, 0D);
        AnchorPaneHelper.setAnchor(this.resizableImageView4, 0D, 0D, 0D, 0D);
        AnchorPaneHelper.setAnchor(this.resizableImageView5, 0D, 0D, 0D, 0D);
        AnchorPaneHelper.setAnchor(this.resizableImageView6, 0D, 0D, 0D, 0D);
        this.getChildren().addAll(
                new AnchorPane(this.resizableImageView1),
                new AnchorPane(this.resizableImageView2),
                new AnchorPane(this.resizableImageView3),
                new AnchorPane(this.resizableImageView4),
                new AnchorPane(this.resizableImageView5),
                new AnchorPane(this.resizableImageView6)
        );
    }

    @Override
    public void initEventHandling() {
    }

    public DestroyableResizableImageView getResizableImageView(final int index) {
        return switch (index) {
            case 1:
                yield this.resizableImageView1;
            case 2:
                yield this.resizableImageView2;
            case 3:
                yield this.resizableImageView3;
            case 4:
                yield this.resizableImageView4;
            case 5:
                yield this.resizableImageView5;
            case 6:
                yield this.resizableImageView6;
            default:
                throw new IllegalStateException("Unexpected value: " + index);
        };
    }
}
