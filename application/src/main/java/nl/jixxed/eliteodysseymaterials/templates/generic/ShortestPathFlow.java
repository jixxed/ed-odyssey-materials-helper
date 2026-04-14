/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.generic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.PathItem;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class ShortestPathFlow<T extends BlueprintName<T>> extends DestroyableFlowPane implements DestroyableTemplate {
    @Getter
    private ObservableList<ShortestPathItem<T>> items = FXCollections.observableArrayList();
    private final Expansion expansion;

    public ShortestPathFlow(final Expansion expansion) {
        this.expansion = expansion;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shortest-path-flow");
    }

    public void setItems(final List<PathItem<T>> pathItems) {
        this.getNodes().clear();
        this.items.clear();
        this.items.addAll(IntStream.range(0, pathItems.size()).mapToObj(index -> new ShortestPathItem<>(pathItems.get(index), index + 1, this.expansion)).toList());
        this.getNodes().addAll(this.items);
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        items.clear();
    }
}
