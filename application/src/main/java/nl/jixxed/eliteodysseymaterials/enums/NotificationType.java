package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotificationType {
    IRRELEVANT_PICKUP(true),
    WISHLIST_PICKUP(false),
    TRADE(true),
    IMPORT(true),
    COPY(true),
    ERROR(true);

    private final boolean defaultEnabled;

}
