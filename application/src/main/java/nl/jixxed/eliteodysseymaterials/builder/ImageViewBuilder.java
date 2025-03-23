package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableImageView;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageViewBuilder extends AbstractNodeBuilder<ImageViewBuilder> {
    private Image image;
    private boolean preserveRatio = true;
    private ObservableValue<Number> fitHeightBinding;
    private ObservableValue<Number> fitWidthBinding;

    public static ImageViewBuilder builder() {
        return new ImageViewBuilder();
    }

    public ImageViewBuilder withPreserveRatio(final boolean preserveRatio) {
        this.preserveRatio = preserveRatio;
        return this;
    }

    public ImageViewBuilder withImage(final Image image) {
        this.image = image;
        return this;
    }

    public ImageViewBuilder withImage(final String imageResource) {
        this.image = ImageService.getImage(imageResource);
        return this;
    }

    public ImageViewBuilder withFitHeightBinding(final ObservableValue<Number> fitHeightBinding) {
        this.fitHeightBinding = fitHeightBinding;
        return this;
    }

    public ImageViewBuilder withFitWidthBinding(final ObservableValue<Number> fitWidthBinding) {
        this.fitWidthBinding = fitWidthBinding;
        return this;
    }

    public DestroyableImageView build() {
        final DestroyableImageView imageView = new DestroyableImageView();
        super.build(imageView);

        imageView.setImage(this.image);
        imageView.setPreserveRatio(this.preserveRatio);
        if (this.fitHeightBinding != null) {
            imageView.addBinding(imageView.fitHeightProperty(), this.fitHeightBinding);
        }
//        if (this.fitWidthBinding != null) {
//            imageView.addBinding(imageView.fitWidthProperty(), this.fitWidthBinding);
//        }
        return imageView;
    }
}
