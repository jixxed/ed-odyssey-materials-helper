package nl.jixxed.eliteodysseymaterials.templates;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.css.*;
import javafx.css.converter.EnumConverter;
import javafx.css.converter.SizeConverter;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static javafx.geometry.Orientation.HORIZONTAL;
import static javafx.geometry.Orientation.VERTICAL;
//TODO removeme. workaround until https://bugs.openjdk.org/browse/JDK-8377153 is implemented in a newer version than 25.0.2. https://github.com/openjdk/jfx/pull/2077/changes
public class PatchedFlowPane extends Pane {

    /* ******************************************************************
     *  BEGIN static methods
     ********************************************************************/
    private static final String MARGIN_CONSTRAINT = "PatchedFlowPane-margin";

    /**
     * Sets the margin for the child when contained by a PatchedFlowPane.
     * If set, the PatchedFlowPane will layout it out with the margin space around it.
     * Setting the value to null will remove the constraint.
     * @param child the child node of a PatchedFlowPane
     * @param value the margin of space around the child
     */
    public static void setMargin(Node child, Insets value) {
        //setConstraint(child, MARGIN_CONSTRAINT, value);
        // call through reflection
        try{
            Method method = Pane.class.getDeclaredMethod(
                    "setConstraint",
                    Node.class,
                    Object.class,
                    Object.class
            );
            method.setAccessible(true);
            method.invoke(null, child, MARGIN_CONSTRAINT, value);
        }catch (NoSuchMethodException |InvocationTargetException| IllegalAccessException e) {

        }
    }

    /**
     * Returns the child's margin constraint if set.
     * @param child the child node of a PatchedFlowPane
     * @return the margin for the child or null if no margin was set
     */
    public static Insets getMargin(Node child) {
//        return (Insets)getConstraint(child, MARGIN_CONSTRAINT);
        try{
            Method method = Pane.class.getDeclaredMethod(
                    "getConstraint",
                    Node.class,
                    Object.class
            );
            method.setAccessible(true);
            return (Insets) method.invoke(null, child, MARGIN_CONSTRAINT);
        }catch (NoSuchMethodException |InvocationTargetException| IllegalAccessException e) {

        }
        return null;
    }

    private static final Callback<Node, Insets> marginAccessor = n -> getMargin(n);

    /**
     * Removes all PatchedFlowPane constraints from the child node.
     * @param child the child node
     */
    public static void clearConstraints(Node child) {
        setMargin(child, null);
    }

    /* ******************************************************************
     *  END static methods
     ********************************************************************/

    /**
     * Creates a horizontal PatchedFlowPane layout with hgap/vgap = 0.
     */
    public PatchedFlowPane() {
        super();
    }

    /**
     * Creates a PatchedFlowPane layout with the specified orientation and hgap/vgap = 0.
     * @param orientation the direction the tiles should flow &amp; wrap
     */
    public PatchedFlowPane(Orientation orientation) {
        this();
        setOrientation(orientation);
    }

    /**
     * Creates a horizontal PatchedFlowPane layout with the specified hgap/vgap.
     * @param hgap the amount of horizontal space between each tile
     * @param vgap the amount of vertical space between each tile
     */
    public PatchedFlowPane(double hgap, double vgap) {
        this();
        setHgap(hgap);
        setVgap(vgap);
    }

    /**
     * Creates a PatchedFlowPane layout with the specified orientation and hgap/vgap.
     * @param orientation the direction the tiles should flow &amp; wrap
     * @param hgap the amount of horizontal space between each tile
     * @param vgap the amount of vertical space between each tile
     */
    public PatchedFlowPane(Orientation orientation, double hgap, double vgap) {
        this();
        setOrientation(orientation);
        setHgap(hgap);
        setVgap(vgap);
    }

    /**
     * Creates a horizontal PatchedFlowPane layout with hgap/vgap = 0.
     * @param children The initial set of children for this pane.
     * @since JavaFX 8.0
     */
    public PatchedFlowPane(Node... children) {
        super();
        getChildren().addAll(children);
    }

