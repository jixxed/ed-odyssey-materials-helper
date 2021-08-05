package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TitledPaneBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private Node content;
    private StringBinding stringBinding;

    public static TitledPaneBuilder builder() {
        return new TitledPaneBuilder();
    }

    public TitledPaneBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public TitledPaneBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public TitledPaneBuilder withText(final StringBinding stringBinding) {
        this.stringBinding = stringBinding;
        return this;
    }


    public TitledPaneBuilder withContent(final Node content) {
        this.content = content;
        return this;
    }


    public TitledPane build() {
        final TitledPane titledPane = new TitledPane();
        titledPane.getStyleClass().addAll(this.styleClasses);
        if (this.stringBinding != null) {
            titledPane.textProperty().bind(this.stringBinding);
        }
        if (this.content != null) {
            titledPane.setContent(this.content);
        }
        return titledPane;
    }

}
