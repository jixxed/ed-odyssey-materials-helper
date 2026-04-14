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
import javafx.geometry.NodeOrientation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableText;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextBuilder extends AbstractShapeBuilder<TextBuilder> {
    private StringBinding stringBinding;
    private String nonLocalizedText;
    private NodeOrientation nodeOrientation;
    private Double wrappingWidth;

    public static TextBuilder builder() {
        return new TextBuilder();
    }

    public TextBuilder withText(final StringBinding stringBinding) {
        this.stringBinding = stringBinding;
        return this;
    }

    public TextBuilder withText(final String localeKey, Object... parameters) {
        this.stringBinding = LocaleService.getStringBinding(localeKey, parameters);
        return this;
    }

    public TextBuilder withNonLocalizedText(final String nonLocalizedText) {
        this.nonLocalizedText = nonLocalizedText;
        return this;
    }

    public TextBuilder withWrappingWidth(final Double wrappingWidth) {
        this.wrappingWidth = wrappingWidth;
        return this;
    }

    public DestroyableText build() {
        final DestroyableText text = new DestroyableText();
        super.build(text);
        if (this.stringBinding != null) {
            text.addBinding(text.textProperty(), this.stringBinding);
        } else if (this.nonLocalizedText != null) {
            text.setText(this.nonLocalizedText);
        }
        if (this.nodeOrientation != null) {
            text.setNodeOrientation(this.nodeOrientation);
        }
        if (this.wrappingWidth != null) {
            text.setWrappingWidth(this.wrappingWidth);
        }
        return text;
    }

}