    /**
     * Creates a PatchedFlowPane layout with the specified orientation and hgap/vgap = 0.
     * @param orientation the direction the tiles should flow &amp; wrap
     * @param children The initial set of children for this pane.
     * @since JavaFX 8.0
     */
    public PatchedFlowPane(Orientation orientation, Node... children) {
        this();
        setOrientation(orientation);
        getChildren().addAll(children);
    }

    /**
     * Creates a horizontal PatchedFlowPane layout with the specified hgap/vgap.
     * @param hgap the amount of horizontal space between each tile
     * @param vgap the amount of vertical space between each tile
     * @param children The initial set of children for this pane.
     * @since JavaFX 8.0
     */
    public PatchedFlowPane(double hgap, double vgap, Node... children) {
        this();
        setHgap(hgap);
        setVgap(vgap);
        getChildren().addAll(children);
    }

    /**
     * Creates a PatchedFlowPane layout with the specified orientation and hgap/vgap.
     * @param orientation the direction the tiles should flow &amp; wrap
     * @param hgap the amount of horizontal space between each tile
     * @param vgap the amount of vertical space between each tile
     * @param children The initial set of children for this pane.
     * @since JavaFX 8.0
     */
    public PatchedFlowPane(Orientation orientation, double hgap, double vgap, Node... children) {
        this();
        setOrientation(orientation);
        setHgap(hgap);
        setVgap(vgap);
        getChildren().addAll(children);
    }

    /**
     * The orientation of this PatchedFlowPane.
     * A horizontal PatchedFlowPane lays out children left to right, wrapping at the
     * PatchedFlowPane's width boundary.   A vertical PatchedFlowPane lays out children top to
     * bottom, wrapping at the PatchedFlowPane's height.
     * The default is horizontal.
     * @return the orientation of this PatchedFlowPane
     */
    public final ObjectProperty<Orientation> orientationProperty() {
        if (orientation == null) {
            orientation = new StyleableObjectProperty(HORIZONTAL) {
                @Override
                public void invalidated() {
                    requestLayout();
                }

                @Override
                public CssMetaData<PatchedFlowPane, Orientation> getCssMetaData() {
                    return PatchedFlowPane.StyleableProperties.ORIENTATION;
                }

                @Override
                public Object getBean() {
                    return PatchedFlowPane.this;
                }

                @Override
                public String getName() {
                    return "orientation";
                }
            };
        }
        return orientation;
    }

    private ObjectProperty<Orientation> orientation;
    public final void setOrientation(Orientation value) { orientationProperty().set(value); }
    public final Orientation getOrientation() { return orientation == null ? HORIZONTAL : orientation.get();  }

    /**
     * The amount of horizontal space between each node in a horizontal PatchedFlowPane
     * or the space between columns in a vertical PatchedFlowPane.
     * @return the amount of horizontal space between each node in a horizontal
     * PatchedFlowPane or the space between columns in a vertical PatchedFlowPane
     */
    public final DoubleProperty hgapProperty() {
        if (hgap == null) {
            hgap = new StyleableDoubleProperty() {

                @Override
                public void invalidated() {
                    requestLayout();
                }

                @Override
                public CssMetaData<PatchedFlowPane, Number> getCssMetaData() {
                    return PatchedFlowPane.StyleableProperties.HGAP;
                }

                @Override
                public Object getBean() {
                    return PatchedFlowPane.this;
                }

                @Override
                public String getName() {
                    return "hgap";
                }
            };
        }
        return hgap;
    }

    private DoubleProperty hgap;
    public final void setHgap(double value) { hgapProperty().set(value); }
    public final double getHgap() { return hgap == null ? 0 : hgap.get(); }

    /**
     * The amount of vertical space between each node in a vertical PatchedFlowPane
     * or the space between rows in a horizontal PatchedFlowPane.
     * @return the amount of vertical space between each node in a vertical
     * PatchedFlowPane or the space between rows in a horizontal PatchedFlowPane
     */
    public final DoubleProperty vgapProperty() {
        if (vgap == null) {
            vgap = new StyleableDoubleProperty() {
                @Override
                public void invalidated() {
                    requestLayout();
                }

                @Override
                public CssMetaData<PatchedFlowPane, Number> getCssMetaData() {
                    return PatchedFlowPane.StyleableProperties.VGAP;
                }

                @Override
                public Object getBean() {
                    return PatchedFlowPane.this;
                }

                @Override
                public String getName() {
                    return "vgap";
                }
            };
        }
        return vgap;
    }

