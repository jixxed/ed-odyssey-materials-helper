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

import io.fair_acc.dataset.AxisDescription;
import io.fair_acc.dataset.DataSet;
import io.fair_acc.dataset.DataSet2D;
import io.fair_acc.dataset.EditableDataSet;
import io.fair_acc.dataset.events.ChartBits;
import io.fair_acc.dataset.spi.AbstractDataSet;
import io.fair_acc.dataset.spi.fastutil.DoubleArrayList;
import io.fair_acc.dataset.utils.AssertUtils;
import javafx.beans.property.StringProperty;
import javafx.beans.property.StringPropertyBase;

import java.util.ArrayList;

public class BandDataSet extends AbstractDataSet<BandDataSet> implements EditableDataSet, DataSet2D {
    private static final long serialVersionUID = -49323231312123123L;
    private static final String X_COORDINATES = "X coordinates";
    private static final String Y_MIN_COORDINATES = "Y min coordinates";
    private static final String Y_MAX_COORDINATES = "Y max coordinates";
    protected DoubleArrayList xValues; // way faster than java default lists
    protected DoubleArrayList yMinValues; // way faster than java default lists
    protected DoubleArrayList yMaxValues; // way faster than java default lists

    /**
     * Creates a new instance of <code>BandDataSet</code> as copy of another (deep-copy).
     *
     * @param another name of this DataSet.
     */
    public BandDataSet(final DataSet another) {
        super(another.getName(), another.getDimension());
        set(another); // NOPMD by rstein on 25/06/19 07:42
    }

    /**
     * Creates a new instance of <code>BandDataSet</code>.
     *
     * @param name name of this DataSet.
     * @throws IllegalArgumentException if {@code name} is {@code null}
     */
    public BandDataSet(final String name) {
        this(name, 0);
    }

    /**
     * <p>
     * Creates a new instance of <code>BandDataSet</code>.
     * </p>
     * The user than specify via the copy parameter, whether the dataset wraps the input arrays themselves or on a
     * copies of the input arrays.
     *
     * @param name name of this data set.
     * @param xValues X coordinates
     * @param yMinValues Y min coordinates
     * @param yMaxValues Y max coordinates
     * @param initalSize how many data points are relevant to be taken
     * @param deepCopy if true, the input array is copied
     * @throws IllegalArgumentException if any of the parameters is {@code null} or if arrays with coordinates have
     *             different lengths
     */
    public BandDataSet(final String name, final double[] xValues, final double[] yMinValues, final double[] yMaxValues, final int initalSize,
                         final boolean deepCopy) {
        super(name, 3);
        set(xValues, yMinValues, yMaxValues, initalSize, deepCopy); // NOPMD
    }

    /**
     * Creates a new instance of <code>BandDataSet</code>.
     *
     * @param name name of this DataSet.
     * @param initalSize initial capacity of buffer (N.B. size=0)
     * @throws IllegalArgumentException if {@code name} is {@code null}
     */
    public BandDataSet(final String name, final int initalSize) {
        super(name, 3);
        AssertUtils.gtEqThanZero("initalSize", initalSize);
        xValues = new DoubleArrayList(initalSize);
        yMinValues = new DoubleArrayList(initalSize);
        yMaxValues = new DoubleArrayList(initalSize);
    }

    /**
     * Add point to the end of the data set
     *
     * @param x index
     * @param y index
     * @return itself
     */
    public BandDataSet add(final double x, final double yMin, final double yMax) {
        return add(this.getDataCount(), x, yMin, yMax, null);
    }

