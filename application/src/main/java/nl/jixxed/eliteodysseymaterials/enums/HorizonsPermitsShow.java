package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.domain.PermitsSearch;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.horizons.permits.PermitCard;

import java.util.function.Predicate;

public enum HorizonsPermitsShow {
    ALL, ALL_EXCEPT_STARTER, STARTER, IMPERIAL_NAVY_RANK, FEDERAL_NAVY_RANK, ALLIED, GAME_RANK, FREE;


    public String getLocalizationKey() {
        return "search.filter." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

    @SuppressWarnings("java:S1452")
    public static Predicate<PermitCard> getFilter(final PermitsSearch search) {
        return switch (search.getPermitsShow()) {
            case ALL -> _ -> true;
            case ALL_EXCEPT_STARTER -> (PermitCard o) -> !PermitType.STARTER.equals(o.getPermit().getType());
            case STARTER -> (PermitCard o) -> PermitType.STARTER.equals(o.getPermit().getType());
            case IMPERIAL_NAVY_RANK -> (PermitCard o) -> PermitType.IMPERIAL_NAVY_RANK.equals(o.getPermit().getType());
            case FEDERAL_NAVY_RANK -> (PermitCard o) -> PermitType.FEDERAL_NAVY_RANK.equals(o.getPermit().getType());
            case ALLIED -> (PermitCard o) -> PermitType.ALLIED.equals(o.getPermit().getType());
            case GAME_RANK -> (PermitCard o) -> PermitType.GAME_RANK.equals(o.getPermit().getType());
            case FREE -> (PermitCard o) -> PermitType.FREE.equals(o.getPermit().getType());
        };
    }
}
