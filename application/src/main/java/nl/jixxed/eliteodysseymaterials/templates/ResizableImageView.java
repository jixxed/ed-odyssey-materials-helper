package nl.jixxed.eliteodysseymaterials.templates;

import javafx.beans.property.BooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ResizableImageView extends Pane {

    private final ImageView iv;

    public ResizableImageView() {
        this.iv = new ImageView();
        getChildren().add(this.iv);
    }

    public final void setImage(final Image image) {
        this.iv.setImage(image);
//        setPrefSize(image.getWidth(), image.getHeight());
        this.iv.fitWidthProperty().bind(widthProperty());
        this.iv.fitHeightProperty().bind(heightProperty());
    }

    public final void setPreserveRatio(final boolean preserveRatio) {
        this.iv.setPreserveRatio(preserveRatio);
    }

    public final boolean isPreserveRatio() {
        return this.iv.isPreserveRatio();
    }

    public final BooleanProperty preserveRatioProperty() {
        return this.iv.preserveRatioProperty();
    }

    public final Image getImage() {
        return this.iv.getImage();
    }
}