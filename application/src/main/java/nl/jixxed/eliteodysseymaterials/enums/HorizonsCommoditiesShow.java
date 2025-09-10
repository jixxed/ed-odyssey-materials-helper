package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.CommoditiesSearch;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.MarketService;
import nl.jixxed.eliteodysseymaterials.templates.horizons.commodities.HorizonsCommodityCard;

import java.util.function.Predicate;

public enum HorizonsCommoditiesShow {
    ALL,
    ALL_WITH_STOCK,
    SOLD_AT_STATION,
    BOUGHT_AT_STATION,
    SHIP,
    FLEETCARRIER,
    SQUADRONCARRIER,
    CHEMICALS,
    CONSUMER_ITEMS,
    FOODS,
    INDUSTRIAL_MATERIALS,
    LEGAL_DRUGS,
    MACHINERY,
    MEDICINES,
    METALS,
    MINERALS,
    NONMARKETABLE,
    SALVAGE,
    SLAVERY,
    TECHNOLOGY,
    TEXTILES,
    WASTE,
    WEAPONS,
    POWERPLAY;

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public String getLocalizationKey() {
        return "search.filter." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

    @SuppressWarnings("java:S1452")
    public static Predicate<HorizonsCommodityCard> getFilter(final CommoditiesSearch search) {
        return switch (search.getCommoditiesShow()) {
            case ALL -> (HorizonsCommodityCard o) -> true;
            case ALL_WITH_STOCK -> (HorizonsCommodityCard o) -> o.getFleetCarrierAmount() > 0 ||o.getSquadronCarrierAmount() > 0 || o.getShipAmount() > 0;
            case SHIP -> (HorizonsCommodityCard o) -> o.getShipAmount() > 0;
            case FLEETCARRIER -> (HorizonsCommodityCard o) -> o.getFleetCarrierAmount() > 0;
            case SQUADRONCARRIER -> (HorizonsCommodityCard o) -> o.getSquadronCarrierAmount() > 0;
            case CHEMICALS -> (HorizonsCommodityCard o) -> o.getCommodity().getCommodityType().equals(CommodityType.CHEMICALS);
            case CONSUMER_ITEMS -> (HorizonsCommodityCard o) -> o.getCommodity().getCommodityType().equals(CommodityType.CONSUMER_ITEMS);
            case FOODS -> (HorizonsCommodityCard o) -> o.getCommodity().getCommodityType().equals(CommodityType.FOODS);
            case INDUSTRIAL_MATERIALS -> (HorizonsCommodityCard o) -> o.getCommodity().getCommodityType().equals(CommodityType.INDUSTRIAL_MATERIALS);
            case LEGAL_DRUGS -> (HorizonsCommodityCard o) -> o.getCommodity().getCommodityType().equals(CommodityType.LEGAL_DRUGS);
            case MACHINERY -> (HorizonsCommodityCard o) -> o.getCommodity().getCommodityType().equals(CommodityType.MACHINERY);
            case MEDICINES -> (HorizonsCommodityCard o) -> o.getCommodity().getCommodityType().equals(CommodityType.MEDICINES);
            case METALS -> (HorizonsCommodityCard o) -> o.getCommodity().getCommodityType().equals(CommodityType.METALS);
            case MINERALS -> (HorizonsCommodityCard o) -> o.getCommodity().getCommodityType().equals(CommodityType.MINERALS);
            case NONMARKETABLE -> (HorizonsCommodityCard o) -> o.getCommodity().getCommodityType().equals(CommodityType.NONMARKETABLE);
            case SALVAGE -> (HorizonsCommodityCard o) -> o.getCommodity().getCommodityType().equals(CommodityType.SALVAGE);
            case SLAVERY -> (HorizonsCommodityCard o) -> o.getCommodity().getCommodityType().equals(CommodityType.SLAVERY);
            case TECHNOLOGY -> (HorizonsCommodityCard o) -> o.getCommodity().getCommodityType().equals(CommodityType.TECHNOLOGY);
            case TEXTILES -> (HorizonsCommodityCard o) -> o.getCommodity().getCommodityType().equals(CommodityType.TEXTILES);
            case WASTE -> (HorizonsCommodityCard o) -> o.getCommodity().getCommodityType().equals(CommodityType.WASTE);
            case WEAPONS -> (HorizonsCommodityCard o) -> o.getCommodity().getCommodityType().equals(CommodityType.WEAPONS);
            case POWERPLAY -> (HorizonsCommodityCard o) -> o.getCommodity().getCommodityType().equals(CommodityType.POWERPLAY);
            case SOLD_AT_STATION -> (HorizonsCommodityCard o) -> MarketService.sells(o.getCommodity());
            case BOUGHT_AT_STATION -> (HorizonsCommodityCard o) -> MarketService.buys(o.getCommodity());
        };
    }
}
