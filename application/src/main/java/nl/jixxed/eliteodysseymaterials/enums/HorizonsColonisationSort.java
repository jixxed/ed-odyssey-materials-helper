package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.domain.ColonisationSearch;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation.BillOfMaterialsEntry;

import java.util.Comparator;

public enum HorizonsColonisationSort {

    ALPHABETICAL,
    CATEGORY,
    REQUIRED,
    DELIVERED,
    TO_COLLECT,
    TO_DELIVER;

    public String getLocalizationKey() {
        return "search.sort." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

    @SuppressWarnings("java:S1452")
    public static Comparator<BillOfMaterialsEntry> getSort(final ColonisationSearch search) {
        return switch (search.getColonisationSort()) {
            case ALPHABETICAL ->
                    Comparator.comparing((BillOfMaterialsEntry o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getCommodity().getLocalizationKey()));
            case CATEGORY ->
                    Comparator.comparing((BillOfMaterialsEntry o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getCommodity().getCommodityType().getLocalizationKey()))
                            .thenComparing((BillOfMaterialsEntry o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getCommodity().getLocalizationKey()));
            case REQUIRED ->
                    Comparator.comparing((BillOfMaterialsEntry bomEntry) -> bomEntry.getProgress().required()).reversed()
                            .thenComparing((BillOfMaterialsEntry o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getCommodity().getLocalizationKey()));
            case DELIVERED ->
                    Comparator.comparing((BillOfMaterialsEntry bomEntry) -> bomEntry.getProgress().provided()).reversed()
                            .thenComparing((BillOfMaterialsEntry o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getCommodity().getLocalizationKey()));
            case TO_COLLECT -> Comparator.comparing((BillOfMaterialsEntry bomEntry) -> Math.max(0,
                            bomEntry.getProgress().required()
                                    - bomEntry.getProgress().provided()
                                    - StorageService.getCommodityCount(bomEntry.getCommodity(), StoragePool.SHIP)
                                    - StorageService.getCommodityCount(bomEntry.getCommodity(), StoragePool.FLEETCARRIER))).reversed()
                    .thenComparing((BillOfMaterialsEntry o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getCommodity().getLocalizationKey()));
            case TO_DELIVER ->
                    Comparator.comparing((BillOfMaterialsEntry bomEntry) -> bomEntry.getProgress().required() - bomEntry.getProgress().provided()).reversed()
                            .thenComparing((BillOfMaterialsEntry o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getCommodity().getLocalizationKey()));
        };
    }
}
