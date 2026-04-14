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
import nl.jixxed.eliteodysseymaterials.templates.components.IntField;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IntFieldBuilder extends AbstractTextInputControlBuilder<IntFieldBuilder> {
    private Integer minValue;
    private Integer maxValue;
    private Integer initialValue;

    public static IntFieldBuilder builder() {
        return new IntFieldBuilder();
    }

    public IntFieldBuilder withMinValue(final Integer minValue) {
        this.minValue = minValue;
        return this;
    }

    public IntFieldBuilder withMaxValue(final Integer maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public IntFieldBuilder withInitialValue(final Integer initialValue) {
        this.initialValue = initialValue;
        return this;
    }

    @SuppressWarnings("unchecked")
    public IntField build() {
        final IntField intField = new IntField(this.minValue, this.maxValue, this.initialValue);
        super.build(intField);
        return intField;
    }

}
