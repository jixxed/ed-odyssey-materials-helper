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
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableRectangle;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RectangleBuilder extends AbstractShapeBuilder<RectangleBuilder> {

    private Double width;
    private Double height;

    public static RectangleBuilder builder() {
        return new RectangleBuilder();
    }

    public RectangleBuilder withWidth(Double width) {
        this.width = width;
        return this;
    }

    public RectangleBuilder withHeight(Double height) {
        this.height = height;
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableRectangle build() {
        DestroyableRectangle rectangle = new DestroyableRectangle();
        super.build(rectangle);
        if (width != null) {
            rectangle.setWidth(width);
        }
        if (height != null) {
            rectangle.setHeight(height);
        }

        return rectangle;
    }
}
