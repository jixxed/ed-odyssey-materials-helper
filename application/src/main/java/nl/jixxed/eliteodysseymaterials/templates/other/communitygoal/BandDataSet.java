/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.other.communitygoal;

import io.fair_acc.dataset.DataSet;
import io.fair_acc.dataset.spi.DoubleDataSet;
import javafx.beans.property.StringProperty;
import javafx.beans.property.StringPropertyBase;

public class BandDataSet extends DoubleDataSet{
    public BandDataSet(DataSet another) {
        super(another);
    }

    public BandDataSet(String name) {
        super(name);
    }

    public BandDataSet(String name, double[] xValues, double[] yValues, int initalSize, boolean deepCopy) {
        super(name, xValues, yValues, initalSize, deepCopy);
    }

    public BandDataSet(String name, int initalSize) {
        super(name, initalSize);
    }


    /** The user displayable name for this series */
    private final StringProperty name = new StringPropertyBase() {
        @Override protected void invalidated() {
            get(); // make non-lazy
        }

        @Override
        public Object getBean() {
            return BandDataSet.this;
        }

        @Override
        public String getName() {
            return "name";
        }
    };
    public final String getName() { return name.get(); }
    public final DoubleDataSet setName(String value) { name.set(value);
        return this;
    }
    public final StringProperty nameProperty() { return name; }

}
