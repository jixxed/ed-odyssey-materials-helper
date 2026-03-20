package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum FederationRank {
    NONE(0),
    RECRUIT(1),
    CADET(2),
    MIDSHIPMAN(3),
    PETTY_OFFICER(4),
    CHIEF_PETTY_OFFICER(5),
    WARRANT_OFFICER(6),
    ENSIGN(7),
    LIEUTENANT(8),
    LIEUTENANT_COMMANDER(9),
    POST_COMMANDER(10),
    POST_CAPTAIN(11),
    REAR_ADMIRAL(12),
    VICE_ADMIRAL(13),
    ADMIRAL(14);

    @Getter
    private final Integer rank;

    public static FederationRank forRank(final Integer rank) {
        return Arrays.stream(FederationRank.values())
                .filter(federationRank -> federationRank.rank.equals(rank))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown Federation Rank: " + rank));
    }

    public String getLocalizationKey() {
        return "federation.rank." + this.name().toLowerCase();
    }
}
