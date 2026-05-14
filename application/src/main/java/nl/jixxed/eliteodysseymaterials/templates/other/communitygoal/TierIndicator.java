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

import io.fair_acc.chartfx.axes.Axis;
import io.fair_acc.chartfx.plugins.AbstractSingleValueIndicator;
import io.fair_acc.chartfx.plugins.ValueIndicator;
import io.fair_acc.chartfx.ui.geometry.Side;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;

/**
 * A vertical line drawn on the plot area, indicating specified X value, with an optional {@link #textProperty() text
 * label} describing the value.
 * <p>
 * Style Classes (from least to most specific):
 * <ul>
 * <li><b>Label:</b> {@code value-indicator-label, x-value-indicator-label, x-value-indicator-label[index]}</li>
 * <li><b>Line:</b> {@code value-indicator-line, x-value-indicator-line, x-value-indicator-line[index]}</li>
 * </ul>
 * where {@code [index]} corresponds to the index (zero based) of this indicator instance added to the
 * {@code XYChartPane}. For example class {@code x-value-indicator-label1} can be used to style label of the second
 * instance of this indicator added to the chart pane.
 *
 * @author mhrabia
 */
public class TierIndicator extends AbstractSingleValueIndicator implements ValueIndicator {
    private final Integer tier;

    /**
     * Creates a new instance of the indicator.
     *
     * @param axis the axis this indicator is associated with
     * @param value a X value to be indicated
     */
    public TierIndicator(final Axis axis, final Integer tier, final double value) {
        this(axis, tier, value, null);
    }

    /**
     * Creates a new instance of the indicator.
     *
     * @param axis the axis this indicator is associated with
     * @param value a X value to be indicated
     * @param text the text to be shown by the label. Value of {@link #textProperty()}.
     */
    public TierIndicator(final Axis axis, final Integer tier, final double value, final String text) {
        super(axis, value, text);
        setLabelPosition(0.04);
        this.tier = tier;
        label.setText("T" + tier);
    }


    @Override
    public void runPostLayout() {
        if (getChart() == null) {
            return;
        }

        final Bounds plotAreaBounds = getChart().getCanvas().getBoundsInLocal();
        final double minX = plotAreaBounds.getMinX();
        final double maxX = plotAreaBounds.getMaxX();
        final double minY = plotAreaBounds.getMinY();
        final double maxY = plotAreaBounds.getMaxY();
        final double xPos = minX + getAxis().getDisplayPosition(getValue());
        final double axisPos;
        if (getAxis().getSide().equals(Side.BOTTOM)) {
            axisPos = getChart().getPlotForeground().sceneToLocal(getAxis().getCanvas().localToScene(0, 0)).getY() + 6;
        } else {
            axisPos = getChart().getPlotForeground().sceneToLocal(getAxis().getCanvas().localToScene(0, getAxis().getHeight())).getY() - 6;
        }
        final double xPosGlobal = getChart().getPlotForeground().sceneToLocal(getChart().getCanvas().localToScene(xPos, 0)).getX();

        if (xPos < minX || xPos > maxX) {
            getChartChildren().clear();
        } else {
            layoutLine(xPos, minY, xPos, maxY);
            layoutLabel(new BoundingBox((tier % 2 == 0)? xPos+ ScalingHelper.scalePixel(10D) : xPos-ScalingHelper.scalePixel(10D), minY, 0, maxY - minY), (tier % 2 == 0)? 1 : 0,
                    (tier % 2 == 0)?getLabelPosition(): 1-getLabelPosition());
        }
    }

    @Override
    public void updateStyleClass() {
        setStyleClasses(label, "x-tier-indicator-label-", (tier % 2 == 0)? "even":"odd");
        setStyleClasses(line, "x-tier-indicator-line-", (tier % 2 == 0)? "even":"odd");
    }
}
