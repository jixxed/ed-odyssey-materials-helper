package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum EmpireRank {
    NONE(0),
    OUTSIDER(1),
    SERF(2),
    MASTER(3),
    SQUIRE(4),
    KNIGHT(5),
    LORD(6),
    BARON(7),
    VISCOUNT(8),
    COUNT(9),
    EARL(10),
    MARQUIS(11),
    DUKE(12),
    PRINCE(13),
    KING(14);

    @Getter
    private final Integer rank;

    public static EmpireRank forRank(final Integer rank) {
        return Arrays.stream(EmpireRank.values())
                .filter(empireRank -> empireRank.rank.equals(rank))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown Empire Rank: " + rank));
    }

    public String getLocalizationKey() {
        return "empire.rank." + this.name().toLowerCase();
    }
}