    private DoubleProperty vgap;
    public final void setVgap(double value) { vgapProperty().set(value); }
    public final double getVgap() { return vgap == null ? 0 : vgap.get(); }


    /**
     * The preferred width where content should wrap in a horizontal PatchedFlowPane or
     * the preferred height where content should wrap in a vertical PatchedFlowPane.
     * <p>
     * This value is used only to compute the preferred size of the PatchedFlowPane and may
     * not reflect the actual width or height, which may change if the PatchedFlowPane is
     * resized to something other than its preferred size.
     * <p>
     * Applications should initialize this value to define a reasonable span
     * for wrapping the content.
     *
     * @return the preferred width where content should wrap in a horizontal
     * PatchedFlowPane or the preferred height where content should wrap in a vertical
     * PatchedFlowPane
     */
    public final DoubleProperty prefWrapLengthProperty() {
        if (prefWrapLength == null) {
            prefWrapLength = new DoublePropertyBase(400) {
                @Override
                protected void invalidated() {
                    requestLayout();
                }

                @Override
                public Object getBean() {
                    return PatchedFlowPane.this;
                }

                @Override
                public String getName() {
                    return "prefWrapLength";
                }
            };
        }
        return prefWrapLength;
    }
    private DoubleProperty prefWrapLength;
    public final void setPrefWrapLength(double value) { prefWrapLengthProperty().set(value); }
    public final double getPrefWrapLength() { return prefWrapLength == null ? 400 : prefWrapLength.get(); }


    /**
     * The overall alignment of the PatchedFlowPane's content within its width and height.
     * <p>For a horizontal PatchedFlowPane, each row will be aligned within the PatchedFlowPane's width
     * using the alignment's hpos value, and the rows will be aligned within the
     * PatchedFlowPane's height using the alignment's vpos value.
     * <p>For a vertical PatchedFlowPane, each column will be aligned within the PatchedFlowPane's height
     * using the alignment's vpos value, and the columns will be aligned within the
     * PatchedFlowPane's width using the alignment's hpos value.
     * @return the overall alignment of the PatchedFlowPane's content within its width
     * and height
     */
    public final ObjectProperty<Pos> alignmentProperty() {
        if (alignment == null) {
            alignment = new StyleableObjectProperty<Pos>(Pos.TOP_LEFT) {

                @Override
                public void invalidated() {
                    requestLayout();
                }

                @Override
                public CssMetaData<PatchedFlowPane, Pos> getCssMetaData() {
                    return PatchedFlowPane.StyleableProperties.ALIGNMENT;
                }

                @Override
                public Object getBean() {
                    return PatchedFlowPane.this;
                }

                @Override
                public String getName() {
                    return "alignment";
                }
            };
        }
        return alignment;
    }

    private ObjectProperty<Pos> alignment;
    public final void setAlignment(Pos value) { alignmentProperty().set(value); }
    public final Pos getAlignment() { return alignment == null ? Pos.TOP_LEFT : alignment.get(); }
    private Pos getAlignmentInternal() {
        Pos localPos = getAlignment();
        return localPos == null ? Pos.TOP_LEFT : localPos;
    }

    /**
     * The horizontal alignment of nodes within each column of a vertical PatchedFlowPane.
     * The property is ignored for horizontal PatchedFlowPanes.
     * @return the horizontal alignment of nodes within each column of a
     * vertical PatchedFlowPane
     */
    public final ObjectProperty<HPos> columnHalignmentProperty() {
        if (columnHalignment == null) {
            columnHalignment = new StyleableObjectProperty<HPos>(HPos.LEFT) {

                @Override
                public void invalidated() {
                    requestLayout();
                }

                @Override
                public CssMetaData<PatchedFlowPane, HPos> getCssMetaData() {
                    return PatchedFlowPane.StyleableProperties.COLUMN_HALIGNMENT;
                }

                @Override
                public Object getBean() {
                    return PatchedFlowPane.this;
                }

                @Override
                public String getName() {
                    return "columnHalignment";
                }
            };
        }
        return columnHalignment;
    }

