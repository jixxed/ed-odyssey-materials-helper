package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HyperlinkBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private StringBinding stringBinding;
    private EventHandler<ActionEvent> action;
    private ImageView imageView;

    public static HyperlinkBuilder builder() {
        return new HyperlinkBuilder();
    }

    public HyperlinkBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public HyperlinkBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public HyperlinkBuilder withText(final StringBinding stringBinding) {
        this.stringBinding = stringBinding;
        return this;
    }

    public HyperlinkBuilder withAction(final EventHandler<ActionEvent> action) {
        this.action = action;
        return this;
    }

    public HyperlinkBuilder withGraphic(final ImageView imageView) {
        this.imageView = imageView;
        return this;
    }

    public Hyperlink build() {
        final Hyperlink hyperlink = new Hyperlink();
        hyperlink.getStyleClass().addAll(this.styleClasses);
        if (this.stringBinding != null) {
            hyperlink.textProperty().bind(this.stringBinding);
        } else if (this.imageView != null) {
            hyperlink.setGraphic(this.imageView);
        }
        if (this.action != null) {
            hyperlink.setOnAction(this.action);
        }
        return hyperlink;
    }

}
