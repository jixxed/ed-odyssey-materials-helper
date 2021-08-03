package nl.jixxed.eliteodysseymaterials.templates;

import javafx.beans.property.BooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ResizableImageView extends Pane {

    private ImageView iv;

    ResizableImageView(final String styleClass) {
        this.getStyleClass().add(styleClass);
    }

    public final void setImage(final Image image) {
        this.iv = new ImageView(image);
        setPrefSize(image.getWidth(), image.getHeight());
        this.iv.fitWidthProperty().bind(widthProperty());
        this.iv.fitHeightProperty().bind(heightProperty());
        this.iv.setPreserveRatio(true);
        getChildren().add(this.iv);
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