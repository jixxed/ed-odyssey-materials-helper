/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
