package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MaterialTradeType {
    DOWNTRADE(1), CROSS_DOWNTRADE(2), UPTRADE(3), CROSS_UPTRADE(4), IMPOSSIBLE(5);
    final int preference;
}
