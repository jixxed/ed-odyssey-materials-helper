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
