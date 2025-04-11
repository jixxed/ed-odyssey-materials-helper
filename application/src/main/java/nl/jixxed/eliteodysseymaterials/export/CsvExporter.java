package nl.jixxed.eliteodysseymaterials.export;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.WishlistMaterial;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
                                case GOOD, DATA, ASSET ->
                                        StorageService.getMaterialCount(item.getKey(), AmountType.AVAILABLE);
                                case CONSUMABLE, OTHER -> 0;
                            };
                            final Integer fc = switch (item.getKey().getStorageType()) {
                                case GOOD, DATA, ASSET ->
                                        StorageService.getMaterialCount(item.getKey(), AmountType.FLEETCARRIER);
                                case CONSUMABLE, OTHER -> 0;
                            };
                            final Integer total = switch (item.getKey().getStorageType()) {
                                case GOOD, DATA, ASSET ->
                                        StorageService.getMaterialCount(item.getKey(), AmountType.TOTAL);
                                case CONSUMABLE, OTHER -> 0;
                            };
                            textBuilder.append(String.join(",", materialName, String.valueOf(ship), String.valueOf(fc), String.valueOf(total), String.valueOf(item.getValue()), String.valueOf(Math.max(0, item.getValue() - ship))));
                            textBuilder.append("\n");
                        })
        );
        return textBuilder.toString();
    }

    public static String createCsvWishlist(final Map<Raw, WishlistMaterial> wishlistNeededRaw, final Map<Encoded, WishlistMaterial> wishlistNeededEncoded, final Map<Manufactured, WishlistMaterial> wishlistNeededManufactured, final Map<Commodity, WishlistMaterial> wishlistNeededCommodity) {
        final StringBuilder textBuilder = new StringBuilder();
        textBuilder.append(String.join(",", "Material", "Available S", "Available FC", "Available Total", "Required minimum", "Required current", "Required maximum", "Need"));
        textBuilder.append("\n");
        ((List<Map<HorizonsMaterial, WishlistMaterial>>) (List<?>) List.of(wishlistNeededRaw, wishlistNeededEncoded, wishlistNeededManufactured, wishlistNeededCommodity)).forEach(wishlistNeededMaterials ->
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
                            textBuilder.append(String.join(",", materialName, String.valueOf(ship), String.valueOf(fc), String.valueOf(total), String.valueOf(item.getValue().getMinimum()), String.valueOf(item.getValue().getRequired()), String.valueOf(item.getValue().getMaximum()), String.valueOf(Math.max(0, item.getValue().getRequired() - ship))));
                            textBuilder.append("\n");
                        })
        );
        return textBuilder.toString();
    }

    public static String createCsvInventory() {
        final StringBuilder textBuilder = new StringBuilder();
        textBuilder.append(String.join(",", "Material", "Relevant", "Amount Backpack", "Amount Ship", "Amount Fleetcarrier", "Amount Total"));
        textBuilder.append("\n");

        Arrays.stream(Good.values()).forEach(material -> addMaterialLine(material, textBuilder));
        Arrays.stream(Asset.values()).forEach(material -> addMaterialLine(material, textBuilder));
        Arrays.stream(Data.values()).forEach(material -> addMaterialLine(material, textBuilder));

        Arrays.stream(Raw.values()).forEach(material -> addMaterialLine(material, textBuilder));
        Arrays.stream(Encoded.values()).forEach(material -> addMaterialLine(material, textBuilder));
        Arrays.stream(Manufactured.values()).forEach(material -> addMaterialLine(material, textBuilder));

        Stream.concat(Arrays.stream(RegularCommodity.values()), Arrays.stream(RareCommodity.values())).forEach((commodity) -> {
            final Integer shipAmount = StorageService.getCommodityCount(commodity, StoragePool.SHIP);
            final Integer fcAmount = StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER);
            if (shipAmount + fcAmount > 0) {
                textBuilder.append(String.join(",", LocaleService.getLocalizedStringForCurrentLocale(commodity.getLocalizationKey()), "", "", String.valueOf(shipAmount), String.valueOf(fcAmount), String.valueOf(shipAmount + fcAmount)));
                textBuilder.append("\n");
            }
        });
        return textBuilder.toString();
    }

    private static void addMaterialLine(HorizonsMaterial material, StringBuilder textBuilder) {
        final Integer materialCount = StorageService.getMaterialCount(material);
        if (materialCount > 0) {
            textBuilder.append(String.join(",", LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey()), "", "", String.valueOf(materialCount), "", String.valueOf(materialCount)));
            textBuilder.append("\n");
        }
    }

    private static void addMaterialLine(OdysseyMaterial material, StringBuilder textBuilder) {
        final Integer total = StorageService.getMaterialCount(material, AmountType.TOTAL);
        if (total > 0) {
            final String backpack = String.valueOf(StorageService.getMaterialCount(material, AmountType.BACKPACK));
            final String shiplocker = String.valueOf(StorageService.getMaterialCount(material, AmountType.SHIPLOCKER));
            final String fleetcarrier = String.valueOf(StorageService.getMaterialCount(material, AmountType.FLEETCARRIER));
            textBuilder.append(String.join(",", LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey()), OdysseyBlueprintConstants.isEngineeringOrBlueprintIngredientWithOverride(material) ? "Yes" : "No", backpack, shiplocker, fleetcarrier, String.valueOf(total)));
            textBuilder.append("\n");
        }
    }
}
