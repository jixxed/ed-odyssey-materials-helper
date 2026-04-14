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
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextInputControl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class AbstractTextInputControlBuilder<T extends AbstractTextInputControlBuilder<T>> extends AbstractControlBuilder<T> {
    private StringBinding stringBinding;
    private StringBinding promptTextBinding;
    private String nonLocalizedText;
    private ChangeListener<String> textPropertyChangeListener;
    private Boolean isEditable;

    public T withPromptTextProperty(final StringBinding promptTextBinding) {
        this.promptTextBinding = promptTextBinding;
        return (T) this;
    }

    public T withNonLocalizedText(final String nonLocalizedText) {
        this.nonLocalizedText = nonLocalizedText;
        return (T) this;
    }

    public T withText(final StringBinding stringBinding) {
        this.stringBinding = stringBinding;
        return (T) this;
    }

    public T withText(final String localeKey, Object... parameters) {
        this.stringBinding = LocaleService.getStringBinding(localeKey, parameters);
        return (T) this;
    }

    public T withTextProperty(final ChangeListener<String> textPropertyChangeListener) {
        this.textPropertyChangeListener = textPropertyChangeListener;
        return (T) this;
    }

    public T withEditable(final boolean isEditable) {
        this.isEditable = isEditable;
        return (T) this;
    }

    public <P extends TextInputControl & DestroyableComponent> P build(P textInputControl) {
        super.build(textInputControl);

        if (this.stringBinding != null) {
            textInputControl.addBinding(textInputControl.textProperty(), this.stringBinding);
        } else if (this.nonLocalizedText != null) {
            textInputControl.setText(this.nonLocalizedText);
        }
        if (this.promptTextBinding != null) {
            textInputControl.addBinding(textInputControl.promptTextProperty(), this.promptTextBinding);
        }
        if (this.isEditable != null) {
            textInputControl.setEditable(this.isEditable);
        }
        if (this.textPropertyChangeListener != null) {
            textInputControl.addChangeListener(textInputControl.textProperty(), this.textPropertyChangeListener);
        }

        return textInputControl;
    }
}

