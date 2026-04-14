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

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationSearch;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation.BillOfMaterialsEntry;

import java.util.function.Predicate;

public enum HorizonsColonisationShow {
    ALL,
    NOT_COLLECTED,
    NOT_DELIVERED;

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public String getLocalizationKey() {
        return "search.filter." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

    @SuppressWarnings("java:S1452")
    public static Predicate<BillOfMaterialsEntry> getFilter(final ColonisationSearch search) {
        return switch (search.getColonisationShow()) {
            case ALL -> (BillOfMaterialsEntry o) -> true;
            case NOT_COLLECTED -> (BillOfMaterialsEntry bomEntry) -> (bomEntry.getProgress().required()
                    - bomEntry.getProgress().provided()
                    - StorageService.getCommodityCount(bomEntry.getCommodity(), StoragePool.SHIP)
                    - StorageService.getCommodityCount(bomEntry.getCommodity(), StoragePool.FLEETCARRIER)) > 0;
            case NOT_DELIVERED ->
                    (BillOfMaterialsEntry bomEntry) -> (bomEntry.getProgress().required() - bomEntry.getProgress().provided()) > 0;
        };
    }
}
