package nl.jixxed.eliteodysseymaterials.templates.components.segmentbar;

public enum SegmentType {
    PRESENT, NOT_PRESENT, POWER_GROUP_1, POWER_GROUP_2, POWER_GROUP_3, POWER_GROUP_4, POWER_GROUP_5, POWER_GROUP_NONE;


    public String getLocalizationKey() {
        return "segment.type." + this.name().toLowerCase();
    }
}