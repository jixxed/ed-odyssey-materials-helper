package nl.jixxed.eliteodysseymaterials.builder;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.ResizableImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResizableImageViewBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private Image image;
    private boolean preserveRatio = true;
    private EventHandler<? super MouseEvent> onMouseClicked;

    public static ResizableImageViewBuilder builder() {
        return new ResizableImageViewBuilder();
    }

    public ResizableImageViewBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public ResizableImageViewBuilder withImage(final Image image) {
        this.image = image;
        return this;
    }

    public ResizableImageViewBuilder withImage(final String imageResource) {
        this.image = new Image(getClass().getResourceAsStream(imageResource));
        return this;
    }

    public ResizableImageViewBuilder withPreserveRatio(final boolean preserveRatio) {
        this.preserveRatio = preserveRatio;
        return this;
    }

    public ResizableImageViewBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public ResizableImageViewBuilder withOnMouseClicked(final EventHandler<? super MouseEvent> onMouseClicked) {
        this.onMouseClicked = onMouseClicked;
        return this;
    }

    public ResizableImageView build() {
        final ResizableImageView imageView = new ResizableImageView();
        imageView.getStyleClass().addAll(this.styleClasses);
        imageView.setImage(this.image);
        imageView.setPreserveRatio(this.preserveRatio);
        if (this.onMouseClicked != null) {
            imageView.setOnMouseClicked(this.onMouseClicked);
        }
        return imageView;

    }
}
