package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.beans.property.BooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import lombok.Getter;

@Getter
public class DestroyableResizableImageView extends Pane implements DestroyableParent {
    ObservableListOverride override = new ObservableListOverride(DestroyableResizableImageView.this, super.getChildren());

    @Override
    public ObservableListOverride getNodes() {
        return override;
    }

    private final DestroyableImageView iv;

    public DestroyableResizableImageView() {
        this.iv = new DestroyableImageView();
        this.iv.setSmooth(true);
        getNodes().add(this.iv);
    }

    public final void setImage(final Image image) {
        this.iv.setImage(image);
        this.iv.addBinding(this.iv.fitWidthProperty(),widthProperty());
        this.iv.addBinding(this.iv.fitHeightProperty(),heightProperty());
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