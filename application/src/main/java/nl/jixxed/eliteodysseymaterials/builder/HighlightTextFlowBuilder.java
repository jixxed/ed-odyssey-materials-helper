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
