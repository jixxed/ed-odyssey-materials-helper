package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.beans.property.BooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.Getter;

@Getter
public class DestroyableResizableImageView extends Pane implements DestroyableComponent {


    private final ImageView iv;

    public DestroyableResizableImageView() {
        this.iv = new ImageView();
        this.iv.setSmooth(true);
        getChildren().add(this.iv);
    }

    public final void setImage(final Image image) {
        this.iv.setImage(image);
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