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

import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.enums.StorageType;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

public class MissionIngredient extends Ingredient {
    private final StorageType storageType;
    private final String localeKey;

    private DestroyableLabel nameLabel;

    public MissionIngredient(final String localeKey, final StorageType storageType) {
        this.storageType = storageType;
        this.localeKey = localeKey;
        initComponents();
    }

    private void initComponents() {
        this.getStyleClass().addAll("ingredient", "mission");
        this.nameLabel = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(this.localeKey)
                .build();
        DestroyableHBox line = BoxBuilder.builder()
                .withNodes(this.nameLabel)
                .buildHBox();
        this.getNodes().addAll(line, new GrowingRegion());
    }

    @Override
    public StorageType getType() {
        return this.storageType;
    }

}