    /**
     * Add point to the data set.
     *
     * @param x horizontal coordinate of the new data point
     * @param y vertical coordinate of the new data point
     * @param label the data label
     * @return itself (fluent design)
     */
    public BandDataSet add(final double x, final double yMin, final double yMax, final String label) {
        lock().writeLockGuard(() -> {
            xValues.add(x);
            yMinValues.add(yMin);
            yMaxValues.add(yMax);

            if ((label != null) && !label.isEmpty()) {
                addDataLabel(xValues.size() - 1, label);
            }

            getAxisDescription(DIM_X).add(x);
            getAxisDescription(DIM_Z).add(yMin);
            getAxisDescription(DIM_Y).add(yMax);
        });
        fireInvalidated(ChartBits.DataSetDataAdded);
        return getThis();
    }

    /**
     * Add array vectors to data set.
     *
     * @param xValuesNew X coordinates
     * @param yValuesNew Y coordinates
     * @return itself
     */
    public BandDataSet add(final double[] xValuesNew, final double[] yMinValuesNew, final double[] yMaxValuesNew) {
        AssertUtils.notNull(X_COORDINATES, xValuesNew);
        AssertUtils.notNull(Y_MIN_COORDINATES, yMinValuesNew);
        AssertUtils.notNull(Y_MAX_COORDINATES, yMaxValuesNew);
        AssertUtils.equalDoubleArrays(xValuesNew, yMinValuesNew);
        AssertUtils.equalDoubleArrays(xValuesNew, yMaxValuesNew);

        lock().writeLockGuard(() -> {
            final int addAt = xValues.size();
            final int newElements = Math.min(xValuesNew.length, Math.min(yMinValuesNew.length, yMaxValuesNew.length));
            resize(addAt + newElements);
            xValues.setElements(addAt, xValuesNew);
            yMinValues.setElements(addAt, yMinValuesNew);
            yMaxValues.setElements(addAt, yMaxValuesNew);

            getAxisDescription(DIM_X).add(xValuesNew);
            getAxisDescription(DIM_Z).add(yMinValuesNew);
            getAxisDescription(DIM_Y).add(yMaxValuesNew);
        });

        fireInvalidated(ChartBits.DataSetDataAdded);
        return getThis();
    }

    /**
     * add point to the data set
     *
     * @param index data point index at which the new data point should be added
     * @param newValue new data point coordinate
     * @return itself (fluent design)
     */
    @Override
    public BandDataSet add(final int index, final double... newValue) {
        return add(index, newValue[0], newValue[1], newValue[2], null);
    }

    /**
     * add point to the data set
     *
     * @param index data point index at which the new data point should be added
     * @param x horizontal coordinate of the new data point
     * @param y vertical coordinate of the new data point
     * @return itself (fluent design)
     */
    public BandDataSet add(final int index, final double x, final double yMin, final double yMax) {
        return add(index, x, yMin, yMax, null);
    }

    /**
     * add point to the data set
     *
     * @param index data point index at which the new data point should be added
     * @param x horizontal coordinates of the new data point
     * @param y vertical coordinates of the new data point
     * @param label data point label (see CategoryAxis)
     * @return itself (fluent design)
     */
    public BandDataSet add(final int index, final double x, final double yMin, final double yMax, final String label) {
        lock().writeLockGuard(() -> {
            final int indexAt = Math.max(0, Math.min(index, getDataCount() + 1));

            xValues.add(indexAt, x);
            yMinValues.add(indexAt, yMin);
            yMaxValues.add(indexAt, yMax);
            getDataLabelMap().addValueAndShiftKeys(indexAt, xValues.size(), label);
            getDataStyleMap().shiftKeys(indexAt, xValues.size());
            getAxisDescription(DIM_X).add(x);
            getAxisDescription(DIM_Z).add(yMin);
            getAxisDescription(DIM_Y).add(yMax);
        });
        fireInvalidated(ChartBits.DataSetDataAdded);
        return getThis();
    }

