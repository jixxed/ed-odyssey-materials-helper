package nl.jixxed.eliteodysseymaterials.builder;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageViewBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private Image image;

    public static ImageViewBuilder builder() {
        return new ImageViewBuilder();
    }

    public ImageViewBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public ImageViewBuilder withImage(final Image image) {
        this.image = image;
        return this;
    }

    public ImageViewBuilder withImage(final String imageResource) {
        this.image = new Image(getClass().getResourceAsStream(imageResource));
        return this;
    }

    public ImageViewBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public ImageView build() {
        final ImageView imageView = new ImageView();
        imageView.getStyleClass().addAll(this.styleClasses);
        imageView.setImage(this.image);
        return imageView;
    }
}
