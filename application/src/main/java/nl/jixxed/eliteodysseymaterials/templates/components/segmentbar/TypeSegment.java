package nl.jixxed.eliteodysseymaterials.templates.components.segmentbar;

import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;
import org.controlsfx.control.SegmentedBar;

public class TypeSegment extends SegmentedBar.Segment implements Destroyable {
    @Getter
    final SegmentType segmentType;

    public TypeSegment(final Integer percentage, final SegmentType segmentType) {
        super(percentage, percentage.toString());
        this.segmentType = segmentType;
    }

    public TypeSegment(final Double percentage, final SegmentType segmentType) {
        super(percentage, percentage.toString());
        this.segmentType = segmentType;
        this.addBinding(this.textProperty(), LocaleService.getStringBinding(segmentType.getLocalizationKey()));
    }


}
