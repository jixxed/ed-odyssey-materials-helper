package nl.jixxed.eliteodysseymaterials.export;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CsvExporter {

    public static String createCsvWishlist(final Map<Data, Integer> wishlistNeededDatas, final Map<Good, Integer> wishlistNeededGoods, final Map<Asset, Integer> wishlistNeededAssets) {
        final StringBuilder textBuilder = new StringBuilder();
        textBuilder.append(String.join(",", "Material", "Available BP+S", "Available FC", "Available Total", "Required", "Need"));
        textBuilder.append("\n");
        List.of(wishlistNeededDatas, wishlistNeededGoods, wishlistNeededAssets).forEach(wishlistNeededMaterials ->
                wishlistNeededMaterials.entrySet().stream()
                        .sorted(Comparator.comparing(item -> item.getKey().getStorageType()))
                        .forEach(item -> {
                            final String materialName = LocaleService.getLocalizedStringForCurrentLocale(item.getKey().getLocalizationKey());
                            final Integer ship = switch (item.getKey().getStorageType()) {
                                case GOOD -> StorageService.getGoods().get(item.getKey()).getAvailableValue();
                                case DATA -> StorageService.getData().get(item.getKey()).getAvailableValue();
                                case ASSET -> StorageService.getAssets().get(item.getKey()).getAvailableValue();
                                case TRADE, CONSUMABLE, OTHER -> 0;
                            };
                            final Integer fc = switch (item.getKey().getStorageType()) {
                                case GOOD -> StorageService.getGoods().get(item.getKey()).getFleetCarrierValue();
                                case DATA -> StorageService.getData().get(item.getKey()).getFleetCarrierValue();
                                case ASSET -> StorageService.getAssets().get(item.getKey()).getFleetCarrierValue();
                                case TRADE, CONSUMABLE, OTHER -> 0;
                            };
                            final Integer total = switch (item.getKey().getStorageType()) {
                                case GOOD -> StorageService.getGoods().get(item.getKey()).getTotalValue();
                                case DATA -> StorageService.getData().get(item.getKey()).getTotalValue();
                                case ASSET -> StorageService.getAssets().get(item.getKey()).getTotalValue();
                                case TRADE, CONSUMABLE, OTHER -> 0;
                            };
                            textBuilder.append(String.join(",", materialName, String.valueOf(ship), String.valueOf(fc), String.valueOf(total), String.valueOf(item.getValue()), String.valueOf(Math.max(0, item.getValue() - ship))));
                            textBuilder.append("\n");
                        })
        );
        return textBuilder.toString();
    }

    public static String createCsvWishlist(final Map<Raw, Integer> wishlistNeededRaw, final Map<Encoded, Integer> wishlistNeededEncoded, final Map<Manufactured, Integer> wishlistNeededManufactured, final Map<Commodity, Integer> wishlistNeededCommodity) {
        final StringBuilder textBuilder = new StringBuilder();
        textBuilder.append(String.join(",", "Material", "Available S", "Available FC", "Available Total", "Required", "Need"));
        textBuilder.append("\n");
        ((List<Map<HorizonsMaterial, Integer>>) (List<?>) List.of(wishlistNeededRaw, wishlistNeededEncoded, wishlistNeededManufactured, wishlistNeededCommodity)).forEach(wishlistNeededMaterials ->
                wishlistNeededMaterials.entrySet().stream()
                        .sorted(Comparator.comparing(item -> item.getKey().getStorageType()))
                        .forEach(item -> {
                            final String materialName = LocaleService.getLocalizedStringForCurrentLocale(item.getKey().getLocalizationKey());
                            final Integer ship = switch (item.getKey().getStorageType()) {
                                case RAW, ENCODED, MANUFACTURED -> StorageService.getMaterialCount(item.getKey());
                                case COMMODITY ->
                                        StorageService.getCommodityCount((Commodity) item.getKey(), StoragePool.SHIP);
                                default -> 0;
                            };
                            final Integer fc = item.getKey() instanceof Commodity commodity ? StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER) : 0;
                            final Integer total = switch (item.getKey().getStorageType()) {
                                case RAW, ENCODED, MANUFACTURED -> StorageService.getMaterialCount(item.getKey());
                                case COMMODITY ->
                                        StorageService.getCommodityCount((Commodity) item.getKey(), StoragePool.SHIP) + StorageService.getCommodityCount((Commodity) item.getKey(), StoragePool.FLEETCARRIER);
                                default -> 0;
                            };
                            textBuilder.append(String.join(",", materialName, String.valueOf(ship), String.valueOf(fc), String.valueOf(total), String.valueOf(item.getValue()), String.valueOf(Math.max(0, item.getValue() - ship))));
                            textBuilder.append("\n");
                        })
        );
        return textBuilder.toString();
    }

    public static String createCsvInventory() {
        final StringBuilder textBuilder = new StringBuilder();
        textBuilder.append(String.join(",", "Material", "Relevant", "Amount Backpack", "Amount Ship", "Amount Fleetcarrier", "Amount Total"));
        textBuilder.append("\n");
        StorageService.getGoods().forEach((material, storage) -> {
            if (storage.getTotalValue() > 0) {
                textBuilder.append(String.join(",", LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey()), OdysseyBlueprintConstants.isEngineeringOrBlueprintIngredientWithOverride(material) ? "Yes" : "No", String.valueOf(storage.getBackPackValue()), String.valueOf(storage.getShipLockerValue()), String.valueOf(storage.getFleetCarrierValue()), String.valueOf(storage.getTotalValue())));
                textBuilder.append("\n");
            }
        });
        StorageService.getAssets().forEach((material, storage) -> {
            if (storage.getTotalValue() > 0) {
                textBuilder.append(String.join(",", LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey()), OdysseyBlueprintConstants.isEngineeringOrBlueprintIngredientWithOverride(material) ? "Yes" : "No", String.valueOf(storage.getBackPackValue()), String.valueOf(storage.getShipLockerValue()), String.valueOf(storage.getFleetCarrierValue()), String.valueOf(storage.getTotalValue())));
                textBuilder.append("\n");
            }
        });
        StorageService.getData().forEach((material, storage) -> {
            if (storage.getTotalValue() > 0) {
                textBuilder.append(String.join(",", LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey()), OdysseyBlueprintConstants.isEngineeringOrBlueprintIngredientWithOverride(material) ? "Yes" : "No", String.valueOf(storage.getBackPackValue()), String.valueOf(storage.getShipLockerValue()), String.valueOf(storage.getFleetCarrierValue()), String.valueOf(storage.getTotalValue())));
                textBuilder.append("\n");
            }
        });
        StorageService.getRaw().forEach((material, amount) -> {
            if (amount > 0) {
                textBuilder.append(String.join(",", LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey()), "", "", String.valueOf(amount), "", String.valueOf(amount)));
                textBuilder.append("\n");
            }
        });
        StorageService.getEncoded().forEach((material, amount) -> {
            if (amount > 0) {
                textBuilder.append(String.join(",", LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey()), "", "", String.valueOf(amount), "", String.valueOf(amount)));
                textBuilder.append("\n");
            }
        });
        StorageService.getManufactured().forEach((material, amount) -> {
            if (amount > 0) {
                textBuilder.append(String.join(",", LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey()), "", "", String.valueOf(amount), "", String.valueOf(amount)));
                textBuilder.append("\n");
            }
        });
        final Set<Commodity> commodities = new HashSet<>();
        commodities.addAll(StorageService.getCommoditiesShip().keySet());
        commodities.addAll(StorageService.getCommoditiesFleetcarrier().keySet());
        commodities.forEach(commodity -> {
            final Integer shipAmount = StorageService.getCommodityCount(commodity, StoragePool.SHIP);
            final Integer fcAmount = StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER);
            if (shipAmount + fcAmount > 0) {
                textBuilder.append(String.join(",", LocaleService.getLocalizedStringForCurrentLocale(commodity.getLocalizationKey()), "", "", String.valueOf(shipAmount), String.valueOf(fcAmount), String.valueOf(shipAmount + fcAmount)));
                textBuilder.append("\n");
            }
        });
        return textBuilder.toString();
    }
}