    private ObjectProperty<HPos> columnHalignment;
    public final void setColumnHalignment(HPos value) { columnHalignmentProperty().set(value); }
    public final HPos getColumnHalignment() { return columnHalignment == null ? HPos.LEFT : columnHalignment.get(); }
    private HPos getColumnHalignmentInternal() {
        HPos localPos = getColumnHalignment();
        return localPos == null ? HPos.LEFT : localPos;
    }

    /**
     * The vertical alignment of nodes within each row of a horizontal PatchedFlowPane.
     * If this property is set to VPos.BASELINE, then the PatchedFlowPane will always
     * resize children to their preferred heights, rather than expanding heights
     * to fill the row height.
     * The property is ignored for vertical PatchedFlowPanes.
     * @return the vertical alignment of nodes within each row of a horizontal
     * PatchedFlowPane
     */
    public final ObjectProperty<VPos> rowValignmentProperty() {
        if (rowValignment == null) {
            rowValignment = new StyleableObjectProperty<VPos>(VPos.CENTER) {
                @Override
                public void invalidated() {
                    requestLayout();
                }

                @Override
                public CssMetaData<PatchedFlowPane, VPos> getCssMetaData() {
                    return PatchedFlowPane.StyleableProperties.ROW_VALIGNMENT;
                }

                @Override
                public Object getBean() {
                    return PatchedFlowPane.this;
                }

                @Override
                public String getName() {
                    return "rowValignment";
                }
            };
        }
        return rowValignment;
    }

    private ObjectProperty<VPos> rowValignment;
    public final void setRowValignment(VPos value) { rowValignmentProperty().set(value); }
    public final VPos getRowValignment() { return rowValignment == null ? VPos.CENTER : rowValignment.get(); }
    private VPos getRowValignmentInternal() {
        VPos localPos =  getRowValignment();
        return localPos == null ? VPos.CENTER : localPos;
    }

    @Override public Orientation getContentBias() {
        return getOrientation();
    }

    @Override protected double computeMinWidth(double height) {
        if (getContentBias() == HORIZONTAL) {
            double maxPref = 0;
            final List<Node> children = getChildren();
            for (int i=0, size=children.size(); i<size; i++) {
                Node child = children.get(i);
                if (child.isManaged()) {
                    maxPref = Math.max(maxPref, child.prefWidth(-1));
                }
            }
            final Insets insets = getInsets();
            return insets.getLeft() + snapSizeX(maxPref) + insets.getRight();
        }
        return computePrefWidth(height);
    }

    @Override protected double computeMinHeight(double width) {
        if (getContentBias() == VERTICAL) {
            double maxPref = 0;
            final List<Node> children = getChildren();
            for (int i=0, size=children.size(); i<size; i++) {
                Node child = children.get(i);
                if (child.isManaged()) {
                    maxPref = Math.max(maxPref, child.prefHeight(-1));
                }
            }
            final Insets insets = getInsets();
            return insets.getTop() + snapSizeY(maxPref) + insets.getBottom();
        }
        return computePrefHeight(width);
    }

    @Override protected double computePrefWidth(double forHeight) {
        final Insets insets = getInsets();
        if (getOrientation() == HORIZONTAL) {
            // horizontal
            double maxRunWidth = getPrefWrapLength();
            List<PatchedFlowPane.Run> hruns = getRuns(maxRunWidth);
            double w = computeContentWidth(hruns);
            w = getPrefWrapLength() > w ? getPrefWrapLength() : w;
            return insets.getLeft() + snapSizeX(w) + insets.getRight();
        } else {
            // vertical
            double maxRunHeight = forHeight != -1?
                    forHeight - insets.getTop() - insets.getBottom() : getPrefWrapLength();
            List<PatchedFlowPane.Run> vruns = getRuns(maxRunHeight);
            return insets.getLeft() + computeContentWidth(vruns) + insets.getRight();
        }
    }

