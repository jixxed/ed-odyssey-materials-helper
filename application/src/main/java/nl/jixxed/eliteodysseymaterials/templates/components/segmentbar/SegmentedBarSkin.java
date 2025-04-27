package nl.jixxed.eliteodysseymaterials.templates.components.segmentbar;

import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SkinBase;
import javafx.util.Callback;
import nl.jixxed.eliteodysseymaterials.builder.PopOverBuilder;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableParent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyablePopOver;
import org.controlsfx.control.PopOver.ArrowLocation;
import org.controlsfx.control.SegmentedBar;

import java.util.*;

public class SegmentedBarSkin<T extends SegmentedBar.Segment> extends SkinBase<SegmentedBar<T>> {
    private Map<T, Node> segmentNodes = new HashMap();
    private InvalidationListener buildListener = (it) -> this.buildSegments();
    private WeakInvalidationListener weakBuildListener;
    private InvalidationListener layoutListener;
    private WeakInvalidationListener weakLayoutListener;
    private DestroyablePopOver popOver;

    public SegmentedBarSkin(SegmentedBar<T> bar) {
        super(bar);
        this.weakBuildListener = new WeakInvalidationListener(this.buildListener);
        this.layoutListener = (it) -> ((SegmentedBar) this.getSkinnable()).requestLayout();
        this.weakLayoutListener = new WeakInvalidationListener(this.layoutListener);
        bar.segmentViewFactoryProperty().addListener(this.weakBuildListener);
        bar.getSegments().addListener(this.weakBuildListener);
        bar.orientationProperty().addListener(this.weakLayoutListener);
        bar.totalProperty().addListener(this.weakBuildListener);
        bar.orientationProperty().addListener((it) -> {
            if (this.popOver != null) {
                switch (bar.getOrientation()) {
                    case HORIZONTAL:
                        this.popOver.setArrowLocation(ArrowLocation.BOTTOM_CENTER);
                        break;
                    case VERTICAL:
                        this.popOver.setArrowLocation(ArrowLocation.RIGHT_CENTER);
                }

            }
        });
        this.buildSegments();
    }

    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        if (((SegmentedBar) this.getSkinnable()).getOrientation().equals(Orientation.HORIZONTAL)) {
            OptionalDouble maxHeight = this.getChildren().stream().mapToDouble((node) -> node.prefHeight((double) -1.0F)).max();
            if (maxHeight.isPresent()) {
                return maxHeight.getAsDouble();
            }
        }

        return ((SegmentedBar) this.getSkinnable()).getPrefHeight();
    }

    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        if (((SegmentedBar) this.getSkinnable()).getOrientation().equals(Orientation.VERTICAL)) {
            OptionalDouble maxWidth = this.getChildren().stream().mapToDouble((node) -> node.prefWidth(height)).max();
            if (maxWidth.isPresent()) {
                return maxWidth.getAsDouble();
            }
        }

        return ((SegmentedBar) this.getSkinnable()).getPrefWidth();
    }

    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return ((SegmentedBar) this.getSkinnable()).getOrientation().equals(Orientation.HORIZONTAL) ? this.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset) : (double) 0.0F;
    }

    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return ((SegmentedBar) this.getSkinnable()).getOrientation().equals(Orientation.VERTICAL) ? this.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset) : (double) 0.0F;
    }

    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return ((SegmentedBar) this.getSkinnable()).getOrientation().equals(Orientation.HORIZONTAL) ? this.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset) : Double.MAX_VALUE;
    }

    protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return ((SegmentedBar) this.getSkinnable()).getOrientation().equals(Orientation.VERTICAL) ? this.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset) : Double.MAX_VALUE;
    }

    private <E extends Node & Destroyable> void buildSegments() {
        this.segmentNodes.values().stream()
                .map(DestroyableParent.class::cast)
                .forEach(DestroyableParent::destroy);
        this.segmentNodes.clear();
        this.getChildren().clear();
        List<T> segments = ((SegmentedBar) this.getSkinnable()).getSegments();
        int size = segments.size();
        Callback<T, E> cellFactory = ((SegmentedBar) this.getSkinnable()).getSegmentViewFactory();

        for (int i = 0; i < size; ++i) {
            T segment = segments.get(i);
            E segmentNode = cellFactory.call(segment);
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

            segmentNode.addEventBinding(segmentNode.onMouseEnteredProperty(), (evt) -> this.showPopOver(segmentNode, segment));
            segmentNode.addEventBinding(segmentNode.onMouseExitedProperty(), (evt) -> this.hidePopOver());
        }

        ((SegmentedBar) this.getSkinnable()).requestLayout();
    }

    private <E extends Node & Destroyable> void showPopOver(Node owner, T segment) {
        Callback<T, E> infoNodeFactory = ((SegmentedBar) this.getSkinnable()).getInfoNodeFactory();
        E infoNode = null;
        if (infoNodeFactory != null) {
            infoNode = infoNodeFactory.call(segment);
        }

        if (infoNode != null) {
            this.popOver = PopOverBuilder.builder()
                    .withContent(infoNode)
                    .withArrowLocation(ArrowLocation.BOTTOM_CENTER)
                    .withDetachable(false)
                    .withArrowSize(6)
                    .withCornerRadius(3)
                    .withAutoHide(true)
                    .withAnimated(false)
                    .withAutoFix(false)
                    .withDestroyOnHide(true)
                    .build();

            this.popOver.show(owner, -2D);
        }

    }

    private void hidePopOver() {
        if (this.popOver != null && this.popOver.isShowing()) {
            this.popOver.hide();
            this.popOver = null;
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
            if (((SegmentedBar) this.getSkinnable()).getOrientation().equals(Orientation.HORIZONTAL)) {
                double segmentWidth = segmentValue / total * contentWidth;
                segmentNode.resizeRelocate(x, contentY, segmentWidth, contentHeight);
                x += segmentWidth;
            } else {
                double segmentHeight = segmentValue / total * contentHeight;
                segmentNode.resizeRelocate(contentX, y - segmentHeight, contentWidth, segmentHeight);
                y -= segmentHeight;
            }
        }

    }

    @Override
    public void dispose() {
        super.dispose();
        if (this.popOver != null) {
            popOver.destroy();
        }
        this.segmentNodes.values().stream()
                .filter(Objects::nonNull)
                .map(DestroyableParent.class::cast)
                .forEach(DestroyableParent::destroy);
        this.segmentNodes.clear();
        this.getChildren().clear();
    }
}
