/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.StringBinding;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.OverrunStyle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTooltip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TooltipBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private StringBinding stringBinding;
    private Duration showDelay;
    private Duration showDuration = Duration.seconds(30);
    private String nonLocalizedText;
    private Supplier<Double> customX;
    private Supplier<Double> customY;

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

    public TooltipBuilder withCustomX(final Supplier<Double> customX) {
        this.customX = customX;
        return this;
    }

    public TooltipBuilder withCustomY(final Supplier<Double> customY) {
        this.customY = customY;
        return this;
    }

    public TooltipBuilder withText(final String localeKey, Object... parameters) {
        this.stringBinding = LocaleService.getStringBinding(localeKey, parameters);
        return this;
    }

    public TooltipBuilder withNonLocalizedText(final String nonLocalizedText) {
        this.nonLocalizedText = nonLocalizedText;
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

    public DestroyableTooltip build() {
        final DestroyableTooltip tooltip = new DestroyableTooltip();
        tooltip.getStyleClass().addAll(this.styleClasses);
        if (this.stringBinding != null) {
            tooltip.addBinding(tooltip.textProperty(), this.stringBinding);
        } else if (this.nonLocalizedText != null) {
            tooltip.setText(this.nonLocalizedText);
        }
        if (this.showDelay != null) {
            tooltip.setShowDelay(this.showDelay);
        }
        if (this.showDuration != null) {
            tooltip.setShowDuration(this.showDuration);
        }
        if (this.customX != null) {
            tooltip.setCustomX(this.customX);
        }
        if (this.customY != null) {
            tooltip.setCustomY(this.customY);
        }
        tooltip.setWrapText(true);
        tooltip.setTextAlignment(TextAlignment.LEFT);
        tooltip.setContentDisplay(ContentDisplay.LEFT);
        tooltip.setTextOverrun(OverrunStyle.CLIP);
        return tooltip;
    }
}
