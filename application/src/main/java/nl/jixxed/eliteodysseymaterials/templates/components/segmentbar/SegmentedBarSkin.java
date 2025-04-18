package nl.jixxed.eliteodysseymaterials.templates.components.segmentbar;

import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SkinBase;
import javafx.util.Callback;
import nl.jixxed.eliteodysseymaterials.builder.PopOverBuilder;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyablePopOver;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableSegmentedBar;
import org.controlsfx.control.SegmentedBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

public class SegmentedBarSkin<T extends SegmentedBar.Segment> extends SkinBase<SegmentedBar<T>> {
    private Map<T, Node> segmentNodes = new HashMap();
    private InvalidationListener buildListener = (it) -> {
        this.buildSegments();
    };
    private WeakInvalidationListener weakBuildListener;
    private InvalidationListener layoutListener;
    private WeakInvalidationListener weakLayoutListener;
    private Map<SegmentedBar.Segment, DestroyablePopOver> popOvers = new HashMap<>();

    public SegmentedBarSkin(SegmentedBar<T> bar) {
        super(bar);
        this.weakBuildListener = new WeakInvalidationListener(this.buildListener);
        this.layoutListener = (it) -> {
            ((SegmentedBar) this.getSkinnable()).requestLayout();
        };
        this.weakLayoutListener = new WeakInvalidationListener(this.layoutListener);
        bar.segmentViewFactoryProperty().addListener(this.weakBuildListener);
        bar.getSegments().addListener(this.weakBuildListener);
        bar.orientationProperty().addListener(this.weakLayoutListener);
        bar.totalProperty().addListener(this.weakBuildListener);

        this.buildSegments();
    }

    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        if (((SegmentedBar) this.getSkinnable()).getOrientation().equals(Orientation.HORIZONTAL)) {
            OptionalDouble maxHeight = this.getChildren().stream().mapToDouble((node) -> {
                return node.prefHeight(-1.0);
            }).max();
            if (maxHeight.isPresent()) {
                return maxHeight.getAsDouble();
            }
        }

        return ((SegmentedBar) this.getSkinnable()).getPrefHeight();
    }

    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        if (((SegmentedBar) this.getSkinnable()).getOrientation().equals(Orientation.VERTICAL)) {
            OptionalDouble maxWidth = this.getChildren().stream().mapToDouble((node) -> {
                return node.prefWidth(height);
            }).max();
            if (maxWidth.isPresent()) {
                return maxWidth.getAsDouble();
            }
        }

        return ((SegmentedBar) this.getSkinnable()).getPrefWidth();
    }

    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return ((SegmentedBar) this.getSkinnable()).getOrientation().equals(Orientation.HORIZONTAL) ? this.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset) : 0.0;
    }

    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return ((SegmentedBar) this.getSkinnable()).getOrientation().equals(Orientation.VERTICAL) ? this.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset) : 0.0;
    }

    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return ((SegmentedBar) this.getSkinnable()).getOrientation().equals(Orientation.HORIZONTAL) ? this.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset) : Double.MAX_VALUE;
    }

    protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return ((SegmentedBar) this.getSkinnable()).getOrientation().equals(Orientation.VERTICAL) ? this.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset) : Double.MAX_VALUE;
    }

    private void buildSegments() {
        this.segmentNodes.values().stream().map(Destroyable.class::cast).forEach(Destroyable::destroy);
        this.segmentNodes.clear();
        this.getChildren().clear();
        List<T> segments = ((SegmentedBar) this.getSkinnable()).getSegments();
        int size = segments.size();
        Callback<T, Node> cellFactory = ((SegmentedBar) this.getSkinnable()).getSegmentViewFactory();

        for (int i = 0; i < size; ++i) {
            T segment = segments.get(i);
            Node segmentNode = (Node) cellFactory.call(segment);
            this.segmentNodes.put(segment, segmentNode);
            this.getChildren().add(segmentNode);
            segmentNode.getStyleClass().add("segment");
            if (i == 0) {
                if (size == 1) {
                    segmentNode.getStyleClass().add("only-segment");
                } else {
                    segmentNode.getStyleClass().add("first-segment");
                }
            } else if (i == size - 1) {
                segmentNode.getStyleClass().add("last-segment");
            } else {
                segmentNode.getStyleClass().add("middle-segment");
            }

            segmentNode.setOnMouseEntered((evt) -> {
                this.showPopOver(segmentNode, segment);
            });
            segmentNode.setOnMouseExited((evt) -> {
                this.hidePopOver(segment);
            });
        }

        ((SegmentedBar) this.getSkinnable()).requestLayout();
    }

    private void showPopOver(Node owner, T segment) {
        Callback<T, Node> infoNodeFactory = ((DestroyableSegmentedBar<T>) this.getSkinnable()).getInfoNodeFactory();
        Node infoNode = null;
        if (infoNodeFactory != null) {
            infoNode = (Node) infoNodeFactory.call(segment);
        }
        if (infoNode != null && this.popOvers.get(segment) == null) {
            DestroyablePopOver popOver = PopOverBuilder.builder()
                    .withDetachable(false)
                    .withHeaderAlwaysVisible(false)
                    .withStyleClass("power-progressbar-popover")
                    .withArrowIndent(0)
                    .withArrowSize(0)
                    .withCornerRadius(0)
                    .withContent((Node & Destroyable) infoNode)
                    .build();
            this.popOvers.put(segment, popOver);
//            popOver.getStyleClass().add("power-progressbar-popover");
//            popOver.setDetachable(false);
//            popOver.setHeaderAlwaysVisible(false);
//            popOver.arrowSizeProperty().set(0);
//            popOver.arrowIndentProperty().set(0);
//            popOver.cornerRadiusProperty().set(0);
//            popOver.setContentNode(infoNode);
            popOver.show(owner, -2.0);
        }

    }

    private void hidePopOver(T segment) {
        final DestroyablePopOver popOver = this.popOvers.get(segment);
        if (popOver != null && popOver.isShowing()) {
            popOver.hide();
            this.popOvers.put(segment, null);
        }
    }

    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        double total = ((SegmentedBar) this.getSkinnable()).getTotal();
        List<T> segments = ((SegmentedBar) this.getSkinnable()).getSegments();
        int size = segments.size();
        double x = contentX;
        double y = contentY + contentHeight;

        for (int i = 0; i < size; ++i) {
            SegmentedBar.Segment segment = (SegmentedBar.Segment) segments.get(i);
            Node segmentNode = (Node) this.segmentNodes.get(segment);
            double segmentValue = segment.getValue();
            double segmentWidth;
            if (((SegmentedBar) this.getSkinnable()).getOrientation().equals(Orientation.HORIZONTAL)) {
                segmentWidth = segmentValue / total * contentWidth;
                segmentNode.resizeRelocate(x, contentY, segmentWidth, contentHeight);
                x += segmentWidth;
            } else {
                segmentWidth = segmentValue / total * contentHeight;
                segmentNode.resizeRelocate(contentX, y - segmentWidth, contentWidth, segmentWidth);
                y -= segmentWidth;
            }
        }

    }

    @Override
    public void dispose() {
        super.dispose();
        this.popOvers.values().forEach(DestroyableComponent::destroy);
        this.segmentNodes.values().stream().map(Destroyable.class::cast).forEach(Destroyable::destroy);
    }
}