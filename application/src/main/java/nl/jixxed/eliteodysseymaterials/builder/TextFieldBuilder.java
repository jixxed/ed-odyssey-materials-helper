package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTextField;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextFieldBuilder extends AbstractTextInputControlBuilder<TextFieldBuilder> {

    public static TextFieldBuilder builder() {
        return new TextFieldBuilder();
    }

    @SuppressWarnings("unchecked")
    public DestroyableTextField build() {
        final DestroyableTextField textField = new DestroyableTextField();
        super.build(textField);
        return textField;
    }

}
