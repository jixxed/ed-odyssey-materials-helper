package nl.jixxed.eliteodysseymaterials.export;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.WishlistMaterial;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XlsExporter {

    public static XSSFWorkbook createXlsWishlist(final Map<Data, Integer> wishlistNeededDatas, final Map<Good, Integer> wishlistNeededGoods, final Map<Asset, Integer> wishlistNeededAssets) {
        final XSSFWorkbook workbook = new XSSFWorkbook();
        final XSSFSheet sheet = workbook.createSheet("wishlist");
//        final StringBuilder textBuilder = new StringBuilder();
        final Row row = sheet.createRow(0);
        final CellStyle style = workbook.createCellStyle();
        final XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(sheet, row, 0, "Material", style);
        createCell(sheet, row, 1, "Available BP+S", style);
        createCell(sheet, row, 2, "Available FC", style);
        createCell(sheet, row, 3, "Available Total", style);
        createCell(sheet, row, 4, "Required", style);
        createCell(sheet, row, 5, "Need", style);
        final AtomicInteger rowNumber = new AtomicInteger(1);
        final CellStyle dataStyle = workbook.createCellStyle();
        final XSSFFont dataFont = workbook.createFont();
        dataFont.setFontHeight(14);
        dataStyle.setFont(dataFont);
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
                            final Row dataRow = sheet.createRow(rowNumber.getAndIncrement());
                            createCell(sheet, dataRow, 0, materialName, dataStyle);
                            createCell(sheet, dataRow, 1, ship, dataStyle);
                            createCell(sheet, dataRow, 2, fc, dataStyle);
                            createCell(sheet, dataRow, 3, total, dataStyle);
                            createCell(sheet, dataRow, 4, item.getValue(), dataStyle);
                            createCell(sheet, dataRow, 5, Math.max(0, item.getValue() - ship), dataStyle);
                        })
        );
        return workbook;
    }

    public static XSSFWorkbook createXlsWishlist(final Map<Raw, WishlistMaterial> wishlistNeededRaw, final Map<Encoded, WishlistMaterial> wishlistNeededEncoded, final Map<Manufactured, WishlistMaterial> wishlistNeededManufactured, final Map<Commodity, WishlistMaterial> wishlistNeededCommodity) {
        final XSSFWorkbook workbook = new XSSFWorkbook();
        final XSSFSheet sheet = workbook.createSheet("wishlist");
//        final StringBuilder textBuilder = new StringBuilder();
        final Row row = sheet.createRow(0);
        final CellStyle style = workbook.createCellStyle();
        final XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(sheet, row, 0, "Material", style);
        createCell(sheet, row, 1, "Available S", style);
        createCell(sheet, row, 2, "Available FC", style);
        createCell(sheet, row, 3, "Available Total", style);
        createCell(sheet, row, 4, "Required", style);
        createCell(sheet, row, 5, "Need", style);
        final AtomicInteger rowNumber = new AtomicInteger(1);
        final CellStyle dataStyle = workbook.createCellStyle();
        final XSSFFont dataFont = workbook.createFont();
        dataFont.setFontHeight(14);
        dataStyle.setFont(dataFont);
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
                            final Row dataRow = sheet.createRow(rowNumber.getAndIncrement());
                            createCell(sheet, dataRow, 0, materialName, dataStyle);
                            createCell(sheet, dataRow, 1, ship, dataStyle);
                            createCell(sheet, dataRow, 2, fc, dataStyle);
                            createCell(sheet, dataRow, 3, total, dataStyle);
                            createCell(sheet, dataRow, 4, item.getValue(), dataStyle);
                            createCell(sheet, dataRow, 5, Math.max(0, item.getValue().getRequired() - ship), dataStyle);
                        })
        );
        return workbook;
    }

    private static void createCell(final Sheet sheet, final Row row, final int columnCount, final Object value, final CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        final Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    public static XSSFWorkbook createXlsInventory() {
        final XSSFWorkbook workbook = new XSSFWorkbook();
        final XSSFSheet sheetGoods = workbook.createSheet("Goods");
        final XSSFSheet sheetAssets = workbook.createSheet("Assets");
        final XSSFSheet sheetData = workbook.createSheet("Data");
        final XSSFSheet sheetRaw = workbook.createSheet("Raw");
        final XSSFSheet sheetEncoded = workbook.createSheet("Encoded");
        final XSSFSheet sheetManufactured = workbook.createSheet("Manufactured");
        final XSSFSheet sheetCommodity = workbook.createSheet("Commodities");
        createHeaderOdyssey(workbook, sheetGoods);
        createHeaderOdyssey(workbook, sheetAssets);
        createHeaderOdyssey(workbook, sheetData);
        createHeaderHorizons(workbook, sheetRaw);
        createHeaderHorizons(workbook, sheetEncoded);
        createHeaderHorizons(workbook, sheetManufactured);
        createHeaderHorizonsCommodity(workbook, sheetCommodity);
        final AtomicInteger rowNumber = new AtomicInteger(1);
        final CellStyle dataStyle = workbook.createCellStyle();
        final XSSFFont dataFont = workbook.createFont();
        dataFont.setFontHeight(14);
        dataStyle.setFont(dataFont);
        StorageService.getGoods().forEach((material, storage) -> {
            if (storage.getTotalValue() > 0) {
                final String materialName = LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey());
                final Row dataRow = sheetGoods.createRow(rowNumber.getAndIncrement());
                createCell(sheetGoods, dataRow, 0, materialName, dataStyle);
                createCell(sheetGoods, dataRow, 1, OdysseyBlueprintConstants.isEngineeringOrBlueprintIngredientWithOverride(material) ? "Yes" : "No", dataStyle);
                createCell(sheetGoods, dataRow, 2, storage.getBackPackValue(), dataStyle);
                createCell(sheetGoods, dataRow, 3, storage.getShipLockerValue(), dataStyle);
                createCell(sheetGoods, dataRow, 4, storage.getFleetCarrierValue(), dataStyle);
                createCell(sheetGoods, dataRow, 5, storage.getTotalValue(), dataStyle);
            }
        });
        rowNumber.set(1);
        StorageService.getAssets().forEach((material, storage) -> {
            if (storage.getTotalValue() > 0) {
                final String materialName = LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey());
                final Row dataRow = sheetAssets.createRow(rowNumber.getAndIncrement());
                createCell(sheetAssets, dataRow, 0, materialName, dataStyle);
                createCell(sheetAssets, dataRow, 1, OdysseyBlueprintConstants.isEngineeringOrBlueprintIngredientWithOverride(material) ? "Yes" : "No", dataStyle);
                createCell(sheetAssets, dataRow, 2, storage.getBackPackValue(), dataStyle);
                createCell(sheetAssets, dataRow, 3, storage.getShipLockerValue(), dataStyle);
                createCell(sheetAssets, dataRow, 4, storage.getFleetCarrierValue(), dataStyle);
                createCell(sheetAssets, dataRow, 5, storage.getTotalValue(), dataStyle);
            }
        });
        rowNumber.set(1);
        StorageService.getData().forEach((material, storage) -> {
            if (storage.getTotalValue() > 0) {
                final String materialName = LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey());
                final Row dataRow = sheetData.createRow(rowNumber.getAndIncrement());
                createCell(sheetData, dataRow, 0, materialName, dataStyle);
                createCell(sheetData, dataRow, 1, OdysseyBlueprintConstants.isEngineeringOrBlueprintIngredientWithOverride(material) ? "Yes" : "No", dataStyle);
                createCell(sheetData, dataRow, 2, storage.getBackPackValue(), dataStyle);
                createCell(sheetData, dataRow, 3, storage.getShipLockerValue(), dataStyle);
                createCell(sheetData, dataRow, 4, storage.getFleetCarrierValue(), dataStyle);
                createCell(sheetData, dataRow, 5, storage.getTotalValue(), dataStyle);
            }
        });
        rowNumber.set(1);
        StorageService.getRaw().forEach((material, amount) -> {
            if (amount > 0) {
                final String materialName = LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey());
                final Row dataRow = sheetRaw.createRow(rowNumber.getAndIncrement());
                createCell(sheetRaw, dataRow, 0, materialName, dataStyle);
                createCell(sheetRaw, dataRow, 1, amount, dataStyle);
            }
        });
        rowNumber.set(1);
        StorageService.getEncoded().forEach((material, amount) -> {
            if (amount > 0) {
                final String materialName = LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey());
                final Row dataRow = sheetEncoded.createRow(rowNumber.getAndIncrement());
                createCell(sheetEncoded, dataRow, 0, materialName, dataStyle);
                createCell(sheetEncoded, dataRow, 1, amount, dataStyle);
            }
        });
        rowNumber.set(1);
        StorageService.getManufactured().forEach((material, amount) -> {
            if (amount > 0) {
                final String materialName = LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey());
                final Row dataRow = sheetManufactured.createRow(rowNumber.getAndIncrement());
                createCell(sheetManufactured, dataRow, 0, materialName, dataStyle);
                createCell(sheetManufactured, dataRow, 1, amount, dataStyle);
            }
        });
        rowNumber.set(1);
        final Set<Commodity> commodities = new HashSet<>();
        commodities.addAll(StorageService.getCommoditiesShip().keySet());
        commodities.addAll(StorageService.getCommoditiesFleetcarrier().keySet());
        commodities.forEach(commodity -> {
            final Integer shipAmount = StorageService.getCommodityCount(commodity, StoragePool.SHIP);
            final Integer fcAmount = StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER);
            if (shipAmount + fcAmount > 0) {
                final String materialName = LocaleService.getLocalizedStringForCurrentLocale(commodity.getLocalizationKey());
                final Row dataRow = sheetCommodity.createRow(rowNumber.getAndIncrement());
                createCell(sheetCommodity, dataRow, 0, materialName, dataStyle);
                createCell(sheetCommodity, dataRow, 1, shipAmount, dataStyle);
                createCell(sheetCommodity, dataRow, 2, fcAmount, dataStyle);
                createCell(sheetCommodity, dataRow, 3, shipAmount + fcAmount, dataStyle);
            }
        });
        return workbook;
    }

    private static void createHeaderOdyssey(final XSSFWorkbook workbook, final XSSFSheet sheet) {
        final Row row = sheet.createRow(0);
        final CellStyle style = workbook.createCellStyle();
        final XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(sheet, row, 0, "Material", style);
        createCell(sheet, row, 1, "Relevant", style);
        createCell(sheet, row, 2, "Amount Backpack", style);
        createCell(sheet, row, 3, "Amount Ship", style);
        createCell(sheet, row, 4, "Amount Fleetcarrier", style);
        createCell(sheet, row, 5, "Amount Total", style);
    }

    private static void createHeaderHorizons(final XSSFWorkbook workbook, final XSSFSheet sheet) {
        final Row row = sheet.createRow(0);
        final CellStyle style = workbook.createCellStyle();
        final XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(sheet, row, 0, "Material", style);
        createCell(sheet, row, 1, "Amount Ship", style);
    }

    private static void createHeaderHorizonsCommodity(final XSSFWorkbook workbook, final XSSFSheet sheet) {
        final Row row = sheet.createRow(0);
        final CellStyle style = workbook.createCellStyle();
        final XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(sheet, row, 0, "Material", style);
        createCell(sheet, row, 1, "Amount Ship", style);
        createCell(sheet, row, 2, "Amount Fleetcarrier", style);
        createCell(sheet, row, 3, "Amount Total", style);
    }
}
