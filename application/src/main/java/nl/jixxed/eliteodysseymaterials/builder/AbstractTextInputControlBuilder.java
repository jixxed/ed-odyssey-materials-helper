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

