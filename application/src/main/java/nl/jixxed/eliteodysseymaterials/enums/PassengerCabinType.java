/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.enums;

public enum PassengerCabinType {
    ECONOMY, BUSINESS, FIRSTCLASS, LUXURY;


    public Integer getMinSize() {
        return switch (this) {
            case ECONOMY -> 2;
            case BUSINESS -> 3;
            case FIRSTCLASS -> 4;
            case LUXURY -> 5;
        };
    }

    public int getMaxSize() {
        return 6;
    }

    public int getPassengerCount(int moduleSize) {
        return switch (this) {
            case ECONOMY -> {
                if (moduleSize == 2) {
                    yield 2;
                } else if (moduleSize == 3) {
                    yield 4;
                } else if (moduleSize == 4) {
                    yield 8;
                } else if (moduleSize == 5) {
                    yield 16;
                } else if (moduleSize >= 6) {
                    yield 32;
                } else {
                    yield 0;
                }
            }
            case BUSINESS -> {
                if (moduleSize == 3) {
                    yield 3;
                } else if (moduleSize == 4) {
                    yield 6;
                } else if (moduleSize == 5) {
                    yield 10;
                } else if (moduleSize >= 6) {
                    yield 16;
                } else {
                    yield 0;
                }
            }
            case FIRSTCLASS -> {
                if (moduleSize == 4) {
                    yield 3;
                } else if (moduleSize == 5) {
                    yield 6;
                } else if (moduleSize >= 6) {
                    yield 12;
                } else {
                    yield 0;
                }
            }
            case LUXURY -> {
                if (moduleSize == 5) {
                    yield 4;
                } else if (moduleSize >= 6) {
                    yield 8;
                } else {
                    yield 0;
                }
            }
        };
    }
}
