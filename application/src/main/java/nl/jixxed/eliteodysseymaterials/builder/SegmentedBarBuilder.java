package nl.jixxed.eliteodysseymaterials.builder;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.util.Callback;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableSegmentedBar;
import org.controlsfx.control.SegmentedBar;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SegmentedBarBuilder<T extends SegmentedBar.Segment & Destroyable> extends AbstractControlBuilder<SegmentedBarBuilder<T>> {
    private final Class<T> clazz;
    private T[] segments;
    private Orientation orientation;
    private Callback<T, Node> infoNodeFactory;
    private Callback<T, Node> segmentViewFactory;

    public static <T extends SegmentedBar.Segment & Destroyable> SegmentedBarBuilder<T> builder(final Class<T> clazz) {
        return new SegmentedBarBuilder<>(clazz);
    }

    public SegmentedBarBuilder<T> withSegments(T... segments) {
        this.segments = segments;
        return this;
    }

    public SegmentedBarBuilder<T> withOrientation(Orientation orientation) {
        this.orientation = orientation;
        return this;
    }

    public <E extends Node & Destroyable> SegmentedBarBuilder<T> withInfoNodeFactory(Callback<T, E> infoNodeFactory) {
        this.infoNodeFactory = (Callback) infoNodeFactory;
        return this;
    }

    public <E extends Node & Destroyable> SegmentedBarBuilder<T> withSegmentViewFactory(Callback<T, E> segmentViewFactory) {
        this.segmentViewFactory = (Callback) segmentViewFactory;
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableSegmentedBar<T> build() {
        final DestroyableSegmentedBar<T> segmentedBar = new DestroyableSegmentedBar<>();
        super.build(segmentedBar);

        if (infoNodeFactory != null) {
            segmentedBar.setInfoNodeFactory(infoNodeFactory);
        }
        if (segmentViewFactory != null) {
            segmentedBar.setSegmentViewFactory(segmentViewFactory);
        }

        if (segments != null) {
            segmentedBar.registerAll(segments);
            segmentedBar.getSegments().addAll(this.segments);
        }

        if (orientation != null) {
            segmentedBar.setOrientation(orientation);
        }
        return segmentedBar;
    }

}
