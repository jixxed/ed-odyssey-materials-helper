package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.StringBinding;
import javafx.scene.control.Labeled;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class AbstractLabeledBuilder<T extends AbstractLabeledBuilder<T>> extends AbstractControlBuilder<T> {
    private StringBinding stringBinding;
    private String nonLocalizedText;
    private DestroyableResizableImageView graphic;
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

    public T withGraphic(final DestroyableResizableImageView graphic) {
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
        }
        if (this.mnemonicParsing != null) {
            labeled.setMnemonicParsing(this.mnemonicParsing);
        }
        return labeled;
    }
}