    /**
     * add point to the data set
     *
     * @param index data point index at which the new data point should be added
     * @param x horizontal coordinate of the new data point
     * @param y vertical coordinate of the new data point
     * @return itself (fluent design)
     */
    public BandDataSet add(final int index, final double[] x, final double[] yMin, final double[] yMax) {
        AssertUtils.notNull(X_COORDINATES, x);
        AssertUtils.notNull(Y_MIN_COORDINATES, yMin);
        AssertUtils.notNull(Y_MAX_COORDINATES, yMax);
        final int min = Math.min(x.length, Math.min(yMin.length,yMax.length));
        AssertUtils.equalDoubleArrays(x, yMin, min);
        AssertUtils.equalDoubleArrays(x, yMax, min);

        lock().writeLockGuard(() -> {
            final int indexAt = Math.max(0, Math.min(index, getDataCount() + 1));
            xValues.addElements(indexAt, x, 0, min);
            yMinValues.addElements(indexAt, yMin, 0, min);
            yMaxValues.addElements(indexAt, yMax, 0, min);
            getAxisDescription(DIM_X).add(x, min);
            getAxisDescription(DIM_Z).add(yMin, min);
            getAxisDescription(DIM_Y).add(yMax, min);
            getDataLabelMap().shiftKeys(indexAt, xValues.size());
            getDataStyleMap().shiftKeys(indexAt, xValues.size());
        });
        fireInvalidated(ChartBits.DataSetDataAdded);
        return getThis();
    }

    /**
     * clear all data points
     *
     * @return itself (fluent design)
     */
    public BandDataSet clearData() {
        lock().writeLockGuard(() -> {
            xValues.clear();
            yMinValues.clear();
            yMaxValues.clear();
            getDataLabelMap().clear();
            getDataStyleMap().clear();
            clearMetaInfo();

            getAxisDescriptions().forEach(AxisDescription::clear);
        });
        fireInvalidated(ChartBits.DataSetDataRemoved);
        return getThis();
    }

    @Override
    public final double get(final int dimIndex, final int index) {
        return switch (dimIndex){
            case DIM_X -> xValues.elements()[index];
            case DIM_Z -> yMinValues.elements()[index];
            case DIM_Y -> yMaxValues.elements()[index];
            default -> throw new IllegalStateException("Unexpected value: " + dimIndex);
        };
    }

    /**
     * @return storage capacity of dataset
     */
    public int getCapacity() {
        return Math.min(xValues.elements().length, Math.min(yMinValues.elements().length, yMaxValues.elements().length));
    }

    @Override
    public int getDataCount() {
        return Math.min(xValues.size(), Math.min(yMinValues.size(), yMaxValues.size()));
    }

    @Override
    public final double[] getValues(final int dimIndex) {
        return switch (dimIndex){
            case DIM_X -> xValues.elements();
            case DIM_Z -> yMinValues.elements();
            case DIM_Y -> yMaxValues.elements();
            default -> throw new IllegalStateException("Unexpected value: " + dimIndex);
        };
    }

    /**
     * @param amount storage capacity increase
     * @return itself (fluent design)
     */
    public BandDataSet increaseCapacity(final int amount) {
        lock().writeLockGuard(() -> {
            final int size = getDataCount();
            resize(getCapacity() + amount);
            resize(size);
        });
        return getThis();
    }

    /**
     * remove point from data set
     *
     * @param index data point which should be removed
     * @return itself (fluent design)
     */
    @Override
    public EditableDataSet remove(final int index) {
        return remove(index, index + 1);
    }

    /**
     * removes sub-range of data points
     *
     * @param fromIndex start index
     * @param toIndex stop index
     * @return itself (fluent design)
     */
    public BandDataSet remove(final int fromIndex, final int toIndex) {
        lock().writeLockGuard(() -> {
            AssertUtils.indexInBounds(fromIndex, getDataCount(), "fromIndex");
            AssertUtils.indexOrder(fromIndex, "fromIndex", toIndex, "toIndex");

            final int clampedToIndex = Math.min(toIndex, getDataCount());
            xValues.removeElements(fromIndex, clampedToIndex);
            yMinValues.removeElements(fromIndex, clampedToIndex);
            yMaxValues.removeElements(fromIndex, clampedToIndex);

            // remove old label and style keys
            getDataLabelMap().remove(fromIndex, clampedToIndex);
            getDataStyleMap().remove(fromIndex, clampedToIndex);

            // invalidate ranges
            getAxisDescriptions().forEach(AxisDescription::clear);
        });
        fireInvalidated(ChartBits.DataSetDataRemoved);
        return getThis();
    }

