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

    public SegmentedBarBuilder<T> withInfoNodeFactory(Callback<T, Node> infoNodeFactory) {
        this.infoNodeFactory = infoNodeFactory;
        return this;
    }
    public SegmentedBarBuilder<T>  withSegmentViewFactory(Callback<T, Node> segmentViewFactory) {
        this.segmentViewFactory = segmentViewFactory;
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableSegmentedBar<T> build() {
        final DestroyableSegmentedBar<T> segmentedBar = new DestroyableSegmentedBar<>();
        super.build(segmentedBar);

        if(segments != null) {
            segmentedBar.registerAll(segments);
            segmentedBar.getSegments().addAll(this.segments);
        }

        if (orientation != null) {
            segmentedBar.setOrientation(orientation);
        }
        if (infoNodeFactory != null) {
            segmentedBar.setInfoNodeFactory(infoNodeFactory);
        }
        if (segmentViewFactory != null) {
            segmentedBar.setInfoNodeFactory(segmentViewFactory);
        }
        return segmentedBar;
    }

}
