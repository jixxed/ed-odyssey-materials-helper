/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.domain;

import java.util.Objects;

public abstract class HorizonsModifierValue<T> {
    private final String modification;
    private final boolean isPositive;

    public HorizonsModifierValue(final String modification, final boolean isPositive) {
        this.modification = modification;
        this.isPositive = isPositive;
    }

    public String modification() {
        return this.modification;
    }

    public boolean isPositive() {
        return this.isPositive;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        final var that = (HorizonsModifierValue) obj;
        return Objects.equals(this.modification, that.modification) &&
                this.isPositive == that.isPositive;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.modification, this.isPositive);
    }

    @Override
    public String toString() {
        return "HorizonsModifierValue[" +
                "modification=" + this.modification + ", " +
                "isPositive=" + this.isPositive + ']';
    }

    //    public abstract  T getModifiedValue(final T baseValue, final Double percentComplete);
    public abstract HorizonsBiFunction getModifier();
}
