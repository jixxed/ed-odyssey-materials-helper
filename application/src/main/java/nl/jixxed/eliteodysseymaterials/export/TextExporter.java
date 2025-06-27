package nl.jixxed.eliteodysseymaterials.export;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.WishlistMaterial;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import org.apache.poi.ss.formula.eval.NotImplementedException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextExporter {

    public static String createTextWishlist(final Map<Data, Integer> wishlistNeededDatas, final Map<Good, Integer> wishlistNeededGoods, final Map<Asset, Integer> wishlistNeededAssets) {
        final StringBuilder textBuilder = new StringBuilder();
        final Integer maxNameLengthData = wishlistNeededDatas.keySet().stream()
                .map(item -> LocaleService.getLocalizedStringForCurrentLocale(item.getLocalizationKey()).length())
                .max(Comparator.comparing(Integer::intValue)).orElse(0);
        final Integer maxNameLengthGood = wishlistNeededGoods.keySet().stream()
                .map(item -> LocaleService.getLocalizedStringForCurrentLocale(item.getLocalizationKey()).length())
                .max(Comparator.comparing(Integer::intValue)).orElse(0);
        final Integer maxNameLengthAsset = wishlistNeededAssets.keySet().stream()
                .map(item -> LocaleService.getLocalizedStringForCurrentLocale(item.getLocalizationKey()).length())
                .max(Comparator.comparing(Integer::intValue)).orElse(0);
        final Integer maxNameLength = Math.max(maxNameLengthData, Math.max(maxNameLengthGood, maxNameLengthAsset));
        final String materialColumnWidth = "%-" + (maxNameLength + 5) + "s";
        textBuilder.append(String.format(materialColumnWidth, "Material"));
        textBuilder.append(String.format("%18s", "Available BP+S"));
        textBuilder.append(String.format("%18s", "Available FC"));
        textBuilder.append(String.format("%18s", "Available Total"));
        textBuilder.append(String.format("%12s", "Required"));
        textBuilder.append(String.format("%12s", "Need"));
        textBuilder.append("\n\n");
        List.of(wishlistNeededDatas, wishlistNeededGoods, wishlistNeededAssets).forEach(wishlistNeededMaterials ->
                wishlistNeededMaterials.entrySet().stream()
                        .sorted(Comparator.comparing(item -> item.getKey().getStorageType()))
                        .forEach(item -> {
                            textBuilder.append(String.format(materialColumnWidth, LocaleService.getLocalizedStringForCurrentLocale(item.getKey().getLocalizationKey())));
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
                            textBuilder.append(String.format("%18s", ship));
                            textBuilder.append(String.format("%18s", fc));
                            textBuilder.append(String.format("%18s", total));
                            textBuilder.append(String.format("%12s", item.getValue()));
                            textBuilder.append(String.format("%12s", Math.max(0, item.getValue() - ship)));
                            textBuilder.append("\n");
                        })
        );
        return textBuilder.toString();
    }

    public static String createTextWishlist(final Map<Raw, WishlistMaterial> wishlistNeededRaw, final Map<Encoded, WishlistMaterial> wishlistNeededEncoded, final Map<Manufactured, WishlistMaterial> wishlistNeededManufactured, final Map<Commodity, WishlistMaterial> wishlistNeededCommodity) {
        final StringBuilder textBuilder = new StringBuilder();
        final Integer maxNameLengthRaw = wishlistNeededRaw.keySet().stream()
                .map(item -> LocaleService.getLocalizedStringForCurrentLocale(item.getLocalizationKey()).length())
                .max(Comparator.comparing(Integer::intValue)).orElse(0);
        final Integer maxNameLengthEncoded = wishlistNeededEncoded.keySet().stream()
                .map(item -> LocaleService.getLocalizedStringForCurrentLocale(item.getLocalizationKey()).length())
                .max(Comparator.comparing(Integer::intValue)).orElse(0);
        final Integer maxNameLengthManufactured = wishlistNeededManufactured.keySet().stream()
                .map(item -> LocaleService.getLocalizedStringForCurrentLocale(item.getLocalizationKey()).length())
                .max(Comparator.comparing(Integer::intValue)).orElse(0);
        final Integer maxNameLengthCommodity = wishlistNeededCommodity.keySet().stream()
                .map(item -> LocaleService.getLocalizedStringForCurrentLocale(item.getLocalizationKey()).length())
                .max(Comparator.comparing(Integer::intValue)).orElse(0);
        final Integer maxNameLength = Math.max(maxNameLengthRaw, Math.max(maxNameLengthEncoded, Math.max(maxNameLengthManufactured, maxNameLengthCommodity)));
        final String materialColumnWidth = "%-" + (maxNameLength + 5) + "s";
        textBuilder.append(String.format(materialColumnWidth, "Material"));
        textBuilder.append(String.format("%18s", "Available S"));
        textBuilder.append(String.format("%18s", "Available FC"));
        textBuilder.append(String.format("%18s", "Available Total"));
        textBuilder.append(String.format("%14s", "Required min"));
        textBuilder.append(String.format("%14s", "Required cur"));
        textBuilder.append(String.format("%14s", "Required max"));
        textBuilder.append(String.format("%14s", "Need"));
        textBuilder.append("\n\n");
        ((List<Map<HorizonsMaterial, WishlistMaterial>>) (List<?>) List.of(wishlistNeededRaw, wishlistNeededEncoded, wishlistNeededManufactured, wishlistNeededCommodity)).forEach(wishlistNeededMaterials ->
                wishlistNeededMaterials.entrySet().stream()
                        .sorted(Comparator.comparing(item -> item.getKey().getStorageType()))
                        .forEach(item -> {
                            textBuilder.append(String.format(materialColumnWidth, LocaleService.getLocalizedStringForCurrentLocale(item.getKey().getLocalizationKey())));
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
                            textBuilder.append(String.format("%18s", ship));
                            textBuilder.append(String.format("%18s", fc));
                            textBuilder.append(String.format("%18s", total));
                            textBuilder.append(String.format("%12s", item.getValue().getMinimum()));
                            textBuilder.append(String.format("%12s", item.getValue().getRequired()));
                            textBuilder.append(String.format("%12s", item.getValue().getMaximum()));
                            textBuilder.append(String.format("%12s", Math.max(0, item.getValue().getRequired() - ship)));
                            textBuilder.append("\n");
                        })
        );
        return textBuilder.toString();
    }

    public static String createTextInventory() {
        throw new NotImplementedException("createTextInventory not implemented");
    }
}
