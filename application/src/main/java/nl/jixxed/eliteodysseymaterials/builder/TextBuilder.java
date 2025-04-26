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
