package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableText;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTextFlow;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextFlowBuilder extends AbstractPaneBuilder<TextFlowBuilder> {
    private DestroyableText[] texts;

    public static TextFlowBuilder builder() {
        return new TextFlowBuilder();
    }

    public TextFlowBuilder withTexts(final DestroyableText... texts) {
        this.texts = texts;
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableTextFlow build() {
        final DestroyableTextFlow textFlow = new DestroyableTextFlow();
        super.build(textFlow);

        textFlow.getNodes().addAll(texts);

        return textFlow;
    }


}
