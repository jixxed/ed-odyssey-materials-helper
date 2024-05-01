package nl.jixxed.eliteodysseymaterials.templates.components.segmentbar;

public enum SegmentType {
    PRESENT, NOT_PRESENT, POWER_GROUP_P, POWER_GROUP_1, POWER_GROUP_2, POWER_GROUP_3, POWER_GROUP_4, POWER_GROUP_5, POWER_GROUP_NONE, POWER_OVERPOWER, HARDPOINT_NONE, HARDPOINT_THERMAL, HARDPOINT_KINETIC, HARDPOINT_EXPLOSIVE, HARDPOINT_ABSOLUTE, HARDPOINT_CAUSTIC, HARDPOINT_ANTIXENO;


    public String getLocalizationKey() {
        return "segment.type." + this.name().toLowerCase();
    }
}