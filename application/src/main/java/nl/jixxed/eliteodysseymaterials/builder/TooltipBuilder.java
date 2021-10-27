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
    private Duration showDelay;
    private Duration showDuration;

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

    public TooltipBuilder withShowDelay(final Duration showDelay) {
        this.showDelay = showDelay;
        return this;
    }
    
    public TooltipBuilder withShowDuration(final Duration showDuration) {
        this.showDuration = showDuration;
        return this;
    }

    public Tooltip build() {
        final Tooltip tooltip = new Tooltip();
        tooltip.getStyleClass().addAll(this.styleClasses);
        if (this.stringBinding != null) {
            tooltip.textProperty().bind(this.stringBinding);
        }
        if (this.showDelay != null) {
            tooltip.setShowDelay(this.showDelay);
        }
        if (this.showDuration != null) {
            tooltip.setShowDuration(this.showDuration);
        }
        return tooltip;
    }

}