    @Override protected double computePrefHeight(double forWidth) {
        final Insets insets = getInsets();
        if (getOrientation() == HORIZONTAL) {
            // horizontal
            double maxRunWidth = forWidth != -1?
                    forWidth - insets.getLeft() - insets.getRight() : getPrefWrapLength();
            List<PatchedFlowPane.Run> hruns = getRuns(maxRunWidth);
            return insets.getTop() + computeContentHeight(hruns) + insets.getBottom();
        } else {
            // vertical
            double maxRunHeight = getPrefWrapLength();
            List<PatchedFlowPane.Run> vruns = getRuns(maxRunHeight);
            double h = computeContentHeight(vruns);
            h = getPrefWrapLength() > h ? getPrefWrapLength() : h;
            return insets.getTop() + snapSizeY(h) + insets.getBottom();
        }
    }

    @Override public void requestLayout() {
        if (!computingRuns) {
            runs = null;
        }
        super.requestLayout();
    }

    private List<PatchedFlowPane.Run> runs = null;
    private double lastMaxRunLength = -1;
    boolean computingRuns = false;

    private List<PatchedFlowPane.Run> getRuns(double maxRunLength) {
        if (runs == null || maxRunLength != lastMaxRunLength) {
            computingRuns = true;
            lastMaxRunLength = maxRunLength;
            runs = new ArrayList();
            double runLength = 0;
            double runOffset = 0;
            PatchedFlowPane.Run run = new PatchedFlowPane.Run();
            double vgap = snapSpaceY(this.getVgap());
            double hgap = snapSpaceX(this.getHgap());

            final List<Node> children = getChildren();
            for (int i=0, size=children.size(); i<size; i++) {
                Node child = children.get(i);
                if (child.isManaged()) {
                    PatchedFlowPane.LayoutRect nodeRect = new PatchedFlowPane.LayoutRect();
                    nodeRect.node = child;
                    Insets margin = getMargin(child);
//                    nodeRect.width = computeChildPrefAreaWidth(child, margin);
//                    nodeRect.height = computeChildPrefAreaHeight(child, -1D, margin, nodeRect.width, false);
                    try{
                        Method wmethod = Region.class.getDeclaredMethod(
                                "computeChildPrefAreaWidth",
                                Node.class,
                                Insets.class
                        );
                        Method hmethod = Region.class.getDeclaredMethod(
                                "computeChildPrefAreaHeight",
                                Node.class,
                                double.class,
                                Insets.class,
                                double.class,
                                boolean.class
                        );
                        wmethod.setAccessible(true);
                        hmethod.setAccessible(true);
                        nodeRect.width = (double)wmethod.invoke(this, child, margin);
                        nodeRect.height = (double)hmethod.invoke(this, child, -1D, margin, nodeRect.width, false);
                    }catch (NoSuchMethodException |InvocationTargetException| IllegalAccessException e) {

                    }


                    double nodeLength = getOrientation() == HORIZONTAL ? nodeRect.width : nodeRect.height;
                    if (runLength + nodeLength > maxRunLength && runLength > 0) {
                        // wrap to next run *unless* its the only node in the run
                        normalizeRun(run, runOffset);
                        if (getOrientation() == HORIZONTAL) {
                            // horizontal
                            runOffset += run.height + vgap;
                        } else {
                            // vertical
                            runOffset += run.width + hgap;
                        }
                        runs.add(run);
                        runLength = 0;
                        run = new PatchedFlowPane.Run();
                    }
                    if (getOrientation() == HORIZONTAL) {
                        // horizontal
                        nodeRect.x = runLength;
                        runLength += nodeRect.width + hgap;
                    } else {
                        // vertical
                        nodeRect.y = runLength;
                        runLength += nodeRect.height + vgap;
                    }
                    run.rects.add(nodeRect);
                }

            }
            // insert last run
            normalizeRun(run, runOffset);
            runs.add(run);
            computingRuns = false;
        }
        return runs;
    }