    /**
     * ensures minimum size, enlarges if necessary
     *
     * @param size the actually used array lengths
     * @return itself (fluent design)
     */
    public BandDataSet resize(final int size) {
        lock().writeLockGuard(() -> {
            xValues.size(size);
            yMinValues.size(size);
            yMaxValues.size(size);
        });
        fireInvalidated(ChartBits.DataSetData);
        return getThis();
    }

    /**
     * clear old data and overwrite with data from 'other' data set
     *
     * @param other the source data set
     * @param copy true: perform a deep copy (default), false: reuse the other dataset's internal data structures (if applicable)
     * @return itself (fluent design)
     */
    @Override
    public BandDataSet set(final DataSet other, final boolean copy) {
        lock().writeLockGuard(() -> other.lock().writeLockGuard(() -> {
            // copy data
            this.set(other.getValues(DIM_X), other.getValues(DIM_Y), other.getValues(DIM_Z), other.getDataCount(), copy);

            copyMetaData(other);
            copyDataLabelsAndStyles(other, copy);
            copyAxisDescription(other);
        }));
        fireInvalidated(ChartBits.DataSetData);
        return getThis();
    }

    /**
     * <p>
     * Initialises the data set with specified data.
     * </p>
     * Note: The method copies values from specified double arrays.
     *
     * @param xValues X coordinates
     * @param yValues Y coordinates
     * @return itself
     */
    public BandDataSet set(final double[] xValues, final double[] yMinValues, final double[] yMaxValues) {
        return set(xValues, yMinValues, yMaxValues, true);
    }

    /**
     * <p>
     * Initialises the data set with specified data.
     * </p>
     * Note: The method copies values from specified double arrays.
     *
     * @param xValues X coordinates
     * @param yValues Y coordinates
     * @param copy true: makes an internal copy, false: use the pointer as is (saves memory allocation
     * @return itself
     */
    public BandDataSet set(final double[] xValues, final double[] yMinValues,final double[] yMaxValues, final boolean copy) {
        return set(xValues, yMinValues,yMaxValues ,-1, copy);
    }

    /**
     * <p>
     * Initialises the data set with specified data.
     * </p>
     * Note: The method copies values from specified double arrays.
     *
     * @param xValues X coordinates
     * @param yValues Y coordinates
     * @param nSamples number of samples to be copied
     * @param copy true: makes an internal copy, false: use the pointer as is (saves memory allocation
     * @return itself
     */
    public BandDataSet set(final double[] xValues, final double[]  yMinValues,final double[] yMaxValues, final int nSamples, final boolean copy) {
        AssertUtils.notNull(X_COORDINATES, xValues);
        AssertUtils.notNull(Y_MIN_COORDINATES, yMinValues);
        AssertUtils.notNull(Y_MAX_COORDINATES, yMaxValues);
        final int dataMaxIndex = Math.min(xValues.length, Math.min(yMinValues.length, yMaxValues.length));
        AssertUtils.equalDoubleArrays(xValues, yMinValues, dataMaxIndex);
        AssertUtils.equalDoubleArrays(xValues, yMaxValues, dataMaxIndex);
        if (nSamples >= 0) {
            AssertUtils.indexInBounds(nSamples, xValues.length + 1, "xValues bounds");
            AssertUtils.indexInBounds(nSamples, yMinValues.length + 1, "yValues bounds");
            AssertUtils.indexInBounds(nSamples, yMaxValues.length + 1, "yValues bounds");
        }
        final int nSamplesToAdd = nSamples >= 0 ? Math.min(nSamples, xValues.length) : xValues.length;

        lock().writeLockGuard(() -> {
            getDataLabelMap().clear();
            getDataStyleMap().clear();
            if (copy) {
                if (this.xValues == null) {
                    this.xValues = new DoubleArrayList();
                }
                if (this.yMinValues == null) {
                    this.yMinValues = new DoubleArrayList();
                }
                if (this.yMaxValues == null) {
                    this.yMaxValues = new DoubleArrayList();
                }
                resize(0);

                this.xValues.addElements(0, xValues, 0, nSamplesToAdd);
                this.yMinValues.addElements(0, yMinValues, 0, nSamplesToAdd);
                this.yMaxValues.addElements(0, yMaxValues, 0, nSamplesToAdd);
            } else {
                this.xValues = DoubleArrayList.wrap(xValues, nSamplesToAdd);
                this.yMinValues = DoubleArrayList.wrap(yMinValues, nSamplesToAdd);
                this.yMaxValues = DoubleArrayList.wrap(yMaxValues, nSamplesToAdd);
            }

            // invalidate ranges
            getAxisDescriptions().forEach(AxisDescription::clear);
        });
        fireInvalidated(ChartBits.DataSetData);
        return getThis();
    }

