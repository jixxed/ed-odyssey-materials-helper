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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.PathItem;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.templates.generic.ShortestPathFlow;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShortestPathFlowBuilder<T extends BlueprintName<T>> extends AbstractFlowPaneBuilder<FlowPaneBuilder> {

    private Expansion expansion;
    private List<PathItem<T>> pathItems;

    public static <T extends BlueprintName<T>> ShortestPathFlowBuilder<T> builder() {
        return new ShortestPathFlowBuilder();
    }

    public ShortestPathFlowBuilder<T> withExpansion(final Expansion expansion) {
        this.expansion = expansion;
        return this;
    }

    public ShortestPathFlowBuilder<T> withPathItems(final List<PathItem<T>> pathItems) {
        this.pathItems = pathItems;
        return this;
    }

    @SuppressWarnings("unchecked")
    public ShortestPathFlow<T> build() {
        if (expansion == null) {
            throw new IllegalStateException("Expansion must be set");
        }
        final ShortestPathFlow<T> shortestPathFlow = new ShortestPathFlow<>(expansion);
        super.build(shortestPathFlow);
        shortestPathFlow.setItems(pathItems);

        return shortestPathFlow;
    }

}