    private void normalizeRun(final PatchedFlowPane.Run run, double runOffset) {
        if (getOrientation() == HORIZONTAL) {
            // horizontal
            ArrayList<Node> rownodes = new ArrayList();
            run.width = (run.rects.size()-1)*snapSpaceX(getHgap());
            for (int i=0, max=run.rects.size(); i<max; i++) {
                PatchedFlowPane.LayoutRect lrect = run.rects.get(i);
                rownodes.add(lrect.node);
                run.width += lrect.width;
                lrect.y = runOffset;
            }
//            run.height = computeMaxPrefAreaHeight(rownodes, marginAccessor, getRowValignment());
//            run.height = computeMaxPrefAreaHeight(rownodes, marginAccessor, run.width, false, getRowValignment());
//            run.baselineOffset = getRowValignment() == VPos.BASELINE?
//                    getAreaBaselineOffset(rownodes, marginAccessor, i -> run.rects.get(i).width, run.height, true) : 0;
            try{
                Method hmethod = Region.class.getDeclaredMethod(
                        "computeMaxPrefAreaHeight",
                        List.class, Callback.class, double.class,
                boolean.class, VPos.class
                );
                Method bmethod = Region.class.getDeclaredMethod(
                        "getAreaBaselineOffset",
                        List.class,
                        Callback.class,
                        Function.class,
                        double.class,
                        boolean.class
                );
                hmethod.setAccessible(true);
                bmethod.setAccessible(true);
                run.height =(double) hmethod.invoke(this, rownodes, marginAccessor, run.width, false, getRowValignment());
                run.baselineOffset = (getRowValignment() == VPos.BASELINE)?(double)  bmethod.invoke(this, rownodes, marginAccessor, (Function<Integer, Double>)i -> run.rects.get(i).width, run.height, true):0D;
            }catch (NoSuchMethodException |InvocationTargetException| IllegalAccessException e) {

            }
//            try{
//                Method method = Pane.class.getDeclaredMethod(
//                        "getConstraint",
//                        Node.class,
//                        Object.class
//                );
//                method.setAccessible(true);
//                return (Insets) method.invoke(null, child, MARGIN_CONSTRAINT);
//            }catch (NoSuchMethodException |InvocationTargetException| IllegalAccessException e) {
//
//            }
//            return null;

        } else {
            // vertical
            run.height = (run.rects.size()-1)*snapSpaceY(getVgap());
            double maxw = 0;
            for (int i=0, max=run.rects.size(); i<max; i++) {
                PatchedFlowPane.LayoutRect lrect = run.rects.get(i);
                run.height += lrect.height;
                lrect.x = runOffset;
                maxw = Math.max(maxw, lrect.width);
            }

            run.width = maxw;
            run.baselineOffset = run.height;
        }
    }

    private double computeContentWidth(List<PatchedFlowPane.Run> runs) {
        double cwidth = getOrientation() == HORIZONTAL ? 0 : (runs.size()-1)*snapSpaceX(getHgap());
        for (int i=0, max=runs.size(); i<max; i++) {
            PatchedFlowPane.Run run = runs.get(i);
            if (getOrientation() == HORIZONTAL) {
                cwidth = Math.max(cwidth, run.width);
            } else {
                // vertical
                cwidth += run.width;
            }
        }
        return cwidth;
    }

    private double computeContentHeight(List<PatchedFlowPane.Run> runs) {
        double cheight = getOrientation() == VERTICAL ? 0 : (runs.size()-1)*snapSpaceY(getVgap());
        for (int i=0, max=runs.size(); i<max; i++) {
            PatchedFlowPane.Run run = runs.get(i);
            if (getOrientation() == VERTICAL) {
                cheight = Math.max(cheight, run.height);
            } else {
                // horizontal
                cheight += run.height;
            }
        }
        return cheight;
    }

