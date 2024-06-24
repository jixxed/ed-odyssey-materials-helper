package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotificationType {
    IRRELEVANT_PICKUP(true),
    WISHLIST_PICKUP(false),
    RELEVANT_POINT(true),
    WISHLIST_POINT(true),
    IRRELEVANT_POINT(true),
    HGE(true),
    TRADE(true),
    IMPORT(true),
    COPY(true),
    ERROR(true),
    SUCCESS(true);

    private final boolean defaultEnabled;

}
