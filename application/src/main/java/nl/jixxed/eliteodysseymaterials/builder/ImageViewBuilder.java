package nl.jixxed.eliteodysseymaterials.builder;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageViewBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private Image image;
    private final Map<EventType<MouseEvent>, EventHandler<? super MouseEvent>> eventHandlers = new HashMap<>();

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

    public ImageViewBuilder addEventHandler(final EventType<MouseEvent> mouseEvent, final EventHandler<MouseEvent> eventHandler) {
        this.eventHandlers.put(mouseEvent, eventHandler);
        return this;
    }

    public ImageView build() {
        final ImageView imageView = new ImageView();
        imageView.getStyleClass().addAll(this.styleClasses);
        imageView.setImage(this.image);
        if (!this.eventHandlers.isEmpty()) {
            this.eventHandlers.forEach(imageView::addEventHandler);

        }
        return imageView;
    }
}
