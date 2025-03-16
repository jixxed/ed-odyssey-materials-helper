package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableImageView;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageViewBuilder extends  AbstractNodeBuilder<ImageViewBuilder> {
    private final List<String> styleClasses = new ArrayList<>();
    private Image image;
    private boolean preserveRatio = true;
    private final Map<EventType<MouseEvent>, EventHandler<? super MouseEvent>> eventHandlers = new HashMap<>();
    private ObservableValue<Number> fitHeightBinding;

    public static ImageViewBuilder builder() {
        return new ImageViewBuilder();
    }

    public ImageViewBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
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

    public ImageViewBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public ImageViewBuilder addEventHandler(final EventType<MouseEvent> mouseEvent, final EventHandler<MouseEvent> eventHandler) {
        this.eventHandlers.put(mouseEvent, eventHandler);
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
        if (!this.eventHandlers.isEmpty()) {
            this.eventHandlers.forEach(imageView::registerEventHandler);

        }
        return imageView;
    }
}
