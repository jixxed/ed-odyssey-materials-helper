package nl.jixxed.eliteodysseymaterials.builder;

import javafx.scene.Node;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHighlightTextFlow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HighlightTextFlowBuilder extends AbstractPaneBuilder<HighlightTextFlowBuilder> {
    public static final String WITH_NODES_ERROR = "Use withText(String, Object...) instead.";
    private final List<String> highlightStyleClasses = new ArrayList<>();
    private String localeKey;
    private Object[] parameters;

    public static HighlightTextFlowBuilder builder() {
        return new HighlightTextFlowBuilder();
    }

    public HighlightTextFlowBuilder withHighlightStyleClass(final String styleClass) {
        this.highlightStyleClasses.add(styleClass);
        return this;
    }

    public HighlightTextFlowBuilder withHighlightStyleClasses(final String... styleClasses) {
        this.highlightStyleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public HighlightTextFlowBuilder withText(final String key, final Object... parameters) {
        this.parameters = parameters;
        this.localeKey = key;
        return this;
    }

    @Override
    public <E extends Node & DestroyableComponent> HighlightTextFlowBuilder withNode(E node) {
        throw new UnsupportedOperationException(WITH_NODES_ERROR);
    }

    @Override
    public <E extends Node & DestroyableComponent> HighlightTextFlowBuilder withNodes(E... nodes) {
        throw new UnsupportedOperationException(WITH_NODES_ERROR);
    }

    @Override
    public <E extends Node & DestroyableComponent> HighlightTextFlowBuilder withNodes(Collection<E> nodes) {
        throw new UnsupportedOperationException(WITH_NODES_ERROR);
    }

    @SuppressWarnings("unchecked")
    public DestroyableHighlightTextFlow build() {
        final DestroyableHighlightTextFlow textFlow = new DestroyableHighlightTextFlow(LocaleService.getStringBinding(this.localeKey), this.parameters);
        super.build(textFlow);
        textFlow.getHighlightStyleClasses().addAll(this.highlightStyleClasses);
        textFlow.populateTextFlow();
        return textFlow;
    }


}
