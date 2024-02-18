package nl.jixxed.eliteodysseymaterials.templates.components.segmentbar;

import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import org.controlsfx.control.SegmentedBar;

public class TypeSegment extends SegmentedBar.Segment {
    @Getter
    final SegmentType segmentType;

    public TypeSegment(final Integer materialCount, final SegmentType segmentType) {
        super(materialCount, materialCount.toString());
        this.segmentType = segmentType;
    }
    public TypeSegment(final Double materialCount, final SegmentType segmentType) {
        super(materialCount, materialCount.toString());
        this.segmentType = segmentType;
        this.textProperty().bind(LocaleService.getStringBinding(segmentType.getLocalizationKey()));
    }


}