    @Override protected void layoutChildren() {
        final Insets insets = getInsets();
        final double width = getWidth();
        final double height = getHeight();
        final double top = insets.getTop();
        final double left = insets.getLeft();
        final double bottom = insets.getBottom();
        final double right = insets.getRight();
        final double insideWidth = width - left - right;
        final double insideHeight = height - top - bottom;

        //REMIND(aim): need to figure out how to cache the runs to avoid over-calculation
        final List<PatchedFlowPane.Run> runs = getRuns(getOrientation() == HORIZONTAL ? insideWidth : insideHeight);

        // Now that the nodes are broken into runs, figure out alignments
        for (int i=0, max=runs.size(); i<max; i++) {
            final PatchedFlowPane.Run run = runs.get(i);
//            final double xoffset = left + computeXOffset(insideWidth,
//                    getOrientation() == HORIZONTAL ? run.width : computeContentWidth(runs),
//                    getAlignmentInternal().getHpos());
//            final double yoffset = top + computeYOffset(insideHeight,
//                    getOrientation() == VERTICAL ? run.height : computeContentHeight(runs),
//                    getAlignmentInternal().getVpos());
            double xoffset = 0;
            double yoffset = 0;
            try{
                Method xmethod = Region.class.getDeclaredMethod(
                        "computeXOffset",
                        double.class,
                        double.class,
                        HPos.class
                );
                xmethod.setAccessible(true);
                 xoffset =  (double) xmethod.invoke(this, insideWidth,
                        getOrientation() == HORIZONTAL ? run.width : computeContentWidth(runs),
                        getAlignmentInternal().getHpos());
                Method ymethod = Region.class.getDeclaredMethod(
                        "computeYOffset",
                        double.class,
                        double.class,
                        VPos.class
                );
                ymethod.setAccessible(true);
                yoffset =  (double) ymethod.invoke(this, insideHeight,
                        getOrientation() == VERTICAL ? run.height : computeContentHeight(runs),
                        getAlignmentInternal().getVpos());
            }catch (NoSuchMethodException |InvocationTargetException| IllegalAccessException e) {

            }
//            try{
//                Method method = Pane.class.getDeclaredMethod(
//                        "getConstraint",
//                        Node.class,
//                        Object.class
//                );
//                method.setAccessible(true);
//                return (Insets) method.invoke(null, child, MARGIN_CONSTRAINT);
//            }catch (NoSuchMethodException |InvocationTargetException| IllegalAccessException e) {
//
//            }
//            return null;


            for (int j = 0; j < run.rects.size(); j++) {
                final PatchedFlowPane.LayoutRect lrect = run.rects.get(j);
//              System.out.println("PatchedFlowPane.layout: run="+i+" "+run.width+"x"+run.height+" xoffset="+xoffset+" yoffset="+yoffset+" lrect="+lrect);
                final double x = xoffset + lrect.x;
                final double y = yoffset + lrect.y;
                layoutInArea(lrect.node, x, y,
                        getOrientation() == HORIZONTAL? lrect.width : run.width,
                        getOrientation() == VERTICAL? lrect.height : run.height,
                        run.baselineOffset, getMargin(lrect.node),
                        getColumnHalignmentInternal(), getRowValignmentInternal());
            }
        }
    }

    /* *************************************************************************
     *                                                                         *
     *                         Stylesheet Handling                             *
     *                                                                         *
     **************************************************************************/


    /*
     * Super-lazy instantiation pattern from Bill Pugh.
     */
    private static class StyleableProperties {

        private static final CssMetaData<PatchedFlowPane,Pos> ALIGNMENT =
                new CssMetaData<>("-fx-alignment",
                        new EnumConverter<>(Pos.class), Pos.TOP_LEFT) {

                    @Override
                    public boolean isSettable(PatchedFlowPane node) {
                        return node.alignment == null || !node.alignment.isBound();
                    }

                    @Override
                    public StyleableProperty<Pos> getStyleableProperty(PatchedFlowPane node) {
                        return (StyleableProperty<Pos>)node.alignmentProperty();
                    }

                };