    /**
     * replaces point coordinate of existing data point
     *
     * @param index data point index at which the new data point should be added
     * @param newValue new data point coordinate
     * @return itself (fluent design)
     */
    @Override
    public BandDataSet set(final int index, final double... newValue) {
        return set(index, newValue[0], newValue[1], newValue[2]);
    }

    /**
     * replaces point coordinate of existing data point
     *
     * @param index the index of the data point
     * @param x new horizontal coordinate
     * @param y new vertical coordinate N.B. errors are implicitly assumed to be zero
     * @return itself (fluent design)
     */
    public BandDataSet set(final int index, final double x, final double yMin, final double yMax) {
        lock().writeLockGuard(() -> {
            final int dataCount = Math.max(index + 1, this.getDataCount());
            xValues.size(dataCount);
            yMinValues.size(dataCount);
            yMaxValues.size(dataCount);
            xValues.elements()[index] = x;
            yMinValues.elements()[index] = yMin;
            yMaxValues.elements()[index] = yMax;
            getDataLabelMap().remove(index);
            getDataStyleMap().remove(index);

            // invalidate ranges
            getAxisDescriptions().forEach(AxisDescription::clear);
        });
        fireInvalidated(ChartBits.DataSetData);
        return getThis();
    }

    public BandDataSet set(final int index, final double[] x, final double[] yMin, final double[] yMax) {
        lock().writeLockGuard(() -> {
            resize(Math.max(index + x.length, xValues.size()));
            System.arraycopy(x, 0, xValues.elements(), index, x.length);
            System.arraycopy(yMin, 0, yMinValues.elements(), index, yMin.length);
            System.arraycopy(yMax, 0, yMaxValues.elements(), index, yMax.length);
            getDataLabelMap().remove(index, index + x.length);
            getDataStyleMap().remove(index, index + x.length);

            // invalidate ranges
            getAxisDescriptions().forEach(AxisDescription::clear);
        });
        fireInvalidated(ChartBits.DataSetData);
        return getThis();
    }

    /**
     * Trims the arrays list so that the capacity is equal to the size.
     *
     * @see ArrayList#trimToSize()
     * @return itself (fluent design)
     */
    public BandDataSet trim() {
        lock().writeLockGuard(() -> {
            xValues.trim(0);
            yMinValues.trim(0);
            yMaxValues.trim(0);
        });
        fireInvalidated(ChartBits.DataSetData);
        return getThis();
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
    public final BandDataSet setName(String value) { name.set(value);
        return this;
    }
    public final StringProperty nameProperty() { return name; }

}
