/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.domain.ships;

public enum ModuleSize {
    SIZE_0("0"),
    SIZE_1("1"),
    SIZE_2("2"),
    SIZE_3("3"),
    SIZE_4("4"),
    SIZE_5("5"),
    SIZE_6("6"),
    SIZE_7("7"),
    SIZE_8("8");

    private final String displayName;

    ModuleSize(final String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }

    public boolean isHigher(ModuleSize moduleSize) {
        return this.ordinal() > moduleSize.ordinal();
    }
    public boolean isLower(ModuleSize moduleSize) {
        return this.ordinal() < moduleSize.ordinal();
    }
    public boolean isLowerOrEqual(int size) {
        return this.ordinal() <= size;
    }

    public int intValue() {
        return ordinal();
    }
}