        private static final CssMetaData<PatchedFlowPane,HPos> COLUMN_HALIGNMENT =
                new CssMetaData<>("-fx-column-halignment",
                        new EnumConverter<>(HPos.class), HPos.LEFT) {

                    @Override
                    public boolean isSettable(PatchedFlowPane node) {
                        return node.columnHalignment == null || !node.columnHalignment.isBound();
                    }

                    @Override
                    public StyleableProperty<HPos> getStyleableProperty(PatchedFlowPane node) {
                        return (StyleableProperty<HPos>)node.columnHalignmentProperty();
                    }

                };

        private static final CssMetaData<PatchedFlowPane,Number> HGAP =
                new CssMetaData<>("-fx-hgap",
                        SizeConverter.getInstance(), 0.0){

                    @Override
                    public boolean isSettable(PatchedFlowPane node) {
                        return node.hgap == null || !node.hgap.isBound();
                    }

                    @Override
                    public StyleableProperty<Number> getStyleableProperty(PatchedFlowPane node) {
                        return (StyleableProperty<Number>)node.hgapProperty();
                    }

                };

        private static final CssMetaData<PatchedFlowPane,VPos> ROW_VALIGNMENT =
                new CssMetaData<>("-fx-row-valignment",
                        new EnumConverter<>(VPos.class), VPos.CENTER) {

                    @Override
                    public boolean isSettable(PatchedFlowPane node) {
                        return node.rowValignment == null || !node.rowValignment.isBound();
                    }

                    @Override
                    public StyleableProperty<VPos> getStyleableProperty(PatchedFlowPane node) {
                        return (StyleableProperty<VPos>)node.rowValignmentProperty();
                    }

                };

        private static final CssMetaData<PatchedFlowPane,Orientation> ORIENTATION =
                new CssMetaData<>("-fx-orientation",
                        new EnumConverter<>(Orientation.class),
                        Orientation.HORIZONTAL) {

                    @Override
                    public Orientation getInitialValue(PatchedFlowPane node) {
                        // A vertical flow pane should remain vertical
                        return node.getOrientation();
                    }

                    @Override
                    public boolean isSettable(PatchedFlowPane node) {
                        return node.orientation == null || !node.orientation.isBound();
                    }

                    @Override
                    public StyleableProperty<Orientation> getStyleableProperty(PatchedFlowPane node) {
                        return (StyleableProperty<Orientation>)node.orientationProperty();
                    }

                };

        private static final CssMetaData<PatchedFlowPane,Number> VGAP =
                new CssMetaData<>("-fx-vgap",
                        SizeConverter.getInstance(), 0.0){

                    @Override
                    public boolean isSettable(PatchedFlowPane node) {
                        return node.vgap == null || !node.vgap.isBound();
                    }

                    @Override
                    public StyleableProperty<Number> getStyleableProperty(PatchedFlowPane node) {
                        return (StyleableProperty<Number>)node.vgapProperty();
                    }

                };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;
        static {

            final List<CssMetaData<? extends Styleable, ?>> styleables =
                    new ArrayList<>(Region.getClassCssMetaData());
            styleables.add(ALIGNMENT);
            styleables.add(COLUMN_HALIGNMENT);
            styleables.add(HGAP);
            styleables.add(ROW_VALIGNMENT);
            styleables.add(ORIENTATION);
            styleables.add(VGAP);

            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }


    /**
     * Gets the {@code CssMetaData} associated with this class, which may include the
     * {@code CssMetaData} of its superclasses.
     * @return the {@code CssMetaData}
     * @since JavaFX 8.0
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return PatchedFlowPane.StyleableProperties.STYLEABLES;
    }

    /**
     * {@inheritDoc}
     *
     * @since JavaFX 8.0
     */


    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }

    //REMIND(aim); replace when we get mutable rects
    private static class LayoutRect {
        public Node node;
        double x;
        double y;
        double width;
        double height;

        @Override public String toString() {
            return "LayoutRect node id="+node.getId()+" "+x+","+y+" "+width+"x"+height;
        }
    }

    private static class Run {
        ArrayList<PatchedFlowPane.LayoutRect> rects = new ArrayList();
        double width;
        double height;
        double baselineOffset;
    }
}
