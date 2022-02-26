package nl.jixxed.eliteodysseymaterials.templates.segmentbar;

import lombok.Getter;
import org.controlsfx.control.SegmentedBar;

public class TypeSegment extends SegmentedBar.Segment {
    @Getter
    final SegmentType segmentType;

    public TypeSegment(final Integer materialCount, final SegmentType segmentType) {
        super(materialCount, materialCount.toString());
        this.segmentType = segmentType;
    }
}
