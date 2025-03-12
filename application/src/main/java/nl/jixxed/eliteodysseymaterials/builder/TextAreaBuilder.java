package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTextArea;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextAreaBuilder extends AbstractTextInputControlBuilder<TextFieldBuilder> {

    public static TextAreaBuilder builder() {
        return new TextAreaBuilder();
    }

    @SuppressWarnings("unchecked")
    public DestroyableTextArea build() {
        final DestroyableTextArea textField = new DestroyableTextArea();
        super.build(textField);
        return textField;
    }

}
