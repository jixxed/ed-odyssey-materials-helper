package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.StringBinding;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TooltipBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private StringBinding stringBinding;
    private Duration duration;

    public static TooltipBuilder builder() {
        return new TooltipBuilder();
    }

    public TooltipBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public TooltipBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public TooltipBuilder withText(final StringBinding stringBinding) {
        this.stringBinding = stringBinding;
        return this;
    }

    public Tooltip build() {
        final Tooltip tooltip = new Tooltip();
        tooltip.getStyleClass().addAll(this.styleClasses);
        if (this.stringBinding != null) {
            tooltip.textProperty().bind(this.stringBinding);
        }
        if (this.duration != null) {
            tooltip.setShowDuration(this.duration);
        }
        return tooltip;
    }

    public TooltipBuilder withShowDelay(final Duration duration) {
        this.duration = duration;
        return this;
    }
}
