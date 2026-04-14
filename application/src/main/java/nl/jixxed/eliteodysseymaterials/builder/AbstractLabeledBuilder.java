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
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class AbstractLabeledBuilder<T extends AbstractLabeledBuilder<T>> extends AbstractControlBuilder<T> {
    private StringBinding stringBinding;
    private String nonLocalizedText;
    private Node graphic;
    private Boolean mnemonicParsing;

    public T withText(final StringBinding stringBinding) {
        this.stringBinding = stringBinding;
        return (T) this;
    }

    public T withText(final String localeKey, Object... parameters) {
        this.stringBinding = LocaleService.getStringBinding(localeKey, parameters);
        return (T) this;
    }

    public T withNonLocalizedText(final String nonLocalizedText) {
        this.nonLocalizedText = nonLocalizedText;
        return (T) this;
    }

    public T withMnemonicParsing(boolean mnemonicParsing) {
        this.mnemonicParsing = mnemonicParsing;
        return (T) this;
    }

    public <G extends Node & DestroyableComponent> T withGraphic(final G graphic) {
        this.graphic = graphic;
        return (T) this;
    }

    public <L extends Labeled & DestroyableComponent> L build(L labeled) {
        super.build(labeled);
        if (this.stringBinding != null) {
            labeled.addBinding(labeled.textProperty(), this.stringBinding);
        } else if (this.nonLocalizedText != null) {
            labeled.setText(this.nonLocalizedText);
        }
        if (this.graphic != null) {
            labeled.setGraphic(this.graphic);
            labeled.register((Node & DestroyableComponent)this.graphic);
        }
        if (this.mnemonicParsing != null) {
            labeled.setMnemonicParsing(this.mnemonicParsing);
        }
        return labeled;
    }
}
