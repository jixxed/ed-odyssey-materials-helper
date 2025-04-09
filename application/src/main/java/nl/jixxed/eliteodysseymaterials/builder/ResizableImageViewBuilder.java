package nl.jixxed.eliteodysseymaterials.builder;

import javafx.scene.image.Image;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResizableImageViewBuilder extends AbstractPaneBuilder<ResizableImageViewBuilder> {
    private Image image;
    private boolean preserveRatio = true;

    public static ResizableImageViewBuilder builder() {
        return new ResizableImageViewBuilder();
    }

    public ResizableImageViewBuilder withImage(final Image image) {
        this.image = image;
        return this;
    }

    public ResizableImageViewBuilder withImage(final String imageResource) {
        this.image = ImageService.getImage(imageResource);
        return this;
    }

    public ResizableImageViewBuilder withPreserveRatio(final boolean preserveRatio) {
        this.preserveRatio = preserveRatio;
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableResizableImageView build() {
        final DestroyableResizableImageView imageView = new DestroyableResizableImageView();
        super.build(imageView);
        imageView.setImage(this.image);
        imageView.setPreserveRatio(this.preserveRatio);
        return imageView;

    }

}
