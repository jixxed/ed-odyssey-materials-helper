package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GuardianStructureLayout {
    STICKYHAND(GuardianStructureLayoutType.VESSEL),
    BEAR(GuardianStructureLayoutType.WEAPON),
    TURTLE(GuardianStructureLayoutType.MODULE),
    BOWL(GuardianStructureLayoutType.WEAPON),
    SQUID(GuardianStructureLayoutType.VESSEL),
    ROBOLOBSTER(GuardianStructureLayoutType.VESSEL),
    HAMMERBOT(GuardianStructureLayoutType.WEAPON);
    @Getter
    private final GuardianStructureLayoutType type;

    public static GuardianStructureLayout forName(final String layout) {
        for (final GuardianStructureLayout guardianStructureLayout : GuardianStructureLayout.values()) {
            if (guardianStructureLayout.name().equalsIgnoreCase(layout)) {
                return guardianStructureLayout;
            }
        }
        throw new IllegalArgumentException("No GuardianStructureLayout found for " + layout);
    }

    public String getLocalizationKey() {
        return "guardian.structure.layout." + this.name().toLowerCase();
    }
}
