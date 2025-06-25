package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import eu.hansolo.fx.charts.Axis;
import eu.hansolo.fx.charts.Grid;
import eu.hansolo.fx.charts.XYChart;
import eu.hansolo.fx.charts.XYPane;
import eu.hansolo.fx.charts.data.XYItem;

import java.util.List;

public class DestroyableLineChart<T extends XYItem> extends XYChart<T> implements DestroyableComponent {
    public DestroyableLineChart(XYPane<T> XY_PANE, Axis... AXIS) {
        super(XY_PANE, AXIS);
    }

    public DestroyableLineChart(XYPane<T> XY_PANE, Grid GRID, Axis... AXIS) {
        super(XY_PANE, GRID, AXIS);
    }

    public DestroyableLineChart(List<XYPane<T>> XY_PANES, Axis... AXIS) {
        super(XY_PANES, AXIS);
    }

    public DestroyableLineChart(List<XYPane<T>> XY_PANES, Grid GRID, Axis... AXIS) {
        super(XY_PANES, GRID, AXIS);
    }
}
